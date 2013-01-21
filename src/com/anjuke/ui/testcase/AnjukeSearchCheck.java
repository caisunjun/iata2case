package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Ajk_Ask;
import com.anjuke.ui.page.Ajk_Community;
import com.anjuke.ui.page.Ajk_Rental;
import com.anjuke.ui.page.Ajk_Sale;
import com.anjuke.ui.page.Ajk_Tycoon;
import com.anjuke.ui.page.Public_HeaderFooter;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;

/**
 * 该测试用例主要完成搜索操作，逻辑如下
 * 1、二手房正常、异常搜索检查
 * 2、租房正常、异常搜索检查
 * 3、小区正常、异常搜索检查
 * 4、经纪人正常、异常搜索检查
 * 5、问答正常、异常搜索检查
 *
 * @author Gabrielgao
 * @time 2012-04-13 13:30
 * @updateAuthor ccyang
 * @last updatetime 2012-01-06
 */
public class AnjukeSearchCheck {
    private Browser driver = null;

    @BeforeMethod
    public void setUp() {
    	Report.G_CASECONTENT = "通用搜索框搜索";
        driver = FactoryBrowser.factoryBrowser();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
        driver = null;
    }
    @SuppressWarnings("deprecation")
	@Configuration(afterTestClass = true)
	public void doBeforeTests() {
		System.out.println("***AnjukeSearchCheck is done***");
	}
    @Test
    public void searchCheck() {
        //Report.setTCNameLog("检查二手房搜索功能-- AnjukeSearchCheck --williamhu");
        // 二手房搜索检查
        check("sale");
        // 租房搜索检查
//        check("rental");
        // 小区搜索检查
        check("community");
        // 经纪人搜索检查
        check("tycoon");
        // 问答搜索检查
        check("ask");
    }

    //

    // 检查相关页面的搜索
    public void check(String checkType) {
        if (checkType == "sale" || checkType == "rental" || checkType == "tycoon" || checkType == "community") {
            if (checkType == "sale" || checkType == "rental") {
                driver.get("http://shanghai.anjuke.com/" + checkType + "/");
                //防页面打开后 无控件可点
            	if(!driver.check("id^header"))
        		{driver.refresh();}
                // 引导词检查
                checkGuideLan(checkType);
                // 正常关键字搜索
                normalSearch("一室一厅", checkType);
                normalSearch("张东路", checkType);
                normalSearch("玉兰香苑", checkType);
                normalSearch("三室 张东路 玉兰香苑", checkType);
            } else if (checkType == "community") {
                driver.get("http://shanghai.anjuke.com/" + checkType + "/");
                // 引导词检查
                checkGuideLan(checkType);
                // 正常关键字搜索
                normalSearch("田林路", checkType);
                normalSearch("田林花苑  ", checkType);
                normalSearch("田林路 田林花苑", checkType);
            } else {
                driver.get("http://shanghai.anjuke.com/" + checkType);
                // 引导词检查
                checkGuideLan(checkType);
                // 正常关键字搜索
                normalSearch("玉兰香苑", checkType);
                normalSearch("益江路", checkType);
                normalSearch("陈群", checkType);
                normalSearch("13918009288", checkType);
            }
            // 组合搜索不到结果
            // 异常关键字搜索
            unusualSearch("  ", checkType);
            unusualSearch("***&", checkType);
            unusualSearch("丂丂", checkType);
            unusualSearch("安全局", checkType);
        } else {
            driver.get("http://shanghai.anjuke.com/" + checkType);
            // 引导词检查
            checkGuideLan(checkType);
            // 正常关键字搜索
            normalAskSearch("二手房过户");
            // 异常关键字搜索
            driver.get("http://shanghai.anjuke.com/" + checkType);
            unusualAskSearch("  ");
        }

    }

    // 正常搜索问答模块
    public void normalAskSearch(String asksearch) {
        // Report.setTCNameLog("检查安居客问答正常关键字搜索-- checkGuideLan");
        driver.type(Ajk_Ask.S_BOX, asksearch, "关键字搜索");
        driver.click(Ajk_Ask.S_BTN, "点击搜索");
        int count = 0;
        count = driver.getElementCount(Ajk_Ask.LIST_COUNT);
        // 判断搜索结果页是否存在翻页按钮
        boolean present = driver.check(Ajk_Ask.FANYE);
        // 如果存在翻页，但是每页数量不为12则报错
        if (present && count != 12) {
            String ps = driver.printScreen();
            Report.writeHTMLLog("search result", "search result count is pass:" + count + "条记录", Report.FAIL, ps);
        } else if (present && count == 12) {
            Report.writeHTMLLog("search result", "search result count is:" + count + "条记录", Report.PASS, "");
        } else if (!present && count > 12) {
            String ps = driver.printScreen();
            Report.writeHTMLLog("search result", "search result count is pass:" + count + "条记录", Report.FAIL, ps);
        } else {
            Report.writeHTMLLog("search result", "search result count is:" + count + "条记录", Report.PASS, "");
        }
        // 获取搜索结果第一条记录标题中高亮的关键字个数
        int keyWordscount = driver.getElementCount(Ajk_Ask.HIGHLIGHT_KEY);
        // 获取搜索结果中出现的高亮关键字与搜索输入的关键字进行比较
        String expect = "";
        if (keyWordscount == 0) {
            String ps = driver.printScreen();
            Report.writeHTMLLog("search result", "search key words not found ", Report.FAIL, ps);
        } else {
            for (int i = 0; i < keyWordscount; i++) {
                expect = driver.getText("//ul[@class='q_list']/li[1]/div/a/span[" + (i + 1) + "]", "获取高亮的关键字");
                driver.assertContains(asksearch, expect);
            }
        }

    }

    // 异常搜索问答模块
    public void unusualAskSearch(String search) {
        // Report.setTCNameLog("检查安居客问答异常关键字搜索-- checkGuideLan");
        int dataCount = 0;
        driver.type(Ajk_Ask.S_BOX, search, "关键字搜索");
        driver.click(Ajk_Ask.S_BTN, "点击搜索");
        dataCount = driver.getElementCount(Ajk_Ask.NO_FOUND, 5);
        if (search == null || search.equals("  ")) {
            if (dataCount == 0) {
                Report.writeHTMLLog("异常搜索问答", "使用空格搜索，显示所有记录", "PASS", "");

            } else {
                String tmpPicName = driver.printScreen();
                Report.writeHTMLLog("异常搜索问答", "使用空格搜索，未发现记录", "FAIL", tmpPicName);
            }

        } else {
            if (dataCount == 0) {
                String tmpPicName = driver.printScreen();
                Report.writeHTMLLog("异常搜索问答", "异常搜索问答【" + search + "】发现记录", "FAIL", tmpPicName);
            } else {
                Report.writeHTMLLog("异常搜索问答", "异常搜索问答【" + search + "】未发现记录", "PASS", "");
            }
        }
    }

    // 正常关键字搜索
    public void normalSearch(String search, String site) {
        // Report.setTCNameLog("检查安居客正常关键字搜索-- checkGuideLan");
        String searchResult = null;
        int toutleCount = 0;
        int count = 0;

        //获取当前url，以应对关键字输入框位置会变的问题
        String currentURL = driver.getCurrentUrl();

        
        // 根据不同页面搜索结果，取回每页显示数量，以及搜索结果搜索数量
        if (site.equals("sale") || site.equals("rental")) {
        	driver.type(Ajk_Sale.KwInput, search, "关键字搜索");
            driver.click(Ajk_Sale.KwSubmit, "点击搜索");
            searchResult = driver.getText(Ajk_Sale.S_COUNT, "获取房源搜索结果");
            // 获取搜索所有数量
            toutleCount = Integer.parseInt(searchResult);
            // 获取单页显示数量
            count = driver.getElementCount(Ajk_Sale.LIST_COUNT);
            Report.writeHTMLLog("二手房/租房搜索结果", "二手房/租房搜索结果总量为：" + toutleCount + "套-----单页数量为：" + count + "套", Report.DONE, "");
        } else if (site.equals("community")) {
        	driver.type(Public_HeaderFooter.S_BOX, search, "关键字搜索");
            driver.click(Public_HeaderFooter.S_BTN, "点击搜索");
            searchResult = driver.getText(Ajk_Community.C_COUNT, "获取房源搜索结果");
            // 获取搜索所有数量
            toutleCount = Integer.parseInt(searchResult);
            // 获取单页显示数量
            count = driver.getElementCount(Ajk_Community.LIST_COUNT);
            Report.writeHTMLLog("小区搜索结果", "小区搜索结果总量为：" + toutleCount + "套-----单页数量为：" + count + "套", Report.DONE, "");
        } else {
        	driver.type(Public_HeaderFooter.S_BOX, search, "关键字搜索");
            driver.click(Public_HeaderFooter.S_BTN, "点击搜索");
            // 获取单页显示数量
            count = driver.getElementCount(Ajk_Tycoon.LIST_COUNT);
            Report.writeHTMLLog("经纪人搜索结果", "经纪人搜索结果单页数量：" + count, Report.DONE, "");
        }
        // 判断搜索结果是否为空
        if ((searchResult == null || searchResult.equals(" ")) && !site.equals("tycoon")) {
            String ps = driver.printScreen();
            Report.writeHTMLLog("normal search result", "normal search result is null", Report.FAIL, ps);
        } else if (count == 0 && site.equals("tycoon")) {
            String ps = driver.printScreen();
            Report.writeHTMLLog("normal search result", "normal search result is null", Report.FAIL, ps);
        } else if (count != 0 && site.equals("tycoon")) {
            Report.writeHTMLLog("normal search result", "normal search result is: " + count + "个经纪人", Report.PASS, "");
        } else {
            Report.writeHTMLLog("normal search result", "normal search result is: " + searchResult + "套房源", Report.PASS, "");
        }
        // 判断每页结果显示数量是否正确
        if ((site.equals("sale") || site.equals("rental")) && count != 25 && toutleCount > 25) {
            String ps = driver.printScreen();
            Report.writeHTMLLog("normal search result", site + " normal search result page count is：" + count + "套房源", Report.FAIL, ps);
        } else if ((site.equals("sale") || site.equals("rental")) && count == 25 && toutleCount > 25) {
            Report.writeHTMLLog("normal search result", site + " normal search result page count is：" + count + "套房源", Report.PASS, "");
        } else if (count > 10 && (site.equals("tycoon") || site.equals("community"))) {
            String ps = driver.printScreen();
            Report.writeHTMLLog("normal tycoonsearch result", site + " normal tycoon search result page count is：" + count, Report.PASS, ps);
        } else if (site.equals("community") && toutleCount > 10 && count != 10) {
            String ps = driver.printScreen();
            Report.writeHTMLLog("normal search result", site + " normal search result page count is：" + count, Report.PASS, ps);
        } else {
            Report.writeHTMLLog("normal tycoonsearch result", site + " normal tycoon search result page count is：" + count, Report.PASS, "");
        }
        driver.get(currentURL);
    }

    // 异常关键字搜索
    public void unusualSearch(String search, String site) {
        // Report.setTCNameLog("检查安居客异常关键字搜索-- checkGuideLan");
        int dataCount = 0;
        //获取当前url，以应对关键字输入框位置会变的问题
        String currentURL = driver.getCurrentUrl();

        if (site.equals("sale"))
        {
        	driver.type(Ajk_Sale.KwInput, search, "关键字搜索");
            driver.click(Ajk_Sale.KwSubmit, "点击搜索");
            dataCount = driver.getElementCount(Ajk_Sale.NO_FOUND, 5);
        } else if (site.equals("community")) {
        	driver.type(Public_HeaderFooter.S_BOX, search, "关键字搜索");
            driver.click(Public_HeaderFooter.S_BTN, "点击搜索");
            dataCount = driver.getElementCount(Ajk_Community.NO_FOUND, 5);
        } else {
        	driver.type(Public_HeaderFooter.S_BOX, search, "关键字搜索");
            driver.click(Public_HeaderFooter.S_BTN, "点击搜索");
            dataCount = driver.getElementCount(Ajk_Tycoon.NO_FOUND, 5);
        }
        if (search == null || search.equals("  ")) {
            if (dataCount == 0) {
                Report.writeHTMLLog("异常搜索关键字", "空格搜索将查找所有记录", "PASS", "");
            } else {
                String tmpPicName = driver.printScreen();
                Report.writeHTMLLog("异常搜索关键字", "使用空格搜索，检索结果为空", "FAIL", tmpPicName);
            }

        } else {
            if (dataCount == 0 && driver.check("//div[@class='jiong']") == false) {
                String tmpPicName = driver.printScreen();
                Report.writeHTMLLog("异常搜索关键字", "异常搜索关键字【" + search + "】发现记录", "FAIL", tmpPicName);
            } else {
                Report.writeHTMLLog("异常搜索关键字", "异常搜索关键字【" + search + "】未发现记录", "PASS", "");
            }
        }

        driver.get(currentURL);
    }

    // 检查搜索框引导语内容是否为空
    public void checkGuideLan(String site) {
        if (!site.equals("ask")) {
        	String guideLan = null;
        	if(site.equals("sale"))
        	{
        		guideLan = driver.getAttribute(Ajk_Sale.KwInput, "value");
        	}
        	else
        	{
        		guideLan = driver.getAttribute(Public_HeaderFooter.S_BOX, "value");
        	}
            // 默认引导语显示
            // Report.setTCNameLog("检查安居客关键字搜索引导语（默认显示） -- checkGuideLan");
            if (guideLan == null || guideLan.equals("")) {
                String ps = driver.printScreen();
                Report.writeHTMLLog("default search guide language", "search guide content is null", Report.FAIL, ps);
            } else {
                Report.writeHTMLLog("default search guide language", "search guide content is ：" + guideLan, Report.PASS, "");
            }
            // 点击搜索框后，引导语消失
            // Report.setTCNameLog("检查安居客关键字搜索引导语 （点击消失）-- checkGuideLan");
            /*
             * driver.click(Init.G_objMap.get("anjuke_keywords_search"),
             * "点击搜索框"); guideLan =
             * driver.getAttribute(Init.G_objMap.get("anjuke_keywords_search"),
             * "value"); if(guideLan==null||guideLan.equals("")){
             * Report.writeHTMLLog("after click search guide language",
             * "search guide language is null", Report.PASS, ""); }else{ String
             * ps = driver.printScreen();
             * Report.writeHTMLLog("after click search guide language",
             * "search guide language is :"+guideLan, Report.FAIL, ps); }
             */
        } else {
            // 问答版块默认引导语显示
            // Report.setTCNameLog("检查安居客问答版本搜索引导语（默认显示） -- checkGuideLan");
            String askguideLan = null;
            askguideLan = driver.getAttribute(Ajk_Ask.S_BOX, "nullvalue");
            if (askguideLan == null || askguideLan.equals("")) {
                String ps = driver.printScreen();
                Report.writeHTMLLog("default search guide language", "search guide language is null", Report.FAIL, ps);
            } else {
                Report.writeHTMLLog("default search guide language", "search guide language is ：" + askguideLan, Report.PASS, "");
            }
            // 问答板块点击搜索框后，引导语消失
            // Report.setTCNameLog("检查安居客问答版本搜索引导语 （点击消失）-- checkGuideLan");
            /*
             * driver.click(Init.G_objMap.get("anjuke_ask_text"), "点击搜索框");
             * askguideLan =
             * driver.getAttribute(Init.G_objMap.get("anjuke_ask_text"),
             * "nullvalue"); if(askguideLan==null||askguideLan.equals("")){
             * Report.writeHTMLLog("after click ask search guide language",
             * "after click ask search guide language is null", Report.PASS,
             * ""); }else{
             * Report.writeHTMLLog("after click ask search guide language",
             * "after click ask search guide language is null", Report.PASS,
             * ""); String ps = driver.printScreen();
             * Report.writeHTMLLog("after click ask search guide language",
             * "after click ask search guide language is :"+askguideLan,
             * Report.FAIL, ps); }
             */
        }
    }
}
