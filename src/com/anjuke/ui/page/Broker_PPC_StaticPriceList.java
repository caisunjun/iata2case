package com.anjuke.ui.page;

/**
 * 中国网络经纪人 - 定价推广相关页面 http://my.anjuke.com/user/ppc/staticpricelist/W0QQactZsale
 * 
 * @author grayhu
 * */

public class Broker_PPC_StaticPriceList {
	
	
	
	/**
	 * 定价 - 房源库 - 根据房源ID进行房源定价推广
	 * 
	 * @param val
	 *            房源ID
	 */
	public static final String SelProp(int val) {
		return "//span[@id='static_" + val + "']/../span[2]";
	}
	
	/**
	 * 定价 - 定价推广页 - 选择刚创建的计划
	 * 
	 * @param val
	 *            计划ID
	 */
	public static final String SelPlan(int val) {
		return "//*[@id='appointplan_" + val + "']";
	}

	/** 定价 - 定价推广页 - 确认推广按钮 */
	public static final String SelPlan = "//*[@class='btn-sure']";

	/**
	 * 定价 - 定价推广列表 - 获得定价价格
	 * 
	 * @param val
	 *            房源ID
	 */
	public static final String getPrice(int val) {
		return "//*[@id='col-price" + val + "']";
	}

	/**
	 * 定价 - 定价推广列表 - 获得今日点击
	 * 
	 * @param val
	 *            房源ID
	 */
	public static final String getPoint(int val) {
		return "//*[@id='col-point" + val + "']";
	}

	/**
	 * 定价 - 定价推广列表 - 获得今日已花费
	 * 
	 * @param val
	 *            计划名称
	 */
	public static final String getCost(int val) {
		return "//input[@defaultval='" + val + "']/../../../div[3]/em";
	}

	/** 定价 - 定价推广列表 - 验证房源在推广计划中
	 * 
	 * @param val
	 *            房源ID
	 */
	public static final String PlanProp(int val){
		 return "//*[@id='col-tit"+ val +"']/p[2]/span";
	}

}
