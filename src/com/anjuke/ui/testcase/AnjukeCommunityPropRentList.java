package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjukeinc.iata.ui.util.GetRandom;
import com.anjuke.ui.page.*;

/**该测试用例用来检查小区租房列表
 * 检查筛选房型、租金、装修条件下对应房源是否正确
 * 检查分页是否正确
 * 检查分页数大于1时，第二页是否有房源显示
 * @author jchen
 */


public class AnjukeCommunityPropRentList {
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
    public void testCommunityRentListCheck(){
    	bs.get("http://shanghai.anjuke.com/");
    	if(!bs.check(Ajk_HomePage.COMMTAB))
    	{bs.refresh();}
    	bs.click(Ajk_HomePage.COMMTAB, "点击小区tab");
    	bs.click(Ajk_Community.RandomComm, "在当前列表内随机点个小区");
    	bs.switchWindo(2);
    	bs.click(Ajk_CommunityView.navtabRent, "打开小区租房页面");
    	
    	//检查单项筛选
    	checkOneRandomFliter("房型");
    	checkOneRandomFliter("租金");
    	checkOneRandomFliter("装修");
    }
    
    
    
    private void checkOneRandomFliter(String fliterType)
    {
    	int val = 0;
    	if(fliterType.equals("房型"))
    	{
    		int fliterHouseAll = bs.getElementCount("//div[@class='condition']/dl[1]/dd/a");
    		
        	//随机算出一个要点的筛选项
        	val = GetRandom.getrandom(fliterHouseAll);
        	String houseFliter = bs.getText(Ajk_CommunityRent.FliterHouseByVal(val), "看下要点的是哪个选项");
        	bs.click(Ajk_CommunityRent.FliterHouseByVal(val), "随便点一个房型筛选项");
        	
        	//当前页面房源数
        	int propCount = bs.getElementCount("//div[@id='properties']/ul/li");
        	propCount--;
        	for(int i = 1;i <= propCount;i++)
        	{
        		String thishouse = bs.getText(Ajk_CommunityRent.PropHouse(i), "获得第"+i+"套房源的房型");
        		//截取“x室”
        		thishouse = thishouse.substring(0,thishouse.lastIndexOf("室"));
        		int thishouseInt = Integer.parseInt(thishouse);
        		//判断是否与随机点的筛选项一致
        		if(houseFliter.equals("一室") && thishouseInt == 1)
        		{
        			Report.writeHTMLLog("小区租房页单项筛选检查", "第 "+i+" 套房源的房型是一室", Report.DONE, "");
        		}
        		else if (houseFliter.equals("二室") && thishouseInt == 2)
        		{
        			Report.writeHTMLLog("小区租房页单项筛选检查", "第 "+i+" 套房源的房型是二室", Report.DONE, "");
        		}
        		else if (houseFliter.equals("三室") && thishouseInt == 3)
        		{
        			Report.writeHTMLLog("小区租房页单项筛选检查", "第 "+i+" 套房源的房型是三室", Report.DONE, "");
        		}
        		else if (houseFliter.equals("四室") && thishouseInt == 4)
        		{
        			Report.writeHTMLLog("小区租房页单项筛选检查", "第 "+i+" 套房源的房型是四室", Report.DONE, "");
        		}
        		else if (houseFliter.equals("五室") && thishouseInt == 5)
        		{
        			Report.writeHTMLLog("小区租房页单项筛选检查", "第 "+i+" 套房源的房型是五室", Report.DONE, "");
        		}
        		else if (houseFliter.equals("五室以上") && thishouseInt > 5)
        		{
        			Report.writeHTMLLog("小区租房页单项筛选检查", "第 "+i+" 套房源的房型是五室以上", Report.DONE, "");
        		}
        		else{
        			String ps = bs.printScreen();
        			Report.writeHTMLLog("小区租房页单项筛选检查", "第 "+i+" 套房源的房型不符合筛选项。**期望："+houseFliter+" =vs= 实际："+thishouse+"室**", Report.FAIL, ps);
        		}
        	}
    	}
    	
    	else if(fliterType.equals("租金"))
    	{
    		int fliterPriceAll = bs.getElementCount("//div[@class='condition']/dl[2]/dd/a");
        	//随机算出一个要点的筛选项
        	val = GetRandom.getrandom(fliterPriceAll);
        	String priceFliter = bs.getText(Ajk_CommunityRent.FliterPriceByVal(val), "看下要点的是哪个选项");
        	bs.click(Ajk_CommunityRent.FliterPriceByVal(val), "随便点一个租金筛选项");
        	
        	int priceLow = 0;
        	int priceHigh = 0;
        	//分类处理处理XXX以上，xxx以下
        	if(priceFliter.contains("以上"))
        	{
        		priceLow = Integer.parseInt(priceFliter.substring(0,priceFliter.lastIndexOf("元以上")));
        		priceHigh = -1;
        	}
        	else if(priceFliter.contains("以下"))
        	{
        		priceLow = -1;
        		priceHigh = Integer.parseInt(priceFliter.substring(0,priceFliter.lastIndexOf("元以下")));
        	}
        	else if(priceFliter.contains("-"))
        	{
        		priceLow = Integer.parseInt(priceFliter.substring(0,priceFliter.lastIndexOf("-")));
            	priceHigh = Integer.parseInt(priceFliter.substring(priceFliter.lastIndexOf("-")+1,priceFliter.lastIndexOf("元")));
        	}
        	else{
        		System.out.println(priceFliter+" 这个价格筛选条件我不认识");
        		return;
        	}
        	int thisprice = 0;
        	if(priceHigh == -1)
        	{
        		int propCount = bs.getElementCount("//div[@id='properties']/ul/li");
            	propCount--;
            	for(int i = 1;i <= propCount;i++)
            	{
            		thisprice = Integer.parseInt(bs.getText(Ajk_CommunityRent.PropPrice(i), "获得第"+i+"套房源的租金"));
            		if(thisprice > priceLow)
            		{
            			//right
            			Report.writeHTMLLog("小区租房页单项筛选检查", "第 "+i+" 套房源的租金在 "+priceFliter+" 范围内", Report.DONE, "");
            		}
            		else
            		{
            			//wrong
            			Report.writeHTMLLog("小区租房页单项筛选检查", "第 "+i+" 套房源的租金： "+thisprice+"不在 "+priceFliter+" 范围内", Report.FAIL, "");
            		}
            	}
        	}
        	else
        	{
        		int propCount = bs.getElementCount("//div[@id='properties']/ul/li");
            	propCount--;
            	for(int i = 1;i <= propCount;i++)
            	{
            		thisprice = Integer.parseInt(bs.getText(Ajk_CommunityRent.PropPrice(i), "获得第"+i+"套房源的租金"));
            		if((thisprice > priceLow) && (thisprice <= priceHigh))
            		{
            			//right
            			Report.writeHTMLLog("小区租房页单项筛选检查", "第 "+i+" 套房源的租金在 "+priceFliter+" 范围内", Report.DONE, "");
            		}
            		else
            		{
            			//wrong
            			Report.writeHTMLLog("小区租房页单项筛选检查", "第 "+i+" 套房源的租金： "+thisprice+"不在 "+priceFliter+" 范围内", Report.FAIL, "");
            		}

            	}
        	}

    	}
    	else if(fliterType.equals("装修"))
    	{
    		int fliterRenovationAll = bs.getElementCount("//div[@class='condition']/dl[3]/dd/a");
        	//随机算出一个要点的筛选项
        	val = GetRandom.getrandom(fliterRenovationAll);
        	String renovationFliter = bs.getText(Ajk_CommunityRent.FliterRenovationByVal(val), "看下要点的是哪个选项");
        	bs.click(Ajk_CommunityRent.FliterRenovationByVal(val), "随便点一个装修筛选项");
        	
        	
        	String thisrenovation = null;
        	int propCount = bs.getElementCount("//div[@id='properties']/ul/li");
            propCount--;
            for(int i = 1;i <= propCount;i++)
            	{
            		thisrenovation = bs.getText(Ajk_CommunityRent.PropRenovation(i), "获得第"+i+"套房源的装修");
            		if(renovationFliter.equals("毛坯")&& thisrenovation.equals("毛坯"))
            		{
            			Report.writeHTMLLog("小区租房页单项筛选检查", "第 "+i+" 套房源的装修是毛坯", Report.DONE, ""); 
            		}
            		else if(renovationFliter.equals("普通装修")&& thisrenovation.equals("普通装修"))
            		{
            			Report.writeHTMLLog("小区租房页单项筛选检查", "第 "+i+" 套房源的装修是普通装修 ", Report.DONE, "");
            		}
            		else if(renovationFliter.equals("精装修")&& thisrenovation.equals("精装修"))
            		{
            			Report.writeHTMLLog("小区租房页单项筛选检查", "第 "+i+" 套房源的装修是精装修 ", Report.DONE, "");
            		}
            		else if(renovationFliter.equals("豪华装修")&& thisrenovation.equals("豪华装修"))
            		{
            			Report.writeHTMLLog("小区租房页单项筛选检查", "第 "+i+" 套房源的装修是豪华装修 ", Report.DONE, "");
            		}
            		else{
            			String ps = bs.printScreen();
            			Report.writeHTMLLog("小区租房页单项筛选检查", "第 "+i+" 套房源的装修不符合筛选项。**期望："+renovationFliter+" =vs= 实际："+thisrenovation+"", Report.FAIL, ps);
            		}

            	}
        	}
        	
    	//传入的fliterType无法匹配
    	else{
    		System.out.println("请传入正确的参数：房型||租金||装修");
    		return;
    	}
    	//检查筛选结果的房源套数与分页
    	if(!bs.check("//div[@id='properties']"))
    	{
    		String ps = bs.printScreen();
    		Report.writeHTMLLog("小区租房页单项筛选检查", "点 "+fliterType+" 的第 "+val+" 个筛选项，筛选后无房源", Report.FAIL, ps);
    	}
    	int propCount = 0;
    	int lastPageExpect = 0;
    	int lastPageNow = 0;
    	propCount = Integer.parseInt(bs.getText(Ajk_CommunityRent.PROPCOUNT, "获得当前筛选条件下总房源数"));
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
        	Report.writeHTMLLog("小区租房页单项筛选检查", "分页数正确", Report.PASS, "");
        }
        else{
        	//分页错误
    		String ps = bs.printScreen();
    		Report.writeHTMLLog("小区租房页单项筛选检查", "分页数错误，期望："+lastPageExpect+"&&实际："+lastPageNow, Report.FAIL, ps);        	
        }
        //检查第二页是否有房源数据
        if(lastPageNow>1)
        {
        	bs.click(Ajk_CommunityRent.NextPageUP, "翻到下一页");
        	if(!bs.check("//div[@id='properties']"))
        	{
        	String ps = bs.printScreen();
        	Report.writeHTMLLog("小区租房页单项筛选检查", "第二页无数据", Report.FAIL, ps); 
        	}
	       		
        }
        bs.click(Ajk_CommunityView.navtabRent, "清空筛选条件");
    }

}


