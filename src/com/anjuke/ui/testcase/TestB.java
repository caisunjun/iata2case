package com.anjuke.ui.testcase;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestB {
	private WebDriver driver;
	private int n;
	Set<String> list = null;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	@BeforeMethod
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://shanghai.anjuke.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void test1() throws Exception {
		driver.get(baseUrl);
		driver.findElement(By.cssSelector("a[title=\"上海二手房\"] > span")).click();
		list=driver.getWindowHandles();
		n=list.size();
		driver = driver.switchTo().window(list.toArray()[n-1].toString());
		driver.findElement(By.id("prop_name_qt_prop_4")).click();
		driver.findElement(By.id("evaluate_trigger")).click();
		
		driver.findElement(By.cssSelector("a.star_active")).click();
		driver.findElement(By.xpath("//a[@onclick='clickstar(10);']")).click();
		driver.findElement(By.xpath("//a[@onclick='clickstar(15);']")).click();
		driver.findElement(By.id("next")).click();
		driver.findElement(By.id("Tflogin_off")).click();
		driver.findElement(By.cssSelector("#ajk_firend > div.view_prop_popup_fav > div.my_member_marked_mm > div.border > form > div.login > div.title > span.close > img")).click();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
