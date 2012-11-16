package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.util.GetRandom;
import com.anjuke.ui.page.*;
import com.anjuke.ui.publicfunction.PublicProcess;

/**
* @Todo 
* 店铺管理-个人介绍-自我介绍
* 更改卖房宣言和详细介绍（固定内容+随机生成的内容）
* 进入个人店铺，验证卖房宣言和详细介绍是否和输入的值一致
* @author fjzhang 
* @since 2012-11-13
* @file AnjukeBrokerIntro.java
* @url http://my.anjuke.com/user/broker/introduction 
*/
public class AnjukeBrokerIntro {
    Browser bs = null;
    String url = "http://my.anjuke.com/user/broker/introduction";
    String username="test1";
    String passwd = "111111";


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
    public void testBrokerIntroduce(){
        PublicProcess.dologin(bs, username, passwd);
        bs.get(url);
        bs.findElement(Broker_profile.SELLHOUSE, "清空卖方宣言", 60).clear();
        String slogan = "安居客测试公告栏2011-1-20ss"+GetRandom.getrandom(1000);
        bs.type(Broker_profile.SELLHOUSE, slogan, "填写卖房宣言");
        bs.findElement(Broker_profile.SELFINTRO, "清空自我介绍", 60);
        String selfintro = "性格活泼开朗的我，刚进大学那会就和房产结下了不解之缘。" +
                "和大学期间接触房产不同的是，现在从二线跨越到了一线。大学兼职的那几年，" +
                "一有空闲时间就协助房产售楼部到各处做宣传。那时认识到的房产很肤浅，" +
                "看售楼大姐向客户做下介绍，带客户看下户型、样板房，很快就签定合同。" +
                "测试 侧his"+GetRandom.getrandom(1000);
        bs.type(Broker_profile.SELFINTRO, selfintro, "填写自我介绍");
        bs.click(Broker_profile.OKENTER, "保存确认");
        //验证店铺页上 是否一致
        bs.click(Broker_profile.MYSHOP, "进入我的店铺");
        bs.switchWindo(2);
        String actualslogan = bs.findElement(Ajk_ShopView.SLOGAN, "获得卖房宣言文本", 60).getText();
        String actualselfintro = bs.findElement(Ajk_ShopView.SELFINTRO, "获取自我介绍文本", 60).getText();
        bs.assertContains(actualslogan, slogan);
        bs.assertContains(actualselfintro, selfintro);
    }
  }
