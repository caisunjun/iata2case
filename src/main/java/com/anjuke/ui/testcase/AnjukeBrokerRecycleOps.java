package com.anjuke.ui.testcase;


import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;


/**
 * 该测试用例主要用来做安居客网站经纪人回收站操作，步骤简介如下：
 * 1.以经纪人账户登录网站
 * 2.进入经纪人管理页面
 * 3.选中一套房源并进行删除房源操作
 * 4.进入回收站页面选中删除的房源（通过房源ID来筛选）并选择永久删除
 * 5.或者选择还原房源到店铺
 * @author Williamhu
 * @time  2012/05/08
 * 
 */

public class AnjukeBrokerRecycleOps {
	private Browser bs = null;
	
	@BeforeMethod
	public void startUp(){
		Report.G_CASECONTENT = "经纪人删除房源";
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
		System.out.println("***AnjukeBrokerRecycleOps is done***");
	}
	//(timeOut = 120000)
	@Test
	public void testStart() throws InterruptedException{

		//设定一个标志id，如果id为1则表示永久删除选中房源，id为0表示还原到店铺；
		int id = 1 ;   

		//默认标志位为1，则表示取消永久删除选中房源
		brokerRecycleOps(1);

		//标志位设为0，取消还原选中房源至店铺
		id = 0;
		brokerRecycleOps(id);
	}

	private void brokerRecycleOps(int id){
		//以经纪人账号登陆anjuke网站
		if(id == 1 ){
			bs.get("http://my.anjuke.com/my/login?history=aHR0cDovL3NoYW5naGFpLmFuanVrZS5jb20v");
			new PublicProcess().dologin(bs, "test1", "111111");
		}else{
			bs.get("http://shanghai.anjuke.com");
		}
		
		//点击主页右上角的我的经纪人链接
		bs.check(Init.G_objMap.get("cityhomepage_login_broker_username_link"), 5);
		bs.get("http://my.anjuke.com/user/brokerpropmanage/W0QQactZsale#proptop");
		/*bs.click(Init.G_objMap.get("cityhomepage_login_broker_username_link"), "点击我的经纪人目录"); 
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale"), "点击管理出售链接");*/
		
		int pageCount;	
		String content=bs.getText("//div[@class='list_top']/dl/dt/a", "获取房源总数", 10);
		int a = content.indexOf("("); 
		String contentF = content.substring(a+1, a+2); 
		int totalCount = Integer.parseInt(contentF);
		
		if(totalCount > 20){
			pageCount = 20;
		}else{
			pageCount = totalCount;
		}				

		if(pageCount==0){
			Report.writeHTMLLog("经纪人回收站操作", "当前小区房源总数为0，无房源可操作", Report.PASS, "");
			return;
		}

		//选中第一套的房源	
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_first_house_checkbox_IE8"), "选中第1套房源");
		
		//点击选中房源的小区链接
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_first_house_link_IE8") , "点击选中的房源链接");
		
		bs.switchWindo(2);

		//获取选取房源的编号
		bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_houseID_IE8"),20);
		String strHouseID = bs.getText(Init.G_objMap.get("anjuke_broke_manage_sale_houseID_IE8"), "截取房源编号字符串");

		String houseID = PublicProcess.splitString(strHouseID, "：");

		Report.writeHTMLLog("回收站操作", "选中房源的ID号为：".concat(houseID), Report.PASS, "");

		//不关闭当前操作的页面交给框架处理
		bs.close();
		bs.switchWindo(1);

		//点击删除按钮
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_delete_btn_IE8"),"点击删除按钮");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//判断是否出现成功删除标志
		new PublicProcess().checkLocator(bs,Init.G_objMap.get("anjuke_broke_manage_sale_rec_succ_prompt_IE8"), "回收站操作", "删除选中房源成功提示是否正确显示");
		//点击回收站链接
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_recycle_tab_IE8"), "点击回收站标签链接");

		//用指定的房源ID号搜索出相应的房源
		bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_houseID_search_input"));
		bs.type(Init.G_objMap.get("anjuke_broke_manage_sale_houseID_search_input"), houseID, "在房源编号搜索框中输入推荐的房源编号");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_houseID_search_btn"), "点击搜索按钮");
		if(!bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_first_house_link_IE8"),10)){
			String ps = bs.printScreen();
			Report.writeHTMLLog("回收站操作", "未能搜索到任何房源，用例失败退出", Report.FAIL, ps);
			return;		
		}
		Report.writeHTMLLog("回收站操作", "正确搜索到房源", Report.PASS, "" );

		//若干id为 1，表示要执行永久删除房源操作
		if(id == 1){
			//选中第一套房源
			bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_first_house_checkbox_IE8"),"选中第一套房源");
			
			//永久删除选中房源
			bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_del_eternal_IE8"),"永久删除选中房源");
			bs.doAlert("确定");

			//判断是否出现成功永久删除标志
			new PublicProcess().checkLocator(bs, Init.G_objMap.get("anjuke_broke_manage_sale_rec_succ_prompt_IE8"), "回收站操作", "永久删除房源成功提示是否正确显示");

		}else{  //否则为还原房源至店铺操作
			
			//选中当前页全部房源还原至店铺
			bs.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_selectall_IE8"), "选中当前页全部房源");
			
			//还原选中房源
			bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_recycle_restore_btn_IE8"),"还原选中房源至店铺");

			//判断是否出现成功还原删除房源标志
			new PublicProcess().checkLocator(bs, Init.G_objMap.get("anjuke_broke_manage_sale_rec_succ_prompt_IE8"), "回收站操作", "还原删除房源成功提示是否正确显示");
			
			//case操作完毕后退出当前登陆账户
			new PublicProcess().logOut(bs);		}

		//点击”回收站“标签链接

//	    bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_recycle_tab"), "点击回收站标签链接");		


//		//点击清空回收站链接
//	    if(bs.check(Init.G_objMap.get("anjuke_broke_manage_sale_del_all"))){
//	    	bs.click(Init.G_objMap.get("anjuke_broke_manage_sale_del_all"),"点击清空回收站");//			
//			bs.doAlert("确定");//
//			//判断是否出现清空回收站成功标志
//			PublicProcess.checkLocator(bs,Init.G_objMap.get("anjuke_broke_manage_sale_rec_succ_prompt"), "回收站操作", "清空回收站操作成功提示正确显示");
//	    }

	}

}



