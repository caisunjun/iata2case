package com.anjuke.ui.testcase;

import java.util.ArrayList;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.bean.AnjukeSaleInfo;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;
/**
 * 该用例完成安居客出售更新操作，逻辑如下
 * 1、编辑出售信息，添加图片附件
 * 2、更新成功后，验证出售基本信息
 * 3、在出租详细页，验证房屋出租详细信息
 * @Author gabrielgao
 * @time 2012-04-11 17:00
 * @UpdateAuthor weihuang
 * @last updatetime 2012-05-08 17:00
 */
public class AnjukeUpdateReleaseRent {
	private Browser driver = null;
	private AnjukeSaleInfo rentUpInfo = new AnjukeSaleInfo();
	private String url ="http://my.anjuke.com/user/brokerpropmanage/W0QQactZrent#proptop";
	private ArrayList<String> houseListNumber = null;
	@BeforeMethod
	public void startUp(){
	driver = FactoryBrowser.factoryBrowser();	
	}
	@AfterMethod
	public void tearDown(){
		//driver.closeAllwindow();
		driver.quit();
		driver=null;
		
	}
	//(timeOut = 200000)
	@Test(groups = {"unstable"})
	public void releaseRent(){
		driver.deleteAllCookies();
		//Report.setTCNameLog("管理出租编辑-- AnjukeUpdateReleaseRent --Hendry_huang");
		driver.deleteAllCookies();
		rentUpInfo.setUserName(PublicProcess.logIn(driver, "ajk_sh", "anjukeqa", false,1));
		driver.get(url);
		//driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_firstEdit"));
		//driver.check("id^edit_0");
		
		if(driver.check("id^edit_0")){
			updateRelease();
		}else{
			addHouseRent();
		}
		
						
	}
	private void addHouseRent(){
		String urladd = "http://my.anjuke.com/user/broker/property/rent/step1/";
		driver.get(urladd);
		if (driver.check(Init.G_objMap.get("ajk_releaseSale_div_full"))) {
			String ps = driver.printScreen();
			houseListNumber = houseNumber();
			Report.writeHTMLLog("发布出租", "抱歉，您本期发布的新房源已达上限，无法再发布了。"+ "本期已发布的房源:"+houseListNumber.get(0)+"剩余发布房源数:"+houseListNumber.get(1),
					Report.FAIL, ps);
		} else {
			// 设置小区名称
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_xiaoqu_yinyu"), "罗马花园",
					"小区名称");
			driver.switchToIframe(Init.G_objMap
					.get("anjuke_wangluojingjiren_salse_ifrCommunityList"),
					"选择小区");
		    driver.check("//ul/li/span[@rel='2710']");
			driver.click("//ul/li/span[@rel='2710']", "选择小区");
			/*driver.click(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_xiaoqu_nameClick"),
					"选择小区");*/
			driver.exitFrame();
			//rentInfo.setCommunityName("罗马花园");
			// 设置房屋类型
			driver.click(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_gongyu_click"), "选择公寓");
			//rentInfo.setHouseType("类型：公寓");
			// 设置楼层
			driver.type(
					Init.G_objMap.get("anjuke_wangluojingjiren_sale_louceng"),
					"3", "当前第三层");
			//rentInfo.setFloorCur("3");
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_loucengCnt"), "6", "共6层");
			//rentInfo.setFloorTotal("6");
			// 设置房屋面积
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_chanzhengArea"), "120",
					"产证面积");
			//rentInfo.setHouseArea("120");
			// 设置房屋类型
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_fangxing_room"), "3",
					"3房");
			//rentInfo.setHouseType_S("3室");
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_fangxing_hall"), "2",
					"2厅");
			//rentInfo.setHouseType_T("2厅");
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_fangxing_toilet"), "1",
					"1卫");
			//rentInfo.setHouseType_W("1卫");
			// 租金
			driver.type(
					Init.G_objMap.get("anjuke_wangluojingjiren_sale_proPrice"),
					"2500", "租金：2500元/月");
			//rentInfo.setRental("2500元/月");
			// 付款方式
			driver.type(
					Init.G_objMap.get("anjuke_wangluojingjiren_rent_payNum"),
					"3", "付3");
			//rentInfo.setPayType_pay("(付3");
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_rent_depositNum"), "1", "押1");
			//rentInfo.setPayType_case("押1)");
			// 建筑年代
			driver.type(
					Init.G_objMap.get("anjuke_wangluojingjiren_sale_houseAge"),
					"2009", "建筑年代：2009年");
			//rentInfo.setBuildYear("建造年代：2009年");
			// 装修情况
			// IE6 无法定位XPATH 使用 ID 定位
			// driver.click("id^radFitMent_2", "装修情况：精装修");
			driver.click(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_fitMent2_ie6"),
					"装修情况：精装修");
			// firefox
			// driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fitMent2"),
			// "装修情况：精装修");
			//rentInfo.setFitmentInfo("装修：精装修");
			// 配置情况
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_tv"),
					"配置：电视");
			//rentInfo.setConfiguration("配置：电视");
			// 朝向
			// IE6 无法定位XPATH 使用 by.ID 定位
			// driver.click("id^radHouseOri_1", "方向：朝南");
			driver.click(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_chaoxiang_south_ie6"),
					"方向：朝南");
			// "方向：朝南");
			/*
			 * 以下为支持firefox的xpath driver.click(Init.G_objMap.get(
			 * "anjuke_wangluojingjiren_sale_chaoxiang_south"), "方向：朝南");
			 */
			//rentInfo.setOrientations("朝向：南");
			// 设置出租方式
			// IE6 无法定位XPATH 使用 ID 定位
			// driver.click("id^radIsDolmus_0",
			// "出租方式：合租");anjuke_wangluojingjiren_rent_radlsDolmus0_for_ie6
			driver.click(Init.G_objMap
					.get("anjuke_wangluojingjiren_rent_radlsDolmus0_for_ie6"),
					"出租方式：合租");
			// driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_radlsDolmus0"),
			// "出租方式：合租");
			//rentInfo.setRentType("(合租)");
			// 自定义编号
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_beizhu_info"), "1985",
					"自定义编号");
			// 房源标题

			String time = PublicProcess.getNowDateTime("HH：MM:SS");

			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_fangyuanbiaoti_name"),
					"发布出租测试房源勿联系" + time, "出租房源标题");
			//rentInfo.setHouseTitle("发布出租测试房源勿联系" + time);
			// 房源描述
			driver.switchToIframe(Init.G_objMap
					.get("anjuke_wangluojingjiren_salse_ifrExplain"),
					"获取房源描述frame");
			driver.activeElementSendkeys("发布出租，此出租房源为测试房源，如果您看到该房源，请勿联系。感谢您的配合。谢谢！");
			driver.exitFrame();
			//rentInfo.setHouseDescribe("发布出租，此出租房源为测试房源，如果您看到该房源，请勿联系。感谢您的配合。谢谢！");
			// 发布类型
			/*driver.click(
					Init.G_objMap
							.get("anjuke_wangluojingjiren_sale_fangyuanleixing_type2_ie6"),
					"发布类型：只在我的店面显示");*/
			
			//* IE6 使用 
			driver.click("id^radSaleType_2", "发布类型：只在我的店面显示");
			 
			/*
			 * Firefox 使用xpath 定位 driver.click(Init.G_objMap.get(
			 * "anjuke_wangluojingjiren_sale_fangyuanleixing_type2"),
			 * "发布类型：只在我的店面显示");
			 */
			// 下一步
			
			driver.click("id^btnNext","下一步");		
			/*driver.click(
					Init.G_objMap.get("anjuke_wangluojingjiren_sale_nextBtn"),
					"下一步");*/
			driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_nextBtn"));
			
			// 上传图片
			PublicProcess.uploadPic(driver, "rent");
			// 点击发布
			driver.click(
					Init.G_objMap.get("anjuke_wangluojingjiren_sale_nextBtn"),
					"发布出租房源");
			// 判断是否发布成功
			String releaseMess = driver.getText(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_newSuccMess"),
					"获取发布后提示信息");
			if (releaseMess.equals("发布成功！")) {
				Report.writeHTMLLog("发布成功", "出售房源发布成功", Report.PASS, "");
			} else {
				String ps = driver.printScreen();
				Report.writeHTMLLog("发布失败", "出售房源发布失败", Report.FAIL, ps);
			}
			
		}
	}
	
	
	private void updateRelease(){
		driver.get(url);
		driver.click("id^edit_0", "编辑第一条数据");
		//driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_firstEdit"), "编辑第一条数据");
		//设置小区名称
		String communityName = driver.getAttribute(Init.G_objMap.get("anjuke_wangluojingjiren_sale_xiaoqu_yinyu"), "value");
		rentUpInfo.setCommunityName(communityName);
		//设置房屋类型
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_gongyu_click"), "选择公寓");
		rentUpInfo.setHouseType("类型：公寓");
		//设置楼层
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_louceng"), "2", "当前第2层");
		rentUpInfo.setFloorCur("2");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_loucengCnt"), "5", "共5层");
		rentUpInfo.setFloorTotal("5");
		//设置房屋面积
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chanzhengArea"), "100", "产证面积");
		rentUpInfo.setHouseArea("100");
		//设置房屋类型
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_room"), "2", "2房");
		rentUpInfo.setHouseType_S("2室");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_hall"), "1", "1厅");
		rentUpInfo.setHouseType_T("1厅");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_toilet"), "1", "1卫");
		rentUpInfo.setHouseType_W("1卫");
		//租金
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_proPrice"), "2800", "租金：2800元");
		rentUpInfo.setRental("2800元/月");
		//付款方式
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_rent_payNum"), "4", "付4");
		rentUpInfo.setPayType_pay("(付4");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_rent_depositNum"), "2", "押2");
		rentUpInfo.setPayType_case("押2)");
		//建筑年代
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_houseAge"), "2010", "建筑年代：2010年");
		rentUpInfo.setBuildYear("建造年代：2010年");
		//装修情况
		//IE6 无法定位XPATH 使用 ID 定位
		//driver.click("id^radFitMent_3", "装修：豪华装修");
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fitMent3_ie6"), "装修情况：豪华装修");
		//driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fitMent3"), "装修情况：豪华装修");
		rentUpInfo.setFitmentInfo("装修：豪华装修");
		//配置情况
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_tv"), "配置：电视");
		rentUpInfo.setConfiguration("配置：电视");
		//朝向
		//IE6 无法定位XPATH 使用 ID 定位
		//driver.click("id^radHouseOri_4", "方向：朝南");
		driver.click(Init.G_objMap.get("anjuke_wangluojinjir_sale_chaoxiang_southeast_ie6"), "方向：东南");
		//driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chaoxiang_southeast"), "方向：东南");
		rentUpInfo.setOrientations("朝向：东南");	
		//设置出租方式
		//IE6 无法定位XPATH 使用 ID 定位
		//driver.click("id^radIsDolmus_1", "出租方式：整租");
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_radlsDolmus1_ie6"), "出租方式：整租");
		//driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_radlsDolmus1"), "出租方式：整租");	
		rentUpInfo.setRentType("(整租)");
		//自定义编号
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_beizhu_info"), "989123", "自定义编号");
		//房源标题

		String time = PublicProcess.getNowDateTime("HH：MM:SS");

		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangyuanbiaoti_name"), "发布出租编辑页面勿联系"+time, "出租房源标题");
		rentUpInfo.setHouseTitle("发布出租编辑页面勿联系"+time);
		//房源描述
		driver.switchToIframe(Init.G_objMap.get("anjuke_wangluojingjiren_salse_ifrExplain"), "获取房源描述frame");
		driver.activeElementSendkeys("发布出租编辑，此出租房源为测试房源，如果您看到该房源，编辑页面，请勿联系。感谢您的配合。谢谢！");
		driver.exitFrame();		
		rentUpInfo.setHouseDescribe("发布出租编辑，此出租房源为测试房源，如果您看到该房源，编辑页面，请勿联系。感谢您的配合。谢谢！");
		//保存并编辑照片
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_saveNext"), "保存并编辑照片");
		//上传图片
		PublicProcess.uploadPic(driver,"rent");	
		//点击发布
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_nextBtn"), "保存房源照片");
		//判断是否发布成功
		String releaseMess = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSuccMess"), "获取发布后提示信息");		
		if(releaseMess.equals("编辑成功！")){
			Report.writeHTMLLog("编辑成功", "出租房源编辑成功", Report.PASS, "");			
		}else{
			String ps = driver.printScreen();
			Report.writeHTMLLog("编辑失败", "出租房源编辑失败", Report.FAIL, ps);			
		}
		//判断预览房源标题
		String houseTitle = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSucc_pageTitle"), "获取房源标题");
		if(houseTitle.equals(rentUpInfo.getHouseTitle())){
			Report.writeHTMLLog("房源标题", "发布后房源标题和输入一致", Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源标题", "发布后房源标题和输入不一致", Report.FAIL, driver.printScreen());			
		}
		//判断发布房源的经纪人姓名
		String tycoonName = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSucc_userName"), "获取经纪人姓名");
		if(tycoonName.equals(rentUpInfo.getUserName())){
			Report.writeHTMLLog("经纪人姓名", "登录经纪人姓名和发布房源经纪人姓名一致", Report.PASS, "");			
		}else{
			Report.writeHTMLLog("经纪人姓名", "登录经纪人姓名和发布房源经纪人姓名不一致", Report.FAIL, driver.printScreen());	
		}
		//判断房型等信息
		String infoAppend = rentUpInfo.getHouseType_S()+rentUpInfo.getHouseType_T()+"， "+rentUpInfo.getHouseArea()+".00平米， 楼层： "+rentUpInfo.getFloorCur()+"/"+rentUpInfo.getFloorTotal();
		String fangyuandesc = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_sucess_desc"), "获取房源信息").trim();
		String fangyuan = PublicProcess.splitString(fangyuandesc, "\n");
		if(infoAppend.equals(fangyuan)){
			Report.writeHTMLLog("房型", "房型："+fangyuan, Report.PASS, "");			
			
		}else{
			Report.writeHTMLLog("房型", "房型错误："+fangyuan+"**正确的应该为："+infoAppend, Report.FAIL, driver.printScreen());			
		}
		//判断房租
		String rent = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_updaSuc_rentPay"), "获取页面租金");
		if(rent.equals(rentUpInfo.getRental())){
			Report.writeHTMLLog("租金", "租金："+rent, Report.PASS, "");			
			
		}else{
			Report.writeHTMLLog("租金", "租金错误："+rent+"**正确的应该为："+rentUpInfo.getRental(), Report.FAIL, driver.printScreen());			
		}
		try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//跳转到房源详细页
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSucc_pageTitle"), "点击房源标题");
		driver.switchWindo(2);
		driver.check("className^propInfoTitle");
		//driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_title"));
		//房源详细页，房源标题检查
		String propInfoTitle = driver.getText("className^propInfoTitle", "获取房源标题");
		//String propInfoTitle = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_title"), "获取房源标题");
		if(propInfoTitle.equals(rentUpInfo.getHouseTitle())){
			Report.writeHTMLLog("房源详细页", "房源详细页标题："+propInfoTitle, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "房源详细页标题错误："+propInfoTitle+"**正确的应该为："+rentUpInfo.getHouseTitle(), Report.FAIL, driver.printScreen());			
		}	
		//租金检查
		String rentMessage = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_taxPrice"), "租金")+driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_detail_rentMon"), "租金")+driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_datail_renttype"), "押金方式");
		String rentInput = rentUpInfo.getRental()+rentUpInfo.getPayType_pay()+rentUpInfo.getPayType_case();
		if(rentMessage.equals(rentInput)){
			Report.writeHTMLLog("房源详细页", "租金检查："+rentMessage, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "租金检查错误："+rentMessage+"**应该为："+rentInput, Report.FAIL, driver.printScreen());		
		}
		//检查房型
		String houseType = driver.getText(Init.G_objMap.get("anjuke_wangluojjr_sale_detail_hossort_ie8"), "获取房型");
		String rentInfo_houseT = "房型："+rentUpInfo.getHouseType_S()+rentUpInfo.getHouseType_T()+rentUpInfo.getHouseType_W()+rentUpInfo.getRentType();
		if(houseType.equals(rentInfo_houseT)){
			Report.writeHTMLLog("房源详细页", "房型详细信息："+houseType, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "房型详细信息错误："+houseType+"正确为："+rentInfo_houseT, Report.FAIL, driver.printScreen());		
		}
		//朝向
		String houseDire = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_dire"), "房子朝向");
		if(houseDire.equals(rentUpInfo.getOrientations())){
			Report.writeHTMLLog("房源详细页", "朝向："+houseDire, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "朝向错误："+houseDire+"正确为："+rentUpInfo.getOrientations(), Report.FAIL, driver.printScreen());		
		}
		//建筑年代
		String buildYear = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_year"), "建筑年代");
		if(buildYear.equals(rentUpInfo.getBuildYear())){
			Report.writeHTMLLog("房源详细页", "建筑年代："+buildYear, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "建筑年代错误："+buildYear+"正确为："+rentUpInfo.getBuildYear(), Report.FAIL, driver.printScreen());		
		}		
		//楼层
		String floor = driver.getText(Init.G_objMap.get("anjuke_wangluojjr_sale_detail_floor"), "楼层");
		String floorInfo = "楼层："+rentUpInfo.getFloorCur()+"/"+rentUpInfo.getFloorTotal();
		if(floor.equals(floorInfo)){
			Report.writeHTMLLog("房源详细页", "楼层情况："+floor, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "楼层情况："+floor+"正确为："+floorInfo, Report.FAIL, driver.printScreen());		
		}
		//装修
		String fitment = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_fitment"), "装修");
		if(fitment.equals(rentUpInfo.getFitmentInfo())){
			Report.writeHTMLLog("房源详细页", "装修："+fitment, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "装修："+fitment+"正确为："+rentUpInfo.getFitmentInfo(), Report.FAIL, driver.printScreen());		
		}	
		//面积
		String area = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_detail_area"), "面积");
		if(area.equals("面积："+rentUpInfo.getHouseArea()+"平米")){
			Report.writeHTMLLog("房源详细页", "装修："+area, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "装修："+area+"正确为："+"面积："+rentUpInfo.getHouseArea()+"平米", Report.FAIL, driver.printScreen());		
		}	
		//房屋类型
		String type = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_detail_housetype"), "房屋类型");
		if(type.equals(rentUpInfo.getHouseType())){
			Report.writeHTMLLog("房源详细页", "房屋类型："+type, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "房屋类型："+type+"正确为："+rentUpInfo.getHouseType(), Report.FAIL, driver.printScreen());		
		}	
		//房源描述
		String description = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detaile_desc"), "房源描述");
		if(description.equals(rentUpInfo.getHouseDescribe())){
			Report.writeHTMLLog("房源详细页", "房源描述："+description, Report.PASS, "");
		}else{
			Report.writeHTMLLog("房源详细页", "房源描述："+description+"正确为："+rentUpInfo.getHouseDescribe(), Report.FAIL, driver.printScreen());		
		}
		driver.close();
		//删除发布房源anjuke_wangluojingjiren_sale_list_firstTitle
		driver.switchWindo(1);
		driver.get("http://my.anjuke.com/user/brokerpropmanage/W0QQactZrent#proptop");
		driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_list_firstTitle"));
		if(driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_firestsele"))&&driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_list_firstTitle"), "获取房源名称").equals(rentUpInfo.getHouseTitle())){
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_firestsele"), "选中发布房源");		
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_hideprop"), "删除发布房源");
			if(!driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_list_firstTitle"), "删除后再获取房源标题").equals(rentUpInfo.getHouseTitle())){
				Report.writeHTMLLog("删除房源", "删除编辑后的房源", "DONE", "");
			}else{
				Report.writeHTMLLog("删除房源", "删除编辑后的房源,删除失败", "FAIL", driver.printScreen());
			}

		}else{
			Report.writeHTMLLog("删除房源", "没有可以删除的房源", "DONE", driver.printScreen());
		}
	}
	
	/**
	 * 获取 已发布房源数，剩余发布房源数 
	 */
	private ArrayList<String> houseNumber(){
		ArrayList<String> houseln = new ArrayList<String>();
		driver.get("http://my.anjuke.com/user/broker/checked/");
		houseln.clear();
		houseln.add(driver.getText("//dl[5]/dd/a", "获取已发布房源数"));
		houseln.add(driver.getText("//dl[6]/dd/a", "获取剩余发布房源数"));
		return houseln;
	}
}
