package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Ajk_Sale;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;

/**
 * 该测试用例主要完成: 
 * 检查列表页经纪人ufs信息是否全部没有
 * @author ccyang
 * @time 2013-3-18 17:16:34
 **/

public class AnjukeSaleUfsIcon {
	private Browser driver = null;

	@BeforeMethod
	public void setUp() {
		Report.G_CASECONTENT = "检查列表页经纪人ufs信息是否存在";
		driver = FactoryBrowser.factoryBrowser();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		driver = null;
	}
	@Test
	public void testAnjukeSaleUfsIcon() {
		driver.get("http://shanghai.anjuke.com/sale/");
		checkUfsIcon();
		checkUfsText();
	}

	private void checkUfsIcon(){
		int failTimes = 0;
		int listCount = driver.getElementCount(Ajk_Sale.LIST_COUNT);
		for(int i = 1;i <= listCount;i++)
		{
			//检查服务评价的icon是否存在
			if(!driver.check("//div[@id='prop_"+i+"']/div[2]/p[2]/img",2)){
				failTimes++;
			}
		}
		if(failTimes >= listCount){
			Report.writeHTMLLog("列表页ufs icon检查", "当前列表页的ufs icon全部没有", Report.FAIL, driver.printScreen());
		}
		else{
			Report.writeHTMLLog("列表页ufs icon检查", "当前列表页的ufs icon正常，没有icon的数量为："+failTimes, Report.PASS, "");
		}
	}
	
	private void checkUfsText(){
		int failTimes = 0;
		String ufsText;
		String a[];
		int listCount = driver.getElementCount(Ajk_Sale.LIST_COUNT);
		
		for(int i = 1;i <= listCount;i++)
		{
			ufsText = driver.getText("//div[@id='prop_"+i+"']/div[2]/p[2]","");
			
			if(ufsText.contains(" ")){
			//通过空格，将string截成两段
			a = ufsText.split(" ",2);
			ufsText = a[1].replaceAll(" ", "");
			}
			
			if(!ufsText.equals("服务等级：")){
				failTimes++;
			}
		}
		if(failTimes >= listCount){
			Report.writeHTMLLog("列表页ufs 文本内容检查", "当前列表页，所有经纪人姓名后的ufs文本内容不是“服务等级：”", Report.FAIL, driver.printScreen());
		}else{
			Report.writeHTMLLog("列表页ufs 文本内容检查", "服务等级提示文字正常，出错的个数为："+failTimes, Report.PASS, "");
		}
	}
}
