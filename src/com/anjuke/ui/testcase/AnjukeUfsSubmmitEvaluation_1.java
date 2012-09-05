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

import com.anjukeinc.iata.ui.report.Report;


public class AnjukeUfsSubmmitEvaluation_1 { 
	private int n;
	Set<String> list = null;
	private WebDriver driver;
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
		driver = new FirefoxDriver();
		baseUrl = "http://shanghai.anjuke.com";
//		baseUrl = "http://shanghai.anjuke.com/prop/view/123464653";
		
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		Report.seleniumReport("www.anjuke.com", "UFS功能验证_提交评价并核对数据正确性");
		driver.quit();
		driver = null;
	}
	
	@Test
	public void submitEvaluate() throws Exception {
		driver.get(baseUrl);
		waitForPageToLoad(3000);
		driver.findElement(By.cssSelector("img")).click();	
		
		//登录
		UserName = "ajk_sh";
		PassWord = "anjukeqa";
		login(UserName,PassWord);
		isLogin = true;
		
		//切换窗口
		switchWindow();
		
		//获取房源单页经纪人信息
		getSaleBrokerInfo();
		
		//点击提交评价入口
		driver.findElement(By.id("evaluate_trigger")).click();
		waitForPageToLoad(1000);	
		
		//不同类型的用户提交评价
		if(isLogin==true){
			//经纪人提交评价
			//判断经纪人已登录的标志控件是否存在
			String brokerIdentControl = "//*[@id='top_broker_panel']/li[4]/a";
			while(check(brokerIdentControl)){
				String BrokerPromp = driver.findElement(By.xpath("//*[@id='error']/div/div/div/form/div/div[3]/div[1]/span")).getText();
				if(BrokerPromp.equalsIgnoreCase("对不起，只有找房用户才可以评价")){
					Report.writeHTMLLog("经纪人提交评价", "提示正确", Report.PASS, "");
				}else{
					Report.writeHTMLLog("经纪人提交评价", "提示信息错误："+BrokerPromp, Report.FAIL, "");
				}
				driver.findElement(By.xpath("//*[@id='error']/div/div/div/form/div/div[3]/div[2]/input")).click();
			}
			//普通用户提交评价
			//判断普通用户已登录的标志控件是否存在
			String userIdentControl = "//*[@id='toplogin_cont_userlogin']/li[5]/a";
			while(check(userIdentControl)){			
				//给经纪人打分
				Scoring();
				
				//提交评论
				driver.findElement(By.id("next")).click();
				waitForPageToLoad(500);
				
				//获取并验证提示信息
				SubbmitPrompt = driver.findElement(By.xpath("//*[@id='ok']/div/div/div/form/div/div[3]/div[1]/span")).getText();			
				if(SubbmitPrompt.equalsIgnoreCase("您的评价已提交成功！")){
					Report.writeHTMLLog("普通用户提交评价", "提示信息正确", Report.PASS, "");
				}else{
					Report.writeHTMLLog("普通用户提交评价", "提示信息错误："+SubbmitPrompt, Report.FAIL, "");
				}
				
				//点击查看我的评价
				driver.findElement(By.id("okBtn")).click();
					
				//切换窗口
				switchWindow();			
					
				//比较用户中心经纪人信息是否与房源单页一致
				UserCenterUFSinfoCheck();
				
				//关闭用户中心页面
//				driver.quit();
				
				//切换窗口
				switchWindow();	
				
//				//刷新房源单页
//				driver.get(driver.getCurrentUrl());
				
			}
		}else{
			//未登录用户提交评价
			//未登录提示层，点击不登录
			driver.findElement(By.id("Tflogin_off")).click();
			waitForPageToLoad(500);
			//关闭成功评价提示层
			driver.findElement(By.cssSelector("#ajk_firend > div.view_prop_popup_fav > div.my_member_marked_mm > div.border > form > div.login > div.title > span.close > img")).click();	
		}
				
		
		//注销用户
		if(isLogin==true){
			driver.findElement(By.xpath("//*[@id='toplogin_cont_userlogin']/li[4]/a")).click();
			isLogin = false;
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
	
	public boolean check(String locator) {
		boolean a = true;
		try{
			driver.findElement(By.xpath(locator));
		}catch (NoSuchElementException e) {
			a = false;
		}
		return a;
	}
	
	//登录
	private void login(String UserName,String PassWord){
		driver.findElement(By.xpath("//*[@id='ajax_nologin_apf_id_7']/li[6]/a")).click();
		driver.findElement(By.id("username")).sendKeys(UserName);
		driver.findElement(By.xpath("//*[@id='loginForm_apf_id_9']/table/tbody/tr[5]/td/input")).sendKeys(PassWord);
		driver.findElement(By.xpath("//*[@id='loginForm_apf_id_9']/table/tbody/tr[9]/td/input")).click();
		isLogin = true;
	}	
	
	//切换窗口
	public void switchWindow(){
		list = driver.getWindowHandles();
		n = list.size();
		driver = driver.switchTo().window(list.toArray()[n-1].toString());
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
		s_BrokerName = driver.findElement(By.xpath("//*[@id='navigation-propview']/div/div/div[2]/a")).getText();
		s_BrokerHeadImgUrl = driver.findElement(By.xpath("//*[@id='navigation-propview']/div/div/div[1]/a/img")).getAttribute("src");
		s_BrokerCompany = driver.findElement(By.xpath("//*[@id='navigation-propview']/div/div/div[3]/p[1]/a")).getText();
		s_BrokerStore = driver.findElement(By.xpath("//*[@id='navigation-propview']/div/div/div[3]/p[2]/a")).getText();
		s_BrokerTel = driver.findElement(By.xpath("//*[@id='content-propview']/div[1]/div/div[2]/div[1]/div[1]/b")).getText();
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
		driver.findElement(By.xpath("//a[@onclick='clickstar("+s_HouseQuality+");']")).click();			
		waitForPageToLoad(500);
		driver.findElement(By.xpath("//a[@onclick='clickstar("+s_ServiceAttitude+");']")).click();
		waitForPageToLoad(500);
		driver.findElement(By.xpath("//a[@onclick='clickstar("+s_Professional+");']")).click();
		waitForPageToLoad(500);
		Report.writeHTMLLog("给经纪人打分", "打分成功", Report.PASS, "");
		driver.findElement(By.xpath("//*[@id='review-body']")).sendKeys(s_UserContent);
		waitForPageToLoad(500);		
	}
	
	//用户中心UFS数据检查
	private void UserCenterUFSinfoCheck(){
		//获取用户中心经纪人信息
		BrokerName = driver.findElement(By.xpath("//*[@id='MyAnjuke']/div[2]/div/div/ul/li[1]/div/div[1]/span/a")).getText();
		BrokerHeadImgUrl = driver.findElement(By.xpath("//*[@id='MyAnjuke']/div[2]/div/div/ul/li[1]/a/img")).getAttribute("src");	
		BrokerTel = driver.findElement(By.xpath("//*[@id='MyAnjuke']/div[2]/div/div/ul/li[1]/div/div[2]/span[1]")).getText();
		//从字符串中分离公司和门店
		String str = driver.findElement(By.xpath("//*[@id='MyAnjuke']/div[2]/div/div/ul/li[1]/div/div[2]/span[2]")).getText();
		String a[] = str.split("\\s");
		BrokerCompany = a[0];
		BrokerStore = a[1];
	
		//获取经纪人3项打分信息及评论信息
		HouseQuality = ChangeStrToInt(driver.findElement(By.xpath("//*[@id='MyAnjuke']/div[2]/div/div/ul/li[1]/div/div[3]/span[1]/strong")).getText());
		ServiceAttitude= ChangeStrToInt(driver.findElement(By.xpath("//*[@id='MyAnjuke']/div[2]/div/div/ul/li[1]/div/div[3]/span[2]/strong")).getText());
		Professional= ChangeStrToInt(driver.findElement(By.xpath("//*[@id='MyAnjuke']/div[2]/div/div/ul/li[1]/div/div[3]/span[3]/strong")).getText());
		UserContent = driver.findElement(By.xpath("//*[@id='MyAnjuke']/div[2]/div/div/ul/li[1]/div/div[4]/div")).getText();
		
		//经纪人姓名检测
		if(BrokerName.equalsIgnoreCase(s_BrokerName)){
			Report.writeHTMLLog("比较用户中心与房源单页的经纪人信息", "经纪人姓名一致", Report.PASS, "");
		}else{
			Report.writeHTMLLog("比较用户中心与房源单页的经纪人信息", "经纪人姓名不一致："+BrokerName+">>"+s_BrokerName, Report.FAIL, "");
		}
		
		//经纪人头像检测
		if(BrokerHeadImgUrl.equalsIgnoreCase(s_BrokerHeadImgUrl)){
			Report.writeHTMLLog("比较用户中心与房源单页的经纪人信息", "经纪人头像一致", Report.PASS, "");
		}else{
			Report.writeHTMLLog("比较用户中心与房源单页的经纪人信息", "经纪人头像不一致："+BrokerHeadImgUrl+">>"+s_BrokerHeadImgUrl, Report.FAIL, "");
		}
		
		//经纪人公司名检测
		if(BrokerCompany.equalsIgnoreCase(s_BrokerCompany)){
			Report.writeHTMLLog("比较用户中心与房源单页的经纪人信息", "经纪人公司名一致", Report.PASS, "");
		}else{
			Report.writeHTMLLog("比较用户中心与房源单页的经纪人信息", "经纪人公司名不一致："+BrokerCompany+">>"+s_BrokerCompany, Report.FAIL, "");
		}
		
		//经纪人门店名检测
		if(BrokerStore.equalsIgnoreCase(s_BrokerStore)){
			Report.writeHTMLLog("比较用户中心与房源单页的经纪人信息", "经纪人门店名一致", Report.PASS, "");
		}else if(BrokerStore.equalsIgnoreCase(s_BrokerCompany+s_BrokerStore)){
			Report.writeHTMLLog("比较用户中心与房源单页的经纪人信息", "经纪人门店名一致", Report.PASS, "");
		}else{
			Report.writeHTMLLog("比较用户中心与房源单页的经纪人信息", "经纪人门店名不一致："+BrokerStore+">>"+s_BrokerStore, Report.FAIL, "");
		}
		
		//经纪人手机号码检测
		s_BrokerTel = s_BrokerTel.replace(" ", "");
		if(BrokerTel.equalsIgnoreCase(s_BrokerTel)){
			Report.writeHTMLLog("比较用户中心与房源单页的经纪人信息", "经纪人手机号码一致", Report.PASS, "");
		}else{
			Report.writeHTMLLog("比较用户中心与房源单页的经纪人信息", "经纪人手机号码不一致："+BrokerTel+">>"+s_BrokerTel, Report.FAIL, "");
		}
			
		//判断用户中心评价信息是否与提交内容一致
		//房屋信息打分检测
		if(HouseQuality==s_HouseQuality){
			Report.writeHTMLLog("比较用户中心与房源单页的评价信息", "房屋信息打分一致", Report.PASS, "");
		}else{
			Report.writeHTMLLog("比较用户中心与房源单页的评价信息", "房屋信息打分不一致："+HouseQuality+">>"+s_HouseQuality, Report.FAIL, "");
		}
		
		//服务态度打分检测
		if(ServiceAttitude==s_ServiceAttitude-5){
			Report.writeHTMLLog("比较用户中心与房源单页的评价信息", "服务态度打分一致", Report.PASS, "");
		}else{
			Report.writeHTMLLog("比较用户中心与房源单页的评价信息", "服务态度打分不一致："+ServiceAttitude+">>"+s_ServiceAttitude, Report.FAIL, "");
		}
		
		//业务水平打分检测
		if(Professional==s_Professional-10){
			Report.writeHTMLLog("比较用户中心与房源单页的评价信息", "业务水平打分一致", Report.PASS, "");
		}else{
			Report.writeHTMLLog("比较用户中心与房源单页的评价信息", "业务水平打分不一致："+Professional+">>"+s_Professional, Report.FAIL, "");
		}		
		
		//评价内容检测
		if(UserContent.equalsIgnoreCase(s_UserContent)){
			Report.writeHTMLLog("比较用户中心与房源单页的评价信息", "评论信息一致", Report.PASS, "");
		}else{
			Report.writeHTMLLog("比较用户中心与房源单页的评价信息", "评论信息不一致："+UserContent+">>"+s_UserContent, Report.FAIL, "");
		}	
	}	
								
}
