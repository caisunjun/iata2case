package com.anjuke.ui.testcase;

import com.anjuke.ui.publicfunction.AnjukeAsk;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 该用例完成问答搜索功能--正常搜索有结果的情况
 * 
 * @Author agneszhang
 * @time 2012-09-19
 * @UpdateAuthor agneszhang
 * @last updatetime 2012-09-19
 */
public class AnjukeAskNormalSearchFound {
	private Browser driver = null;

	@BeforeMethod
	public void setUp() throws Exception {
		Report.G_CASECONTENT = "问答列表正常内容搜索";
		driver = FactoryBrowser.factoryBrowser();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		// Report.seleniumReport("问答搜索脚本", "正常搜索有结果");
		driver.quit();
		driver = null;
	}

	@Test
	public void normalSearchFound() {

		new AnjukeAsk().checkAskSearch(driver, "关于房产");// 正常搜索有结果

	}

}
