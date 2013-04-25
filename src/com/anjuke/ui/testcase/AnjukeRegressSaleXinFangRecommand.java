package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import com.anjuke.ui.page.*;
//import com.anjuke.ui.publicfunction.AjkSaleSearchAction;
import com.anjuke.ui.publicfunction.PublicProcess;

/**该测试用例主要完成: 查看二手房列表页底部是否存在“您可能感兴趣的新房”模块
 * 用于回归bug 15212
 * @author minzhao
 **/

public class AnjukeRegressSaleXinFangRecommand {
    Browser driver = null;
    
    @BeforeMethod
    public void setUp() {
    	Report.G_CASECONTENT = "二手房列表页新房模块检测";
    	driver = FactoryBrowser.factoryBrowser();
    }
    @AfterMethod
    public void tearDown(){
    	driver.quit();
    	driver = null;
    }
    @Test
    public void testSaleXinFangRecommand(){
    	//随机返回一个城市的"city域名（字母）"
    	String[] city;
    	String cityPinyin = "";
    	//String cityName = "";
    	int cityType = 1;
    	city = PublicProcess.getRandomCityFromConfig(cityType).split("-");
    	cityPinyin = city[0];
    	String homePageUrl = "http://"+cityPinyin+".anjuke.com/sale";
    	driver.get(homePageUrl);

		String tips=driver.getText(Ajk_Sale.interestXinfang, "获得页面底部新房推荐提示文字");
		driver.assertEquals("您可能感兴趣的新房:",tips,"检测页面上是否有“您可能感兴趣的新房”模块", " 检测"+cityPinyin+"城市页面上是否有“您可能感兴趣的新房”模块");

    }
 
    
}