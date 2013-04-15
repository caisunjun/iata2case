package com.anjuke.ui.testcase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjuke.ui.page.*;
import com.anjuke.ui.publicfunction.PublicProcess;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
* @Todo 大学堂考试 -线上考试
* @author fjzhang
* @since 2012-8-14
* @file AnjukeBrokerUniversity.java
*http://shanghai.anjuke.com/
*/
public class AnjukeBrokerUniversity {
    Browser bs = null;
    String url = "http://my.anjuke.com/user/broker/checked/";
    String username="ajk_sh";
    String passwd = "anjukeqa";
    @BeforeMethod
    public void setUp() {
    	Report.G_CASECONTENT = "经纪人参加大学堂线上考试";
        bs = FactoryBrowser.factoryBrowser();
    }
    @AfterMethod
    public void tearDown(){
        bs.quit();
        bs = null;
    }
    @Test
    public void testUniversity(){
        /**
         * 验证经纪人可以参加大学堂考试
         */
        new PublicProcess().dologin(bs, username, passwd);
        bs.get(url);
        bs.click(Broker_info.UNIVERSITYLINK, "进入大学堂");
        bs.click(Broker_info.ONLINECHECK, "点击线上考试");
        WebElement ul = bs.findElement(Broker_info.UNIVERSITYLIST, "获取大学堂考试列表", 60);
        WebElement li = ul.findElements(By.tagName("li")).get(0); //获取第一个大学堂考试
        li.findElement(By.className("control")).findElement(By.tagName("a")).click(); //点击参加考试
        bs.switchWindo(2);
        String actualText = bs.getText(Broker_info.EXAM, "获取开始考试文本");
        System.out.println(actualText);
        bs.assertContains(actualText, "开始考试");
    }
}

