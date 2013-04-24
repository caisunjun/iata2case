package com.anjuke.ui.page;

/**
 * 安居客 - 城市首页 http://shanghai.anjuke.com/
 * 
 * @author grayhu
 * */

public class Ajk_HomePage {
	
	//搜索框
	/** 首页 - 搜索 - 输入框 */
	public static final String H_BOX = "//input[@id='home_kw']";
	/** 首页 - 搜索 - 找二手房按钮 */
	public static final String H_BTN = "//input[@class='find-button']";
	
	/** 新版首页 - 搜索 - 二手房搜索框 */
	public static final String SaleSearchBox = "//input[@id='home_kw']";
	/** 新版首页 - 搜索 - 新房搜索框 */
	public static final String XinfangSearchBox = "//input[@id='houses-kw']";
	
	//倾向二手房的首页
	/** 新版首页 - 搜索 - 倾向二手房的找二手房按钮 */
	public static final String SaleSearchBtn_SMain = "//input[@id='home_kw_sale']";
	/** 新版首页 - 搜索 - 倾向二手房的找新房按钮 */
	public static final String XinfangSearchBtn_SMain = "//input[@id='home_kw_xinfang']";
	//倾向新房的首页
	/** 新版首页 - 搜索 - 倾向新房的找二手房按钮 */
	public static final String SaleSearchBtn_XMain = "//input[@id='houses_kw_sale']";
	/** 新版首页 - 搜索 - 倾向新房的找新房按钮 */
	public static final String XinfangSearchBtn_XMain = "//input[@id='houses_kw_xinfang']";
	
	//搜索下拉联想
	/** 新版首页 - 搜索下拉联想 - 二手房分类结果 */
	public static final String SaleResult = "//div[@class='SearchSuggestion']/ul/li[1]";
	/** 新版首页 - 搜索下拉联想 - 新房分类结果 */
	public static final String XinfangResult = "//div[@class='SearchSuggestion']/ul/li[2]";
	/** 新版首页 - 搜索下拉联想 - 第一个联想结果 */
	public static final String FirstSuggestion = "//div[@class='SearchSuggestion']/ul/li[3]";
	/** 新版首页 - 搜索下拉联想 - 第一个联想结果的关键词 */
	public static final String FirstSuggestionKw = "//div[@class='SearchSuggestion']/ul/li[3]/div[1]";
	/** 新版首页 - 搜索下拉联想 - 第一个联想结果的房源数 */
	public static final String FirstSuggestionNum = "//div[@class='SearchSuggestion']/ul/li[3]/div[2]";
	
	/** 新版首页 - 有爱房 - 热门区域模块 */
	public static final String HotAreaHasXin = "//div[@class='filter-sec']/dl[@class='area-filter']/dd/a";
	/** 新版首页 - 无爱房 - 热门区域模块 */
	public static final String HotAreaNoXin = "//div[@class='filter-box hot-area']/a";
	/** 旧版首页 - 从区域开始模块 */
	public static final String AreaInfo = "//dd[@class='areainfo']/a";
	/** 首页 - 导航 - 二手房tab */
	public static final String SALETAB = "//li[@class='sale']";
	/** 首页 - 导航 - 租房tab */
	public static final String RENTALTAB = "//li[@class='rental']";
	/** 首页 - 导航 - 小区tab */
	public static final String COMMTAB = "//li[@class='comm']";
	/** 首页 - 导航 - 经纪人tab */
	public static final String BROKERTAB = "//li[@class='broker']";

	/** 首页 - 房价行情 - 文字 */
	public static final String MarketStat ="//div[@class='stat']";
	/** 首页 - 房价行情 - 小区链接 */
	public static final String CommHideLink ="//div[@class='tip']/a[@class='hidelink']";
	
	/** 首页 - 热门城市列表 */
	public static final String HotCityList ="//div[@class='list'][1]/a";
	
	/** 首页 - 服务之星-服务等级 */
	public static final String SERVICEGRADE(int val){
		String Locator = "//div[@class='mod broker-area']/ul/li["+val+"]/div/div/a[2]";
		return  Locator;
	} 
	/** 首页 - 服务之星-寻找优秀经纪人> */
	public static final String SearchExcellentBroker ="//div[@class='mod broker-area']/a";
}
