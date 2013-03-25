package com.anjuke.ui.page;

/**
 * 安居客 - 二手房列表 http://shanghai.anjuke.com/sale/
 * 
 * @author grayhu
 * */

public class Ajk_Sale {
	
	/*顶部搜索栏*/
	/** 二手房列表 - 顶部搜索框 */
	public static final String KwInput = "//input[@class='input_text']";
	/** 二手房列表 - 顶部搜索按钮 */
	public static final String KwSubmit = "//input[@class='input_button se_house']";
	
	/*移动广告横幅*/
	/** 二手房列表 - 移动广告横幅 - 关闭广告*/
	public static final String CloseMobAdv = "//i[@id='closeMobileGuide']";
	
	/*找房条件栏*/
	/** 二手房列表 - 找房条件栏 - 上次找房条件*/
	public static final String LastCond = "//a[@class='specialchars']";
	/** 二手房列表 - 找房条件栏 - 订阅上次找房条件*/
	public static final String SaveLastCond = "//span[@class='savepropcond-ion']";
	/** 二手房列表 - 找房条件栏 - 订阅这次找房条件*/
	public static final String SaveThisCond = "//a[@id='subscribe']";
	
	/** 二手房列表 - 保存找房条件弹出框*/
	public static final String SaveCondPopUp = "//div[@class='layer_box']";
	/** 二手房列表 - 保存找房条件弹出框 - 提示信息*/
	public static final String SaveCondPopUpMessage = "//div[@class='layer_cont']/dl/dt/strong";
	/** 二手房列表 - 保存找房条件弹出框 - 查看保存的找房条件*/
	public static final String CheckSaveCond = "//a[contains(.,'去看看保存的找房条件>>')]";
	/** 二手房列表 - 保存找房条件弹出框 - 继续找房按钮*/
	public static final String CloseSaveCondPopUp = "//a[contains(.,'继续找房')]";
	
	/*筛选区域*/
	/** 二手房列表 - 筛选条件 - 区域
	 * @param val 为页面显示值
	 * 传入筛选条件值时，售价需添加“元”，“50-80”万为：“50-80万元”，其他按页面显示值 */
	public static final String S_SELECT(String val) {
		String locater = "//a[@title='" + val + "']";
		return locater;
	}
	
	//新推等tag切换栏
	/** 二手房列表 - tag切换 - 全部房源 */
	public static final String TagAll = "//div[@class='quicktabs']/ul/li[1]";
	
//	/** 二手房列表 - tag切换 - 急售房源 */
//	public static final String TagHot = "//div[@class='quicktabs']/ul/li[2]";
//	
//	/** 二手房列表 - tag切换 - 新推房源 */
//	public static final String TagNew = "//div[@class='quicktabs']/ul/li[3]";
	
	/** 二手房列表 - tag切换 - 多图房源 */
	public static final String TagMore = "//div[@class='quicktabs']/ul/li[2]";
	
	/** 二手房列表 - tag切换 - 个人房源 */
	public static final String TagOwner = "//div[@class='quicktabs']/ul/li[3]";
	
	/** 二手房列表 - 下一页按钮 */
	public static final String NextPage = "//div[@class='pagelink nextpage']";
	
	//房源信息
	
	/** 二手房列表 - 搜索 - 搜索结果数量 */
	public static final String S_COUNT = "//div[@class='totalnum']/strong";
	
	/** 二手房列表 - 搜索 - 房源分页 */
	public static final String PAGE_COUNT = "//div[@class='current']";
	
	//*[@id='content']/div[5]/div[1]/div[1]/div[1]/div[2]/strong
	
	/** 二手房列表 - 搜索 - 第一页房源数量 */
	public static final String LIST_COUNT = "//ol[@id='list_body']/li";

	/** 二手房列表 - 搜索 - 搜索无结果 */
	public static final String NO_FOUND = "//div[@class='search-nofound']";
	
	/** 二手房列表 - 第一套房源 */
	public static final String FIRSTPROP = "id^prop_1";
	
	/** 二手房列表 - 房源 - 多图标签*/
	public static final String getTagMore(int val){
		String locater= "//div[@id='prop_"+ val +"']/div[2]/h4/img";
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
	
	

	/** 二手房列表 - 搜索结果 - 房源 - 高亮小区名 */
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

	/** 二手房列表 - 房源 - 急售、新推、个人图片 */
	public static final String getTags(int val) {
		String locater = "//div[@id='prop_" + val + "']/div[4]/img";
		return locater;
	}

}
