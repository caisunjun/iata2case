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
import com.anjukeinc.iata.ui.report.Report;

/**
 * 该测试用例主要完成: 
 * 1、检查翻页后页码及页面房源数是否正确
 * 2、租房列表筛选条件结果验证 
 * @author ccyang
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
		
		checkP2(driver);
		
		driver.click(ars.getLocater("浦东"), "点击浦东区域的筛选条件");
		driver.click(ars.getLocater("张江"), "点击张江板块的筛选条件");
		driver.click(ars.getLocater("1000-2000元"), "点击1000-2000的筛选条件");
		driver.click(ars.getLocater("一室"), "点击1室的筛选条件");

		driver.assertTrue(ars.verifyRegionBlock("浦东张江"), "筛选条件搜索","筛选结果中的房源区域板块是否正确");
		driver.assertTrue(ars.verifyPrice("1000-2000"), "筛选条件搜索","筛选结果中的房源总价是否正确");
		driver.assertTrue(ars.verifyRoom("1室"), "筛选条件搜索", "搜索结果中的房源户型是否正确");
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
				Report.writeHTMLLog("租房列表页翻页检查", "第二页页码不对", Report.FAIL, "");
			}
		}
		else{
			String ps = driver.printScreen();
			Report.writeHTMLLog("租房列表页翻页检查", "第二页页码找不到", Report.FAIL, ps);
		}
		driver.click("//div[@class='pagelink nextpage']", "再翻到第二页");
		try {
			//获得当前列表房源数
			listCount = driver.getElementCount("//ol[@id='list_body']/li");
			if(listCount == 0)
			{
				String ps = driver.printScreen();
				System.out.println("**********租房列表页第二页没有房源");
				Report.writeHTMLLog("租房列表页翻页检查", "第二页没房源", Report.FAIL, ps);
			}
			
		} catch (NullPointerException e) {
			String ps = driver.printScreen();
			System.out.println("*******************租房列表页第二页没有房源");
			Report.writeHTMLLog("租房列表页翻页检查", "第二页没房源", Report.FAIL, ps);
		}
	}
}
