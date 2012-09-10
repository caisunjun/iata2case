package com.anjuke.ui.page;

/**
 * 安居客 - 二手房列表 http://shanghai.anjuke.com/sale/
 * 
 * @author grayhu
 * */

public class Ajk_Sale {

	/** 二手房列表 - 第一套房源 */
	public static final String FIRSTPROP = "id^prop_1";
	/** 二手房列表 - 搜索 - 搜索结果数量 */
	public static final String S_COUNT = "//div[@class='totalnum']/strong";
	/** 二手房列表 - 搜索 - 第一页房源数量 */
	public static final String LIST_COUNT = "//ol[@id='list_body']/li";

	/** 二手房列表 - 搜索 - 搜索无结果 */
	public static final String NO_FOUND = "//div[@class='search-nofound']";

	/** 二手房列表 - 筛选条件 - 区域
	 * @param val 为页面显示值
	 * 传入筛选条件值时，售价需添加“元”，“50-80”万为：“50-80万元”，其他按页面显示值 */
	public static final String S_SELECT(String val) {
		String locater = "//a[@title='" + val + "']";
		return locater;
	}

	/** 二手房列表 - 搜索结果 - 房源 - 高亮区域 */
	public static final String getKeyRegion(int val) {
		String locater = "//div[@id='prop_" + val + "']/div[2]/address/em";
		return locater;
	}

	/** 二手房列表 - 搜索结果 - 房源 - 高亮板块 */
	public static final String getKeyBlock(int val) {
		String locater = "//div[@id='prop_" + val + "']/div[2]/address/em";
		return locater;
	}

	/** 二手房列表 - 搜索结果 - 房源 - 小区名 */
	public static final String getKeyDistrict(int val) {
		String locater = "//div[@id='prop_" + val + "']/div[2]/address/a/em";
		return locater;
	}

	/** 二手房列表 - 搜索结果 - 房源 - 高亮户型室 */
	public static final String getKeyRoom(int val) {
		String locater = "//p[@id='prop_detail_qt_prop_" + val + "']/em";
		return locater;
	}

	/** 二手房列表 - 搜索结果 - 房源 - 图片 */
	public static final String getTitle(int val) {
		String locater = "//img[@id='prop_" + val + "_a']";
		return locater;
	}

	/** 二手房列表 - 搜索结果 - 房源 - 小区名地址 */
	public static final String getAddress(int val) {
		String locater = "//div[@id='prop_" + val + "']/div[2]/address";
		return locater;
	}

	/** 二手房列表 - 搜索结果 - 房源 - 厅室、面积、均价、楼层、建筑年代 */
	public static final String getDetail(int val) {
		String locater = "//p[@id='prop_detail_qt_prop_" + val + "']";
		return locater;
	}

	/** 二手房列表 - 搜索结果 - 房源 - 面积 */
	public static final String getArea(int val) {
		String locater = "//p[@id='prop_detail_qt_prop_" + val + "']";
		return locater;
	}

	/** 二手房列表 - 搜索结果 - 房源 - 室 */
	public static final String getRoom(int val) {
		String locater = "//p[@id='prop_detail_qt_prop_" + val + "']";
		return locater;
	}

	/** 二手房列表 - 搜索结果 - 房源 - 总价 */
	public static final String getPrice(int val) {
		String locater = "//div[@id='prop_price_qt_prop_" + val + "']/strong";
		return locater;
	}

	/** 二手房列表 - 搜索结果 - 房源 - 急推图片 */
	public static final String getTags(int val) {
		String locater = "//div[@id='prop_" + val + "']/div[4]/img";
		return locater;
	}

}
