package com.anjuke.ui.page;

/**
 * 安居客 - 小区单页 http://shanghai.anjuke.com/community/view/**
 * 
 * @author grayhu
 * */

public class Ajk_CommunityView {
	/** 小区单页 - 小区名（非点击用途）*/
	public static final String COMMTITLE = "id^commtitle";//h2[@id='commtitle']
	
	/** 小区单页 - 顶部切换tab - 小区概况*/
	public static final String navtabView = "//a[.='小区概况']";
	/** 小区单页 - 顶部切换tab - 价格行情*/
	public static final String navtabTrends = "//a[.='价格行情']";
	/** 小区单页 - 顶部切换tab - 房型图*/
	public static final String navtabPhotosA = "//a[.='房型图']";
	/** 小区单页 - 顶部切换tab - 实景图*/
	public static final String navtabPhotosB = "//a[.='实景图']";
	/** 小区单页 - 顶部切换tab - 生活配套*/
	public static final String navtabRound = "//a[.='生活配套']";
	/** 小区单页 - 顶部切换tab - 二手房*/
	public static final String navtabSale = "//a[contains(.,'二手房(')]";
	/** 小区单页 - 顶部切换tab - 租房*/
	public static final String navtabRent = "//a[contains(.,'租房(')]";
	/** 小区单页 - 顶部切换tab - 小区问答*/
	public static final String navtabQa = "//a[.='小区问答']";
	
	/** 小区单页 - 主营小区房源模块*/
	public static final String CommMaster = "//div[@id='comm-property-master']";
	/** 小区单页 - 小区问答 - 问题搜索输入框*/
	public static final String QuestionInput = "//*[@id='question_title']";
	
	/** 小区单页 - 小区问答 - 搜索答案按钮*/
	public static final String SearchQuestion = "//*[@id='submit_btn']";
	
	/** 小区单页 - 小区问答 - 第一条问题标题*/
	public static final String FirstQuestion = "//*[@id='commqalist']/li[2]/a";

}
