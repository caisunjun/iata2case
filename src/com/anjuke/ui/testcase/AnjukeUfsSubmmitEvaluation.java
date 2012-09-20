package com.anjuke.ui.testcase;

import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.*;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;


public class AnjukeUfsSubmmitEvaluation { 
	private int n;
	Set<String> list = null;
	public Browser bs = null;
	private String baseUrl;
	private boolean isLogin = false;
	private String SubbmitPrompt;
	private String UserName;
	private String PassWord;
	
	//用户中心的经济人信息，经纪人3项打分及评价内容
	private String BrokerName;
	private String BrokerHeadImgUrl;
	private String BrokerCompany;
	private String BrokerStore;
	private String BrokerTel;

	private int HouseQuality;
	private int ServiceAttitude;
	private int Professional;
	private String UserContent;
	
	
	//房源单页的经济人信息，经纪人3项打分及评价内容
	private String s_BrokerName;
	private String s_BrokerHeadImgUrl;
	private String s_BrokerCompany;
	private String s_BrokerStore;
	private String s_BrokerTel;
	
	private int s_HouseQuality;
	private int s_ServiceAttitude;
	private int s_Professional;
	private String s_UserContent;		
	
	@BeforeMethod
	public void setUp() throws Exception {
//		bs.deleteAllCookies();
		bs = FactoryBrowser.factoryBrowser();
//		baseUrl = "http://shanghai.anjuke.com";	
		baseUrl = "http://shenzhen.anjuke.com/prop/view/121857833";
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		bs.quit();
		bs = null;
	}
	
	@Test(groups = { "unstable" })
	public void submitEvaluate() throws Exception {			
		//登录
		UserName = "jessi21";
		PassWord = "jessi21";
		PublicProcess.dologin(bs, UserName, PassWord);
		isLogin = true;		
		
		bs.get(baseUrl);
		
		//获取房源单页经纪人信息
		getSaleBrokerInfo();
		
		//点击提交评价入口
		bs.click(Ajk_PropView.SALE_WOYAOPINGJIA, "点击“我要评价”");
		
		//不同类型的用户提交评价
		if(isLogin==true){
			//经纪人提交评价
			//获取提示信息
//			String brokerSubmitContentPrompt_Actual = bs.getText(Ajk_SaleView.SALE_BROKERSUBMITCONTENTPROMPT, "获取提示信息");
			//提示信息期望值			
//			String brokerSubmitContentPrompt_Expected = "对不起，只有找房用户才可以评价";
			
			//判断经纪人已登录的标志控件是否存在
			while(bs.check(Ajk_PropView.SALE_BROKERLOGINFLAG)){
				//验证提示信息是否正确
//				bs.assertContains(brokerSubmitContentPrompt_Actual, brokerSubmitContentPrompt_Expected);
				bs.click(Ajk_PropView.SALE_CLOSEBROKERPROMPT, "关闭经纪人评价提示");	
				
				//注销用户
				bs.click(Ajk_PropView.SALE_LOGOUTBROKER, "注销用户");	
				isLogin = false;
			}

			//普通用户提交评价
			//判断普通用户已登录的标志控件是否存在
			while(bs.check(Ajk_PropView.SALE_USERLOGINFLAG)){
				//给经纪人打分
				Scoring();
				
				bs.click(Ajk_PropView.SALE_USERSUBMITCONTENT,"普通用户提交评价");
				
				//获取并验证提示信息
				//提示信息期望值
//				String SubbmitPrompt_Expected = "您的评价已提交成功！";			
				//获取提交成功的提示信息
//				String SubbmitPrompt_Actual = bs.getText(Ajk_SaleView.SALE_USERSUBMITCONTENTPROMPT, "获取提交成功的提示信息");								
//				bs.assertContains(SubbmitPrompt_Actual, SubbmitPrompt_Expected);			
				waitForPageToLoad(500);
				bs.click(Ajk_PropView.SALE_SEEMYEVALUATION, "查看我的评价");
					
				//切换窗口
				switchWindow();			
					
				//比较用户中心经纪人信息是否与房源单页一致
				UserCenterUFSinfoCheck();
				
				//注销用户
				bs.click(Ajk_PropView.SALE_LOGOUTUSER, "注销用户");	
				isLogin = false;
			}				
		}else{
			//给经纪人打分
			Scoring();	
			//提交评价
			bs.click(Ajk_PropView.SALE_USERSUBMITCONTENT,"普通用户提交评价");
			bs.click(Ajk_PropView.SALE_ANONYMOUSEVALUATION,"不登录，匿名评价");			
			waitForPageToLoad(500);
			//关闭成功评价提示层
			bs.click(Ajk_PropView.SALE_CLOSEANONYMOUSEVALUATIONPROMPT, "关闭成功评价提示层");
		}						
		
	}	
	
	//定义等待时间
	public void waitForPageToLoad(long longtime) {
		try {
			Thread.sleep(longtime);
		} catch (InterruptedException exception) {
			exception.getStackTrace();
		}
	}		
	
	//切换窗口
	public void switchWindow(){
		n = bs.getWindowHandles();		 
		bs.switchWindo(n);
		waitForPageToLoad(500);
	}
	
	//提取字符串中的数字
	public int ChangeStrToInt(String s){
		int i=0;
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(s);
		if(m.find()){
			i = Integer.parseInt(m.group());
		}
		if(i==0){
			Report.writeHTMLLog("用户中心经纪人打分检测", "3项打分项显示空", Report.FAIL, "");			
		}
		return i;
	}
	
	//获取房源单页经纪人信息
	private void getSaleBrokerInfo(){
		s_BrokerName = bs.getText(Ajk_PropView.BROKERNAME, "获取经纪人姓名");
		s_BrokerHeadImgUrl = bs.getAttribute(Ajk_PropView.BROKERHEADIMG,"src");
		s_BrokerCompany = bs.getText(Ajk_PropView.SALE_BROKERCOMPANY,"获取经纪人公司名");
		s_BrokerStore = bs.getText(Ajk_PropView.SALE_BROKERSTORE,"获取经纪人门店名");
		s_BrokerTel = bs.getText(Ajk_PropView.BROKERTEL,"获取经纪人手机号");
	}
	
	
	//给经纪人打分及评价
	private void Scoring(){
		//随机生成3项打分分数
		Random rand = new Random();
		s_HouseQuality = rand.nextInt(4)+1;
	    s_ServiceAttitude = rand.nextInt(4) + 6;
	    s_Professional = rand.nextInt(4) + 11;
	   
	    s_UserContent = "房源真实，服务态度好，房产交易相关的知识比较丰富!";
	    
		
		//给经纪人打分				
		bs.click("//a[@onclick='clickstar("+s_HouseQuality+");']", "房屋质量分");
		waitForPageToLoad(500);
		bs.click("//a[@onclick='clickstar("+s_ServiceAttitude+");']", "服务态度分");	
		waitForPageToLoad(500);
		bs.click("//a[@onclick='clickstar("+s_Professional+");']", "业务水平分");	
		waitForPageToLoad(500);
		Report.writeHTMLLog("给经纪人打分", "打分成功", Report.PASS, "");	
		s_UserContent = "房源真实，服务态度好,考虑这套房源!";
		bs.findElement(Ajk_PropView.SALE_USERINPUTCONTENT, "输入评论内容",5).sendKeys(s_UserContent);
		waitForPageToLoad(500);		
	}
	
	//用户中心UFS数据检查
	private void UserCenterUFSinfoCheck(){
		//获取用户中心经纪人信息
		
		BrokerName = bs.getText(Ajk_UserCenter.USERCENTER_BROKERNAME, "获取经纪人姓名");
		BrokerHeadImgUrl = bs.getAttribute(Ajk_UserCenter.USERCENTER_BROKERHEADIMG,"src");	
		BrokerTel = bs.getText(Ajk_UserCenter.USERCENTER_BROKERTEL, "获取经纪人手机号");
		//从字符串中分离公司和门店
		String str = bs.getText(Ajk_UserCenter.USERCENTER_BROKERCOMPANYSTORE, "获取经纪人公司和门店");
		String a[] = str.split("\\s");
		BrokerCompany = a[0];
		BrokerStore = a[1];
	
		//获取经纪人3项打分信息及评论信息
		HouseQuality = ChangeStrToInt(bs.getText(Ajk_UserCenter.USERCENTER_HOUSEQUALITY,"获取房源质量"));
		ServiceAttitude= ChangeStrToInt(bs.getText(Ajk_UserCenter.USERCENTER_SERVICEATTITUDE,"获取服务态度"));
		Professional= ChangeStrToInt(bs.getText(Ajk_UserCenter.USERCENTER_PROFESSIONAL,"获取专业知识"));
		UserContent = bs.getText(Ajk_UserCenter.USERCENTER_CONTENT,"获取评论内容");
		
		//经纪人姓名检测
		bs.assertEquals(s_BrokerName, BrokerName, "比较经纪人姓名", "经纪人姓名："+BrokerName+">>"+s_BrokerName);

		//经纪人头像检测
		bs.assertEquals(s_BrokerHeadImgUrl, BrokerHeadImgUrl, "比较经纪人头像URL", "经纪人头像："+BrokerHeadImgUrl+">>"+s_BrokerHeadImgUrl);
		
		//经纪人公司名检测
		bs.assertEquals(s_BrokerCompany, BrokerCompany, "比较经纪人公司", "经纪人公司："+BrokerCompany+">>"+s_BrokerCompany);
		
		//经纪人门店名检测
		if(BrokerStore.equalsIgnoreCase(s_BrokerStore)){
			Report.writeHTMLLog("比较经纪人信息", "经纪人门店名一致", Report.PASS, "");
		}else if(BrokerStore.equalsIgnoreCase(s_BrokerCompany+s_BrokerStore)){
			Report.writeHTMLLog("比较经纪人信息", "经纪人门店名一致", Report.PASS, "");
		}else{
			Report.writeHTMLLog("比较经纪人信息", "经纪人门店名不一致："+BrokerStore+">>"+s_BrokerStore, Report.FAIL, "");
		}
		
		//经纪人手机号码检测
		s_BrokerTel = s_BrokerTel.replace(" ", "");
		bs.assertEquals(s_BrokerTel, BrokerTel, "比较经纪人手机号", "经纪人手机号："+BrokerTel+">>"+s_BrokerTel);		
			
		//判断用户中心评价信息是否与提交内容一致
		//房屋信息打分检测
		bs.assertIntEquals(s_HouseQuality, HouseQuality, "比较房屋信息打分", "房屋信息打分："+HouseQuality+">>"+s_HouseQuality);		
		
		//服务态度打分检测
		bs.assertIntEquals(s_ServiceAttitude-5, ServiceAttitude, "比较服务态度打分", "服务态度打分："+ServiceAttitude+">>"+(s_ServiceAttitude-5));
		
		//业务水平打分检测
		bs.assertIntEquals(s_Professional-10, Professional, "比较业务水平打分", "业务水平打分："+Professional+">>"+s_Professional);	
		
		//评价内容检测
		bs.assertEquals(s_UserContent, UserContent, "比较经纪人评价信息", "评价信息："+UserContent+">>"+s_UserContent);
	}	
								
}
