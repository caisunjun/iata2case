package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;
import com.anjuke.ui.publicfunction.AjkSaleSearchAction;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;

/**
 * 该测试用例主要完成: 1、筛选条件结果验证 2、区域、板块、户型的keyword搜索结果验证
 * 
 * @author Grayhu
 * @time 2012-09-04 17:30
 **/

public class AnjukeSaleSearchResultCheck {
	private Browser driver = null;
	AjkSaleSearchAction ass = null;

	@BeforeMethod
	public void setUp() {
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
		driver.click(ass.getLocater("浦东"), "点击浦东区域的筛选条件");
		driver.click(ass.getLocater("碧云"), "点击碧云板块的筛选条件");
		driver.click(ass.getLocater("200-250万"), "点击200-250万的筛选条件");
		driver.click(ass.getLocater("90-110平米"), "点击90-110平米的筛选条件");
		driver.click(ass.getLocater("二室"), "点击2室的筛选条件");

		driver.assertTrue(ass.verifyRegionBlock("浦东碧云"), "筛选条件搜索",
				"搜索结果中的房源区域板块是否正确");
		driver.assertTrue(ass.verifyPrice("200-250万"), "筛选条件搜索",
				"搜索结果中的房源总价是否正确");
		driver.assertTrue(ass.verifyArea("90-110平米"), "筛选条件搜索",
				"搜索结果中的房源面积是否正确");
		driver.assertTrue(ass.verifyRoom("2室"), "筛选条件搜索", "搜索结果中的房源户型是否正确");
	}

	// @Test
	public void keywordSearch() {
		driver.get("http://shanghai.anjuke.com/sale/");
		driver.type("//*[@id='keyword_apf_id_9']", "北新泾2室", "输入查询条件：北新泾2室");
		driver.click(".//*[@id='sbtn2_apf_id_9']", "点击找房子");

		driver.assertTrue(ass.verifyKeyBlock("北新泾"), "列表页Keyword搜索结果",
				"搜索结果中的房源板块是否正确");
		driver.assertTrue(ass.verifyKeyRoom("2室"), "列表页Keyword搜索结果",
				"搜索结果中的房源户型是否正确");
	}
}
