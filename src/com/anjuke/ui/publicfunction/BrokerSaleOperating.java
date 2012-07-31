package com.anjuke.ui.publicfunction;

import java.util.ArrayList;

import com.anjuke.ui.bean.AnjukeSaleInfo;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;

public class BrokerSaleOperating {
	private static ArrayList<String> houseListNumber = null;
	private static int isPayed = 0;
	//出售房源发布的方法==============================================================================================
	public static void releaseSale(Browser driver,AnjukeSaleInfo saleInfo,boolean needPic)
	{
		houseListNumber = houseNumber(driver);
		driver.get("http://my.anjuke.com/user/broker/property/sale/step1");
		if (driver.check(Init.G_objMap.get("ajk_releaseSale_div_full"))) {
			String ps = driver.printScreen();
			houseListNumber = houseNumber(driver);
			Report.writeHTMLLog("发布出售", "抱歉，您本期发布的新房源已达上限，无法再发布了。"+ "本期已发布的房源:"+houseListNumber.get(0)+"剩余发布房源数:"+houseListNumber.get(1),Report.FAIL, ps);
		} else {
			// 设置小区名称
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_xiaoqu_yinyu"), "选中小区名输入框");//为了IE6
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_xiaoqu_yinyu"), saleInfo.getCommunityName(),"输入小区名称");
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_xiaoqu_nameClick"),"选择小区");

			// 税费自理价
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_proPrice"),	saleInfo.getPriceTaxe(), "税费自理价："+saleInfo.getPriceTaxe()+"万");

			// 设置房屋面积
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chanzhengArea"), saleInfo.getHouseArea(),"产证面积");
			
			// 设置房型
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_room"), saleInfo.getHouseType_S(),saleInfo.getHouseType_S()+"房");
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_hall"), saleInfo.getHouseType_T(),saleInfo.getHouseType_T()+"厅");
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_toilet"), saleInfo.getHouseType_W(),saleInfo.getHouseType_W()+"卫");

			// 设置楼层
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_louceng"),saleInfo.getFloorCur(), "当前第"+saleInfo.getFloorCur()+"层");
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_loucengCnt"), saleInfo.getFloorTotal(), "共"+saleInfo.getFloorTotal()+"层");

			// 设置房屋类型  --新版发房公寓是下拉里的第3个
			driver.select(Init.G_objMap.get("anjuke_wangluojingjiren_sale_housetype"),saleInfo.getHouseType());
			
			// 装修情况
			driver.select(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fitMent"),saleInfo.getFitmentInfo());

			// 朝向
			driver.select(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chaoxiang"), saleInfo.getOrientations());
			
			// 建筑年代
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_houseAge"),	saleInfo.getBuildYear(), "建筑年代："+saleInfo.getBuildYear()+"年");

			// 房源标题
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangyuanbiaoti_name"),saleInfo.getHouseTitle(), "房源标题");

			// 房源描述
			driver.switchToIframe(Init.G_objMap.get("anjuke_wangluojingjiren_salse_ifrExplain"),"获取房源描述frame");
			driver.activeElementSendkeys("发布出售，此房源为测试房源，房源信息没有经过验证，请勿联系，感谢您对的支持。谢谢！");
			driver.exitFrame();

			// 计算单价
			double priceSale =Double.parseDouble(saleInfo.getPriceTaxe())*10000 ;//售价
			double areaSale = Double.parseDouble(saleInfo.getHouseArea());//面积	
			double danjia = priceSale / areaSale;
			String price = Double.toString(Math.floor(danjia)); 
			price.indexOf(".");
			String price1 = price.substring(0,price.indexOf("."));
			saleInfo.setPrice("单价：" + price1 + "元");
			
			//--新版发房用单选区分推荐与否   有这句就是非推荐房源
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_tuijiangaifangyuan"),"不推荐该房源");
			if (needPic == true)
			{
				// 点去上传照片  进入发房第二步
				driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_nextBtn"),"去上传照片");
				// 上传房型图
				PublicProcess.uploadPic(driver, "sale");
				// 点击发布房源
				driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_step2_submitup"),"发房第二步-发布房源");
			}
			else{
				driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_submitBtn"),"立刻发布");
			}
			// 判断是否发布成功
			String releaseMess = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSuccMess"),"获取发布后提示信息");
			
			//如果是付费经纪人，才生成以下房源发布套数的report
			if (isPayed != 0)
			{
				//houseListNumber.get(1) 拿到的是剩余发布房源数：“xx 套”
				String houseList = houseListNumber.get(1);
				//把字符串处理一下，只剩数字
				houseList=houseList.replaceAll(" 套", "");
				if (releaseMess.equals("发布成功！") && Integer.parseInt(houseList)>= 10) {
					Report.writeHTMLLog("发布成功", "出售房源发布成功"+"本期已发布的房源:"+houseListNumber.get(0)+"剩余发布房源数:"+houseListNumber.get(1), Report.PASS, "");
				} else if(releaseMess.equals("发布成功！") && Integer.parseInt(houseList) < 10){
					Report.writeHTMLLog("发布成功", "出售房源发布成功"+"本期已发布的房源:"+houseListNumber.get(0)+"剩余发布房源数:"+houseListNumber.get(1), Report.WARNING, "");
				}else{
					String ps = driver.printScreen();
					Report.writeHTMLLog("发布失败", "出售房源发布失败" + "本期已发布的房源:"+houseListNumber.get(0)+"剩余发布房源数:"+houseListNumber.get(1), Report.FAIL, ps);
				}
			}
			else{
				Report.writeHTMLLog("发布结束", "当前经纪人账号没付费，无法得知本期剩余数量", Report.DONE, "");
			}
			
			// 跳转到房源单页
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSucc_pageLink"),"点击房源链接");
			driver.check("className^propInfoTitle");
			// 房源详细页，房源标题检查
			String propInfoTitle = driver.getText("className^propInfoTitle", "获取房源标题");
			/*String propInfoTitle = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_title"),"获取房源标题");*/
			if (propInfoTitle.equals(saleInfo.getHouseTitle())) {
				Report.writeHTMLLog("房源详细页", "房源详细页标题：" + propInfoTitle,Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "房源详细页标题错误：" + propInfoTitle+ "**正确的应该为：" + saleInfo.getHouseTitle(), Report.FAIL,driver.printScreen());
			}
			// 税费自理价格检查
			String taxPrice = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_taxPrice"),"获取税费自理价");
			if (taxPrice.equals(saleInfo.getPriceTaxe())) {
				Report.writeHTMLLog("房源详细页", "税费自理价：" + taxPrice + "万",Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "税费自理价错误：" + taxPrice + "万"+ "**应该为：" + saleInfo.getPriceTaxe() + "万",Report.FAIL, driver.printScreen());
			}
			// 房屋单价检查
			String unitPrice = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_detail_housetype"),"获取房屋单价");
			if (unitPrice.equals(saleInfo.getPrice() + "/平米")) {
				Report.writeHTMLLog("房源详细页", "验证房屋单价-->" + unitPrice,Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "验证房屋单价错误-->" + unitPrice+ "**应该为：" + saleInfo.getPrice() + "/平米", Report.FAIL,driver.printScreen());
			}
			
			// 检查房型
			String houseType = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_housetype"),"获取房型");
			String saleInfo_houseT = "房型：" + saleInfo.getHouseType_S()+"室"+saleInfo.getHouseType_T()+"厅"+ saleInfo.getHouseType_W()+"卫";
			if (houseType.equals(saleInfo_houseT)) {
				Report.writeHTMLLog("房源详细页", "房型详细信息：" + houseType,Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "房型详细信息错误：" + houseType + "正确为："+ saleInfo_houseT, Report.FAIL, driver.printScreen());
			}
			// 产证面积
			String houseArea = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_area"), "产证面积");
			if (houseArea.equals("面积：" + saleInfo.getHouseArea()+"平米")) {
				Report.writeHTMLLog("房源详细页", "产证面积：" + houseArea, Report.PASS,"");
			} else {
				Report.writeHTMLLog("房源详细页", "产证面积错误：" + houseArea + "正确为："+ "产证面积：" + saleInfo.getHouseArea(), Report.FAIL,driver.printScreen());
			}
			// 朝向
			String houseDire = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_dire"), "房子朝向");
			if (houseDire.equals("朝向："+saleInfo.getOrientations())) {
				Report.writeHTMLLog("房源详细页", "朝向：" + houseDire, Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "朝向错误：" + houseDire + "正确为："+ saleInfo.getOrientations(), Report.FAIL,driver.printScreen());
			}
			// 建筑年代
			String buildYear = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_year"), "建筑年代");
			if (buildYear.equals("建造年代："+saleInfo.getBuildYear() + "年")) {
				Report.writeHTMLLog("房源详细页", "建筑年代：" + buildYear, Report.PASS,"");
			} else {
				Report.writeHTMLLog("房源详细页", "建筑年代错误：" + buildYear + "正确为："+ saleInfo.getBuildYear() + "年", Report.FAIL,driver.printScreen());
			}
			// 楼层
			String floor = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_floor"), "楼层");
			String floorInfo = "楼层：" + saleInfo.getFloorCur() + "/"+ saleInfo.getFloorTotal();
			if (floor.equals(floorInfo)) {
				Report.writeHTMLLog("房源详细页", "楼层情况：" + floor, Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "楼层情况：" + floor + "正确为："+ floorInfo, Report.FAIL, driver.printScreen());
			}
			// 装修
			String fitment = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_fitment"), "装修");
			if (fitment.equals("装修："+saleInfo.getFitmentInfo())) {
				Report.writeHTMLLog("房源详细页", "装修：" + fitment, Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "装修：" + fitment + "正确为："+ saleInfo.getFitmentInfo(), Report.FAIL,driver.printScreen());
			}
			// 房源描述
			String description = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detaile_desc"), "房源描述");
			if (description.equals(saleInfo.getHouseDescribe())) {
				Report.writeHTMLLog("房源详细页", "房源描述：" + description,Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "房源描述：" + description + "正确为："+ saleInfo.getHouseDescribe(), Report.FAIL,driver.printScreen());
			}
		}
	}
		
	/**
	 * 获取 已发布房源数，剩余发布房源数 
	 * 出售方法的配套方法
	 */
	private static ArrayList<String> houseNumber(Browser driver){
		ArrayList<String> houseln = new ArrayList<String>();
		driver.get("http://my.anjuke.com/user/broker/checked/");
		houseln.clear();
		//如果拿不到经纪人首页那个发布房源数的模块，则说明这个经纪人不是付费的经纪人
		isPayed = driver.getElementCount("//dl[5]/dd/a");
		if (isPayed !=0 )
		{
			houseln.add(driver.getText("//dl[5]/dd/a", "获取已发布房源数"));
			houseln.add(driver.getText("//dl[6]/dd/a", "获取剩余发布房源数"));
		}
		return houseln;
	}
	
	//出售房源编辑的方法==============================================================================================
	public static void editSale(Browser driver,AnjukeSaleInfo updateInfo,boolean needPic)
	{
		driver.get("http://my.anjuke.com/user/brokerpropmanage/W0QQactZsale#proptop");
		driver.click("id^edit_0", "编辑第一条数据");
		//driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_firstEdit"), "编辑第一条数据");
		String communityName = driver.getAttribute(Init.G_objMap.get("anjuke_wangluojingjiren_sale_xiaoqu_yinyu"), "value");
		updateInfo.setCommunityName(communityName);
		//设置房屋类型
		driver.select(Init.G_objMap.get("anjuke_wangluojingjiren_sale_housetype"),updateInfo.getHouseType());
		//设置楼层
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_louceng"),updateInfo.getFloorCur(), "当前第"+updateInfo.getFloorCur()+"层");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_loucengCnt"), updateInfo.getFloorTotal(), "共"+updateInfo.getFloorTotal()+"层");
		//设置房屋面积
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chanzhengArea"), updateInfo.getHouseArea(),"产证面积");
		//设置房屋类型
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_room"), updateInfo.getHouseType_S(),updateInfo.getHouseType_S()+"房");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_hall"), updateInfo.getHouseType_T(),updateInfo.getHouseType_T()+"厅");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_toilet"), updateInfo.getHouseType_W(),updateInfo.getHouseType_W()+"卫");
		//税费自理价
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_proPrice"),updateInfo.getPriceTaxe(), "税费自理价："+updateInfo.getPriceTaxe()+"万");
		// 计算单价
		double priceUpdate =Double.parseDouble(updateInfo.getPriceTaxe())*10000 ;//售价
		double areaUpdate = Double.parseDouble(updateInfo.getHouseArea());//面积	
		double danjia = priceUpdate / areaUpdate;
		String price = Double.toString(Math.floor(danjia)); 
		price.indexOf(".");
		String price1 = price.substring(0,price.indexOf("."));
		updateInfo.setPrice("单价：" + price1 + "元");
		//String price = new BigDecimal(danjia).setScale(0, BigDecimal.ROUND_FLOOR).toString();

		//建筑年代
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_houseAge"),	updateInfo.getBuildYear(), "建筑年代："+updateInfo.getBuildYear()+"年");
		//装修情况-------------------------------------------新版发房ie6还没改 --ie6 must die
		//XPATH 适配IE6不行  使用id定位IE6
		//driver.click("id^radFitMent_1", "装修情况：普通装修");
		//driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fitMent1_ie6"), "装修情况：普通装修");
		driver.select(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fitMent"),updateInfo.getFitmentInfo());
		
		//朝向-------------------------------------------新版发房ie6还没改 --ie6 must die
		//XPATH 适配IE6不行  使用id定位IE6
		//driver.click("id^radHouseOri_4", "方向：东南");
		//driver.click(Init.G_objMap.get("anjuke_wangluojinjir_sale_chaoxiang_southeast_ie6"), "方向：东南");
		driver.select(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chaoxiang"), updateInfo.getOrientations());

		//房源标题
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangyuanbiaoti_name"),updateInfo.getHouseTitle(), "房源标题");

		//房源描述
		driver.switchToIframe(Init.G_objMap.get("anjuke_wangluojingjiren_salse_ifrExplain"),"获取房源描述frame");
		driver.activeElementSendkeys(updateInfo.getHouseDescribe());
		driver.exitFrame();
		if (needPic == true)
		{
			// 点去上传照片  进入发房第二步
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_nextBtn"),"去上传照片");
			// 上传房型图
			PublicProcess.uploadPic(driver, "sale");
			// 点击发布房源
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_step2_submitup"),"编辑完毕-发布房源");
		}
		else{
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_submitBtn"),"立刻发布");
		}
		//判断是否编辑成功
		String releaseMess = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSuccMess"), "获取发布后提示信息");		
		if(releaseMess.equals("发布成功！")){
			Report.writeHTMLLog("发布成功", "出售房源编辑成功", Report.PASS, "");			
		}else{
			String ps = driver.printScreen();
			Report.writeHTMLLog("发布失败", "出售房源编辑失败", Report.FAIL, ps);			
		}
		// 跳转到房源单页
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSucc_pageLink"),"点击房源链接");
//			------------------------------------新版发房在当前页面打开房源单页
//			driver.switchWindo(2);
		//房源详细页，房源标题检查
		driver.check("className^propInfoTitle");
		

		//driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_title"));
		String propInfoTitle = driver.getText("className^propInfoTitle", "获取房源标题");
		//String propInfoTitle = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_title"), "获取房源标题");
		if(propInfoTitle.equals(updateInfo.getHouseTitle())){
			Report.writeHTMLLog("房源详细页", "验证房源详细页标题-->"+propInfoTitle, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "验证房源详细页标题错误-->"+propInfoTitle+"**正确的应该为："+updateInfo.getHouseTitle(), Report.FAIL, driver.printScreen());			
		}	
		//税费自理价格检查
		String taxPrice = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_taxPrice"), "获取税费自理价");
		if(taxPrice.equals(updateInfo.getPriceTaxe())){
			Report.writeHTMLLog("房源详细页", "验证税费自理价-->"+taxPrice+"万", Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "验证税费自理价错误-->"+taxPrice+"万**应该为："+updateInfo.getPriceTaxe()+"万", Report.FAIL, driver.printScreen());		
		}
		//房屋单价检查
		String unitPrice = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_price"), "获取房屋单价");
		if(unitPrice.equals(updateInfo.getPrice()+"/平米")){
			Report.writeHTMLLog("房源详细页", "验证房屋单价-->"+unitPrice ,Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "验证房屋单价错误-->"+unitPrice+"**应该为："+updateInfo.getPrice()+"/平米", Report.FAIL, driver.printScreen());		
		}
		//检查房型
		String houseType = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_housetype"), "获取房型");
		String updateInfo_houseT = "房型："+updateInfo.getHouseType_S()+"室"+updateInfo.getHouseType_T()+"厅"+updateInfo.getHouseType_W()+"卫";
		if(houseType.equals(updateInfo_houseT)){
			Report.writeHTMLLog("房源详细页", "验证房型详细信息-->"+houseType, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "验证房型详细信息错误-->"+houseType+"正确为："+updateInfo_houseT, Report.FAIL, driver.printScreen());		
		}
		//产证面积
		String houseArea = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_area"), "产证面积");
		if(houseArea.equals("面积："+updateInfo.getHouseArea()+"平米")){
			Report.writeHTMLLog("房源详细页", "验证产证面积-->"+houseArea, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "验证产证面积错误-->"+houseArea+"正确为："+"产证面积："+updateInfo.getHouseArea(), Report.FAIL, driver.printScreen());		
		}
		//小区名称
		String commuName = driver.getAttribute("//a[@id='text_for_school_1']", "title");
		if(commuName.equals(updateInfo.getCommunityName())){
			Report.writeHTMLLog("房源详细页", "验证小区名称-->"+commuName, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "验证小区名称错误-->"+commuName+"正确为："+updateInfo.getCommunityName(), Report.FAIL, driver.printScreen());		
		}
		//朝向
		String houseDire = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_dire"), "房子朝向");
		if(houseDire.equals("朝向："+updateInfo.getOrientations())){
			Report.writeHTMLLog("房源详细页", "验证朝向-->"+houseDire, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "验证朝向错误-->"+houseDire+"正确为："+updateInfo.getOrientations(), Report.FAIL, driver.printScreen());		
		}
		//建筑年代
		String buildYear = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_year"), "建筑年代");
		if(buildYear.equals("建造年代："+updateInfo.getBuildYear()+"年")){
			Report.writeHTMLLog("房源详细页", "验证建筑年代-->"+buildYear, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "验证建筑年代错误-->"+buildYear+"正确为："+updateInfo.getBuildYear()+"年", Report.FAIL, driver.printScreen());		
		}		
		//楼层
		String floor = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_floor"), "楼层");
		String floorInfo = "楼层："+updateInfo.getFloorCur()+"/"+updateInfo.getFloorTotal();
		if(floor.equals(floorInfo)){
			Report.writeHTMLLog("房源详细页", "验证楼层情况-->"+floor, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "验证楼层情况-->"+floor+"**正确为："+floorInfo, Report.FAIL, driver.printScreen());		
		}
		//装修
		String fitment = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_fitment"), "装修");
		if(fitment.equals("装修："+updateInfo.getFitmentInfo())){
			Report.writeHTMLLog("房源详细页", "验证装修-->"+fitment, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "验证装修-->"+fitment+"正确为："+updateInfo.getFitmentInfo(), Report.FAIL, driver.printScreen());		
		}	
		//房源描述
		String description = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detaile_desc"), "房源描述");
		if(description.equals(updateInfo.getHouseDescribe())){
			Report.writeHTMLLog("房源详细页", "验证房源描述-->"+description, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "验证房源描述-->"+description+"正确为："+updateInfo.getHouseDescribe(), Report.FAIL, driver.printScreen());		
		}
	
		
		//删除发布房源
//			driver.close();
//			driver.switchWindo(1);
		driver.get("http://my.anjuke.com/user/brokerpropmanage/W0QQactZsale#proptop");		
		if(driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_firestsele"))&&driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_list_firstTitle"), "获取房源名称").equals(updateInfo.getHouseTitle())){
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_firestsele"), "选中发布房源");		
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_hideprop"), "删除发布房源");
			
			if(driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_firestsele"))){
				String firstTitle = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_list_firstTitle"), "删除后再获取房源标题");
				System.out.println(firstTitle);
				
				System.out.println("-----------");
				
				String houseTitle = updateInfo.getHouseTitle();
				System.out.println(houseTitle);
				
				if(!firstTitle.equals(houseTitle)){
					Report.writeHTMLLog("删除房源", "删除编辑后的房源"+firstTitle+"<br>vs"+houseTitle, "DONE", "");
				}else{
					Report.writeHTMLLog("删除房源", "删除编辑后的房源,删除失败"+firstTitle+"<br>vs"+houseTitle, "FAIL", driver.printScreen());
				}
			}else{
				Report.writeHTMLLog("删除房源", "删除编辑后的房源", "DONE", "");
			}
		}else{
			Report.writeHTMLLog("删除房源", "没有可以删除的房源", "DONE", driver.printScreen());
		}
	}

}
