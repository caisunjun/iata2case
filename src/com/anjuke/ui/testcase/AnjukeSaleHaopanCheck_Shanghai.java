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
 * 该测试用例主要用来检查上海二手房列表页搜索和筛选后显示的好盘房源正确与否，验证点如下：
 * 1、上海-筛选列表页分页检测
 * 2、上海-筛选列表页竞价房源
 * 3、上海-搜索列表页分页检测
 * 4、上海-搜索列表页-搜小区词，展示该小区所有竞价房源
 * 5、上海-搜索列表页-同小区同价格段，展示竞价房源：3套
 * 6、上海-搜索列表页-搜索结构词，展示竞价房源：15套
 * 7、上海-搜索列表页-搜索普通文本，展示竞价房源：15套
 * @author jessili
 */

@Test
public class AnjukeSaleHaopanCheck_Shanghai {	
	public Browser bs = null;
	public String baseUrl;
	
	
	@BeforeMethod
	public void setUp() throws Exception {
//		bs.deleteAllCookies();
		bs = FactoryBrowser.factoryBrowser();
		baseUrl = "http://shanghai.anjuke.com/sale/";
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		bs.quit();
		bs = null;
	}
	
	@Test
	public void haopanCheck(){		
		//上海-筛选列表页分页检测
		saleScreenListPaging_Shanghai();
		
		//上海-筛选列表页竞价房源
		saleScreenListHaopanCheck_Shanghai();
		
		//上海-搜索列表页分页检测
		saleSearchListPaging_Shanghai();
		
		//上海-搜索列表页-搜小区词，展示该小区所有竞价房源
		saleSearchListHaopanCheck_CommSearch_Shanghai();
		
		//上海-搜索列表页-同小区同价格段，展示竞价房源：3套
		saleSearchListHaopanCheck_SameCommAndPrice_Shanghai();
		
		//上海-搜索列表页-搜索结构词，展示竞价房源：15套
		saleSearchListHaopanCheck_StructureSearch_Shanghai();	
		
		//上海-搜索列表页-搜索普通文本，展示竞价房源：15套
		saleSearchListHaopanCheck_TextSearch_Shanghai();
	}
	
	
	//上海-筛选列表页分页检测
	private void saleScreenListPaging_Shanghai(){
		bs.get(baseUrl);
		bs.click("//*[@id='apf_id_13_areacontainer']/a[1]", "选择区域：浦东");
		bs.click("//*[@id='apf_id_13_blockcontainer']/a[8]", "选择板块：陆家嘴");
		bs.click("//*[@id='panel_apf_id_13']/div[5]/div[2]/a[4]", "选择房型：四室");
		//获取分页总数
		String temp = bs.findElement(Ajk_Sale.PAGE_COUNT, "获取分页总数", 5).getText();
		String s[] = temp.split("/");
		int pageNo = Integer.parseInt(s[1]);
		
		int sum  = Integer.parseInt(bs.findElement(Ajk_Sale.S_COUNT, "获取搜索结果数",5).getText());
		
		//计算预期分页总数（向上取证）
		int p = (int)Math.ceil((double)sum/25);
		
		//比较实际分页总数与预期分页总数是否一致
		if(pageNo==p){
			Report.writeHTMLLog("筛选列表分页检测", "比较实际分页总数与预期分页总数是否一致", Report.PASS, "");
		}else{
			Report.writeHTMLLog("筛选列表分页检测", "比较实际分页总数与预期分页总数是否一致", Report.FAIL, "");
		}		
	}
	
	//检查上海-筛选列表页，展示竞价房源:5套
	private void saleScreenListHaopanCheck_Shanghai(){
		bs.get(baseUrl);
		bs.click("//*[@id='apf_id_13_areacontainer']/a[1]", "选择区域：浦东");
		bs.click("//*[@id='apf_id_13_blockcontainer']/a[8]", "选择板块：陆家嘴");
		for(int i=1;i<=5;i++){
			String tmp = bs.getAttribute("//*[@id='prop_name_qt_prop_"+i+"']","href");
			bs.assertContains(tmp,"spread=filtersearch");
		}
	}	
	
	//检查上海-搜索列表页分页检测
	private void saleSearchListPaging_Shanghai(){
		bs.get(baseUrl);
		bs.findElement(Ajk_Sale.KwInput, "输入关键字",10).sendKeys("陆家嘴四室");
		bs.click(Ajk_Sale.KwSubmit, "点击：找房子");
		//获取分页总数
		String temp = bs.findElement(Ajk_Sale.PAGE_COUNT, "获取分页总数", 5).getText();
		String s[] = temp.split("/");
		int pageNo = Integer.parseInt(s[1]);
		
		int sum  = Integer.parseInt(bs.findElement(Ajk_Sale.S_COUNT, "获取搜索结果数",5).getText());	
		
		//计算预期分页总数（向上取证）
		int p = (int)Math.ceil((double)sum/25);
		
		//比较实际分页总数与预期分页总数是否一致
		if(pageNo==p){
			Report.writeHTMLLog("搜索列表分页检测", "比较实际分页总数与预期分页总数是否一致", Report.PASS, "");
		}else{
			Report.writeHTMLLog("搜索列表分页检测", "比较实际分页总数与预期分页总数是否一致", Report.FAIL, "");
		}		
	}
	
	//检查上海-搜索列表页-搜小区词，展示该小区所有竞价房源
	private void saleSearchListHaopanCheck_CommSearch_Shanghai(){
		bs.get(baseUrl);
		bs.findElement(Ajk_Sale.KwInput, "输入关键字",10).sendKeys("中远两湾城");
		bs.click(Ajk_Sale.KwSubmit, "点击：找房子");
		
		//获取该小区竞价房源总数
		int m = 0;
		for(int i=1;i<=25;i++){
			String tmp = bs.getAttribute("//*[@id='prop_name_qt_prop_"+i+"']","href");
			if(tmp.contains("spread=commsearch&from=comm_one")){
				m = m+1;
			}
		}
		//小区竞价房源>25套
		if(m==25){
			bs.findElement(Ajk_Sale.NextPage, "下一页", 10);
			for(int i=1;i<=25;i++){
				String tmp = bs.getAttribute("//*[@id='prop_name_qt_prop_"+i+"']","href");
				if(tmp.contains("spread=commsearch&from=comm_one")){
					m = m+1;
				}
			}
		}			
		
		//获取小区单页竞价房源总数
		bs.get("http://shanghai.anjuke.com/community/props/sale/1550");
		int n = 0;
		for(int i=1;i<=20;i++){
			String tmp = bs.getAttribute("//*[@id='prop_name_qt_"+i+"']","href");
			if(tmp.endsWith("spread=commprop&invalid=1")){
				n = n+1;
			}
		}
		//小区竞价房源>20套
		if(n==20){
			bs.findElement(Ajk_CommunitySale.NextPageUP, "下一页", 10);
			for(int i=1;i<=20;i++){
				String tmp = bs.getAttribute("//*[@id='prop_name_qt_"+i+"']","href");
				if(tmp.endsWith("spread=commprop&invalid=1")){
					n = n+1;
				}
			}
		}
		//比较二手房列表和小区单页-二手房列表竞价房源总数
		bs.assertIntEquals(n, m, "小区竞价房源展示", "二手房列表与小区单页，竞价房源数一致");
	}
	
	//检查上海-搜索列表页-同小区同价格段，展示竞价房源：3套
	private void saleSearchListHaopanCheck_SameCommAndPrice_Shanghai(){
		bs.get(baseUrl);
		bs.findElement(Ajk_Sale.KwInput, "输入关键字",10).sendKeys("中远两湾城");
		bs.click(Ajk_Sale.KwSubmit, "点击：找房子");
		bs.click("//*[@id='Search-fragment']//dl[2]/dd/a[4]", "选择价格段：250-300万");
		int m = 0;
		for(int i=1;i<=25;i++){
			String tmp = bs.getAttribute("//*[@id='prop_name_qt_prop_"+i+"']","href");
			if(tmp.contains("spread=commsearch&from=comm_one")){
				m = m+1;
			}
		}
		if(m<=3){
			Report.writeHTMLLog("同小区同价格段，展示竞价房源",Integer.toString(m), Report.PASS, "");
		}else{
			Report.writeHTMLLog("同小区同价格段，展示竞价房源", Integer.toString(m), Report.FAIL, "");
		}
	}

	//检查上海-搜索列表页-搜索结构词，展示竞价房源：15套
	private void saleSearchListHaopanCheck_StructureSearch_Shanghai(){
		bs.get(baseUrl);
		bs.findElement(Ajk_Sale.KwInput, "输入关键字",10).sendKeys("浦东四室");
		bs.click(Ajk_Sale.KwSubmit, "点击：找房子");
		int n = 0;
		for(int i=1;i<=25;i++){
			String tmp = bs.getAttribute("//*[@id='prop_name_qt_prop_"+i+"']","href");
			if(tmp.contains("spread=commsearch&from=structured_dict")){
				n = n+1;
			}
		}
		bs.assertIntEquals(15, n, "搜索结构词，展示竞价房源", "15套");
	}
	
	//检查上海-搜索列表页-搜索普通文本，展示竞价房源：15套
	private void saleSearchListHaopanCheck_TextSearch_Shanghai(){
		bs.get(baseUrl);
		bs.findElement(Ajk_Sale.KwInput, "输入关键字",10).sendKeys("朝南");
		bs.click(Ajk_Sale.KwSubmit, "点击：找房子");
		int n = 0;
		for(int i=1;i<=25;i++){
			String tmp = bs.getAttribute("//*[@id='prop_name_qt_prop_"+i+"']","href");
			if(tmp.contains("spread=commsearch&from=kw")){
				n = n+1;
			}
		}
		bs.assertIntEquals(15, n, "搜索普通文本，展示竞价房源", "15套");
	}
}
