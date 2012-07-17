package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.bean.AnjukeSaleInfo;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjuke.ui.publicfunction.BrokerRentOperating;

/**
 * 该用例完成安居客出租发布改版后操作，逻辑如下 1、填写出租信息，添加图片附件 2、验证发布成功 3、在出租详细页，验证房屋出租详细信息
 * 
 * @Author gabrielgao
 * @time 2012-04-11 17:00
 * @UpdateAuthor grayhu
 * @last updatetime 2012-07-16 17:00
 */
public class AnjukeBrokerRentRelease {
	private Browser driver = null;
	private AnjukeSaleInfo rentInfo = new AnjukeSaleInfo();
	
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

	private AnjukeSaleInfo rentInfo_init() {
		rentInfo.setUserName("shyzhang");
		rentInfo.setHouseType("公寓");
		rentInfo.setFloorCur("3");
		rentInfo.setFloorTotal("6");
		rentInfo.setHouseArea("120");
		rentInfo.setHouseType_S("3");
		rentInfo.setHouseType_T("2");
		rentInfo.setHouseType_W("1");
		rentInfo.setRental("2500");//租金
		rentInfo.setPayType_pay("3");//付
		rentInfo.setPayType_case("1");//押
		rentInfo.setBuildYear("2009");//建造年代
		rentInfo.setFitmentInfo("精装修");
		rentInfo.setConfiguration("电视");
		rentInfo.setOrientations("南");
		rentInfo.setRentType("合租");
		rentInfo.setRecommendation("不推荐");
		rentInfo.setHouseImage("无图片");
		String time = PublicProcess.getNowDateTime("HH：MM:SS");
		rentInfo.setHouseTitle("发布出租测试房源勿联系" + time);
		rentInfo.setHouseDescribe("发布出租，此出租房源为测试房源，如果您看到该房源，请勿联系。感谢您的配合。谢谢！");
		return rentInfo;
	}
	
	@Test (timeOut = 300000)
	public void rentRelease(){
		rentInfo = rentInfo_init();
		PublicProcess.logIn(driver, "ajk_sh","anjukeqa", false, 1);
		BrokerRentOperating.releaseRent(driver, rentInfo);
	}
}

