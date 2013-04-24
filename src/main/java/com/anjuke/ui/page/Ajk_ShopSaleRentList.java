package com.anjuke.ui.page;

/**
* @Todo http://shanghai.anjuke.com/shop/view/147468
* @author fjzhang
* @since 2012-8-15
* @file Broker_shopview.java
*
*/
public class Ajk_ShopSaleRentList {

	
    /**店铺二手房、租房列表 - 右侧栏服务等级  -  全店铺通用 */
    public static final String Star = "//p[@class='grade']/span[2]";
    /**店铺二手房、租房列表 - 无房源提示 */
    public static final String NoProp = "//div[@class='none_content']";
    /**店铺二手房、租房列表 - 第一套房源的名称（链接） */
    public static final String FirstPropName = "//*[@class='list']/li[1]/ul/li[2]/h4/a";
    
	
	/**店铺通用 - 导航  - 首页tab*/
    public static final String ViewTab = "//li[@class='tab1']/a";
	/**店铺通用 - 导航  - 二手房tab*/
    public static final String SaleListTab = "//li[@class='tab2']/a";
	/**店铺通用 - 导航  - 租房tab*/
    public static final String RentListTab = "//li[@class='tab3']/a";
	/**店铺通用 - 导航  - 自我介绍tab*/
    public static final String ProfileTab = "//li[@class='tab4']/a";
	/**店铺通用 - 导航  - 问答tab*/
    public static final String QuestionTab = "//li[@class='tab7']/a";
	/**店铺通用 - 导航  - 评价tab*/
    public static final String UfsTab = "//li[@class='tab8']/a";
    
}

