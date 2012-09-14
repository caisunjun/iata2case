package com.anjuke.ui.testcase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.LogFile;
import com.anjukeinc.iata.ui.report.Report;



public class TestA { 
	private int n;
	private String  str1;
	Set<String> list = null;
	private WebDriver driver;

	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	
	@BeforeMethod
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://shanghai.anjuke.com";
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
	
	@Test
	public void submitEvaluate() throws Exception {
		driver.get(baseUrl);
		driver.findElement(By.cssSelector("img")).click();		
		//切换窗口
		list = driver.getWindowHandles();
		n = list.size();
		driver = driver.switchTo().window(list.toArray()[n-1].toString());
		waitForPageToLoad(1000);
		//提交评价
		driver.findElement(By.id("evaluate_trigger")).click();
		waitForPageToLoad(1000);					
		driver.findElement(By.xpath("//a[@onclick='clickstar(5);']")).click();		
		waitForPageToLoad(1000);
		driver.findElement(By.xpath("//a[@onclick='clickstar(9);']")).click();
		waitForPageToLoad(1000);
		driver.findElement(By.xpath("//a[@onclick='clickstar(14);']")).click();
		waitForPageToLoad(1000);
		//点击提交评论
		driver.findElement(By.id("next")).click();
		waitForPageToLoad(1000);
		//未登录提示层，点击不登录
		driver.findElement(By.id("Tflogin_off")).click();
		waitForPageToLoad(1000);
		//关闭成功评价提示层
		driver.findElement(By.cssSelector("#ajk_firend > div.view_prop_popup_fav > div.my_member_marked_mm > div.border > form > div.login > div.title > span.close > img")).click();
		
		str1 = driver.findElement(By.xpath("//*[@id='yonghuhaoping']/b/cite")).getText();		
//		if(str1 == "暂无评价"){
//			System.out.println(str1);			
//		}
//		else{
//			Pattern p = Pattern.compile("\\d+");
//			Matcher m = p.matcher(str1);
//			if(m.find()){
//				System.out.println(m);
//			}
//			else{
//				System.out.println(m);
//			}
//		}
	}
	
	//检查各页面UFS数据是否一致
//	public void checkEvaluate(){
//		private String 
//	}
//
//	

	
	public void waitForPageToLoad(long longtime) {
		try {
			Thread.sleep(longtime);
		} catch (InterruptedException exception) {
			exception.getStackTrace();
		}
	}



	private void fail(String verificationErrorString) {
		// TODO Auto-generated method stub
		
	}

//	private boolean isElementPresent(By by) {
//		try {
//			driver.findElement(by);
//			return true;
//		} catch (NoSuchElementException e) {
//			return false;
//		}
//	}

}
