package com.anjuke.ui.page;

/**
 * 安居客 - 小区单页 http://shanghai.anjuke.com/community/view/**
 * 
 * @author grayhu
 * */

public class Ajk_CommunityView {
	/** 小区单页 - 小区名（非点击用途）*/
	public static final String COMMTITLE = "id^commtitle";//h2[@id='commtitle']
	
	/** 小区单页 - 主营小区房源模块*/
	public static final String CommMaster = "//div[@id='comm-property-master']";
	
	/** 小区单页 - 价格行情tab*/
	public static final String TrendsTab = "//a[.='价格行情']";
	
	/** 小区单页 - 小区问答 - 问题搜索输入框*/
	public static final String QuestionInput = "//*[@id='question_title']";
	
	/** 小区单页 - 小区问答 - 搜索答案按钮*/
	public static final String SearchQuestion = "//*[@id='submit_btn']";
	
	/** 小区单页 - 小区问答 - 第一条问题标题*/
	public static final String FirstQuestion = "//*[@id='commqalist']/li[2]/a";

}
