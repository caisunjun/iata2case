package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Ajk_Sale;
import com.anjuke.ui.publicfunction.AjkSaleSearchAction;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;

/**
 * 该测试用例主要完成: 
 * 1、检查翻页后页码及页面房源数是否正确
 * 2、筛选条件结果验证 
 * 3、区域、板块、户型的keyword搜索结果验证
 * 
 * @author Grayhu
 * @time 2012-09-04 17:30
 **/

public class AnjukeSaleSearchResultCheck {
	private Browser driver = null;
	AjkSaleSearchAction ass = null;

	@BeforeMethod
	public void setUp() {
		Report.G_CASECONTENT = "二手房列表页筛选 搜索功能检测";
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
		System.out.println("***AnjukeSaleSearchResultCheck is done***");
	}

	@Test
	public void filterSearch() {
		driver.get("http://shanghai.anjuke.com/sale/");
		
		checkP2(driver);
		
		driver.click(ass.getLocater("浦东"), "点击浦东区域的筛选条件");
		driver.click(ass.getLocater("碧云"), "点击碧云板块的筛选条件");
		driver.click(ass.getLocater("500-1000万"), "点击500-1000万的筛选条件");
		driver.click(ass.getLocater("150-200平米"), "点击150-200平米的筛选条件");
		driver.click(ass.getLocater("三室"), "点击3室的筛选条件");

		driver.assertTrue(ass.verifyRegionBlock("浦东碧云"), "筛选条件搜索",
				"搜索结果中的房源区域板块是否正确");
		driver.assertTrue(ass.verifyPrice("500-1000万"), "筛选条件搜索",
				"搜索结果中的房源总价是否正确");
		driver.assertTrue(ass.verifyArea("150-200平米"), "筛选条件搜索",
				"搜索结果中的房源面积是否正确");
		driver.assertTrue(ass.verifyRoom("3室"), "筛选条件搜索", "搜索结果中的房源户型是否正确");
	}

	@Test
	public void keywordSearch() {
		driver.get("http://shanghai.anjuke.com/sale/");
		driver.type(Ajk_Sale.KwInput, "北新泾2室", "输入查询条件：北新泾2室");
		driver.click(Ajk_Sale.KwSubmit, "点击找房子");

		driver.assertTrue(ass.verifyKeyBlock("北新泾"), "列表页Keyword搜索结果",
				"搜索结果中的房源板块是否正确");
		driver.assertTrue(ass.verifyKeyRoom("2室"), "列表页Keyword搜索结果",
				"搜索结果中的房源户型是否正确");
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
