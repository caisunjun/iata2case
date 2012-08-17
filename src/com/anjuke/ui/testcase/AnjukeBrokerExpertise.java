package com.anjuke.ui.testcase;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Broker_areas;
import com.anjuke.ui.page.Broker_profile;
import com.anjuke.ui.page.Broker_shopview;
import com.anjuke.ui.publicfunction.BrokerInfo;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;

/**
* @Todo TODO
* @author fjzhang
* @since 2012-8-15
* @file AnjukeBrokerExpertise.java
*	http://my.anjuke.com/user/broker/expertise
*/
public class AnjukeBrokerExpertise {
    Browser bs = null;
    String url = "http://my.anjuke.com/user/broker/expertise";
    String username="ajk_sh";
    String passwd = "anjukeqa";

    @BeforeMethod
    public void setUp() {
        bs = FactoryBrowser.factoryBrowser();
    }
    @AfterMethod
    public void tearDown(){
        bs.quit();
        bs = null;
    }
    @Test
    public void testBrokerExpertise(){
        /**
         * 经纪人职业特长 验证，我的店铺页
         */
        PublicProcess.dologin(bs, username, passwd);
        bs.get(url);
        BrokerInfo.ulSelect(bs, Broker_areas.JOBS, Broker_areas.SELECTJOBS, "获取职业特长列表", "获取待选择的特长列表");
        bs.click(Broker_areas.OKBUTTON, "保存确认");
        ArrayList<String> jobs = new ArrayList<String>();
        jobs = BrokerInfo.ulSelect1(bs, Broker_areas.JOBS, "重新获取职业特长列表");
        bs.click(Broker_profile.MYSHOP, "进入我的店铺");
        bs.switchWindo(2);
        WebElement shopjob = bs.findElement(Broker_shopview.EXPERTISE, "获得职业特长对象", 60);
        ArrayList<String> shopjobs = new ArrayList<String>();
        for (WebElement job : shopjob.findElements(By.tagName("a"))){
            shopjobs.add(job.getText());
        }
        boolean tag = true;
        if( jobs.size() == shopjobs.size()){
            for( String job : jobs){
                if(!shopjobs.contains(job)){
                    Report.writeHTMLLog("职业能力验证", "验证店铺页职业能力完整性失败", Report.FAIL, bs.printScreen());
                    tag = false;
                }
            }
        }
        if ( tag ){
            Report.writeHTMLLog("职业能力验证", "验证店铺页职业能力完整性OK", Report.PASS, "");
        }

    }

}

