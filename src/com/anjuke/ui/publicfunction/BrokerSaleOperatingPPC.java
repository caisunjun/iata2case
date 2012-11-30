package com.anjuke.ui.publicfunction;

import com.anjuke.ui.bean.AnjukeSaleInfo;
import com.anjuke.ui.page.Ajk_PropView;
import com.anjuke.ui.page.Broker_Checked;
import com.anjuke.ui.page.Broker_PPC_PropmanageSale;
import com.anjuke.ui.page.Broker_PropertynewSaleStep;
import com.anjuke.ui.page.Public_HeaderFooter;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;

/**二手房发布方法：
 * 1.点击header上的“进入我的网络经纪人”
 * 2.点击奶牛首页的房源库
 * 3.点击房源发布
 * 
 * 二手房编辑方法：
 * 1.点击header上的“进入我的网络经纪人”
 * 2.点击奶牛首页的房源库
 * 3.检查第一套房源是否存在
 * 4.不存在则调用二手房发布
 * 5.编辑第一套房源
 * @author ccyang
 */
public class BrokerSaleOperatingPPC {
	//出售房源发布的方法==============================================================================================
	public static void releaseSale(Browser driver,AnjukeSaleInfo saleInfo,boolean needPic)
	{
		driver.click(Public_HeaderFooter.HEADER_BROKERLINK, "进入我的网络经纪人");
		driver.click(Broker_Checked.Fangyuanku_ppc, "进入房源库");
		//获得已发布房源数和剩余发布数
		String propRest = driver.getText(Broker_PPC_PropmanageSale.PropRest, "还可发布房源数");
		
		if (propRest.equals("0")) {
			String ps = driver.printScreen();
			Report.writeHTMLLog("房源库", "已有房源达上限，剩余发布数为："+propRest,Report.FAIL, ps);
		} else {
			driver.click(Broker_PPC_PropmanageSale.SendSale, "发布出售房源");
			driver.switchWindo(2);
			// 设置小区名称
			driver.click(Broker_PropertynewSaleStep.XIAOQU, "选中小区名输入框");//为了IE6
			driver.type(Broker_PropertynewSaleStep.XIAOQU, saleInfo.getCommunityName(),"输入小区名称");
			driver.click(Broker_PropertynewSaleStep.XIALAXIAOQU,"选择小区");

			// 税费自理价
			driver.type(Broker_PropertynewSaleStep.PRICE,	saleInfo.getPriceTaxe(), "税费自理价："+saleInfo.getPriceTaxe()+"万");

			// 设置房屋面积
			driver.type(Broker_PropertynewSaleStep.AREA, saleInfo.getHouseArea(),"产证面积");
			
			// 设置房型
			driver.type(Broker_PropertynewSaleStep.ROOM, saleInfo.getHouseType_S(),saleInfo.getHouseType_S()+"房");
			driver.type(Broker_PropertynewSaleStep.HALL, saleInfo.getHouseType_T(),saleInfo.getHouseType_T()+"厅");
			driver.type(Broker_PropertynewSaleStep.TOILET, saleInfo.getHouseType_W(),saleInfo.getHouseType_W()+"卫");

			// 设置楼层
			driver.type(Broker_PropertynewSaleStep.LOUCENG,saleInfo.getFloorCur(), "当前第"+saleInfo.getFloorCur()+"层");
			driver.type(Broker_PropertynewSaleStep.ZONGLOUCENG, saleInfo.getFloorTotal(), "共"+saleInfo.getFloorTotal()+"层");

			// 设置房屋类型  --新版发房公寓是下拉里的第3个
			driver.select(Broker_PropertynewSaleStep.LEIXING,saleInfo.getHouseType());
			
			// 装修情况
			driver.select(Broker_PropertynewSaleStep.ZHUANGXIU,saleInfo.getFitmentInfo());

			// 朝向
			driver.select(Broker_PropertynewSaleStep.CHAOXIANG, saleInfo.getOrientations());
			
			// 建筑年代
			driver.type(Broker_PropertynewSaleStep.HOUSEAGE,	saleInfo.getBuildYear(), "建筑年代："+saleInfo.getBuildYear()+"年");

			// 房源标题
			driver.type(Broker_PropertynewSaleStep.HOUSETITLE,saleInfo.getHouseTitle(), "房源标题");

			// 房源描述
			driver.switchToIframe(Broker_PropertynewSaleStep.HOUSEDES,"获取房源描述frame");
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
			
			//--PPC分上传图片，定价推广，存至房源库
			driver.click(Broker_PropertynewSaleStep.FIRSTSUBMIT,"存至房源库");
			
			// 判断是否发布成功
			String releaseMess = driver.getText(Broker_PropertynewSaleStep.SUCCESSMESSAGE_PPC,"获取发布后提示信息");

			if (releaseMess.equals("保存成功！") && Integer.parseInt(propRest)>= 10) {
				Report.writeHTMLLog("发布成功", "出售房源发布成功，剩余发布房源数:"+propRest, Report.PASS, "");
			} else if(releaseMess.equals("保存成功！") && Integer.parseInt(propRest) < 10){
				Report.writeHTMLLog("发布成功", "出售房源发布成功，剩余发布房源数:"+propRest, Report.WARNING, "");
			}else{
				String ps = driver.printScreen();
				Report.writeHTMLLog("发布失败", "出售房源发布失败，剩余发布房源数:"+propRest, Report.FAIL, ps);
			}
			
			// 跳转到房源单页
			driver.click("//a[@id='Proname0']","点击列表第一套房源");
			driver.switchWindo(3);
			driver.check("className^propInfoTitle");
			// 房源详细页，房源标题检查
			String propInfoTitle = driver.getText("className^propInfoTitle", "获取房源标题");
			if (propInfoTitle.equals(saleInfo.getHouseTitle())) {
				Report.writeHTMLLog("房源详细页", "房源详细页标题：" + propInfoTitle,Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "房源详细页标题错误：" + propInfoTitle+ "**正确的应该为：" + saleInfo.getHouseTitle(), Report.FAIL,driver.printScreen());
			}
			// 税费自理价格检查
			String taxPrice = driver.getText(Ajk_PropView.PRICE,"获取税费自理价");
			if (taxPrice.equals(saleInfo.getPriceTaxe())) {
				Report.writeHTMLLog("房源详细页", "税费自理价：" + taxPrice + "万",Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "税费自理价错误：" + taxPrice + "万"+ "**应该为：" + saleInfo.getPriceTaxe() + "万",Report.FAIL, driver.printScreen());
			}
			// 房屋单价检查
			String unitPrice = driver.getText(Ajk_PropView.UNITPRICE,"获取房屋单价");
			if (unitPrice.equals(saleInfo.getPrice() + "/平米")) {
				Report.writeHTMLLog("房源详细页", "验证房屋单价-->" + unitPrice,Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "验证房屋单价错误-->" + unitPrice+ "**应该为：" + saleInfo.getPrice() + "/平米", Report.FAIL,driver.printScreen());
			}
			
			// 检查房型
			String houseType = driver.getText(Ajk_PropView.HOUSETYPE,"获取房型");
			String saleInfo_houseT = "房型：" + saleInfo.getHouseType_S()+"室"+saleInfo.getHouseType_T()+"厅"+ saleInfo.getHouseType_W()+"卫";
			if (houseType.equals(saleInfo_houseT)) {
				Report.writeHTMLLog("房源详细页", "房型详细信息：" + houseType,Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "房型详细信息错误：" + houseType + "正确为："+ saleInfo_houseT, Report.FAIL, driver.printScreen());
			}
			// 产证面积
			String houseArea = driver.getText(Ajk_PropView.AREA, "产证面积");
			if (houseArea.equals("面积：" + saleInfo.getHouseArea()+"平米")) {
				Report.writeHTMLLog("房源详细页", "产证面积：" + houseArea, Report.PASS,"");
			} else {
				Report.writeHTMLLog("房源详细页", "产证面积错误：" + houseArea + "正确为："+ "产证面积：" + saleInfo.getHouseArea(), Report.FAIL,driver.printScreen());
			}
			// 朝向
			String houseDire = driver.getText(Ajk_PropView.CHAOXIANG, "房子朝向");
			if (houseDire.equals("朝向："+saleInfo.getOrientations())) {
				Report.writeHTMLLog("房源详细页", "朝向：" + houseDire, Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "朝向错误：" + houseDire + "正确为："+ saleInfo.getOrientations(), Report.FAIL,driver.printScreen());
			}
			// 建筑年代
			String buildYear = driver.getText(Ajk_PropView.HOUSEAGE, "建筑年代");
			if (buildYear.equals("建造年代："+saleInfo.getBuildYear() + "年")) {
				Report.writeHTMLLog("房源详细页", "建筑年代：" + buildYear, Report.PASS,"");
			} else {
				Report.writeHTMLLog("房源详细页", "建筑年代错误：" + buildYear + "正确为："+ saleInfo.getBuildYear() + "年", Report.FAIL,driver.printScreen());
			}
			// 楼层
			String floor = driver.getText(Ajk_PropView.FLOOR, "楼层");
			String floorInfo = "楼层：" + saleInfo.getFloorCur() + "/"+ saleInfo.getFloorTotal();
			if (floor.equals(floorInfo)) {
				Report.writeHTMLLog("房源详细页", "楼层情况：" + floor, Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "楼层情况：" + floor + "正确为："+ floorInfo, Report.FAIL, driver.printScreen());
			}
			// 装修
			String fitment = driver.getText(Ajk_PropView.ZHUANGXIU, "装修");
			if (fitment.equals("装修："+saleInfo.getFitmentInfo())) {
				Report.writeHTMLLog("房源详细页", "装修：" + fitment, Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "装修：" + fitment + "正确为："+ saleInfo.getFitmentInfo(), Report.FAIL,driver.printScreen());
			}
			// 房源描述
			String description = driver.getText(Ajk_PropView.HOUSEDES, "房源描述");
			if (description.equals(saleInfo.getHouseDescribe())) {
				Report.writeHTMLLog("房源详细页", "房源描述：" + description,Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "房源描述：" + description + "正确为："+ saleInfo.getHouseDescribe(), Report.FAIL,driver.printScreen());
			}
			driver.close();
			driver.switchWindo(2);
		}
	}
		
	
	//出售房源编辑的方法==============================================================================================
	public static void editSale(Browser driver,AnjukeSaleInfo updateInfo,boolean needPic) throws InterruptedException
	{
		driver.click(Public_HeaderFooter.HEADER_BROKERLINK, "进入我的网络经纪人");
		driver.click(Broker_Checked.Fangyuanku_ppc, "进入房源库");
		if(!driver.check("id^Proname0")){
			releaseSale(driver,updateInfo,needPic);
		}
		
		driver.click(Broker_PPC_PropmanageSale.EditProp(1), "编辑第一套房源");
		driver.switchWindo(2);
		String communityName = driver.getAttribute(Broker_PropertynewSaleStep.XIAOQU, "value");
		updateInfo.setCommunityName(communityName);
		//设置房屋类型
		driver.select(Broker_PropertynewSaleStep.LEIXING,updateInfo.getHouseType());
		//设置楼层
		driver.type(Broker_PropertynewSaleStep.LOUCENG,updateInfo.getFloorCur(), "当前第"+updateInfo.getFloorCur()+"层");
		driver.type(Broker_PropertynewSaleStep.ZONGLOUCENG, updateInfo.getFloorTotal(), "共"+updateInfo.getFloorTotal()+"层");
		//设置房屋面积
		driver.type(Broker_PropertynewSaleStep.AREA, updateInfo.getHouseArea(),"产证面积");
		//设置房屋类型
		driver.type(Broker_PropertynewSaleStep.ROOM, updateInfo.getHouseType_S(),updateInfo.getHouseType_S()+"房");
		driver.type(Broker_PropertynewSaleStep.HALL, updateInfo.getHouseType_T(),updateInfo.getHouseType_T()+"厅");
		driver.type(Broker_PropertynewSaleStep.TOILET, updateInfo.getHouseType_W(),updateInfo.getHouseType_W()+"卫");
		//税费自理价
		driver.type(Broker_PropertynewSaleStep.PRICE,updateInfo.getPriceTaxe(), "税费自理价："+updateInfo.getPriceTaxe()+"万");
		// 计算单价
		double priceUpdate =Double.parseDouble(updateInfo.getPriceTaxe())*10000 ;//售价
		double areaUpdate = Double.parseDouble(updateInfo.getHouseArea());//面积	
		double danjia = priceUpdate / areaUpdate;
		String price = Double.toString(Math.floor(danjia)); 
		price.indexOf(".");
		String price1 = price.substring(0,price.indexOf("."));
		updateInfo.setPrice("单价：" + price1 + "元");

		//建筑年代
		driver.type(Broker_PropertynewSaleStep.HOUSEAGE,	updateInfo.getBuildYear(), "建筑年代："+updateInfo.getBuildYear()+"年");
		//装修情况
		driver.select(Broker_PropertynewSaleStep.ZHUANGXIU,updateInfo.getFitmentInfo());
		//朝向
		driver.select(Broker_PropertynewSaleStep.CHAOXIANG, updateInfo.getOrientations());

		//房源标题
		driver.type(Broker_PropertynewSaleStep.HOUSETITLE,updateInfo.getHouseTitle(), "房源标题");

		//房源描述
		driver.switchToIframe(Broker_PropertynewSaleStep.HOUSEDES,"获取房源描述frame");
		driver.activeElementSendkeys(updateInfo.getHouseDescribe());
		driver.exitFrame();
		if (needPic == true)
		{
			// 点去上传照片  进入发房第二步
			driver.click(Broker_PropertynewSaleStep.NEXTSTEP,"去上传照片");
			// 上传房型图
			PublicProcess.uploadPic(driver, "sale");
			// 点击发布房源
			driver.click(Broker_PropertynewSaleStep.SECONDSUBMIT,"编辑完毕-保存至房源库");
		}
		else{
			driver.click(Broker_PropertynewSaleStep.FIRSTSUBMIT,"存至房源库");
		}
		//判断是否编辑成功
		String releaseMess = driver.getText(Broker_PropertynewSaleStep.SUCCESSMESSAGE_PPC, "获取发布后提示信息");		
		if(releaseMess.equals("保存成功！")){
			Report.writeHTMLLog("发布成功", "出售房源编辑成功", Report.PASS, "");			
		}else{
			String ps = driver.printScreen();
			Report.writeHTMLLog("发布失败", "出售房源编辑失败", Report.FAIL, ps);			
		}
		
		//以下先注释了  无法解决hudson机器上编辑后房源单页数据无法及时更新的问题
//		//等5秒钟，让数据更新
//		Thread.sleep(5000);
//		
//		// 跳转到房源单页
//		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSucc_pageLink"),"点击房源链接");
//
//		//房源详细页，房源标题检查
//		driver.check("className^propInfoTitle");
//
//		//driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_title"));
//		String propInfoTitle = driver.getText("className^propInfoTitle", "获取房源标题");
//		//String propInfoTitle = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_title"), "获取房源标题");
//		if(propInfoTitle.equals(updateInfo.getHouseTitle())){
//			Report.writeHTMLLog("房源详细页", "验证房源详细页标题-->"+propInfoTitle, Report.PASS, "");
//		}else{
//			Report.writeHTMLLog("房源详细页", "验证房源详细页标题错误-->"+propInfoTitle+"**正确的应该为："+updateInfo.getHouseTitle(), Report.FAIL, driver.printScreen());			
//		}	
//		//税费自理价格检查
//		String taxPrice = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_taxPrice"), "获取税费自理价");
//		if(taxPrice.equals(updateInfo.getPriceTaxe())){
//			Report.writeHTMLLog("房源详细页", "验证税费自理价-->"+taxPrice+"万", Report.PASS, "");
//		}else{
//			Report.writeHTMLLog("房源详细页", "验证税费自理价错误-->"+taxPrice+"万**应该为："+updateInfo.getPriceTaxe()+"万", Report.FAIL, driver.printScreen());		
//		}
//		//房屋单价检查
//		String unitPrice = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_price"), "获取房屋单价");
//		if(unitPrice.equals(updateInfo.getPrice()+"/平米")){
//			Report.writeHTMLLog("房源详细页", "验证房屋单价-->"+unitPrice ,Report.PASS, "");
//		}else{
//			Report.writeHTMLLog("房源详细页", "验证房屋单价错误-->"+unitPrice+"**应该为："+updateInfo.getPrice()+"/平米", Report.FAIL, driver.printScreen());		
//		}
//		//检查房型
//		String houseType = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_housetype"), "获取房型");
//		String updateInfo_houseT = "房型："+updateInfo.getHouseType_S()+"室"+updateInfo.getHouseType_T()+"厅"+updateInfo.getHouseType_W()+"卫";
//		if(houseType.equals(updateInfo_houseT)){
//			Report.writeHTMLLog("房源详细页", "验证房型详细信息-->"+houseType, Report.PASS, "");
//		}else{
//			Report.writeHTMLLog("房源详细页", "验证房型详细信息错误-->"+houseType+"正确为："+updateInfo_houseT, Report.FAIL, driver.printScreen());		
//		}
//		//产证面积
//		String houseArea = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_area"), "产证面积");
//		if(houseArea.equals("面积："+updateInfo.getHouseArea()+"平米")){
//			Report.writeHTMLLog("房源详细页", "验证产证面积-->"+houseArea, Report.PASS, "");
//		}else{
//			Report.writeHTMLLog("房源详细页", "验证产证面积错误-->"+houseArea+"正确为："+"产证面积："+updateInfo.getHouseArea(), Report.FAIL, driver.printScreen());		
//		}
//		//小区名称
//		String commuName = driver.getAttribute("//a[@id='text_for_school_1']", "title");
//		if(commuName.equals(updateInfo.getCommunityName())){
//			Report.writeHTMLLog("房源详细页", "验证小区名称-->"+commuName, Report.PASS, "");
//		}else{
//			Report.writeHTMLLog("房源详细页", "验证小区名称错误-->"+commuName+"正确为："+updateInfo.getCommunityName(), Report.FAIL, driver.printScreen());		
//		}
//		//朝向
//		String houseDire = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_dire"), "房子朝向");
//		if(houseDire.equals("朝向："+updateInfo.getOrientations())){
//			Report.writeHTMLLog("房源详细页", "验证朝向-->"+houseDire, Report.PASS, "");
//		}else{
//			Report.writeHTMLLog("房源详细页", "验证朝向错误-->"+houseDire+"正确为："+updateInfo.getOrientations(), Report.FAIL, driver.printScreen());		
//		}
//		//建筑年代
//		String buildYear = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_year"), "建筑年代");
//		if(buildYear.equals("建造年代："+updateInfo.getBuildYear()+"年")){
//			Report.writeHTMLLog("房源详细页", "验证建筑年代-->"+buildYear, Report.PASS, "");
//		}else{
//			Report.writeHTMLLog("房源详细页", "验证建筑年代错误-->"+buildYear+"正确为："+updateInfo.getBuildYear()+"年", Report.FAIL, driver.printScreen());		
//		}
//		//楼层
//		String floor = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_floor"), "楼层");
//		String floorInfo = "楼层："+updateInfo.getFloorCur()+"/"+updateInfo.getFloorTotal();
//		if(floor.equals(floorInfo)){
//			Report.writeHTMLLog("房源详细页", "验证楼层情况-->"+floor, Report.PASS, "");
//		}else{
//			Report.writeHTMLLog("房源详细页", "验证楼层情况-->"+floor+"**正确为："+floorInfo, Report.FAIL, driver.printScreen());		
//		}
//		//装修
//		String fitment = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_fitment"), "装修");
//		if(fitment.equals("装修："+updateInfo.getFitmentInfo())){
//			Report.writeHTMLLog("房源详细页", "验证装修-->"+fitment, Report.PASS, "");
//		}else{
//			Report.writeHTMLLog("房源详细页", "验证装修-->"+fitment+"正确为："+updateInfo.getFitmentInfo(), Report.FAIL, driver.printScreen());		
//		}
//		//房源描述
//		String description = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detaile_desc"), "房源描述");
//		if(description.equals(updateInfo.getHouseDescribe())){
//			Report.writeHTMLLog("房源详细页", "验证房源描述-->"+description, Report.PASS, "");
//		}else{
//			Report.writeHTMLLog("房源详细页", "验证房源描述-->"+description+"正确为："+updateInfo.getHouseDescribe(), Report.FAIL, driver.printScreen());		
//		}
	
		//删除发布房源```删除后再做验证？删的对不对？在这个case里验证？需要考虑回收站翻页
//			driver.close();
//			driver.switchWindo(1);
//		driver.get("http://my.anjuke.com/user/brokerpropmanage/W0QQactZsale#proptop");		
//		if(driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_firestsele"))&&driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_list_firstTitle"), "获取房源名称").equals(updateInfo.getHouseTitle())){
//			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_firestsele"), "选中发布房源");		
//			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_hideprop"), "删除发布房源");
//		}else{
//			Report.writeHTMLLog("删除房源", "没有可以删除的房源", "DONE", driver.printScreen());
//		}
	}

}
