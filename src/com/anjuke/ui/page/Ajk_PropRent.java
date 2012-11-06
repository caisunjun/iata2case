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

	/** 租房单页 - 标题 */
	public static final String TITLE = "//h1[@class='propInfoTitle']";
	/** 租房单页 - 租金 */
	public static final String ZUJIN = "//ul/li[1]/em";
	/** 租房单页 - 租金单位 */
	public static final String ZUJIN_DW = "//ul[@class='prop_basic']/li[1]/span[1]";
	/** 房源详情页 - 押金方式 */
	public static final String YAJIN = "//ul[@class='prop_basic']/li[1]/span[2]";
	/** 租房单页 - 建筑类型 */
	public static final String LEIXING = "//ul/li[7]/div[1]";
	/** 租房单页 - 房型、合租方式 */
	public static final String FANGXING = "//ul[@class='prop_basic']/li[2]";
	/** 租房单页 - 朝向 */
	public static final String CHAOXIANG = "//li[@class='prop_basic_right']/div[2]";
	/** 租房单页 - 面积 */
	public static final String AREA = "//ul[@class='prop_basic']/li[3]";
	/** 租房单页 - 装修 */
	public static final String ZHUANGXIU = "//li[@class='prop_basic_right']/div[3]";
	/** 租房单页 - 楼层 */
	public static final String FLOOR = "//ul[@class='prop_basic']/li[4]";
	/** 租房单页 - 建筑年代 */
	public static final String HOUSE_AGE = "//li[@class='prop_basic_right']/div[4]";
	/** 租房单页 - 小区名 */
	public static final String XIAOQUMING = "id^text_for_school_1";
	/** 租房单页 - 房源描述 */
	public static final String MIAOSHU = "//div[@class='propDescValue_p']/p";
	
	/*右侧经纪人模块*/
	/** 租房单页 - 经纪人头像 */
	public static final String BROKERHEADIMG = "//div[@class='propBrokerPhoto']/a/img";
	/** 租房单页 - 经纪人姓名 */
	public static final String BROKERNAME = "//div[@class='prop_new_line']";
	/** 租房单页 - 经纪人 公司门店 */
	public static final String COMPANYNAME = "//div[@class='mycompany shop2c']";
	/** 租房单页 - 经纪人服务等级(钻/冠) */
	public static final String Star_BrokerInfo = "//span[@id='fux']";
	
	/** 租房单页 - 小区简介tab */
	public static final String COMMINTROTAB = "//*[@id='extra-propview']/ul/li[4]/a";
	
	/* 地图 */
	/** 租房单页 - 小区地图 - 小区名链接 */
	public static final String MAPCOMMNAME = "//div[@id='around_search']/table/tbody/tr[1]/td/a";

}
