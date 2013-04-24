package com.anjuke.ui.page;

/**
 * 普通用户 - 我的问答  http://my.anjuke.com/member/ask/*****\/****
 * 
 * @author grayhu
 * */

public class Ajk_MemberAsk {
	
	/** 我的问答 - 经验值 */
	public static final String EXP = "//ul[@class='user_details']/li[1]/p[2]";
	/** 我的问答 - 金币值 */
	public static final String GOLD = "//ul[@class='user_details']/li[3]/p[2]";
	/** 我的问答 - 提问数 */
	public static final String ASK = "//ul[@class='user_details']/li[4]/p[2]";
	/** 我的问答 - 回答数 */
	public static final String ANSWER = "//ul[@class='user_details']/li[5]/p[2]";
	/** 我的问答 - 采纳率 */
	public static final String ADOPT = "//ul[@class='user_details']/li[6]/p[2]";
	
	/** 我的问答 - 我提出的问题 */
	public static final String MY_QUESTION = "id^myquestion_tab";
	/** 我的问答 - 我提出的问题 - 提问日期1 */
	public static final String Q_DATE = "//ul[@class='question_list']/li[1]/div[1]";
	/** 我的问答 - 我提出的问题 - 提问问题正文1 */
	public static final String Q_DETAIL = "//ul[@class='question_list']/li[1]/div[2]/a";
	/** 我的问答 - 我提出的问题 - 问题回复数1 */
	public static final String Q_REPLY = "//ul[@class='question_list']/li[1]/div[3]/span";
	
	/** 我的问答 - 我回答的问题 */
	public static final String MY_ANSWER = "id^myanswer_tab";
	/** 我的问答 - 我提出的问题 - 提问日期1 */
	public static final String A_DATE = "//ul[@class='question_list']/li[1]/div[1]";
	/** 我的问答 - 我提出的问题 - 提问问题正文1 */
	public static final String A_DETAIL = "//ul[@class='question_list']/li[1]/div[2]/a";
	/** 我的问答 - 我提出的问题 - 问题回复数1 */
	public static final String A_REPLY = "//ul[@class='question_list']/li[1]/div[3]/span";
	

}
