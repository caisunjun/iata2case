package com.anjuke.ui.testcase;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Ajk_Ask;
import com.anjuke.ui.page.Public_HeaderFooter;
import com.anjuke.ui.publicfunction.AnjukeAsk;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;

/**
 * @Todo 外部专家编辑资料
 * @author Agneszhang
 * @since 2012-10-25
 * */

public class AnjukeAskEditExpertInfo {
	private Browser driver = null;
	//AnjukeAsk ask = new AnjukeAsk(driver);
	@BeforeMethod
	public void setUp() throws Exception {
		Report.G_CASECONTENT = "问答专家资料修改";
		driver = FactoryBrowser.factoryBrowser();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		//Report.seleniumReport("问答--编辑外部专家资料", "");
		driver.quit();
		driver = null;
	}
	@Test
	public void EditExpertInfo(){
		//外部专家登录
		PublicProcess.logIn(driver, "rmfans2000", "050100001", false, 0);
		// 判断用户是否登录成功
		driver.assertEquals("rmfans2000", driver.getText(Public_HeaderFooter.HEADER_UserName, "当前用户名"), "用户名登录判断", "是否成功");
		driver.get("http://shanghai.anjuke.com/ask");
		driver.click(Ajk_Ask.ExpertPersonal, "从问答首页进入专家个人主页");
		driver.switchWindo(2);
        String imgPath = System.getProperty("user.dir") + "\\tools\\Water lilies.jpg";
		AnjukeAsk.EditExternalExpertInfo(driver, "刘先生", "13918909652", "专家机械化制造工厂磊", "中华人民共和国江苏省太仓市大在", "13918909652", "我是专业的贷款律师", imgPath);
		
	}
}
