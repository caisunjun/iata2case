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
 * 该测试用例主要完成安居客收藏房源操作，逻辑如下
 * 1、选择二手房列表页面，第一个房源
 * 2、判断该房源是不是爱房的房源，如果是选择下一条数据
 * 3、在房源详细页进行收藏操作
 * 4、提示收藏成功后，在房源列表页进行房源验证
 * 5、当房源数据超过10条时，删除房源数据
 * 6、判断房源列表当前页，是否存在多条相同的数据
 * @UpdateAuthor Gabrielgao
 * @last updatetime 2012-05-08 11:30
 * @UpdateAuthor ccyang
 * @last updatetime 2012-08-01 10:30
 * 列表页里经纪人名称模块，可能取到其他信息，需要将经纪人的名称取出来
 */
public class AnjukeCollectHouseResource {
	private Browser driver = null;
	private String firstdataTitle = null;
	private String releaseName;
	private String price;
	private String url;
	
	@BeforeMethod
	public void startUp(){
	driver = FactoryBrowser.factoryBrowser();
	}
	@AfterMethod
	public void tearDown(){
		driver.quit();
		driver=null;
	}
    @SuppressWarnings("deprecation")
	@Configuration(afterTestClass = true)
	public void doBeforeTests() {
		System.out.println("***AnjukeCollectHouseResource is done***");
	}
	//(timeOut = 250000)
	@Test
	public void collectHouse(){
		//Report.setTCNameLog("收藏房源-- AnjukeCollectHouseResource --williamhu");
		String brow = "";
		//从config.ini中取出browser
		brow = Init.G_config.get("browser");
		if(brow.contains("ie"))
		{
			//IE6兼容性
//			PublicProcess.logIn(driver, "小瓶盖001", "6634472", false, 0);
			PublicProcess.dologin(driver, "小瓶盖001", "6634472");
		}
		else
		{
			// 普通用户登录
//			driver.deleteAllCookies();
			String loginName = PublicProcess.logIn(driver, "小瓶盖001", "6634472", false, 0);
			// 判断用户是否登录成功
			if (!(loginName == null || loginName.equals(""))) {
				
				Report.writeHTMLLog("user login", "user login sucess,and name is: " + loginName, Report.DONE, "");
			} else {
				String ps = driver.printScreen();
				Report.writeHTMLLog("user login", "user login fail,and name is: " + loginName, Report.FAIL, ps);
			}
		}
		//访问二手房列表
		driver.check(Init.G_objMap.get("public_link_sale"),10);
		driver.click(Init.G_objMap.get("public_link_sale"), "访问二手房列表");
		//判断二手房房源列表数据是否为空
		if(!driver.check(Init.G_objMap.get("sale_text_no_found"))){	
			//访问第一条二手房房源记录
			int count = driver.getElementCount(Init.G_objMap.get("ajk_sale_houseList_data_count"));
			Report.writeHTMLLog("当前列表 页房源数量", "数量："+count, Report.DONE, "");
			for(int i = 1;i<=count;i++){
				String anjuke_salefirstdata_title = Init.G_objMap.get("anjuke_saleresultlist_firstdata")+i;
				String anjuke_salefirstdata_price = Init.G_objMap.get("anjuke_salefirstdata_price")+i+"']/strong";
				String anjuke_salefirstdata_releaseName = Init.G_objMap.get("anjuke_salefirstdata_releaseName")+i+"']/div[2]/p[2]";
				String anjuke_salefirstdata_img = Init.G_objMap.get("anjuke_salefirstdata_img") + i + "_a";
				url = driver.getAttribute(anjuke_salefirstdata_title, "href");
				if(!url.contains("aifang")){
					//获取第一条二手房记录
					firstdataTitle = driver.getText(anjuke_salefirstdata_title, "获取第一条合格的记录标题");
					price = driver.getText(anjuke_salefirstdata_price, "获取第一条合格的记录价格");
					//经纪人姓名的控件里可能出现其他内容
					releaseName = driver.getText(anjuke_salefirstdata_releaseName, "获取第一条合格的记录经纪人姓名").trim();
					String s = new String(releaseName);   
			        String a[] = s.split(" ");  
			        releaseName = a[0];
					driver.click(anjuke_salefirstdata_img, "访问第一条记录的图片");
					break;
				}
			}
			driver.switchWindo(2);
			//进行房源收藏操作
			if(driver.check(Init.G_objMap.get("anjuke_sale_detail_collect_IE6"),20)){
				driver.click(Init.G_objMap.get("anjuke_sale_detail_collect_IE6"), "点击收藏房源按钮");
			}else{
				//进行刷新页面操作，如果刷新后，按钮还是不出来，则退出
				driver.refresh();
				if(driver.check(Init.G_objMap.get("anjuke_sale_detail_collect"),20)){
					driver.click(Init.G_objMap.get("anjuke_sale_detail_collect"), "点击收藏房源按钮");
				}else{
					String ps = driver.printScreen();
					Report.writeHTMLLog("收藏按钮不存在", "收藏按钮不存在", Report.FAIL, ps);
					return;
				}
			}
			//判断房源收藏操作是否成功-弹出框是否出现
			if(driver.check(Init.G_objMap.get("anjuke_sale_detail_collectPop"))){
				//判断收藏房源后返回信息是否正确
				String returnMsg = driver.getText(Init.G_objMap.get("anjuke_sale_detal_collectsuc_msg"), "获取返回信息");
				if(returnMsg.equals("成功添加到选房单啦！")){
					Report.writeHTMLLog("添加选房单成功", "添加成功返回消息:"+returnMsg, Report.PASS, "");
				}else{
					String ps = driver.printScreen();
					Report.writeHTMLLog("添加选房单失败", "已经添加过该选房单:"+returnMsg, Report.DONE, ps);					
				}
				//进入选房单页面
                driver.click(Init.G_objMap.get("anjuke_sale_detal_collectsuc_link"), "查看选房单");
                driver.switchWindo(3);
                //判断选房单列表是否为空
                if(!driver.check(Init.G_objMap.get("anjuke_sale_member_houselist_noresult"))){
                	 //判断选房单标题
                	driver.check(Init.G_objMap.get("anjuke_sale_member_houselist_firstdatatitle"),10);
                    String collectDatatitle = driver.getAttribute(Init.G_objMap.get("anjuke_sale_member_houselist_firstdatatitle"), "title");
                    if(collectDatatitle.equals(firstdataTitle)){
                    	Report.writeHTMLLog("collect data title", "collect data title is correct!"+"<br>"+collectDatatitle+"<br>VS "+firstdataTitle, Report.PASS, "");
                    }else{
                    	String ps = driver.printScreen();
                    	Report.writeHTMLLog("collect data title", "collect data title is fault"+"<br>"+collectDatatitle+"<br>VS "+firstdataTitle, Report.FAIL, ps);
                    }
                    //判断选房单价格
                    String collectPrice = driver.getText(Init.G_objMap.get("anjuke_sale_member_houselist_firstdataprice"), "获取房源价格");
                    if(collectPrice.equals(price)){
                    	Report.writeHTMLLog("collect data price", "collect data price is correct!"+collectPrice+"万"+"<br>VS "+price+"万", Report.PASS, "");
                    }else{
                    	String ps = driver.printScreen();
                    	Report.writeHTMLLog("collect data price", "collect data price is fault", Report.FAIL, ps);
                    }
                    //判断选房单发布人姓名
                    String collectReleName = driver.getText(Init.G_objMap.get("anjuke_sale_member_houselist_firstdataname"),"获取发布房源经纪人姓名").trim();
                    if(collectReleName.equals(releaseName)){
                    	Report.writeHTMLLog("collect data releaseName", "collect data releaseName is correct!"+collectReleName+"<br>VS "+releaseName, Report.PASS, "");
                    }else{
                    	String ps = driver.printScreen();
                    	Report.writeHTMLLog("collect data releaseName", "collect data releaseName is fault!"+collectReleName+"<br>VS" + releaseName, Report.FAIL, ps);
                    }
                    //判断是否存在相同的数据
                    int datacount = driver.getElementCount(Init.G_objMap.get("anjuke_sale_member_houselist_dataCount"));
                    int num = Integer.parseInt(driver.getText(Init.G_objMap.get("anjuke_sale_member_houselist_textNum"), "获取房源总数"));
                    if(datacount!=1){
                    	boolean status = true;
                    	String href = driver.getAttribute(Init.G_objMap.get("anjuke_sale_member_houselist_firstdatatitle"), "href");
                    	for(int i=1;i<datacount;i++){
                    		int first = 12;
                    		String hrefResult = driver.getAttribute("//*[@id='prop_name_qt_apf_id_"+(first+4)+"']", "href");
                    		if(href.equals(hrefResult)){
                    			String ps = driver.printScreen();
                    			Report.writeHTMLLog("存在相同的房源", "房源收藏列表存在相同的房源", Report.FAIL, ps);
                    			status = false;
                    			break;
                    		}
                    	} 
                    	if(status){
                			Report.writeHTMLLog("不存在相同的房源", "房源收藏列表不存在相同的房源", Report.PASS,"");
                    	}
                    }if(num>=10){
                    	driver.click(Init.G_objMap.get("anjuke_sale_member_houselist_allsel"), "选择当前页数据");
                    	driver.click(Init.G_objMap.get("anjuke_sale_member_houselist_btnDelAll"), "删除所有数据");
                    	driver.check(Init.G_objMap.get("anjuke_sale_member_commlist_sucnotice"),5);
                    	driver.click(Init.G_objMap.get("anjuke_sale_member_houselist_btnCon"), "确认删除");
                    	int resultNum =0;
                    	if(driver.check("//div[@class='deletespan']", 10)){
                    		resultNum = Integer.parseInt(driver.getText(Init.G_objMap.get("anjuke_sale_member_houselist_textNum"), "获取房源总数"));
                    	}                    	
                    	if(resultNum<num){
                    		Report.writeHTMLLog("删除数据成功", "删除数据成功", Report.DONE, "");
                    	}else{
                    		String ps = driver.printScreen();
                    		Report.writeHTMLLog("删除数据失败", "删除数据失败", Report.FAIL, ps);
                    	}
                    }
                }else{
                	//选房单列表页为空
    				String ps = driver.printScreen();
    				Report.writeHTMLLog("收藏房源失败", "选房单列表页为空", Report.FAIL, ps);	               	
                }			
			}else{
				String ps = driver.printScreen();
				Report.writeHTMLLog("收藏房源失败", "收藏房源失败", Report.FAIL, ps);				
			}
		}else{
			String ps = driver.printScreen();
			Report.writeHTMLLog("二手房列表为空", "二手房列表搜索结果为空", Report.FAIL, ps);
		}		
	}
}
