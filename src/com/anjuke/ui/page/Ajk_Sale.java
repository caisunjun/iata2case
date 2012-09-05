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
	
	/** 二手房列表 - 搜索结果 - 房源 - 高亮区域 */
	public static String getKeyRegion(int val){
		String element = "//div[@id='prop_" + val + "']/div[2]/address/em";
		return element;
	}
	
	/** 二手房列表 - 搜索结果 - 房源 - 高亮板块 */
	public static String getKeyBlock(int val){
		String element = "//div[@id='prop_" + val + "']/div[2]/address/em";
		return element;
	}
	
	/** 二手房列表 - 搜索结果 - 房源 - 小区名 */
	public static String getKeyDistrict(int val){
		String element = "//div[@id='prop_" + val + "']/div[2]/address/a/em";
		return element;
	}
	
	/** 二手房列表 - 搜索结果 - 房源 - 高亮户型室 */
	public static String getKeyRoom(int val){
		String element = "//p[@id='prop_detail_qt_prop_" + val + "']/em";
		return element;
	}
	
	/** 二手房列表 - 搜索结果 - 房源 - 图片 */
	public static String getTitle(int val){
		String element = "//img[@id='prop_" + val +"_a']" ;
		return element;
	}
	
	/** 二手房列表 - 搜索结果 - 房源 - 小区名地址 */
	public static String getAddress(int val){
		String element = "//div[@id='prop_" + val + "']/div[2]/address";
		return element;
	}
	
	/** 二手房列表 - 搜索结果 - 房源 - 厅室、面积、均价、楼层、建筑年代 */
	public static String getDetail(int val){
		String element = "//p[@id='prop_detail_qt_prop_" + val + "']";
		return element;
	}
	
	/** 二手房列表 - 搜索结果 - 房源 - 面积*/
	public static String getArea(int val){
		String element = "//p[@id='prop_detail_qt_prop_" + val + "']";
		return element;
	}
	
	/** 二手房列表 - 搜索结果 - 房源 - 室*/
	public static String getRoom(int val){
		String element = "//p[@id='prop_detail_qt_prop_" + val + "']";
		return element;
	}
	
	/** 二手房列表 - 搜索结果 - 房源 - 总价 */
	public static String getPrice(int val){
		String element = "//div[@id='prop_price_qt_prop_" + val + "']/strong";
		return element;
	}
	
	/** 二手房列表 - 搜索结果 - 房源 - 急推图片 */
	public static String getTags(int val){
		String element = "//div[@id='prop_" + val + "']/div[4]/img";
		return element;
	}
	
	
	
}
