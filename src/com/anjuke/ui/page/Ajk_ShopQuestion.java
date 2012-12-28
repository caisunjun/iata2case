package com.anjuke.ui.page;

/**
 * 安居客 - 经纪人店铺问答页 http://shanghai.anjuke.com/shop/question/913870
 * 
 * @author jchen
 * */

public class Ajk_ShopQuestion {
	
	/** 经纪人店铺问答页 - 目前还没有回答过问题 */
	public static final String NO_QUESTION = "//*[@class='no_answer']";
	
	/** 经纪人店铺问答页 - 回答数 */
	public static final String QUESTION = "//*[@class='borker_qainfo']/table/tbody/tr[2]/td[1]";
	
	/** 经纪人店铺问答页 - 第一个问题的回答时间 */
	public static final String AnswerTime = "//div[@class='qa_list']/ul/li[2]/table/tbody/tr/td[2]";
	

	
}
