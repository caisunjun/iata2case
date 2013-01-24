package com.anjuke.ui.page;

/**
 * 中国网络经纪人 - 发布出售
 * http://my.anjuke.com/user/broker/propertynew/sale/step1/W0QQoooflagZ1
 * 
 * @author grayhu
 * @updateAuthor ccyang 
 * Last update time 2012-8-7 14:20
 * */

public class Broker_PropertynewSaleStep {
	
	/** 发房第一步*/
	
	/** 发布出售 - 请输入小区名 */
	public static final String XIAOQU = "//input[@id='txtCommunity']";
	/** 发布出售 - 下拉选择小区名 */
	public static final String XIALAXIAOQU = "//div[@id='autocommunity']/ul[2]/li[2]";
	/** 发布出售 - 房源价格 */
	public static final String PRICE = "//input[@id='txtProPrice']";
	/** 发布出售 - 产证面积 */
	public static final String AREA = "//input[@id='txtAreaNum']";
	/** 发布出售 - 房型-X室*/
	public static final String ROOM = "//input[@id='txtRoomNum']";
	/** 发布出售 - 房型-X厅*/
	public static final String HALL = "//input[@id='txtHallNum']";
	/** 发布出售 - 房型-X卫 */
	public static final String TOILET = "//input[@id='txtToiletNum']";
	/** 发布出售 - 当前楼层*/
	public static final String LOUCENG = "//input[@id='txtProFloor']";
	/** 发布出售 - 共几层 */
	public static final String ZONGLOUCENG = "//input[@id='txtFloorNum']";
	/** 发布出售 - 房屋类型  for select方法 后接选项名 */
	public static final String LEIXING = "id^radUseType";
	/** 发布出售 - 装修类型 for select方法 后接选项名*/
	public static final String ZHUANGXIU = "id^radFitment";
	/** 发布出售 - 朝向 */
	public static final String CHAOXIANG = "id^radHouseOri";
	/** 发布出售 - 建造年代*/
	public static final String HOUSEAGE = "//input[@id='txtHouseAge']";
	/** 发布出售 - 房源标题 */
	public static final String HOUSETITLE = "//input[@id='txtProName']";
	/** 发布出售 - 房源描述 */
	public static final String HOUSEDES = "//td[@id='cke_contents_txtExplain']/iframe";
	/** 发布出售 - 推荐该房源（不点才是推荐） */
	public static final String RECOMMAND = "name^radSaleType";
	/** 发布出售 - 去上传照片按钮 */
	public static final String NEXTSTEP = "//input[@id='btnNext']";
	/** 发布出售 - 立即发布按钮 */
	public static final String FIRSTSUBMIT = "//input[@id='btnSubmit']";
	
	/** 发房第二步*/
	
	/** 发布出售 - 上传室内图-多张上传 */
	public static final String SHINEIMulti = "//div[@class='box1px block-pinner']/div/div[@class='upcontrol']/object";
	/** 发布出售 - 上传房型图-多张上传 */
	public static final String FANGXINGMulti = "//div[@class='box1px block-ptype']/div/div[@class='upcontrol']/object";
	/** 发布出售 - 上传小区图-多张上传 */
	public static final String XIAOQUMulti = "//div[@class='box1px block-ptype block-community']/div/div[@class='upcontrol']/object";
	
	/** 发布出售 - 上传室内图-单张上传 */
	public static final String SHINEISINGLE = "//input[@id='single_upload_apf_id_6']";
	/** 发布出售 - 上传房型图-单张上传 */
	public static final String FANGXINGSINGLE = "//input[@id='single_upload_apf_id_7']";
	/** 发布出售 - 上传小区图-单张上传 */
	public static final String XIAOQUSINGLE = "//input[@id='single_upload_apf_id_8']";
	
	/** 发布出售 - 所有已上传的图片数 */
	public static final String PicCount = "//div[@class='photoseries']/div/div";
	
//	/** 发布出售 - 上传室内图-单张上传 - PPC*/
//	public static final String SHINEISINGLE_PPC = "//div[@class='box1px block-pinner']/div/div[@class='upcontrol']/div/input";
//	/** 发布出售 - 上传房型图-单张上传 - PPC*/
//	public static final String FANGXINGSINGLE_PPC = "//div[@class='box1px block-ptype']/div/div[@class='upcontrol']/div/input";
//	/** 发布出售 - 上传小区图-单张上传 - PPC*/
//	public static final String XIAOQUSINGLE_PPC = "//div[@class='box1px block-ptype block-community']/div/div[@class='upcontrol']/div/input";
	
	/** 发布出售 - 发布房源按钮 */
	public static final String SECONDSUBMIT = "//input[@id='submitup']";
	
	/** 发房成功*/
	
	/** 发布出售 - 房源发布、编辑成功的提示语 */
	public static final String SUCCESSMESSAGE = "//div[@class='p-popresult succeed']/h2";
	/** 发布出售 - 房源发布、编辑成功的提示语 - PPC*/
	public static final String SUCCESSMESSAGE_PPC = "//em[@class='h-tips']/h3";
	/** 发布出售 - 查看房源单页 */
	public static final String PROPLINK = "//div[@class='p-popresult succeed']/p/a[1]";

}
