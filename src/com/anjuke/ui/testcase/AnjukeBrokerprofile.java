package com.anjuke.ui.testcase;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Broker_profile;
import com.anjuke.ui.page.Broker_shopprofile;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjukeinc.iata.ui.util.GetRandom;

/**
 * @Todo TODO
 * @author fjzhang
 * @since 2012-8-14
 * @file AnjukeBrokerprofile.java http://my.anjuke.com/user/broker/profile
 */
public class AnjukeBrokerprofile {
    Browser bs = null;
    String url = "http://my.anjuke.com/user/broker/profile";
    String username = "ajk_sh";
    String passwd = "anjukeqa";

    @BeforeMethod
    public void setUp() {
        bs = FactoryBrowser.factoryBrowser();
    }

    @AfterMethod
    public void tearDown() {
        Report.seleniumReport("", "");
        bs.quit();
        bs = null;
    }

    @Test
    public void testBrokerprofile() {
        /**
         * 验证安居客经纪人个人介绍
         */
        PublicProcess.dologin(bs, username, passwd);
        bs.get(url);
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
        ulSelect(Broker_profile.LIKEBOOK,
                Broker_profile.SELECTLIKEBOOK, "获取喜欢的书的列表", "获取选择书的列表");

        // 喜欢的食物
        ArrayList<String> likefood = new ArrayList<String>();
        ulSelect(Broker_profile.LIKEFOOD,
                Broker_profile.SELECTLIKEFOOD, "获取喜欢的食物的列表", "获取喜欢的食物的列表");
        for( String aa : likefood){
            System.out.println("介绍页："+aa);
        }
        // 娱乐活动
        ArrayList<String> recretional = new ArrayList<String>();
        ulSelect(Broker_profile.RECRETIONAL,
                Broker_profile.SELECTRECRETIONAL, "获取喜欢的娱乐活动", "获取娱乐活动列表");
        bs.click(Broker_profile.SAVE, "保存个人介绍修改");
        
        likebook = ulSelect1(Broker_profile.LIKEBOOK,"获取选择好的书的列表");
        likefood = ulSelect1(Broker_profile.LIKEFOOD,"获取选择好的食物的列表");
        recretional = ulSelect1(Broker_profile.RECRETIONAL,"获取选择好的娱乐活动");

        String actualText = bs.getText(Broker_profile.SAVETEXT, "获取保存成功文本");
        bs.assertContains(actualText, "保存成功");
        for( String aa : recretional){
            System.out.println("介绍页："+aa);
        }

        /**
         *  到我的店铺验证 ，个人介绍是否生效
         */
        bs.click(Broker_profile.MYSHOP, "进入我的店铺");
        bs.switchWindo(2);
        bs.click(Broker_shopprofile.SELFINTRO, "进入自我介绍页");
        String provinceactual = bs.getText(Broker_shopprofile.PROVINCE, "获取出生地省份");
        String cityactual = bs.getText(Broker_shopprofile.CITY, "获取出生地城市");
        String lunaractual = bs.getText(Broker_shopprofile.LUNAR, "获取生肖");
        String constellation1 = bs.getText(Broker_shopprofile.CONSTELLATION, "获取星座");
        String actualText1 = provinceactual+cityactual+lunaractual+constellation1;
        bs.assertContains(actualText1.trim(), expectedText.trim());

        //我的店铺页获取喜欢的书，与个人介绍页对比
        WebElement shoplikebook = bs.findElement(Broker_shopprofile.LIKEBOOK, "获取喜欢的书", 60);
        List<WebElement> likebooks = shoplikebook.findElements(By.tagName("a"));
        boolean tag = true;
        for( WebElement aa : likebooks){
            System.out.println("店铺页："+aa.getText());
        }
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
        for( WebElement aa : likefoods){
            System.out.println("店铺页："+aa.getText());
        }
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
        for( WebElement aa : rentionals){
            System.out.println("店铺页："+aa.getText());
        }
        if(tag){
            bs.assertTrue(true, "验证娱乐活动", "娱乐活动在我的店铺和个人介绍页一致");
        }
    }

    private void ulSelect(String ullocator, String tdlocator,
            String Info1, String Info2){
        /**
         * 安居客 经济人个人介绍 的 喜欢的图书，喜欢的食物，娱乐活动的公用函数
         */
        WebElement ul = bs.findElement(ullocator, Info1, 60);
        for (WebElement op : ul.findElements(By.tagName("a"))) {// 将所有喜欢的列表清除
            op.click();
        }
        ul = null;
        WebElement td = bs.findElement(tdlocator, Info2, 60);
        int len = td.findElements(By.tagName("a")).size();// 获取供选择的长度
        List<WebElement> tds = td.findElements(By.tagName("a"));
        for (int i = 0; i < 5; i++) {// 选择喜欢的
            tds.get(GetRandom.getrandom(len)).click();
        }
    }
    private ArrayList<String> ulSelect1(String ullocator,String Info1){
        /**
         * 安居客 经济人个人介绍 的 喜欢的图书，喜欢的食物，娱乐活动的公用函数
         */
        WebElement ul1 = bs.findElement(ullocator, Info1, 60); // 重新获取喜欢的列表
        int ul1len = ul1.findElements(By.tagName("a")).size();// 选择好的喜欢的列表的长度
        List<WebElement> ullist = ul1.findElements(By.tagName("a")); // 获取选择好的喜欢
        ArrayList<String> selected = new ArrayList<String>();
        for (int i = 0; i < ul1len; i++) {
            selected.add(ullist.get(i).getText());
        }
        ul1 = null;
        return selected;
    }
}
