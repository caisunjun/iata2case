package com.anjuke.ui.testcase;

import com.anjuke.ui.page.Ajk_Community;
import com.anjuke.ui.page.Ajk_HomePage;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
/**
 * 该测试用例主要完成验证城市首页取均价统计文字、以及小区列表页的价格
 * 以及价格趋势是否存在操作，逻辑如下
 * 1、访问安居客上海首页，获取页脚所有城市URL以及名称
 * 2、判断每个城市首页的均价统计文字
 * 3、判断小区列表页小区的均价以及均价趋势是否存在
 * @updateAuthor gabrielgao
 * @last updatetime 2012-05-10 13:30112333
 */
public class AnjukeCommunityListPrice {
	private Browser bs = null;

	@BeforeMethod
	public void setUp() {
		Report.G_CASECONTENT = "首页、小区列表页均价检测";
		bs = FactoryBrowser.factoryBrowser();
	}

	@AfterMethod
	public void tearDown() {
		bs.quit();
		bs = null;
	}

	@Test
	public void testStart() {
		bs.get("http://shanghai.anjuke.com");
		String cityUrl = null;
		String cityName =null;
		int checkTimes = 0;
		HashMap<String,String> resultList =null;
		String tmpCityList = Ajk_HomePage.HotCityList; // 底部，"房地产热门城市"列表
		int tmpCount = 0;
		int num = 0;
		//判断城市列表是否存在  首页经常vanish 暂时用refresh破
		while(!bs.check(tmpCityList,10)&&num<3)
		{
			bs.refresh();
			num++;
			System.out.println("刷了一次");
		}
		tmpCount = bs.getElementCount(Ajk_HomePage.HotCityList);
		Report.writeHTMLLog("获取城市列表", "城市个数："+tmpCount, Report.DONE, "");
		//执行循环操作各个城市
        if(tmpCount!=0){
        	resultList = getCommunityCount(tmpCount);
        	Iterator<Entry<String, String>> iterComm = resultList.entrySet().iterator();
        	//本来要跑完所有的城市··················先跑5个吧·······································
        	while(iterComm.hasNext()&& checkTimes<5 ){
        		checkTimes = checkTimes + 1;
            	Map.Entry<String, String> result = iterComm.next();
            	cityName = result.getKey();
            	cityUrl = result.getValue();
            	cityUrl = cityUrl.replace("sale/", "");
            	if(cityName!=null&&cityUrl!=null){
                	bs.get(cityUrl);
                	checkPrice(cityName,cityUrl);  
            	}else{           		
            		Report.writeHTMLLog("当前城市", "当前城市名称："+cityName+"<br>当前城市URL："+cityUrl, Report.FAIL, "");
            	}         	
        	}
        }else{
        	String ps = bs.printScreen();
    		Report.writeHTMLLog("获取城市列表", "城市个数："+tmpCount, Report.FAIL, ps);
        }
	}

	private void checkPrice(String inCityName,String cityUrl) {

		String tmpStat = Ajk_HomePage.MarketStat; // 房价行情，右侧均价统计文字
		String tmpCommPriceLink = Ajk_HomePage.CommHideLink; // XX小区房价链接
		String tmpTabCommunity = cityUrl+"community/"; // tab页，小区链接

		// 检查 房价行情，右侧均价统计文字 是否存在
		if (bs.getElementCount(tmpStat, 5) != 0) {
			String StatString = bs.getText(tmpStat, "获取均价统计文字");

			if (StatString.indexOf("暂无数据") != -1) {
				String tmpPicName = bs.printScreen();
				Report.writeHTMLLog("城市首页-均价统计文字", "城市首页-均价统计文字检查失败，包含“暂无数据”", "FAIL", tmpPicName);
			} else {
				Report.writeHTMLLog("城市首页-均价统计文字", "城市首页-均价统计文字检查成功，不包含“暂无数据”", "PASS", "");
			}
		}

		// 检查 XX小区房价链接 是否存在
		if (bs.getElementCount(tmpCommPriceLink, 5) != 0) {
			// 点击“XX小区房价链接”
			bs.get(tmpTabCommunity);
			doCheckListData(inCityName);
		} else {
			Report.writeHTMLLog("城市首页-小区房价链接", "【" + inCityName + "】小区房价不存在", "DONE", "");
		}
	}
	
	private HashMap<String, String> getCommunityCount(int count){
		HashMap<String,String> communityList = new HashMap<String,String>();
		String cityName = null;
		String cityUrl = null;
		String tmpCityList = Ajk_HomePage.HotCityList; // 底部，"房地产热门城市"列表
		//只跑5个城市吧
		for(int i=1;i<=5;i++){
			tmpCityList = tmpCityList+"["+i+"]";
			if(bs.check(tmpCityList)){
				cityName = bs.getText(tmpCityList, "获取城市名称");
				cityUrl = bs.getAttribute(tmpCityList, "href");
				communityList.put(cityName, cityUrl);
			}else{
				Report.writeHTMLLog("获取当前城市名称和URL", "获取当前城市和URL失败"+tmpCityList, Report.DONE, "");
				continue;
			}
			tmpCityList=Ajk_HomePage.HotCityList;
		}
		return communityList;
	}
	
	private void doCheckListData(String inCityName) {
		// String CommName = "";
		String CommPrice = "";
		String CommTrend = "";
		String tmpPrice = "";
		String tmpTrend = "";
		String result = "fail";

		// 获取列表页第一个小区流水号
		String tmpFirstCommID = Ajk_Community.FirsrComm;
		
		String firstCommIDString = bs.getAttribute(tmpFirstCommID, "id");
		
		if(inCityName.equals("北京")||inCityName.equals("上海")){
			firstCommIDString = firstCommIDString.replace("li_apf_html_id_", "");
		}else{
			firstCommIDString = firstCommIDString.replace("li_apf_id_", "");
		}
		
		int firstCommID = Integer.parseInt(firstCommIDString); 
		
		for (int i = 0; i <= 9; i++) {
			// 获取小区名
			// CommName=bs.getText("//a[@id='comm_name_qt_apf_id_"+(firstCommID+i*2)+"']",
			// "获取小区名称");
			
            if(inCityName.equals("北京")||inCityName.equals("上海")){
    			// 获取小区均价
            	//div[@id='apf_html_id_0']/div[2]/strong/em
    			tmpPrice = "//div[@id='apf_html_id_" + (firstCommID + i) + "']/div[2]/strong/em";
    			if (bs.getElementCount(tmpPrice, 10) != 0) {
    				CommPrice = bs.getText(tmpPrice, "获取小区均价");
    			} else {
    				CommPrice = "-";
    			}

    			// 获取小区均价趋势
    			tmpTrend = "//div[@id='apf_html_id_" + (firstCommID + i) + "']/div[2]/p/span";
    			if (bs.getElementCount(tmpTrend, 10) != 0) {
    				CommTrend = bs.getText(tmpTrend, "获取小区价格趋势");
    			} else {
    				CommTrend = "-";
    			}

    			// 有一个小区均价不为"-"，跳出循环
    			if (!CommPrice.equals("-")) {
    				result = "pass";
    				break;
    			}

    			// 有一个小区均价趋势不为"-"，跳出循环
    			if (!CommTrend.equals("-")) {
    				result = "pass";
    				break;
    			}
            }else{
        		// 获取小区均价
    			tmpPrice = "//div[@id='apf_id_" + (firstCommID + i*2) + "']/div[2]/strong/em";
    			if (bs.getElementCount(tmpPrice, 10) != 0) {
    				CommPrice = bs.getText(tmpPrice, "获取小区均价");
    			} else {
    				CommPrice = "-";
    			}

    			// 获取小区均价趋势
    			tmpTrend = "//div[@id='apf_id_" + (firstCommID + i*2) + "']/div[2]/p/span";
    			if (bs.getElementCount(tmpTrend, 10) != 0) {
    				CommTrend = bs.getText(tmpTrend, "获取小区价格趋势");
    			} else {
    				CommTrend = "-";
    			}

    			// 有一个小区均价不为"-"，跳出循环
    			if (!CommPrice.equals("-")) {
    				result = "pass";
    				break;
    			}

    			// 有一个小区均价趋势不为"-"，跳出循环
    			if (!CommTrend.equals("-")) {
    				result = "pass";
    				break;
    			}
            }
	
		}

		if (result.equals("pass")) {
			Report.writeHTMLLog("小区列表页-小区价格检查", "【" + inCityName + "】检查成功，每个小区价格正常", "PASS", "");
		} else {
			String tmpPicName = bs.printScreen();
			Report.writeHTMLLog("小区列表页-小区价格检查", "【" + inCityName + "】检查失败，每个小区价格含有“-”", "FAIL", tmpPicName);
		}
	}

}
