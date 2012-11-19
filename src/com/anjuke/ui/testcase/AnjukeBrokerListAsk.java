package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjuke.ui.page.*;

/**该测试用例用来检查经纪人列表里，有回答问题的经纪人筛选结果是否正确
 * 包括：
 * 回答问题数量是否存在
 * 经纪人店铺问答页是否存在问答内容
 * 经纪人列表经纪人回答问题数量和经纪人店铺问答页回答数是否一致
 * 
 * @author jchen
 */


public class AnjukeBrokerListAsk {
    Browser bs = null;

    @BeforeMethod
    public void setUp() {
        bs = FactoryBrowser.factoryBrowser();
    }
    @AfterMethod
    public void tearDown(){
        bs.quit();
        bs = null;
    }
    @Test
    public void testBrokerListAsk(){
    	String qnum="";
    	String sqnum="";
    	int i;
    	boolean qnumavailable;
    	
    	bs.get("http://shanghai.anjuke.com");
    	if(!bs.check("Ajk_HomePage.BROKERTAB"))
    	{bs.refresh();}
    	bs.click(Ajk_HomePage.BROKERTAB, "点击TAB里的经纪人链接");
    	bs.click(Ajk_Tycoon.ask, "点击筛选是否有回答问题复选框");
    	//筛选是否有回答问题
    	
    
    	for(i=3;i>=0;i--)
    	{
    		qnumavailable=bs.check(Ajk_Tycoon.getQuestionNum(i));
    		if(qnumavailable)
        	{
    			qnum=bs.getText(Ajk_Tycoon.getQuestionNum(i), "获取经纪人列表筛选出的经纪人问题回答数");
           		qnum=qnum.replace("( ", "");
            	qnum=qnum.replace(" )", "");        		
        	}
    	}
    	//获取经纪人列表问题回答数，如果无二手房或租房标签，则往前类推取值
    		
    	
    	bs.click(Ajk_Tycoon.headimg, "点击经纪人头像");
    	bs.switchWindo(2);
    	//切换到经纪人店铺页
    	
    	bs.click(Ajk_ShopView.QuestionTab, "点击TAB里的问答链接");
    	//切换到经纪人店铺问答页
    	
    	
    	sqnum=bs.getText(Ajk_ShopQuestion.QUESTION, "获取经纪人店铺问答页回答数");
    	if(qnum.equals(sqnum))
    	{
    		System.out.println("这个经纪人有回答问题数");
    		Report.writeHTMLLog("经纪人列表有回答问题的经纪人筛选结果正确", "筛选出的经纪人问题回答数和经纪人问答页回答数相同： "+qnum, Report.PASS, "");  		
    	
    	}
    	else if(bs.getElementCount("//*[@class='no_answer']") != 0)
    	{
    		System.out.println("这个经纪人无问答内容");
    		Report.writeHTMLLog("经纪人列表有回答问题的经纪人筛选结果不正确", "筛选出的经纪人无问答内容", Report.FAIL, bs.printScreen());
    		
    	}
    		else
    		{
    			System.out.println("这个经纪人有回答问题数");
    			Report.writeHTMLLog("经纪人列表有回答问题的经纪人筛选结果不正确", "筛选出的经纪人问题回答数： "+qnum+";"+"经纪人问答页回答数： "+sqnum, Report.FAIL, bs.printScreen());
    		
    		}
    	
    	//bs.closeAllwindow();
    	bs.close();
    }
}
