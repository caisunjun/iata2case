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

public class sellhouse {
	WebDriver driver;
	private String baseUrl;
	
	@BeforeMethod
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://shanghai.anjuke.com/";
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
		driver.findElement(By.id("loginName")).sendKeys("ajk_nj");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("anjukeqa");
		driver.findElement(By.cssSelector("input.btn")).click();
		waitForPageToLoad(500);
		driver.findElement(By.id("my_app_sale")).click();
		waitForPageToLoad(500);
		driver.findElement(By.id("txtCommunity")).clear();
		driver.findElement(By.id("txtCommunity")).sendKeys("花园路小区");
		waitForPageToLoad(500);
		driver.findElement(By.cssSelector("span.code")).click();
		waitForPageToLoad(500);
		driver.findElement(By.id("txtProPrice")).clear();
		waitForPageToLoad(500);
		driver.findElement(By.id("txtProPrice")).sendKeys("112");
		waitForPageToLoad(500);
		driver.findElement(By.id("txtAreaNum")).clear();
		waitForPageToLoad(500);
		driver.findElement(By.id("txtAreaNum")).sendKeys("112");
		waitForPageToLoad(500);
		driver.findElement(By.id("txtRoomNum")).clear();
		waitForPageToLoad(500);
		driver.findElement(By.id("txtRoomNum")).sendKeys("2");
		waitForPageToLoad(500);
		driver.findElement(By.id("txtHallNum")).clear();
		waitForPageToLoad(500);
		driver.findElement(By.id("txtHallNum")).sendKeys("2");
		waitForPageToLoad(500);
		driver.findElement(By.id("txtToiletNum")).clear();
		waitForPageToLoad(500);
		driver.findElement(By.id("txtToiletNum")).sendKeys("2");
		waitForPageToLoad(500);
		driver.findElement(By.id("txtProFloor")).clear();
		waitForPageToLoad(500);
		driver.findElement(By.id("txtProFloor")).sendKeys("2");
		waitForPageToLoad(500);
		driver.findElement(By.id("txtFloorNum")).clear();
		waitForPageToLoad(500);
		driver.findElement(By.id("txtFloorNum")).sendKeys("3");
		waitForPageToLoad(500);
		new Select(driver.findElement(By.id("radUseType"))).selectByVisibleText("普通住宅");
		waitForPageToLoad(500);
		new Select(driver.findElement(By.id("radFitment"))).selectByVisibleText("普通装修");
		waitForPageToLoad(500);
		new Select(driver.findElement(By.id("radHouseOri"))).selectByVisibleText("东");
		waitForPageToLoad(500);
		driver.findElement(By.id("txtHouseAge")).clear();
		waitForPageToLoad(500);
		driver.findElement(By.id("txtHouseAge")).sendKeys("2005");
		waitForPageToLoad(500);
		driver.findElement(By.id("txtProName")).clear();
		waitForPageToLoad(500);
		driver.findElement(By.id("txtProName")).sendKeys("测试房源 请勿联系");
		waitForPageToLoad(500);
		//切换到富文本框
		WebElement descrition = driver.findElement(By.xpath("//td[@id='cke_contents_txtExplain']/iframe"));
		driver.switchTo().frame(descrition);
		driver.switchTo().activeElement().sendKeys("测试房源，请勿联系！！");
		waitForPageToLoad(500);	
		//切换到原发房页面
		driver.switchTo().defaultContent();
		driver.findElement(By.id("btnSubmit")).click();
	}
	
	public void waitForPageToLoad(long longtime) {
		try {
			Thread.sleep(longtime);
		} catch (InterruptedException exception) {
			exception.getStackTrace();
		}
	}

}


