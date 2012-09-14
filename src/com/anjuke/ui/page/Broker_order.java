package com.anjuke.ui.page;


/**
* @Todo 经纪人购买套餐
* @author fjzhang
* @since 2012-8-8
* @file Broker_order.java
*http://my.anjuke.com/user/payment/order/selectcombo/
*/
public class Broker_order {

    /**
     * 套餐列表
     */
    public static final String  COMBOLIST= "id^combolist";
    /**
     *第一个套餐 选项
     */
    public static final String COMBOFIRST = "//input[@id='radio']";

    /**
     * 下一步 按钮
     */
    public static final String XIAYIBU = "id^button";

    /**
     *  确认按钮
     */
    public static final String QUEREN = "id^imageField";
    /**
     * 付款最后一步 提示文本  “订单已提交，请尽快付款”
     */
    public static final String OKTEXT = "//div[@id='paymethod']/div[3]/div/div[2]";
}

