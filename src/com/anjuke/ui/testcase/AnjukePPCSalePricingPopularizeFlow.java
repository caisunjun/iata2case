package com.anjuke.ui.testcase;

import static org.testng.Assert.fail;
import java.io.IOException;
import java.net.MalformedURLException;
import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import com.anjuke.ui.bean.AnjukeSaleInfo;
import com.anjuke.ui.publicfunction.BrokerSaleOperatingPPC;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;

/**
 * 该用例完成二手房定价操作，逻辑如下
 * 
 * 发房源 --> 初始化定价计划 --> 新建定价计划 --> 推广房源 --> 房源列表页搜索房源 --> 点击 --> 验证扣费结果。
 * 
 * @Author grayhu
 * @time 2013-02-05 9:00
 */
public class AnjukePPCSalePricingPopularizeFlow {
	private Browser driver = null;
	private WebDriver webDriver = null;
	private AnjukeSaleInfo saleInfo = new AnjukeSaleInfo();
	String PropId = "";
	String PlanName = "";
	String PlanId = "";

	@BeforeClass
	public void startUp() throws MalformedURLException {
	}

	@AfterTest
	public void tearDown() {
		driver = null;
	}

	@Test(groups = {"unstable"})
	public void NewPricingPlan() {
		saleInfo = saleInfo_init();
		driver = FactoryBrowser.factoryBrowser();
		PublicProcess.logIn(driver, "1349689430yzN", "abc123", false, 1);
		driver.get("http://my.anjuke.com/user/ppc/brokerpropmanage2/W0QQactZsaleQQggZ1");
		releaseSale();
		driver.click("//h3[@class='ppch ppch01']/a", "从房源库进入定价推广页面");
		initPlanList();
		createPlan();
		driver.close();
		driver.quit();
	}

	@Test(dependsOnMethods = "NewPricingPlan")
	public void ClickFee() {
		driver = FactoryBrowser.factoryProxyBrowser();
		driver.get("http://shanghai.anjuke.com/sale/");
		sleep(30000);
		driver.type("//input[@class='input_text']", PropId, "输入刚推广的定价房源ID："
				+ PropId + "进行搜索");
		driver.click("//input[@class='input_button se_house']", "点击搜索一下按钮");
		String href = driver.getAttribute("//a[@id='prop_name_qt_prop_1']",
				"href", 3);
		driver.assertContains("没有找到刚推广的房子 " + href + "", PropId);
		if (href.contains(PropId)) {
			driver.click("//a[@id='prop_name_qt_prop_1']", "点击搜索结果中刚推广的房源", 10);
		}
		sleep(15000);
		driver.close();
		driver.quit();
	}

	@Test(dependsOnMethods = "ClickFee")
	public void CheckCost() {
		driver = FactoryBrowser.factoryBrowser();
		PublicProcess.logIn(driver, "1349689430yzN", "anjukeqa", false, 1);
		driver.get("http://my.anjuke.com/user/ppcnew/staticpricelist/W0QQactZsale");
		sleep(5000);
		double planCost = getPlanCost();
		int propCol = getPropCol();
		double propPrice = getPropPrice();

		if (propCol > 0) {
			driver.assertEquals(Double.toString(planCost), Double
					.toString(propCol * propPrice), "扣费",
					"扣费正确 - 计划的花费等于定价底价乘以扣费次数");
		} else {
			driver.assertEquals(Double.toString(planCost), Double
					.toString(propPrice), "未扣费", "未扣费 - 需要验证为何没有扣费成功");
		}
		sleep(3000);
		driver.close();
		driver.quit();
	}

	// 发房源 - 二手房信息初始化
	private AnjukeSaleInfo saleInfo_init() {
		saleInfo.setCommunityName("好盘测试小区");// 小区
		saleInfo.setPriceTaxe("49");// 售价
		saleInfo.setHouseArea("35.0");// 面积
		saleInfo.setHouseType_S("1");// 室
		saleInfo.setHouseType_T("1");// 厅
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
		driver.assertContains(driver.getText("//div[@class='tit']/strong",
				"获取新建定价计划的状态"), "推广中");
	}

	// 定价推广页 - 根据计划ID,来获取定价计划今日已花费
	private Double getPlanCost() {
		String planCost = driver
				.getText("//div[@class='cost']/em", "定价计划今日已花费");
		return getDouble(planCost);
	}

	// 定价推广页 - 根据房源ID,来获取房源今日点击数
	private int getPropCol() {
		String planCol = driver.getText("//td[@id='col-point" + PropId + "']",
				"定价计划今日今日点击数");
		return getInt(planCol);
	}

	// 定价推广页 - 根据房源ID,来获取房源定价价格
	private Double getPropPrice() {
		String propPrice = driver.getText("//td[@id='col-price" + PropId
				+ "']/em", "房源定价价格");
		return getDouble(propPrice);
	}

	// 使用代理服务器
	private void FirefoxBrowser() {
		FirefoxProfile ffPro = new FirefoxProfile();
		try {
			JavaScriptError.addExtension(ffPro);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		ffPro.setPreference("network.proxy.type", 1);
		ffPro.setPreference("network.proxy.http", Init.G_config
				.get("FFproxyIp"));
		ffPro.setPreference("network.proxy.http_port", Init.G_config
				.get("FFproxyPort"));
		ffPro
				.setPreference("network.proxy.ssl", Init.G_config
						.get("FFproxyIp"));
		ffPro.setPreference("network.proxy.ssl_port", Init.G_config
				.get("FFproxyPort"));
		System
				.setProperty("webdriver.firefox.bin", Init.G_config
						.get("FFpath"));
		webDriver = new FirefoxDriver(ffPro);
		driver = new Browser(webDriver);
	}

	private int getInt(String val) {
		return Integer.parseInt(val);
	}

	private Double getDouble(String val) {
		return Double.parseDouble(val);
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException exception) {
			exception.getStackTrace();
		}
	}

}
