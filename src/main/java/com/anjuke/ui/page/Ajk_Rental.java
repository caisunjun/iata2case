package com.anjuke.ui.page;

/**
 * 安居客 - 租房列表 http://shanghai.anjuke.com/rental/
 * 
 * @author grayhu
 * */

public class Ajk_Rental {
	
	/** 租房列表 - 搜索 - 搜索无结果 */
	public static final String NO_FOUND = "//div[@class='SearchIndepNoResult']";

	/** 租房列表 - 筛选条件 - 区域、租金、房型、类型
	 * @param val 为鼠标移至筛选项上的悬浮提示 */
	public static final String S_SELECT(String val) {
		String locater = "//a[@title='" + val + "']";
		return locater;
	}
	
	/** 租房列表 - 房源信息 - 图片 */
	public static final String getPic(int val) {
		String locater = "//img[@id='prop_" + val + "_a']";
		return locater;
	}
	
	/** 租房列表 - 房源信息 - 户型+合租+装修+楼层 */
	public static final String getKeyRoom(int val) {
		String locater = "//p[@id='prop_detail_qt_prop_" + val + "']";
		return locater;
	}
	
	/** 租房列表 - 房源信息 - 租金 */
	public static final String getPrice(int val) {
		String locater = "//div[@id='prop_price_qt_prop_" + val + "']/strong";
		return locater;
	}
	
}
