package com.anjuke.ui.page;

/**
* @Todo 店铺管理
* @author fjzhang
* @since 2012-8-14
* @file Broker_profile.java
*http://my.anjuke.com/user/broker/profile
*
*包括http://my.anjuke.com/user/broker/introduction 自我介绍
*/
public class Broker_profile {

    /**
     * 出生地 省，
      */
    public static final String PROVINCE = "id^s1";
    /**
     * 出生地 市
     */
    public static final String  STADE= "id^s2";

    /**
     * 生肖
     */
    public static final String  LUNAR= "id^birthanimal";
    /**
     * 星座
     */
    public static final String CONSTELLATION = "id^constellation";
    /**
     * 喜欢的书
     */
    public static final String LIKEBOOK = "id^showTag_book";
    /**
     * 选择喜欢的书
     */
    public static final String SELECTLIKEBOOK = "//table[@id='hotTag_book']/tbody/tr[2]/td";
    /**
     * 喜欢的美食
     */
    public static final String LIKEFOOD = "id^showTag_food";
    /**
     * 选择喜欢的美食
     */
    public static final String SELECTLIKEFOOD = "//table[@id='hotTag_food']/tbody/tr[2]/td";
    /**
     * 娱乐活动列表
     */
    public static final String  RECRETIONAL= "id^showTag_acti";
    /**
     * 待选择的娱乐活动
     */
    public static final String SELECTRECRETIONAL = "//table[@id='hotTag_acti']/tbody/tr[2]/td";
    /**
     * 确认保存按钮
     */
    public static final String SAVE = "id^saveTag";
    /**
     * 保存成功 提示
     */
    public static final String SAVETEXT = "id^operatesuccess";

    /**
     * 我的店铺链接
     */
    public static final String MYSHOP = "html/body/div[1]/div[2]/a[3]";
     /**
      * 自我介绍  卖房宣言
      */
    public static final String  SELLHOUSE= "id^shopnotic";
    /**
     * 自我介绍 详细介绍
     */
    public static final String  SELFINTRO= "id^userintro";
    /**
     * 自我介绍 保存
     */
    public static final String OKENTER = "id^submit_apf_id_1";
}

