package com.anjuke.ui.page;

/**
 * 安居客 - 经纪人服务排行榜 http://shanghai.anjuke.com/lastgetcomment
 * 
 * @author ccyang
 * */

public class Ajk_LastGetComment {
	
	
	/** 经纪人服务排行榜 - 区域筛选 - （请传入区域名称，长宁、朝阳··）*/
	public static final String Area(String areaName) 
	{
		return "//a[@title='"+areaName+"']";
	}
	
	/** 经纪人服务排行榜 - 列表中第一个经纪人的服务等级(钻石/皇冠)*/
	public static final String FirstBrokerStar = "//ul[@class='AJKBTMLM_blist clearfix']/li[1]/div[2]/dl[2]/dd/span";
	/** 经纪人服务排行榜 - 列表中第一个经纪人的头像*/
	public static final String FirstBrokerImg = "//ul[@class='AJKBTMLM_blist clearfix']/li[1]/div[1]/a/img";
	/** 经纪人服务排行榜 - 列表中第一个经纪人的服务等级分*/
	public static final String FirstBrokerScore = "//ul[@class='AJKBTMLM_blist clearfix']/li[1]/div[3]/p[2]/strong";

}
