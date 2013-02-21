package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjuke.ui.page.*;

/**该测试用例用来检查经纪人列表里，无虚假反馈的经纪人筛选结果是否正确
 * 包括：
 * 经纪人店铺服务档案页90天内收到的虚假反馈数是否为0
 * 
 * @author jchen
 */


public class AnjukeTycoonListEvaluation {
    Browser bs = null;
    int EvaluationCount;

    @BeforeMethod
    public void setUp() {
    	Report.G_CASECONTENT = "经纪人列表无虚假反馈筛选检查";
        bs = FactoryBrowser.factoryBrowser();
    }
    @AfterMethod
    public void tearDown(){
        bs.quit();
        bs = null;
    }
    
    @Test(groups = {"unstable"})
    public void testBrokerListEvaluation(){
    	bs.get("http://shanghai.anjuke.com");
    	if(!bs.check("Ajk_HomePage.H_BTN"))
    	{bs.refresh();}
    	bs.click(Ajk_HomePage.H_BTN, "点击TAB里的二手房链接");
    	//进入二手房列表页
    	bs.click("//div[@class='divBrokerList']/a", "点击页面最下方经纪人列表页入口链接");
    	bs.switchWindo(2);
    	//点击二手房列表页最下方"现在去找"经纪人列表页入口链接
    	bs.click(Ajk_Tycoon.evaluation, "点击筛选是否无虚假反馈复选框");
    	//筛选是否有无虚假反馈
    	
    	bs.click(Ajk_Tycoon.headimg, "点击第一个经纪人头像");
    	bs.switchWindo(3);
    	//切换到经纪人店铺
    	
    	bs.click(Ajk_ShopView.UfsTab, "点击经纪人店铺服务档案tab");
    	//点击经纪人店铺服务评价tab
    	
    	
    	EvaluationCount=Integer.parseInt(bs.getText(Ajk_ShopUFS.NO_EVALUATION, "获取虚假反馈数"));
    	//获取该经纪人90天收到的虚假反馈数，并转换成int型
    	
    	if(EvaluationCount==0)
    	{
    		System.out.println("这个经纪人无虚假反馈");
    		Report.writeHTMLLog("无虚假反馈的经纪人筛选结果正确", "筛选出的经纪人90天内收到的虚假反馈数为0", Report.PASS, "");	
    	}
    	else
    	{
    		System.out.println("这个经纪人有虚假反馈");
    		Report.writeHTMLLog("无虚假反馈的经纪人筛选结果不正确", "筛选出的经纪人90天内收到的虚假反馈数不为0", Report.FAIL, bs.printScreen());  		
    	}
    	
    	//bs.closeAllwindow();
    	bs.close();
    }
}
