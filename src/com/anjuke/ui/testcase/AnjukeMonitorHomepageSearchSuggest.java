package com.anjuke.ui.testcase;

/**
 * 该用例主要用来检查anjuke城市首页的搜索下拉联想
 * 步骤：
 * 
 * @UpdateAuthor ccyang
 * @last updatetime  2013/03/13
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

public class AnjukeMonitorHomepageSearchSuggest {
	private Browser bs = null;
	@BeforeMethod
	public void setUp() {
		bs = FactoryBrowser.factoryBrowser();
	}
	@AfterMethod
	public void tearDown() {
		bs.quit();
		bs = null;
	}
	
	@Test (groups = {"unstable"})
	public void testSaleSuggestion() throws InterruptedException {
		String[] city;
    	String cityPinyin = "";
    	String cityName = "";
    	
    	city = PublicProcess.getRandomCityFromConfig(1).split("-");
    	cityPinyin = city[0];
    	cityName = city[1];
    	
    	checkSaleSearchSuggest(cityPinyin,cityName);
	}
	
	@Test (groups = {"unstable"})
	public void testXinfangSuggestion() throws InterruptedException {
		String[] city;
    	String cityPinyin = "";
    	String cityName = "";
    	
    	city = PublicProcess.getRandomCityFromConfig(2).split("-");
    	cityPinyin = city[0];
    	cityName = city[1];
    	
    	checkXinfangSearchSuggest(cityPinyin,cityName);
	}
	
	//检查二手房搜索下拉的内容
	private void checkSaleSearchSuggest(String cityPinyin,String cityName){
		String homePageUrl = "http://"+cityPinyin+".anjuke.com";
    	bs.get(homePageUrl);
    	bs.type(Ajk_HomePage.SaleSearchBox, cityName+" ", "输入搜索关键词");
    	
    	String FirstSuggestion = ""; 
    	FirstSuggestion = bs.getText(Ajk_HomePage.FirstSuggestion, "获得第一个下拉联想的内容");
    	//二手房下拉联想的量词为套
    	bs.assertContains(FirstSuggestion, "套");
    	bs.click(Ajk_HomePage.FirstSuggestion, "点击第一个下拉联想");
    	//判断搜索词是否正确带入了
    	String suggestionKw = bs.getAttribute(Ajk_Sale.KwInput_Searched, "value");
    	System.out.println(suggestionKw);
    	bs.assertContains(FirstSuggestion, suggestionKw);
	}
	
	//检查新房搜索下拉的内容
	private void checkXinfangSearchSuggest(String cityPinyin,String cityName){
		String homePageUrl = "http://"+cityPinyin+".anjuke.com";
    	bs.get(homePageUrl);
    	bs.type(Ajk_HomePage.XinfangSearchBox, cityName+" ", "输入搜索关键词");
    	String FirstSuggestion = ""; 
    	FirstSuggestion = bs.getText(Ajk_HomePage.FirstSuggestion, "获得第一个下拉联想的内容");
    	//新房下拉联想的量词为个
    	bs.assertContains(FirstSuggestion, "个");
    	bs.click(Ajk_HomePage.FirstSuggestion, "点击第一个下拉联想");
    	//判断搜索词是否正确带入了
    	String suggestionKw = bs.getAttribute("//input[@id='global_search']", "value");
    	System.out.println(suggestionKw);
    	bs.assertContains(FirstSuggestion, suggestionKw);
	}
}
