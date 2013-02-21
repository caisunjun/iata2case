package com.anjuke.ui.page;
import org.openqa.selenium.NoSuchElementException;;

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
	/** 问答单页 - 相关问题模块"相关问题"4个字 */
	public static final String RELATED = "//*[@id='content']/div[3]/div[3]/div[1]/h3";
	/** 问答单页 - 当有相关问题模块时，回答列表各条元素集 */
	public static final String ANSWERLISTS_Related = "//*[@id='content']/div[3]/div[4]/ul/li";
	/** 问答单页 - 当没有相关问题模块时，回答列表各条元素集 */
	public static final String ANSWERLISTS_NoRelated = "//*[@id='content']/div[3]/div[3]/ul/li";

	/** 问答单页 - 获取回答列表中的某一条数据的元素，
	 * @param n表示第几条
	 * @param relate为true时，表示存在相关问题模块;为false时，表示不存在相关问题模块;
	 * */
	public static String getAnswerElement (int n,boolean relate){
		String element = null;
		if(relate){
			element = "//*[@id='content']/div[3]/div[3]/ul/li["+n+"]/div[2]";
		}
		else{
			element = "//*[@id='content']/div[3]/div[2]/ul/li["+n+"]/div[2]";
		}
			return element;
	}

	/** 问答单页 - 问题标题 */
	public static final String TITLE = "//*[@id='content']/div[3]/div[1]/div[1]/dl/dt/h1";
	/** 问答单页 - 问题描述 */
	public static final String DESCRIPTION = "//*[@id='content']/div[3]/div[1]/div[1]/div[1]";
	/** 问答单页 - 问题补充 */
	public static final String DESCRIPTIONADD = "//*[@id='content']/div[3]/div[1]/div[1]/div[2]/div[2]";
	/** 问答单页 - 回答列表第一条数据的采纳最佳答案 */
	public static final String AdoptBestAnswer = "//*[@id='content']/div[3]/div[3]/ul/li[1]/div[6]/div[2]/a[2]";
	/** 问答单页 - 最佳答案模块上的”最佳答案“4个字 */
	public static final String BestAnswer = "//*[@id='content']/div[3]/div[3]/h2";
	/** 问答单页 -  补充问题TAB */
	public static final String AddLi = "//*[@id='choose-tab']/li[2]/a/span";
	/** 问答单页 -  补充问题输入框 */
	public static final String Supplement = "//*[@id='supplement']";
	/** 问答单页 -  补充问题”确认提交“按钮 */
	public static final String SupplementSubmit = "//*[@id='submit_supplement']";



}


