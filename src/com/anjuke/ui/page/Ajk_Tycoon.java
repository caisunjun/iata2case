package com.anjuke.ui.page;

/**
 * 安居客 - 经纪人列表 http://shanghai.anjuke.com/tycoon/
 * 
 * @author grayhu
 * */

public class Ajk_Tycoon {
	
	/** 经纪人列表 - 搜索 - 第一页经纪人数量 */
	public static final String LIST_COUNT = "//div[@class='tycoon_list']/ul/li";
	
	/** 经纪人列表 - 搜索 - 搜索无结果 */
	public static final String NO_FOUND = "//div[@class='tycoon_nolist']";
	
	/** 经纪人列表 - 筛选 - 是否无虚假反馈*/
	public static final String evaluation = "//*[@id='hasevaluation']";
	
	/** 经纪人列表 - 第一个经纪人 - 评价标签*/
	public static final String assess = "//*[@class='tycoon_list']/ul/li[1]/dl/dt/a[2]";
	
	/** 经纪人列表 - 筛选 - 是否有回答问题*/
	public static final String ask = "//*[@id='hasask']";
	
	/** 经纪人列表 - 第一个经纪人 - 回答问题数*/
	public static final String getQuestionNum(int i) {
		String question = "//*[@class='tycoon_list']/ul/li[1]/dl/dd[3]/a["+i+"]/span";
		return question;
	}
	
	/** 经纪人列表 - 第一个经纪人 - 头像*/
	public static final String headimg = "//*[@class='tycoon_list']/ul/li[1]/a/img";
	
	/** 经纪人列表 - 筛选 - 是否有生活照*/
	public static final String photo = "//*[@id='hasphotos']";
			
		
}
