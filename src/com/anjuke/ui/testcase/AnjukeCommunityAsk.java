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

/**该测试用例用来检查小区单页里，小区概况页面上的搜索功能以及多个链接跳转是否正确
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
    	Report.G_CASECONTENT = "小区问答数据检测";
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
    }
}
