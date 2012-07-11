package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.bean.AnjukeCommonUserInfo;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;

/**
 * 该用例主要用来做安居客普通用户修改个人资料，步骤简介如下：
 * 1.进入Anjuke网站，并以当前时间注册一个普通用户账号
 * 2.进入普通用户个人中心页面
 * 3.依次修改普通用户个人资料.头像.个人签名.个人密码以及邮箱 
 * @UpdateAuthor Williamhu
 * @last updatetime 2012/05/08
 */


public class AnjukeCommonUserModifyInfo {
	private Browser bs = null;
	private AnjukeCommonUserInfo anjukeCommonUserInfo= new  AnjukeCommonUserInfo();

	@BeforeMethod
	public void stratUp(){
		bs = FactoryBrowser.factoryBrowser();

	}

	@AfterMethod
	public  void tearDown(){
		bs.quit();
		bs = null;
	}

	//(timeOut = 210000)
	@Test
	public void testStart() throws InterruptedException{

		//Report.setTCNameLog("修改普通用户个人资料-- AnjukeCommonUserModifyInfo --Hendry_huang");

		//修改普通用户个人资料
		modifyCommonUserInfo();  


		//Report.setTCNameLog("修改普通用户头像-- AnjukeCommonUserModifyIcon --Hendry_huang");	

		//*修改普通用户头像
		modifyCommonUserIcon();   


		//Report.setTCNameLog("修改普通用户个人签名-- AnjukeCommonUserModifySignature --Hendry_huang");	

		//*修改普通用户头像

		modifyCommonUserSignature();   


		//Report.setTCNameLog("修改普通用户个人密码-- AnjukeCommonUserModifyPassword --Hendry_huang");	

		//*修改普通用户登录密码
		modifyCommonUserPassword();  
		
		
		//Report.setTCNameLog("修改普通用户个人邮箱-- AnjukeCommonUserModifyEmail --Hendry_huang");	

		//*修改普通用户邮箱
		modifyCommonUseremail(); 
	}


	/*
	 * 修改普通用户个人资料
	 */
	private void modifyCommonUserInfo(){	 

		//删除当前浏览器所有Cookie
		bs.deleteAllCookies();
		
		//注册普通账户
		PublicProcess.registerCommonUser(bs);

		//点击右上角用户名链接
		bs.check(Init.G_objMap.get("anjuke_citypage_login_success_username"));
		bs.click(Init.G_objMap.get("anjuke_citypage_login_success_username"), "点击主页右上角用户名链接");		

		//点击修改资料链接
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_link"), "点击修改资料链接");


		//输入真实用户名
		bs.type(Init.G_objMap.get("anjuke_commonuser_info_modification_realname"), "William", "输入真实用户名");		
		anjukeCommonUserInfo.setRealName("William");

		//选中公开真实姓名
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_realname_ispub"),"选中公开真实姓名");

		//选择性别
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_gender"),"选中性别男");
		anjukeCommonUserInfo.setGender("男");


		//输入手机号
		bs.type(Init.G_objMap.get("anjuke_commonuser_info_modification_phone"), "13666666666", "输入手机号");
		anjukeCommonUserInfo.setMobileNubmer("13666666666");

		//选中公开手机号
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_mobileNo_ispub"),"选中公开手机号");


		//输入区号
		bs.type(Init.G_objMap.get("anjuke_commonuser_info_modification_provincecode"), "021", "输入区号");
		anjukeCommonUserInfo.setPhone_provinceCode("021");

		//输入座机号
		bs.type(Init.G_objMap.get("anjuke_commonuser_info_modification_phoneNo"),"66666666","输入座机号");
		anjukeCommonUserInfo.setPhone_number("66666666");

		//选中公开座机号
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_phoneNo_ispub"),"选中公开座机号");

		//输入城市名
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_city"), "选择上海城市");
		anjukeCommonUserInfo.setAddress_city("上海");

		//输入所在小区
		bs.type(Init.G_objMap.get("anjuke_commonuser_info_modification_community"), "金桥", "输入所在区");			


		bs.switchToIframe(Init.G_objMap.get("anjuke_commonuser_info_modification_community_ifra"), "选择小区");		

		//选中小区弹窗口中的第一个小区
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_first_communit"), "选择第一个小区");	    
		bs.exitFrame();
		anjukeCommonUserInfo.setAddress_place("凯鑫苑");

		bs.check(Init.G_objMap.get("anjuke_commonuser_info_modification_submit_button"));
		//点击提交按钮
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_submit_button"),"点击提交按钮");	    

		
		
		//检查修改成功标志是否预期显示
		if(bs.check(Init.G_objMap.get("anjuke_commonuser_info_modification_succPrompt"))){
			Report.writeHTMLLog("普通用户更改个人信息", "资料修改成功标志显示出来", Report.PASS, "");
		}else{
			String  ps = bs.printScreen();
			Report.writeHTMLLog("普通用户更改个人信息", "资料修改成功标志未显示出来,测试失败", Report.FAIL, ps);
		}


		//比较修改前后的真实姓名
		String realName = anjukeCommonUserInfo.getRealName();
		if(realName.equals(bs.getAttribute(Init.G_objMap.get("anjuke_commonuser_info_modification_realname"), "value"))){
			Report.writeHTMLLog("普通用户更改个人信息", "个人真实姓名修改成功", Report.PASS, "");
		}else{
			String ps = bs.printScreen();
			Report.writeHTMLLog("普通用户更改个人信息", "个人真实姓名修改失败", Report.FAIL, ps);
		}


		//比较修改前后的真实性别信息
		String gender = anjukeCommonUserInfo.getGender();
		int genderID;                //单选框中选中选项的序列号对于的值
		int indexOfSelectedOption;  //单选框中选中选项的序列号
		String strGender;	        //选中选项值

		indexOfSelectedOption = bs.selectedOptionIndex(Init.G_objMap.get("anjuke_commonuser_info_modification_gender_option"), 2);

		genderID = Integer.parseInt(bs.getAttribute(Init.G_objMap.get("anjuke_commonuser_info_modification_gender_option")+ "[" +indexOfSelectedOption+ "]", "value"));

		//根据返回的性别ID确定性别
		if(genderID == 1){
			strGender =  "男";
		}else if(genderID == 2){
			strGender = "女";
		}else{
			strGender = "保密";
		}
		if(gender.equals(strGender)){
			Report.writeHTMLLog("普通用户更改个人信息", "个人信息中的性别信息修改成功", Report.PASS, "");
		}else{
			String ps = bs.printScreen();
			Report.writeHTMLLog("普通用户更改个人信息", "个人信息中的性别信息修改失败", Report.FAIL, ps);
		}

		//比较修改前后的手机号信息
		String  mobile_Number= anjukeCommonUserInfo.getMobileNumber();
		if(mobile_Number.equals(bs.getAttribute(Init.G_objMap.get("anjuke_commonuser_info_modification_phone"), "value"))){
			Report.writeHTMLLog("普通用户更改个人信息", "个人信息中的手机号码信息修改成功", Report.PASS, "");
		}else{
			String ps = bs.printScreen();
			Report.writeHTMLLog("普通用户更改个人信息", "个人信息中的手机号码信息修改失败", Report.FAIL, ps);
		}

		//比较修改前后的座机区号信息
		String  phone_ProivnceCode = anjukeCommonUserInfo.getPhone_provinceCode();
		if(phone_ProivnceCode.equals(bs.getAttribute(Init.G_objMap.get("anjuke_commonuser_info_modification_provincecode"), "value"))){
			Report.writeHTMLLog("普通用户更改个人信息", "个人信息中的座机区号信息修改成功", Report.PASS, "");
		}else{
			String ps = bs.printScreen();
			Report.writeHTMLLog("普通用户更改个人信息", "个人信息中的座机区号信息修改失败", Report.FAIL, ps);
		}

		//比较修改前后的座机号信息
		String  phone_Number = anjukeCommonUserInfo.getPhone_number();
		if(phone_Number.equals(bs.getAttribute(Init.G_objMap.get("anjuke_commonuser_info_modification_phoneNo"), "value"))){
			Report.writeHTMLLog("普通用户更改个人信息", "个人信息中的座机号码信息修改成功", Report.PASS, "");
		}else{
			String ps = bs.printScreen();
			Report.writeHTMLLog("普通用户更改个人信息", "个人信息中的座机号码信息修改失败", Report.FAIL, ps);
		}

		//获取选中城市信息
		String tempText =bs.getText(Init.G_objMap.get("anjuke_commonuser_info_modification_city_dropdownl"), "获取城市列表");	       
		String  city = anjukeCommonUserInfo.getAddress_city();
		if(city.equals(tempText)){
			Report.writeHTMLLog("普通用户更改个人信息", "个人信息中的城市信息修改成功", Report.PASS, "");
		}else{
			String ps = bs.printScreen();
			Report.writeHTMLLog("普通用户更改个人信息", "个人信息中的城市信息修改失败", Report.FAIL, ps);
		}

		//比较修改前后的选中小区信息 
		String  community_name = anjukeCommonUserInfo.getAddress_place();
		if(community_name.equals(bs.getAttribute(Init.G_objMap.get("anjuke_commonuser_info_modification_community"), "value"))){
			Report.writeHTMLLog("普通用户更改个人信息", "个人信息中的小区信息修改成功", Report.PASS, "");
		}else{
			String ps = bs.printScreen();
			Report.writeHTMLLog("普通用户更改个人信息", "个人信息中的小区信息修改失败", Report.FAIL, ps);
		}	


	}

	/*
	 * 修改普通用户个人头像
	 */
	private void modifyCommonUserIcon(){

		//点击修改头像标签栏
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_icon_link"),"点击修改头像标签栏");

		//上传尺寸正确的头像
		String picPath = Init.G_config.get("picPath") + "Correct Icon.gif";
		bs.uploadFile(Init.G_objMap.get("anjuke_commonuser_info_modification_icon_upload_fi"), picPath, "选中一张头像尺寸不符的图片上传");

		//检查正确提示是否显示正确
		if(bs.getText(Init.G_objMap.get("anjuke_commonuser_info_modification_icon_prompt"), "获取上传成功提示").contains("按钮保存您的设置")){
			Report.writeHTMLLog("修改头像", "上传尺寸相符的头像提示正确", Report.PASS, "");
		}else{
			String ps = bs.printScreen();
			Report.writeHTMLLog("修改头像", "上传尺寸相符的头像提示不正确", Report.FAIL, ps);
		}

		//点击保存图像按钮
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_icon_save_butt"), "点击提交按钮");

		//检查修改成功标志是否预期显示
		if(bs.check(Init.G_objMap.get("anjuke_commonuser_info_modification_succPrompt"))){
			Report.writeHTMLLog("普通用户更改个人信息", "资料修改成功标志显示出来", Report.PASS, "");
		}else{
			String  ps = bs.printScreen();
			Report.writeHTMLLog("普通用户更改个人信息", "资料修改成功标志未显示出来,测试失败", Report.FAIL, ps);
		}
	}

	
	/*
	 * 修改普通用户个人签名
	 */
	private void modifyCommonUserSignature(){

		//点击修改个人签名标签栏
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_signature_link"),"点击修改个人签名标签栏");

		//修改个人签名
		bs.type(Init.G_objMap.get("anjuke_commonuser_info_modification_signature_text"),"测试勿扰，请勿联系!","输入个人签名");

		//点击确定保存按钮
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_signature_save"),"点击提交按钮");		

		
		//检查签名修改成功标志是否显示
		if(bs.check(Init.G_objMap.get("anjuke_commonuser_info_modification_succPrompt"))){
			Report.writeHTMLLog("普通用户更改个人签名", "个人签名修改成功标志显示出来", Report.PASS, "");
		}else{
			String  ps = bs.printScreen();
			Report.writeHTMLLog("普通用户更改个人签名", "个人修改成功标志未显示出来,测试失败", Report.FAIL, ps);
		}		 

	}

	/*
	 * 修改普通用户登录密码
	 */
	private void modifyCommonUserPassword(){
		
		String userName;
		String oldPassword;
		String newPassword;
				
		oldPassword = "test1234";
		newPassword = "test567";
		
		//点击修改普通用户登录密码标签栏
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_password_link"),"点击修改个人登录密码");
		
		//获取登录用户名
		String tempUserName = bs.getText(Init.G_objMap.get("anjuke_index_login_commonuser_succ"), "获取包含用户名在内的欢迎信息");
		
			
		userName = PublicProcess.splitString(tempUserName, "，");
		
	    bs.check(Init.G_objMap.get("anjuke_commonuser_info_modification_pass_old"));
		
		bs.type(Init.G_objMap.get("anjuke_commonuser_info_modification_pass_old"), oldPassword, "输入旧登录密码");		
		
		
		//输入新密码
		bs.type(Init.G_objMap.get("anjuke_commonuser_info_modification_pass_new"), newPassword, "输入新登录密码");
		
		//确认输入新密码
		bs.type(Init.G_objMap.get("anjuke_commonuser_info_modification_pass_confirm"), newPassword, "输入新登录密码");	
	
		
		//点击确定保存按钮
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_pass_save_btn"),"点击提交按钮");
		
		//检查密码修改成功标志是否显示
		if(bs.check(Init.G_objMap.get("anjuke_commonuser_info_modification_succPrompt"))){
			Report.writeHTMLLog("普通用户更改个人签名", "登录密码修改成功标志显示出来", Report.PASS, "");
		}else{
			String  ps = bs.printScreen();
			Report.writeHTMLLog("普通用户更改个人签名", "登录密码成功标志未显示出来,测试失败", Report.FAIL, ps);
		}		 
		
//		//登出账户
		PublicProcess.logOut(bs);
		
//		//点击登录链接
		if(bs.check("//a[contains(text(),'请登录')]")){
		bs.click("//a[contains(text(),'请登录')]", "点击登录链接");
		}		

		
		//检查是否可以用新的密码登录网站
		PublicProcess.dologin(bs, userName, newPassword);		
	}
	
	/*
	 * 修改普通用户邮箱
	 */
	private void modifyCommonUseremail(){
	
		String newEmail;    //新邮箱
		String password;    //登录密码
		
	    password = "test567";
		
	    //产生一个新的邮箱
	    newEmail = PublicProcess.generateEmail();
	    
		//点击右上角用户名链接
		bs.click(Init.G_objMap.get("anjuke_citypage_login_success_username"), "点击主页右上角用户名链接");	
		
		//点击修改资料链接
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_link"), "点击修改资料链接");

		//点击修改邮箱标签栏
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_email_link"),"点击修改个人登录密码");
		
		
		//输入登录密码		
		bs.type(Init.G_objMap.get("anjuke_commonuser_info_modification_email_pass"), password, "输入登录密码");
		
		
		//输入新的邮箱		
		bs.type(Init.G_objMap.get("anjuke_commonuser_info_modification_email_newemail"),newEmail,"输入新的邮箱");	
				
		
		//点击确定保存按钮
		bs.click(Init.G_objMap.get("anjuke_commonuser_info_modification_email_save_btn"),"点击提交按钮");
		
		//判断是否邮箱修改成功
		if(bs.check(Init.G_objMap.get("anjuke_commonuser_info_modification_email_verify_i"))){
			Report.writeHTMLLog("修改邮箱", "邮箱修改成功", Report.PASS, "");
		}else{
			String ps = bs.printScreen();
			Report.writeHTMLLog("修改邮箱", "邮箱修改失败", Report.FAIL, ps);
		}			
	}	
}

