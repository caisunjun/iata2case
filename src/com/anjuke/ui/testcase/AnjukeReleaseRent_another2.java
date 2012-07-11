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
 * 该用例完成安居客出租发布改版后操作，逻辑如下 1、填写出租信息，添加图片附件 2、验证发布成功 3、在出租详细页，验证房屋出租详细信息
 * 
 * @Author gabrielgao
 * @time 2012-04-11 17:00
 * @UpdateAuthor grayhu
 * @last updatetime 2012-07-10 17:00
 */
public class AnjukeReleaseRent_another2 {
	private Browser driver = null;
	private AnjukeSaleInfo rentInfo = new AnjukeSaleInfo();
	private String url = "http://my.anjuke.com/user/broker/property/rent/step1/";

	@BeforeMethod
	public void startUp() {
		driver = FactoryBrowser.factoryBrowser();
		driver.deleteAllCookies();
		rentInfo = rentInfo_init();
	}

	@AfterMethod
	public void tearDown() {
		// driver.closeAllwindow();
		driver.close();
		driver.quit();
		driver = null;
	}

	private AnjukeSaleInfo rentInfo_init() {
		rentInfo.setUserName("shyzhang");
		rentInfo.setHouseType("类型：公寓");
		rentInfo.setFloorCur("3");
		rentInfo.setFloorTotal("6");
		rentInfo.setHouseArea("120");
		rentInfo.setHouseType_S("3室");
		rentInfo.setHouseType_T("2厅");
		rentInfo.setHouseType_W("1卫");
		rentInfo.setRental("2500元/月");
		rentInfo.setPayType_pay("(付3");
		rentInfo.setPayType_case("押1)");
		rentInfo.setBuildYear("建造年代：2009年");
		rentInfo.setFitmentInfo("装修：精装修");
		rentInfo.setConfiguration("配置：电视");
		rentInfo.setOrientations("朝向：南");
		rentInfo.setRentType("(合租)");
		String time = PublicProcess.getNowDateTime("HH：MM:SS");
		rentInfo.setHouseTitle("发布出租测试房源勿联系" + time);
		rentInfo.setHouseDescribe("发布出租，此出租房源为测试房源，如果您看到该房源，请勿联系。感谢您的配合。谢谢！");
		return rentInfo;
	}

	public void operatingReleaseRent() {
		//Report.setTCNameLog("发布出租-- AnjukeReleaseRent --Hendry_huang");
		PublicProcess.logIn(driver, "ajk_sh","anjukeqa", false, 1);
		driver.get(url);
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
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_louceng"), "3", "当前第三层");
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
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangyuanbiaoti_name"), rentInfo.getHouseTitle(), "出租房源标题");

		 //房源描述
		driver.switchToIframe(Init.G_objMap.get("anjuke_wangluojingjiren_salse_ifrExplain"), "获取房源描述frame");
		driver.activeElementSendkeys(rentInfo.getHouseDescribe());
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

	public void verifyNum() {
		// 判断是否发布成功
		String releaseMess = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSuccMess"), "获取发布后提示信息");
		driver.assertEquals("发布成功！", releaseMess, "发布成功", "判断房源发布是否成功");

		// 跳转到房源详细页  
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_setp3_danye"), "点击房源单页");

		// 房源详细页，房源标题检查
		driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_title"));
		String propInfoTitle = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_title"), "获取房源标题");
		driver.assertEquals(rentInfo.getHouseTitle(), propInfoTitle, "房源详细页", "房源详细页标题是否正确");

		// 租金检查
		String rentMessage = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_taxPrice"), "租金")
				+ driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_detail_rentMon"), "租金") + driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_datail_renttype"), "押金方式");
		String rentInput = rentInfo.getRental() + rentInfo.getPayType_pay() + rentInfo.getPayType_case();
		driver.assertEquals(rentInput, rentMessage, "房源详细页", "租金检查是否正确");

		// 检查房型
		String houseType = driver.getText(Init.G_objMap.get("anjuke_wangluojjr_sale_detail_hossort_ie8"), "获取房型");
		String rentInfo_houseT = "房型：" + rentInfo.getHouseType_S() + rentInfo.getHouseType_T() + rentInfo.getHouseType_W() + rentInfo.getRentType();
		driver.assertEquals(rentInfo_houseT, houseType, "房源详细页", "房型详细信息是否正确");

		// 朝向
		String houseDire = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_dire"), "房子朝向");
		driver.assertEquals(rentInfo.getOrientations(), houseDire, "房源详细页", "朝向 ");

		// 建筑年代
		String buildYear = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_year"), "建筑年代");

		driver.assertEquals(rentInfo.getBuildYear(), buildYear, "房源详细页", "建筑年代");

		// 楼层
		String floor = driver.getText(Init.G_objMap.get("anjuke_wangluojjr_sale_detail_floor"), "楼层");
		String floorInfo = "楼层：" + rentInfo.getFloorCur() + "/" + rentInfo.getFloorTotal();
		driver.assertEquals(floorInfo, floor, "房源详细页", "楼层情况 ");

		// 装修
		String fitment = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_fitment"), "装修");
		driver.assertEquals(rentInfo.getFitmentInfo(), fitment, "房源详细页", "装修");

		// 面积
		String area = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_detail_area"), "面积");
		driver.assertEquals("面积：" + rentInfo.getHouseArea() + "平米", area, "房源详细页", "装修");

		// 房屋类型
		String type = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_detail_housetype"), "房屋类型");
		driver.assertEquals(rentInfo.getHouseType(), type, "房源详细页", "房屋类型");
		// 房源描述
		String description = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detaile_desc"), "房源描述");
		driver.assertEquals(rentInfo.getHouseDescribe(), description, "房源详细页", "房源描述");
		/*
		 * if (description.equals(rentInfo.getHouseDescribe())) {
		 * Report.writeHTMLLog("房源详细页", "房源描述：" + description, Report.PASS, "");
		 * } else { Report.writeHTMLLog("房源详细页", "房源描述：" + description + "正确为："
		 * + rentInfo.getHouseDescribe(), Report.FAIL, driver.printScreen()); }
		 */
	}

	@Test(groups = {"unstable"})
	public void releaseRent() {
		operatingReleaseRent();
		//无图片立即发布
//		operatingReleaseNotImg();
		//上传后租房发布
		operatingReleaseUploadImg();
		verifyNum();
	}
}

