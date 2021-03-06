package com.anjuke.ui.page;
/**
 * 安居客 - 问答提问页   http://shanghai.anjuke.com/ask/new/**
 * 
 * @author agneszhang
 * */
public class Ajk_AskQuestion {
	/** 问答提问页 - 页头注册按钮 */
	public static final String REG = "//li[@class='register']/a";
	/** 问答提问页 - 标题输入框 */
	public static final String TITLE = "id^question_title";
	/** 问答提问页 - 描述输入框 */
	public static final String DISCRIPTION = "id^question_description";
	/** 问答提问页 - 标题框和描述框的中间的展开条 */
	public static final String OPENMORE = "id^openmore";
	/** 问答提问页 - 提问按钮 */
	public static final String SUBMIT = "id^submit_btn";
	/** 问答提问页 - 贷款专家 */
	public static final String EXPERT_LOAN = "//dl[@id='expertList']/dd[1]";
	/** 问答提问页 - 法律专家 */
	public static final String EXPERT_LAW = "//dl[@id='expertList']/dd[2]";
	/** 问答提问页 - 装修专家 */
	public static final String EXPERT_ZX = "//dl[@id='expertList']/dd[3]";
	/** 问答提问页 - 风水专家 */
	public static final String EXPERT_FS = "//dl[@id='expertList']/dd[4]";
	/** 问答提问页 - 房产专家 */
	public static final String EXPERT_FC = "//dl[@id='expertList']/dd[5]";
	
	/** 问答提问页 - 专家类型（val为为左起第几个项）*/
	public static final String ExpertTypeByVal(int val)
	{
		 return "//dl[@id='expertList']/dd["+val+"]";
	}
	
}
