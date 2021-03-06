package com.anjuke.ui.testcase;

import com.anjuke.ui.page.Ajk_ShopView;
import com.anjuke.ui.page.Broker_areas;
import com.anjuke.ui.page.Broker_profile;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @Todo 店铺管理-职业能力-擅长区域 更改熟悉版块（随机） 更改熟悉小区（随机） 店铺页验证
 * @author fjzhang
 * @since 2012-8-15
 * @file AnjukeBrokerpractice.java
 * @url http://my.anjuke.com/user/broker/areas
 * 
 */
public class AnjukeBrokerpractice {
	Browser bs = null;
	String url = "http://my.anjuke.com/user/broker/areas";
	String username = "test1";
	String passwd = "111111";

	@BeforeMethod
	public void setUp() {
		Report.G_CASECONTENT = "经纪人更改熟悉小区和熟悉版块";
		bs = FactoryBrowser.factoryBrowser();
	}

	@AfterMethod
	public void tearDown() {
		bs.quit();
		bs = null;
	}

	@Test
	public void testBrokerPractice() throws InterruptedException {
		/**
		 * 验证执业能力---擅长区域
		 */
		new PublicProcess().dologin(bs, username, passwd);
		bs.get(url);
		/*
		 * 随机选择熟悉的板块，并返回选择的值
		 */
		bs.check(Broker_areas.AREA1);
		String area1 = bs.selectReturn(Broker_areas.AREA1);
		Thread.sleep(1000);
		String block1 = bs.selectReturn(Broker_areas.BLOCK1);
		String area2 = bs.selectReturn(Broker_areas.AREA2);
		Thread.sleep(1000);
		String block2 = bs.selectReturn(Broker_areas.BLOCK2);
		/*
		 * 随机选择熟悉的小区
		 */
		bs.type(Broker_areas.COMM1, "s ", "输入熟悉的小区1");
		liSelect(Broker_areas.IFRAME1, bs, Broker_areas.COMMDIV, "选择熟悉的小区");
		bs.type(Broker_areas.COMM2, "g ", "输入熟悉的小区2");
		liSelect(Broker_areas.IFRAME2, bs, Broker_areas.COMMDIV, "选择熟悉的小区");
		bs.type(Broker_areas.COMM3, "m ", "输入熟悉的小区3");
		liSelect(Broker_areas.IFRAME3, bs, Broker_areas.COMMDIV, "选择熟悉的小区");

		bs.click(Broker_areas.OKENTER, "确认保存");
		String savetext = bs.getText(Broker_areas.SAVEOK, "取得保存后提示文案");
		bs.assertContains(savetext, "保存成功");

		String comm1 = bs.getAttribute(Broker_areas.COMM1, "value");
		String comm2 = bs.getAttribute(Broker_areas.COMM2, "value");
		String comm3 = bs.getAttribute(Broker_areas.COMM3, "value");

		area1 = area1 + "-" + block1;
		area2 = area2 + "-" + block2;
		/*
		 * 进入我的店铺
		 */
		bs.click(Broker_profile.MYSHOP, "进入我的店铺");
		bs.switchWindo(2);
		String knowncomm = bs.getText(Ajk_ShopView.KNOWNCOMM, "获取最熟悉的小区 ");
		String knownarea = bs.getText(Ajk_ShopView.KNOWNAREA, "获取最熟悉的区域");
		
		String tmpUrl = bs.getCurrentUrl()+ "?cc=cc";
		bs.get(tmpUrl);
		
		knowncomm = bs.getText(Ajk_ShopView.KNOWNCOMM, "获取最熟悉的小区 ");
		knownarea = bs.getText(Ajk_ShopView.KNOWNAREA, "获取最熟悉的区域");
		bs.assertOneContainsMany(knowncomm, "验证我的店铺中小区显示是否完整", comm1, comm2, comm3);
		bs.assertOneContainsMany(knownarea, "验证我的店铺中区域板块显示是否完整", area1,area2);
	}

	private void liSelect(String frame, Browser bs, String locator, String Info){
		bs.switchToIframe(frame, "");
		//随机选前10个小区
		bs.click(Broker_areas.SelectCommInRange(10), "下拉中随机选一个小区");
		bs.exitFrame();
		return;
	}

	private boolean checkOneContainsMany(String actual, String scase, String... expected) {
		boolean tag = true;
		for (String text : expected) {
			if (!actual.contains(text)) {
				tag = false;
				break;
			}
		}
		if (tag) {
			Report.writeHTMLLog(scase, actual + "中包含所有期望的值", Report.PASS, "");
		}
		return tag;
	}

}
