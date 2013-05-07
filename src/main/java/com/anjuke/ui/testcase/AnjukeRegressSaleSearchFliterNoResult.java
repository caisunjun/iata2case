package com.anjuke.ui.testcase;

import com.anjuke.ui.page.Ajk_Sale;
import com.anjuke.ui.publicfunction.AjkSaleSearchAction;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**在二手房列表页搜索一个关键词，然后再筛选至无结果，验证提示信息及推荐是否存在
 * 此脚本为了回归 bug 15158
 * @author ccyang
 * @last createtime 2013-4-26 13:42:48
 */
public class AnjukeRegressSaleSearchFliterNoResult {
    Browser bs = null;
    AjkSaleSearchAction ass = null;

    @BeforeMethod
    public void setUp() {
    	Report.G_CASECONTENT = "Bug Regression 二手房列表页无结果提示信息";
        bs = FactoryBrowser.factoryBrowser();
        ass = new AjkSaleSearchAction(bs);
    }
    @AfterMethod
    public void tearDown(){
        bs.quit();
        bs = null;
    }
    @Test
    public void testSaleSearchFliterNoResultInfo(){
    	String saleUrl = "http://shanghai.anjuke.com/sale/";
    	bs.get(saleUrl);
		bs.type(Ajk_Sale.KwInput, "奉贤", "输入关键词：奉贤");
		bs.click(Ajk_Sale.KwSubmit, "点击找房子");
		bs.click("//div[@class='condition_links2']/div[3]/div[2]/a[1]", "筛选第一项");
		bs.click("//div[@class='condition_links2']/div[4]/div[2]/a[" + bs.getElementCount("//div[@class='condition_links2']/div[4]/div[2]/a") + "]", "筛选最后一项");
		
		String noResultTip = bs.getText("//div[@class='tip']", "获得无结果提示语");
		bs.assertEquals(noResultTip, "哎呀，没有找到符合要求的房源。","验证无结果提示语","");
		
		String noResultSuggest = bs.getText("//div[@class='suggest']", "获得无结果建议");
		bs.assertContains(noResultSuggest, "建议");
		
		bs.assertTrue(bs.check("//div[@class='HotRecommend']",5), "无结果时应该出现最新二手房的推荐模块", "");
    }
}
