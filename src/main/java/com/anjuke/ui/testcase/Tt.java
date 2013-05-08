package com.anjuke.ui.testcase;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 该用例完成问答首页各数据项检测功能
 *
 * @Author agneszhang
 * @time 2012-11-06
 * @UpdateAuthor agneszhang
 * @last updatetime 2012-11-20
 */
public class Tt {
    private Browser driver = null;

    @BeforeMethod
    public void setUp() throws Exception {
        System.out.println("aaaaaaaaaaaaaa:setUp");
        Report.G_CASECONTENT = "问答首页数据检测";
        driver = FactoryBrowser.factoryBrowser();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        System.out.println("aaaaaaaaaaaaaa:tearDown");
        //Report.seleniumReport("问答首页数据检测脚本", "");
        driver.quit();
        driver = null;
    }

    @Test
    public void checkAskHomeData() {
        System.out.println("aaaaaaaaaaaaaa:test");
        driver.assertEquals("1", "2", "3", "4");
    }

}
