package com.anjuke.ui.page;

/**
 * 用户中心 - 收藏的小区=关注的小区
 * http://my.anjuke.com/member/concerned/
 * 
 * @author ccyang
 * */

public class Ajk_MemberConcerned {
	/** 用户中心 - 收藏的小区 - 无任何收藏记录 */
	public static final String NoComm = "//div[@class='noresult-tips']";
	
	/** 用户中心 - 收藏的小区 - 关注小区第一条记录的小区名 */
	public static final String FirstCommName = "//div[@class='p2974']/div[2]/div[@class='propinfo']/p[1]/a";
	/** 用户中心 - 收藏的小区 - 关注小区第一条记录的价格 */
	public static final String FirstCommPrice = "//div[@class='p2974']/div[2]/div[@class='saleprice']/em";
	/** 用户中心 - 收藏的小区 - 关注小区第一条记录的邮件订阅状态 */
	public static final String FirstCommMessage = "//div[@class='p2974']/div[2]/div[@class='message']";
	
	/** 用户中心 - 收藏的小区 - 当前页面收藏小区数（请用getElementCount） */
	public static final String CommCount = "//div[@class='box']/div[3]/p/a";
	/** 用户中心 - 收藏的小区 - 删除收藏小区 */
	public static final String DeleteComm = "//div[@title='删除']";
	/** 用户中心 - 收藏的小区 - 确认删除收藏小区 */
	public static final String DeleteCommCon = "//*[@id='del_box_0_apf_id_1']/div/p[3]/input[2]";
	
	/** 用户中心 - 收藏的小区 - 关注小区第N条记录的小区名 */
	public static final String getNCommName(int i){
		return "//div[@class='p2974']/div[@class='box']["+i+"]/div[@class='propinfo']/p[1]/a";
	}
}
