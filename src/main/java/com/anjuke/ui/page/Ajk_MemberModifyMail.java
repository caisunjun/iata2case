package com.anjuke.ui.page;

/**
 * 用户中心 - 修改资料
 * http://my.anjuke.com/member/modify/mail/
 * 
 * @author ccyang
 * */

public class Ajk_MemberModifyMail {
	/** 用户中心 - 修改资料 - 输入登录密码*/
	public static final String UserPassword = "//input[@id='userpwd']";
	/** 用户中心 - 修改资料 - 输入新邮箱 */
	public static final String NewEmail = "//input[@id='useremail']";
	/** 用户中心 - 修改资料 - 点击提交 */
	public static final String SubmitButton = "//*[@id='updateprofile']/table/tbody/tr[4]/td/input";
	/** 用户中心 - 修改资料 - 提示信息 */
	public static final String  SuccessMessage= "//div[@class='modifyemail2']/a/img";
	
}
