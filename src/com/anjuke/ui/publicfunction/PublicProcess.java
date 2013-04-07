package com.anjuke.ui.publicfunction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.anjuke.ui.page.Broker_PropertynewRentStep;
import com.anjuke.ui.page.Broker_PropertynewSaleStep;
import com.anjuke.ui.page.Login_My;
import com.anjuke.ui.page.Public_HeaderFooter;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;
import com.anjukeinc.iata.ui.util.GetRandom;
import com.anjukeinc.iata.ui.util.TcTools;

public class PublicProcess {
//	private static String url = "http://my.anjuke.com/my/login?history=aHR0cDovL3NoYW5naGFpLmFuanVrZS5jb20v";
	private static String homeUrl = "http://shanghai.anjuke.com/";

	// 执行登录操作
	public static String dologin(Browser driver, String name, String pass) {
		driver.get(homeUrl);
		//打开页面后，这里经常找不到登陆的元素--------------------------2012.7.30
		if(driver.check(Public_HeaderFooter.Login,5))
		{
			driver.click(Public_HeaderFooter.Login, "点击登录按钮");
		}
		else
		{
			String ps = driver.printScreen();
			Report.writeHTMLLog("从首页登陆", "又没有找到登陆按钮，尝试刷新页面", Report.DONE,ps);
			driver.refresh();
			driver.click(Public_HeaderFooter.Login, "点击登录按钮");
		}
		
		driver.type(Login_My.UserName, name, "用户名");
		driver.type(Login_My.Password, pass, "密码");
		driver.click(Login_My.LoginSubmit, "登录");

		return driver.getText(Public_HeaderFooter.HEADER_UserName, "获取用户名");
	}
	
	// 执行退出（包括普通用户以及经纪人用户）
	public static void logOut(Browser driver) {
		// 退出已通用
		driver.get(homeUrl);

		if (driver.check(Public_HeaderFooter.HEADER_LogOut)) {
			driver.click(Public_HeaderFooter.HEADER_LogOut, "普通用户退出登录");
		}
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
			driver.click(Public_HeaderFooter.Register, "点击主页右上角的注册按钮");
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

		email_suffix = "@mail.com";
		userName = generateUserName();
		email = userName.concat(email_suffix);
		return email;

	}

	// 房源发布、编辑单张上传图片操作
	public static void uploadPic(Browser driver, String type) {
		String picMess = null;
		int picCount = 0;
		if (type.equals("sale")) {
			picMess = "上传室内图";
			picCount = driver.getElementCount(Broker_PropertynewSaleStep.PicCount);
			driver.uploadFile(Broker_PropertynewSaleStep.SHINEISINGLE, TcTools.imgPath("600x600.jpg"), picMess ,30);
		} else {
			picMess = "上传室内图";
			driver.uploadFile(Broker_PropertynewRentStep.SHINEITU, TcTools.imgPath("600x600.jpg"), picMess);
		}
		exception(driver, picMess ,picCount);
		// 上传房型图片
		if (type.equals("sale")) {
			picMess = "上传房型图";
			picCount = driver.getElementCount(Broker_PropertynewSaleStep.PicCount);
			driver.uploadFile(Broker_PropertynewSaleStep.FANGXINGSINGLE, TcTools.imgPath("600x600x0.jpg"), picMess);
		} else {
			picMess = "上传房型图";
			driver.uploadFile(Broker_PropertynewRentStep.FANGXINGTU, TcTools.imgPath("800x800.jpg"), picMess);
		}
		exception(driver, picMess ,picCount);
		// 上传小区图片
		if (type.equals("sale")) {
			picMess = "上传小区图";
			picCount = driver.getElementCount(Broker_PropertynewSaleStep.PicCount);
			driver.uploadFile(Broker_PropertynewSaleStep.XIAOQUSINGLE, TcTools.imgPath("800x800.jpg"), picMess);
		} else {
			picMess = "上传小区图";
			driver.uploadFile(Broker_PropertynewRentStep.XIAOQUTU, TcTools.imgPath("600x600x0.jpg"), picMess);
		}
		exception(driver, picMess ,picCount);

	}
	
	// 房源发布、编辑多张上传图片操作
	public static void uploadPicMulti(Browser driver, String type) {
		String picMess = null;
		int picCount = 0;
		if (type.equals("sale")) {
			picCount = driver.getElementCount(Broker_PropertynewSaleStep.PicCount,5);
			driver.click(Broker_PropertynewSaleStep.SHINEIMulti, "点击室内图多图上传");
			picMess = "上传室内图";
			try {
				Thread.sleep(2000);
				Runtime.getRuntime().exec(".\\tools\\uploadShinei.exe").waitFor();
				Thread.sleep(3000);
				} catch (Exception e) {
				e.printStackTrace();
				}
			
		} else {
//			picMess = "上传室内图";
//			driver.uploadFile(Broker_PropertynewRentStep.SHINEITU, TcTools.imgPath("600x600.jpg"), picMess);
		}
		exception(driver, picMess ,picCount);
		// 上传房型图片
		if (type.equals("sale")) {
			picCount = driver.getElementCount(Broker_PropertynewSaleStep.PicCount,5);
			driver.click(Broker_PropertynewSaleStep.FANGXINGMulti, "点击房型图多图上传");
			picMess = "上传房型图";
			try {
				Thread.sleep(2000);
				Runtime.getRuntime().exec(".\\tools\\uploadHuxing.exe").waitFor();
				Thread.sleep(3000);
				} catch (Exception e) {
				e.printStackTrace();
				}

		} else {
//			picMess = "上传房型图";
//			driver.uploadFile(Broker_PropertynewRentStep.FANGXINGTU, TcTools.imgPath("800x800.jpg"), picMess);
		}
		exception(driver, picMess ,picCount);
		// 上传小区图片
		if (type.equals("sale")) {
			picCount = driver.getElementCount(Broker_PropertynewSaleStep.PicCount,5);
			driver.click(Broker_PropertynewSaleStep.XIAOQUMulti, "点击小区图多图上传");
			picMess = "上传小区图";
			try {
				Thread.sleep(2000);
				Runtime.getRuntime().exec(".\\tools\\uploadXiaoqu.exe").waitFor();
				Thread.sleep(3000);
				} catch (Exception e) {
				e.printStackTrace();
				}
		} else {
//			picMess = "上传小区图";
//			driver.uploadFile(Broker_PropertynewRentStep.XIAOQUTU, TcTools.imgPath("600x600x0.jpg"), picMess);
		}
		exception(driver, picMess ,picCount);

	}

	// 房源发布、编辑 单张、多张混合上传图片操作
	public static void uploadPicMix(Browser driver, String type) {
		String picMess = null;
		int picCount = 0;
		if (type.equals("sale")) {
			picMess = "上传室内图";
			picCount = driver.getElementCount(Broker_PropertynewSaleStep.PicCount);
			
//			未完成js
//			driver.runScript("document.getElementById(\"multi_upload_apf_id_5\").style.display = \"block\";");
			
			driver.uploadFile(Broker_PropertynewSaleStep.SHINEISINGLE, TcTools.imgPath("600x600.jpg"), picMess ,30);
		} else {
			picMess = "上传室内图";
			driver.uploadFile(Broker_PropertynewRentStep.SHINEITU, TcTools.imgPath("600x600.jpg"), picMess);
		}
		exception(driver, picMess ,picCount);
		// 上传房型图片
		if (type.equals("sale")) {
			picCount = driver.getElementCount(Broker_PropertynewSaleStep.PicCount,5);
			driver.click(Broker_PropertynewSaleStep.FANGXINGMulti, "点击房型图多图上传");
			picMess = "上传房型图";
			try {
				Thread.sleep(2000);
				Runtime.getRuntime().exec(".\\tools\\uploadHuxing.exe").waitFor();
				Thread.sleep(3000);
				} catch (Exception e) {
				e.printStackTrace();
				}

		} else {
//			picMess = "上传房型图";
//			driver.uploadFile(Broker_PropertynewRentStep.FANGXINGTU, TcTools.imgPath("800x800.jpg"), picMess);
		}
		exception(driver, picMess ,picCount);
		//firefox多图上传之后会失焦
		driver.switchWindo(driver.getWindowHandles());
		
		// 上传小区图片
		if (type.equals("sale")) {
			picCount = driver.getElementCount(Broker_PropertynewSaleStep.PicCount,5);
			driver.click(Broker_PropertynewSaleStep.XIAOQUMulti, "点击小区图多图上传");
			picMess = "上传小区图";
			try {
				Thread.sleep(2000);
				Runtime.getRuntime().exec(".\\tools\\uploadXiaoqu.exe").waitFor();
				Thread.sleep(3000);
				} catch (Exception e) {
				e.printStackTrace();
				}
		} else {
//			picMess = "上传小区图";
//			driver.uploadFile(Broker_PropertynewRentStep.XIAOQUTU, TcTools.imgPath("600x600x0.jpg"), picMess);
		}
		exception(driver, picMess ,picCount);
		//firefox多图上传之后会失焦
		driver.switchWindo(driver.getWindowHandles());
	}

	
	// 处理图片上传异常
	private static void exception(Browser driver, String picMess ,int picCount) {
		String getText = driver.doAlert("取值");
		int picCountNow = driver.getElementCount(Broker_PropertynewSaleStep.PicCount,5);
		System.out.println(picCountNow);
		System.out.println(picCount);
		if (getText != null) {
			driver.doAlert("取消");
			Report.writeHTMLLog("上传图片", picMess + "失败", Report.FAIL, driver.printScreen());
		} else if(picCountNow == picCount+1){
			Report.writeHTMLLog("上传图片", picMess + "成功", Report.PASS, "");
		}
		else{
			Report.writeHTMLLog("上传图片", picMess + "失败，房源数量不匹配，上传前："+picCount+"&上传后："+picCountNow, Report.FAIL, driver.printScreen());
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
	
	/**
     * 根据提供的文件路径、文件名保存对应的文件内容
     * @param filePath 保存文件路径
     * @param content 需要保存的文件内容
     * @param fileName 需要保存的文件名称
     */
    final public static void saveFile(String filePath, String fileName, String content) {
        FileOutputStream outFile = null;
        OutputStreamWriter osw = null;

        filePathExists(filePath);

        try {
            outFile = new FileOutputStream(filePath + fileName);
            osw = new OutputStreamWriter(outFile, "UTF-8");
            osw.write(content);
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                osw.close();
                outFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    final public static void filePathExists(String path) {
        File f = null;

        f = new File(path);
        if (f.exists() == false) {
            f.mkdirs();// 没有存在就在建立一个
        }
    }
	/**
	 * 把cookie内容保存到文件中
	 * 生成的文件地址见config中的cookiePath
	 */
    final public static void saveCookieToFile(Browser driver){
    	driver.refresh();
    	String cookieContent = driver.getCookies("","");
    	saveFile(Init.G_config.get("cookiePath"), "cookie"+getTraceInfo()+".txt", cookieContent);
    }
    
    
    public static ArrayList<String> getTraceInfo(){
        StackTraceElement[] stacks = new Throwable().getStackTrace();   
        int stacksLen = stacks.length;   
       
        ArrayList<String> caseNameList = new ArrayList<String>();
        for(int i=1;i<stacksLen;i++){
        	if(stacks[i].getMethodName().toString().equals("invoke0")){
        		int index = stacks[i-1].getClassName().toString().lastIndexOf(".");
        		String className = stacks[i-1].getClassName().toString().substring(index+1);             
        		caseNameList.add(className);
        		caseNameList.add(stacks[i-1].getMethodName().toString());
        	}
        }
        return caseNameList;
	}
    
/**随机一个代表城市的city域名
 * getRandomCityFromConfig会调用getConfigInfo
 * @return 随机返回config里城市列表里一个城市的city域名
 */
    public static String getRandomCityFromConfig(){
    	int randomNum = GetRandom.getrandom(30);
    	int now = 0;
		// 随机城市
    	LinkedHashMap<String, String> cityMap = getConfigInfo("anjukeCityInfo");
		Iterator<Entry<String, String>> cityInfoIter = cityMap.entrySet().iterator();
		String cityVal = null;
		while (cityInfoIter.hasNext() && now < randomNum) {
			Map.Entry<String, String> cityEntry = cityInfoIter.next();
			cityVal = cityEntry.getValue();
			now = now + 1;
		}
		return cityVal;
    }
    //按config里的顺序取出城市列表
    private static LinkedHashMap<String, String> getConfigInfo(String configKey) {
        String dataInfo = "";
        String[] data;
        String[] singleData;
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        dataInfo = Init.G_config.get(configKey);
        data = dataInfo.split(",");
        for (String dataStr : data) {
            singleData = dataStr.split("-");
            map.put(singleData[0], singleData[1]);
        }
        return map;
    }

}
