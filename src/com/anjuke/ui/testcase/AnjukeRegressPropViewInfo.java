package com.anjuke.ui.testcase;

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
import com.anjukeinc.iata.ui.util.GetRandom;
import com.anjuke.ui.page.*;
import com.anjuke.ui.publicfunction.PublicProcess;

/**二手房列表页随机找一套房源
 * 点进房源单页查看经纪人信息，小区信息是否存在
 * 此脚本为了回归 bug 14042  14132
 * @author ccyang
 * @last updatetime 2013-3-6上午10:11:37
 */
public class AnjukeRegressPropViewInfo {
    Browser bs = null;

    @BeforeMethod
    public void setUp() {
    	Report.G_CASECONTENT = "Bug Regression 房源单页内容展示";
        bs = FactoryBrowser.factoryBrowser();
    }
    @AfterMethod
    public void tearDown(){
        bs.quit();
        bs = null;
    }
    @Test
    public void testSaleSearchComm(){
    	//随机获得一个城市，123 for 非第6大区城市
		String[] city;
    	String cityPinyin = "";
    	int cityType = 123;
    	
    	city = PublicProcess.getRandomCityFromConfig(cityType).split("-");
    	cityPinyin = city[0];
		
    	String saleUrl = "http://"+cityPinyin+".anjuke.com/sale/";
    	bs.get(saleUrl);
    	
    	//随机算出点第几个房源
    	int randomPropNo = GetRandom.getrandom(bs.getElementCount(Ajk_Sale.LIST_COUNT));
    	//获得整行并取出经纪人姓名
    	String brokerName = bs.getText("//*[@id='prop_"+randomPropNo+"']/div[2]/p[2]", "获得经纪人姓名");
    	String a[] = brokerName.split(" ");
    	brokerName = a[0];
    	if(brokerName.contains("业主")){
    		Report.writeHTMLLog("选到了一套业主房源", "当前列表页第"+randomPropNo+"套房源是业主房源", Report.WARNING, bs.printScreen());
    	}
    	else{
    		bs.click(Ajk_Sale.getTitle(randomPropNo), "在列表第一页随机点一套房源");
        	bs.switchWindo(2);
        	
        	String propUrl = bs.getCurrentUrl();
        	String brokerNameProp = bs.getText(Ajk_PropView.BROKERNAME, "获得房源单页经纪人姓名");
        	bs.assertEquals(brokerName, brokerNameProp, "列表和单页的经纪人姓名是否一致，房源url："+propUrl, "");
        	
        	bs.click(Ajk_PropView.COMMINTROTAB, "移动下页面，触发ajax");
        	String priceCount = bs.getText(Ajk_PropView.PriceCount, "按售价统计房源数量",10);
        	//去掉结果中的非数字
        	priceCount = priceCount.replaceAll("\\D", "");
        	if(Integer.parseInt(priceCount)>0)
        	{
        		Report.writeHTMLLog("房源单页", "小区在售二手房有数据", Report.PASS, "");
        	}else{
        		Report.writeHTMLLog("房源单页", "小区在售二手房无数据,出问题的房源为："+propUrl, Report.FAIL, bs.printScreen());
        	}
    	}
    }

    
}
