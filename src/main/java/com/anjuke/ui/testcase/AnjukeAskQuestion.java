package com.anjuke.ui.testcase;

import com.anjuke.ui.page.Public_HeaderFooter;
import com.anjuke.ui.publicfunction.AnjukeAsk;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 该用例完成问答的正常提问及正常回答的功能检测，同时检测个人中心问答相关数据
 * @Author agneszhang
 * @time 2012-09-19
 * @UpdateAuthor agneszhang
 * @last updatetime 2012-09-19
 */
public class AnjukeAskQuestion {
	
	private Browser driver = null;
	//AnjukeAsk ask = new AnjukeAsk(driver);
	private String askurl;
	private String expert;
	private String title;
	private String nowDateTime = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());// 当前时间
	@BeforeMethod
	public void setUp() throws Exception {
		Report.G_CASECONTENT = "";
		driver = FactoryBrowser.factoryBrowser();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		//Report.seleniumReport("问答提问页脚本+回答脚本", "提问页脚本+回答脚本");
		driver.quit();
		driver = null;
	}

	//提问
	
	@Test(groups = {"unstable"})
	public void askQustion(){
		// 普通用户登录并获取当前登录成功后的用户名
		String loginName = "agneszhang1"; 

		new PublicProcess().dologin(driver, loginName, "123456");
				
		// 判断用户是否登录成功
		driver.assertEquals(loginName, driver.getText(Public_HeaderFooter.HEADER_UserName, "当前用户名"), "用户名登录判断", "是否成功");
		
		//提交问题，随机指定某类专家并提出相关类型的问题
		driver.get("http://shanghai.anjuke.com/ask/new/");
		Random r = new Random();
		int i = r.nextInt(5);
		expert = new AnjukeAsk().getExpertText(driver,i).substring(0, 2);
		title = "关于房子"+expert+"的问题要注意些什么？"+nowDateTime;
		askurl = new AnjukeAsk().submitQuestion(driver,title,"关于房子"+expert+"的问题要注意些什么？",i);
		driver.get(askurl);
		new AnjukeAsk().submitSupplement(driver, "问题补充");
		
	}
	
	//回答
	@Test(groups = {"unstable"},dependsOnMethods="askQustion") 
	public void askSubmitAnswer(){
		new PublicProcess().dologin(driver, "rmfans2000", "050100001");
		
		// 判断用户是否登录成功
		driver.assertEquals("rmfans2000", driver.getText(Public_HeaderFooter.HEADER_UserName, "当前用户名"), "用户名登录判断", "是否成功");
		
		driver.get(askurl);
		new AnjukeAsk().submitAnswer(driver,"关于房子"+expert+"的问题要注意"+nowDateTime);
	}
	
	//提问者采纳最佳答案并检测个人中心数据
	@Test(groups = {"unstable"},dependsOnMethods="askSubmitAnswer")
	public void askCheckUserInfo(){
		new PublicProcess().dologin(driver, "agneszhang1", "123456");
		driver.assertEquals("agneszhang1", driver.getText(Public_HeaderFooter.HEADER_UserName, "当前用户名"), "用户名登录判断", "是否成功");
		driver.get(askurl);
		new AnjukeAsk().AdoptBestAnswer(driver);
		new AnjukeAsk().checkNormalUserAskInfo(driver, title);
	}

	
}
