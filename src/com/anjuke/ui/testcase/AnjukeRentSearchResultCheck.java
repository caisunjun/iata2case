package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Ajk_PropView;
import com.anjuke.ui.page.Ajk_Sale;
import com.anjuke.ui.publicfunction.AjkRentalSearchAction;
import com.anjuke.ui.publicfunction.AjkSaleSearchAction;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;

/**
 * 该测试用例主要完成: 1、租房列表筛选条件结果验证 
 * 
 * @author ccyang
 * @time 2012-09-04 17:30
 **/

public class AnjukeRentSearchResultCheck {
	private Browser driver = null;
	AjkRentalSearchAction ars = null;
	
	@BeforeMethod
	public void setUp() {
		driver = FactoryBrowser.factoryBrowser();
		ars = new AjkRentalSearchAction(driver);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		driver = null;
	}
    @SuppressWarnings("deprecation")
	@Configuration(afterTestClass = true)
	public void doBeforeTests() {
		System.out.println("***AnjukeRentSearchResultCheck is done***");
	}

	@Test
	public void filterSearch() {
		driver.get("http://shanghai.anjuke.com/rental/");
		driver.click(ars.getLocater("浦东"), "点击浦东区域的筛选条件");
		driver.click(ars.getLocater("张江"), "点击张江板块的筛选条件");
		driver.click(ars.getLocater("1000-2000元"), "点击1000-2000的筛选条件");
		driver.click(ars.getLocater("一室"), "点击1室的筛选条件");

		driver.assertTrue(ars.verifyRegionBlock("浦东张江"), "筛选条件搜索","筛选结果中的房源区域板块是否正确");
		driver.assertTrue(ars.verifyPrice("1000-2000"), "筛选条件搜索","筛选结果中的房源总价是否正确");
		driver.assertTrue(ars.verifyRoom("1室"), "筛选条件搜索", "搜索结果中的房源户型是否正确");
	}
}
