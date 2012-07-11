package com.anjuke.ui.testcase;

/**
 * 该用例主要用来检查anjuke网站各区的页面标题跟对于的区县关键字是否匹配
 * @UpdateAuthor Gabrielgao
 * @last updatetime  2012/05/10
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;

public class AnjukeCityhomepageSearchareaLink {
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
	
	@Test (groups = {"unstable"})
	public void testStart() throws InterruptedException {
		bs.get("http://shanghai.anjuke.com");
		String cityUrl = null;
		String cityName =null;
		HashMap<String,String> resultList =null;
		// 底部，"房地产热门城市"列表
		String tmpCityList = Init.G_objMap.get("cityhomepage_text_city_list"); 
		int tmpCount = 0;
		//判断城市列表是否存在
		if(bs.check(tmpCityList,60)){
			tmpCount = bs.getElementCount(Init.G_objMap.get("cityhomepage_text_city_list"));
		}else{
			String ps = bs.printScreen();
			Report.writeHTMLLog("获取城市列表", "当前城市个数是0", "", ps);
			bs.refresh();
			tmpCount = bs.getElementCount(Init.G_objMap.get("cityhomepage_text_city_list"));			
		}
		Report.writeHTMLLog("获取城市列表", "城市个数："+tmpCount, Report.DONE, "");

	    bs.assertEquals("64",Integer.toString(tmpCount) , "获取城市列表", "城市个数为"+tmpCount);
        if(tmpCount!=0){
        	//返回所有城市名称和URL
        	resultList = getCommunityCount(tmpCount);
        	Iterator<Entry<String, String>> iterComm = resultList.entrySet().iterator();
        	while(iterComm.hasNext()){
            	Map.Entry<String, String> result = iterComm.next();
            	cityName = result.getKey();
            	cityUrl = result.getValue();
            	if(cityName!=null&&cityUrl!=null){
                	bs.get(cityUrl);
                	checkLink(cityName);  
            	}else{           		
            		Report.writeHTMLLog("当前城市", "当前城市名称："+cityName+"<br>当前城市URL："+cityUrl, Report.FAIL, "");
            	}         	
        	}
        }else{
        	String ps = bs.printScreen();
    		Report.writeHTMLLog("获取城市列表", "城市个数："+tmpCount, Report.FAIL, ps);
        }	

 
	}
	//检查区域链接
	private void checkLink(String cityName){		
		String areaName;
		String areaLink;
		String actualTitle;
		String areaList = Init.G_objMap.get("ajk_anjukeMain_search_link_area");		
		HashMap<String,String> areaLinkList = new HashMap<String,String>();
		if(bs.check(areaList,10)){
			int linkCount = bs.getElementCount(areaList);
			for(int i=1;i<linkCount;i++){
				String tempLocator = areaList+"["+i+"]";
				areaName = bs.getText(tempLocator, "获取区域名称");
				areaLink = bs.getAttribute(tempLocator, "href");
				areaLinkList.put(areaName, areaLink);
			}			
		}else{
			String ps = bs.printScreen();
			Report.writeHTMLLog("城市区域不存在", cityName+"不存在【从区域开始】链接", Report.DONE, ps);
			return;
		}
		Iterator<Entry<String,String>> areaItem = areaLinkList.entrySet().iterator();
		while(areaItem.hasNext()){
			Map.Entry<String, String> area = areaItem.next();
			areaName = area.getKey();
			areaLink = area.getValue();			
			bs.get(areaLink);
			actualTitle = bs.getTitle();
			if(!cityName.equals("广州")&&!cityName.equals("佛山")){
				bs.assertContains(actualTitle, areaName);
			}else{
				if(cityName.equals("广州")){
					if(areaName.equals("广佛同城")){
						bs.assertContains(actualTitle, "佛山");
					}
				}else{
					if(areaName.equals("佛山")){
						bs.assertContains(actualTitle, "广州");
					}
				}
			}
		}
	}
	//获取城市列表
	private HashMap<String, String> getCommunityCount(int count){
		HashMap<String,String> communityList = new LinkedHashMap<String,String>();
		String cityName = null;
		String cityUrl = null;
		String tmpCityList = Init.G_objMap.get("cityhomepage_text_city_list"); // 底部，"房地产热门城市"列表
		for(int i=1;i<=count;i++){
			tmpCityList = tmpCityList+"["+i+"]";
			if(bs.check(tmpCityList)){
				cityName = bs.getText(tmpCityList, "获取城市名称");
				cityUrl = bs.getAttribute(tmpCityList, "href");
				communityList.put(cityName, cityUrl);
			}else{
				Report.writeHTMLLog("获取当前城市名称和URL", "获取当前城市和URL失败"+tmpCityList, Report.DONE, "");
				continue;
			}
			tmpCityList=Init.G_objMap.get("cityhomepage_text_city_list");
		}
		return communityList;
	}
}
