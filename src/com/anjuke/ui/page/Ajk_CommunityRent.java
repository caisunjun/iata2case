package com.anjuke.ui.page;

/**
 * 安居客 - 小区租房页http://shanghai.anjuke.com/community/props/rent/1234**
 * 租房合并后，同时存放好租小区页面控件  http://shanghai.haozu.com/compound/broker/W0QQcZ2971
 * @author jchen
 * */

public class Ajk_CommunityRent {
	
	/** 小区租房页 - 筛选条件 - 房型（val为为左起第几个项）*/
	public static final String FliterHouseByVal(int val)
	{
		 return "//div[@class='condition']/dl[1]/dd/a["+val+"]";
	}
	/** 小区租房页 - 筛选条件 - 租金（val为为左起第几个项）*/
	public static final String FliterPriceByVal(int val)
	{
		 return "//div[@class='condition']/dl[2]/dd/a["+val+"]";
	}
	/** 小区租房页 - 筛选条件 - 装修（val为为左起第几个项）*/
	public static final String FliterRenovationByVal(int val)
	{
		 return "//div[@class='condition']/dl[3]/dd/a["+val+"]";
	}
	/** 小区租房页 - 房源套数*/
	public static final String PROPCOUNT = "//ul[@id='filter_order']/li[1]/strong";
	/** 小区租房页 - 页码：1/33*/
	public static final String PageNum = "//div[@class='current']";
	/** 小区租房页 - 下一页（上方页码旁）*/
	public static final String NextPageUP = "//div[@class='pagelink nextpage']";
	
	/*   房源列表     */
	
	/** 小区租房页 - 房源列表 - 第val套房源的标题*/
	public static final String PropName(int val)
	{
		return "//a[@id='prop_name_qt_"+val+"']";
	}
	
	/** 小区租房页 - 房源列表 - 第val套房源的房型（x室x厅）*/
	public static final String PropHouse(int val)
	{
		return "//div[@id='prop_model_qt_"+val+"']/span";
	}
	
	/** 小区租房页 - 房源列表 - 第val套房源的面积*/
	public static final String PropRenovation(int val)
	{
		return "//div[@id='prop_fit_qt_"+val+"']";
	}
	
	/** 小区租房页 - 房源列表 - 第val套房源的租金*/
	public static final String PropPrice(int val)
	{
		return "//div[@id='prop_price_qt_"+val+"']/strong";
	}
	
	/** 好租小区租房页 - 小区名（非点击用途）*/
	public static final String COMMTITLE = "id^commtitle";
}
