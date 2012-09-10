package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;

/**
 * 该用例主要用来检查首页.二手房列表页.二手房列表页搜索广告
 * @UpdateAuthor Williamhu
 * @last updatetime  2012/05/08
 */

public class AnjukeCheckAds {
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

	//(timeOut = 120000)
	@Test(groups = {"unstable"})
	public void testSart() throws InterruptedException {
		checkCityHomePage("http://shanghai.anjuke.com");
		checkSaleListPage("http://shanghai.anjuke.com/sale/");
		checkSaleListSearchResultPage("http://shanghai.anjuke.com/sale/?kw=aaaa");
	}

	public void checkCityHomePage(String url) {
		Report.writeHTMLLog("*****检查广告-城市首页*****", "=================================================", "DONE", "");

		bs.get(url);

		checkAD("cityhomepage_right_google_ads_200*200", "城市首页-右侧google 200*200广告位");
		checkAD("cityhomepage_baidu_ads", "城市首页-左侧baidu长条广告位");
		checkAD("cityhomepage_google_ads_728*90", "城市首页-左侧google 728*90广告位");
	}

	public void checkSaleListPage(String url) {
		Report.writeHTMLLog("*****检查广告-二手房列表页*****", "=================================================", "DONE", "");
		bs.quit();
		bs = null;
		bs = FactoryBrowser.factoryBrowser();
		bs.get(url);
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!bs.check("salelist_right_baidu_ads1"))
		{
			bs.refresh();
		}
		checkAD("salelist_right_baidu_ads1", "二手房列表页-右侧baidu广告位1");
		checkAD("salelist_right_baidu_ads2", "二手房列表页-右侧baidu广告位2");
	}

	public void checkSaleListSearchResultPage(String url) {
		Report.writeHTMLLog("*****检查广告-二手房列表搜索结果页*****", "=================================================", "DONE", "");
		bs.get(url);
		checkAD("saleresultlist_right_baidu_ads1", "二手房列表搜索结果页-右侧baidu广告位1");
		checkAD("saleresultlist_right_baidu_ads2", "二手房列表搜索结果页-右侧baidu广告位2");
	}

	public void checkAD(String sID, String sDesc) {
		System.out.println(Init.G_objMap.get(sID));
		if (bs.getElementCount(Init.G_objMap.get(sID)) != 0) {
			Report.writeHTMLLog("检查广告", sDesc + "检查成功", "PASS", "");
		} else {
			String tmpPicName = bs.printScreen();
			Report.writeHTMLLog("检查广告", sDesc + "检查失败", "FAIL", tmpPicName);
		}
	}
}
