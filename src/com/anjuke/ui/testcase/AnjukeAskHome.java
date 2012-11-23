package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.publicfunction.AnjukeAsk;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
/**
 * 该用例完成问答首页各数据项检测功能
 * @Author agneszhang
 * @time 2012-11-06
 * @UpdateAuthor agneszhang
 * @last updatetime 2012-11-20
 */
public class AnjukeAskHome {
	private Browser driver = null;
	
	@BeforeMethod
	public void setUp() throws Exception {
		driver = FactoryBrowser.factoryBrowser();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		//Report.seleniumReport("问答首页数据检测脚本", "");
		driver.quit();
		driver = null;
	}
	@Test
	public void checkAskHomeData(){
		AnjukeAsk.getCityAskHome(driver, "shanghai");
		AnjukeAsk.checkExpertMovingData(driver);
		AnjukeAsk.checkFiveTabData(driver,0);//常见问题TAB下数据检测
		AnjukeAsk.checkFiveTabData(driver,1);//房产问题TAB下数据检测
		AnjukeAsk.checkFiveTabData(driver,2);//新房问题TAB下数据检测
		AnjukeAsk.checkFiveTabData(driver,3);//法律问题TAB下数据检测
		AnjukeAsk.checkFiveTabData(driver,4);//贷款问题TAB下数据检测
		AnjukeAsk.checkFiveTabData(driver,5);//装修风水TAB下数据检测
		AnjukeAsk.checkAskHomeToBeResolveQuestion(driver);//待解决问题列表数据检测
	}

}
