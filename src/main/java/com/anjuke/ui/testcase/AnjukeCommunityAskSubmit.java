package com.anjuke.ui.testcase;

import com.anjuke.ui.page.*;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjukeinc.iata.ui.util.GetRandom;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

/**该测试用例用来检查小区单页里，小区问答tab下提问功能是否正常
 * 包括：
 * 提问页带入的标题是否正确
 * 提问功能是否正常
 * 
 * @author jchen
 */


public class AnjukeCommunityAskSubmit {
    Browser bs = null;

    @BeforeMethod
    public void setUp() {
    	Report.G_CASECONTENT = "小区问答提问检测";
        bs = FactoryBrowser.factoryBrowser();
    }
    @AfterMethod
    public void tearDown(){
        bs.quit();
        bs = null;
    }
    @Test(groups = {"unstable"})
    public void testBrokerCommunityAsk(){
    	String[] Question={"小区环境怎么样？","小区房价会跌吗？","小区物业费高吗？","小区周边有学校吗？","小区的绿化率高吗？"};
    	String Title="";
    	int Val=0;
    	int ExpertType=0;
    	//String ExpertFliter="";
    	String AskSucess="";
    	Date currentTime;
    	String Time="";
    	String currentQuestion="";
    	    	
    	new PublicProcess().dologin(bs, "chenxiaojia", "chenjia");
    	//普通用户“chenxiaojia”登录
    	
    	bs.click(Ajk_HomePage.COMMTAB, "点击TAB里的小区链接");
    	bs.click(Ajk_Community.FirsrComm, "点击小区列表页第一个小区");
    	//点击小区列表页第一个小区
   
    	bs.switchWindo(2);
    	//切换到小区单页
    	
    	bs.click(Ajk_CommunityView.navtabQa, "点击小区单页里的小区问答TAB");
    	//切换到小区问答tab页
 	
    	if(bs.check(Ajk_CommunityAsk.QuestionInput))
    	{
    		bs.click(Ajk_CommunityAsk.QuestionInput,"点击提问框");
    		Val=GetRandom.getrandom(Question.length);
    		currentTime=new Date();
    		Time=String.valueOf(currentTime.getTime());
    		//当前时间转换成string类型
    		currentQuestion=Question[Val]+Time;
    		//随机问题加上当前时间
    		bs.type(Ajk_CommunityAsk.QuestionInput, currentQuestion, "输入提问标题");
    		bs.click(Ajk_CommunityAsk.SubmitButton,"点击提交按钮");    		
    		
    	}
    	//切换到小区问答tab页，输入问题并提交
    	
    	bs.switchWindo(3);
    	Title=bs.getText(Ajk_AskQuestion.TITLE, "获取标题");
    	if(currentQuestion.equals(Title))
    	{
    		System.out.println("提问页带入该标题");
    		Report.writeHTMLLog("提问页带入该标题", "标题匹配", Report.PASS, "");

    	}
    	else
    	{
    		Report.writeHTMLLog("提问页带入标题不正确", "标题不匹配", Report.FAIL, bs.printScreen());
    	}
    	//检查提问页带入的标题是否正确
    	
    	ExpertType=bs.getElementCount("//dl[@id='expertList']/dd");
    	Val=GetRandom.getrandom(ExpertType);
    	//ExpertFliter=bs.getText(Ajk_AskQuestion.ExpertTypeByVal(Val), "获取随机指定的专家类型");
    	bs.click(Ajk_AskQuestion.ExpertTypeByVal(Val), "点击随机指定的专家类型");
    	bs.click(Ajk_AskQuestion.SUBMIT, "点击提交问题按钮");
    	AskSucess=bs.getText("//dl[@class='submit_ok']/dt", "获取成功提交问题的提示信息");
    	if(AskSucess.contains("您的问题我们已经收到！请耐心等待网友的回答"))
    	{
    		System.out.println("提问成功");
    		Report.writeHTMLLog("提问成功", "提问成功", Report.PASS, "");

    	}
    	else
    	{
    		Report.writeHTMLLog("提问失败", "提问失败", Report.FAIL, bs.printScreen());
    	
    	}
    	//检查提问是否成功  	
    	
    	bs.close();
    }
}
