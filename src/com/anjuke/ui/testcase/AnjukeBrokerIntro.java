package com.anjuke.ui.testcase;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjukeinc.iata.ui.util.GetRandom;
import com.anjuke.ui.page.*;
import com.anjuke.ui.publicfunction.PublicProcess;

/**
* @Todo http://my.anjuke.com/user/broker/introduction & 验证个人介绍部分可否点击
* @author fjzhang & chuzhaoqin
* @since 2012-11-13
* @file AnjukeBrokerIntro.java
*
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
        bs.get("http://my.anjuke.com/user/broker/profile");
        //waitForPageToLoad(500);
        bs.select(Ajk_ShopView.PROAndCity, "上海市");
        bs.select(Ajk_ShopView.Region, "浦东新区");
        bs.click(Ajk_ShopView.Region1, "公开");
        //年龄不公开
        bs.click(Ajk_ShopView.Age2, "不公开");
        //生肖
        bs.select(Ajk_ShopView.shenXiao, "羊");
        bs.click(Ajk_ShopView.shenXiao2,"不公开");
        //星座
        bs.select(Ajk_ShopView.xingzuo, "射手座");
        bs.click(Ajk_ShopView.xingzuo1, "公开");
        
        //生成书籍list
        //waitForPageToLoad(500);
	   	int n;
        for(int i=0;i<5;i++){
        	Random r = new Random();
	 		n= r.nextInt(11)+1; 
        	bs.click("//*[@id='hotTag_book']/tbody/tr[2]/td/a["+n+"]", "点击书籍");
       int m;
       for (int a=0;a<5;a++);
            Random s = new Random();
            m= s.nextInt(13)+1;
        	bs.click("//*[@id='hotTag_food']/tbody/tr[2]/td/a["+m+"]", "选择美食");
       int l;
  	   for (int b=0;b<5;b++);
  	        Random q = new Random();
  	        l= q.nextInt(14)+1;
  	        bs.click("//*[@id='hotTag_acti']/tbody/tr[2]/td/a["+l+"]", "选择娱乐活动");  
        }	        ////bs.click(Ajk_ShopView.submit, "点击确认保存");
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
