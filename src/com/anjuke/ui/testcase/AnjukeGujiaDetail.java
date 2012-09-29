package com.anjuke.ui.testcase;

//import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.AjkGujia;
import com.anjuke.ui.page.AnjukeGujiaInformation;
import com.anjuke.ui.publicfunction.PublicProcess;
//import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;
/**
 * 验证估价页面与普通用户中心显示的估价信息是否一致
 * @Author chuzhaoqin
 */
public class AnjukeGujiaDetail {
	private Browser driver = null;
	private AnjukeGujiaInformation GjInfo = new AnjukeGujiaInformation();

	@BeforeMethod
	public void startUp() {
		driver = FactoryBrowser.factoryBrowser();
//		driver.deleteAllCookies();
		GjInfo = gujiaInfo_init();
		
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		driver = null;
		Report.seleniumReport("shanghai.anjuke.com", "房价评估页验证");
	}
	
	private AnjukeGujiaInformation gujiaInfo_init(){
		GjInfo.setCommunityName("潍坊八村  ");//小区
		GjInfo.setHouseType_S("5");//室
		GjInfo.setHouseType_T("4");//厅
		GjInfo.setHouseType_W("4");//卫
		GjInfo.setHouseArea("120");//面积	
		GjInfo.setFloorCur("3");//第几层
		GjInfo.setFloorTotal("6");//共几层
		GjInfo.setHouseType("老公房");//房屋类型
		GjInfo.setFitmentInfo("普通装修");//装修类型
		GjInfo.setOrientations("东");//朝向
		GjInfo.setBuildYear("2009");//建造年代
		GjInfo.setGarden("45");
		GjInfo.setBasement("45");
		//String time = PublicProcess.getNowDateTime("HH:mm:ss");
		return GjInfo;
		
	  }

		//(groups = {"unstable"})
		@Test
		public void gujia() {
			PublicProcess.logIn(driver, "xinxin@123", "123456", true, 1);
			AjkGujia.GJDetail(driver, GjInfo);
			driver.close();
		}
}