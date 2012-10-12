package com.anjuke.ui.page;

/**
 * 安居客 - 租房详细页 http://shanghai.anjuke.com/prop/rent/**
 * 
 * @author grayhu
 * */

public class Ajk_PropRent {

	/* 房源面包屑*/
	/** 租房单页 - 区域 */
	public static final String REGION = "//div[@id='propNav']/a[3]";
	/** 租房单页 - 板块 */
	public static final String BLOCK = "//div[@id='propNav']/a[4]";

	/* 房源信息 */
	/** 租房单页 - 面包屑处小区名 */
	public static final String NavCommName = "//*[@id='propNav']/a[5]";
	
	
	// 房源基本信息

	/** 房源详情页 - 标题 */
	public static final String TITLE = "//h1[@class='propInfoTitle']";
	/** 房源详情页 - 租金 */
	public static final String ZUJIN = "//ul/li[1]/em";
	/** 房源详情页 - 租金单位 */
	public static final String ZUJIN_DW = "//ul[@class='prop_basic']/li[1]/span[1]";
	/** 房源详情页 - 押金方式 */
	public static final String YAJIN = "//ul[@class='prop_basic']/li[1]/span[2]";
	/** 房源详情页 - 建筑类型 */
	public static final String LEIXING = "//ul/li[7]/div[1]";
	/** 房源详情页 - 房型、合租方式 */
	public static final String FANGXING = "//ul[@class='prop_basic']/li[2]";
	/** 房源详情页 - 朝向 */
	public static final String CHAOXIANG = "//li[@class='prop_basic_right']/div[2]";
	/** 房源详情页 - 面积 */
	public static final String AREA = "//ul[@class='prop_basic']/li[3]";
	/** 房源详情页 - 装修 */
	public static final String ZHUANGXIU = "//li[@class='prop_basic_right']/div[3]";
	/** 房源详情页 - 楼层 */
	public static final String FLOOR = "//ul[@class='prop_basic']/li[4]";
	/** 房源详情页 - 建筑年代 */
	public static final String HOUSE_AGE = "//li[@class='prop_basic_right']/div[4]";
	/** 房源详情页 - 小区名 */
	public static final String XIAOQUMING = "id^text_for_school_1";
	/** 房源详情页 - 房源描述 */
	public static final String MIAOSHU = "//div[@class='propDescValue_p']/p";

}
