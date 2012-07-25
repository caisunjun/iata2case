package com.anjuke.ui.testcase;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.bean.AnjukeSaleInfo;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;
/**
 * 该用例完成安居客出售更新操作，逻辑如下
 * 1、编辑出售信息，添加图片附件
 * 2、更新成功后，验证出售基本信息
 * 3、在出售详细页，验证房屋出售详细信息
 * 
 * @Author gabrielgao
 * @time 2012-04-11 17:00
 * @UpdateAuthor ccyang
 * @last updatetime 2012-07-11 10:00
 */
public class AnjukeBrokerSaleEdit {
	private Browser driver = null;
	private AnjukeSaleInfo updateInfo = new AnjukeSaleInfo();
	private ArrayList<String> houseListNumber = null;
	@BeforeMethod
	public void startUp(){
	driver = FactoryBrowser.factoryBrowser();	
	}
	@AfterMethod
	public void tearDown(){
		//driver.closeAllwindow();
		driver.quit();
		driver=null;		
	}
	//(timeOut = 200000)
	@Test(groups = {"unstable"})
	public void releaseSale(){
		driver.deleteAllCookies();
		//Report.setTCNameLog("管理出售编辑-- AnjukeUpdateReleaseSale --Hendry_huang");
		driver.deleteAllCookies();
		updateInfo.setUserName(PublicProcess.logIn(driver, "ajk_sh", "anjukeqa", false,1));
		driver.get("http://my.anjuke.com/user/brokerpropmanage/W0QQactZsale#proptop");
		//driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_firstEdit"));
		
		if(driver.check("id^edit_0")){
			updateRelease();
		}else{
			addHouseRent();
		}
	}
	
	private void addHouseRent(){
		String urladd = "http://my.anjuke.com/user/broker/property/sale/step1/W0QQoooflagZ1";
		driver.get(urladd);
		if (driver.check(Init.G_objMap.get("ajk_releaseSale_div_full"))) {
			String ps = driver.printScreen();
			houseListNumber = houseNumber();
			Report.writeHTMLLog("发布出租", "抱歉，您本期发布的新房源已达上限，无法再发布了。"+ "本期已发布的房源:"+houseListNumber.get(0)+"剩余发布房源数:"+houseListNumber.get(1),
					Report.FAIL, ps);
		} else {
			// 设置小区名称
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_xiaoqu_yinyu"), "潍坊八村",
					"小区名称");
			driver.switchToIframe(Init.G_objMap
					.get("anjuke_wangluojingjiren_salse_ifrCommunityList"),
					"选择小区");
			driver.click(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_xiaoqu_nameClick"),
					"选择小区");
			driver.exitFrame();
			//saleInfo.setCommunityName("潍坊八村");
			// 设置房屋类型
			driver.click(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_gongyu_click"), "选择公寓");
			//saleInfo.setHouseType("公寓");
			// 设置楼层
			driver.type(
					Init.G_objMap.get("anjuke_wangluojingjiren_sale_louceng"),
					"3", "当前第三层");
			//saleInfo.setFloorCur("3");
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_loucengCnt"), "6", "共6层");
			//saleInfo.setFloorTotal("6");
			// 设置房屋面积
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_chanzhengArea"), "120",
					"产证面积");
			//saleInfo.setHouseArea("120.00平米");
			// 设置房屋类型
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_fangxing_room"), "3",
					"3房");
			//saleInfo.setHouseType_S("3室");
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_fangxing_hall"), "2",
					"2厅");
			//saleInfo.setHouseType_T("2厅");
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_fangxing_toilet"), "1",
					"1卫");
			//saleInfo.setHouseType_W("1卫");
			// 税费自理价
			driver.type(
					Init.G_objMap.get("anjuke_wangluojingjiren_sale_proPrice"),
					"200", "税费自理价：200万");
			//saleInfo.setPriceTaxe("200");
			// 计算单价
			double danjia = (double) 2000000.00 / (double) 120.00;
			/*String price = new BigDecimal(danjia).setScale(0,
					BigDecimal.ROUND_HALF_DOWN).toString();*/
			String price = Double.toString(Math.floor(danjia)); 
			price.indexOf(".");
			String price1 = price.substring(0,price.indexOf("."));
			//saleInfo.setPrice("单价：" + price1 + "元");
			// 房东到手价
			/*driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_landladyPrice"), "180",
					"房东到手价：180万");
			saleInfo.setPriceGet("房东到手价：180万");*/
			// 建筑年代
			driver.type(
					Init.G_objMap.get("anjuke_wangluojingjiren_sale_houseAge"),
					"2009", "建筑年代：2009年");
			//saleInfo.setBuildYear("建造年代：2009");
			// 装修情况
			// XPATH 适配IE6不行 使用id定位IE6
			// driver.click("id^radFitMent_2", "装修情况：精装修");
			driver.click(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_fitMent2_ie6"),
					"装修情况：精装修");
			// driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fitMent2"),
			// "装修情况：精装修");
			//saleInfo.setFitmentInfo("装修：精装修");
			// 朝向
			// XPATH 适配IE6不行 使用id定位IE6
			// driver.click("id^radHouseOri_1", "方向：朝南");
			driver.click(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_chaoxiang_south_ie6"),
					"方向：朝南");
			// driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chaoxiang_south"),
			// "方向：朝南");
			//saleInfo.setOrientations("朝向：南");
			// 设置租约
			// XPATH 适配IE6不行 使用id定位IE6
			// driver.click("id^radCurrentState_0", "租约：是");
			driver.click(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_zuyue_true_ie6"), "租约：是");
			// driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_zuyue_true"),
			// "租约：是");
			// 备注信息
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_beizhu_info"),
					"测试房源，请勿联系，谢谢", "房源备注");
			// 房源标题
			String time = PublicProcess.getNowDateTime("HH:mm:ss");
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_fangyuanbiaoti_name"),
					"发布出售请勿联系谢谢" + time, "房源标题");
			//saleInfo.setHouseTitle("发布出售请勿联系谢谢" + time);
			// 房源描述
			driver.switchToIframe(Init.G_objMap
					.get("anjuke_wangluojingjiren_salse_ifrExplain"),
					"获取房源描述frame");
			driver.activeElementSendkeys("发布出售，此房源为测试房源，房源信息没有经过验证，请勿联系，感谢您对的支持。谢谢！");
			driver.exitFrame();
			//saleInfo.setHouseDescribe("发布出售，此房源为测试房源，房源信息没有经过验证，请勿联系，感谢您对的支持。谢谢！");
			// 发布类型
			// XPATH 适配IE6不行 使用id定位IE6
			// driver.click("id^radSaleType_2", "发布类型：只在我的店面显示");
			driver.click(
					Init.G_objMap.get("anjuke_sale_fangyuanleixing_type2_ie6"),
					"发布类型：只在我的店面显示");
			// driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangyuanleixing_type2"),
			// "发布类型：只在我的店面显示");
			// 下一步
			driver.click(
					Init.G_objMap.get("anjuke_wangluojingjiren_sale_nextBtn"),
					"下一步");
			// 上传房型图
			PublicProcess.uploadPic(driver, "sale");
			// 点击发布房源
			driver.click(
					Init.G_objMap.get("anjuke_wangluojingjiren_sale_nextBtn"),
					"发布房源");
			// 判断是否发布成功
			String releaseMess = driver.getText(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_newSuccMess"),
					"获取发布后提示信息");
			
			if (releaseMess.equals("发布成功！")) {
				Report.writeHTMLLog("发布成功", "出售房源发布成功", Report.PASS, "");
			} else{
				String ps = driver.printScreen();
				Report.writeHTMLLog("发布失败", "出售房源发布失败", Report.FAIL, ps);
			}
		}
	}
	
	private void updateRelease(){
		driver.get("http://my.anjuke.com/user/brokerpropmanage/W0QQactZsale#proptop");
		driver.click("id^edit_0", "编辑第一条数据");
		//driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_firstEdit"), "编辑第一条数据");
		String communityName = driver.getAttribute(Init.G_objMap.get("anjuke_wangluojingjiren_sale_xiaoqu_yinyu"), "value");
		updateInfo.setCommunityName(communityName);
		//设置房屋类型
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_gongyu_click"), "选择公寓");
		updateInfo.setHouseType("公寓");
		//设置楼层
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_louceng"), "5", "当前第五层");
		updateInfo.setFloorCur("5");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_loucengCnt"), "8", "共8层");
		updateInfo.setFloorTotal("8");
		//设置房屋面积
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chanzhengArea"), "115", "产证面积");
		updateInfo.setHouseArea("115.00平米");		
		//设置房屋类型
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_room"), "2", "2房");
		updateInfo.setHouseType_S("2室");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_hall"), "1", "1厅");
		updateInfo.setHouseType_T("1厅");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_toilet"), "1", "1卫");
		updateInfo.setHouseType_W("1卫");
		//税费自理价
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_proPrice"), "165", "税费自理价：165万");
		updateInfo.setPriceTaxe("165");
		//计算单价
		double danjia = (double)1650000.00/(double)115.00;
		String price = new BigDecimal(danjia).setScale(0, BigDecimal.ROUND_FLOOR).toString();
		updateInfo.setPrice("单价："+price+"元");
		//房东到手价
		//driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_landladyPrice"), "125", "房东到手价：125万");
		//updateInfo.setPriceGet("房东到手价：125万");
		//建筑年代
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_houseAge"), "2007", "建筑年代：2007年");
		updateInfo.setBuildYear("建造年代：2007");
		//装修情况-------------------------------------------新版发房ie6还没改 --ie6 must die
		//XPATH 适配IE6不行  使用id定位IE6
		//driver.click("id^radFitMent_1", "装修情况：普通装修");
		//driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fitMent1_ie6"), "装修情况：普通装修");
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fitMent3"), "装修情况：普通装修");
		updateInfo.setFitmentInfo("装修：普通装修");
		//朝向-------------------------------------------新版发房ie6还没改 --ie6 must die
		//XPATH 适配IE6不行  使用id定位IE6
		//driver.click("id^radHouseOri_4", "方向：东南");
		//driver.click(Init.G_objMap.get("anjuke_wangluojinjir_sale_chaoxiang_southeast_ie6"), "方向：东南");
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chaoxiang_southeast"), "方向：东南");
		updateInfo.setOrientations("朝向：东南");	
		//设置租约-------------------------------------------新版发房没有租约了  租约你一脸
		//XPATH 适配IE6不行  使用id定位IE6
		//driver.click("id^radCurrentState_1", "租约：否");
		//driver.click(Init.G_objMap.get("anjuke_wangluojinjir_sale_zuyue_false_ie6"), "租约：否");
		//driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_zuyue_false"), "租约：否");	
		//备注信息-------------------------------------------新版发房没有备注信息了  备注信息你一脸
		//driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_beizhu_info"), "测试房源，请勿联系，谢谢-编辑状态", "房源备注");
		//房源标题
		String time = PublicProcess.getNowDateTime("HH:mm:ss");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangyuanbiaoti_name"), "发布出售编辑请勿联系"+time, "房源标题");
		updateInfo.setHouseTitle("发布出售编辑请勿联系"+time);
		//房源描述
		driver.switchToIframe(Init.G_objMap.get("anjuke_wangluojingjiren_salse_ifrExplain"), "获取房源描述frame");
		driver.activeElementSendkeys("发布出售编辑，此房源为测试房源，房源信息没有经过验证，请勿联系，感谢您的支持。谢谢！目前为编辑状态");
		driver.exitFrame();		
		updateInfo.setHouseDescribe("发布出售编辑，此房源为测试房源，房源信息没有经过验证，请勿联系，感谢您的支持。谢谢！目前为编辑状态");
		//去上传照片
		driver.click(
				Init.G_objMap.get("anjuke_wangluojingjiren_sale_nextBtn"),
				"去上传照片");
		//上传房型图
		PublicProcess.uploadPic(driver,"sale");	
		//编辑完毕 发布房源
		driver.click(
				Init.G_objMap.get("anjuke_wangluojingjiren_sale_step2_submitup"),
				"编辑完毕-发布房源");
		//判断是否编辑成功
		String releaseMess = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSuccMess"), "获取发布后提示信息");		
		if(releaseMess.equals("发布成功！")){
			Report.writeHTMLLog("发布成功", "出售房源编辑成功", Report.PASS, "");			
		}else{
			String ps = driver.printScreen();
			Report.writeHTMLLog("发布失败", "出售房源编辑失败", Report.FAIL, ps);			
		}
		//判断预览房源标题------------------------------------新版发房取消了预览
//		String houseTitle = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSucc_pageTitle"), "获取房源标题");
//		if(houseTitle.equals(updateInfo.getHouseTitle())){
//			Report.writeHTMLLog("房源标题", "发布后房源标题和输入一致", Report.PASS, "");
//		}else{
//			Report.writeHTMLLog("房源标题", "发布后房源标题和输入不一致", Report.FAIL, driver.printScreen());			
//		}
		//判断发布房源的经纪人姓名------------------------------------新版发房取消了预览
//		String tycoonName = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSucc_userName"), "获取经纪人姓名");
//		if(tycoonName.equals(updateInfo.getUserName())){
//			Report.writeHTMLLog("经纪人姓名", "登录经纪人姓名和发布房源经纪人姓名一致", Report.PASS, "");			
//		}else{
//			Report.writeHTMLLog("经纪人姓名", "登录经纪人姓名和发布房源经纪人姓名不一致", Report.FAIL, driver.printScreen());	
//		}
		//房型检查------------------------------------新版发房取消了预览
//		String infoAppend = updateInfo.getHouseType_S()+updateInfo.getHouseType_T()+"， "+updateInfo.getHouseArea()+"，"+updateInfo.getPrice()+"， 楼层： "+updateInfo.getFloorCur()+"/"+updateInfo.getFloorTotal()+" ，"+updateInfo.getBuildYear();
//		String fangyuandesc = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_sucess_desc"), "获取房源信息").trim();
//		String fangyuan = PublicProcess.splitString(fangyuandesc, "\n");
//		if(infoAppend.equals(fangyuan)){
//			Report.writeHTMLLog("房型", "房型："+fangyuan, Report.PASS, "");			
//			
//		}else{
//			Report.writeHTMLLog("房型", "房型错误："+fangyuan+"**正确的应该为："+infoAppend, Report.FAIL, driver.printScreen());			
//		}		
		//跳转到房源详细页
		/*try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		// 跳转到房源单页
		driver.click(Init.G_objMap
				.get("anjuke_wangluojingjiren_sale_newSucc_pageLink"),
				"点击房源链接");
//		------------------------------------新版发房在当前页面打开房源单页
//		driver.switchWindo(2);
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
		//房东到手价检查
		/*String priceGet = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_handPrice"), "房东到手价").trim();
		if(priceGet.equals(updateInfo.getPriceGet())){
			Report.writeHTMLLog("房源详细页", "验证房东到手价-->"+priceGet, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "验证房东到手价错误-->"+priceGet+"**正确为："+updateInfo.getPriceGet(), Report.FAIL, driver.printScreen());		
		}*/
		//检查房型
		String houseType = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_housetype"), "获取房型");
		String updateInfo_houseT = "房型："+updateInfo.getHouseType_S()+updateInfo.getHouseType_T()+updateInfo.getHouseType_W();
		if(houseType.equals(updateInfo_houseT)){
			Report.writeHTMLLog("房源详细页", "验证房型详细信息-->"+houseType, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "验证房型详细信息错误-->"+houseType+"正确为："+updateInfo_houseT, Report.FAIL, driver.printScreen());		
		}
		//产证面积
		String houseArea = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_area"), "产证面积");
		if(houseArea.equals("面积："+updateInfo.getHouseArea())){
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
		if(houseDire.equals(updateInfo.getOrientations())){
			Report.writeHTMLLog("房源详细页", "验证朝向-->"+houseDire, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "验证朝向错误-->"+houseDire+"正确为："+updateInfo.getOrientations(), Report.FAIL, driver.printScreen());		
		}
		//建筑年代
		String buildYear = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_year"), "建筑年代");
		if(buildYear.equals(updateInfo.getBuildYear()+"年")){
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
		if(fitment.equals(updateInfo.getFitmentInfo())){
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
//		driver.close();
//		driver.switchWindo(1);
		driver.get("http://my.anjuke.com/user/brokerpropmanage/W0QQactZsale#proptop");		
		if(driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_firestsele"))&&driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_list_firstTitle"), "获取房源名称").equals(updateInfo.getHouseTitle())){
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_firestsele"), "选中发布房源");		
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_hideprop"), "删除发布房源");
			
			if(driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_firestsele"))){
				System.out.println(driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_list_firstTitle"), "删除后再获取房源标题"));
				System.out.println("-----------");
				System.out.println(updateInfo.getHouseTitle());
				if(!driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_list_firstTitle"), "删除后再获取房源标题").equals(updateInfo.getHouseTitle())){
					
					Report.writeHTMLLog("删除房源", "删除编辑后的房源", "DONE", "");
				}else{
					Report.writeHTMLLog("删除房源", "删除编辑后的房源,删除失败", "FAIL", driver.printScreen());
				}
			}else{
				Report.writeHTMLLog("删除房源", "删除编辑后的房源", "DONE", "");
			}
		}else{
			Report.writeHTMLLog("删除房源", "没有可以删除的房源", "DONE", driver.printScreen());
		}
	}
	/**
	 * 获取 已发布房源数，剩余发布房源数 
	 */
	private ArrayList<String> houseNumber(){
		ArrayList<String> houseln = new ArrayList<String>();
		driver.get("http://my.anjuke.com/user/broker/checked/");
		houseln.clear();
		houseln.add(driver.getText("//dl[5]/dd/a", "获取已发布房源数"));
		houseln.add(driver.getText("//dl[6]/dd/a", "获取剩余发布房源数"));
		return houseln;
	}
}
