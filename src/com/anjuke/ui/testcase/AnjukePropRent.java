package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;
import com.anjuke.ui.page.*;
import com.anjuke.ui.publicfunction.PublicProcess;

/**该用例用来检查租房房源单页
 * 包括小区名，经济人，中介公司，各种链接
 * @author ccyang
 */

public class AnjukePropRent {
    Browser bs = null;

    @BeforeMethod
    public void setUp() {
        bs = FactoryBrowser.factoryBrowser();
    	bs.get("http://shanghai.anjuke.com/");
    }
    @AfterMethod
    public void tearDown(){
        bs.quit();
        bs = null;
    }
    @SuppressWarnings("deprecation")
	@Configuration(afterTestClass = true)
	public void doBeforeTests() {
		System.out.println("***AnjukePropRent is done***");
	}
    
    @Test
    public void testPropView(){
    	String brokerName = "";
    	String brokerCompany = "";
    	
    	//点导航里的二手房tab
    	if(!bs.check(Ajk_HomePage.RENTALTAB))
    	{bs.refresh();}
    	bs.click(Ajk_HomePage.RENTALTAB, "点导航里的二手房tab");
    	//点第一套房源
    	bs.click(Ajk_Rental.getPic(1), "点第一套房源");
    	bs.switchWindo(2);
    	checkCommNameAndLink(bs);
    	checkBrokerAndCompanyName(bs,brokerName,brokerCompany);
    	checkCommAveragePrice(bs);
    }    
    
    private static void checkCommNameAndLink(Browser bs)
    {
    	//验证小区名，小区链接========================================================================================
    	String textCommName = bs.getText(Ajk_PropRent.XIAOQUMING, "房源信息处的小区名");
    	//是否有必要继续验证页面其他地方的小区名是否一致？
    	bs.assertEquals(textCommName, bs.getText(Ajk_PropRent.NavCommName, "面包屑处的小区名"), "验证小区名是否一致", "面包屑里的小区名和房源信息里的一致") ;
    	//验证前往小区的链接是否一致
    	String urlComm1 = bs.getAttribute(Ajk_PropRent.XIAOQUMING, "href");
    	String urlComm2 = bs.getAttribute(Ajk_PropRent.MAPCOMMNAME, "href");
    	//获得？前的字符串
    	if(urlComm1.lastIndexOf("?") != -1)
    	{
    		urlComm1 = urlComm1.substring(0,urlComm1.lastIndexOf("?")).trim();
    	}
    	if(urlComm2.lastIndexOf("?") != -1)
    	{
    		urlComm2 = urlComm2.substring(0,urlComm2.lastIndexOf("?")).trim();
    	}
    	bs.assertEquals(urlComm1,urlComm2,"验证小区链接是否一致", "房源信息里的小区链接和地图块的链接一致");
    	//验证小区首页的小区名=======================================================================================
    	//继续点小区名链接（打开新页面）
    	bs.click(Ajk_PropRent.XIAOQUMING, "点击房源信息处的小区名链接");
    	bs.switchWindo(3);
    	//验证小区名是否一致
    	if(textCommName.lastIndexOf("（") != -1)
    	{textCommName = textCommName.substring(0 ,textCommName.lastIndexOf("（")).trim();}
    	bs.assertEquals(textCommName,bs.getText(Ajk_CommunityView.COMMTITLE, "获取小区单页的小区名"), "验证小区名是否一致", "房源单页的小区名和小区单页里的一致") ;
    	bs.close();
    	//回到房源单页
    	bs.switchWindo(2);
    }
     
    private static void checkBrokerAndCompanyName(Browser bs,String brokerName,String brokerCompany)
    {
    	//3验证经纪人姓名，公司门店======================================================================================
    	//取右侧头像处经纪人姓名，公司门店
    	brokerName = bs.getText(Ajk_PropRent.BROKERNAME, "取经纪人姓名");
    	brokerCompany = bs.getText(Ajk_PropRent.COMPANYNAME, "取经纪人公司门店");
    	
    	//经纪人公司有一种不可点击的“独立经济人”的情况
    	if(brokerCompany.equals("  门店:   独立经纪人")){
    		String ps = bs.printScreen();
    		Report.writeHTMLLog("经纪人公司门店检查", "该经纪人公司为不可点击的独立经济人", Report.DONE, ps);
    		return;
    	}
    	
      	//先处理下字符串
      	brokerCompany = brokerCompany.replace("公司 :   ", "");
      	brokerCompany = brokerCompany.replace('\n', ' ');
      	brokerCompany = brokerCompany.replace("门店 :   ", "");
    	System.out.println(brokerCompany);
    	//点头像下经纪人名字的链接
    	bs.click(Ajk_PropRent.BROKERNAME, "点头像下经纪人名字的链接");
    	bs.switchWindo(3);
    	
    	//是否有经纪人姓名
    	if(bs.check(Ajk_ShopView.BROKERNAME))
    	{
    		//有姓名的话，验证经纪人姓名是否一致
    		bs.assertEquals(brokerName,bs.getText(Ajk_ShopView.BROKERNAME, "获取经纪人店铺首页的经纪人名"), "验证经纪人姓名是否一致", "房源单页的经纪人姓名和店铺首页里的一致") ;
    	}
    	else{
    		String ps = bs.printScreen();
    		Report.writeHTMLLog("进入经纪人  "+brokerName+" 店铺遇到问题", "可能碰到了一个休眠经纪人", Report.FAIL, ps);
    	}
    	bs.close();
    	//回到房源单页
    	bs.switchWindo(2);
    	//点经纪人头像下公司门店的链接
    	bs.click(Ajk_PropRent.COMPANYNAME, "点经纪人头像下公司门店的链接");
    	bs.switchWindo(3);
    	//获得经纪人公司门店
    	bs.getElementCount(Ajk_AgencyStore.SIDESTORENAME);
    	String agencyName = bs.getText(Ajk_AgencyStore.SIDESTORENAME, "获取经纪公司门店名");
    	agencyName = agencyName.replace("公司：", "");
    	//验证经纪人公司门店名是否一致
    	bs.assertEquals(brokerCompany,agencyName, "验证经纪人公司门店是否一致", "房源单页的经纪人公司门店和公司门店里的一致") ;
    	bs.close();
    	//回到房源单页
    	bs.switchWindo(2);
    }
    
    private static void checkCommAveragePrice(Browser bs)
    {
    	//检查小区均价
    	bs.click(Ajk_PropRent.COMMINTROTAB, "移动下页面，触发ajax");
    	bs.refresh();
    	//getText
    	String thisMonthAveR = null;
    	String lastMonthAveR = null;
    	thisMonthAveR = bs.getText("//span[@class='price']/span", "租房单页，本月该小区均价");
    	lastMonthAveR = bs.getText("//div[@class='commtrends']/span/strong/span[2]", "比上月涨or跌")+bs.getText("//span[@id='normalsize']/span", "涨or跌了多少");
    	lastMonthAveR = lastMonthAveR.replace("：", "");
    	bs.click("//li[@class='reputation']/a", "点击小区链接");
    	
    	bs.switchWindo(3);
    	String thisMonthAveC = null;
    	String lastMonthAveC = null;
    	thisMonthAveC = bs.getText("//span[@id='comm_price_qt_1']/em", "小区首页，本月该小区均价");
    	lastMonthAveC = bs.getText("//*[@id='lazy_line']/p[1]/span[2]", "比上月");
    	
        if(lastMonthAveC.indexOf(".") > 0){
        	lastMonthAveC = lastMonthAveC.replace("%", "");
        	lastMonthAveC = lastMonthAveC.replaceAll("0+?$", "");//去掉多余的0  
        	lastMonthAveC = lastMonthAveC.replaceAll("[.]$", "");//如最后一位是.则去掉  
        	lastMonthAveC = lastMonthAveC+"%";
        }
        
    	bs.assertEquals(thisMonthAveR, thisMonthAveC, "本月小区均价比较", "租房单页和小区首页的均价一致");
    	if(lastMonthAveR.contains("持平"))
    	{
    		bs.assertContains(lastMonthAveC, "0%");
    	}else{
    		bs.assertEquals(lastMonthAveR, lastMonthAveC, "均价涨跌比较", "租房单页和小区首页的涨跌幅一致");
    	}
    }

    
}
