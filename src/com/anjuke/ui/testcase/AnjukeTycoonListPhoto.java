package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjuke.ui.page.*;

/**该测试用例用来检查经纪人列表里，有生活照的经纪人筛选结果是否正确
 * 包括：
 * 经纪人店铺自我介绍生活照页是否有照片
 * 
 * @author jchen
 */


public class AnjukeTycoonListPhoto {
    Browser bs = null;

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
    public void testBrokerListPhoto(){
    	bs.get("http://shanghai.anjuke.com");
    	if(!bs.check("Ajk_HomePage.H_BTN"))
    	{bs.refresh();}
    	bs.click(Ajk_HomePage.H_BTN, "点击TAB里的二手房链接");
    	//进入二手房列表页
    	bs.click("//div[@class='divBrokerList']/a", "点击页面最下方经纪人列表页入口链接");
    	bs.switchWindo(2);
    	//点击二手房列表页最下方"现在去找"经纪人列表页入口链接
    	bs.click(Ajk_Tycoon.photo, "点击筛选是否有生活照复选框");
    	//筛选是否有生活照
    	
    	bs.click(Ajk_Tycoon.headimg, "点击经纪人头像");
    	bs.switchWindo(3);
    	//切换到经纪人店铺
    	
    	bs.click(Ajk_ShopView.ProfileTab, "点击经纪人店铺自我介绍tab");
    	//点击经纪人店铺自我介绍tab
    	
    	bs.click(Ajk_ShopProfile.photo, "点击经纪人店铺自我介绍页生活照链接");
    	//点击经纪人店铺自我介绍页生活照链接
    	
    	if(bs.getElementCount("//*[@class='nophoto']/dl/dd") != 0)
    	{
    		System.out.println("这个经纪人无生活照");
    		Report.writeHTMLLog("有生活照的经纪人筛选结果不正确", "筛选出的经纪人无生活照", Report.FAIL, bs.printScreen());

    	}
    	else
    	{
    		System.out.println("这个经纪人有生活照");
    		Report.writeHTMLLog("有生活照的经纪人筛选结果正确", "筛选出的经纪人有生活照", Report.PASS, "");
    	}
    	//bs.closeAllwindow();
    	bs.close();
    }
}
