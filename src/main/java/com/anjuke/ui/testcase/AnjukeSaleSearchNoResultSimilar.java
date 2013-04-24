package com.anjuke.ui.testcase;

import com.anjuke.ui.page.Ajk_Sale;
import com.anjuke.ui.publicfunction.AjkSaleSearchAction;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;

/**
 * 该测试用例主要完成: 
 * 1、搜索无结果后进入删词逻辑
 * 2、页面给出删词后的新搜索词
 * 3、翻页后仍是按新搜索词进行搜索
 * 
 * @author ccyang
 * @time 2013-3-14 13:52:59
 **/

public class AnjukeSaleSearchNoResultSimilar {
	private Browser driver = null;
	AjkSaleSearchAction ass = null;

	@BeforeMethod
	public void setUp() {
		Report.G_CASECONTENT = "二手房列表页搜索 删词逻辑检测";
		driver = FactoryBrowser.factoryBrowser();
		ass = new AjkSaleSearchAction(driver);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		driver = null;
	}
    @SuppressWarnings("deprecation")
	@Configuration(afterTestClass = true)
	public void doBeforeTests() {
		System.out.println("***AnjukeSaleSearchNoResultSimilar is done***");
	}
    
	@Test
	public void keywordSearch() {
		//进入删词逻辑的搜索关键词由两部分组成，有结果的可被结构化的词+意义互斥的部分
		String keywordHasResult = "中远两湾城";
		String keywordNoResult = "防空洞";
		String searchKeyword = keywordHasResult + keywordNoResult;
		driver.get("http://shanghai.anjuke.com/sale/");
		
		driver.type(Ajk_Sale.KwInput, searchKeyword, "输入关键词：" + searchKeyword);
		driver.click(Ajk_Sale.KwSubmit, "点击找房子");
		//删词提示是否正常出现
		String searchTips = driver.getText("//div[@class='similar_keywords search_tips']","获得删词逻辑的提示");
		//验证提示语内容是否正确
		driver.assertContains(searchTips, "“"+searchKeyword+"”"+"没有找到房源，");
		driver.assertContains(searchTips, "为您找到了“"+keywordHasResult+"”的房源");
		//搜索结果的小区是否为删词后的关键词
		driver.assertTrue(ass.verifyDistrict(keywordHasResult), "删词逻辑搜索结果","第一页搜索结果中的房源小区是否正确");
		int resultNumber = Integer.parseInt(searchTips.replaceAll("\\D", ""));
		if(resultNumber>25){
			checkP2(driver);
			driver.assertTrue(ass.verifyDistrict(keywordHasResult), "删词逻辑搜索结果","第二页搜索结果中的房源小区是否正确");
		}
	}
	
	private void checkP2(Browser driver){
		int listCount = 0;
		String currentP1 = "";
		String currentP2 = "";
		//翻页检查
		driver.click("//div[@class='pagelink nextpage']", "翻到第二页");
		if(driver.check("//div[@class='current']")){
			//获得页码
			currentP2 = driver.getText("//div[@class='current']", "获得第二页页码");
			driver.click("//div[@class='pagelink prexpage']", "翻回第一页再确认下");
			currentP1 = driver.getText("//div[@class='current']", "获得第一页页码");
			//确认第二页页码正确性
			if(!currentP2.equals(currentP1.replace("1/", "2/"))){
				Report.writeHTMLLog("二手房列表页翻页检查", "第二页页码不对", Report.FAIL, "");
			}
		}
		else{
			String ps = driver.printScreen();
			Report.writeHTMLLog("二手房列表页翻页检查", "第二页页码找不到", Report.FAIL, ps);
		}
		driver.click("//div[@class='pagelink nextpage']", "再翻到第二页");
		try {
			//获得当前列表房源数
			listCount = driver.getElementCount("//ol[@id='list_body']/li");
			if(listCount == 0)
			{
				String ps = driver.printScreen();
				System.out.println("**********二手房列表页第二页没有房源");
				Report.writeHTMLLog("二手房列表页翻页检查", "第二页没房源", Report.FAIL, ps);
			}
		} catch (NullPointerException e) {
			String ps = driver.printScreen();
			System.out.println("*******************二手房列表页第二页没有房源");
			Report.writeHTMLLog("二手房列表页翻页检查", "第二页没房源", Report.FAIL, ps);
		}
	}
}
