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

public class AnjukeSaleTagCheck {
    Browser driver = null;
    AjkSaleSearchAction ass = null;
    
    @BeforeMethod
    public void setUp() {
    	Report.G_CASECONTENT = "二手房列表页标签检测";
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
		driver.get("http://shenzhen.anjuke.com/sale/");
		driver.click(ass.getABFliterLocater("宝安"), "点击宝安区域的筛选条件");
		driver.click(ass.getABFliterLocater("龙华"), "点击龙华板块的筛选条件");
		driver.click(ass.getOtherFliterLocater("120-150万"), "点击100-150万的筛选条件");
		driver.click(ass.getOtherFliterLocater("70-90平米"), "点击70-90平米的筛选条件");
		
//		checkTag(driver,"急售","sale");
//		checkTag(driver,"新推","sale");
		checkTag(driver,"多图","sale");
		
		driver.get("http://shenzhen.anjuke.com/sale/");
		checkTag(driver,"个人","sale");
    }

    @Test
    public void testSaleKeywordTagCheck(){
		driver.get("http://shenzhen.anjuke.com/sale/");
		driver.type(Ajk_Sale.KwInput, "公园大地四室", "输入关键词");
		driver.click(Ajk_Sale.KwSubmit, "点击搜索按钮");
		
//		checkTag(driver,"急售","sale");
//		checkTag(driver,"新推","sale");
		checkTag(driver,"多图","sale");
    }
    
    public void checkTag(Browser driver,String tagType,String listType)
    {
//    	if(tagType.equals("急售")||tagType.equals("急推"))
//    	{
//        	//切换tag
//        	driver.click(Ajk_Sale.TagHot, "点击急售tag");       		
//        	checkHotNewOwnerTagImage(tagType,listType);
//    	}
//    	else if(tagType.equals("新推")){
//        	//切换tag
//        	driver.click(Ajk_Sale.TagNew, "点击新推tag");
//        	checkHotNewOwnerTagImage(tagType,listType);
//    	}
//    	else 
    	if(tagType.equals("个人")){
        	//切换tag
        	driver.click(Ajk_Sale.TagOwner, "点击个人tag");
        	checkHotNewOwnerTagImage(tagType,listType);
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
        		//有房源的话再检查多图tag的src
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
    
	private void checkHotNewOwnerTagImage(String tagType,String listType){
		
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
    		//获取标签总数
    		int tagNum = driver.getElementCount("//div[@class='tags']/img");
    		//获取当前页面房源总数
    		int listCount = driver.getElementCount("//ol[@id='list_body']/li");
    		
    		if(tagNum == listCount){
			//everything is right
    		}
    		else if((listCount - tagNum) < 5){
    			Report.writeHTMLLog("列表页标签检查","当前筛选条件下，有房源缺少 "+ tagType + " 标签，但数量小于5个", Report.WARNING, "");
    		}
    		else if((listCount - tagNum) > 4){
    			Report.writeHTMLLog("列表页标签检查","当前筛选条件下，有房源缺少 "+ tagType + " 标签，且数量大于等于5个", Report.FAIL, "");
    		}
    		for(int n = 1; n<= listCount; n++)
    		{
    			if(driver.check(Ajk_Sale.getTags(n),3))
    			{
    				tagTitle = driver.getAttribute(Ajk_Sale.getTags(n), "title");
        			tagPicSrc = driver.getAttribute(Ajk_Sale.getTags(n), "src");
        			if(tagType.equals("急售")||tagType.equals("急推")){
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
        			else if(tagType.equals("新推")){
        				if(!tagTitle.equals("新推")){
            				Report.writeHTMLLog("列表页标签检查","第 "+n+" 套房源的 "+tagType+"标签 ，图片title有问题", Report.FAIL, "");
            			}
            			if(!tagPicSrc.contains("tag_new_32x32.gif")){
            				Report.writeHTMLLog("列表页标签检查","第 "+n+" 套房源的 "+tagType+"标签 ，图片地址有问题", Report.FAIL, "");
            			}
        			}
        			else if(tagType.equals("个人")){
        				if(!tagTitle.equals("个人")){
            				Report.writeHTMLLog("列表页标签检查","第 "+n+" 套房源的 "+tagType+"标签 ，图片title有问题", Report.FAIL, "");
            			}
            			if(!tagPicSrc.contains("tag_owner_32x32.gif")){
            				Report.writeHTMLLog("列表页标签检查","第 "+n+" 套房源的 "+tagType+"标签 ，图片地址有问题", Report.FAIL, "");
            			}
        			}
    			}
    		}
    		if(driver.check(Ajk_Sale.NextPage))
    		{
    			driver.click(Ajk_Sale.NextPage, "翻到下一页");
    			int listCountn = driver.getElementCount("//ol[@id='list_body']/li");
        		int tagNumn = driver.getElementCount("//div[@class='tags']/img");;
        		
        		if(tagNumn == listCountn){
    			//everything is right
        		}
        		else if((listCount - tagNum) < 5){
        			Report.writeHTMLLog("列表页标签检查","当前筛选条件下，有房源缺少 "+ tagType + " 标签，但数量小于5个", Report.WARNING, "");
        		}
        		else if((listCount - tagNum) > 4){
        			Report.writeHTMLLog("列表页标签检查","当前筛选条件下，有房源缺少 "+ tagType + " 标签，且数量大于等于5个", Report.FAIL, "");
        		}
        		for(int nn = 1; nn<= listCountn; nn++)
        		{
        			if(driver.check(Ajk_Sale.getTags(nn),3)){
            			tagTitle = driver.getAttribute(Ajk_Sale.getTags(nn), "title");
            			tagPicSrc = driver.getAttribute(Ajk_Sale.getTags(nn), "src");
            			if(tagType.equals("急售")||tagType.equals("急推")){
                			if(!tagTitle.equals("急推")&&listType.equals("sale")){
                				Report.writeHTMLLog("列表页标签检查","第 "+nn+" 套房源的 "+tagType+"标签 ，图片title有问题", Report.FAIL, "");
                			}
                			if(!tagTitle.equals("急租")&&(listType.equals("rental")||listType.equals("rent"))){
                				Report.writeHTMLLog("列表页标签检查","第 "+nn+" 套房源的 "+tagType+"标签 ，图片title有问题", Report.FAIL, "");
                			}
                			if(!tagPicSrc.contains("tag_hot_32x32.gif")){
                				Report.writeHTMLLog("列表页标签检查","第 "+nn+" 套房源的 "+tagType+"标签 ，图片地址有问题", Report.FAIL, "");
                			}
            			}
            			else if(tagType.equals("新推")){
            				if(!tagTitle.equals("新推")){
                				Report.writeHTMLLog("列表页标签检查","第 "+nn+" 套房源的 "+tagType+"标签 ，图片title有问题", Report.FAIL, "");
                			}
                			if(!tagPicSrc.contains("tag_new_32x32.gif")){
                				Report.writeHTMLLog("列表页标签检查","第 "+nn+" 套房源的 "+tagType+"标签 ，图片地址有问题", Report.FAIL, "");
                			}
            			}
            			else if(tagType.equals("个人")){
            				if(!tagTitle.equals("个人")){
                				Report.writeHTMLLog("列表页标签检查","第 "+nn+" 套房源的 "+tagType+"标签 ，图片title有问题", Report.FAIL, "");
                			}
                			if(!tagPicSrc.contains("tag_owner_32x32.gif")){
                				Report.writeHTMLLog("列表页标签检查","第 "+nn+" 套房源的 "+tagType+"标签 ，图片地址有问题", Report.FAIL, "");
                			}
            			}        			
            		}
        		}
    		}
        }
	}
}
