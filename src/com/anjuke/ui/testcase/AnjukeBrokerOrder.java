package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjuke.ui.page.*;
import com.anjuke.ui.publicfunction.PublicProcess;

/**
* @Todo TODO
* 选择端口套餐（非PPC城市经济人才可用）
* @author fjzhang
* @since 2012-8-8
* @file AnjukeBrokerOrder.java
* http://my.anjuke.com/user/payment/order/selectcombo/
*/
public class AnjukeBrokerOrder {
    Browser bs = null;
    String url = "http://my.anjuke.com/user/payment/order/selectcombo/";
    String username = "cdtest";
    String passwd = "anjukeqa";
    String[] firstcolumn = null ;
    String[] secondcolumn = null ;

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
    public void testBrokerOrder(){
        /**
         * 检查套餐列表
         */
        PublicProcess.dologin(bs, username, passwd);
        bs.get(url);
        bs.click(Broker_order.COMBOFIRST, "点击一个套餐");
        bs.click(Broker_order.XIAYIBU, "点击下一步");
        bs.click(Broker_order.QUEREN, "确认");
        String oktext = bs.getText(Broker_order.OKTEXT, "获取付款提示文本");
        bs.assertContains(oktext, "订单已提交，请尽快付款");
    }
}

