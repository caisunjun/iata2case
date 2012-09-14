package com.anjuke.ui.page;

/**
 * 安居客 - 多数页面公用的头部、页脚  http://shanghai.anjuke.com/*
 * 大部分页面都是相同的，除经纪人店铺、问答外部专家、奶牛等
 * 
 * @author ccyang
 * */

public class Public_HeaderFooter {
	
	/** 公用头部信息 **/
	
	/** 头部 - 普通用户登录后 - 用户名 */
	public static final String HEADER_UserName = "//li[@class='username_boxer']/a[@class='username']";
	/** 头部 - 经纪人登录后 - 用户名 */
	public static final String HEADER_BrokerName = "//ul[@id='top_broker_panel']/li[4]/a";
	
	/** 头部 - 经纪人登录 - 我的网络经济人链接 */
	public static final String HEADER_BROKERLINK = "//li[@class='broker_boxer']/a";
	
	/** 头部 - 搜索 - 搜索输入框*/
	public static final String S_BOX = "//input[@id='keyword_apf_id_9']" ;//"id^keyword_apf_id_9";
	/** 头部 - 搜索 - 搜索按钮*/
	public static final String S_BTN = "id^sbtn2_apf_id_9";
	

}
