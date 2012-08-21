package com.anjuke.ui.testcase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.report.Report;

public class Test1 {
	
	WebDriver driver = null;
	private String BaseURL; 
	
	@BeforeMethod
	public void setup(){
		driver = new FirefoxDriver();
		BaseURL = "http://shanghai.bbs.anjuke.com/t96Z";		
	}
	
	
	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
		driver = null;
	}
	
	@Test
	public void test1(){
		String str1,str2;
		int sum;
		int n;
		
		driver.get(BaseURL);
		
		//获取搜索结果总数
		str1 = driver.findElement(By.xpath("//*[@id='content']/div[1]/strong/em")).getText();
//		sum = Integer.getInteger(str1);
		System.out.println(str1);
		
		//获取返回关键字
		str2 = driver.findElement(By.xpath("//*[@id='content']/div[1]/strong")).getText();
		Pattern p = Pattern.compile(".+“(.+)”.+");
		Matcher m = p.matcher(str2);
		
		n = m.groupCount();
		System.out.println(n);
		
		if(m.find()){
			System.out.println(m.group(1));
		}		
	}

}
