package com.anjuke.ui.testcase;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Ajk_ShopView;
import com.anjuke.ui.page.Broker_profile;
import com.anjuke.ui.page.Broker_shopprofile;
import com.anjuke.ui.publicfunction.BrokerInfo;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjukeinc.iata.ui.util.GetRandom;

/**
 * @Todo 
 * 验证公开按钮可点击
 * 修改喜欢的书、美食、娱乐活动（清除现有+所有候选里随机取5个）
 * @author fjzhang
 * @update chuzhaoqin
 * @since 2012-8-14
 * @file AnjukeBrokerprofile.java 
 * @url http://my.anjuke.com/user/broker/profile
 */
public class AnjukeBrokerprofile {
    Browser bs = null;
    String url = "http://my.anjuke.com/user/broker/profile";
    String username = "test1";
    String passwd = "111111";

    @BeforeMethod
    public void setUp() {
    	Report.G_CASECONTENT = "经纪人更改熟悉小区和熟悉版块";
        bs = FactoryBrowser.factoryBrowser();
    }

    @AfterMethod
    public void tearDown() {
        bs.quit();
        bs = null;
    }

    @Test
    public void testBrokerprofile() {
        /**
         * 验证安居客经纪人基本资料
         */
        PublicProcess.dologin(bs, username, passwd);
        bs.get(url);
        //coded by chuzhaoqin -start
        bs.click(Ajk_ShopView.Region1, "公开");
        //年龄不公开
        bs.click(Ajk_ShopView.Age2, "不公开");
        //生肖
        bs.select(Ajk_ShopView.shenXiao, "羊");
        bs.click(Ajk_ShopView.shenXiao2,"不公开");
        //星座
        bs.select(Ajk_ShopView.xingzuo, "射手座");
        bs.click(Ajk_ShopView.xingzuo1, "公开");
        //coded by chuzhaoqin -end
        
        bs.refresh();
        // 选择省
        String province = bs.selectReturn(Broker_profile.PROVINCE, GetRandom
                .getrandom(10));
        // 选择市
        String city = bs.selectReturn(Broker_profile.STADE, GetRandom
                .getrandom(10));
        // 选择生肖
        String lunar = bs.selectReturn(Broker_profile.LUNAR, GetRandom
                .getrandom(10));
        // 选择星座
        String constellation = bs.selectReturn(Broker_profile.CONSTELLATION,
                GetRandom.getrandom(10));
        String expectedText = province+city+lunar+constellation; //期望的文本串


        // 喜欢的书
        ArrayList<String> likebook = new ArrayList<String>();
        BrokerInfo.ulSelect(bs,Broker_profile.LIKEBOOK,
                Broker_profile.SELECTLIKEBOOK, "获取喜欢的书的列表", "获取选择书的列表");

        // 喜欢的食物
        ArrayList<String> likefood = new ArrayList<String>();
        BrokerInfo.ulSelect(bs,Broker_profile.LIKEFOOD,
                Broker_profile.SELECTLIKEFOOD, "获取喜欢的食物的列表", "获取喜欢的食物的列表");
        // 娱乐活动
        ArrayList<String> recretional = new ArrayList<String>();
        BrokerInfo.ulSelect(bs,Broker_profile.RECRETIONAL,
                Broker_profile.SELECTRECRETIONAL, "获取喜欢的娱乐活动", "获取娱乐活动列表");
        bs.click(Broker_profile.SAVE, "保存个人介绍修改");

        likebook = BrokerInfo.ulSelect1(bs,Broker_profile.LIKEBOOK,"获取选择好的书的列表");
        likefood = BrokerInfo.ulSelect1(bs,Broker_profile.LIKEFOOD,"获取选择好的食物的列表");
        recretional = BrokerInfo.ulSelect1(bs,Broker_profile.RECRETIONAL,"获取选择好的娱乐活动");

        String actualText = bs.getText(Broker_profile.SAVETEXT, "获取保存成功文本");
        bs.assertContains(actualText, "保存成功");

        /**
         *  到我的店铺验证 ，基本资料是否生效
         */
        bs.click(Broker_profile.MYSHOP, "进入我的店铺");
        bs.switchWindo(2);
        bs.click(Broker_shopprofile.SELFINTRO, "进入自我介绍页");
        String provinceactual = bs.getText(Broker_shopprofile.PROVINCE, "获取出生地省份");
        String cityactual = bs.getText(Broker_shopprofile.CITY, "获取出生地城市");
        String lunaractual = bs.getText(Broker_shopprofile.LUNAR, "获取生肖");
        String constellation1 = bs.getText(Broker_shopprofile.CONSTELLATION, "获取星座");
        String actualText1 = provinceactual+cityactual+lunaractual+constellation1;
        if(!expectedText.trim().contains(actualText1.trim()))
        {
        	String tmpUrl = bs.getCurrentUrl()+"?cc=cc";
        	System.out.println(tmpUrl);
        	bs.get(tmpUrl);
        	bs.refresh();
        	actualText1 = provinceactual+cityactual+lunaractual+constellation1;
        	bs.assertContains(actualText1.trim(), expectedText.trim());
        }
        
        //我的店铺页获取喜欢的书，与个人介绍页对比
        WebElement shoplikebook = bs.findElement(Broker_shopprofile.LIKEBOOK, "获取喜欢的书", 60);
        List<WebElement> likebooks = shoplikebook.findElements(By.tagName("a"));
        boolean tag = true;
        for( int i =0;i<likebooks.size();i++){
            if(!likebooks.get(i).getText().contains(likebook.get(i))){
                bs.assertTrue(false, "验证喜欢的书", "喜欢的书在我的店铺和个人介绍页不一致");
                tag = false;
            }
        }
        if (tag){
            bs.assertTrue(true, "验证喜欢的书", "喜欢的书在我的店铺和个人介绍页一致");
        }

        //我的店铺页获取喜欢的食物，与个人介绍页对比
        tag = true;
        WebElement shoplikefood = bs.findElement(Broker_shopprofile.LIKEFOOD, "店铺页获取喜欢的食物", 60);
        List<WebElement> likefoods = shoplikefood.findElements(By.tagName("a"));
        for ( int i=0;i<likefoods.size();i++){
            if(!likefoods.get(i).getText().contains(likefood.get(i))){
                bs.assertTrue(false, "验证喜欢的食物", "喜欢的美食在我的店铺和个人介绍页不一致");
                tag = false;
            }
        }
        if(tag){
            bs.assertTrue(true, "验证喜欢的食物", "喜欢的美食在我的店铺和个人介绍页一致");
        }
        //我的店铺页获取娱乐活动，与个人介绍页对比
        tag = true;
        WebElement shoprentional = bs.findElement(Broker_shopprofile.RENTIONAL, "店铺页获取娱乐活动", 60);
        List<WebElement> rentionals = shoprentional.findElements(By.tagName("a"));
        for( int i =0;i<rentionals.size();i++){
            if( !rentionals.get(i).getText().contains(recretional.get(i))){
                bs.assertTrue(false, "验证娱乐活动", "娱乐活动在我的店铺和个人介绍页不一致");
                tag = false;
            }
        }
        if(tag){
            bs.assertTrue(true, "验证娱乐活动", "娱乐活动在我的店铺和个人介绍页一致");
        }
    }
}
