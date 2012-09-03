package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;

/**
 * 该用例主要用来检查二手房小区详细页面一些标志是否显示正常
 * @UpdateAuthor Gabrielgao
 * @last updatetime 2012-05-04 17:55
 */

public class AnjukeCommunityPropertyMaster {
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
	@Test
	public void testStart() {
		//Report.setTCNameLog("检查主营小区-- AnjukeCommunityPropertyMaster --gabrielgao");
		checkCommPropMaster("http://shanghai.anjuke.com/v2/community/view/1550");
		checkCommPropMaster("http://shanghai.anjuke.com/v2/community/view/147");
		checkCommPropMaster("http://shanghai.anjuke.com/v2/community/view/27");
	}

	private void checkCommPropMaster(String url) {
		Report.writeHTMLLog("*****小区单页-主营小区*****", "=================================================", "DONE", "");

		String tmpCommunityNameObj = Init.G_objMap.get("community_text_title");
		String tmpCommPropMasterObj = Init.G_objMap.get("community_property_master");
		String tmpCommPropTabPriceObj = Init.G_objMap.get("community_tab_price");
		String tmpCommPriceMasterObj = Init.G_objMap.get("community_price_master");
		String tmpCommPriceGoldStoreObj = Init.G_objMap.get("community_price_goldstore");

		// 打开主营小区单页
		bs.get(url);

		// 获取小区名称
		String CommunityName = bs.getText(tmpCommunityNameObj, "获取小区名称");

		// 检查主营小区精选房源是否存在 
		int propCount = bs.getElementCount(tmpCommPropMasterObj);
		
		// 点击“价格行情”
		bs.click(tmpCommPropTabPriceObj, "点击【价格行情】");
		
		// 看价格行情页该门店人数，再判断小区首页是否应该有主营小区模块
		String brokerNum = bs.getText("//*[@id='content']/div/div[6]/div[1]/div[2]/ul/li[1]/a", "获得该主营门店当前经纪人数");
		String propNum = bs.getText("//*[@id='content']/div/div[6]/div[1]/div[2]/ul/li[2]/a", "获得该主营门店当前房源数");

		if (propCount!=0) {
			Report.writeHTMLLog("小区单页-精选房源-主营小区", "【" + CommunityName + "】小区单页-主营小区模块检查成功", "PASS", "");
		} else if(propNum.contains("0套"))
		{
			Report.writeHTMLLog("小区单页-精选房源-主营小区", "【" + CommunityName + "】小区单页-主营小区当前有 "+propNum+" 房源, 所以该小区首页没有主营小区模块", "PASS", "");}
		else
		{
			String tmpPicName = bs.printScreen();
			Report.writeHTMLLog("小区单页-精选房源-主营小区", "【" + CommunityName + "】小区单页-主营小区模块检查失败", "FAIL", tmpPicName);
		}
		if(brokerNum.contains("0人")&& !propNum.contains("0套"))
		{
			String ps = bs.printScreen();
			Report.writeHTMLLog("价格行情页-主营小区经纪人与房源数", "小区单页-主营小区模块数据出现异常 ：门店经纪人为“"+brokerNum+"”,门店房源数为“"+propNum+"”", "FAIL", ps);
		}

		// 检查价格行情页，右侧主营小区模块是否存在
		if (bs.getElementCount(tmpCommPriceMasterObj) != 0) {
			Report.writeHTMLLog("价格行情页-右侧金牌门店图标", "【" + CommunityName + "】价格行情页-右侧主营小区模块检查成功", "PASS", "");
		} else {
			String tmpPicName = bs.printScreen();
			Report.writeHTMLLog("价格行情页-右侧金牌门店图标", "【" + CommunityName + "】价格行情页-右侧主营小区模块检查失败", "FAIL", tmpPicName);
		}

		// 检查价格行情页，右侧金牌门店图标是否存在
		if (bs.getElementCount(tmpCommPriceGoldStoreObj) != 0) {
			Report.writeHTMLLog("价格行情页-右侧金牌门店图标", "【" + CommunityName + "】价格行情页-右侧金牌门店图标检查成功", "PASS", "");
		} else {
			String tmpPicName = bs.printScreen();
			Report.writeHTMLLog("价格行情页-右侧金牌门店图标", "【" + CommunityName + "】价格行情页-右侧金牌门店图标检查失败", "FAIL", tmpPicName);
		}
	}
}
