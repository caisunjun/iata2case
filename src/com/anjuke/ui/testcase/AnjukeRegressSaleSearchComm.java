package com.anjuke.ui.testcase;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjukeinc.iata.ui.util.GetRandom;
import com.anjuke.ui.page.*;
import com.anjuke.ui.publicfunction.AjkSaleSearchAction;

/**小区列表找一个有房源的小区，获得小区名 
 * 在二手房列表页搜这个小区名，判断搜索结果的小区字段是否正确
 * 此脚本为了回归 bug 13638
 * @author ccyang
 * @last updatetime 2013-3-6上午10:11:37
 */
public class AnjukeRegressSaleSearchComm {
    Browser bs = null;
    AjkSaleSearchAction ass = null;

    @BeforeMethod
    public void setUp() {
    	Report.G_CASECONTENT = "Bug Regression 二手房列表页搜索小区名";
        bs = FactoryBrowser.factoryBrowser();
        ass = new AjkSaleSearchAction(bs);
    }
    @AfterMethod
    public void tearDown(){
        bs.quit();
        bs = null;
    }
    @Test
    public void testSaleSearchComm(){
    	//脚本执行上海或北京,通过当前秒数奇偶选择城市
    	SimpleDateFormat myFormatter = new SimpleDateFormat("ss");
		String repdate = myFormatter.format(new java.util.Date());
		
    	String commUrl = null;
    	String saleUrl = null;
    	System.out.println(repdate);
    	if(Integer.parseInt(repdate)%2==0){
    		commUrl = "http://shanghai.anjuke.com/community/";
    		saleUrl = "http://shanghai.anjuke.com/sale/";
    	}else{
    		commUrl = "http://beijing.anjuke.com/community/";
    		saleUrl = "http://beijing.anjuke.com/sale/";
    	}
    	bs.get(commUrl);
    	
    	//随机点一个区域
    	int areaCount = bs.getElementCount(Ajk_Community.AreaBox);
    	int randomAreaNumber = GetRandom.getrandom(areaCount);
    	String randomAreaName = bs.getText(Ajk_Community.AreaOfNumber(randomAreaNumber), "");
    	bs.click(Ajk_Community.AreaOfNumber(randomAreaNumber), "点击区域："+randomAreaName);
    	//随机点一个板块
    	int blockCount = bs.getElementCount(Ajk_Community.BlockBox);
    	int randomBlockNumber = GetRandom.getrandom(blockCount);
    	String randomBlockName = bs.getText(Ajk_Community.BlockOfNumber(randomBlockNumber), "");
    	bs.click(Ajk_Community.BlockOfNumber(randomBlockNumber), "点击区域："+randomBlockName);
    	//选列表第一个小区，判断是否有房源，获得小区名
    	String commPropNum = bs.getText(Ajk_Community.FirsrCommPropNum, "获得第一个小区的二手房数量");
    	if(commPropNum.contains("二手房")){
    		String commName = bs.getText(Ajk_Community.FirsrCommName, "获得第一个小区的小区名");
    		bs.get(saleUrl);
    		//在二手房列表页搜索这个小区
    		bs.type(Ajk_Sale.KwInput, commName, "输入搜索关键字："+commName);
    		bs.click(Ajk_Sale.KwSubmit, "点击找房子");
    		//检查搜索结果
    		bs.assertTrue(ass.verifyDistrict(commName), "搜索小区词", "搜索结果中的房源小区名是否正确");
    	}else{
    		Report.writeHTMLLog("小区没有房源", randomBlockName+"板块下的小区都没有房源", Report.WARNING, bs.printScreen());
    	}
    }
}
