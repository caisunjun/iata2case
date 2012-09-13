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

/**该用例用来检查二手房房源单页
 * 包括小区名，经济人，中介公司，各种链接，同小区房源数量统计
 * @author ccyang
 *
 */

public class AnjukePropView {
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
    @SuppressWarnings("deprecation")
	@Configuration(afterTestClass = true)
	public void doBeforeTests() {
		System.out.println("***AnjukePropView is done***");
	}
    @Test
    public void testPropView(){
//    	bs.deleteAllCookies();
//    	bs.get("http://www.anjuke.com/version/switch/?f1=" + Init.G_config.get("version").toLowerCase());
    	String brokerName = "";
    	String brokerCompany = "";
    	
    	bs.get("http://shanghai.anjuke.com/");
    	//点导航里的二手房tab
    	if(!bs.check(Ajk_HomePage.SALETAB))
    	{bs.refresh();}
    	bs.click(Ajk_HomePage.SALETAB, "点导航里的二手房tab");
    	//点第一套房源
    	bs.click(Ajk_Sale.getTitle(1), "点第一套房源");
    	bs.switchWindo(2);
    	checkCommNameAndLink(bs);
    	checkCaculator(bs);
    	checkBrokerAndCompanyName(bs,brokerName,brokerCompany);
    	priceCountDiff(bs);
    	areaCountDiff(bs);

//    	tryReport(bs);//受制于弹出框，目前不好用
//    	priceNotice(bs);//受制于弹出框，目前不好用
    }
    
    private static void checkCommNameAndLink(Browser bs)
    {
    	//验证小区名，小区链接========================================================================================
    	String textCommName = bs.getText(Ajk_PropView.COMMNAME, "房源信息处的小区名");
    	//是否有必要继续验证页面其他地方的小区名是否一致？
    	bs.assertEquals(textCommName, bs.getText(Ajk_PropView.NavCommName, "面包屑处的小区名"), "验证小区名是否一致", "面包屑里的小区名和房源信息里的一致") ;
    	//验证前往小区的链接是否一致
    	String urlComm1 = bs.getAttribute(Ajk_PropView.COMMNAME, "href");
    	String urlComm2 = bs.getAttribute(Ajk_PropView.MAPCOMMNAME, "href");
    	//获得？前的字符串
    	urlComm2 = urlComm2.substring(0,urlComm2.lastIndexOf("?")).trim();
    	
    	bs.assertEquals(urlComm1,urlComm2,"验证小区链接是否一致", "房源信息里的小区链接和地图块的链接一致");
    	//验证小区首页的小区名=======================================================================================
    	//继续点小区名链接（打开新页面）
    	bs.click(Ajk_PropView.COMMNAME, "点击房源信息处的小区名链接");
    	bs.switchWindo(3);
    	//验证小区名是否一致
    	if(textCommName.lastIndexOf("（") != -1)
    	{textCommName = textCommName.substring(0 ,textCommName.lastIndexOf("（")).trim();}
    	bs.assertEquals(textCommName,bs.getText(Ajk_CommunityView.COMMTITLE, "获取小区单页的小区名"), "验证小区名是否一致", "房源单页的小区名和小区单页里的一致") ;
    	bs.close();
    	//回到房源单页
    	bs.switchWindo(2);
    }
    
    private static void checkCaculator(Browser bs)
    {
    	//验证房贷计算器链接==========================================================================================
    	//点房贷计算器链接（打开新页面）
    	bs.click(Ajk_PropView.CALCULATOR, "点房贷计算器");
    	bs.switchWindo(3);
    	//找到tab里的房贷计算器后，就把页面关了
    	bs.getElementCount(Ajk_Calculator.FIRSTTAB);
    	bs.getText(Ajk_Calculator.FIRSTTAB, "获得房贷计算器第一个tab里的文字").contains("房贷计算器");
    	bs.close();
    	//回到房源单页
    	bs.switchWindo(2);
    }
    
    private static void checkBrokerAndCompanyName(Browser bs,String brokerName,String brokerCompany)
    {
    	//3验证经纪人姓名，公司门店======================================================================================
    	//取右侧头像处经纪人姓名，公司门店
    	brokerName = bs.getText(Ajk_PropView.BROKERNAME, "取经纪人姓名");
    	brokerCompany = bs.getText(Ajk_PropView.COMPANYNAME, "取经纪人公司门店");
    	System.out.println(brokerCompany);
    	//点头像下经纪人名字的链接
    	bs.click(Ajk_PropView.BROKERNAME, "点头像下经纪人名字的链接");
    	bs.switchWindo(3);
    	//验证经纪人姓名是否一致
    	bs.getElementCount(Broker_shopview.BROKERNAME);
    	bs.assertEquals(brokerName,bs.getText(Broker_shopview.BROKERNAME, "获取经纪人店铺首页的经纪人名"), "验证经纪人姓名是否一致", "房源单页的经纪人姓名和店铺首页里的一致") ;
    	bs.close();
    	//回到房源单页
    	bs.switchWindo(2);
    	//点经纪人头像下公司门店的链接
    	bs.click(Ajk_PropView.COMPANYNAME, "点经纪人头像下公司门店的链接");
    	bs.switchWindo(3);
    	//获得经纪人公司门店
    	bs.getElementCount(Ajk_AgencyStore.SIDESTORENAME);
    	String agencyName = bs.getText(Ajk_AgencyStore.SIDESTORENAME, "获取经纪人店铺首页的经纪人名");
    	//先处理下字符串
    	brokerCompany = brokerCompany.replace("公司 :   ", "");
    	brokerCompany = brokerCompany.replace('\n', ' ');
    	brokerCompany = brokerCompany.replace("门店 :   ", "");
    	agencyName = agencyName.replace("公司：", "");
    	System.out.println(agencyName);
    	//验证经纪人公司门店名是否一致
    	bs.assertEquals(brokerCompany,agencyName, "验证经纪人公司门店是否一致", "房源单页的经纪人公司门店和公司门店里的一致") ;
    	bs.close();
    	//回到房源单页
    	bs.switchWindo(2);
    }
    
    private static void priceCountDiff(Browser bs)
    {
    	//验证按售价统计数量证确性=============================================================================
    	String priceCount = "";
    	bs.click(Ajk_PropView.COMMINTROTAB, "移动下页面，触发ajax");
    	priceCount = bs.getText(Ajk_PropView.PriceCount, "按售价统计房源数量");
    	priceCount = priceCount.replace("套", "");
    	bs.click(Ajk_PropView.PriceCount, "点-按售价统计房源链接");
    	bs.switchWindo(3);
    	//这里的数量可能不精确···········两边不一定完全相等
    	String priceCountC = bs.getText(Ajk_CommunitySale.PROPCOUNT, "取小区二手房页按售价筛选的房源数量");
    	int countDiffP = Math.abs(Integer.parseInt(priceCountC) - Integer.parseInt(priceCount));
    	if(Integer.parseInt(priceCountC) == 0)
    	{
    		String ps = bs.printScreen();
    		Report.writeHTMLLog("按售价统计房源数", "小区内房源数为0", Report.FAIL, ps);
    		System.out.println("111");
    	}
    	else if(countDiffP < 10 || countDiffP < 0.2*Integer.parseInt(priceCount))
    	{
    		Report.writeHTMLLog("按售价统计房源数,房源单页和小区的差值在可接受范围内", "**房源单页："+priceCount+" ||小区："+priceCountC+" ||差值为："+countDiffP+"**", Report.PASS, "");
    		System.out.println("222"+"**房源单页："+priceCount+" ||小区："+priceCountC+" ||差值为："+countDiffP+"**");
    	}
    	else
    	{
    		Report.writeHTMLLog("按售价统计房源数,房源单页和小区的差值过大", "**房源单页："+priceCount+" ||小区："+priceCountC+" ||差值为："+countDiffP+"**", Report.WARNING, "");
    		System.out.println("333"+"**房源单页："+priceCount+" ||小区："+priceCountC+" ||差值为："+countDiffP+"**");
    	}

    	bs.close();
    	//回到房源单页
    	bs.switchWindo(2);
    }
    
    private static void areaCountDiff(Browser bs)
    {
    	//验证按面积统计数量证确性=============================================================================
		String areaCount =  bs.getText(Ajk_PropView.AreaCount, "按面积统计房源数量");
		areaCount = areaCount.replace("套", "");
		bs.click(Ajk_PropView.AreaCount, "点-按面积统计房源链接");
		bs.switchWindo(3);
		String areaCountC = bs.getText(Ajk_CommunitySale.PROPCOUNT, "取小区二手房页按面积筛选的房源数量");
		//用来计算
		int countDiffA = Math.abs(Integer.parseInt(areaCountC) - Integer.parseInt(areaCount));
		if(Integer.parseInt(areaCountC) == 0)
		{
			String ps = bs.printScreen();
			Report.writeHTMLLog("按面积统计房源数", "小区内房源数为0", Report.FAIL, ps);
		}
		else if(countDiffA < 10 || countDiffA < 0.1*Integer.parseInt(areaCount))
		{
			Report.writeHTMLLog("按面积统计房源数,房源单页和小区的差值在可接受范围内", "**房源单页："+areaCount+" ||小区："+areaCountC+" ||差值为："+countDiffA+"**", Report.PASS, "");
			System.out.println("**房源单页："+areaCount+" ||小区："+areaCountC+" ||差值为："+countDiffA+"**");
		}
		else
		{
			Report.writeHTMLLog("按售价统计房源数,房源单页和小区的差值过大", "**房源单页："+areaCount+" ||小区："+areaCountC+" ||差值为："+countDiffA+"**", Report.WARNING, "");
			System.out.println("**房源单页："+areaCount+" ||小区："+areaCountC+" ||差值为："+countDiffA+"**");
		}
		bs.close();
		//回到房源单页
		bs.switchWindo(2);
    }
    private static void tryReport(Browser bs)
    {
    	//点举报
    	bs.click("id^report", "点举报");
    	//弹出层很悲剧
    	bs.check("id^popup_apf_id_16");
    	bs.click("id^popup_apf_id_16", "点弹出框");
    	bs.assertEquals("您确认要举报该房源？", bs.getText("//form[@id='rptForm_apf_id_16']/div[2]/div[1]", "获得举报框的title文字"), "举报框的title文字", "title文字是：您确认要举报该房源？");
    	bs.assertEquals("房源不存在或已售", bs.getText("//form[@id='rptForm_apf_id_16']/div[2]/div[2]/label", "获得举报框的title文字"), "选项1：", "房源不存在或已售");
    	bs.assertEquals("价格不真实", bs.getText("//form[@id='rptForm_apf_id_16']/div[2]/div[3]/label", "获得举报框的title文字"), "选项2：", "价格不真实");
    	bs.click("//form[@id='rptForm_apf_id_16']/div[1]/span", "关掉举报弹出层");

    }
    private static void priceNotice(Browser bs)
    {
    	//点变价通知
    	bs.click("id^pricenotice_apf_id_13", "点变价通知");
    	bs.click("id^username_apf_id_14","输入用户名");
    	bs.type("id^username_apf_id_14", "max21", "输入用户名");
    	bs.click("id^password_apf_id_14","输入密码");
    	bs.type("id^password_apf_id_14", "123123", "输入密码");
    	bs.click("id^submit_apf_id_14", "登陆");
    	String tmp = bs.getText("//div[@id='content_apf_id_15']/div[1]", "获得变价通知弹出框的title");
    	System.out.println(tmp);
    	tmp = tmp.replaceAll("关闭", "");
    	tmp = tmp.replace('\n', ' ');
    	tmp = tmp.replaceAll(" ", "");
    	System.out.println(tmp);
    	bs.assertEquals("售价变动通知",tmp,"获得变价通知弹出框的title", "内容是：");
    	bs.check("id^btnSubscribe_apf_id_15");
    	bs.click("//div[@id='content_apf_id_15']/div[1]/span", "关掉变价通知弹出框");
    }
    
    
}
