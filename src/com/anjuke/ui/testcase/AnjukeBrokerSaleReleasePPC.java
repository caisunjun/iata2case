package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;

import com.anjuke.ui.bean.AnjukeSaleInfo;
import com.anjuke.ui.page.Broker_Checked;
import com.anjuke.ui.page.Public_HeaderFooter;
import com.anjuke.ui.publicfunction.BrokerSaleOperating;
import com.anjuke.ui.publicfunction.BrokerSaleOperatingPPC;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;

/**
 * 该用例完成安居客出售发布操作，逻辑如下 
 * 1、从奶牛首页进入房源库
 * 2、点击右上发布房源按钮
 * 3、新窗口发布房源，并保存至房源库
 * 3、返回房源库点击第一套房源
 * 4、在房源单页验证房源详细信息
 * @Author ccyang
 * @time 2012-11-07 15:00
 */
public class AnjukeBrokerSaleReleasePPC {
	private Browser driver = null;
	private AnjukeSaleInfo saleInfo = new AnjukeSaleInfo();
	private boolean needPic = false;

	@BeforeMethod
	public void startUp() {
		driver = FactoryBrowser.factoryBrowser();
//		driver.deleteAllCookies();
		saleInfo = saleInfo_init();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		driver = null;
	}
	
    @SuppressWarnings("deprecation")
	@Configuration(afterTestClass = true)
	public void doBeforeTests() {
		System.out.println("***AnjukeBrokerSaleRelease is done***");
	}

	private AnjukeSaleInfo saleInfo_init() {
		saleInfo.setCommunityName("潍坊八村");// 小区
		saleInfo.setPriceTaxe("200");// 售价
		saleInfo.setHouseArea("120.1");// 面积
		saleInfo.setHouseType_S("3");// 室
		saleInfo.setHouseType_T("2");// 厅
		saleInfo.setHouseType_W("1");// 卫
		saleInfo.setFloorCur("3");// 第几层
		saleInfo.setFloorTotal("6");// 共几层
		saleInfo.setHouseType("公寓");// 房屋类型
		saleInfo.setFitmentInfo("精装修");// 装修类型
		saleInfo.setOrientations("东西");// 朝向
		saleInfo.setBuildYear("2009");// 建造年代
		String time = PublicProcess.getNowDateTime("HH:mm:ss");
		saleInfo.setHouseTitle("发布出售请勿联系谢谢" + time);
		saleInfo.setHouseDescribe("发布出售，此房源为测试房源，房源信息没有经过验证，请勿联系，感谢您对的支持。谢谢！");
		return saleInfo;
	}

	// (timeOut = 200000)
	@Test(groups = { "unstable" })
	public void releaseSale() {
//		driver.deleteAllCookies();
		String casestatus = "";
		String testing = "testing";
		//从config.ini中取出casestatus
		casestatus = Init.G_config.get("casestatus");
		//如果取出的值等于testing 则用另一个账号登陆
		if(testing.equals(casestatus))
		{
			saleInfo.setUserName(PublicProcess.logIn(driver, "1349689747de9", "anjukeqa",false, 1));
		}
		//如果config中casestatus的值不为testing或config未配置casestatus，用原先的账号登陆
		else
		{
			saleInfo.setUserName(PublicProcess.logIn(driver, "1349689747de9", "anjukeqa",false, 1));
		}
		driver.click(Public_HeaderFooter.HEADER_BROKERLINK, "进入我的网络经纪人");
		driver.click(Broker_Checked.Fangyuanku_ppc, "进入房源库");
		BrokerSaleOperatingPPC.releaseSale(driver, saleInfo, needPic);
		
		driver.close();
	}
	
} 
