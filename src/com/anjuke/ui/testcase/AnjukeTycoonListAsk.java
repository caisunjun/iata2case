package com.anjuke.ui.testcase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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



public class AnjukeTycoonListAsk {
    Browser bs = null;
    String answerDate = "";
    Date currentTime;
    Date asDate;


    @BeforeMethod
    public void setUp() {
        bs = FactoryBrowser.factoryBrowser();
    }
    @AfterMethod
    public void tearDown(){
        bs.quit();
        bs = null;
    }
    
    public long getTwoDay(String answerDate) throws ParseException
    {
//		传入的 answerDate 格式必须为 "yyyy-MM-dd"类
    	currentTime = new Date();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		String currentDate = formatter.format(currentTime);
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");

		asDate = myFormatter.parse(answerDate);
		return(currentTime.getTime() - asDate.getTime())/ (24 * 60 * 60 * 1000);
    }
    
    @Test
    public void testBrokerListAsk() throws ParseException{
    	String qnum="";
    	String sqnum="";
    	int i;
    	boolean qnumavailable;
    	long day = 0;
    	
    	bs.get("http://shanghai.anjuke.com");
    	if(!bs.check("Ajk_HomePage.H_BTN"))
    	{bs.refresh();}
    	bs.click(Ajk_HomePage.H_BTN, "点击TAB里的二手房链接");
    	//进入二手房列表页
    	bs.click("//div[@class='divBrokerList']/a", "点击页面最下方经纪人列表页入口链接");
    	bs.switchWindo(2);
    	//点击二手房列表页最下方"现在去找"经纪人列表页入口链接
    	bs.click(Ajk_Tycoon.ask, "点击筛选是否有回答问题复选框");
    	//筛选是否有回答问题
    	
    	for(i=3;qnum=="";i--)
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
    	bs.switchWindo(3);
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
    			answerDate = bs.getText(Ajk_ShopQuestion.AnswerTime, "获取店铺问答页经纪人最新回答的问题的日期");
    	    	//替换数据日期的格式
    	    	//answerDate = answerDate.replace("年", "-");
    	    	//answerDate = answerDate.replace("月", "-");
    	    	//answerDate = answerDate.replace("日", "");
    			
    	    	//比较数据的日期和now，相差几天
    	    	day = getTwoDay(answerDate);
    	    	//如果经纪人当天回答过问题，则经纪人列表页的回答问题数可能还未及时更新
    	    	if(day==0||day==1||day==2)
    	    	{
    	    		System.out.println("这个经纪人有回答问题数");
    	    		Report.writeHTMLLog("经纪人列表有回答问题的经纪人筛选结果有问题", "筛选出的经纪人问题回答数： "+qnum+";"+"经纪人问答页回答数： "+sqnum, Report.WARNING, bs.printScreen());
    	    	}
    	    	else
    	    	{
    				System.out.println("这个经纪人有回答问题数");
        			Report.writeHTMLLog("经纪人列表有回答问题的经纪人筛选结果不正确", "筛选出的经纪人问题回答数： "+qnum+";"+"经纪人问答页回答数： "+sqnum, Report.FAIL, bs.printScreen());
    	    	}
 
    		}
    	
    	//bs.closeAllwindow();
    	bs.close();
    }
}
	
