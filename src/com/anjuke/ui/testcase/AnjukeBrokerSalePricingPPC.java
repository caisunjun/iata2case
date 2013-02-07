package com.anjuke.ui.testcase;

import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.anjuke.ui.bean.AnjukeSaleInfo;
import com.anjuke.ui.page.Ajk_Sale;
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
	String PropId = "";
	String PlanName = "";
	String PlanId = "";
	
	WebDriver driver1;
	WebDriver webDriver1;
	
//	@BeforeMethod
	public void startUp() throws MalformedURLException {
//		driver = FactoryBrowser.factoryBrowser();
//		saleInfo = saleInfo_init();
	
		
	  //远程访问
//      URL url = new URL("http://192.168.201.190:5555/wd/hub");
//      DesiredCapabilities capabilities = new DesiredCapabilities();
//      capabilities.setBrowserName("firefox");
//      capabilities.setPlatform(Platform.LINUX);
//      driver1 = new RemoteWebDriver(url,capabilities); 
        
        
		DesiredCapabilities capabilities = null;
    	FirefoxProfile ffPro = new FirefoxProfile(new File("C:\\Documents and Settings\\grayhu\\Application Data\\Mozilla\\Firefox\\Profiles\\czma0y37.default"));
		try {
			JavaScriptError.addExtension(ffPro);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability(FirefoxDriver.PROFILE, ffPro);
		capabilities.setCapability("webdriver.remote.quietExceptions", true);
    	capabilities.setPlatform(Platform.XP);
    	
    	ffPro.setPreference("network.proxy.type", 1); 
    	ffPro.setPreference("network.proxy.http", "202.171.253.108"); 
    	ffPro.setPreference("network.proxy.http_port", 80); 
    	ffPro.setPreference("network.proxy.ssl", "202.171.253.108"); 
    	ffPro.setPreference("network.proxy.ssl_port", 80); 
    	System.setProperty("webdriver.firefox.bin", "D:\\Mozilla firefox\\firefox.exe");
    	
    	
		webDriver1 = new FirefoxDriver(ffPro);
	}
	
//	@Test
	public void test1(){
		webDriver1.get("http://www.baidu.com");
	        sleep(5000);
	}

//	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
		driver = null;
	}

	// 登录经纪人后台
	private void login() {
		// PublicProcess.logIn(driver, "1349689430yzN", "anjukeqa", false, 1);
		// driver.click(Public_HeaderFooter.HEADER_BROKERLINK, "进入我的网络经纪人");
		driver.get("http://my.anjuke.com/my/login");
		driver.type("id^username", "1349689430yzN", "输入用户名");
		driver.type("name^password", "anjukeqa", "输入密码");
		driver.click("name^submit", "登录");
	}

	// 发房源 - 二手房信息初始化
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

	// 房源库发布二手房
	public void releaseSale() {
		BrokerSaleOperatingPPC.releaseSale(driver, saleInfo, false);
		PropId = driver.getAttribute("//*[@id='showProdId_0']", "title");
	}

	// 定价推广 - 定价计划初始化，删除所有的定价计划
	private void initPlanList() {
		int pc = driver.getElementCount("//div[@class='intro-box']");
		for (; pc > 0; pc--) {
			driver.moveToElementClick("//div[@class='tab-cont']/div[" + pc
					+ "]/div[1]");
			driver.click("//div[@class='tab-cont']/div[" + pc
					+ "]//i[@class='delete']", "删除定价计划");
			driver.doAlert("确定");
			sleep(5000);
		}
	}

	// 推广房源 - 新建计划，添加房源
	private void createPlan() {
		driver.click("//a[@class='btn-create']", "点击进入新建定价推广计划");
		PlanName = "定价计划" + PublicProcess.getNowDateTime("mm:ss") + "";
		driver.type("//input[@class='txt']", PlanName, "第一步 - 输入新建的定价计划名称");
		driver.click("//button[@class='btn-sure']", "第一步 - 点击确定计划按钮");
		driver.assertEquals(PlanName, driver.getText(
				"//div[@class='plan-info']/span[1]/strong",
				"第二步 - 获取新建定价计划的计划名称"), "比较新建的计划名称是否相等", "第二步 - 新建计划名称");
		driver.click("//div[@class='operate-box']/a[1]",
				"第二步 - 点击去房源库挑选按钮，进入第三步挑选房源");
		driver.click("id^chkboxProid0", "第二步 - 房源库，勾选房源库中的第一条房源");
		driver.click("id^prices0", "第二步 - 房源库，点击确认选择按钮，把房源添加到计划");
		driver.assertTrue(driver.check("//tr[@class='cancel_" + PropId + "']"),
				"第三步 - 确认房源", "加入计划的房源是否是刚发布的房源");
		driver.click("//button[@class='btn-sure']", "第三步 - 点击确认房源按钮，进入第四步");
		driver.click("//button[@class='btn-sure']", "第三步 - 点击确定计划按钮");
		driver.assertEquals("推广成功！", driver.getText(
				"//div[@class='price-spread']/dl/dt", "第四步 - 房源推广成功信息提示"),
				"是否推广成功", "添加房源到新建定价计划成功提示");
		driver.click("//div[@id='content']/div[1]/dl/dd/a",
				"第四步 - 点击跳转到定价推广管理页");

	}

	// 定价推广页 - 根据计划ID,来获取定价计划今日已花费
	private int getPlanCost() {
		String planCost = driver
				.getText("//div[@class='cost']/em", "定价计划今日已花费");
		System.out.println("--------------" + planCost);
		return getInt(planCost);
	}

	// 定价推广页 - 根据房源ID,来获取房源今日点击数
	private int getPropCol() {
		String planCol = driver.getText("//td[@id='col-point" + PropId + "']",
				"定价计划今日今日点击数");
		System.out.println("--------------" + planCol);
		return getInt(planCol);
	}

	// 定价推广页 - 根据房源ID,来获取房源定价价格
	private int getPropPrice() {
		String propPrice = driver.getText("//td[@id='col-price" + PropId
				+ "']/em", "房源定价价格");
		System.out.println("--------------" + propPrice);
		return getInt(propPrice);
	}

	private int getInt(String val) {
		return Integer.parseInt(val);
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException exception) {
			exception.getStackTrace();
		}
	}

	@SuppressWarnings("unused")
//	@Test
	private void BrokerLogIn() {
		login();
		driver
				.get("http://my.anjuke.com/user/ppc/brokerpropmanage2/W0QQactZsaleQQggZ1");

		releaseSale();
		driver.click("//h3[@class='ppch ppch01']/a", "从房源库进入定价推广页面");
		initPlanList();
		createPlan();
	}
	
	@SuppressWarnings("unused")
//	@Test
	private void CheckCost() {
		login();
		sleep(5000);
		driver
				.get("http://my.anjuke.com/user/ppcnew/staticpricelist/W0QQactZsale");
		sleep(5000);
		double planCost = getPlanCost();
		int propCol = getPropCol();
		double propPrice = getPropPrice();
		if (propCol > 0) {
			System.out.println("+++++++++++++++++++++++扣费成功;" + "点击量："
					+ propCol);
			if (planCost == propPrice) {
				System.out.println("+++++++++++++++++++++++扣费正确" + planCost
						+ "==" + propPrice);

			} else {
				System.out.println("+++++++++++++++++++++++无效点击");
			}
		}

	}
	
	@SuppressWarnings("unused")
//	@Test
	private void ClickFee() {
		driver.get("http://shanghai.anjuke.com/sale/");
		driver.deleteAllCookies();
		sleep(10000);
		driver.type(Ajk_Sale.KwInput, PropId , "输入查询条件：房源ID");
		driver.click(Ajk_Sale.KwSubmit, "点击找房子");
		driver.click("//a[@id='prop_name_qt_prop_1']", "点击定价房源");
		sleep(5000);
	}
	
//	@Test(dependsOnMethods= "")
	private void ClickFeeF() {
		webDriver1.manage().deleteAllCookies();
		sleep(10000);
		webDriver1.get("http://shanghai.anjuke.com/sale/");
		webDriver1.manage().deleteAllCookies();
		webDriver1.findElement(By.xpath("//input[@class='input_text']")).sendKeys("152975003");
		sleep(5000);
		webDriver1.findElement(By.xpath("//input[@class='input_button se_house']")).click();
		sleep(10000);
		webDriver1.findElement(By.xpath("//a[@id='prop_name_qt_prop_1']")).click();
		sleep(5000);
	}


}
