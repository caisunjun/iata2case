package com.anjuke.ui.testcase;

import java.util.Date;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjukeinc.iata.ui.util.GetRandom;
import com.anjuke.ui.page.*;
import com.anjuke.ui.publicfunction.PublicProcess;

/**该测试用例用来检查小区单页里，小区问答的搜索功能以及多个链接跳转是否正确
 * 包括：
 * 搜索答案功能是否正常
 * 问题单页中的标题与小区问答中的问题标题是否一致
 * 
 * @author jchen
 */


public class AnjukeCommunityAsk {
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
    public void testBrokerCommunityAsk(){
    	String Qtitle="房产证";
    	String keywords="";
    	String Atitle="";
    	String Ptitle="";
    	String[] Question={"小区环境怎么样？","小区房价会跌吗？","小区物业费高吗？","小区周边有学校吗？","小区的绿化率高吗？"};
    	String Title="";
    	int Val=0;
    	int ExpertType=0;
    	String ExpertFliter="";
    	String AskSucess="";
    	Date currentTime;
    	String Time="";
    	String currentQuestion="";
    	
    	bs.get("http://shanghai.anjuke.com");
    	if(!bs.check("Ajk_HomePage.CommHideLink"))
    	{bs.refresh();}
    	bs.click(Ajk_HomePage.COMMTAB, "点击TAB里的小区链接");
    	bs.click(Ajk_Community.FirsrComm, "点击小区列表页第一个小区");
    	//点击小区列表页第一个小区
   
    	bs.switchWindo(2);
    	//切换到小区单页
    	
    	bs.click(Ajk_CommunityView.QuestionInput, "点击搜索答案的问题输入框");
    	bs.type(Ajk_CommunityView.QuestionInput, Qtitle, "输入问题标题");    	    	
    	bs.click(Ajk_CommunityView.SearchQuestion, "点击搜索答案按钮");    		
    	//输入问题，点击搜索答案按钮
    	
    	bs.switchWindo(3);	
    	//切换到搜索结果页面
    	
    	keywords=bs.getAttribute(Ajk_AskSearch.QuestionInput, "value");
    	if(Qtitle.equals(keywords))
    	{
    		System.out.println("搜索结果包含该关键字");
    		Report.writeHTMLLog("搜索结果包含该关键字", "关键字匹配", Report.PASS, "");

    	}
    	else
    	{
    		Report.writeHTMLLog("搜索结果不包含该关键字", "关键字不匹配", Report.FAIL, bs.printScreen());
    	}
    	//检查搜索结果是否包含该关键字
    	
    	bs.close();
    	bs.switchWindo(2);
    	//切换到小区单页
    	
    	Atitle=bs.getText(Ajk_CommunityView.FirstQuestion, "获取第一条问题的标题内容");
    	//获取第一条问题的标题内容
    	
    	
    	bs.click(Ajk_CommunityView.FirstQuestion, "点击第一条问题的标题链接");
    	//点击第一条问题标题链接
    	
    	bs.switchWindo(3);
    	//切换到第一条问题单页
    	
    	
    	Ptitle=bs.getText(Ajk_AskView.TITLE, "获取问题单页中的标题内容");
    	//获取问题单页中的标题内容

    	if(Ptitle.contains(Atitle))
    	{
    		System.out.println("问题单页中的标题与小区问答中的问题标题一致");
    		Report.writeHTMLLog("问题单页中的标题与小区问答中的问题标题一致", "标题一致", Report.PASS, "");

    	}
    	else
    	{
    		System.out.println(Ajk_AskView.TITLE);
    		Report.writeHTMLLog("问题单页中的标题与小区问答中的问题标题不一致", "标题不一致", Report.FAIL, bs.printScreen());
    	}
    	//检查标题是否与小区问答中问题标题一致
    	
    	bs.close();
    	bs.switchWindo(2);
    	
    	PublicProcess.logIn(bs, "chenxiaojia", "chenjia", true, 0);
    	//普通用户“chenxiaojia”登录
    	
    	bs.click(Ajk_HomePage.COMMTAB, "点击TAB里的小区链接");
    	bs.click(Ajk_Community.FirsrComm, "点击小区列表页第一个小区");
    	//点击小区列表页第一个小区
   
    	bs.switchWindo(3);
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
    	
    	bs.switchWindo(4);
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
    	ExpertFliter=bs.getText(Ajk_AskQuestion.ExpertTypeByVal(Val), "获取随机指定的专家类型");
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
