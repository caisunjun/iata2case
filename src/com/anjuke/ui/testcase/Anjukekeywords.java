package com.anjuke.ui.testcase;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.LogFile;
import com.anjukeinc.iata.ui.report.Report;

/**
 * @Todo : TODO
 * @author : fjzhang
 * @filename : Anjukekeywords.java
 * @date : 2012-9-10
 */

public class Anjukekeywords {
	Browser bs = null;

	@BeforeMethod
	public void setUp() {
		bs = FactoryBrowser.htmlBrowser();
	}

	@AfterMethod
	public void teardown() {
		Report.seleniumReport("", "");
		bs.quit();
		bs = null;
	}

	@Test (groups = "{unstable}")
	public void testlistpage() throws UnsupportedEncodingException {
		String url = "";
		int dataCount = 0;
		// 设定要跑几个城市
		
		int cityNum = 5;
		// 遍历敏感词
		Map<String, String> keywordsMap = LogFile.getConfigInfo("keywords");
		Iterator<Entry<String, String>> keywordsIter = keywordsMap.entrySet()
				.iterator();
		while (keywordsIter.hasNext()) {
			int now = 0;
			Map.Entry<String, String> keywordsEntry = keywordsIter.next();
			Object keywordsVal = keywordsEntry.getValue();
			Object keywordsURL = keywordsEntry.getValue();
			System.out.println(keywordsVal + "--" + keywordsURL);

			// 遍历城市
			Map<String, String> cityMap = LogFile
					.getConfigInfo("anjukeCityInfo");
			Iterator<Entry<String, String>> cityInfoIter = cityMap.entrySet()
					.iterator();
			while (cityInfoIter.hasNext() && now < cityNum) {
				Map.Entry<String, String> cityEntry = (Map.Entry<String, String>) cityInfoIter
						.next();
				Object cityKey = cityEntry.getKey();
				Object cityVal = cityEntry.getValue();
				now = now + 1;
				// 检查二手房列表页搜索敏感词
				url = "http://" + cityVal + ".anjuke.com/sale/rd1/?kw="
						+ URLEncoder.encode((String) keywordsURL, "UTF-8");

				bs.get(url);
				System.out.println(url);
				if (bs.check(Init.G_objMap.get("sale_text_no_found"))) {
					Report.writeHTMLLog("检查安居客敏感词-二手房列表页", "未搜索到任何结果的页面元素成功找到",
							Report.PASS, "");
				} else {
					//String ps = bs.printScreen();
					Report.writeHTMLLog("检查安居客敏感词-二手房列表页",
							"未搜索到任何结果的页面元素未找到，尝试刷新页面", "", "");

					// 尝试刷新当前页面
					bs.refreshPage();
				}

				// 获取二手房列表页无结果字符串元素个数
				dataCount = bs.getElementCount(
						Init.G_objMap.get("sale_text_no_found"), 5);
				if (dataCount == 0) {
					//String tmpPicName = bs.printScreen();
					Report.writeHTMLLog("检查安居客敏感词-二手房列表页", "二手房列表页【" + cityKey
							+ "】敏感词【" + keywordsVal + "】发现记录", "FAIL",
							"");
				} else {
					Report.writeHTMLLog("检查安居客敏感词-二手房列表页", "二手房列表页【" + cityKey
							+ "】敏感词【" + keywordsVal + "】查无结果", "PASS", "");
				}
			}
		}
	}

	@Test (groups = "{unstable}")
	public void testRentlist() throws UnsupportedEncodingException {
		String url = "";
		int dataCount = 0;
		// 设定要跑几个城市
		int now = 0;
		int cityNum = 5;
		// 遍历敏感词
		Map<String, String> keywordsMap = LogFile.getConfigInfo("keywords");
		Iterator<Entry<String, String>> keywordsIter = keywordsMap.entrySet()
				.iterator();
		while (keywordsIter.hasNext()) {
			Map.Entry<String, String> keywordsEntry = keywordsIter.next();
			Object keywordsVal = keywordsEntry.getValue();
			Object keywordsURL = keywordsEntry.getValue();
			// System.out.println(val);

			// 遍历城市
			Map<String, String> cityMap = LogFile
					.getConfigInfo("anjukeCityInfo");
			Iterator<Entry<String, String>> cityInfoIter = cityMap.entrySet()
					.iterator();
			while (cityInfoIter.hasNext() && now < cityNum) {
				Map.Entry<String, String> cityEntry = (Map.Entry<String, String>) cityInfoIter
						.next();
				Object cityKey = cityEntry.getKey();
				Object cityVal = cityEntry.getValue();
				now = now + 1;
				// 检查租房列表页搜索敏感词
				url = "http://" + cityVal + ".anjuke.com/rental/rd1?kw="
						+ URLEncoder.encode((String) keywordsURL, "UTF-8");

				bs.get(url);

				if (bs.check(Init.G_objMap.get("rental_text_no_found"))) {
					Report.writeHTMLLog("检查安居客敏感词-租房列表页", "未搜索到任何结果的页面元素成功找到",
							Report.PASS, "");
				} else {
					//String ps = bs.printScreen();
					Report.writeHTMLLog("检查安居客敏感词-租房列表页",
							"未搜索到任何结果的页面元素未找到，尝试刷新页面", "", "");

					// 尝试刷新当前页面
					bs.refreshPage();
				}

				// 获取租房列表页无结果字符串元素个数
				dataCount = bs.getElementCount(
						Init.G_objMap.get("rental_text_no_found"), 5);
				if (dataCount == 0) {
					//String tmpPicName = bs.printScreen();
					Report.writeHTMLLog("检查安居客敏感词-租房列表页", "租房列表页【" + cityKey
							+ "】敏感词【" + keywordsVal + "】发现记录", "FAIL",
							"");
				} else {
					Report.writeHTMLLog("检查安居客敏感词-租房列表页", "租房列表页【" + cityKey
							+ "】敏感词【" + keywordsVal + "】查无结果", "PASS", "");
				}
			}
		}
	}

	@Test (groups = "{unstable}")
	public void testcommunity() throws UnsupportedEncodingException {
		String url = "";
		int dataCount = 0;
		// 设定要跑几个城市
		int now = 0;
		int cityNum = 5;
		// 遍历敏感词
		Map<String, String> keywordsMap = LogFile.getConfigInfo("keywords");
		Iterator<Entry<String, String>> keywordsIter = keywordsMap.entrySet()
				.iterator();
		while (keywordsIter.hasNext()) {
			Map.Entry<String, String> keywordsEntry = keywordsIter.next();
			Object keywordsVal = keywordsEntry.getValue();
			Object keywordsURL = keywordsEntry.getValue();
			// System.out.println(val);

			// 遍历城市
			Map<String, String> cityMap = LogFile
					.getConfigInfo("anjukeCityInfo");
			Iterator<Entry<String, String>> cityInfoIter = cityMap.entrySet()
					.iterator();
			while (cityInfoIter.hasNext() && now < cityNum) {
				Map.Entry<String, String> cityEntry = (Map.Entry<String, String>) cityInfoIter
						.next();
				Object cityKey = cityEntry.getKey();
				Object cityVal = cityEntry.getValue();
				now = now + 1;
				// 检查小区列表页搜索敏感词
				if (cityVal.equals("shanghai") || cityVal.equals("beijing")) {
					url = "http://" + cityVal
							+ ".anjuke.com/community/list/W0QQkwZ"
							+ URLEncoder.encode((String) keywordsURL, "UTF-8");
					bs.get(url);
					// 获取小区列表页无结果字符串元素个数
					if (bs.check(Init.G_objMap
							.get("anjuke_community_text_no_found_2"))) {
						Report.writeHTMLLog("检查安居客敏感词-小区列表页",
								"未搜索到任何结果的页面元素成功找到", Report.PASS, "");
					} else {
						//String ps = bs.printScreen();
						Report.writeHTMLLog("检查安居客敏感词-小区列表页",
								"未搜索到任何结果的页面元素未找到，尝试刷新页面", Report.DONE, "");

						// 尝试刷新当前页面
						bs.refreshPage();
					}
					dataCount = bs.getElementCount(Init.G_objMap
							.get("anjuke_community_text_no_found_2"), 5);

				} else {
					url = "http://" + cityVal + ".anjuke.com/community/list/"
							+ URLEncoder.encode((String) keywordsURL, "UTF-8")
							+ "W0QQrdZ1";
					bs.get(url);
					// 获取小区列表页无结果字符串元素个数
					if (bs.check(Init.G_objMap
							.get("anjuke_community_text_no_found"))) {
						Report.writeHTMLLog("检查安居客敏感词-小区列表页",
								"未搜索到任何结果的页面元素成功找到", Report.PASS, "");
					} else {
						//String ps = bs.printScreen();
						Report.writeHTMLLog("检查安居客敏感词-小区列表页",
								"未搜索到任何结果的页面元素未找到，尝试刷新页面", "", "");

						// 尝试刷新当前页面
						bs.refreshPage();
					}
					dataCount = bs
							.getElementCount(Init.G_objMap
									.get("anjuke_community_text_no_found"), 5);

				}

				if (dataCount == 0) {
					//String tmpPicName = bs.printScreen();
					Report.writeHTMLLog("检查安居客敏感词-小区列表页", "小区列表页【" + cityKey
							+ "】敏感词【" + keywordsVal + "】发现记录", "FAIL",
							"");
				} else {
					Report.writeHTMLLog("检查安居客敏感词-小区列表页", "小区列表页【" + cityKey
							+ "】敏感词【" + keywordsVal + "】查无结果", "PASS", "");
				}
			}
		}
	}
	@Test (groups = "{unstable}")
	public void testvip() throws UnsupportedEncodingException{
		String url = "";
		int dataCount = 0;
		// 设定要跑几个城市
		int now = 0;
		int cityNum = 5;
		// 遍历敏感词
		Map<String, String> keywordsMap = LogFile.getConfigInfo("keywords");
		Iterator<Entry<String, String>> keywordsIter = keywordsMap.entrySet()
				.iterator();
		while (keywordsIter.hasNext()) {
			Map.Entry<String, String> keywordsEntry = keywordsIter.next();
			Object keywordsVal = keywordsEntry.getValue();
			Object keywordsURL = keywordsEntry.getValue();
			// System.out.println(val);

			// 遍历城市
			Map<String, String> cityMap = LogFile
					.getConfigInfo("anjukeCityInfo");
			Iterator<Entry<String, String>> cityInfoIter = cityMap.entrySet()
					.iterator();
			while (cityInfoIter.hasNext() && now < cityNum) {
				Map.Entry<String, String> cityEntry = (Map.Entry<String, String>) cityInfoIter
						.next();
				Object cityKey = cityEntry.getKey();
				Object cityVal = cityEntry.getValue();
				now = now + 1;
		// 检查经纪人列表页搜索敏感词
		url = "http://" + cityVal
				+ ".anjuke.com/tycoon/W0QQtxtKeywordZ" + URLEncoder.encode((String) keywordsURL, "UTF-8");
		bs.get(url);

		if (bs.check(Init.G_objMap.get("anjuke_broker_no_found"))) {
			Report.writeHTMLLog("检查安居客敏感词-经纪人列表页", "未搜索到任何结果的页面元素成功找到",
					Report.PASS, "");
		} else {
			Report.writeHTMLLog("检查安居客敏感词-经纪人列表页",
					"未搜索到任何结果的页面元素未找到，尝试刷新页面", "", "");

			// 尝试刷新当前页面
			bs.refreshPage();
		}

		// 获取经纪人列表页无结果字符串元素个数
		dataCount = bs.getElementCount(
				Init.G_objMap.get("anjuke_broker_no_found"), 5);
		if (dataCount == 0) {
			//String tmpPicName = bs.printScreen();
			Report.writeHTMLLog("检查安居客敏感词-经纪人列表页", "经纪人列表页【" + cityKey
					+ "】敏感词【" + keywordsVal + "】发现记录", "FAIL",
					"");
		} else {
			Report.writeHTMLLog("检查安居客敏感词-经纪人列表页", "经纪人列表页【" + cityKey
					+ "】敏感词【" + keywordsVal + "】查无结果", "PASS", "");
		}
	}
}
}}