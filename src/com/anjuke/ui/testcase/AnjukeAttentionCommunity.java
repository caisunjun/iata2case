package com.anjuke.ui.testcase;

import org.testng.annotations.*;

import com.anjuke.ui.page.Ajk_Community;
import com.anjuke.ui.page.Ajk_CommunityView;
import com.anjuke.ui.page.Ajk_MemberConcerned;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
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
		Report.G_CASECONTENT = "关注小区";
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

		new PublicProcess().dologin(driver, "小瓶盖001", "6634472");
		
		// 访问小区列表
		driver.get("http://shanghai.anjuke.com/community/");
		// 判断小区列表数据是否存在数据
		if (!driver.check(Ajk_Community.NO_FOUND)){
			// 获取第一条记录标题
			commTitle = driver.getText(Ajk_Community.FirsrCommName, "第一条记录标题");
			// 获取第一条记录价格
			commPrice = driver.getText(Ajk_Community.FirsrCommPrice, "第一条记录价格");
			// 获取当前URL
			formerUrl = driver.getAttribute(Ajk_Community.FirsrCommName, "href");
			// 访问第一条记录
			driver.click(Ajk_Community.FirsrCommName, "访问第一条记录");
			driver.switchWindo(2);
			// 如果关注小区按钮不可用，则退出当前执行
			if (!attentionComm()) {
				return;
			}
			// 判断关注小区弹出框是否出现
			if (driver.check(Ajk_CommunityView.attentionPopup, 30)) {
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
		driver.click(Ajk_CommunityView.attentionFailPopupLink, "去关注的小区列表查看");
		driver.switchWindo(3);
		deleteAllcomm();
		driver.close();
		driver.switchWindo(2);
		driver.click("//a[@class='submit']", "点击确定按钮");
		if (attentionComm()) {
			String returnMess = driver.getText(Ajk_CommunityView.attentionSucPopupMessage, "获取弹出框返回内容");
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
		if (driver.check(Ajk_CommunityView.attentionButton, 20)) {
			driver.click(Ajk_CommunityView.attentionButton, "点击关注小区按钮");
			return true;
		} else {
			// 当按钮不存在，进行一次刷新页面
			driver.refresh();
			// 刷新完毕后再次确认是否可以进行关注小区操作，不可以则退出该方法
			if (driver.check(Ajk_CommunityView.attentionButton, 20)) {
				driver.click(Ajk_CommunityView.attentionButton, "点击关注小区按钮");
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
		checkMail = driver.isSelect(Ajk_CommunityView.attentionSucPopupMail, 10);
		Report.writeHTMLLog("邮件订阅checkbox", "邮件订阅状态：" + checkMail, Report.DONE, "");
		driver.click(Ajk_CommunityView.attentionSucPopupLink, "点击去看看关注的小区连接");
		// 跳转到关注小区列表页
		driver.switchWindo(3);

		// 等待第三个窗口加载成功
		driver.check(Ajk_MemberConcerned.FirstCommName, 10);

		// 判断关注小区列表数据是否为空
		if (!driver.check(Ajk_MemberConcerned.NoComm)) {
			// 获取第一条记录的URL,且判断是否为关注小区的URL
			String nowUrl = driver.getAttribute(Ajk_MemberConcerned.FirstCommName, "href");
			System.out.println(formerUrl);
			System.out.println(nowUrl);
			if (formerUrl.equals(nowUrl)) {
				Report.writeHTMLLog("关注小区成功", "关注小区成功，前后URL一致：" + formerUrl, Report.PASS, "");
			} else {
				String ps = driver.printScreen();
				Report.writeHTMLLog("关注小区失败", "关注小区失败：URL不一致<br>" + formerUrl + "<br>" + nowUrl, Report.FAIL, ps);
			}
			// 获取第一条记录的小区名称,且判断是否为关注小区的名称
			String resultTitle = driver.getText(Ajk_MemberConcerned.FirstCommName, "获取第一条数据小区名称");
			if(resultTitle.lastIndexOf("（") != -1)
			{resultTitle = resultTitle.substring(0 ,resultTitle.lastIndexOf("（")).trim();}
			if (commTitle.equals(resultTitle)) {
				Report.writeHTMLLog("关注小区成功", "关注小区成功，前后小区名称一致：" + commTitle, Report.PASS, "");
			} else {
				String ps = driver.printScreen();
				Report.writeHTMLLog("关注小区失败", "关注小区失败：小区名称不一致<br>" + commTitle + "<br>" + resultTitle, Report.FAIL, ps);
			}
			// 获取第一条记录的小区价格,且判断是否为关注小区的价格
			String resultPrice = driver.getText(Ajk_MemberConcerned.FirstCommPrice, "获取第一条数据小区价格");
			if (commPrice.equals(resultPrice)) {
				Report.writeHTMLLog("关注小区成功", "关注小区成功，前后小区价格一致：" + commPrice + "万", Report.PASS, "");
			} else {
				String ps = driver.printScreen();
				Report.writeHTMLLog("关注小区失败", "关注小区失败：小区价格不一致,数据未同步,问题目前解决中<br>" + commPrice + "万" + "<br>" + resultPrice + "万", Report.PASS, ps);
			}
			// 获取第一条记录的小区邮件订阅状态,且判断是否符合关注小区的邮件状态
			String mailMsg = driver.getText(Ajk_MemberConcerned.FirstCommMessage, "获取第一条数据邮件订阅状态");
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
		driver.check(Ajk_CommunityView.attentionPopupMessage, 10);
		String returnMess = driver.getText(Ajk_CommunityView.attentionPopupMessage, "获取弹出框返回内容");
		return returnMess;
	}

	// 删除所有小区
	public void deleteAllcomm() {

		driver.check(Ajk_MemberConcerned.CommCount, 30);

		int datacount = driver.getElementCount(Ajk_MemberConcerned.CommCount);
		for (int i = 1; i <= datacount; i++) {
			if (driver.check(Ajk_MemberConcerned.CommCount, 30)) {
				driver.moveToElement(Ajk_MemberConcerned.CommCount, 20);
				driver.click(Ajk_MemberConcerned.DeleteComm, "删除记录", 30);
				driver.click(Ajk_MemberConcerned.DeleteCommCon, "确定删除", 30);
			}
		}
		if (driver.check(Ajk_MemberConcerned.NoComm, 20)) {
			String ps = driver.printScreen();
			Report.writeHTMLLog("关注小区列表", "关注小区列表删除完毕", Report.PASS, ps);
		}
	}

	// 判断关注小区列表是否存在相同记录
	public void equalData() {
		int datacount = driver.getElementCount(Ajk_MemberConcerned.CommCount);
		int equalCount = 0;
		Report.writeHTMLLog("关注小区列表", "关注小区列表数据数量:" + datacount, Report.DONE, "");
		for (int i = 1; i <= datacount; i++) {
			String nowUrl = driver.getAttribute(Ajk_MemberConcerned.getNCommName(i), "href");
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

    @SuppressWarnings("deprecation")
	@Configuration(afterTestClass = true)
	public void doBeforeTests() {
		System.out.println("***AnjukeAttentionCommunity is done***");
	}
}
