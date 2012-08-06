package com.anjuke.ui.testcase;

import org.openqa.selenium.firefox.FirefoxDriver;
//import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import com.anjukeinc.iata.ui.init.Init;

public class sellhouse {
	private String  baseUrl;
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();
	@BeforeMethod
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://shanghai.anjuke.com/";
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
		}

	@Test
	public void test1() throws Exception {
		driver.get(baseUrl);
		driver.get("http://agent.anjuke.com/login/");
		//driver.findElement(By.xpath("//ul[@id='ajax_nologin_apf_id_6']/li/a")).click();
		driver.findElement(By.id("loginName")).clear();
		driver.findElement(By.id("loginName")).sendKeys("ajk_sh");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("anjukeqa");
		driver.findElement(By.cssSelector("input.btn")).click();
		driver.findElement(By.id("my_app_sale")).click();
		driver.findElement(By.id("txtCommunity")).clear();
	    driver.findElement(By.id("txtCommunity")).sendKeys("申良花园");
		waitForPageToLoad(1000);
		//driver.type(Init.G_objMap
				//.get("anjuke_wangluojingjiren_sale_xiaoqu_yinyu"), "潍坊八村",
				//"小区名称");
		//driver.switchToIframe(Init.G_objMap
				//.get("anjuke_wangluojingjiren_salse_ifrCommunityList"),
				//"选择小区");
		//.get("anjuke_wangluojingjiren_sale_xiaoqu_nameClick"),
		//"选择小区");
        //driver.exitFrame()
		driver.findElement(By.cssSelector("span.code")).click();
		driver.findElement(By.id("txtProPrice")).clear();
		driver.findElement(By.id("txtProPrice")).sendKeys("115");
		driver.findElement(By.id("txtAreaNum")).clear();
		driver.findElement(By.id("txtAreaNum")).sendKeys("112");
		driver.findElement(By.id("txtRoomNum")).clear();
		driver.findElement(By.id("txtRoomNum")).sendKeys("2");
		driver.findElement(By.id("txtHallNum")).clear();
		driver.findElement(By.id("txtHallNum")).sendKeys("2");
		driver.findElement(By.id("txtToiletNum")).clear();
		driver.findElement(By.id("txtToiletNum")).sendKeys("2");
		driver.findElement(By.id("txtProFloor")).clear();
		driver.findElement(By.id("txtProFloor")).sendKeys("2");
		driver.findElement(By.id("txtFloorNum")).clear();
		driver.findElement(By.id("txtFloorNum")).sendKeys("3");
		new Select(driver.findElement(By.id("radUseType"))).selectByVisibleText("老公房");
		new Select(driver.findElement(By.id("radFitment"))).selectByVisibleText("普通装修");
		new Select(driver.findElement(By.id("radHouseOri"))).selectByVisibleText("东");
		driver.findElement(By.id("txtHouseAge")).clear();
		driver.findElement(By.id("txtHouseAge")).sendKeys("2005");
		driver.findElement(By.id("txtProName")).clear();
		driver.findElement(By.id("txtProName")).sendKeys("测试房源 请勿联系");
		driver.findElement(By.id("btnSubmit")).click();
		WebElement description = driver.findElement(By.xpath("//td[@id='cke_contents_txtExplain']/iframe"));
		driver.switchTo().frame(description);
		driver.switchTo().activeElement().sendKeys("测试 测试 测试");
		driver.switchTo().defaultContent();
		driver.findElement(By.id("btnSubmit")).click();
		//driver.findElement(By.id("radSaleType_2")).click();
		//driver.findElement(By.id("chkUploadPic")).click();
		//driver.findElement(By.id("btnNext")).click();
	}
	public void waitForPageToLoad(long longtime) {
		try {
			Thread.sleep(longtime);
		} catch (InterruptedException exception) {
			exception.getStackTrace();
		}
	}

}


