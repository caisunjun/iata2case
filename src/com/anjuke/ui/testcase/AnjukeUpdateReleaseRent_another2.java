package com.anjuke.ui.testcase;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.anjuke.ui.bean.AnjukeSaleInfo;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;

/**
 * 
 * 该用例完成安居客出租发布、修改操作，逻辑如下
 * 1、输入租房信息，上传图片
 * 2、发布成功后，修改租房信息
 * 3、修改成功后，在详细页验证修改后房屋出租信息
 * 
 * @Author grayhu
 * @time 2012-07-11 17:00
 * @UpdateAuthor grayhu	
 * @last updatetime 2012-07-11 17:00
 */

public class AnjukeUpdateReleaseRent_another2 {
	private Browser driver = null;
	private AnjukeSaleInfo rentUpInfo = new AnjukeSaleInfo();
	private String addUrl = "http://my.anjuke.com/user/broker/property/rent/step1/";
		
	@BeforeMethod
	public void startUp() {
		driver = FactoryBrowser.factoryBrowser();
		driver.deleteAllCookies();
		rentUpInfo = rentUpInfo_init();
	}

	@AfterMethod
	public void tearDown() {
		// driver.closeAllwindow();
		driver.close();
		driver.quit();
		driver = null;
	}

	private AnjukeSaleInfo rentUpInfo_init() {
		rentUpInfo.setUserName("shyzhang");
		rentUpInfo.setHouseType("类型：老公房");
		rentUpInfo.setFloorCur("1");
		rentUpInfo.setFloorTotal("6");
		rentUpInfo.setHouseArea("121");
		rentUpInfo.setHouseType_S("1室");
		rentUpInfo.setHouseType_T("1厅");
		rentUpInfo.setHouseType_W("1卫");
		rentUpInfo.setRental("2501元/月");
		rentUpInfo.setPayType_pay("(付1");
		rentUpInfo.setPayType_case("押1)");
		rentUpInfo.setBuildYear("建造年代：2000年");
		rentUpInfo.setFitmentInfo("装修：毛坯");
		rentUpInfo.setConfiguration("配置：电视 ");
		rentUpInfo.setOrientations("朝向：东南");
		rentUpInfo.setRentType("(合租)");
		String time = PublicProcess.getNowDateTime("HH：MM:SS");
		rentUpInfo.setHouseTitle("发布出租测试房源勿联系" + time);
		rentUpInfo.setHouseDescribe("发布出租，此出租房源为测试房源，如果您看到该房源，请勿联系。感谢您的配合。谢谢！");
		return rentUpInfo;
	}
	
	public void operatingReleaseRent() {
		//Report.setTCNameLog("发布出租-- AnjukeReleaseRent --Hendry_huang");
		PublicProcess.logIn(driver, "ajk_sh","anjukeqa", true, 1);
		driver.get(addUrl);
		driver.assertFalse(driver.check(Init.G_objMap.get("ajk_releaseSale_div_full")), "本月发布房源是否到上限", "本期发布房源未到上限！", "抱歉，您本期发布的新房源已达上限，无法再发布了。");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_xiaoqu_yinyu"), "罗马花园", "小区名称");
		driver.click(".//*[@id='targetid']/li[2]", "选择小区", 60); 

		// 设置房屋类型
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_radlsDolmus0_for_ie6"), "出租方式：合租");
		
		// 租金
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_proPrice"), "2500", "租金：2500元/月");
		
		// 付款方式
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_rent_payNum"), "3", "付3");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_rent_depositNum"), "1", "押1");

		// 设置房屋面积
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chanzhengArea"), "120", "产证面积");
		
		// 设置房屋类型
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_room"), "3", "3室");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_hall"), "2", "2厅");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_toilet"), "1", "1卫");

		// 设置楼层
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_louceng"), "3", "当前第3层");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_loucengCnt"), "6", "共6层");

		//房屋类型
		driver.select(Init.G_objMap.get("anjuke_wangluojingjiren_sale_leixing_house_ie6"), "公寓");
		
		//装修情况
		driver.select(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fitMent2_ie6"), "精装修");
		
		//朝向
		driver.select(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chaoxiang_south_ie6"), "南");

		// 建筑年代
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_houseAge"), "2009", "建筑年代：2009年");

		// 配置情况
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_tv"), "配置：电视");

		// 房源标题
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangyuanbiaoti_name"), rentUpInfo.getHouseTitle(), "出租房源标题");

		 //房源描述
		driver.switchToIframe(Init.G_objMap.get("anjuke_wangluojingjiren_salse_ifrExplain"), "获取房源描述frame");
		driver.activeElementSendkeys(rentUpInfo.getHouseDescribe());
		driver.exitFrame();
		driver.click("id^checkbox-r", "发布类型：不推荐该房源");
	}
	
	public void operatingReleaseNotImg(){
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_lijifabu_SaveEdit"), "租房立即发布");	
	}
	
	public void operatingReleaseUploadImg() {
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_UploadImg_To2"), "去上传照片");
		PublicProcess.uploadPic(driver, "rent");
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_step2_submitup"), "发布出租房源");
	}
	
	public void operatingUpdateRent() {
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_setp3_manage"), "点击房源管理");
		driver.click("id^edit_0", "编辑第一套房源");
		
		//检查小区名
		driver.assertEquals("罗马花园", driver.getAttribute("id^txtCommunity", "value"), "获取小区名", "修改时小区不可编辑");

		// 设置房屋类型
//		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_radlsDolmus0_for_ie6"), "出租方式：合租");
		
		// 租金
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_proPrice"), "2501", "租金：2501元/月");
		
		// 付款方式
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_rent_payNum"), "1", "付1");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_rent_depositNum"), "1", "押1");

		// 设置房屋面积
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chanzhengArea"), "121", "产证面积");
		
		// 设置房屋类型
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_room"), "1", "1室");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_hall"), "1", "1厅");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_toilet"), "1", "1卫");

		// 设置楼层
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_louceng"), "1", "当前第1层");
//		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_loucengCnt"), "6", "共6层");

		//房屋类型
		driver.select(Init.G_objMap.get("anjuke_wangluojingjiren_sale_leixing_house_ie6"), "老公房");
		
		//装修情况
		driver.select(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fitMent2_ie6"), "毛坯");
		
		//朝向
		driver.select(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chaoxiang_south_ie6"), "东南");

		// 建筑年代
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_houseAge"), "2000", "建筑年代：2000年");

		// 配置情况
		driver.click("id^chkHaveBroad", "配置：宽带");

		// 房源标题
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangyuanbiaoti_name"), rentUpInfo.getHouseTitle(), "出租房源标题");

		 //房源描述
		driver.switchToIframe(Init.G_objMap.get("anjuke_wangluojingjiren_salse_ifrExplain"), "获取房源描述frame");
		driver.activeElementSendkeys(rentUpInfo.getHouseDescribe());
		driver.exitFrame();
		
	}
	
	public void verifyNum() {
		// 判断是否发布成功
		String releaseMess = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSuccMess"), "获取发布后提示信息");
		driver.assertEquals("发布成功！", releaseMess, "发布成功", "判断房源发布是否成功");

		// 跳转到房源详细页  
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_setp3_danye"), "点击房源单页");

		// 房源详细页，房源标题检查
		driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_title"));
		String propInfoTitle = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_title"), "获取房源标题");
		driver.assertEquals(rentUpInfo.getHouseTitle(), propInfoTitle, "房源详细页", "房源详细页标题是否正确");

		// 租金检查
		String rentMessage = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_taxPrice"), "租金")
				+ driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_detail_rentMon"), "租金") + driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_datail_renttype"), "押金方式");
		String rentInput = rentUpInfo.getRental() + rentUpInfo.getPayType_pay() + rentUpInfo.getPayType_case();
		driver.assertEquals(rentInput, rentMessage, "房源详细页", "租金检查是否正确");

		// 检查房型
		String houseType = driver.getText(Init.G_objMap.get("anjuke_wangluojjr_sale_detail_hossort_ie8"), "获取房型");
		String rentUpInfo_houseT = "房型：" + rentUpInfo.getHouseType_S() + rentUpInfo.getHouseType_T() + rentUpInfo.getHouseType_W() + rentUpInfo.getRentType();
		driver.assertEquals(rentUpInfo_houseT, houseType, "房源详细页", "房型详细信息是否正确");

		// 朝向
		String houseDire = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_dire"), "房子朝向");
		driver.assertEquals(rentUpInfo.getOrientations(), houseDire, "房源详细页", "朝向 ");

		// 建筑年代
		String buildYear = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_year"), "建筑年代");

		driver.assertEquals(rentUpInfo.getBuildYear(), buildYear, "房源详细页", "建筑年代");

		// 楼层
		String floor = driver.getText(Init.G_objMap.get("anjuke_wangluojjr_sale_detail_floor"), "楼层");
		String floorInfo = "楼层：" + rentUpInfo.getFloorCur() + "/" + rentUpInfo.getFloorTotal();
		driver.assertEquals(floorInfo, floor, "房源详细页", "楼层情况 ");

		// 装修
		String fitment = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_fitment"), "装修");
		driver.assertEquals(rentUpInfo.getFitmentInfo(), fitment, "房源详细页", "装修");

		// 面积
		String area = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_detail_area"), "面积");
		driver.assertEquals("面积：" + rentUpInfo.getHouseArea() + "平米", area, "房源详细页", "装修");

		// 房屋类型
		String type = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_detail_housetype"), "房屋类型");
		driver.assertEquals(rentUpInfo.getHouseType(), type, "房源详细页", "房屋类型");
		// 房源描述
		String description = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detaile_desc"), "房源描述");
		driver.assertEquals(rentUpInfo.getHouseDescribe(), description, "房源详细页", "房源描述");
	}
	
	

	@Test(timeOut = 300000)
	public void releaseRent() {
		//发布房源
		operatingReleaseRent();
		operatingReleaseUploadImg();
		
		//修改房源
		operatingUpdateRent();
		operatingReleaseUploadImg();
		
		//检验数据
		verifyNum();
	}
}
