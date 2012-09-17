package com.anjuke.ui.page;

/**
 * 安居客 - 二手房房源单页 http://shanghai.anjuke.com/prop/view/**
 * 
 * @author grayhu
 * @updateAuthor ccyang 
 * Last update time 2012-8-7 16:00
 * */

public class Ajk_PropView {
	/* 房源面包屑*/
	/** 二手房单页 - 区域 */
	public static final String REGION = "//div[@id='propNav']/a[3]";
	/** 二手房单页 - 板块 */
	public static final String BLOCK = "//div[@id='propNav']/a[4]";

	/* 房源信息 */
	/** 二手房单页 - 面包屑处小区名 */
	public static final String NavCommName = "//*[@id='propNav']/a[5]";
	
	
	/** 二手房单页 - 房源标题 */
	public static final String HOUSETITLE = "className^propInfoTitle";
	/** 二手房单页 - 总价 */
	public static final String RICE = "//ul/li[1]/em";
	/** 二手房单页 - 房型:X室X厅X卫*/
	public static final String HOUSETYPE = "//ul[@class='prop_basic']/li[2]";
	/** 二手房单页 - 面积 */
	public static final String AREA = "//ul[@class='prop_basic']/li[3]";
	/** 二手房单页 - 楼层：XX/YY层 */
	public static final String FLOOR = "//ul[@class='prop_basic']/li[4]";
	/** 二手房单页 - 单价 */
	public static final String UNITPRICE = "//ul/li[7]/div[1]";
	/** 二手房单页 - 朝向 */
	public static final String CHAOXIANG = "//ul/li[7]/div[2]";
	/** 二手房单页 - 装修 */
	public static final String ZHUANGXIU = "//ul/li[7]/div[3]";
	/** 二手房单页 - 建造年代 */
	public static final String HOUSEAGE = "//ul/li[7]/div[4]";
	/** 二手房单页 - 房源信息处小区名 */
	public static final String COMMNAME = "//a[@id='text_for_school_1']";
	
	/** 二手房单页 - 手机号 */
	public static final String BROKERTEL = "//div[@class='number']/b";
	/** 二手房单页 - 手机号下方服务等级(钻/冠) */
	public static final String Star_PropInfo = "//p[@id='fwdj_propinfo']/span[2]";
	
	/*手机号下方按钮*/
	/** 二手房单页 - 房贷计算器链接 */
	public static final String CALCULATOR = "id^caculator";
	
	/*右侧经纪人模块*/
	/** 二手房单页 - 经纪人头像 */
	public static final String BROKERHEADIMG = "//[@class='propBrokerPhoto']/a/img";
	/** 二手房单页 - 经纪人姓名 */
	public static final String BROKERNAME = "//div[@class='prop_new_line']";
	/** 二手房单页 - 经纪人 公司门店 */
	public static final String COMPANYNAME = "//div[@class='mycompany shop2c']";
	/** 二手房单页 - 经纪人服务等级(钻/冠) */
	public static final String Star_BrokerInfo = "//span[@id='fux']";
	
	
	/** 二手房单页 - 小区简介tab */
	public static final String COMMINTROTAB = "//*[@id='extra-propview']/ul/li[4]/a";
	/** 二手房单页 - 房源编号 */
	public static final String PROPID = "id^propID";
	
	/* 房源描述 */
	
	/** 二手房单页 - 房源描述 */
	public static final String HOUSEDES = "//div[@class='propDescValue_p']/p";
	
	/* 近期评价 */
	/** 二手房单页 - 近期评价 - 服务等级(钻/冠) */
	public static final String Star_downufsinfo = "//*[@id='downufsinfo']/ul[1]/li[1]/span";
	
	/* 地图 */
	/** 二手房单页 - 小区地图 - 小区名链接 */
	public static final String MAPCOMMNAME = "//*[@id='bottom_map_container']/dl/dt/a";
	/* 小区简介 */
	/** 二手房单页 - 按售价统计房源数量 */
	public static final String PriceCount = "//div[@class='commtrends']/dl/dd[1]/a";
	/** 二手房单页 - 按面积统计房源数量 */
	public static final String AreaCount = "//div[@class='commtrends']/dl/dd[2]/a";
	/* 等等 */
	
	
	
	
	
	
	/* AnjukeUfsSubmmitEvaluation.java里新增的locator */
	/** 二手房单页 - 公司 */
	public static final String SALE_BROKERCOMPANY = "//*[@id='navigation-propview']/div/div/div[3]/p[1]/a";
	/** 二手房单页 - 门店 */
	public static final String SALE_BROKERSTORE = "//*[@id='navigation-propview']/div/div/div[3]/p[2]/a";


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
	/** 二手房单页 - 输入评论内容 */
	public static final String SALE_USERINPUTCONTENT = "//*[@id='review-body']";
	/** 二手房单页 - 普通用户提交评价 */
	public static final String SALE_USERSUBMITCONTENT = "//*[@id='next']";
	/** 二手房单页 - 普通用户提交评价的提示信息 */
	public static final String SALE_USERSUBMITCONTENTPROMPT = "//*[@id='ok']/div/div/div/form/div/div[3]/div[1]/span";
	/** 二手房单页 - 已登录用户，点击"查看我的评价"按钮 */
	public static final String SALE_SEEMYEVALUATION = "//*[@id='okBtn']";
	
	/** 二手房单页 - 未登录用户，点击"不登录，匿名评价"按钮 */
	public static final String SALE_ANONYMOUSEVALUATION = "//*[@id='Tflogin_off']";	
	/** 二手房单页 - 匿名用户评价成功，关闭提示层 */
	public static final String SALE_CLOSEANONYMOUSEVALUATIONPROMPT = "//*[@id='ajk_firend']/div/div/div/form/div/div[1]/span[2]/img";	
	
	/** 二手房单页 - 注销用户*/
	public static final String SALE_LOGOUTUSER = "//*[@id='toplogin_cont_userlogin']/li[4]/a";	
	public static final String SALE_LOGOUTBROKER = "//*[@id='top_broker_panel']/li[1]/a";
	
	
	
	
}
