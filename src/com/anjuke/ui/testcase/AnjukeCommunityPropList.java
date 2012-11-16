package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjukeinc.iata.ui.util.GetRandom;
import com.anjuke.ui.page.*;

/**
 * 该测试用例主要用来检查小区二手房列表页筛选后房源正确与否，逻辑如下
 * 1、进入上海小区列表页
 * 2、在第一页随机点一个小区
 * 3、点击tab进入小区二手房页
 * 4、进行单项筛选，验证结果是否符合筛选条件
 * @author ccyang
 */

public class AnjukeCommunityPropList {
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
    public void testCommunitySaleListCheck(){
    	bs.get("http://shanghai.anjuke.com/");
    	if(!bs.check(Ajk_HomePage.COMMTAB))
    	{bs.refresh();}
    	bs.click(Ajk_HomePage.COMMTAB, "点击小区tab");
    	bs.click(Ajk_Community.RandomComm, "在当前列表内随机点个小区");
    	bs.switchWindo(2);
    	bs.click(Ajk_CommunityView.navtabSale, "打开小区二手房页面");
    	
    	//检查单项筛选
    	checkOneRandomFliter("房型");
    	checkOneRandomFliter("售价");
    	checkOneRandomFliter("面积");
    }
    
    
    
    private void checkOneRandomFliter(String fliterType)
    {
    	int val = 0;
    	if(fliterType.equals("房型"))
    	{
    		int fliterHouseAll = bs.getElementCount("//div[@class='condition']/dl[1]/dd/a");
    		
        	//随机算出一个要点的筛选项
        	val = GetRandom.getrandom(fliterHouseAll);
        	String houseFliter = bs.getText(Ajk_CommunitySale.FliterHouseByVal(val), "看下要点的是哪个选项");
        	bs.click(Ajk_CommunitySale.FliterHouseByVal(val), "随便点一个房型筛选项");
        	
        	//当前页面房源数
        	int propCount = bs.getElementCount("//div[@id='properties']/ul/li");
        	propCount--;
        	for(int i = 1;i <= propCount;i++)
        	{
        		String thishouse = bs.getText(Ajk_CommunitySale.PropHouse(i), "获得第"+i+"套房源的房型");
        		//截取“x室”
        		thishouse = thishouse.substring(0,thishouse.lastIndexOf("室"));
        		int thishouseInt = Integer.parseInt(thishouse);
        		//判断是否与随机点的筛选项一致
        		if(houseFliter.equals("一室") && thishouseInt == 1)
        		{
        			Report.writeHTMLLog("小区二手房页单项筛选检查", "第 "+i+" 套房源的房型是一室", Report.DONE, "");
        		}
        		else if (houseFliter.equals("二室") && thishouseInt == 2)
        		{
        			Report.writeHTMLLog("小区二手房页单项筛选检查", "第 "+i+" 套房源的房型是二室", Report.DONE, "");
        		}
        		else if (houseFliter.equals("三室") && thishouseInt == 3)
        		{
        			Report.writeHTMLLog("小区二手房页单项筛选检查", "第 "+i+" 套房源的房型是三室", Report.DONE, "");
        		}
        		else if (houseFliter.equals("四室") && thishouseInt == 4)
        		{
        			Report.writeHTMLLog("小区二手房页单项筛选检查", "第 "+i+" 套房源的房型是四室", Report.DONE, "");
        		}
        		else if (houseFliter.equals("五室") && thishouseInt == 5)
        		{
        			Report.writeHTMLLog("小区二手房页单项筛选检查", "第 "+i+" 套房源的房型是五室", Report.DONE, "");
        		}
        		else if (houseFliter.equals("五室以上") && thishouseInt > 5)
        		{
        			Report.writeHTMLLog("小区二手房页单项筛选检查", "第 "+i+" 套房源的房型是五室以上", Report.DONE, "");
        		}
        		else{
        			String ps = bs.printScreen();
        			Report.writeHTMLLog("小区二手房页单项筛选检查", "第 "+i+" 套房源的房型不符合筛选项。**期望："+houseFliter+" =vs= 实际："+thishouse+"室**", Report.FAIL, ps);
        		}
        	}
    	}
    	
    	else if(fliterType.equals("售价"))
    	{
    		int fliterPriceAll = bs.getElementCount("//div[@class='condition']/dl[2]/dd/a");
        	//随机算出一个要点的筛选项
        	val = GetRandom.getrandom(fliterPriceAll);
        	String priceFliter = bs.getText(Ajk_CommunitySale.FliterPriceByVal(val), "看下要点的是哪个选项");
        	bs.click(Ajk_CommunitySale.FliterPriceByVal(val), "随便点一个售价筛选项");
        	
        	int priceLow = 0;
        	int priceHigh = 0;
        	//分类处理处理XXX以上，xxx以下
        	if(priceFliter.contains("以上"))
        	{
        		priceLow = Integer.parseInt(priceFliter.substring(0,priceFliter.lastIndexOf("万以上")));
        		priceHigh = -1;
        	}
        	else if(priceFliter.contains("以下"))
        	{
        		priceLow = -1;
        		priceHigh = Integer.parseInt(priceFliter.substring(0,priceFliter.lastIndexOf("万以下")));
        	}
        	else if(priceFliter.contains("-"))
        	{
        		priceLow = Integer.parseInt(priceFliter.substring(0,priceFliter.lastIndexOf("-")));
            	priceHigh = Integer.parseInt(priceFliter.substring(priceFliter.lastIndexOf("-")+1,priceFliter.lastIndexOf("万")));
        	}
        	else{
        		System.out.println(priceFliter+" 这个价格筛选条件我不认识");
        		return;
        	}
        	double thisprice = 0;
        	if(priceHigh == -1)
        	{
        		int propCount = bs.getElementCount("//div[@id='properties']/ul/li");
            	propCount--;
            	for(int i = 1;i <= propCount;i++)
            	{
            		thisprice = Double.parseDouble(bs.getText(Ajk_CommunitySale.PropPrice(i), "获得第"+i+"套房源的总价"));
            		if(thisprice > priceLow)
            		{
            			//right
            			Report.writeHTMLLog("小区二手房页单项筛选检查", "第 "+i+" 套房源的价格在 "+priceFliter+" 范围内", Report.DONE, "");
            		}
            		else
            		{
            			//wrong
            			Report.writeHTMLLog("小区二手房页单项筛选检查", "第 "+i+" 套房源的价格： "+thisprice+"不在 "+priceFliter+" 范围内", Report.FAIL, "");
            		}
            	}
        	}
        	else
        	{
        		int propCount = bs.getElementCount("//div[@id='properties']/ul/li");
            	propCount--;
            	for(int i = 1;i <= propCount;i++)
            	{
            		thisprice = Double.parseDouble(bs.getText(Ajk_CommunitySale.PropPrice(i), "获得第"+i+"套房源的总价"));
            		if((thisprice > priceLow) && (thisprice <= priceHigh))
            		{
            			//right
            			Report.writeHTMLLog("小区二手房页单项筛选检查", "第 "+i+" 套房源的价格在 "+priceFliter+" 范围内", Report.DONE, "");
            		}
            		else
            		{
            			//wrong
            			Report.writeHTMLLog("小区二手房页单项筛选检查", "第 "+i+" 套房源的价格： "+thisprice+"不在 "+priceFliter+" 范围内", Report.FAIL, "");
            		}

            	}
        	}

    	}
    	else if(fliterType.equals("面积"))
    	{
    		int fliterAreaAll = bs.getElementCount("//div[@class='condition']/dl[3]/dd/a");
        	//随机算出一个要点的筛选项
        	val = GetRandom.getrandom(fliterAreaAll);
        	String areaFliter = bs.getText(Ajk_CommunitySale.FliterAreaByVal(val), "看下要点的是哪个选项");
        	bs.click(Ajk_CommunitySale.FliterAreaByVal(val), "随便点一个面积筛选项");
        	
        	int areaLow = 0;
        	int areaHigh = 0;
        	//分类处理处理XXX以上，xxx以下
        	if(areaFliter.contains("以上"))
        	{
        		areaLow = Integer.parseInt(areaFliter.substring(0,areaFliter.lastIndexOf("平米以上")));
        		areaHigh = -1;
        	}
        	else if(areaFliter.contains("以下"))
        	{
        		areaLow = -1;
        		areaHigh = Integer.parseInt(areaFliter.substring(0,areaFliter.lastIndexOf("平米以下")));
        	}
        	else if(areaFliter.contains("-"))
        	{
        		areaLow = Integer.parseInt(areaFliter.substring(0,areaFliter.lastIndexOf("-")));
        		areaHigh = Integer.parseInt(areaFliter.substring(areaFliter.lastIndexOf("-")+1,areaFliter.lastIndexOf("平米")));
        	}
        	else{
        		System.out.println(areaFliter+" 这个面积筛选条件我不认识");
        		return;
        	}
        	double thisarea = 0;
        	if(areaHigh == -1)
        	{
        		int propCount = bs.getElementCount("//div[@id='properties']/ul/li");
            	propCount--;
            	for(int i = 1;i <= propCount;i++)
            	{
            		thisarea = Double.parseDouble(bs.getText(Ajk_CommunitySale.PropArea(i), "获得第"+i+"套房源的面积").replace("m2", ""));
            		if(thisarea > areaLow)
            		{
            			//right
            			Report.writeHTMLLog("小区二手房页单项筛选检查", "第 "+i+" 套房源的面积在 "+areaFliter+" 范围内", Report.DONE, "");
            		}
            		else
            		{
            			//wrong
            			Report.writeHTMLLog("小区二手房页单项筛选检查", "第 "+i+" 套房源的面积： "+thisarea+"不在 "+areaFliter+" 范围内", Report.FAIL, "");
            		}
            	}
        	}
        	else
        	{
        		int propCount = bs.getElementCount("//div[@id='properties']/ul/li");
            	propCount--;
            	for(int i = 1;i <= propCount;i++)
            	{
            		thisarea = Double.parseDouble(bs.getText(Ajk_CommunitySale.PropArea(i), "获得第"+i+"套房源的面积").replace("m2", ""));
            		if((thisarea > areaLow) && (thisarea <= areaHigh))
            		{
            			//right
            			Report.writeHTMLLog("小区二手房页单项筛选检查", "第 "+i+" 套房源的面积在 "+areaFliter+" 范围内", Report.DONE, "");
            		}
            		else
            		{
            			//wrong
            			Report.writeHTMLLog("小区二手房页单项筛选检查", "第 "+i+" 套房源的面积： "+thisarea+"不在 "+areaFliter+" 范围内", Report.FAIL, "");
            		}

            	}
        	}

        	
    	}
    	//传入的fliterType无法匹配
    	else{
    		System.out.println("请传入正确的参数：房型||售价||面积");
    		return;
    	}
    	//检查筛选结果的房源套数与分页
    	if(!bs.check("//div[@id='properties']"))
    	{
    		String ps = bs.printScreen();
    		Report.writeHTMLLog("小区二手房页单项筛选检查", "点 "+fliterType+" 的第 "+val+" 个筛选项，筛选后无房源", Report.FAIL, ps);
    	}
    	int propCount = 0;
    	int lastPageExpect = 0;
    	int lastPageNow = 0;
    	propCount = Integer.parseInt(bs.getText(Ajk_CommunitySale.PROPCOUNT, "获得当前筛选条件下总房源数"));
    	//获得页码，并去掉"1/"
    	lastPageNow = Integer.parseInt(bs.getText(Ajk_CommunitySale.PageNum, "获得总页码").replace("1/", ""));
    	//总条数/每页显示的条数=总页数
    	lastPageExpect = propCount/20;
    	//最后一页的条数
    	int mod = propCount%20;
        if(mod != 0)
        	lastPageExpect++;
        if(lastPageNow == lastPageExpect)
        {
        	//分页正确
        	Report.writeHTMLLog("小区二手房页单项筛选检查", "分页数正确", Report.PASS, "");
        }
        else{
        	//分页错误
    		String ps = bs.printScreen();
    		Report.writeHTMLLog("小区二手房页单项筛选检查", "分页数错误，期望："+lastPageExpect+"&&实际："+lastPageNow, Report.FAIL, ps);        	
        }
        if(lastPageNow>1)
        {
        	bs.click(Ajk_CommunitySale.NextPageUP, "翻到下一页");
        	bs.findElement("//div[@id='properties']", "找到第二页的房源", 5);
        }
        bs.click(Ajk_CommunityView.navtabSale, "清空筛选条件");
    }

}


