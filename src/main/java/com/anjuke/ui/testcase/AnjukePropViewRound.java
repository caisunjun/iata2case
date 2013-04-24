package com.anjuke.ui.testcase;

import com.anjuke.ui.page.Ajk_HomePage;
import com.anjuke.ui.page.Ajk_MapSale;
import com.anjuke.ui.page.Ajk_PropView;
import com.anjuke.ui.page.Ajk_Sale;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;

/**该用例用来检查二手房房源单页地图模块、弹出层中的周边信息及地图中对应的小区
 * @author ccyang
 */

public class AnjukePropViewRound {
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
		System.out.println("***AnjukePropView is done***");
	}
    
    @Test(groups = {"unstable"})
    public void testPropViewRound(){
    	
    	//点导航里的二手房tab
    	if(!bs.check(Ajk_HomePage.SALETAB))
    	{bs.refresh();}
    	bs.click(Ajk_HomePage.SALETAB, "点导航里的二手房tab");
    	//点第一套房源
    	bs.click(Ajk_Sale.getTitle(1), "点第一套房源");
    	bs.switchWindo(2);
    	checkPropRound();
    }
    private void checkPropRound() {
    	String textCommName = null;
    	String mapPopUpCommName = null;
    	String itemOn = null;
    	bs.click(Ajk_PropView.MAPTAB, "移动下页面，触发ajax");
    	textCommName = bs.getText(Ajk_PropView.COMMNAME, "房源信息处的小区名");
    	//点击查看周边信息链接```````````````````````````````````````````````````````````````````````````````````````
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	bs.click(Ajk_PropView.MAPRound, "点击查看周边信息",5);
    	bs.click(Ajk_PropView.MAPPOPUPClose, "关掉弹出层");
    	//点击商业
    	bs.click(Ajk_PropView.MAPRoundBusi, "点周边信息“商业”");
    	itemOn = bs.getAttribute(Ajk_PropView.MAPPOPUPTABBUSI, "class");
    	//验证弹出层里是否选中了商业tab
    	bs.assertContains(itemOn, "business");
    	bs.assertContains(itemOn, "item-on");
    	//验证小区名一致
    	mapPopUpCommName = bs.getText(Ajk_PropView.MAPPOPUPCOMMNAME, "获得地图弹出层气泡上的小区名");
    	bs.assertEquals(textCommName, mapPopUpCommName, "验证小区名是否一致", "地图弹出层里的小区名和房源信息里的一致");
    	//进入地图
    	bs.click(Ajk_PropView.MAPPOPUPRound, "点击地图找周边房源");
    	bs.switchWindo(3);
    	//获得左侧列表展示的当前选中的小区名
    	String mapCommName = bs.getText(Ajk_MapSale.ListCommNameSelected, "地图页面当前选中的小区名");
    	System.out.println(mapCommName);
    	//防止内容为空
    	int timess=0;
    	if(mapCommName.equals(null) && timess<10){
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		mapCommName = bs.getText(Ajk_MapSale.ListCommNameSelected, "地图页面当前选中的小区名");
    		timess=timess+1;
    	}
    	bs.assertEquals(textCommName,mapCommName, "验证小区名是否一致", "地图找房里选中的小区名和房源信息里的一致");
    	bs.click(Ajk_MapSale.ListClearComm, "清除标记");
    	//清除标记后，显示小区内容的模块将被置为display: none
    	String commInfo = bs.getAttribute(Ajk_MapSale.ListCommInfo, "style");
    	bs.assertEquals("display: none;", commInfo, "地图功能验证", "清除标记后prop-info-comm被置为display: none;");
    	//等待载入结束
    	String loadingLayer = null;
    	int times = 0;
    	do{
    		try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		loadingLayer = bs.getAttribute(Ajk_MapSale.ListPropLoading, "style");
    		times++;
    		if(times>5)
    		{
    			Report.writeHTMLLog("地图功能验证", "点击清除标记按钮，房源载入时间过长", Report.WARNING, "");
    		}
    	}while(!loadingLayer.equals("display: none;") || times>5);
    	//判断区域房源的模块是否正常显示
    	String defaultInfo = bs.getAttribute(Ajk_MapSale.ListPropInfo, "style");
    	if(defaultInfo.equals("display: none;"))
    	{
    		String ps = bs.printScreen();
    		Report.writeHTMLLog("地图功能验证", "点击清除标记按钮，未能取消小区。左侧列表内区域房源未能正常显示，其"+defaultInfo, Report.FAIL, ps);
    	}
    	else if(Integer.parseInt(bs.getText(Ajk_MapSale.ListPropCount, "当前地图区域房源数"))<1){
    		String ps = bs.printScreen();
    		Report.writeHTMLLog("地图功能验证", "点击清除标记按钮，区域房源数为："+bs.getText(Ajk_MapSale.ListPropCount, "当前地图区域房源数"), Report.FAIL, ps);
    	}
    	else{
    		Report.writeHTMLLog("地图功能验证", "点击清除标记按钮，成功取消小区", Report.PASS, "");
    	}
	}    
}
