package com.anjuke.ui.testcase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;
import com.anjuke.ui.page.*;

/**该测试用例用来检查经纪人网络助手里，数据是否异常
 * 包括：
 * 数据的时间是否是前一天（方法待完善）
 * 连续7日新发房源数是否为0（其他数据很难判断是否异常）
 * 
 * @author ccyang
 */

public class AnjukeBrokerReport {
    Browser bs = null;
    String username = "";
    String passwd = "";
    String reportDate = "";
    Date currentTime;

    @BeforeMethod
    public void setUp() {
        bs = FactoryBrowser.factoryBrowser();
    }
    @AfterMethod
    public void tearDown(){
        bs.quit();
        bs = null;
    }
    
    public void getTwoDay(String reportDate,long day) throws ParseException
    {
//		传入的 reportDate 格式必须为 "yyyy-MM-dd"类
    	currentTime = new Date();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		String currentDate = formatter.format(currentTime);
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");

		
		Date repdate = myFormatter.parse(reportDate);
		day = (currentTime.getTime() - repdate.getTime())/ (24 * 60 * 60 * 1000);
		System.out.println(day);
    }
    @Test
    public void testBrokerReport() throws ParseException{
    	long day = 0;
    	String tmp = "";
    	String c = "";
    	int zerotao = 0;
    	
		String casestatus = "";
		String testing = "testing";
		//从config.ini中取出casestatus
		casestatus = Init.G_config.get("casestatus");
		//如果取出的值等于testing 则用另一个账号登陆
		if(testing.equals(casestatus))
		{
		    username = "anjuketest";
		    passwd = "anjukeqa";
		}
		//如果config中casestatus的值不为testing或config未配置casestatus，用原先的账号登陆
		else
		{
		    username = "ajk_sh";
		    passwd = "anjukeqa";
		}

    	PublicProcess.logIn(bs,username,passwd,false, 1);
    	if(!bs.check("Public_HeaderFooter.HEADER_BROKERLINK"))
    	{bs.refresh();}
    	if(bs.check("html/body/div[2]/div[2]/ul[2]/div[2]/div/div/div[2]/div"))
    	{
    		bs.printScreen();
    		System.out.println("这个经纪人没有网络助手数据");
    	}
    	else
    	{System.out.println("这个经纪人有网络助手数据");}
    	
    	bs.click(Public_HeaderFooter.HEADER_BROKERLINK, "点header里的经纪人链接");
    	bs.click(Broker_Checked.REPORTICON, "点首页的网络助手icon");
    	//到网络助手页面了
    	reportDate = bs.getText(Broker_Report.REPORTDATE, "网络助手当前数据的日期");
    	//替换数据日期的格式
    	reportDate = reportDate.replace("年", "-");
    	reportDate = reportDate.replace("月", "-");
    	reportDate = reportDate.replace("日", "");
    	//比较数据的日期和now，相差几天
    	getTwoDay(reportDate,day);
    	
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentHour = formatter.format(currentTime);
		String hour;
		hour = currentHour.substring(11, 13);

    	//相差一天为正常情况，不是一天说明有问题，当前时间小于9点时 相差按两天算，先warning·························
    	if((Integer.parseInt(hour)<9 && day == 2) || (Integer.parseInt(hour)>=9 && day== 1))
    	{Report.writeHTMLLog("网络助手数据日期正确", "网络助手数据日期为: " + reportDate, Report.PASS, "");}
    	else
    	{Report.writeHTMLLog("网络助手数据日期有问题", "网络助手数据日期为: " + reportDate+" | | "+"今天是："+currentTime, Report.WARNING, "");}
    	//建个数组拿7天的新增房源数
    	ArrayList<String> newProp1to7 = new ArrayList<String>();
    	for(int i = 0;i<7;i++)
    	{
    		c = String.valueOf(i+1);
    		tmp = Broker_Report.NEWPROP.replaceAll("m",c);
    		newProp1to7.add(bs.getText(tmp, "获得左起第"+(i+1)+"条（"+(7-i)+"天前）房源发布数量"));
    		//如果某一天的新增房源数为0，则计数+1
    		if(newProp1to7.get(i).equals("0套"))
    		{zerotao++;}
    	}
    	//计数达到7时，高概率会有数据问题，请查看账号当前网络助手的情况
    	if(zerotao < 7)
    	{
    		Report.writeHTMLLog("新发房源数据7天不全为0，数据正常", "新发房源数据，7天内为0的次数: " + zerotao, Report.PASS, "");
    	}
    	else if(zerotao > 6)
    	{
    		Report.writeHTMLLog("···难道7天内这个账号都没有发过房源么···跪求看下这个账号的网络助手数据吧", "新发房源数据，7天内为0的次数: " + zerotao, Report.FAIL, "");
    	}
    	
    }
}
