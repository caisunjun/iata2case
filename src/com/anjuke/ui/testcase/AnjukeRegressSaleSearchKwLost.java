package com.anjuke.ui.testcase;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjukeinc.iata.ui.util.GetRandom;
import com.anjuke.ui.page.*;
import com.anjuke.ui.publicfunction.AjkSaleSearchAction;
import com.anjuke.ui.publicfunction.PublicProcess;

/**在二手房列表页搜索一个关键词，然后再进行筛选或排序，验证搜索词是否依然存在
 * 此脚本为了回归 bug 15171
 * @author ccyang
 * @last createtime 2013年4月23日15:04:31
 */
public class AnjukeRegressSaleSearchKwLost {
    Browser bs = null;
    AjkSaleSearchAction ass = null;

    @BeforeMethod
    public void setUp() {
    	Report.G_CASECONTENT = "Bug Regression 二手房列表页搜索关键词是否会丢失";
        bs = FactoryBrowser.factoryBrowser();
        ass = new AjkSaleSearchAction(bs);
    }
    @AfterMethod
    public void tearDown(){
        bs.quit();
        bs = null;
    }
    @Test
    public void testSaleSearchKwLost(){
		String[] city;
    	String cityPinyin = "";
    	
    	city = PublicProcess.getRandomCityFromConfig(1).split("-");
    	cityPinyin = city[0];
    	String kw = "2室";
    	
    	String saleUrl = "http://"+cityPinyin+".anjuke.com/sale/";
    	bs.get(saleUrl);
		bs.type(Ajk_Sale.KwInput, kw, "输入关键词：" + kw);
		bs.click(Ajk_Sale.KwSubmit, "点击找房子");
		
		bs.click(Ajk_Sale.NextPage, "点击下一页");
		String searchKwNextPage = bs.getAttribute(Ajk_Sale.KwInput_Searched, "value");
		bs.assertEquals(kw, searchKwNextPage, "点击下一页", "搜索关键词是否正常带入了");
		
		bs.click(Ajk_Sale.SortByPrice, "点击按总价排序");
		String searchKwSortByPrice = bs.getAttribute(Ajk_Sale.KwInput_Searched, "value");
		bs.assertEquals(kw, searchKwSortByPrice, "点击按总价排序", "搜索关键词是否正常带入了");
		
		bs.click(Ajk_Sale.SortByArea, "点击按面积排序");
		String searchKwSortByArea = bs.getAttribute(Ajk_Sale.KwInput_Searched, "value");
		bs.assertEquals(kw, searchKwSortByArea, "点击按面积排序", "搜索关键词是否正常带入了");
		
		bs.click(Ajk_Sale.TagMore, "点击多图tag");
		String searchKwMore = bs.getAttribute(Ajk_Sale.KwInput_Searched, "value");
		bs.assertEquals(kw, searchKwMore, "点击多图tag后", "搜索关键词是否正常带入了");
		
		bs.click(Ajk_Sale.TagOwner, "点击个人tag");
		String searchKwOwner = bs.getAttribute(Ajk_Sale.KwInput_Searched, "value");
		bs.assertEquals(kw, searchKwOwner, "点击个人tag后", "搜索关键词是否正常带入了");
    }
}
