package com.anjuke.ui.testcase;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;

import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;


/**
 * 该用例主要用来做安居客网站经纪人推荐房源操作，步骤简介如下：
 * 1.以经纪人账户登录网站
 * 2.进入经纪人管理页面（出售和出租)
 * 3.分别进入推荐房源，取消推荐房源tab页，并记录相关页面房源总数
 * 4.在取消推荐房源tab页，选中一套房源并选择推荐
 * 5.进入推荐房源tab页，通过房源ID搜索出相应的房源，检查是否能找到取消推荐的房源，并对比推荐前后各tab页的数量是否正确
 * 6.最后进行删除房源操作
 * @UpdateAuthor Williamhu
 * @last updatetime 2012/05/08
 */


public class AnjukeBrokerRecommendHouse {

	private Browser bs = null;
    private Boolean flag = false;  		//设定一个标志位，如果flag为true则表示推荐出租房源，否则为推荐购买房源
	
	@BeforeMethod
	public void startUp(){
		bs  = FactoryBrowser.factoryBrowser();	
	}

	@AfterMethod
	public void teardown(){
		bs.quit();
		bs = null;	
	}
    @SuppressWarnings("deprecation")
	@Configuration(afterTestClass = true)
	public void doBeforeTests() {
		System.out.println("***AnjukeRecommendHouse is done***");
	}

	//(timeOut = 250000)
	@Test
	public void testRecommendBuyHouse() throws InterruptedException{
		//Report.setTCNameLog("推荐购买房源-- AnjukeRecommendBuyHouse --williamhu");		
		recommendHouse(flag);
	}

	@Test
	public void testRecommendRentHouse() throws InterruptedException {
		//Report.setTCNameLog("推荐出租房源-- AnjukeRecommendRentHouse --williamhu");
		flag = true;	  	
		recommendHouse(flag);		
	}

	/*根据Boolean类型的标志位来决定推荐的是出租房源还是购买房源
	 *bool为true代表出租房源，为false代表购买房源	  
	 */
	public void recommendHouse(Boolean bool){
		int notRecommendCountBefore = 0;    //未推荐之前非推荐房源数量
		int recommendCountBefore = 0;       //未推荐之前推荐房源数量
		int notRecommendCountAfter = 0;     //推荐后未推荐房源数量
		int recommendCountAfter = 0;        //推荐之后推荐房源数量
		String strHouseID = "";                  //房源编号字符串
		String houseID = "";                      //房源编号 
		String communityName = "";                //推荐房源小区名
		String title = "";                        //推荐房源信息标题

		//以经纪人账号登陆anjuke网站
		if(!bool){
			//删除当前浏览器所有Cookie
//			bs.deleteAllCookies();			
		}		

		//For anjuke daily
		PublicProcess.dologin(bs, "cdtest", "anjukeqa");
        //For other
//		PublicProcess.dologin(bs, "ajk_sh", "anjukeqa");

		//点击主页右上角我的经纪人目录链接
		bs.check(Init.G_objMap.get("cityhomepage_login_broker_username_link"), 5);

		bs.click(Init.G_objMap.get("cityhomepage_login_broker_username_link"), "选中我的经纪人目录");   

		//根据bool的取值决定点击管理出售链接还是管理出租链接
		if(!bool){
			bs.click(Init.G_objMap.get("anjuke_broke_manage_sale"), "点击管理出售链接");
		}else{
			bs.click(Init.G_objMap.get("anjuke_broke_manage_rent"),"点击管理出租链接");
		}

		//点击推荐房源链接
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_rec_tab"), "点击推荐房源链接");

		if(bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_total"))){
			recommendCountBefore = Integer.parseInt(bs.getText(
					Init.G_objMap.get("anjuke_broke_manage_sale_total"), "获取推荐房源总数"));
		}else{
			recommendCountBefore = 0;
			Report.writeHTMLLog("推荐房源", "当前推荐房源总数为0", Report.PASS, "");
		}

		//点击非推荐链接
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_Nrec_tab"), "点击非推荐链接");

		if(bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_total"))){
			notRecommendCountBefore = Integer.parseInt(bs.getText(
					Init.G_objMap.get("anjuke_broke_manage_sale_total"), "获取非推荐房源总数"));
		}else{                              
			//如果取不到非推荐房源数，则表示非推荐房源数位0，无房源可推荐，case失败退出.
			notRecommendCountBefore = 0;
			String ps = bs.printScreen();
			Report.writeHTMLLog("推荐房源", "非推荐房源总数为0，无房源可推荐", Report.PASS, ps);   
			return;
		}

		int pageCount;
		int totalCount;
		if(bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_total"))){
			totalCount = Integer.parseInt(bs.getText(Init.G_objMap.get("anjuke_broke_manage_sale_total"), "获取房源总数"));			
		}else{
			totalCount = 0;
		}

		if (totalCount > 20) {
			pageCount = 20;
		} else {
			pageCount = totalCount;
		}	

		//根据每条记录是否显示"锁"图标来判断该房源是否可以被推荐//tbody/tr[" + i + "]/td[2]/dl/dt/img
		int i =0;
		for(i=1;i<=pageCount;i++){
			if(!bs.check("//tr[" + i + "][@id='apf_id_7']/td[2]/dl/dt/img")&&
					!bs.check("//p[@id='proinfo_"+ (i-1) +"']/a")){
				Report.writeHTMLLog("推荐房源", "第"+i+"套房源可以被推荐", Report.PASS, "");
				break;
			}
		}
		
		if(i>pageCount){
			String ps = bs.printScreen();
			Report.writeHTMLLog("推荐房源", "当前所有房源都上锁了不可被推荐",Report.DONE , ps);
			return;
		}

		//选中该条房源记录
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_checkbox_IE8") + (i-1), "选中第" + i + "套房源");

		communityName = bs.getText(Init.G_objMap.get("anjuke_broke_manage_sale_house_link_IE8") + (i-1), "获取推荐房源的小区名");
		title = bs.getText("//tr[" + i + "][@id='apf_id_7']/td[2]/p[2]","获取推荐房源信息标题");  ////tbody/tr[" + i + "]/td[2]/p[2]

		//点击选中的房源链接
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_house_link_IE8") + (i-1), "点击选中的房源链接");

		bs.switchWindo(2);
		  
		bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_houseID"), 5);
		if(bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_houseID"),5)){
			strHouseID = bs.getText(Init.G_objMap.get("anjuke_broke_manage_sale_houseID"), "截取房源编号字符串");
		}


		houseID = PublicProcess.splitString(strHouseID, "：");

		Report.writeHTMLLog("推荐房源", "推荐房源的ID号为：".concat(houseID), Report.PASS, "");

		bs.close();
		bs.switchWindo(1);		
		

		if(bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_recommend_btn_IE8"),5)){
			//点击推荐房源按钮
			bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_recommend_btn_IE8"), "点击推荐房源按钮");	
		}
	

		//判断是否出现成功推荐标志
		if(bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_rec_succ_prompt_IE8"))) {
			Report.writeHTMLLog("推荐房源", "推荐房源成功提示显示", Report.PASS,"" );
		}else{
			String ps = bs.printScreen();
			Report.writeHTMLLog("推荐房源", "推荐房源成功提示未显示", Report.FAIL, ps);
		}


		//推荐成功后非推荐房源总数
		if(bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_total"))){
			notRecommendCountAfter = Integer.parseInt(bs.getText(
					Init.G_objMap.get("anjuke_broke_manage_sale_total"), "获取非推荐房源总数"));
		}else{                              
			//如果取不到非推荐房源数，则表示非推荐房源数位0，无房源可推荐，case失败退出.
			notRecommendCountAfter = 0;
			Report.writeHTMLLog("推荐房源", "非推荐房源总数为0", Report.PASS, ""); 
		}

		//点击推荐房源链接
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_rec_tab"), "点击推荐房源链接");
		//推荐成功后推荐房源总数
		if(bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_total"))){
			recommendCountAfter = Integer.parseInt(bs.getText(
					Init.G_objMap.get("anjuke_broke_manage_sale_total"), "获取推荐房源总数"));
		}else{                              
			//如果取不到非推荐房源数，则表示非推荐房源数位0，无房源可推荐，case失败退出.
			notRecommendCountAfter = 0;
			Report.writeHTMLLog("推荐房源", "推荐房源总数为0，无房源可推荐", Report.PASS, ""); 
		}		


		//如果推荐前的推荐房源数等于推荐后推荐房源数减一，而且推荐前非推荐房源数等于推荐后非推荐房源数加一，则表示推荐成功
		if(recommendCountBefore == recommendCountAfter -1 && notRecommendCountBefore == notRecommendCountAfter + 1){
			Report.writeHTMLLog("推荐房源", "验证房源数量成功", Report.PASS,"" );
		}  else{
			String ps = bs.printScreen();
			Report.writeHTMLLog("推荐房源", "验证房源数量失败", Report.FAIL, ps);
		}

		//点击推荐房源链接
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_rec_tab"), "点击推荐房源链接");
		bs.type(Init.G_objMap.get("anjuke_broke_manage_sale_houseID_search_input"), houseID, "在房源编号搜索框中输入推荐的房源编号");
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_houseID_search_btn"), "点击搜索按钮");


		if(!bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_first_house_link_IE8"))){
			String ps = bs.printScreen();
			Report.writeHTMLLog("推荐房源", "未能搜索到任何房源，用例失败退出", Report.FAIL, ps);
			return;
		}

		Report.writeHTMLLog("推荐房源", "正确搜索到房源", Report.PASS, "" );
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_first_house_link_IE8"),"点击搜索到的第一套房源链接");
		bs.switchWindo(2);

		bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_comm_name"), 10);

		if (!bool) {
			if (communityName.equals(bs.getText(Init.G_objMap.get("anjuke_broke_manage_sale_comm_name"), "获取打开房源页面的小区名"))
					&& title.equals(bs.getText(Init.G_objMap.get("anjuke_broke_manage_sale_house_info_title"), "获取打开房源页面的标题"))) {
				Report.writeHTMLLog("取消推荐房源", "正确匹配房源小区名和房源标题", Report.PASS, "");
			} else {
				String ps = bs.printScreen();
				Report.writeHTMLLog("取消推荐房源", "未能匹配房源小区名和房源标题", Report.FAIL, ps);
			}
		}
		else{
			if (communityName.equals(bs.getText("html/body/div[3]/div[2]/div[1]/div/div[2]/ul/li[9]/a[1]", "获取打开房源页面的小区名"))
					&& title.equals(bs.getText("//h1[@class='title f16 txt_c']", "获取打开房源页面的标题"))) {
				Report.writeHTMLLog("取消推荐房源", "正确匹配房源小区名和房源标题", Report.PASS, "");
			} else {
				String ps = bs.printScreen();
				Report.writeHTMLLog("取消推荐房源", "未能匹配房源小区名和房源标题", Report.FAIL, ps);
			}
		}

		bs.close();
		bs.switchWindo(1);

//		是不是应该把删除房源的操作放在删除房源的脚本里？
//		bs.type(Init.G_objMap.get("anjuke_broke_manage_sale_houseID_search_input"), houseID, "在房源编号搜索框中输入推荐的房源编号");
//
//		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_houseID_search_btn"), "点击搜索按钮");
//
//		Report.writeHTMLLog("推荐房源", "正确搜索到房源", Report.PASS, "" );
//		//选中第一套房源
//		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_first_house_checkbox_IE8"),"选中第一套房源");
//
//		//点击删除按钮
//		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_delete_btn_IE8"),"点击删除按钮");
//
//		//判断是否出现成功删除标志
//		if(bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_rec_succ_prompt_IE8"))) {
//			Report.writeHTMLLog("推荐房源", "删除推荐房源成功提示正确显示", Report.PASS,"" );
//		}else{
//			String ps = bs.printScreen();
//			Report.writeHTMLLog("推荐房源", "删除推荐房源成功提示未正确显示", Report.FAIL, ps);
//		}
	}

}



