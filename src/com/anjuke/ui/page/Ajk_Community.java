package com.anjuke.ui.page;

import com.anjukeinc.iata.ui.util.GetRandom;

/**
 * 安居客 - 小区列表 http://shanghai.anjuke.com/community/
 * 
 * @author grayhu
 * */

public class Ajk_Community {
	/** 小区列表 - 筛选 - 全部上海小区 - 区域 */
	public static final String AreaBox = "//div[@id='subtagbox']/div[1]/ul/li";
	/** 小区列表 - 筛选 - 全部上海小区 - 传入数字 返回区域模块里对应的区域 */
	public static final String AreaOfNumber (int AreaNumber){
		return "//div[@id='subtagbox']/div[1]/ul/li["+AreaNumber+"]/a";
	}
	
	/** 小区列表 - 筛选 - 按区域找 - 板块 */
	public static final String BlockBox = "//div[@id='subtagbox']/div[2]/ul/li";
	/** 小区列表 - 筛选 - 按区域找 - 传入数字 返回区域模块里对应的板块*/
	public static final String BlockOfNumber (int BlockNumber){
		return "//div[@id='subtagbox']/div[2]/ul/li["+BlockNumber+"]/a";
	}
	
	/** 小区列表 - 搜索 - 搜索结果数量 */
	public static final String C_COUNT = "//div[@class='num']/span";
	/** 小区列表 - 列表页第一个小区 */
	public static final String FirsrComm = "//ol[@id='list_body']/li[1]";
	/** 小区列表 - 列表页第一个小区的小区名 */
	public static final String FirsrCommName = "//ol[@id='list_body']/li[1]/div/div[@class='details']/h4/a";
	/** 小区列表 - 列表页第一个小区的二手房数量*/
	public static final String FirsrCommPropNum = "//ol[@id='list_body']/li[1]/div/div[@class='details']/p[@class='house']/span";
	/** 小区列表 - 列表页第一个小区的均价 */
	public static final String FirsrCommPrice = "//ol[@id='list_body']/li[1]/div/div[@class='price']/strong/em";
	/** 小区列表 - 随机获得列表页的一个小区 （1页10个小区）*/
	public static final String RandomComm = "//ol[@id='list_body']/li["+GetRandom.getrandom(10)+"]";
	
	
	/** 小区列表 - 搜索 - 第一页小区数量 */
	public static final String LIST_COUNT = "//ol[@id='list_body']/li";
	
	/** 小区列表 - 搜索 - 搜索无结果 */
	public static final String NO_FOUND = "//div[@id='tagsearch_remmto']";
}
