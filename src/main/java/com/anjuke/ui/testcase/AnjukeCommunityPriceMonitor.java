package com.anjuke.ui.testcase;

import com.anjuke.ui.bean.AnjukePriceMonitorBean;
import com.anjuke.ui.publicfunction.AnjukeCommunityReport;
import com.anjuke.ui.publicfunction.AnjukeDb;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Map;
/**
 * 该类主要完成小区均价监控，完成今天20个城市的小区价格和昨天的价格进行比较
 * @UpdateAuthor Gabrielgao
 * @last updatetime 2012-05-10 13:55
 */
public class AnjukeCommunityPriceMonitor {
	private Browser driver = null;
	private String baseUrl = "http://shanghai.anjuke.com/community/view/";
	static  int unchangeCount = 0;
	private static int fialCount = 0;
	private String commTitle = null;
	private String commTrend = null;
	private String commPrice = null;

	@BeforeMethod
	public void setUp() {
		Report.G_CASECONTENT = "主营小区标志检测";
		driver = FactoryBrowser.factoryBrowser();

	}
	@AfterMethod
	public void tearDown(){
		driver.quit();
		driver = null;		
	}
	
	@Test(groups = {"unstable"})
	public void testStart(){
		//Report.setTCNameLog("小区价格监控-- AnjukeCommunityPriceMonitor --gabrielgao");
		int datacount = getCommulist().length;
		//比较和插入数据
		for(int i=0;i<datacount;i++){
			String url = baseUrl+getCommulist()[i].toString();
			String commid = getCommulist()[i];
			driver.get(url);
			String currentUrl = driver.getCurrentUrl();
			String city = currentUrl.substring(7, currentUrl.indexOf("."));
			compareData(i);
		    insertData(city,commTitle,commid,commPrice,commTrend);		    
		}
		if(fialCount+unchangeCount==datacount){
			Report.writeHTMLLog(datacount+"个小区均价比较结果", unchangeCount+"个小区均价、走势今日未更新", Report.FAIL, "");
			
		}else{
			Report.writeHTMLLog(datacount+"个小区均价比较结果", (datacount-fialCount-unchangeCount)+"个小区均价、走势今日有更新", Report.PASS, "");
		}
		//输出报表数据
		for(int i=0;i<datacount;i++){
			String commid = getCommulist()[i];
			AnjukeCommunityReport.writeDataLog(i+1,getLastFiveData(commid));
		}
		Report.setHtml(AnjukeCommunityReport.getHtml());
	}
	
	//获取今日小区数据
	private void getCommunityData(){
		//获取小区名称
		if(driver.check(Init.G_objMap.get("community_text_title"))){
			commTitle = driver.getText(Init.G_objMap.get("community_text_title"), "获取小区名称",30);				
		}else{
			driver.refresh();
			commTitle = driver.getText(Init.G_objMap.get("community_text_title"), "获取小区名称");
			compareNull(commTitle);
		}
		//获取小区均价
		if(driver.check(Init.G_objMap.get("ajk_communityDetails_commInfo_text_price"))){
			commPrice = driver.getText(Init.G_objMap.get("ajk_communityDetails_commInfo_text_price"), "获取小区均价");
		}else{
			driver.refresh();
			commPrice = driver.getText(Init.G_objMap.get("ajk_communityDetails_commInfo_text_price"), "获取小区均价");
			compareNull(commPrice);
		}
		//获取小区均价走势
		if(driver.check(Init.G_objMap.get("ajk_communityDetails_commInfo_text_priceChange"))){
			commTrend = driver.getText(Init.G_objMap.get("ajk_communityDetails_commInfo_text_priceChange"), "获取小区均价趋势");
		}else{
			driver.refresh();
			commTrend = driver.getText(Init.G_objMap.get("ajk_communityDetails_commInfo_text_priceChange"), "获取小区均价趋势");	
			compareNull(commTrend);
		}
	}
	
	//从配置文件获取小区ID
	private String [] getCommulist(){
		String dataString = Init.G_config.get("communityList");
		String [] dataList = dataString.split(",");
		return dataList;
	}
	//向数据库插入今日小区均价以及均价趋势数据
	private void insertData(String city,String community,String commid,String commPrice,String commChg){
		AnjukeDb.insertCommunity(city, community, commid, commPrice, commChg);             
	}
	//获取最近5天小区均价以及均价走势数据
	private Map<String, AnjukePriceMonitorBean> getLastFiveData(String commId){
		return AnjukeDb.getCommunityPrice(commId);
	}
	//获取最近昨天小区均价以及均价走势数据
	private Map<String, String> getLastDayData(String commId){
		return AnjukeDb.getCommunity(commId);
	}
	//获取数据为空，则打印错误报告
	private void compareNull(String nulldata){
		if(nulldata==null||nulldata.trim().equals("")){
			String ps = driver.printScreen();
			Report.writeHTMLLog("获取小区数据", "获取数据："+nulldata, Report.FAIL, ps);
		}
	}
	//小区数据对比
	private void compareData(int i){
		getCommunityData();
		//如果小区均价、走势不为空。进行价格对比
		if(commPrice!=null&&commTrend!=null){
			Map<String, String> result = getLastDayData(getCommulist()[i]);
			Iterator<Map.Entry<String, String>> it = result.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
				String lastPrice = entry.getKey();
				String lastTrend = entry.getValue();
                if(commPrice.equals(lastPrice)&&commTrend.equals(lastTrend)){
                	Report.writeHTMLLog(commTitle+"小区均价比较", "昨日均价："+lastPrice+"元/平米<br>今日均价："+commPrice+"元/平米", Report.DONE, "");
                	Report.writeHTMLLog(commTitle+"小区均价走势比较", "昨日均价走势："+lastTrend+"<br>今日均价走势："+commTrend, Report.DONE, "");
                	unchangeCount++;
                }else{
                	Report.writeHTMLLog(commTitle+"小区均价比较", "昨日均价："+lastPrice+"元/平米<br>今日均价更新为："+commPrice+"元/平米", Report.DONE, "");
                	Report.writeHTMLLog(commTitle+"小区均价走势比较", "昨日均价走势："+lastTrend+"<br>今日均价走势更新为："+commTrend, Report.DONE, "");
                }
			}			
		}
		else{
			String ps = driver.printScreen();
			Report.writeHTMLLog("小区均价获取失败", "小区价格："+commPrice+"<br>小区价格走势："+commTrend, Report.FAIL, ps);
			fialCount++;
		}
	}

}
