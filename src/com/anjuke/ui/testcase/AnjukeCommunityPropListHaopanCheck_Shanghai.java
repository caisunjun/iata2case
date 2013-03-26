package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Ajk_CommunitySale;
import com.anjuke.ui.page.Ajk_Sale;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;

/**
 * 该测试用例主要用来检查上海小区二手房列表页显示的竞价房源正确与否，验证点如下：
 * 1、上海-小区二手房-分页检测
 * 2、上海-小区二手房-筛选价格段，展示竞价房源：3套
 * 3、上海-小区二手房-筛选其他项，展示所有符合条件的竞价房源
 * 4、上海-小区二手房-手动排序，不展示好盘房源
 * @author jessili
 */

public class AnjukeCommunityPropListHaopanCheck_Shanghai {	
	public Browser bs = null;
	public String baseUrl;
	
	
	@BeforeMethod
	public void setUp() throws Exception {
		Report.G_CASECONTENT = "小区二手房列表页好盘房源检测-上海";
//		bs.deleteAllCookies();
		bs = FactoryBrowser.factoryBrowser();
		baseUrl = "http://shanghai.anjuke.com/community/props/sale/1550";
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		bs.quit();
		bs = null;
	}
	
	@Test
	public void haopanCheck(){		
		//上海-小区二手房-分页检测
		communityProplistPaging_Shanghai();
		
		//上海-小区二手房-筛选价格段，展示竞价房源：3套
		communityProplist_SamePrice_Shanghai();
		
		//上海-小区二手房-筛选其他项，展示所有符合条件的竞价房源
		communityProplist_Screen_Shanghai();
		
		//上海-小区二手房-手动排序，不展示好盘房源
		communityProplist_HandSort_Shanghai();
	}
	
	
	//上海-小区二手房-分页检测
	private void communityProplistPaging_Shanghai(){
		bs.get(baseUrl);
		//获取分页总数
		String temp = bs.findElement(Ajk_CommunitySale.PageNum, "获取分页总数", 5).getText();
		String s[] = temp.split("/");
		int pageNo = Integer.parseInt(s[1]);
		
		int sum  = Integer.parseInt(bs.findElement(Ajk_CommunitySale.PROPCOUNT, "获取搜索结果数",5).getText());
		
		//计算预期分页总数（向上取证）
		int p = (int)Math.ceil((double)sum/20);
		
		//比较实际分页总数与预期分页总数是否一致
		if(pageNo==p){
			Report.writeHTMLLog("小区二手房列表分页检测", "比较实际分页总数与预期分页总数是否一致", Report.PASS, "");
		}else{
			Report.writeHTMLLog("小区二手房列表分页检测", "比较实际分页总数与预期分页总数是否一致", Report.FAIL, "");
		}		
	}

	//上海-小区二手房-筛选价格段，展示竞价房源：3套
	private void communityProplist_SamePrice_Shanghai(){
		bs.get(baseUrl);
		bs.click("//*[@id='content']//dl[2]/dd/a[4]", "选择价格段：250-300万");
		int m = 0;
		for(int i=1;i<=20;i++){
			String tmp = bs.getAttribute("//*[@id='prop_name_qt_"+i+"']","href");
			if(tmp.endsWith("spread=commprop")){
				m = m+1;
			}
		}
		if(m<=3){
			Report.writeHTMLLog("同小区同价格段，展示竞价房源",Integer.toString(m), Report.PASS, "");
		}else{
			Report.writeHTMLLog("同小区同价格段，展示竞价房源", Integer.toString(m), Report.FAIL, "");
		}
	}

	//上海-小区二手房-筛选其他项，展示所有符合条件的竞价房源	
	private void communityProplist_Screen_Shanghai(){
		bs.get(baseUrl);
		int m = 0;
		for(int i=1;i<=20;i++){
			String tmp1 = bs.getAttribute("//*[@id='prop_name_qt_"+i+"']","href");
			String tmp2 = bs.getText(Ajk_CommunitySale.PropHouse(i), "获取房源的房型"); 
			if(tmp1.endsWith("spread=commprop")&tmp2.startsWith("2")){
				m = m+1;
			}
		}
		
		int n = 0;
		bs.click(Ajk_CommunitySale.FliterHouseByVal(2), "选择房型：二室");
		for(int i=1;i<20;i++){
			String tmp3 = bs.getText(Ajk_CommunitySale.PropHouse(i), "获取房源的房型"); 
			if(tmp3.endsWith("spread=commprop")){
				n = n+1;
			}
		}
		bs.assertIntEquals(m, n, "小区二手房列表页筛选", "筛选前和筛选后，同类型房源的竞价房源数比较");
	}
	
	//上海-小区二手房-手动排序，不展示好盘房源
	private void communityProplist_HandSort_Shanghai(){
		bs.get(baseUrl);
		bs.click(Ajk_CommunitySale.SortByPrice, "选择排序方式：总价从高到低");
		int n = 0;
		for(int i=1;i<=20;i++){
			String tmp = bs.getAttribute("//*[@id='prop_name_qt_"+i+"']","href");
			if(tmp.endsWith("spread=commprop")){
				n = n+1;
			}
		}
		bs.assertIntEquals(0, n, "排序", "手动排序，不展示竞价房源");
	}
}
