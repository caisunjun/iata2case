package com.anjuke.ui.page;


/**
* @Todo http://my.anjuke.com/user/broker/areas
* @author fjzhang
* @since 2012-8-15
* @file Broker_areas.java
*	也包括http://my.anjuke.com/user/broker/expertise###
*/
public class Broker_areas {

    /**
     * 最熟悉的区域板块列表
     */
    public static final String AREA1 = "//div[@id='selarea']/select";
    public static final String  BLOCK1= "//div[@id='selblock']/select";
    public static final String AREA2 = "//div[@id='selarea2']/select";
    public static final String BLOCK2 = "//div[@id='selblock2']/select";

    /**
     * 最熟悉的小区1
     */
    public static final String COMM1 = "id^commName1";
    /**
     * 最熟悉的小区2
     */
    public static final String COMM2 = "id^commName2";
    /**
     * 最熟悉的小区3
     */
    public static final String COMM3 = "id^commName3";

    /**
     * 确认保存
     */
    public static final String OKENTER = "id^areasSubmit";

    /**
     * ajax的弹窗口，熟悉的小区
     */
    public static final String COMMDIV = "/html/body/div/div[2]";
    public static final String IFRAME1= "id^ifrCommunityList1";
    public static final String IFRAME2 = "id^ifrCommunityList2";
    public static final String IFRAME3 = "id^ifrCommunityList3";
    /**
     * 保存成功
     */
    public static final String SAVEOK = "//*[@id='userbox']/ul";

    /**
     * http://my.anjuke.com/user/broker/expertise###
     *执业特长
     */
    public static final String JOBS = "html/body/div[2]/div[1]/div[1]/div[2]/div[1]/ul";
    /**
     * 待选择特长
     */
    public static final String SELECTJOBS = "id^foreach0";
    /**
     * 职业特长 保存按钮
     */
    public static final String OKBUTTON = "id^saveTag";
}

