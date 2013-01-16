package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjuke.ui.page.*;

/**检查经纪人ufs等级（无评价、被差评/钻石/皇冠） 在网站各页面的展示是否一致
*/
public class AnjukeUfsStarCheck {
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
    public void ufsStarCheck(){
    	checkStar();
    	checkBadEva();
//    	checkClown();
//    	checkNoEva();
    }
    private void checkStar()
    {
    	//打开经纪人服务排行榜
    	String lastgetcomment = "http://shanghai.anjuke.com/lastgetcomment";
    	bs.get(lastgetcomment);
    	bs.click(Ajk_LastGetComment.Area("卢湾"), "切换区域");
    	//列表第一个经纪人的服务等级
    	String dmdBang = bs.getAttribute(Ajk_LastGetComment.FirstBrokerStar, "class");
    	//只能检查class||图片能否？
    	bs.assertEquals(dmdBang, "diamond diamond1", "UFS排行榜-服务等级", "服务等级处的class是diamond");
    	//点第一个经纪人头像进入其店铺
    	bs.click(Ajk_LastGetComment.FirstBrokerImg, "点经纪人头像进入其店铺");
    	
    	bs.switchWindo(2);
    	//检查店铺首页的ufs服务等级
    	String dmdShopView = bs.getAttribute(Ajk_ShopView.Star, "class");
    	bs.assertEquals(dmdShopView, "diamond diamond1", "经纪人店铺首页-服务等级", "服务等级处的class是diamond");
    	//点二手房tab
    	bs.click(Ajk_ShopView.SaleListTab, "点二手房tab");
    	//检查二手房tab的ufs服务等级
    	String dmdShopSale = bs.getAttribute(Ajk_ShopView.Star, "class");
    	bs.assertEquals(dmdShopSale, "diamond diamond1", "经纪人店铺二手房tab-服务等级", "服务等级处的class是diamond");
    	
    	//看看经纪人有没有二手房房源
    	if(bs.check(Ajk_ShopSaleRentList.NoProp,5))
    	{
    		String ps = bs.printScreen();
    		Report.writeHTMLLog("经纪人服务等级", "这个经纪人没有发布二手房", Report.WARNING, ps);
    	}
    	else{
        	bs.click(Ajk_ShopSaleRentList.FirstPropName, "点第一套二手房的链接");
        	bs.switchWindo(3);
        	String dmdPropViewRight = bs.getAttribute(Ajk_PropView.Star_BrokerInfo, "class");
        	bs.assertEquals(dmdPropViewRight, "dmd-one", "经纪人二手房-右侧服务等级", "服务等级处的class是diamond");
        	String dmdPropViewDownufsinfo = bs.getAttribute(Ajk_PropView.Star_downufsinfo, "class");
        	bs.assertEquals(dmdPropViewDownufsinfo, "dmd-one", "经纪人二手房-下方UFS信息服务等级", "服务等级处的class是diamond");
        	bs.close();
        	bs.switchWindo(2);
    	}
//    	店铺没有租房了
//    	//点租房tab
//    	bs.click(Ajk_ShopView.RentListTab, "点租房tab");
//    	//看看经纪人有没有二手房房源
//    	if(bs.check(Ajk_ShopSaleRentList.NoProp,5))
//    	{
//    		String ps = bs.printScreen();
//    		Report.writeHTMLLog("经纪人服务等级", "这个经纪人没有发布租房", Report.DONE, ps);
//    	}
//    	else{
//        	bs.click(Ajk_ShopSaleRentList.FirstPropName, "点第一套租房的链接");
//        	bs.switchWindo(3);
//        	//租房单页 服务等级的locator和二手房单页一样
//        	String dmdPropViewRight = bs.getAttribute(Ajk_PropView.Star_BrokerInfo, "class");
//        	bs.assertEquals(dmdPropViewRight, "dmd-one", "经纪人租房-右侧服务等级", "服务等级处的class是diamond");
//        	String dmdPropViewDownufsinfo = bs.getAttribute(Ajk_PropView.Star_downufsinfo, "class");
//        	bs.assertEquals(dmdPropViewDownufsinfo, "dmd-one", "经纪人租房-下方UFS信息服务等级", "服务等级处的class是diamond");
//        	bs.close();
//        	bs.switchWindo(2);
//    	}
    	//点评价tab
    	bs.click(Ajk_ShopView.UfsTab, "点评价tab");
    	//经纪人店铺右侧通栏 服务等级locator通用
    	String dmdShopUfs = bs.getAttribute(Ajk_ShopView.Star, "class");
    	bs.assertEquals(dmdShopUfs, "diamond diamond1", "经纪人店铺评价页-左侧服务等级", "服务等级处的class是diamond");
    	bs.close();
    	bs.switchWindo(1);
    }
    private void checkBadEva()
    {

    	//打开经纪人服务排行榜
    	String lastgetcomment = "http://shanghai.anjuke.com/lastgetcomment";
    	bs.get(lastgetcomment);
    	bs.click(Ajk_LastGetComment.Area("卢湾"), "切换区域");
    	//这个区域下，总共有pageall个分页，仅用于第一页
    	int pageall = bs.getElementCount("//div[@class='multipage']/a");
    	//*[@class='multipage']/a[6]/span
    	bs.click("//div[@class='multipage']/a["+(pageall-1)+"]/span", "点最后一页");
    	
    	int UfsScore = Integer.parseInt(bs.getText(Ajk_LastGetComment.FirstBrokerScore, "取第一个经纪人的评价分"));
    	String StarBang = "";
    	switch(UfsScore/10)
    	{
    	case 1:
    	case 2:
    	case 3:
    	case 4:
    	case 5:
    		StarBang = bs.getText("//ul[@class='AJKBTMLM_blist clearfix']/li[1]/div[2]/dl[2]/dd", "text");
    		StarBang = StarBang.substring(0,StarBang.lastIndexOf("好评率")).trim();
    		bs.assertEquals(StarBang, "暂无", "UFS排行榜-服务等级", "经纪人评价分为："+UfsScore+"|||服务等级为：暂无");
    		break;
    	default:
        	//列表第一个经纪人的服务等级
    		StarBang = bs.getText(Ajk_LastGetComment.FirstBrokerStar, "text");
        	//只能检查class
        	bs.assertEquals(StarBang, "diamond diamond1", "UFS排行榜-服务等级", "服务等级处的class是diamond");
        	String ps = bs.printScreen();
        	Report.writeHTMLLog("检查ufs排行榜", "最后一页的经纪人也是钻石？那就没继续的必要了", Report.DONE, ps);
        	return;
    	}

    	//点第一个经纪人头像进入其店铺
    	bs.click(Ajk_LastGetComment.FirstBrokerImg, "点经纪人头像进入其店铺");
    	bs.switchWindo(2);
    	//检查店铺首页的ufs服务等级
    	String badShopView = bs.getText("//p[@class='grade']", "class");
    	badShopView = badShopView.replace("服务等级：", "");
    	badShopView = badShopView.replace('\n', ' ').replace(" ", "");
//    	System.out.println(badShopView);
//    	badShopView = badShopView.substring(0,badBang.lastIndexOf("（")).trim();
		bs.assertEquals(badShopView, "暂无", "经纪人店铺首页-服务等级", "经纪人服务等级为：暂无");
    	//点二手房tab
    	bs.click(Ajk_ShopView.SaleListTab, "点二手房tab");
    	//检查二手房tab的ufs服务等级
    	String badShopSale = bs.getText("//p[@class='grade']", "class");
    	badShopSale = badShopSale.replace("服务等级：", "");
    	badShopSale = badShopSale.replace('\n', ' ').replace(" ", "");
    	badShopSale = badShopSale.substring(0,badShopSale.lastIndexOf("(")).trim();
		bs.assertEquals(badShopSale, "暂无", "经纪人店铺二手房列表页-服务等级", "经纪人服务等级为：暂无");
		
    	//看看经纪人有没有二手房房源
    	if(bs.check(Ajk_ShopSaleRentList.NoProp,5))
    	{
    		String ps = bs.printScreen();
    		Report.writeHTMLLog("经纪人服务等级", "这个经纪人没有发布二手房", Report.WARNING, ps);
    	}
    	else{
        	bs.click(Ajk_ShopSaleRentList.FirstPropName, "点第一套二手房的链接");
        	bs.switchWindo(3);
        	//
        	String badPropViewRight = bs.getText(Ajk_PropView.Star_BrokerInfo, "获得服务等级");
        	badPropViewRight = badPropViewRight.replace(" ", "");
        	bs.assertEquals(badPropViewRight, "暂无", "经纪人二手房-右侧服务等级", "服务等级为暂无");
        	String badPropViewDownufsinfo = bs.getText(Ajk_PropView.Star_downufsinfo, "获得服务等级");
        	badPropViewDownufsinfo = badPropViewDownufsinfo.replaceAll(" ", "");
        	bs.assertEquals(badPropViewDownufsinfo, "暂无", "经纪人二手房-下方UFS信息服务等级", "服务等级为暂无");
        	bs.close();
        	bs.switchWindo(2);
    	}
// 		租房合并了
//    	//点租房tab
//    	bs.click(Ajk_ShopView.RentListTab, "点租房tab");
//    	//看看经纪人有没有二手房房源
//    	if(bs.check(Ajk_ShopSaleRentList.NoProp,5))
//    	{
//    		String ps = bs.printScreen();
//    		Report.writeHTMLLog("经纪人服务等级", "这个经纪人没有发布租房", Report.DONE, ps);
//    	}
//    	else{
//        	bs.click(Ajk_ShopSaleRentList.FirstPropName, "点第一套租房的链接");
//        	bs.switchWindo(3);
//        	//租房单页 服务等级的locator和二手房单页一样
//        	String badRentViewRight = bs.getText(Ajk_PropView.Star_BrokerInfo, "获得服务等级");
//        	badRentViewRight = badRentViewRight.replaceAll(" ", "");
//        	bs.assertEquals(badRentViewRight, "暂无", "经纪人二手房-右侧服务等级", "服务等级为暂无");
//        	String badRentViewDownufsinfo = bs.getText(Ajk_PropView.Star_downufsinfo, "获得服务等级");
//        	badRentViewDownufsinfo = badRentViewDownufsinfo.replaceAll(" ", "");
//        	bs.assertEquals(badRentViewDownufsinfo, "暂无", "经纪人二手房-下方UFS信息服务等级", "服务等级为暂无");
//        	bs.close();
//        	bs.switchWindo(2);
//    	}
    	//点评价tab
    	bs.click(Ajk_ShopView.UfsTab, "点评价tab");
    	//经纪人店铺右侧通栏 服务等级locator通用
    	String badShopViewRight = bs.getText(Ajk_ShopView.Star, "获得服务等级");
    	bs.assertEquals(badShopViewRight, "暂无", "经纪人二手房-下方UFS信息服务等级", "服务等级为暂无");
    }
    
//    待继续优化
//    private void checkClown()
//    {}
//    private void checkNoEva()
//    {}
    
}
