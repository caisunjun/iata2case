package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;

import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;

/**
 * 该用例主要用来做安居客网站经纪人取消推荐房源操作，步骤简介如下： 
 * 1.以经纪人账户登录网站 
 * 2.进入经纪人管理页面（出售和出租)
 * 3.分别进入取消推荐房源，推荐房源tab页，并记录相关页面房源总数 
 * 4.在推荐房源tab页，选中一套房源并选择取消推荐
 * 5.进入取消推荐房源tab页，通过房源ID搜索出相应的房源，检查是否能找到取消推荐的房源，并检查取消推荐前后各tab页的房源数量是否正确
 * 6.最后进行删除房源操作
 * 
 * @author Williamhu
 * @last updatetime 2012/05/08
 */

public class AnjukeCancelRecommendHouse {
	private Browser bs = null;
	private Boolean flag = false;  	// 设定一个标志位，如果flag为true则表示取消推荐出租房源，否则为取消推荐购买房源


	@BeforeMethod
	public void startUp() {
		bs = FactoryBrowser.factoryBrowser();
	}

	@AfterMethod
	public void teardown() {
		bs.quit();
		bs = null;
	}
    @SuppressWarnings("deprecation")
	@Configuration(afterTestClass = true)
	public void doBeforeTests() {
		System.out.println("***AnjukeCancelRecommendHouse is done***");
	}
	//(timeOut = 250000)
	@Test
	public void testCancelRecommendBuyHouse() throws InterruptedException {		
		//Report.setTCNameLog("*****取消推荐购买房源-- AnjukeCancelRecommendBuyHouse --williamhu");
		cancelRecommendHouse(flag);
	}
	
	@Test
	public void testCancelRecommendRentHouse() throws InterruptedException{		
		//Report.setTCNameLog("*****取消推荐出租房源-- AnjukeCancelRecommendRentHouse --williamhu");
		flag = true;
		cancelRecommendHouse(flag);		
	}

	public void cancelRecommendHouse(boolean bool) {

		int notRecommendCountBefore = 0; // 未推荐之前非推荐房源数量
		int recommendCountBefore = 0; // 未推荐之前推荐房源数量
		int notRecommendCountAfter = 0; // 推荐后未推荐房源数量
		int recommendCountAfter = 0; // 推荐之后推荐房源数量
		String strHouseID = ""; // 房源编号字符串
		String houseID = ""; // 房源编号
		String communityName = ""; // 推荐房源小区名
		String title = ""; // 推荐房源信息标题

		// 以经纪人身份登录anjuke网站
		if (!bool) {
			// 清空当前浏览器的cookie
//			bs.deleteAllCookies();
		} 
		//For anjuke daily
		PublicProcess.dologin(bs, "cdtest", "anjukeqa");
        //For other
//		PublicProcess.dologin(bs, "ajk_sh", "anjukeqa");


		// 点击主页右上角的我的经纪人链接
		bs.check(Init.G_objMap.get("cityhomepage_login_broker_username_link"), 5);

		bs.click(Init.G_objMap.get("cityhomepage_login_broker_username_link"), "选中我的经纪人目录");

		// 根据传进来的标志位来判断点击管理出售链接还是管理出租链接
		if (!bool) {
			bs.click(Init.G_objMap.get("anjuke_broke_manage_sale"), "点击管理出售链接");
		} else {
			bs.click(Init.G_objMap.get("anjuke_broke_manage_rent"), "点击管理出租链接");
		}

		// 点击非推荐标签页
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_Nrec_tab"), "点击非推荐房源链接");

		// 如果取不到非推荐房源数量，则表是非推荐房源数位0，否则为取到的数
		if (bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_total"))) {
			notRecommendCountBefore = Integer.parseInt(bs.getText(Init.G_objMap.get("anjuke_broke_manage_sale_total"), "获取非推荐房源总数"));
		} else {
			// 非推荐房源数目元素取不到，表示数目为0
			notRecommendCountBefore = 0;
			Report.writeHTMLLog("取消推荐房源", "非推荐房源数目元素取不到，表示数目为0", Report.PASS, "");
		}

		// 点击推荐房源标签页
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_rec_tab"), "点击推荐房源链接");

		if (bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_total"))) {
			recommendCountBefore = Integer.parseInt(bs.getText(Init.G_objMap.get("anjuke_broke_manage_sale_total"), "获取推荐房源总数"));
		} else {
			// 推荐房源数目元素取不到，表示数目为0,case失败退出
			String ps = bs.printScreen();
			Report.writeHTMLLog("取消推荐房源", "非推荐房源数量为0，case失败退出", Report.PASS, ps);
			return;
		}

		int pageCount;
		int totalCount;
		if(bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_total"))){
			totalCount = Integer.parseInt(bs.getText(Init.G_objMap.get("anjuke_broke_manage_sale_total"), "获取房源总数"));			
		}else{
			totalCount = 0;
		}

		if (totalCount > 20) {
			pageCount = 20;
		} else {
			pageCount = totalCount;
		}

		// 只有当选取的房源记录没有“违规”标志的时候才可选取
		int i = 0;
		for (i = 1; i <= pageCount; i++) {
			if (bs.getAttribute("//tr[" + i + "][@id='apf_id_7']/td[1]/input", "disabled").equals("true")) {
				Report.writeHTMLLog("取消推荐房源", "当前列房源不可被选取", Report.DONE, "");
			} else {
				Report.writeHTMLLog("取消推荐房源", "当前列即第" + i + "房源可以可被选取", Report.DONE, "");
				break;
			}
		}

		// 选中该套房源
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_checkbox_IE8") + (i - 1), "选中第" + i + "套房源");

		// 获取选中套房源的小区名
		communityName = bs.getText(Init.G_objMap.get("anjuke_broke_manage_sale_house_link_IE8") + (i - 1), "获取选中房源小区名");

		// 该套房源信息的标题
		title = bs.getText("//tr[" + i + "][@id='apf_id_7']/td[2]/p[2]", "获取选中房源标题");

		// 点击该套房源的小区链接
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_house_link_IE8") + (i - 1), "点击该套房源的小区链接");
		bs.switchWindo(2);

		// 获取选取房源的编号
		if (bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_houseID"), 5)) {
			strHouseID = bs.getText(Init.G_objMap.get("anjuke_broke_manage_sale_houseID"), "截取房源编号字符串");
		}

		houseID = PublicProcess.splitString(strHouseID, "：");

		Report.writeHTMLLog("推荐房源", "推荐房源的ID号为：".concat(houseID), Report.PASS, "");

		bs.close();
		bs.switchWindo(1);

		// 点击取消推荐按钮
		if (bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_cancel_recommend_btn_IE8"), 5)) {
			bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_cancel_recommend_btn_IE8"), "点击取消推荐房源按钮");
		}

		if (bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_rec_succ_prompt_IE8"))) {
			Report.writeHTMLLog("取消推荐房源", "取消推荐房源提示成功显示", Report.PASS, "");
		} else {
			String ps = bs.printScreen();
			Report.writeHTMLLog("取消推荐房源", "取消推荐房源成功提示未显示", Report.FAIL, ps);
		}

		// 获取取消推荐房源后推荐房源总数
		if (bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_total"))) {
			recommendCountAfter = Integer.parseInt(bs.getText(Init.G_objMap.get("anjuke_broke_manage_sale_total"), "获取推荐房源总数"));
		} else {
			recommendCountAfter = 0;
		}

		// 点击非推荐房源链接
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_Nrec_tab"), "点击非推荐链接");
		if (bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_total"))) {
			notRecommendCountAfter = Integer.parseInt(bs.getText(Init.G_objMap.get("anjuke_broke_manage_sale_total"), "获取非推荐房源总数"));
		} else {
			notRecommendCountAfter = 0;
			Report.writeHTMLLog("取消推荐房源", "取消推荐房源后非推荐房源总数为0", Report.PASS, "");
		}

		if (notRecommendCountAfter == notRecommendCountBefore + 1 && recommendCountAfter == recommendCountBefore - 1) {
			Report.writeHTMLLog("取消推荐房源", "验证房源数量成功", Report.PASS, "");
		} else {
			String ps = bs.printScreen();
			Report.writeHTMLLog("取消推荐房源", "验证房源数量失败", Report.FAIL, ps);
		}

		// 用指定的房源ID号搜索出相应的房源
		bs.type(Init.G_objMap.get("anjuke_broke_manage_sale_houseID_search_input"), houseID, "在房源编号搜索框中输入推荐的房源编号");
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_houseID_search_btn"), "点击搜索按钮");
		if (!bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_first_house_link_IE8"))) {
			String ps = bs.printScreen();
			Report.writeHTMLLog("取消推荐房源", "未能搜索到任何房源，用例失败退出", Report.FAIL, ps);
			return;
		}
		Report.writeHTMLLog("取消推荐房源", "正确搜索到房源", Report.PASS, "");

		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_first_house_link_IE8"), "点击搜索到的房源小区链接");

		bs.switchWindo(2);
		bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_comm_name"), 3);

		if (communityName.equals(bs.getText(Init.G_objMap.get("anjuke_broke_manage_sale_comm_name"), "获取打开房源页面的小区名"))
				&& title.equals(bs.getText(Init.G_objMap.get("anjuke_broke_manage_sale_house_info_title"), "获取打开房源页面的标题"))) {
			Report.writeHTMLLog("取消推荐房源", "正确匹配房源小区名和房源标题", Report.PASS, "");
		} else {
			String ps = bs.printScreen();
			Report.writeHTMLLog("取消推荐房源", "未能匹配房源小区名和房源标题", Report.FAIL, ps);
		}
		bs.close();
		bs.switchWindo(1);
	}
}
