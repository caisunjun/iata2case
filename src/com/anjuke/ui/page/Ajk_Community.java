package com.anjuke.ui.page;

import com.anjukeinc.iata.ui.util.GetRandom;

/**
 * 安居客 - 小区列表 http://shanghai.anjuke.com/community/
 * 
 * @author grayhu
 * */

public class Ajk_Community {
	/** 小区列表 - 搜索 - 搜索结果数量 */
	public static final String C_COUNT = "//div[@class='num']/span";
	/** 小区列表 - 列表页第一个小区 */
	public static final String FirsrComm = "//ol[@id='list_body']/li[1]";
	/** 小区列表 - 随机获得列表页的一个小区 （1页10个小区）*/
	public static final String RandomComm = "//ol[@id='list_body']/li["+GetRandom.getrandom(10)+"]";
	
	
	/** 小区列表 - 搜索 - 第一页小区数量 */
	public static final String LIST_COUNT = "//ol[@id='list_body']/li";
	
	/** 小区列表 - 搜索 - 搜索无结果 */
	public static final String NO_FOUND = "//div[@id='tagsearch_remmto']";
}
