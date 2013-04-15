package com.anjuke.ui.publicfunction;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.util.GetRandom;

/**
* @Todo TODO
* @author fjzhang
* @since 2012-8-15
* @file BrokerInfo.java
*
*/
public class BrokerInfo {
    public final void ulSelect(Browser bs, String ullocator, String tdlocator,
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
    public final ArrayList<String> ulSelect1(Browser bs, String ullocator,String Info1){
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

