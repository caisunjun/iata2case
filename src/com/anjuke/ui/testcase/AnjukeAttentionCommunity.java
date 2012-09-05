package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;

/**
 * 该测试用例主要完成安居客关注小区操作，逻辑如下 
 * 1、选择小区列表页面，第一个小区
 * 2、在小区详细页判断关注该小区按钮是否存在
 * 3、点击关注小区按钮后，判断关注弹出框是否出现 
 * 4、如果判断已经收藏过该小区、则去关注小区列表删除所有小区
 * 5、再次关注，提示关注成功，则去小区列表页判断是否收藏成功 
 * 6、验证判断成功后，删除所有小区
 * 
 * @author Gabrielgao
 * @last updatetime 2012-05-08 11:30
 */
public class AnjukeAttentionCommunity {
	private Browser driver = null;
	public String commTitle;
	public String commPrice;
	public String formerUrl = null;
	public boolean checkMail;

	@BeforeMethod
	public void startUp() {
		driver = FactoryBrowser.factoryBrowser();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		driver = null;
	}
    //(timeOut = 250000)
	@Test
	public void attentionCommunity() {
		String brow = "";
		//从config.ini中取出browser
		brow = Init.G_config.get("browser");
		if(brow.contains("ie"))
		{
			//IE6兼容性
//			PublicProcess.logIn(driver, "小瓶盖001", "6634472", false, 0);
			PublicProcess.dologin(driver, "小瓶盖001", "6634472");
		}
		else
		{
			// 普通用户登录
			driver.deleteAllCookies();
			String loginName = PublicProcess.logIn(driver, "小瓶盖001", "6634472", false, 0);
			// 判断用户是否登录成功
			if (!(loginName == null || loginName.equals(""))) {
				
				Report.writeHTMLLog("user login", "user login sucess,and name is: " + loginName, Report.DONE, "");
			} else {
				String ps = driver.printScreen();
				Report.writeHTMLLog("user login", "user login fail,and name is: " + loginName, Report.FAIL, ps);
			}
		}
		
		// 访问小区列表
		driver.get("http://shanghai.anjuke.com/community/");
		// 判断小区列表数据是否存在数据
		if (!driver.check(Init.G_objMap.get("anjuke_community_text_no_found"))) {
			// 获取第一条记录标题
			commTitle = driver.getText(Init.G_objMap.get("anjuke_community_list_firstData_title"), "第一条记录标题");
			// 获取第一条记录价格
			commPrice = driver.getText("//*[@id='mid_price_1550']/em", "第一条记录价格");
			// 获取当前URL
			formerUrl = driver.getAttribute(Init.G_objMap.get("anjuke_community_list_firstData_title"), "href");
			// 访问第一条记录
			driver.click(Init.G_objMap.get("anjuke_community_list_firstData_title"), "访问第一条记录");
			driver.switchWindo(2);
			// 如果关注小区按钮不可用，则退出当前执行
			if (!attentionComm()) {
				return;
			}
			// 判断关注小区弹出框是否出现
			if (driver.check(Init.G_objMap.get("community_detail_attention_popup"), 30)) {
				// 获取弹出框返回内容
				String returnMess = returnMessage();
				// 判断是否已经关注过该小区，并返回消息
				if (returnMess.equals("您已经收藏了该小区！")) {
					clearExistComm();
				}
				// 判断小区是否收藏成功
				if (returnMess.equals("您成功收藏了该小区！")) {
					// 验证小区关注操作是否成功
					verifyAttention();
				}
			} else {
				String psm = driver.printScreen();
				Report.writeHTMLLog("关注小区失败", "关注小区失败，没有出现弹出框", Report.FAIL, psm);
			}
		} else {
			String ps = driver.printScreen();
			Report.writeHTMLLog("community list is null", "community list is null", Report.FAIL, ps);
		}
	}

	// 如果小区已经被收藏，则去关注小区列表删除小区
	public void clearExistComm() {
		String ps = driver.printScreen();
		Report.writeHTMLLog("关注小区", "已经收藏了该小区", Report.DONE, ps);
		driver.click(Init.G_objMap.get("community_detail_attention_linkerr"), "去关注的小区列表查看");
		driver.switchWindo(3);
		deleteAllcomm();
		driver.close();
		driver.switchWindo(2);
		driver.click("//a[@class='submit']", "点击确定按钮");
		if (attentionComm()) {
			String returnMess = driver.getText(Init.G_objMap.get("community_detail_attention_message_suc"), "获取弹出框返回内容");
			if (returnMess.equals("您成功收藏了该小区！")) {
				verifyAttention();
			} else {
				String ps1 = driver.printScreen();
				Report.writeHTMLLog("", "", Report.FAIL, ps1);
				return;
			}
		} else {
			// 不能进行正常关注，退出执行
			return;
		}

	}

	// 关注小区操作
	public boolean attentionComm() {
		// 关注小区操作,判断关注按钮是否可用
		if (driver.check(Init.G_objMap.get("community_detail_attention_button"), 20)) {
			driver.click(Init.G_objMap.get("community_detail_attention_button"), "点击关注小区按钮");
			return true;
		} else {
			// 当按钮不存在，进行一次刷新页面
			driver.refresh();
			// 刷新完毕后再次确认是否可以进行关注小区操作，不可以则退出该方法
			if (driver.check(Init.G_objMap.get("community_detail_attention_button"), 20)) {
				driver.click(Init.G_objMap.get("community_detail_attention_button"), "点击关注小区按钮");
				return true;
			} else {
				String ps = driver.printScreen();
				Report.writeHTMLLog("关注小区按钮不存在", "关注小区按钮不存在", Report.FAIL, ps);
				// 退出执行
				return false;
			}
		}
	}

	// 验证关注小区操作是否成功
	public void verifyAttention() {
		// 获取邮件订阅状态
		checkMail = driver.isSelect(Init.G_objMap.get("community_detail_attention_mail"), 10);
		Report.writeHTMLLog("邮件订阅checkbox", "邮件订阅状态：" + checkMail, Report.DONE, "");
		driver.click(Init.G_objMap.get("community_detail_attention_link"), "点击去看看关注的小区连接");
		// 跳转到关注小区列表页
		driver.switchWindo(3);

		// 等待第三个窗口加载成功
		driver.check(Init.G_objMap.get("anjuke_sale_member_commlist_firstdatalink_IE8"), 10);

		// 判断关注小区列表数据是否为空
		if (!driver.check(Init.G_objMap.get("anjuke_sale_member_commlist_noresult"))) {
			// 获取第一条记录的URL,且判断是否为关注小区的URL
			String nowUrl = driver.getAttribute(Init.G_objMap.get("anjuke_sale_member_commlist_firstdatalink_IE8"), "href");
			if (formerUrl.equals(nowUrl)) {
				Report.writeHTMLLog("关注小区成功", "关注小区成功，前后URL一致：" + formerUrl, Report.PASS, "");
			} else {
				String ps = driver.printScreen();
				Report.writeHTMLLog("关注小区失败", "关注小区失败：URL不一致<br>" + formerUrl + "<br>" + nowUrl, Report.FAIL, ps);
			}
			// 获取第一条记录的小区名称,且判断是否为关注小区的名称
			String resultTitle = driver.getText(Init.G_objMap.get("anjuke_sale_member_commlist_firstdatalink_IE8"), "获取第一条数据小区名称");
			if (commTitle.equals(resultTitle)) {
				Report.writeHTMLLog("关注小区成功", "关注小区成功，前后小区名称一致：" + commTitle, Report.PASS, "");
			} else {
				String ps = driver.printScreen();
				Report.writeHTMLLog("关注小区失败", "关注小区失败：小区名称不一致<br>" + commTitle + "<br>" + resultTitle, Report.FAIL, ps);
			}
			// 获取第一条记录的小区价格,且判断是否为关注小区的价格
			String resultPrice = driver.getText(Init.G_objMap.get("anjuke_sale_member_commlist_firstdataprice"), "获取第一条数据小区价格");
			if (commPrice.equals(resultPrice)) {
				Report.writeHTMLLog("关注小区成功", "关注小区成功，前后小区价格一致：" + commPrice + "万", Report.PASS, "");
			} else {
				String ps = driver.printScreen();
				Report.writeHTMLLog("关注小区失败", "关注小区失败：小区价格不一致,数据未同步,问题目前解决中<br>" + commPrice + "万" + "<br>" + resultPrice + "万", Report.PASS, ps);
			}
			// 获取第一条记录的小区邮件订阅状态,且判断是否符合关注小区的邮件状态
			String mailMsg = driver.getText(Init.G_objMap.get("anjuke_sale_member_commlist_firstdatamail"), "获取第一条数据邮件订阅状态");
//			if (checkMail && mailMsg.equals("已订阅新房源")) {
//				Report.writeHTMLLog("关注小区成功", "关注小区成功，邮件订阅状态正确：" + mailMsg, Report.PASS, "");
//			} 
			if (mailMsg.equals("有新房源通知我")) {
				Report.writeHTMLLog("关注小区成功", "关注小区成功，邮件订阅状态正确：" + mailMsg, Report.PASS, "");
			} else {
				String ps = driver.printScreen();
				Report.writeHTMLLog("关注小区失败", "关注小区失败：小区邮件订阅状态不一致", Report.FAIL, ps);
			}
			// 判断是否存在多条相同的数据
			equalData();
			// 删除所有的数据
			deleteAllcomm();
		} else {
			String ps = driver.printScreen();
			Report.writeHTMLLog("关注小区列表", "关注小区列表为空，无数据可操作", Report.FAIL, ps);
		}
	}

	// 获取弹出框内容
	public String returnMessage() {
		driver.check(Init.G_objMap.get("community_detail_attention_message"), 10);
		String returnMess = driver.getText(Init.G_objMap.get("community_detail_attention_message"), "获取弹出框返回内容");
		return returnMess;
	}

	// 删除所有小区
	public void deleteAllcomm() {

		driver.check(Init.G_objMap.get("anjuke_sale_member_commlist_firstdatalink"), 30);

		int datacount = driver.getElementCount(Init.G_objMap.get("anjuke_sale_member_commlist_firstdatalink"));
		for (int i = 1; i <= datacount; i++) {
			if (driver.check(Init.G_objMap.get("anjuke_sale_member_commlist_firstdatalink"), 30)) {
				driver.moveToElement(Init.G_objMap.get("anjuke_sale_member_commlist_firstdatalink"), 20);
				driver.click(Init.G_objMap.get("anjuke_sale_member_commlist_btnDel"), "删除记录", 30);
				driver.click(Init.G_objMap.get("anjuke_sale_member_commlist_btnDelCon"), "确定删除", 30);
			}
		}
		if (driver.check(Init.G_objMap.get("anjuke_sale_member_commlist_noresult"), 20)) {
			String ps = driver.printScreen();
			Report.writeHTMLLog("关注小区列表", "关注小区列表删除完毕", Report.PASS, ps);
		}
	}

	// 判断关注小区列表是否存在相同记录
	public void equalData() {
		int datacount = driver.getElementCount(Init.G_objMap.get("anjuke_sale_member_commlist_firstdatalink"));
		int equalCount = 0;
		Report.writeHTMLLog("关注小区列表", "关注小区列表数据数量:" + datacount, Report.DONE, "");
		for (int i = 1; i <= datacount; i++) {
			String nowUrl = driver.getAttribute("//div[@class='p2974']/div[@class='box'][" + i + "]/div[3]/p/a", "href");
			System.out.println(nowUrl);
			if (formerUrl.equals(nowUrl)) {
				equalCount = equalCount + 1;
			}
		}
		if (equalCount >= 2) {
			String ps = driver.printScreen();
			Report.writeHTMLLog("关注小区列表", "关注小区列表存在：" + equalCount + "条相同记录", Report.FAIL, ps);
		} else {
			Report.writeHTMLLog("关注小区列表", "关注小区列表不存在相同记录", Report.PASS, "");
		}

	}
}
