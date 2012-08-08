package com.anjuke.ui.page;

/**
 * 安居客 - 问答详情页 http://www.anjuke.com/ask/view/*****
 * 
 * @author agneszhang
 * */

public class Ajk_AskView {
	/** 问答单页 - 我来帮他回答按钮 */
	public static final String IANSWER = "id^addanswer";
	/** 问答单页 - 回答输入框 */
	public static final String INPUTANSWER = "id^answer_content";
	/** 问答单页 - 回答提交按钮 */
	public static final String SUBMITANSWER = "id^submit_btn_answer";
	/** 问答单页 - 回答框收起按钮 */
	public static final String CANCELANSWER = "id^cancel_answer";
	/** 问答单页 - 回答列表各条元素集 */
	public static final String ANSWERLISTS = "//*[@id='content']/div[3]/div[3]/ul/li";
	/** 问答单页 - 获取回答列表中的某一条数据的元素，n表示第几条 */
	public static String getAnswerElement(int n){
		String element = "//*[@id='content']/div[3]/div[3]/ul/li["+n+"]/div[2]";
		return element;
	}

}
