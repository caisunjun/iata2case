package com.anjuke.ui.testcase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Ajk_AskQuestion;
import com.anjuke.ui.page.Ajk_AskQuestionSuccess;
import com.anjuke.ui.page.Ajk_AskView;import com.anjuke.ui.page.Login_My;
import com.anjuke.ui.page.Public_HeaderFooter;
import com.anjuke.ui.publicfunction.AnjukeAsk;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;

/**
 * 该用例完成问答的正常提问及正常回答的功能检测，同时检测个人中心问答相关数据
 * @Author agneszhang
 * @time 2012-09-19
 * @UpdateAuthor minzhao
 * @last updatetime 2013-04-16
 */
public class AnjukeMonitorAskQuestion {
	
	private Browser driver = null;
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
		new PublicProcess().dologin(driver, "agneszhang1", "123456");
		driver.get("http://shanghai.anjuke.com/ask/new/");
		//提交问题，随机指定某类专家并提出相关类型的问题
		Random r = new Random();
		int i = r.nextInt(5);
		expert = driver.getText(new AnjukeAsk().expertlist[i], "返回指定专家的类型名称").substring(0,2);
		title = "关于房子"+expert+"的问题要注意些什么？"+nowDateTime;
		driver.type(Ajk_AskQuestion.TITLE, title, "输入问题的标题");
		if (!driver.getAttribute(Ajk_AskQuestion.OPENMORE, "class").equals("forclose")) {
				driver.click(Ajk_AskQuestion.OPENMORE, "点击中间的展开条");
			}
		driver.type(Ajk_AskQuestion.DISCRIPTION, "关于房子"+expert+"的问题要注意些什么？","输入问题的描述");
		driver.click(new AnjukeAsk().expertlist[i], "随机选择其中某类专家");// type为0-4；表示专家类型的locator数组里的下标
		driver.click(Ajk_AskQuestion.SUBMIT, "点击提问按钮");
		try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 进入提问成功页
		if (driver.check(Ajk_AskQuestionSuccess.VIEWBUTTON)) {
				askurl = driver.getAttribute(Ajk_AskQuestionSuccess.VIEWBUTTON, "href");
				driver.click(Ajk_AskQuestionSuccess.VIEWBUTTON, "点击查看按钮进入问答单页");
				Report.writeHTMLLog("测试报告之提问是否成功", "提问成功且问题URL是："+askurl , Report.PASS, "");
			} else {
				String ps = driver.printScreen();
				Report.writeHTMLLog("测试报告之提问是否成功", "提问失败，没有进入提问成功页",Report.FAIL, ps);
			}
	}
	//回答
	@Test(groups = {"unstable"},dependsOnMethods="askQustion") 
	public void askSubmitAnswer(){
		new PublicProcess().dologin(driver, "rmfans2000", "050100001");
		// 判断用户是否登录成功
		driver.assertEquals("rmfans2000", driver.getText(Public_HeaderFooter.HEADER_UserName, "当前用户名"), "用户名登录判断", "是否成功");
		driver.get(askurl);
		if(driver.getAttribute(Ajk_AskView.IANSWER, "class").equals("answer-btn down")){
		driver.click(Ajk_AskView.IANSWER, "点击我来帮你回答按钮");
		}
		driver.type(Ajk_AskView.INPUTANSWER, "关于房子"+expert+"的问题应该要注意什么 "+nowDateTime, "输入回答内容");
		driver.click(Ajk_AskView.SUBMITANSWER, "点击提交回答的按钮");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
	//提问者采纳最佳答案并检测个人中心数据
	@Test(groups = {"unstable"},dependsOnMethods="askSubmitAnswer")
	public void askCheckUserInfo(){
		new PublicProcess().dologin(driver, "agneszhang1", "123456");
		driver.assertEquals("agneszhang1", driver.getText(Public_HeaderFooter.HEADER_UserName, "当前用户名"), "用户名登录判断", "是否成功");
		driver.get(askurl);
		driver.click(Ajk_AskView.AdoptBestAnswer, "点击第一条回答数据的”采纳最佳答案“按钮");
	
	}
}
