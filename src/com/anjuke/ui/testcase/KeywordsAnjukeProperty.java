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
 * 该测试用例主要完成安居客关键字检查操作，逻辑如下
 * 1、遍历所有的安居客城市
 * 2、二手房列表页敏感词检查
 * 3、租房列表页敏感词检查
 * 4、小区列表页敏感词检查
 * 5、经纪人列表页敏感词检查
 * @updateAuthor gabrielgao
 * @last updatetime 2012-05-08 17:00
 */
public class KeywordsAnjukeProperty {
	private Browser bs = null;

	@BeforeMethod
	public void setUp() {
		bs = FactoryBrowser.factoryBrowser();
	}

	@AfterMethod
	public void tearDown() {
		bs.quit();
		bs=null;
	}

	//(timeOut=600000)
	@Test(groups = {"unstable"})
	public void testSart() throws InterruptedException,
			UnsupportedEncodingException {

		//Report.setTCNameLog("检查二手房/租房列表页敏感词-- KeywordsAnjukeProperty --gabrielgao");


		String url = "";
		int dataCount = 0;
		// 遍历敏感词
		Map<String, String> keywordsMap = LogFile.getConfigInfo("keywords");
		Iterator<Entry<String, String>> keywordsIter = keywordsMap.entrySet()
				.iterator();
		while (keywordsIter.hasNext()) {
			Map.Entry<String, String> keywordsEntry = keywordsIter.next();
			Object keywordsVal = keywordsEntry.getValue();
			// System.out.println(val);

			// 遍历城市
			Map<String, String> cityMap = LogFile
					.getConfigInfo("anjukeCityInfo");
			Iterator<Entry<String, String>> cityInfoIter = cityMap.entrySet()
					.iterator();
			while (cityInfoIter.hasNext()) {
				Map.Entry<String, String> cityEntry = (Map.Entry<String, String>) cityInfoIter.next();
				Object cityKey = cityEntry.getKey();
				Object cityVal = cityEntry.getValue();

				// 检查二手房列表页搜索敏感词
				url = "http://" + cityVal + ".anjuke.com/sale/rd1/?kw="
						+ URLEncoder.encode((String) keywordsVal, "UTF-8");

				bs.get(url);

				if (bs.check(Init.G_objMap.get("sale_text_no_found"))) {
					Report.writeHTMLLog("检查安居客敏感词-二手房列表页", "未搜索到任何结果的页面元素成功找到",
							Report.PASS, "");
				} else {
					String ps = bs.printScreen();
					Report.writeHTMLLog("检查安居客敏感词-二手房列表页",
							"未搜索到任何结果的页面元素未找到，尝试刷新页面", "", ps);

					// 尝试刷新当前页面
					bs.refreshPage();
				}

				// 获取二手房列表页无结果字符串元素个数
				dataCount = bs.getElementCount(
						Init.G_objMap.get("sale_text_no_found"), 5);
				if (dataCount == 0) {
					String tmpPicName = bs.printScreen();
					Report.writeHTMLLog("检查安居客敏感词-二手房列表页", "二手房列表页【" + cityKey
							+ "】敏感词【" + keywordsVal + "】发现记录", "FAIL",
							tmpPicName);
				} else {
					Report.writeHTMLLog("检查安居客敏感词-二手房列表页", "二手房列表页【" + cityKey
							+ "】敏感词【" + keywordsVal + "】查无结果", "PASS", "");
				}

				// 检查租房列表页搜索敏感词
				url = "http://" + cityVal + ".anjuke.com/rental/rd1?kw="
						+ keywordsVal;

				bs.get(url);

				if (bs.check(Init.G_objMap.get("rental_text_no_found"))) {
					Report.writeHTMLLog("检查安居客敏感词-租房列表页", "未搜索到任何结果的页面元素成功找到",
							Report.PASS, "");
				} else {
					String ps = bs.printScreen();
					Report.writeHTMLLog("检查安居客敏感词-租房列表页",
							"未搜索到任何结果的页面元素未找到，尝试刷新页面", "", ps);

					// 尝试刷新当前页面
					bs.refreshPage();
				}

				// 获取租房列表页无结果字符串元素个数
				dataCount = bs.getElementCount(
						Init.G_objMap.get("rental_text_no_found"), 5);
				if (dataCount == 0) {
					String tmpPicName = bs.printScreen();
					Report.writeHTMLLog("检查安居客敏感词-租房列表页", "租房列表页【" + cityKey
							+ "】敏感词【" + keywordsVal + "】发现记录", "FAIL",
							tmpPicName);
				} else {
					Report.writeHTMLLog("检查安居客敏感词-租房列表页", "租房列表页【" + cityKey
							+ "】敏感词【" + keywordsVal + "】查无结果", "PASS", "");
				}

				// 检查小区列表页搜索敏感词
				if (cityVal.equals("shanghai") || cityVal.equals("beijing")) {
					url = "http://" + cityVal
							+ ".anjuke.com/community/list/W0QQkwZ"
							+ keywordsVal;
					bs.get(url);
					// 获取小区列表页无结果字符串元素个数
					if (bs.check(Init.G_objMap
							.get("anjuke_community_text_no_found_2"))) {
						Report.writeHTMLLog("检查安居客敏感词-小区列表页",
								"未搜索到任何结果的页面元素成功找到", Report.PASS, "");
					} else {
						String ps = bs.printScreen();
						Report.writeHTMLLog("检查安居客敏感词-小区列表页", "未搜索到任何结果的页面元素未找到，尝试刷新页面", Report.DONE,ps);

						// 尝试刷新当前页面
						bs.refreshPage();
					}
					dataCount = bs.getElementCount(Init.G_objMap
							.get("anjuke_community_text_no_found_2"), 5);

				} else {
					url = "http://" + cityVal + ".anjuke.com/community/list/"
							+ keywordsVal + "W0QQrdZ1";
					bs.get(url);
					// 获取小区列表页无结果字符串元素个数
					if (bs.check(Init.G_objMap
							.get("anjuke_community_text_no_found"))) {
						Report.writeHTMLLog("检查安居客敏感词-小区列表页",
								"未搜索到任何结果的页面元素成功找到", Report.PASS, "");
					} else {
						String ps = bs.printScreen();
						Report.writeHTMLLog("检查安居客敏感词-小区列表页",
								"未搜索到任何结果的页面元素未找到，尝试刷新页面", "", ps);

						// 尝试刷新当前页面
						bs.refreshPage();
					}
					dataCount = bs
							.getElementCount(Init.G_objMap
									.get("anjuke_community_text_no_found"), 5);

				}

				if (dataCount == 0) {
					String tmpPicName = bs.printScreen();
					Report.writeHTMLLog("检查安居客敏感词-小区列表页", "小区列表页【" + cityKey
							+ "】敏感词【" + keywordsVal + "】发现记录", "FAIL",
							tmpPicName);
				} else {
					Report.writeHTMLLog("检查安居客敏感词-小区列表页", "小区列表页【" + cityKey
							+ "】敏感词【" + keywordsVal + "】查无结果", "PASS", "");
				}

				// 检查经纪人列表页搜索敏感词
				url = "http://" + cityVal
						+ ".anjuke.com/tycoon/W0QQtxtKeywordZ" + keywordsVal;
				bs.get(url);

				if (bs.check(Init.G_objMap.get("anjuke_broker_no_found"))) {
					Report.writeHTMLLog("检查安居客敏感词-经纪人列表页", "未搜索到任何结果的页面元素成功找到",
							Report.PASS, "");
				} else {
					String ps = bs.printScreen();
					Report.writeHTMLLog("检查安居客敏感词-经纪人列表页",
							"未搜索到任何结果的页面元素未找到，尝试刷新页面", "", ps);

					// 尝试刷新当前页面
					bs.refreshPage();
				}

				// 获取经纪人列表页无结果字符串元素个数
				dataCount = bs.getElementCount(
						Init.G_objMap.get("anjuke_broker_no_found"), 5);
				if (dataCount == 0) {
					String tmpPicName = bs.printScreen();
					Report.writeHTMLLog("检查安居客敏感词-经纪人列表页", "经纪人列表页【" + cityKey
							+ "】敏感词【" + keywordsVal + "】发现记录", "FAIL",
							tmpPicName);
				} else {
					Report.writeHTMLLog("检查安居客敏感词-经纪人列表页", "经纪人列表页【" + cityKey
							+ "】敏感词【" + keywordsVal + "】查无结果", "PASS", "");
				}

				// 检查问答列表页搜索敏感词
				/*
				 * url = "http://" + cityVal + ".anjuke.com/ask/W0QQtagsZ" +
				 * keywordsVal; bs.get(url);
				 * 
				 * //获取问答列表页无结果字符串元素个数
				 * dataCount=bs.getElementCount(Init.G_objMap
				 * .get("anjuke_interlocution_text_no_found"),5); if (dataCount
				 * == 0) { String tmpPicName = bs.printScreen();
				 * Report.writeHTMLLog("检查安居客敏感词-问答列表页",
				 * "问答列表页【"+cityKey+"】敏感词【"+keywordsVal+"】发现记录", "FAIL",
				 * tmpPicName); } else { Report.writeHTMLLog("检查安居客敏感词-问答列表页",
				 * "问答列表页【"+cityKey+"】敏感词【"+keywordsVal+"】查无结果", "PASS", ""); }
				 */

				/*
				 * if(!cityVal.equals("shanghai") &&
				 * !cityVal.equals("beijing")&& !cityVal.equals("chongqing")){
				 * bs.get("http://" + cityVal + ".bbs.anjuke.com/");
				 * 
				 * // 论坛 String keyWords = keywordsVal.toString();
				 * bs.type(Init.G_objMap.get("anjuke_luntan_keywords_input"),
				 * keyWords, "论坛输入关键字"+keyWords, 5);
				 * bs.click(Init.G_objMap.get("anjuke_searchBtn_click"), "找帖子");
				 * 
				 * // 获取论坛列表页无结果字符串元素个数 dataCount =
				 * bs.getElementCount(Init.G_objMap
				 * .get("anjuke_luntan_keywords_no_found"),5); if (dataCount ==
				 * 0) { String tmpPicName = bs.printScreen();
				 * Report.writeHTMLLog
				 * ("检查安居客敏感词-论坛搜索列表页","论坛搜索列表页【"+cityKey+"】敏感词【" + keywordsVal
				 * + "】发现记录","FAIL", tmpPicName); } else {
				 * Report.writeHTMLLog("检查安居客敏感词-论坛搜索列表页"
				 * ,"论坛搜索列表页【"+cityKey+"】敏感词【" + keywordsVal + "】查无结果", "PASS",
				 * ""); } }
				 */
			}

		}

	}
}
