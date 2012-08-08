package com.anjuke.ui.page;

/**
 * 中国网络经纪人 - 发布出租
 * http://my.anjuke.com/user/broker/propertynew/rent/step1/W0QQoooflagZ1
 * 
 * @author grayhu
 * */

public class Broker_PropertynewRentStep {

	/* 发布租房第一步 */

	/** 发布出租1 - 输入小区名 */
	public static final String RENT_XIAOQU = "//input[@id='txtCommunity']";
	/** 发布出租1 - 选择合租 */
	public static final String RENT_HEZU = "id^radIsDolmus_0";
	/** 发布出租1 - 选择整租 */
	public static final String RENT_ZHENGZU = "id^radIsDolmus_1";
	/** 发布出租1 - 输入租金 */
	public static final String RENT_PRICE = "//input[@id='txtProPrice']";
	/** 发布出租1 - 付款方式 -付 */
	public static final String RENT_FUKUAN = "//input[@id='txtPayNum']";
	/** 发布出租1 - 付款方式 -押 */
	public static final String RENT_YAJIN = "//input[@id='txtDepositNum']";
	/** 发布出租1 - 建筑面积 */
	public static final String RENT_AREA = "//input[@id='txtAreaNum']";
	/** 发布出租1 - 房型 - 室 */
	public static final String RENT_ROOM = "//input[@id='txtRoomNum']";
	/** 发布出租1 - 房型 - 厅 */
	public static final String RENT_HALL = "//input[@id='txtHallNum']";
	/** 发布出租1 - 房型 - 卫 */
	public static final String RENT_TOILE = "//input[@id='txtToiletNum']";
	/** 发布出租1 - 楼层 - 所在楼层 */
	public static final String RENT_FLOOR = "//input[@id='txtProFloor']";
	/** 发布出租1 - 房屋情况 - 房屋类型 */
	public static final String RENT_LEIXING = "//input[@id='radUseType']";
	/** 发布出租1 - 房屋情况 - 装修情况 */
	public static final String RENT_ZHUANGXIU = "//input[@id='radFitment']";
	/** 发布出租1 - 房屋情况 - 朝向 */
	public static final String RENT_CHAOXIANG = "//input[@id='radHouseOri']";
	/** 发布出租1 - 建筑年代 */
	public static final String RENT_HOUSE_AGE = "//input[@id='txtHouseAge']";
	/** 发布出租1 - 配置 - 电视机 */
	public static final String RENT_HAVE_TV = "//input[@id='chkHaveTvbox']";
	/** 发布出租1 - 房源标题 */
	public static final String RENT_TITLE = "//input[@id='txtProName']";
	/** 发布出租1 - 房源描述 - frame定位 */
	public static final String RENT_HOUSE_DESC = "//td[@id='cke_contents_txtExplain']/iframe";
	/** 发布出租1 - 房源推荐 */
	public static final String RENT_RECOM = "//input[@id='checkbox-r']";
	/** 发布出租1 - 去上传照片 - 按钮 */
	public static final String RENT_UPLOAD_PHOTO = "//input[@id='btnSaveEditp']";
	/** 发布出租1 - 立即发布 - 按钮 */
	public static final String RENT_PUBLISH = "//input[@id='btnSaveEdit']";
	
	
	/* 发布租房第二步 */
	/** 发布出租2 - 上传图片 - 室内图 */
	public static final String RENT_SHINEITU = "//object[@id='multi_upload_apf_id_6Uploader']";
	/** 发布出租2 - 上传图片 - 房型图 */
	public static final String RENT_FANGXINGTU = "//object[@id='multi_upload_apf_id_7Uploader']";
	/** 发布出租2 - 上传图片 - 小区图 */
	public static final String RENT_XIAOQUTU = "//object[@id='multi_upload_apf_id_8Uploader']";
	/** 发布出租2 - 发布房源 - 按钮 */
	public static final String RENT_PUBLISH2 = "//input[@id='submitup']";

	
	/* 发布租房第三步 */
	/** 发布出租3 - 发布成功 - 文字提示 */
	public static final String RENT_SUCCEED = "//div[@class='p-popresult succeed']/h2";
	/** 发布出租3 - 房源单页 - 文字提示 */
	public static final String RENT_PROPVIEW = "//div[@class='p-popresult succeed']/p/a[1]";
	/** 发布出租3 - 管理房源 - 文字提示 */
	public static final String RENT_PROPMANAGE = "//div[@class='p-popresult succeed']/p/a[2]";

}
