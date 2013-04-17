package com.anjuke.ui.testcase;

/**
 * 该用例主要用来检查anjuke城市首页的找二手房、找新房按钮顺序
 * 步骤：
 * 随机一个二手房城市，判断排在前面的搜索按钮是否为找二手房
 * 随机一个新城市，判断排在前面的搜索按钮是否为找新房
 * @author ccyang
 * @last updatetime 2013-4-16上午10:55:38
 */

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Ajk_HomePage;
import com.anjuke.ui.page.Ajk_Sale;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;

public class AnjukeMonitorHomepageSearchButton {
	private Browser bs = null;
	@BeforeMethod
	public void setUp() {
		bs = FactoryBrowser.factoryBrowser();
		Report.G_CASECONTENT = "监控城市首页搜索按钮顺序";
	}
	@AfterMethod
	public void tearDown() {
		bs.quit();
		bs = null;
	}
	
	@Test
	public void testSaleButton() throws InterruptedException {
		String[] city;
    	String cityPinyin = "";
    	String cityName = "";
    	
    	city = PublicProcess.getRandomCityFromConfig(1).split("-");
    	cityPinyin = city[0];
    	cityName = city[1];
    	
    	checkSaleSearchButton(cityPinyin,cityName);
	}
	
	@Test
	public void testXinfangButton() throws InterruptedException {
		String[] city;
    	String cityPinyin = "";
    	String cityName = "";
    	
    	city = PublicProcess.getRandomCityFromConfig(2).split("-");
    	cityPinyin = city[0];
    	cityName = city[1];
    	
    	checkXinfangSearchButton(cityPinyin,cityName);
	}
	
	//检查首页搜索按钮顺序
	private void checkSaleSearchButton(String cityPinyin,String cityName){
		String homePageUrl = "http://"+cityPinyin+".anjuke.com";
    	bs.get(homePageUrl);
    	
		//清空cookie，防止guid被记住
		bs.deleteAllCookies();
    	bs.get("http://www.anjuke.com/version/switch?f1=ga");
    	bs.get(homePageUrl);
    	
    	//input[@class='find-button']的按钮总共有两个，selenium默认取到的那个就是排在靠前位置的
    	String buttonValue = bs.getAttribute("//input[@class='find-button']", "value");
    	bs.assertEquals("找二手房",buttonValue, "二手房为主的城市，默认情况下搜索按钮的顺序", "排第一位的搜索按钮是找二手房");
	}
	
	//检查首页搜索按钮顺序
	private void checkXinfangSearchButton(String cityPinyin,String cityName){
		String homePageUrl = "http://"+cityPinyin+".anjuke.com";
    	bs.get(homePageUrl);
    	
		//清空cookie，防止guid被记住
		bs.deleteAllCookies();
		bs.get("http://beijing.anjuke.com/version/switch?f1=ga");
    	bs.get(homePageUrl);
    	
    	//input[@class='find-button']的按钮总共有两个，selenium默认取到的那个就是排在靠前位置的
    	String buttonValue = bs.getAttribute("//input[@class='find-button']", "value");
    	bs.assertEquals("找新盘",buttonValue, "新房为主的城市，默认情况下搜索按钮的顺序", "排第一位的搜索按钮是找新房");
	}
}
