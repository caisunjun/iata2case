package com.anjuke.ui.testcase;

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
import com.anjuke.ui.publicfunction.BrokerSaleOperating;
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
public class AnjukeBrokerSaleRelease {
	private Browser driver = null;
	private AnjukeSaleInfo saleInfo = new AnjukeSaleInfo();
	private boolean needPic = false;

	@BeforeMethod
	public void startUp() {
		driver = FactoryBrowser.factoryBrowser();
		driver.deleteAllCookies();
		saleInfo = saleInfo_init();
	}

	@AfterMethod
	public void tearDown() {
		Report.seleniumReport("", "");
		driver.quit();
		driver = null;
	}
	
	private AnjukeSaleInfo saleInfo_init() {
		saleInfo.setCommunityName("潍坊八村");//小区
		saleInfo.setPriceTaxe("200");//售价
		saleInfo.setHouseArea("120.00");//面积	
		saleInfo.setHouseType_S("3");//室
		saleInfo.setHouseType_T("2");//厅
		saleInfo.setHouseType_W("1");//卫
		saleInfo.setFloorCur("3");//第几层
		saleInfo.setFloorTotal("6");//共几层
		saleInfo.setHouseType("公寓");//房屋类型
		saleInfo.setFitmentInfo("精装修");//装修类型
		saleInfo.setOrientations("东西");//朝向
		saleInfo.setBuildYear("2009");//建造年代
		String time = PublicProcess.getNowDateTime("HH:mm:ss");
		saleInfo.setHouseTitle("发布出售请勿联系谢谢" + time);
		saleInfo.setHouseDescribe("发布出售，此房源为测试房源，房源信息没有经过验证，请勿联系，感谢您对的支持。谢谢！");
		return saleInfo;
	}

	//(timeOut = 200000)
	@Test(groups = {"unstable"})
	public void releaseSale() {
		driver.deleteAllCookies();
		saleInfo.setUserName(PublicProcess.logIn(driver, "test1", "123456",false, 1));
		
		BrokerSaleOperating.releaseSale(driver, saleInfo,needPic);
		
		driver.close();
		}
}
