package com.anjuke.ui.page;

/**
 * 中国网络经纪人 - 房源库 http://my.anjuke.com/user/ppc/brokerpropmanage/W0QQactZsaleQQggZ1
 * 
 * @author ccyang
 * Last update time 2012-8-7 16:00
 * */

public class Broker_PPC_PropmanageSale {
	
	/** 房源库 - 还可发布房源数 */
	public static final String PropRest = "//div[@class='balance-infos-box-new']/p/strong";
	/** 房源库 - 已有房源数 */
	public static final String PropNow = "//div[@class='balance-infos-box-new']/p/span[2]/strong";
	/** 房源库 - 发布出售房源 */
	public static final String SendSale = "//input[@id='send-housing-sale']";
	
	/** 房源库 - 编辑第val套房源 */
	public static final String EditProp(int val){
		return "//table[@id='datatable']/tbody/tr["+val+"]/td[6]/div/p[1]/a[1]";	
	}
	
	/** 房源库 - 单条房源定价推广链接*/
	public static final String PropPricing = "//*[@id='datatable']/tbody/tr[1]//p[2]/a[2]/span[2]";
		
	/** 房源库 - 单条房源竞价推广链接*/
	public static final String PropBidding = "//*[@id='datatable']/tbody/tr[1]//p[2]/a[2]/span[2]";

	
	
}
