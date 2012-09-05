package com.anjuke.ui.testcase;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.anjuke.ui.bean.AnjukeSaleInfo;
import com.anjuke.ui.publicfunction.BrokerRentOperating;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;

/**
 * 
 * 该用例完成安居客出租发布、修改操作，逻辑如下
 * 1、编辑房源管理列表第一条房源（可选第几条，默认第一条）
 * 2、修改租房信息，上次图片（可选，默认上传）
 * 3、修改成功后，在详细页验证修改后房屋出租信息
 * 
 * @Author grayhu
 * @time 2012-07-11 17:00
 * @UpdateAuthor grayhu	
 * @last updatetime 2012-07-16 17:00
 */

public class AnjukeBrokerRentEdit {
	private Browser driver = null;
	private AnjukeSaleInfo rentUpInfo = new AnjukeSaleInfo();
		
	@BeforeMethod
	public void startUp() {
		driver = FactoryBrowser.factoryBrowser();
		driver.deleteAllCookies();
	}

	@AfterMethod
	public void tearDown() {
		// driver.closeAllwindow();
		driver.close();
		driver.quit();
		driver = null;
	}

	private AnjukeSaleInfo rentUpInfo_init() {
		rentUpInfo.setHouseType("老公房");
		rentUpInfo.setFloorCur("1");
		rentUpInfo.setFloorTotal("6");
		rentUpInfo.setHouseArea("121");
		rentUpInfo.setHouseType_S("1");
		rentUpInfo.setHouseType_T("1");
		rentUpInfo.setHouseType_W("1");
		rentUpInfo.setRental("2501");//租金
		rentUpInfo.setPayType_pay("1");//付
		rentUpInfo.setPayType_case("1");//押
		rentUpInfo.setBuildYear("2000");//建造年代
		rentUpInfo.setFitmentInfo("毛坯");
		rentUpInfo.setConfiguration("电视 ");
		rentUpInfo.setOrientations("东南");
		rentUpInfo.setRentType("整租");
		rentUpInfo.setHouseImage("无图片");
		String time = PublicProcess.getNowDateTime("HH：MM:SS");
		rentUpInfo.setHouseTitle("发布出租测试房源勿联系" + time);
		rentUpInfo.setHouseDescribe("发布出租，此出租房源为测试房源，如果您看到该房源，请勿联系。感谢您的配合。谢谢！");
		return rentUpInfo;
	}
	
	@Test(timeOut = 500000)
	public void rentEdit() {
		rentUpInfo = rentUpInfo_init();
		PublicProcess.logIn(driver, "ajk_sh","anjukeqa", false, 1);
		BrokerRentOperating.editRent(driver, rentUpInfo, 0);
	}
}
