package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjuke.ui.page.*;
import com.anjuke.ui.publicfunction.AjkSaleSearchAction;

/**
 * 该测试用例主要完成: 1、筛选后验证新推、急售、多图标签  2、搜索后验证新推、急售、多图标签 
 * @author ccyang
 **/

public class AnjukeSaleRentListTagCheck {
    Browser driver = null;
    AjkSaleSearchAction ass = null;
    
    @BeforeMethod
    public void setUp() {
    	driver = FactoryBrowser.factoryBrowser();
    	ass = new AjkSaleSearchAction(driver);
    }
    @AfterMethod
    public void tearDown(){
    	driver.quit();
    	driver = null;
    }
    @Test
    public void testSaleFliterTagCheck(){
		driver.get("http://shanghai.anjuke.com/sale/");
		driver.click(ass.getLocater("普陀"), "点击浦东区域的筛选条件");
		driver.click(ass.getLocater("万里"), "点击碧云板块的筛选条件");
		driver.click(ass.getLocater("200-250万"), "点击200-250万的筛选条件");
		driver.click(ass.getLocater("90-110平米"), "点击90-110平米的筛选条件");
		driver.click(ass.getLocater("二室"), "点击2室的筛选条件");
		
		checkTag(driver,"急售","sale");
		checkTag(driver,"新推","sale");
		checkTag(driver,"多图","sale");
    }
    @Test
    public void testRentalFliterTagCheck(){
		driver.get("http://shanghai.anjuke.com/rental/");
		driver.click(ass.getLocater("徐汇"), "点击浦东区域的筛选条件");
		driver.click(ass.getLocater("徐家汇"), "点击碧云板块的筛选条件");
		driver.click(ass.getLocater("三室"), "点击3室的筛选条件");
		
		checkTag(driver,"急售","rent");
		checkTag(driver,"新推","rent");
		checkTag(driver,"多图","rent");
    }
    
    @Test
    public void testSaleKeywordTagCheck(){
		driver.get("http://shanghai.anjuke.com/sale/");
		driver.type(Ajk_Sale.KwInput, "康桥半岛四室别墅", "输入关键词");
		driver.click(Ajk_Sale.KwSubmit, "点击搜索按钮");
		
		checkTag(driver,"急售","sale");
		checkTag(driver,"新推","sale");
		checkTag(driver,"多图","sale");
    }
    
    public void checkTag(Browser driver,String tagType,String listType)
    {
    	if(tagType.equals("急售")||tagType.equals("急推"))
    	{
        	//切换tag
        	driver.click(Ajk_Sale.TagHot, "点击急售tag");

        	//检查急售tag下是否有房源
        	String propCount = driver.getText(Ajk_Sale.S_COUNT,"看看当前tag下有多少房源");
        	if(Integer.parseInt(propCount) < 1)
        	{
        		Report.writeHTMLLog("列表页标签检查","当前筛选条件下，选择 "+ tagType + " 标签时没有房源", Report.WARNING, "");
        	}
        	else{

        		//有房源的话再检查tag图标的title、src
        		String tagTitle = null;
        		String tagPicSrc = null;
        		int listCount = driver.getElementCount("//ol[@id='list_body']/li");
        		for(int n = 1; n<= listCount; n++)
        		{
        			tagTitle = driver.getAttribute(Ajk_Sale.getTags(n), "title");
        			tagPicSrc = driver.getAttribute(Ajk_Sale.getTags(n), "src");
        			if(!tagTitle.equals("急推")&&listType.equals("sale")){
        				Report.writeHTMLLog("列表页标签检查","第 "+n+" 套房源的 "+tagType+"标签 ，图片title有问题", Report.FAIL, "");
        			}
        			if(!tagTitle.equals("急租")&&(listType.equals("rental")||listType.equals("rent"))){
        				Report.writeHTMLLog("列表页标签检查","第 "+n+" 套房源的 "+tagType+"标签 ，图片title有问题", Report.FAIL, "");
        			}
        			if(!tagPicSrc.contains("tag_hot_32x32.gif")){
        				Report.writeHTMLLog("列表页标签检查","第 "+n+" 套房源的 "+tagType+"标签 ，图片地址有问题", Report.FAIL, "");
        			}
        		}
        		
        		if(driver.check(Ajk_Sale.NextPage))
        		{
        			driver.click(Ajk_Sale.NextPage, "翻到下一页");
        			int listCountn = driver.getElementCount("//ol[@id='list_body']/li");
            		for(int nn = 1; nn<= listCountn; nn++)
            		{
            			tagTitle = driver.getAttribute(Ajk_Sale.getTags(nn), "title");
            			tagPicSrc = driver.getAttribute(Ajk_Sale.getTags(nn), "src");
            			if(!tagTitle.equals("急推")){
            				System.out.println(tagTitle);
            				Report.writeHTMLLog("列表页标签检查","第 2 页的第 "+nn+" 套房源的 "+tagType+"标签 ，图片title有问题", Report.FAIL, "");
            			}
            			if(!tagPicSrc.contains("tag_hot_32x32.gif")){
            				System.out.println(tagPicSrc);
            				Report.writeHTMLLog("列表页标签检查","第 2 页的第 "+nn+" 套房源的 "+tagType+"标签 ，图片地址有问题", Report.FAIL, "");
            			}
            		}
        		}
        		
            		
            }


    	}
    	else if(tagType.equals("新推")){
        	//切换tag
        	driver.click(Ajk_Sale.TagNew, "点击新推tag");
        	//检查急售tag下是否有房源
        	String propCount = driver.getText(Ajk_Sale.S_COUNT,"看看当前tag下有多少房源");
        	if(Integer.parseInt(propCount) < 1)
        	{
        		Report.writeHTMLLog("列表页标签检查","当前筛选条件下，选择 "+ tagType + " 标签，没有房源", Report.WARNING, "");
        	}
        	else{
        		//有房源的话再检查tag图标的title、src
        		String tagTitle = null;
        		String tagPicSrc = null;
        		int listCount = driver.getElementCount("//ol[@id='list_body']/li");
        		for(int n = 1; n<= listCount; n++)
        		{
        			tagTitle = driver.getAttribute(Ajk_Sale.getTags(n), "title");
        			tagPicSrc = driver.getAttribute(Ajk_Sale.getTags(n), "src");
        			if(!tagTitle.equals("新推")){
        				Report.writeHTMLLog("列表页标签检查","第 "+n+" 套房源的 "+tagType+"标签 ，图片title有问题", Report.FAIL, "");
        			}
        			if(!tagPicSrc.contains("tag_new_32x32.gif")){
        				Report.writeHTMLLog("列表页标签检查","第 "+n+" 套房源的 "+tagType+"标签 ，图片地址有问题", Report.FAIL, "");
        			}
        		}
        		if(driver.check(Ajk_Sale.NextPage))
        		{
        			driver.click(Ajk_Sale.NextPage, "翻到下一页");
        			int listCountn = driver.getElementCount("//ol[@id='list_body']/li");
            		for(int nn = 1; nn<= listCountn; nn++)
            		{
            			tagTitle = driver.getAttribute(Ajk_Sale.getTags(nn), "title");
            			tagPicSrc = driver.getAttribute(Ajk_Sale.getTags(nn), "src");
            			if(!tagTitle.equals("新推")){
            				Report.writeHTMLLog("列表页标签检查","第 2 页的第 "+nn+" 套房源的 "+tagType+"标签 ，图片title有问题", Report.FAIL, "");
            			}
            			if(!tagPicSrc.contains("tag_new_32x32.gif")){
            				Report.writeHTMLLog("列表页标签检查","第 2 页的第 "+nn+" 套房源的 "+tagType+"标签 ，图片地址有问题", Report.FAIL, "");
            			}
            		}
        		}
        		
        	}
    	}
    	else if(tagType.equals("个人")){
        	//切换tag
        	driver.click(Ajk_Sale.TagOwner, "点击个人tag");
        	//检查急售tag下是否有房源
        	String propCount = driver.getText(Ajk_Sale.S_COUNT,"看看当前tag下有多少房源");
        	if(Integer.parseInt(propCount) < 1)
        	{
        		Report.writeHTMLLog("列表页标签检查","当前筛选条件下，选择 "+ tagType + " 标签，没有房源", Report.WARNING, "");
        	}
        	else{
        		//有房源的话再检查tag图标的title、src
        		String tagTitle = null;
        		String tagPicSrc = null;
        		int listCount = driver.getElementCount("//ol[@id='list_body']/li");
        		for(int n = 1; n<= listCount; n++)
        		{
        			tagTitle = driver.getAttribute(Ajk_Sale.getTags(n), "title");
        			tagPicSrc = driver.getAttribute(Ajk_Sale.getTags(n), "src");
        			if(!tagTitle.equals("个人")){
        				Report.writeHTMLLog("列表页标签检查","第 "+n+" 套房源的 "+tagType+"标签 ，图片title有问题", Report.FAIL, "");
        			}
        			if(!tagPicSrc.contains("tag_owner_32x32.gif")){
        				Report.writeHTMLLog("列表页标签检查","第 "+n+" 套房源的 "+tagType+"标签 ，图片地址有问题", Report.FAIL, "");
        			}
        		}
        		if(driver.check(Ajk_Sale.NextPage))
        		{
        			driver.click(Ajk_Sale.NextPage, "翻到下一页");
        			int listCountn = driver.getElementCount("//ol[@id='list_body']/li");
            		for(int nn = 1; nn<= listCountn; nn++)
            		{
            			tagTitle = driver.getAttribute(Ajk_Sale.getTags(nn), "title");
            			tagPicSrc = driver.getAttribute(Ajk_Sale.getTags(nn), "src");
            			if(!tagTitle.equals("个人")){
            				Report.writeHTMLLog("列表页标签检查","第 2 页的第 "+nn+" 套房源的 "+tagType+"标签 ，图片title有问题", Report.FAIL, "");
            			}
            			if(!tagPicSrc.contains("tag_owner_32x32.gif")){
            				Report.writeHTMLLog("列表页标签检查","第 2 页的第 "+nn+" 套房源的 "+tagType+"标签 ，图片地址有问题", Report.FAIL, "");
            			}
            		}
        		}
        		
        	}
    	}
    	else if(tagType.equals("多图")){
        	//切换tag
        	driver.click(Ajk_Sale.TagMore, "点击多图tag");
        	//检查急售tag下是否有房源
        	String propCount = driver.getText(Ajk_Sale.S_COUNT,"看看当前tag下有多少房源");
        	if(Integer.parseInt(propCount) < 1)
        	{
        		Report.writeHTMLLog("列表页标签检查","当前筛选条件下，选择 "+ tagType + " 标签，没有房源", Report.WARNING, "");
        	}
        	else{
        		//有房源的话再检查tag图标的title、src
        		String tagPicSrc = null;
        		int listCount = driver.getElementCount("//ol[@id='list_body']/li");
        		for(int n = 1; n<= listCount; n++)
        		{
        			tagPicSrc = driver.getAttribute(Ajk_Sale.getTagMore(n), "src");
        			if(!tagPicSrc.contains("icon-more-28x16.gif")){
        				Report.writeHTMLLog("列表页标签检查","第 "+n+" 套房源的 "+tagType+"标签 ，图片地址有问题", Report.FAIL, "");
        			}
        		}
        		if(driver.check(Ajk_Sale.NextPage))
        		{
        			driver.click(Ajk_Sale.NextPage, "翻到下一页");
        			int listCountn = driver.getElementCount("//ol[@id='list_body']/li");
            		for(int n = 1; n<= listCountn; n++)
            		{
            			tagPicSrc = driver.getAttribute(Ajk_Sale.getTagMore(n), "src");
            			if(!tagPicSrc.contains("icon-more-28x16.gif")){
            				Report.writeHTMLLog("列表页标签检查","第 "+n+" 套房源的 "+tagType+"标签 ，图片地址有问题", Report.FAIL, "");
            			}
            		}
        		}
        		
        	}
    	}
    	
    	
    }
}
