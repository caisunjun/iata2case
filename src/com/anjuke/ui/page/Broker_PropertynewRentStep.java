package com.anjuke.ui.page;

/**
 * 中国网络经纪人 - 发布出租
 * http://my.anjuke.com/user/broker/propertynew/rent/step1/W0QQoooflagZ1
 * 
 * @author grayhu
 * */

public class Broker_PropertynewRentStep {

	/** 发布出租1 - 房源数量达到上限 */
	public static final String SHANGXIAN = "//div[@class='p-popresult alert']/h2";
	
	/* 发布租房第一步 */ //div[@class='p-popresult alert']/h2
	
	/** 发布出租1 - 输入小区名 */
	public static final String XIAOQUMING = "id^txtCommunity";
	/** 发布出租1 - 下拉框 - 选小区 */
	public static final String XUANXIAOQU = "//ul[@id='targetid']/li[2]/span";
	/** 发布出租1 - 选择合租 */
	public static final String HEZU = "id^radIsDolmus_0";
	/** 发布出租1 - 选择整租 */
	public static final String ZHENGZU = "id^radIsDolmus_1";
	/** 发布出租1 - 输入租金 */
	public static final String ZUJIN = "id^txtProPrice";
	/** 发布出租1 - 付款方式 -付 */
	public static final String FUKUAN = "id^txtPayNum";
	/** 发布出租1 - 付款方式 -押 */
	public static final String YAJIN = "id^txtDepositNum";
	/** 发布出租1 - 建筑面积 */
	public static final String AREA = "id^txtAreaNum";
	/** 发布出租1 - 房型 - 室 */
	public static final String ROOM = "id^txtRoomNum";
	/** 发布出租1 - 房型 - 厅 */
	public static final String HALL = "id^txtHallNum";
	/** 发布出租1 - 房型 - 卫 */
	public static final String TOILE = "id^txtToiletNum";
	/** 发布出租1 - 楼层 - 所在楼层 */
	public static final String FLOOR = "id^txtProFloor";
	/** 发布出租1 - 楼层 - 总楼层 */
	public static final String FLOOR_T = "id^txtFloorNum";
	/** 发布出租1 - 房屋情况 - 房屋类型 */
	public static final String LEIXING = "id^radUseType";
	/** 发布出租1 - 房屋情况 - 装修情况 */
	public static final String ZHUANGXIU = "id^radFitment";
	/** 发布出租1 - 房屋情况 - 朝向 */
	public static final String CHAOXIANG = "id^radHouseOri";
	/** 发布出租1 - 建筑年代 */
	public static final String HOUSE_AGE = "id^txtHouseAge";
	/** 发布出租1 - 配置 - 电视机 */
	public static final String CONFIG_TV = "id^chkHaveTvbox";
	/** 发布出租1 - 房源标题 */
	public static final String TITLE = "id^txtProName]";
	/** 发布出租1 - 房源描述 - frame定位 */
	public static final String MIAOSHU = "//td[@id='cke_contents_txtExplain']/iframe";
	/** 发布出租1 - 房源推荐 */
	public static final String TUIJIAN = "id^checkbox-r";
	/** 发布出租1 - 去上传照片 - 按钮 */
	public static final String UPLOAD_PHOTO = "id^btnSaveEditp";
	/** 发布出租1 - 立即发布 - 按钮 */
	public static final String PUBLISH = "id^btnSaveEdit";

	/* 发布租房第二步 */
	/** 发布出租2 - 上传图片 - 室内图 */
	public static final String SHINEITU = "//object[@id='multi_upload_apf_id_6Uploader']";
	/** 发布出租2 - 上传图片 - 房型图 */
	public static final String FANGXINGTU = "//object[@id='multi_upload_apf_id_7Uploader']";
	/** 发布出租2 - 上传图片 - 小区图 */
	public static final String XIAOQUTU = "//object[@id='multi_upload_apf_id_8Uploader']";
	/** 发布出租2 - 发布房源 - 按钮 */
	public static final String PUBLISH2 = "id^submitup";

	/* 发布租房第三步 */
	/** 发布出租3 - 发布成功 - 文字提示 */
	public static final String SUCCEED = "//div[@class='p-popresult succeed']/h2";
	/** 发布出租3 - 房源单页 - 文字链接 */
	public static final String PROPRENT = "//div[@class='p-popresult succeed']/p/a[1]";
	/** 发布出租3 - 管理房源 - 文字链接 */
	public static final String PROPMANAGE = "//div[@class='p-popresult succeed']/p/a[2]";

}
