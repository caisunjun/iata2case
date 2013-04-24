package com.anjuke.ui.page;

/**
 * 用户中心 - 修改资料
 * http://my.anjuke.com/member/modify/info/
 * 
 * @author ccyang
 * */

public class Ajk_MemberModifyInfo {
	/** 用户中心 - 修改资料 - 修改头像tab */
	public static final String HeadpicTab = "//div[@class='main-tab']/a[2]";
	/** 用户中心 - 修改资料 - 修改密码tab */
	public static final String PasswordTab = "//div[@class='main-tab']/a[3]";
	/** 用户中心 - 修改资料 - 修改邮箱tab */
	public static final String EmailTab = "//div[@class='main-tab']/a[4]";
	/** 用户中心 - 修改资料 - 真实姓名 */
	public static final String RealName = "//input[@name='f_realname']";
	/** 用户中心 - 修改资料 - 性别 */
	public static final String Gender = "//input[@name='f_gender']";
	/** 用户中心 - 修改资料 - 性别可选项 */
	public static final String GenderOption = "//td/input[@name='f_gender']";
	/** 用户中心 - 修改资料 - 手机号 */
	public static final String CellPhone = "//input[@name='f_cellphone']";
	/** 用户中心 - 修改资料 - 电话-区号 */
	public static final String ZoneNumber = "//input[@name='f_phone1']";
	/** 用户中心 - 修改资料 - 电话-号码 */
	public static final String PhoneNumber = "//input[@name='f_phone2']";
	/** 用户中心 - 修改资料 - 住址-选择城市 */
	public static final String AddressCity = "//select[@name='slt_cityid']/option[2]";
	/** 用户中心 - 修改资料 - 住址-小区 */
	public static final String AddressComm = "//input[@name='txtCommunityName']";
	/** 用户中心 - 修改资料 - 住址-小区下拉框 */
	public static final String AddressCommDropList = "//iframe[@name='listComm']";
	/** 用户中心 - 修改资料 - 住址-小区下拉框的第一个小区 */
	public static final String AddressCommDropListFirst = "//div[@id='showCommNameBox']/div[2]/ul/li[1]/span[1]";
	/** 用户中心 - 修改资料 - 提交更改我的设置 */
	public static final String SubmitButton = "//*[@id='updateprofile']/table/tbody/tr[8]/td/input[2]";
	/** 用户中心 - 修改资料 - 修改成功提示 */
	public static final String SuccessMessage = "//div[@class='tishi_new']";
	
}
