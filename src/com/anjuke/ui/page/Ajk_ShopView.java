package com.anjuke.ui.page;

/**
* @Todo http://shanghai.anjuke.com/shop/view/147468
* @author fjzhang
* @since 2012-8-15
* @file Broker_shopview.java
*
*/
public class Ajk_ShopView {
	
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
	
	/**店铺首页 - 经纪人姓名*/
    public static final String BROKERNAME = "//div[@class='left_pad']/h4";
    
	/**店铺首页 - 经纪人姓名 ie6版*/
    public static final String BROKERNAMEForIe6 = "//*[@id='content']/div[1]/div[1]/div[1]/div[1]/div[1]/h4";

    /**店铺首页 - 最熟悉的板块     */
    public static final String KNOWNAREA = "//*[@id='content']/div[1]/div[1]/div[1]/div[1]/div[2]/dl/dd[2]";
    
    /**店铺首页 - 最熟悉的小区     */
    public static final String KNOWNCOMM = "//*[@id='content']/div[1]/div[1]/div[1]/div[1]/div[2]/dl/dd[1]";
    
    /**店铺首页 - 业务特长     */
    public static final String EXPERTISE = "//*[@id='content']/div[1]/div[1]/div[1]/div[1]/div[2]/dl/dd[3]";
    
    /**店铺首页 - 右侧栏服务等级  -  全店铺通用 */
    public static final String Star = "//p[@class='grade']/span[2]";
    
    /**店铺首页 - 自我介绍     */
    public static final String SELFINTRO = "//div[@id='content']/div[1]/div[2]/div[1]/div/div[1]/p";

    /**卖房宣言     */
    public static final String SLOGAN = "//div[@id='content']/div[1]/div[1]/div[1]/div[1]/div[2]/h4";


   /*
    * 
    *验证店铺管理中输入的基本资料和经济人店铺中显示的是否一致
    *@Author chuzhaoqin
    */
   /**出生地--省-市*/
   public static final String PROAndCity="//*[@id='s1']";
   
   /**--出生地--区---*/
   public static final String  Region="//*[@id='s2']";
   
   /**--出生地--公开/不公开---*/
   public static final String  Region1="//*[@id='birplcprivilege1']";
   public static final String  Region2="//*[@id='birplcprivilege2']";
   
   /**--年龄--公开/不公开---*/
   public static final String  Age1="//*[@id='ageprivilege1']";
   public static final String  Age2="//*[@id='ageprivilege2']";
   
   /**--生肖---*/
   public static final String  shenXiao="//*[@id='birthanimal']";
   public static final String  shenXiao1="//*[@id='biraniprivilege1']";
   public static final String  shenXiao2="//*[@id='biraniprivilege2']";
   
   /**--星座---*/
   public static final String  xingzuo="//*[@id='constellation']";
   public static final String  xingzuo1="//*[@id='constlprivilege1']";
   public static final String  xingzuo2="//*[@id='constlprivilege2']";
   
   /**--确认保存--*/
   public static  final String  submit="//*[@id='saveTag']";
  

   
}
