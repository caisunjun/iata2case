package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjuke.ui.page.*;
import com.anjuke.ui.publicfunction.AjkSaleSearchAction;

/**
 * 该测试用例主要完成: 检测二手房列表页显示的发布时间是否正常
 * 
 * @author minzhao
 **/
public class AnjukeSalePropReleaseTime {
	Browser driver = null;

	@BeforeMethod
	public void setUp() {
		Report.G_CASECONTENT = "二手房列表页标签检测";
		driver = FactoryBrowser.factoryBrowser();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		driver = null;
	}

	@Test
	/* 打开二手房列表页（未按发布时间排序）,查看每一条房源是否包含发布时间 */
	public void testSaleReleaseTime() {
		driver.get("http://shanghai.anjuke.com/sale/");
		for (int val = 1; val <= driver.getElementCount(Ajk_Sale.LIST_COUNT); val++) {
			String tips = driver.getText(Ajk_Sale.PropReleaseTime(val), "获取发布时间模块");
			// 若房源下方包含发布或者更新，说明每条房源下方包含发布时间，否则不包含；
			if (!(tips.contains("发布") || tips.contains("更新"))) {
				Report.writeHTMLLog("验证第" + val + "房源下方是否存在房源发布时间", "不包含发布时间", Report.PASS, "");
			} else {
				Report.writeHTMLLog("验证第" + val + "房源下方是否存在房源发布时间", "包括发布时间", Report.FAIL, "");
			}
		}
	}
	
	@Test
	/* 打开二手房列表页（按发布时间排序）,查看第一条房源的发布时间是否为当天发布*/
	public void testSaleReleseLastest() {
		driver.get("http://shanghai.anjuke.com/sale/o5/");
		String tips = driver.getText(Ajk_Sale.PropReleaseTime(1), "获取发布时间模块");
		driver.assertContains(tips, "当天发布");
	}
}