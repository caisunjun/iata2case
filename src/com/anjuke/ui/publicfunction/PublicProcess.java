package com.anjuke.ui.publicfunction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.anjuke.ui.page.Broker_PropertynewRentStep;
import com.anjuke.ui.page.Public_HeaderFooter;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;
import com.anjukeinc.iata.ui.util.TcTools;

public class PublicProcess {
	private static String url = "http://my.anjuke.com/my/login?history=aHR0cDovL3NoYW5naGFpLmFuanVrZS5jb20v";
	private static String homeUrl = "http://shanghai.anjuke.com/";
	private static String versionUrl = "http://www.anjuke.com/version/switch/";
	private static String logInSuccName = null;

	/*
	 * 简介：该方法为安居客登录的公共方法，用户身份分为普通用户和经纪人 原因：因为普通用户以及经纪人用户的登录、退出的页面元素地址不同，所以操作分开进行
	 * 使用：使用分为以下情况 1、用户直接访问登录页面，进行登录 2、用户正在访问某些页面，然后需要执行登录操作，当前状态如下
	 * 1.当前用户已经登录（身份：普通用户） 以上情况分为三种：1.直接返回，不需要再登录 2.换另外一个普通用户登录 3.换经纪人身份登录
	 * 2.当前用户已经登录（身份：经纪人用户） 以上情况分为三种：1.直接返回，不需要再登录 2.换另外一个普通用户登录 3.换经纪人身份登录
	 * 3.当前用户未登录 以上情况分为两种：1.以普通用户登录 2.以经纪人进行登录 参数：参数含义如下 name/pass为需要登录的用户名以及密码
	 * relogin用来表示如果用户已经登录，是否需要重新登录（true：需要；false：不需要）
	 * id用来表示将以普通用户登录还是经纪人（0：普通用户；1：经纪人）
	 */
	public static final String logIn(Browser driver, String name, String pass, boolean relogin, int id) {
		// 获取当前url
		String nowUrl = driver.getCurrentUrl();
		// 如果当前url为空，或者为新开浏览器，启动的版本切换url。则执行登录操作
		if (nowUrl == null || nowUrl.equals("") || nowUrl.equals(versionUrl)) {
//			driver.get(url);    //意义不明 dologin里会打开上海首页的
			dologin(driver, name, pass);
			// 获得经纪人、用户的登录名
			if (id == 1) {
				tycoonLogin(driver);
			} else {
				commLoin(driver);
			}
			// 如果当前url不为空，则跳转到首页，判断是否登录
		} else {
			// 跳转到主页
			nowUrl = driver.getCurrentUrl();
			if(!nowUrl.equals(homeUrl))
			{driver.get(homeUrl);}
			boolean tycoonStatus = driver.check(Public_HeaderFooter.HEADER_BrokerName,5);
			boolean commStatus = driver.check(Public_HeaderFooter.HEADER_UserName,5);
			// 如果当前是经纪人用户
			if (tycoonStatus) {
				Report.writeHTMLLog("当前登录状态", "状态：经纪人登录", "Done", "");
				// 当前为经纪人登录
				int currentId = 1;
				Report.writeHTMLLog("当前登录状态", "经纪人登录", "Done", "");
				// 且不需要从新登录
				if (!relogin) {
					logInSuccName = driver.getText(Public_HeaderFooter.HEADER_BrokerName, "获取用户名");
					Report.writeHTMLLog("current user name", logInSuccName, "Done", "");
					driver.get(nowUrl);
				} else {
					reLogin(driver, currentId, id, name, pass, nowUrl);
				}
			}
			// 普通用户登录
			if (commStatus) {
				int currentId = 0;
				Report.writeHTMLLog("当前登录状态", "状态：普通用户登录", "Done", "");
				// 且不需要从新登录
				if (!relogin) {
					logInSuccName = driver.getText(Public_HeaderFooter.HEADER_UserName, "获取用户名");
					Report.writeHTMLLog("current user name", logInSuccName, "Done", "");
					driver.get(nowUrl);
				} else {
					reLogin(driver, currentId, id, name, pass, nowUrl);
				}
			}
			// 如果未登录
			if ((!commStatus) && (!tycoonStatus)) {
				Report.writeHTMLLog("当前登录状态", "状态：用户未登录", "Done", "");
//				driver.get(url);
				dologin(driver, name, pass);
				// 经纪人用户
				if (id == 1) {
					tycoonLogin(driver);
				} else {
					// 普通用户
					commLoin(driver);
				}
				//登陆后会回到原页面的
				//driver.get(nowUrl);
			}

		}
		return logInSuccName;
	}

	// 用户执行重新登录
	private static void reLogin(Browser driver, int currentId, int id, String name, String pass, String nowUrl) {
		if (id == 1) {
			logOut(driver);
//			driver.get(url);
			dologin(driver, name, pass);
			tycoonLogin(driver);
			if (currentId == id) {
				driver.get(nowUrl);
			}
		} else {
			logOut(driver);
//			driver.get(url);
			dologin(driver, name, pass);
			commLoin(driver);
			if (currentId == id) {
				driver.get(nowUrl);
			}
		}
	}

	// 处理普通用户登录后操作，获取登录后用户名
	private static void commLoin(Browser driver) {
		if (driver.check(Public_HeaderFooter.HEADER_UserName, 20)) {
			try {
				String welcomeinfo = driver.getText(Public_HeaderFooter.HEADER_UserName, "登录成功,获取用户名", 20);
				logInSuccName = PublicProcess.splitString(welcomeinfo, "，");
				Report.writeHTMLLog("login user name", logInSuccName, "Done", "");
			} catch (NullPointerException e) {
				String ps = driver.printScreen();
				Report.writeHTMLLog("login fail", "return username is null", Report.FAIL, ps);
			}
		}
	}

	// 处理经纪人登录后操作，获取登录后用户名
	private static void tycoonLogin(Browser driver) {
		if (driver.check(Public_HeaderFooter.HEADER_BrokerName, 20)) {
			try {
				logInSuccName = driver.getText(Public_HeaderFooter.HEADER_BrokerName, "登录成功,获取用户名", 20);
				Report.writeHTMLLog("login user name", logInSuccName, "Done", "");
			} catch (NullPointerException e) {
				String ps = driver.printScreen();
				Report.writeHTMLLog("login fail", "return username is null", Report.FAIL, ps);
			}
		}
	}

	// 执行退出（包括普通用于以及经纪人用户）
	public static void logOut(Browser driver) {
		// 经纪人用户退出
		driver.get(homeUrl);

		// 判断当前的登录用户类型
		boolean tycoonStatus = driver.check(Public_HeaderFooter.HEADER_BrokerName);
		boolean commStatus = driver.check(Public_HeaderFooter.HEADER_UserName);

		if (tycoonStatus) { // 经纪人退出
			if (driver.check(Init.G_objMap.get("anjuke_use_logout_button"))) {
				driver.click(Init.G_objMap.get("anjuke_use_logout_button"), "经纪人退出登录");
			}
		} else if (commStatus) { // 普通用户退出
			if (driver.check(Init.G_objMap.get("ajk_head_button_logoutComm"))) {
				driver.click(Init.G_objMap.get("ajk_head_button_logoutComm"), "普通用户退出登录");
			}
		}
	}

	// 执行登录操作
	public static void dologin(Browser driver, String name, String pass) {
		driver.get(homeUrl);
		//打开页面后，这里经常找不到登陆的元素--------------------------2012.7.30
		if(driver.check(Init.G_objMap.get("anjuke_click_login")))
		{
			driver.click(Init.G_objMap.get("anjuke_click_login"), "点击登录按钮");
		}
		else
		{
			String ps = driver.printScreen();
			Report.writeHTMLLog("从首页登陆", "又没有找到登陆按钮，尝试刷新页面", Report.DONE,ps);
			driver.refresh();
			driver.click(Init.G_objMap.get("anjuke_click_login"), "点击登录按钮");
		}
		
		driver.type(Init.G_objMap.get("anjuke_login_userName"), name, "用户名");
		driver.type(Init.G_objMap.get("anjuke_login_password"), pass, "密码");
		driver.click(Init.G_objMap.get("anjuke_login_button"), "登录");

	}

	// 注册普通账号
	public static void registerCommonUser(Browser driver) {

		String loginSuccessUserName = ""; // 注册成功显示的用户名
		String userName; // 注册用户名
		String password; // 注册密码
		String email;

		userName = generateUserName();
		email = generateEmail();
		password = "test1234";

		driver.get(homeUrl);
		//以防打开页面后，这里经常找不到登陆的元素--------------------------2012.7.30
		if(driver.check(Init.G_objMap.get("public_link_reg")))
		{
			driver.click(Init.G_objMap.get("public_link_reg"), "点击主页右上角的注册按钮");		
		}
		else
		{
			String ps = driver.printScreen();
			Report.writeHTMLLog("从首页注册", "又没有找到注册按钮，尝试刷新页面", Report.DONE,ps);
			driver.refresh();
			driver.click(Init.G_objMap.get("public_link_reg"), "点击主页右上角的注册按钮");
		}

		driver.type(Init.G_objMap.get("anjuke_register_username"), userName, "输入用户名");
		driver.type(Init.G_objMap.get("anjuke_register_password"), password, "输入密码");
		driver.type(Init.G_objMap.get("anjuke_register_email"), email, "输入邮箱");

		driver.isSelect(Init.G_objMap.get("anjuke_register_service_term_checkbox"), 2);

		driver.click(Init.G_objMap.get("anjuke_register_submit"), "点击注册按钮");

		if (driver.check(Init.G_objMap.get("anjuke_citypage_login_success_username"))) {
			loginSuccessUserName = driver.getText(Init.G_objMap.get("anjuke_citypage_login_success_username"), "获取成功登陆后页面显示的用户名");
			Report.writeHTMLLog("注册普通用户", "抓取注册成功后的用户名" + loginSuccessUserName, Report.PASS, "");
		} else {
			String ps = driver.printScreen();
			Report.writeHTMLLog("注册普通用户", "抓取注册成功后的用户名" + loginSuccessUserName, Report.FAIL, ps);
		}
	}

	// 产生注册用户名
	public static String generateUserName() {

		String userName_prefix; // 注册用户名前缀
		String userName_body; // 注册用户名中间部分
		String userName;

		userName_prefix = "Test";

		// 根据当时的时间（月日时分）来作为userName的体部分
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("MMddHHmm");
		userName_body = df.format(date);

		userName = userName_prefix.concat(userName_body);
		return userName;
	}

	// 根据注册邮箱
	public static String generateEmail() {
		String userName; // 注册用户名
		String email = null; // 注册邮箱
		String email_suffix = null; // 注册邮箱后缀

		email_suffix = "@hotmail.com";
		userName = generateUserName();
		email = userName.concat(email_suffix);
		return email;

	}

	// 房源发布、编辑上传图片操作
	public static void uploadPic(Browser driver, String type) {
		String picMess = null;
		if (type.equals("sale")) {
			picMess = "上传室内图3";
			driver.uploadFile(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxingtu"), TcTools.imgPath("600x600x0.jpg"), picMess);
		} else {
			picMess = "上传室内图多图";
			driver.uploadFile(Broker_PropertynewRentStep.SHINEITU, TcTools.imgPath("600x600.jpg"), picMess);
		}
		exception(driver, picMess);
		// 上传房型图片
		if (type.equals("sale")) {
			picMess = "上传房型图";
			driver.uploadFile(Init.G_objMap.get("anjuke_wangluojingjiren_sale_shineitu"), TcTools.imgPath("600x600.jpg"), picMess);
		} else {
			picMess = "上传房型图";
			driver.uploadFile(Broker_PropertynewRentStep.FANGXINGTU, TcTools.imgPath("800x800.jpg"), picMess);
		}
		exception(driver, picMess);
		// 上传小区图片
		if (type.equals("sale")) {
			picMess = "上传小区图";
			driver.uploadFile(Init.G_objMap.get("anjuke_wangluojingjiren_sale_xiaoqutu"), TcTools.imgPath("800x800.jpg"), picMess);
		} else {
			picMess = "上传小区图";
			driver.uploadFile(Broker_PropertynewRentStep.XIAOQUTU, TcTools.imgPath("600x600x0.jpg"), picMess);
		}
		exception(driver, picMess);

	}

	// 处理图片上传异常
	private static void exception(Browser driver, String picMess) {
		String getText = driver.doAlert("取值");
		if (getText != null) {
			driver.doAlert("取消");
			Report.writeHTMLLog("上传图片", picMess + "失败", Report.FAIL, driver.printScreen());
		} else {
			Report.writeHTMLLog("上传图片", picMess + "成功", Report.PASS, "");
		}
	}

	// 截取字符串
	public static String splitString(String formerMess, String split) {
		String result = null;
		result = formerMess.substring(formerMess.lastIndexOf(split) + 1, formerMess.length()).trim();
		return result;
	}

	// 返回时间戳
	final public static String getNowDateTime(String format) {
		return new SimpleDateFormat(format).format(new Date());

	}

	// 判断当前页面是否能找到对应的locator
	/*
	 * @drive
	 * 
	 * @sTcase 运行的测试用例
	 * 
	 * @sDetails 运行的用例的详细信息
	 * 
	 * @locator 元素定位器
	 */
	public static void checkLocator(Browser driver, String locator, String sTcase, String sDetails) {
		if (driver.check(locator)) {
			Report.writeHTMLLog(sTcase, sDetails, Report.PASS, "");
		} else {
			String ps = driver.printScreen();
			Report.writeHTMLLog(sTcase, sDetails, Report.FAIL, ps);
		}

	}

}
