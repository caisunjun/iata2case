package com.anjuke.ui.testcase;

import com.anjuke.ui.page.Broker_gradeexchanges;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @Todo 
 * 获取兑换列表中第一个兑换
 * 兑换成功后判断 积分余额和 成功文本
 * @author fjzhang
 * @since 2012-8-9
 * @file AnjukeBrokerIntegral.java
 * @url http://my.anjuke.com/user/broker/gradeexchanges/
 */
public class AnjukeBrokerIntegral {
    Browser bs = null;
    String url = "http://my.anjuke.com/user/broker/gradeexchanges/";
    String username = "ajk_sh";
    String passwd = "anjukeqa";

    @BeforeMethod
    public void setUp() {
    	Report.G_CASECONTENT = "";
        bs = FactoryBrowser.factoryBrowser();
    }

    @AfterMethod
    public void tearDown() {
        bs.quit();
        bs = null;
    }

    @Test(groups = {"unstable"})
    public void testIntegral() {
        /**
         * 获取兑换列表中第一个兑换，兑换成功后判断 积分余额和 成功文本
         */
        new PublicProcess().dologin(bs, username, passwd);
        bs.get(url);
        String allpoint = bs.getText(Broker_gradeexchanges.UNCHANGEPOINT,"获取消耗前积分");
        System.out.println(allpoint);
        WebElement list = bs.findElement(Broker_gradeexchanges.CHANGELIST,"获取兑换列表", 60);
        // 获取每一个兑换条目
        List<WebElement> ul = list.findElements(By.tagName("ul"));
        // 获取每个条目中的 列
        List<WebElement> li = ul.get(0).findElements(By.tagName("li"));
        // 获取需要消耗的积分
        String point = li.get(2).findElement(By.tagName("strong")).getText();
        // 点击兑换
        li.get(3).click();
        bs.click(Broker_gradeexchanges.POPUP, "点击确认");
        // 获取消耗后的积分
        String chagepoint = bs.getText(Broker_gradeexchanges.REMAINPOINTS,"获取消耗后的积分");
        // 获取成功文本
        String succtext = bs.getText(Broker_gradeexchanges.CHANGESUCC, "获取成功文本");
        //增加判断：如当前积分数不足以兑换，给另一种判断
        if(Integer.parseInt(allpoint) - Integer.parseInt(point)< 0)
        {	
        	bs.assertContains(succtext, "无法兑换");
        	bs.assertTrue(Integer.parseInt(chagepoint) - Integer.parseInt(allpoint) >= 0, "验证积分兑换余额", "由于积分不足以兑换，所以此时积分不能减少");
        }
        else
        {
        	bs.assertContains(succtext, "兑换成功");
        	bs.assertIntEquals(Integer.parseInt(chagepoint), Integer.parseInt(allpoint)- Integer.parseInt(point), "验证积分兑换余额", "验证积分兑换余额是否正确");
        }
    }
}
