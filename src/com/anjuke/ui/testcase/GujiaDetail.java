package com.anjuke.ui.testcase;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.AjkGujia;
import com.anjuke.ui.page.AnjukeGujiaInformation;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;
//import com.example.tests.Ajk_AskView;
/**
 * 该用例完成安居客出售发布操作，逻辑如下
 * 1、填写出售信息，添加图片附件
 * 2、发布成功后，验证出售基本信息
 * 3、在出售详细页，验证房屋出售详细信息
 * 
 * @Author gabrielgao
 * @time 2012-04-11 17:00
 * @UpdateAuthor ccyang
 * @last updatetime 2012-07-25 13:00
 */
public class GujiaDetail {
	//private String evaTotal;
	private Browser driver = null;
	private AnjukeGujiaInformation GjInfo = new AnjukeGujiaInformation();
	//private AnjukeGujiaInformation GjInfosuccess = new AnjukeGujiaInformation();

	@BeforeMethod
	public void startUp() {
		driver = FactoryBrowser.factoryBrowser();
//		driver.deleteAllCookies();
		GjInfo = gujiaInfo_init();
		//Report.seleniumReport("shanghai.anjuke.com", "房价评估页验证");
		
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		driver = null;
	}
	
	private AnjukeGujiaInformation gujiaInfo_init(){
		GjInfo.setCommunityName("潍坊八村 ");//小区
		GjInfo.setHouseType_S("4");//室
		GjInfo.setHouseType_T("4");//厅
		GjInfo.setHouseType_W("4");//卫
		GjInfo.setHouseArea("120");//面积	
		GjInfo.setFloorCur("3");//第几层
		GjInfo.setFloorTotal("6");//共几层
		GjInfo.setHouseType("老公房");//房屋类型
		GjInfo.setFitmentInfo("普通装修");//装修类型
		GjInfo.setOrientations("东");//朝向
		GjInfo.setBuildYear("2009");//建造年代
		//String time = PublicProcess.getNowDateTime("HH:mm:ss");
		return GjInfo;
	  }
		@Test(groups = {"unstable"})
		public void gujia() {
//			driver.deleteAllCookies();
			AjkGujia.GJDetail(driver, GjInfo);
		}
}
			
		



