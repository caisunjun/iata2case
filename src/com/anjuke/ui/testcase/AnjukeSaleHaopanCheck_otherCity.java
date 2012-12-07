package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Ajk_Sale;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;

/**
 * 该测试用例主要用来检查外地城市二手房列表页搜索和筛选时显示的好盘房源正确与否，验证点如下：
 * 1、外地城市，筛选列表页分页检测
 * 2、外地城市，二手房筛选列表，第1、3、5展位好盘房源检测
 * 3、外地城市，搜索列表页分页检测
 * 4、外地城市，搜索列表页好盘房源检测
 * @author jessili
 */

public class AnjukeSaleHaopanCheck_otherCity {	
	public Browser bs = null;
	public String baseUrl;
	
	
	@BeforeMethod
	public void setUp() throws Exception {
//		bs.deleteAllCookies();
		bs = FactoryBrowser.factoryBrowser();
		baseUrl = "http://chengdu.anjuke.com/sale/";
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		bs.quit();
		bs = null;
	}
	
	@Test
	//检查好盘的前台展示
	public void haopanCheck(){		
		//外地城市，筛选列表页分页检测
		saleScreenListPaging_otherCity();
		
		//外地城市，二手房筛选列表，第1、3、5展位好盘房源检测
		saleScreenListHaopan135Check_otherCity();
		
		//外地城市，搜索列表页分页检测
		saleSearchListPaging_otherCity();
		
		//外地城市，搜索列表页好盘房源检测
		saleSearchListHaopanCheck_otherCity();	
	}
	
	//外地城市，筛选列表页分页检测
	public void saleScreenListPaging_otherCity(){
		bs.get(baseUrl);
		bs.click("//*[@id='apf_id_14_areacontainer']/a[5]", "选择区域：成华");
		bs.click("//*[@id='apf_id_14_blockcontainer']/a[3]", "选择板块：电子科大");
		//获取分页总数
		String temp = bs.findElement(Ajk_Sale.PAGE_COUNT, "获取分页总数", 5).getText();
		String s[] = temp.split("/");
		int pageNo = Integer.parseInt(s[1]);
		
		int sum  = Integer.parseInt(bs.findElement(Ajk_Sale.S_COUNT, "获取搜索结果数",5).getText());
		
		//计算预期分页总数（向上取整）
		int p = (int)Math.ceil((double)sum/25);
		
		//比较实际分页总数与预期分页总数是否一致
		if(pageNo==p){
			Report.writeHTMLLog("筛选列表分页检测", "比较实际分页总数与预期分页总数是否一致", Report.PASS, "");
		}else{
			Report.writeHTMLLog("筛选列表分页检测", "比较实际分页总数与预期分页总数是否一致", Report.FAIL, "");
		}		
	}
	
	//检查外地老好盘上线城市，二手房筛选列表，第1、3、5展位显示好盘房源
	public void saleScreenListHaopan135Check_otherCity(){
		bs.get(baseUrl);
		String str1,str2,str3;
		bs.click("//*[@id='apf_id_13_areacontainer']/a[5]", "点击区域:成华");
		bs.click("//*[@id='apf_id_13_blockcontainer']/a[3]", "点击板块：电子科大");
		str1 = bs.getAttribute("//*[@id='prop_name_qt_prop_1']", "href");
		str2 = bs.getAttribute("//*[@id='prop_name_qt_prop_3']", "href");
		str3 = bs.getAttribute("//*[@id='prop_name_qt_prop_5']", "href");
	
		bs.assertContains(str1, "from=filtertop");
		bs.assertContains(str2, "from=filtermid");
		bs.assertContains(str3, "from=filterbot");
		Report.writeHTMLLog("外地城市好盘展示", "检查二手房筛选列表好盘房源在第1、3、5展位的展示", Report.DONE, "");
		
		System.out.println(str1);
	}
	
	//检查外地城市，搜索列表页分页是否正确
	public void saleSearchListPaging_otherCity(){
		bs.get(baseUrl);
		bs.findElement(Ajk_Sale.KwInput, "输入关键字",10).sendKeys("电子科大");
		bs.click(Ajk_Sale.KwSubmit, "点击：找房子");
		//获取分页总数
		String temp = bs.findElement(Ajk_Sale.PAGE_COUNT, "获取分页总数", 5).getText();
		String s[] = temp.split("/");
		int pageNo = Integer.parseInt(s[1]);
		
		int sum  = Integer.parseInt(bs.findElement(Ajk_Sale.S_COUNT, "获取搜索结果数",5).getText());	
		
		//计算预期分页总数（向上取整）
		int p = (int)Math.ceil((double)sum/25);
		
		//比较实际分页总数与预期分页总数是否一致
		if(pageNo==p){
			Report.writeHTMLLog("搜索列表分页检测", "比较实际分页总数与预期分页总数是否一致", Report.PASS, "");
		}else{
			Report.writeHTMLLog("搜索列表分页检测", "比较实际分页总数与预期分页总数是否一致", Report.FAIL, "");
		}		
	}
	
	//检查外地城市，搜索列表页，是否展示好盘房源
	public void saleSearchListHaopanCheck_otherCity(){
		bs.get(baseUrl);
		bs.findElement(Ajk_Sale.KwInput, "输入关键字",10).sendKeys("电子科大");
		bs.click(Ajk_Sale.KwSubmit, "点击：找房子");
		for(int i=1;i<=5;i++){
			String tmp = bs.getAttribute("//*[@id='prop_name_qt_prop_"+i+"']","href");
			bs.assertContains(tmp,"spread=commsearch");
		}
	}	
}
