package com.anjuke.ui.page;

/**
 * 安居客 - 小区二手房页http://shanghai.anjuke.com/community/props/sale/1234**
 * 
 * @author ccyang
 * */

public class Ajk_CommunitySale {
	
	/** 小区二手房页 - 筛选条件 - 房型（val为为左起第几个项）*/
	public static final String FliterHouseByVal(int val)
	{
		 return "//div[@class='condition']/dl[1]/dd/a["+val+"]";
	}
	/** 小区二手房页 - 筛选条件 - 售价（val为为左起第几个项）*/
	public static final String FliterPriceByVal(int val)
	{
		 return "//div[@class='condition']/dl[2]/dd/a["+val+"]";
	}
	/** 小区二手房页 - 筛选条件 - 面积（val为为左起第几个项）*/
	public static final String FliterAreaByVal(int val)
	{
		 return "//div[@class='condition']/dl[3]/dd/a["+val+"]";
	}
	/** 小区二手房页 - 房源套数*/
	public static final String PROPCOUNT = "//ul[@id='filter_order']/li[1]/strong";
	/** 小区二手房页 - 页码：1/33*/
	public static final String PageNum = "//div[@class='current']";
	/** 小区二手房页 - 下一页（上方页码旁）*/
	public static final String NextPageUP = "//div[@class='pagelink nextpage']";
	
	/*   房源列表     */
	
	/** 小区二手房页 - 房源列表 - 第val套房源的标题*/
	public static final String PropName(int val)
	{
		return "//a[@id='prop_name_qt_"+val+"']";
	}
	
	/** 小区二手房页 - 房源列表 - 第val套房源的房型（x室x厅）*/
	public static final String PropHouse(int val)
	{
		return "//div[@id='prop_model_qt_"+val+"']/span";
	}
	
	/** 小区二手房页 - 房源列表 - 第val套房源的面积*/
	public static final String PropArea(int val)
	{
		return "//div[@id='prop_area_qt_"+val+"']";
	}
	
	/** 小区二手房页 - 房源列表 - 第val套房源的总价*/
	public static final String PropPrice(int val)
	{
		return "//div[@id='prop_total_qt_"+val+"']/strong";
	}
}
