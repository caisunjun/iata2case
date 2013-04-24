package com.anjuke.ui.page;

/**
 * 安居客 - 多数页面公用的头部、页脚  http://shanghai.anjuke.com/*
 * 大部分页面都是相同的，除经纪人店铺、问答外部专家、奶牛等
 * 
 * @author ccyang
 * */

public class Public_HeaderFooter {
	
	/** 公用头部信息 **/
	
	/** 头部 - 未登陆时 - 登陆链接 */
	public static final String Login = "//li[@class='welcome']/a";
	/** 头部 - 未登陆时 - 注册链接 */
	public static final String Register = "//li[@class='register']/a";
	/** 头部 - 普通用户登录后 - 用户名 */
	public static final String HEADER_UserName = "//a[@class='username']";
	/** 头部 - 普通用户登录后 - 退出 */
	public static final String HEADER_LogOut = "//a[@class='exit']";
	/** 头部 - 经纪人登录后 - 用户名 */
	public static final String HEADER_BrokerName = "//a[@class='username']";
	
	/** 头部 - 经纪人登录 - 我的网络经济人链接 */
	public static final String HEADER_BROKERLINK = "//a[@class='owner']";
	
	/** 头部 - 搜索 - 搜索输入框*/
	public static final String S_BOX = "//*[@id='keyword_apf_id_9']" ;//"id^keyword_apf_id_9";
	/** 头部 - 搜索 - 搜索按钮*/
	public static final String S_BTN = "//*[@id='sbtn2_apf_id_9']";
	

}
