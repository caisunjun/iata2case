package com.anjuke.ui.page;

/**
 * 二手房 - 房源单页
 * http://shanghai.anjuke.com/prop/view/115895728#
 * 
 * @author jessili
 * */

public class Ajk_SaleView {
	/* 二手房房源单页 */
	/** 二手房单页 - 经纪人姓名 */
	public static final String SALE_BROKERNAME = "//*[@id='evaluate_trigger']";
	/** 二手房单页 - 经纪人头像 */
	public static final String SALE_BROKERHEADIMG = "//*[@id='evaluate_trigger']";
	/** 二手房单页 - 我要评价 */
	public static final String SALE_BROKERCOMPANY = "//*[@id='evaluate_trigger']";
	/** 二手房单页 - 我要评价 */
	public static final String SALE_BROKERSTORE = "//*[@id='evaluate_trigger']";
	/** 二手房单页 - 我要评价 */
	public static final String SALE_BROKERTEL = "//*[@id='evaluate_trigger']";
	
	
	/** 二手房单页 - 我要评价 */
	public static final String SALE_WOYAOPINGJIA = "//*[@id='evaluate_trigger']";
	/** 二手房单页 - 输入评论内容 */
	public static final String SALE_CONTENT = "//*[@id='review-body']";
	/** 二手房单页 - 提交评价 */
	public static final String SALE_SUBMITCONTENT = "//*[@id='next']";
	/** 二手房单页 - 经纪人身份登录判断标识 */
	public static final String SALE_BROKERLOGINFLAG = "//*[@id='top_broker_panel']/li[4]/a";
	/** 二手房单页 - 经纪人提交评价的提示信息 */
	public static final String SALE_BROKERSUBMITCONTENTPROMPT = "//*[@id='error']/div/div/div/form/div/div[3]/div[1]/span";
	/** 二手房单页 - 关闭经纪人评价提示框 */
	public static final String SALE_CLOSEBROKERPROMPT = "//*[@id='error']/div/div/div/form/div/div[3]/div[2]/input";
	
	
	/** 二手房单页 - 普通用户登录身份判断标识 */
	public static final String SALE_USERLOGINFLAG = "//*[@id='toplogin_cont_userlogin']/li[5]/a";
	/** 二手房单页 - 普通用户提交评价的提示信息 */
	public static final String SALE_USERSUBMITCONTENTPROMPT = "//*[@id='ok']/div/div/div/form/div/div[3]/div[1]/span";
	/** 二手房单页 - 已登录用户，点击"查看我的评价"按钮 */
	public static final String SALE_SEEMYEVALUATION = "okBtn";
	
	/** 二手房单页 - 未登录用户，点击"不登录，匿名评价"按钮 */
	public static final String SALE_ANONYMOUSEVALUATION = "Tflogin_off";	
	/** 二手房单页 - 匿名用户评价成功，关闭提示层 */
	public static final String SALE_CLOSEANONYMOUSEVALUATIONPROMPT = "//*[@id='ajk_firend']/div/div/div/form/div/div[1]/span[2]/img";
	
	/** 二手房单页 - 注销用户*/
	public static final String SALE_LOGOUT = "//*[@id='toplogin_cont_userlogin']/li[4]/a";	
	
	
	
	
	
	
	

}
