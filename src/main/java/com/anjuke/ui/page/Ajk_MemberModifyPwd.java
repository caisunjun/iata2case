package com.anjuke.ui.page;

/**
 * 用户中心 - 修改资料
 * http://my.anjuke.com/member/modify/pwd/
 * 
 * @author ccyang
 * */

public class Ajk_MemberModifyPwd {
	/** 用户中心 - 修改资料 - 旧密码 */
	public static final String OldPassword = "//input[@name='oldpasswd']";
	/** 用户中心 - 修改资料 - 新密码 */
	public static final String NewPassword = "//input[@name='newpasswd']";
	/** 用户中心 - 修改资料 - 再输新密码 */
	public static final String NewPasswordConfirm = "//input[@name='confirmpasswd']";
	/** 用户中心 - 修改资料 - 确认提交新密码 */
	public static final String SubmitButton = "//*[@id='updateprofile']/table/tbody/tr[4]/td/input[2]";
	
}
