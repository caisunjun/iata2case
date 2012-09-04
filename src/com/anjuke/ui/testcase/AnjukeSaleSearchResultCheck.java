package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.publicfunction.AjkSearchResultCheck;
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
	AjkSearchResultCheck ass = null;

	@BeforeMethod
	public void setUp() {
		driver = FactoryBrowser.factoryBrowser();
		ass = new AjkSearchResultCheck(driver);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		driver = null;
	}

	@Test
	public void siftSearch() {
		driver.get("http://shanghai.anjuke.com/sale/");
		driver.click("//div[@id='apf_id_13_areacontainer']/a[1]",
				"点击浦东区域的筛选条件");
		driver.click("//div[@id='apf_id_13_blockcontainer']/a[1]",
				"点击碧云板块的筛选条件");
		driver.click("//div[@id='panel_apf_id_13']/div[3]/div[2]/div[1]/a[7]",
				"点击200-250万元的筛选条件");
		driver.click("//div[@id='panel_apf_id_13']/div[4]/div[2]/a[4]",
				"点击90-110平米的筛选条件");
		driver.click("//div[@id='panel_apf_id_13']/div[5]/div[2]/a[2]",
				"点击2室的筛选条件");

		driver.assertTrue(ass.verifyRegionBlock("浦东碧云"), "筛选条件搜索",
				"搜索结果中的房源区域板块是否正确");
		driver.assertTrue(ass.verifyPrice("200-250万元"), "筛选条件搜索",
				"搜索结果中的房源总价是否正确");
		driver.assertTrue(ass.verifyArea("90-110平米"), "筛选条件搜索",
				"搜索结果中的房源面积是否正确");
		driver.assertTrue(ass.verifyRoom("2室"), "筛选条件搜索", "搜索结果中的房源户型是否正确");
	}

	@Test
	public void keywordSearch() {
		driver.get("http://shanghai.anjuke.com/sale/");
		driver.type("//*[@id='keyword_apf_id_9']", "北新泾2室", "输入长宁");
		driver.click(".//*[@id='sbtn2_apf_id_9']", "点击长宁");
		
		driver.assertTrue(ass.verifyKeyBlock("北新泾"), "列表页Keyword搜索结果",
				"搜索结果中的房源板块是否正确");
		driver.assertTrue(ass.verifyKeyRoom("2室"), "列表页Keyword搜索结果",
				"搜索结果中的房源户型是否正确");
	}
}
