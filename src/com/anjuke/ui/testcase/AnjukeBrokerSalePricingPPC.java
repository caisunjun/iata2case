package com.anjuke.ui.testcase;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.anjuke.ui.bean.AnjukeSaleInfo;
import com.anjuke.ui.page.Broker_Checked;
import com.anjuke.ui.page.Broker_PPC_StaticPriceList;
import com.anjuke.ui.page.Public_HeaderFooter;
import com.anjuke.ui.publicfunction.BrokerSaleOperatingPPC;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;

/**
 * 该用例完成二手房定价操作，逻辑如下
 * 
 * @Author grayhu
 * @time 2012-12-04 9:00
 */
public class AnjukeBrokerSalePricingPPC {
	private Browser driver = null;
	private AnjukeSaleInfo saleInfo = new AnjukeSaleInfo();
	int PropId = 0;
	String PlanName = "";

//	@BeforeMethod
	public void startUp() {
		driver = FactoryBrowser.factoryBrowser();
		// driver.deleteAllCookies();
		saleInfo = saleInfo_init();
	}

//	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
		driver = null;
	}
	
	//登录经纪人后台
	private void login(){
		PublicProcess.logIn(driver,"13370290211","anjukeqa", false, 1);
		driver.click(Public_HeaderFooter.HEADER_BROKERLINK, "进入我的网络经纪人");
	}
	
	//定价计划初始化 - 删除所有的定价计划
	private void initPlanList(){
		int pc = driver.getElementCount("//div[@class='extend']");
			driver.moveToElement("//div[@class='tab-cont']/div["+ pc +"]");
			driver.click("//div[@class='tab-cont']/div["+ pc +"]//i[@class='delete']", "删除定价计划");
			driver.doAlert("确定");
	}
	
	
	//创建一个定价计划
	private void CreatePlan(){
		driver.click("//a[@class='btn-create']", "创建推广计划");
		PlanName = "定价计划" +PublicProcess.getNowDateTime("mm:ss") +"";
		driver.type(".//*[@id='dialog-box']/div/form/div[1]/input", PlanName, "创建定价计划计划名称");
		driver.type(".//*[@id='dialog-box']/div/form/div[2]/input", "20", "创建定价计划每日限额");
		driver.click(".//*[@id='dialog-box']/div/form/button", "确定创建定价计划按钮");
		driver.doAlert("确定");
	}
	
	//二手房信息初始化
	private AnjukeSaleInfo saleInfo_init() {
		saleInfo.setCommunityName("好盘测试小区");// 小区
		saleInfo.setPriceTaxe("200");// 售价
		saleInfo.setHouseArea("120.00");// 面积
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
		saleInfo.setHouseTitle("定价测试请勿联系谢谢" + time);
		saleInfo.setHouseDescribe("定价测试，此房源为测试房源，房源信息没有经过验证，请勿联系，感谢您对的支持。谢谢！");
		return saleInfo;
	}
	
	//房源库发布二手房
	public void releaseSale() {
		BrokerSaleOperatingPPC.releaseSale(driver, saleInfo, false);
		PropId = getInt(driver.getAttribute("//*[@id='showProdId_0']", "title"));
	}
	
	//选择一条房源的进行定价推广,且进入推广页面
	private void SelProp(){
		driver.click(Broker_PPC_StaticPriceList.SelProp(PropId), "点击房源库中定价推广，进行房源定价推广");
		driver.switchWindo(3);
	}
	
	private int getInt(String val) {
		return Integer.parseInt(val);
	}
	
//	@Test
	private void BrokerLogIn(){
		driver.get("http://my.anjuke.com/my/login");
		driver.type("id^username", "13370290211", "输入用户名");
		driver.type("name^password", "anjukeqa", "输入密码");
		driver.click("name^submit", "登录");
		driver.click(Public_HeaderFooter.HEADER_BROKERLINK, "进入我的网络经纪人");
		
		
		
		
		
//		login();
//		driver.click("//i[@class='icon_01']", "从经纪人后台首页进入定价推广页面");
//		initPlanList();
//		CreatePlan();
//		driver.click("//h3[@class='ppch ppch03']/a", "进入房源库");
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException exception) {
//			exception.getStackTrace();
//		}
//		releaseSale();
//		SelProp();
//		String tempEle = driver.getAttribute("//label[contains(.,'"+ PlanName +"')]", "for");
//		driver.click("//input[@id='"+  tempEle + "']", "选择一个定价计划");
//		driver.click("//button[@class='btn-sure']", "点击提交定价计划");
//		driver.close();
//		
	}
	
	//@Test
	
	
	
	
	
}
