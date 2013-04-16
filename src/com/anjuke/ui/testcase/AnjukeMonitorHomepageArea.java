package com.anjuke.ui.testcase;

/**
 * 该用例主要用来检查anjuke网站各区的页面标题跟对于的区县关键字是否匹配
 * 步骤：
 * 随机一个城市
 * 取首页热门区域的区域名、href
 * 打开区域名对应的链接，验证页面高亮的文字是否包含区域名
 * @UpdateAuthor ccyang
 * @last updatetime  2013/03/13
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Ajk_HomePage;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;

public class AnjukeMonitorHomepageArea {
	private Browser bs = null;
	@BeforeMethod
	public void setUp() {
		Report.G_CASECONTENT = "监控城市首页热门区域模块";
		bs = FactoryBrowser.factoryBrowser();
	}
	@AfterMethod
	public void tearDown() {
		bs.quit();
		bs = null;
	}
	
	@Test
	public void testStart() throws InterruptedException {
		String[] city;
    	String cityPinyin = "";
    	String cityName = "";
    	int cityType = 1;
    	
    	city = PublicProcess.getRandomCityFromConfig(cityType).split("-");
    	cityPinyin = city[0];
    	cityName = city[1];
		
    	String homePageUrl = "http://"+cityPinyin+".anjuke.com";
    	bs.get(homePageUrl);
    	checkLink(cityName,cityType);
	}
	//检查区域链接
	private void checkLink(String cityName ,int cityType){
		String areaName;
		String areaLink;
		String highlightArea;
		HashMap<String,String> areaLinkList = new HashMap<String,String>();
		
		switch (cityType){
		//首页有三种：热门区域-有新房城市
		case 1: case 2:
			if(bs.check(Ajk_HomePage.HotAreaHasXin,3)){
				int linkCount = bs.getElementCount(Ajk_HomePage.HotAreaHasXin);
				for(int i=1;i<linkCount;i++){
					String tempLocator = Ajk_HomePage.HotAreaHasXin+"["+i+"]";
					areaName = bs.getText(tempLocator, "获取区域名称");
					areaLink = bs.getAttribute(tempLocator, "href");
					areaLinkList.put(areaName, areaLink);
				}
				break;
			}else{
				String ps = bs.printScreen();
				Report.writeHTMLLog("城市区域不存在", cityName+"不存在【热门区域】/【从区域开始】模块", Report.FAIL, ps);
				return;
			}
		//热门区域-无新房城市
		case 3:
			if(bs.check(Ajk_HomePage.HotAreaNoXin,3)){
				int linkCount = bs.getElementCount(Ajk_HomePage.HotAreaNoXin);
				for(int i=1;i<linkCount;i++){
					String tempLocator = Ajk_HomePage.HotAreaNoXin+"["+i+"]";
					areaName = bs.getText(tempLocator, "获取区域名称");
					areaLink = bs.getAttribute(tempLocator, "href");
					areaLinkList.put(areaName, areaLink);
				}
				break;
			}else{
				String ps = bs.printScreen();
				Report.writeHTMLLog("城市区域不存在", cityName+"不存在【热门区域】/【从区域开始】模块", Report.FAIL, ps);
				return;
			}
		//从区域开始
		case 4:
			if(bs.check(Ajk_HomePage.AreaInfo,3)){
				int linkCount = bs.getElementCount(Ajk_HomePage.AreaInfo);
				for(int i=1;i<linkCount;i++){
					String tempLocator = Ajk_HomePage.AreaInfo+"["+i+"]";
					areaName = bs.getText(tempLocator, "获取区域名称");
					areaLink = bs.getAttribute(tempLocator, "href");
					areaLinkList.put(areaName, areaLink);
				}
				break;
			}else{
				String ps = bs.printScreen();
				Report.writeHTMLLog("城市区域不存在", cityName+"不存在【热门区域】/【从区域开始】模块", Report.FAIL, ps);
				return;
			}
		}
		
		Iterator<Entry<String,String>> areaItem = areaLinkList.entrySet().iterator();
		while(areaItem.hasNext()){
			Map.Entry<String, String> area = areaItem.next();
			areaName = area.getKey();
			areaLink = area.getValue();			
			bs.get(areaLink);
			highlightArea = bs.getText("//strong[contains(.,'"+areaName+"')]", "获得页面高亮的区域名");
			bs.assertEquals(areaName, highlightArea, "验证首页热门区域的链接", "首页的链接打开后高亮的区域正确");
		}
	}
}
