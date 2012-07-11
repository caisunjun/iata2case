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
 * 该用例完成安居客出租发布操作，逻辑如下
 * 1、填写出租信息，添加图片附件
 * 2、发布成功后，验证出租基本信息
 * 3、在出租详细页，验证房屋出租详细信息
 * @Author gabrielgao
 * @time 2012-04-11 17:00
 * @UpdateAuthor weihuang
 * @last updatetime 2012-05-08 17:00
 */
public class AnjukeReleaseRent {
	private Browser driver = null;
	private AnjukeSaleInfo rentInfo = new AnjukeSaleInfo();
	private String url = "http://my.anjuke.com/user/broker/property/rent/step1/";
	private ArrayList<String> houseListNumber = null;
	
	@BeforeMethod
	public void startUp() {
		driver = FactoryBrowser.factoryBrowser();
	}

	@AfterMethod
	public void tearDown() {
		//driver.closeAllwindow();
		driver.quit();
		driver = null;
	}

	//(timeOut = 200000)
	@Test(groups = {"unstable"})
	public void releaseRent() {
		driver.deleteAllCookies();
		//Report.setTCNameLog("发布出租-- AnjukeReleaseRent --Hendry_huang");
		rentInfo.setUserName(PublicProcess.logIn(driver, "13712345678", "111111",
				false, 1));
		houseListNumber = houseNumber();
		driver.get(url);
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
			rentInfo.setCommunityName("罗马花园");
			// 设置房屋类型
			driver.click(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_gongyu_click"), "选择公寓");
			rentInfo.setHouseType("类型：公寓");
			// 设置楼层
			driver.type(
					Init.G_objMap.get("anjuke_wangluojingjiren_sale_louceng"),
					"3", "当前第三层");
			rentInfo.setFloorCur("3");
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_loucengCnt"), "6", "共6层");
			rentInfo.setFloorTotal("6");
			// 设置房屋面积
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_chanzhengArea"), "120",
					"产证面积");
			rentInfo.setHouseArea("120");
			// 设置房屋类型
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_fangxing_room"), "3",
					"3房");
			rentInfo.setHouseType_S("3室");
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_fangxing_hall"), "2",
					"2厅");
			rentInfo.setHouseType_T("2厅");
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_fangxing_toilet"), "1",
					"1卫");
			rentInfo.setHouseType_W("1卫");
			// 租金
			driver.type(
					Init.G_objMap.get("anjuke_wangluojingjiren_sale_proPrice"),
					"2500", "租金：2500元/月");
			rentInfo.setRental("2500元/月");
			// 付款方式
			driver.type(
					Init.G_objMap.get("anjuke_wangluojingjiren_rent_payNum"),
					"3", "付3");
			rentInfo.setPayType_pay("(付3");
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_rent_depositNum"), "1", "押1");
			rentInfo.setPayType_case("押1)");
			// 建筑年代
			driver.type(
					Init.G_objMap.get("anjuke_wangluojingjiren_sale_houseAge"),
					"2009", "建筑年代：2009年");
			rentInfo.setBuildYear("建造年代：2009年");
			// 装修情况
			// IE6 无法定位XPATH 使用 ID 定位
			// driver.click("id^radFitMent_2", "装修情况：精装修");
			driver.click(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_fitMent2_ie6"),
					"装修情况：精装修");
			// firefox
			// driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fitMent2"),
			// "装修情况：精装修");
			rentInfo.setFitmentInfo("装修：精装修");
			// 配置情况
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_tv"),
					"配置：电视");
			rentInfo.setConfiguration("配置：电视");
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
			rentInfo.setOrientations("朝向：南");
			// 设置出租方式
			// IE6 无法定位XPATH 使用 ID 定位
			// driver.click("id^radIsDolmus_0",
			// "出租方式：合租");anjuke_wangluojingjiren_rent_radlsDolmus0_for_ie6
			driver.click(Init.G_objMap
					.get("anjuke_wangluojingjiren_rent_radlsDolmus0_for_ie6"),
					"出租方式：合租");
			// driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_radlsDolmus0"),
			// "出租方式：合租");
			rentInfo.setRentType("(合租)");
			// 自定义编号
			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_beizhu_info"), "1985",
					"自定义编号");
			// 房源标题

			String time = PublicProcess.getNowDateTime("HH：MM:SS");

			driver.type(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_fangyuanbiaoti_name"),
					"发布出租测试房源勿联系" + time, "出租房源标题");
			rentInfo.setHouseTitle("发布出租测试房源勿联系" + time);
			// 房源描述
			driver.switchToIframe(Init.G_objMap
					.get("anjuke_wangluojingjiren_salse_ifrExplain"),
					"获取房源描述frame");
			driver.activeElementSendkeys("发布出租，此出租房源为测试房源，如果您看到该房源，请勿联系。感谢您的配合。谢谢！");
			driver.exitFrame();
			rentInfo.setHouseDescribe("发布出租，此出租房源为测试房源，如果您看到该房源，请勿联系。感谢您的配合。谢谢！");
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
			
			//获取发布房源数
			
			if (releaseMess.equals("发布成功！") && Integer.parseInt(houseListNumber.get(1))>= 10) {
				Report.writeHTMLLog("发布成功", "出售房源发布成功"+ "本期已发布的房源:"+houseListNumber.get(0)+"剩余发布房源数:"+houseListNumber.get(1), Report.PASS, "");
			} else if(releaseMess.equals("发布成功！") && Integer.parseInt(houseListNumber.get(1)) < 10){
				Report.writeHTMLLog("发布成功", "出售房源发布成功"+ "本期已发布的房源:"+houseListNumber.get(0)+"剩余发布房源数:"+houseListNumber.get(1), Report.WARNING, "");
			}else{
				String ps = driver.printScreen();
				Report.writeHTMLLog("发布失败", "出售房源发布失败"+ "本期已发布的房源:"+houseListNumber.get(0)+"剩余发布房源数:"+houseListNumber.get(1), Report.FAIL, ps);
			}
		
			
			// 判断预览房源标题
			String houseTitle = driver.getText(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_newSucc_pageTitle"),
					"获取房源标题");
			if (houseTitle.equals(rentInfo.getHouseTitle())) {
				Report.writeHTMLLog("房源标题", "发布后房源标题和输入一致", Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源标题", "发布后房源标题和输入不一致", Report.FAIL,
						driver.printScreen());
			}
			// 判断发布房源的经纪人姓名
			String tycoonName = driver.getText(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_newSucc_userName"),
					"获取经纪人姓名");
			if (tycoonName.equals(rentInfo.getUserName())) {
				Report.writeHTMLLog("经纪人姓名", "登录经纪人姓名和发布房源经纪人姓名一致",
						Report.PASS, "");
			} else {
				Report.writeHTMLLog("经纪人姓名", "登录经纪人姓名和发布房源经纪人姓名不一致",
						Report.FAIL, driver.printScreen());
			}
			// 判断房型等信息
			String infoAppend = rentInfo.getHouseType_S()
					+ rentInfo.getHouseType_T() + "， "
					+ rentInfo.getHouseArea() + ".00平米， 楼层： "
					+ rentInfo.getFloorCur() + "/" + rentInfo.getFloorTotal();
			String fangyuandesc = driver
					.getText(
							Init.G_objMap
									.get("anjuke_wangluojingjiren_sale_sucess_desc"),
							"获取房源信息").trim();
			String fangyuan = PublicProcess.splitString(fangyuandesc, "\n");
			if (infoAppend.equals(fangyuan)) {
				Report.writeHTMLLog("房型", "房型：" + fangyuan, Report.PASS, "");

			} else {
				Report.writeHTMLLog("房型", "房型错误：" + fangyuan + "**正确的应该为："
						+ infoAppend, Report.FAIL, driver.printScreen());
			}
			// 判断房租
			String rent = driver.getText(Init.G_objMap
					.get("anjuke_wangluojingjiren_rent_updaSuc_rentPay"),
					"获取页面租金");
			if (rent.equals(rentInfo.getRental())) {
				Report.writeHTMLLog("租金", "租金：" + rent, Report.PASS, "");

			} else {
				Report.writeHTMLLog("租金", "租金错误：" + rent + "**正确的应该为："
						+ rentInfo.getRental(), Report.FAIL,
						driver.printScreen());
			}
			// 跳转到房源详细页
			driver.click(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_newSucc_pageTitle"),
					"点击房源标题");
			driver.switchWindo(2);
			driver.check("className^propInfoTitle");
			// 房源详细页，房源标题检查
			/*driver.check(Init.G_objMap
							.get("anjuke_wangluojingjiren_sale_detail_title"));
			String propInfoTitle = driver
					.getText(Init.G_objMap
							.get("anjuke_wangluojingjiren_sale_detail_title"),
							"获取房源标题");*/
			String propInfoTitle = driver.getText("className^propInfoTitle", "获取房源标题");
			if (propInfoTitle.equals(rentInfo.getHouseTitle())) {
				Report.writeHTMLLog("房源详细页", "房源详细页标题：" + propInfoTitle,
						Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "房源详细页标题错误：" + propInfoTitle
						+ "**正确的应该为：" + rentInfo.getHouseTitle(), Report.FAIL,
						driver.printScreen());
			}
			// 租金检查
			String rentMessage = driver.getText(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_detail_taxPrice"), "租金")
					+ driver.getText(
							Init.G_objMap
									.get("anjuke_wangluojingjiren_rent_detail_rentMon"),
							"租金")
					+ driver.getText(
							Init.G_objMap
									.get("anjuke_wangluojingjiren_rent_datail_renttype"),
							"押金方式");
			String rentInput = rentInfo.getRental() + rentInfo.getPayType_pay()
					+ rentInfo.getPayType_case();
			if (rentMessage.equals(rentInput)) {
				Report.writeHTMLLog("房源详细页", "租金检查：" + rentMessage,
						Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "租金检查错误：" + rentMessage + "**应该为："
						+ rentInput, Report.FAIL, driver.printScreen());
			}
			// 检查房型
			String houseType = driver.getText(Init.G_objMap
					.get("anjuke_wangluojjr_sale_detail_hossort_ie8"), "获取房型");
			String rentInfo_houseT = "房型：" + rentInfo.getHouseType_S()
					+ rentInfo.getHouseType_T() + rentInfo.getHouseType_W()
					+ rentInfo.getRentType();
			if (houseType.equals(rentInfo_houseT)) {
				Report.writeHTMLLog("房源详细页", "房型详细信息：" + houseType,
						Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "房型详细信息错误：" + houseType + "正确为："
						+ rentInfo_houseT, Report.FAIL, driver.printScreen());
			}
			// 朝向
			//ul/li[7]/div[2]
			//String houseDire = driver.getText("//ul/li[7]/div[2]", "房子朝向");
			String houseDire = driver.getText(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_detail_dire"), "房子朝向");
			if (houseDire.equals(rentInfo.getOrientations())) {
				Report.writeHTMLLog("房源详细页", "朝向：" + houseDire, Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "朝向错误：" + houseDire + "正确为："
						+ rentInfo.getOrientations(), Report.FAIL,
						driver.printScreen());
			}
			// 建筑年代
			String buildYear = driver.getText(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_detail_year"), "建筑年代");
			/*String buildYear = driver.getText(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_detail_year"), "建筑年代");*/
			if (buildYear.equals(rentInfo.getBuildYear())) {
				Report.writeHTMLLog("房源详细页", "建筑年代：" + buildYear, Report.PASS,
						"");
			} else {
				Report.writeHTMLLog("房源详细页", "建筑年代错误：" + buildYear + "正确为："
						+ rentInfo.getBuildYear(), Report.FAIL,
						driver.printScreen());
			}
			// 楼层
			String floor = driver.getText(Init.G_objMap
					.get("anjuke_wangluojjr_sale_detail_floor"), "楼层");
			String floorInfo = "楼层：" + rentInfo.getFloorCur() + "/"
					+ rentInfo.getFloorTotal();
			if (floor.equals(floorInfo)) {
				Report.writeHTMLLog("房源详细页", "楼层情况：" + floor, Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "楼层情况：" + floor + "正确为："
						+ floorInfo, Report.FAIL, driver.printScreen());
			}
			// 装修
			String fitment = driver.getText(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_detail_fitment"), "装修");
			if (fitment.equals(rentInfo.getFitmentInfo())) {
				Report.writeHTMLLog("房源详细页", "装修：" + fitment, Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "装修：" + fitment + "正确为："
						+ rentInfo.getFitmentInfo(), Report.FAIL,
						driver.printScreen());
			}
			// 面积
			String area = driver.getText(Init.G_objMap
					.get("anjuke_wangluojingjiren_rent_detail_area"), "面积");
			if (area.equals("面积：" + rentInfo.getHouseArea() + "平米")) {
				Report.writeHTMLLog("房源详细页", "装修：" + area, Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页",
						"装修：" + area + "正确为：" + rentInfo.getHouseArea(),
						Report.FAIL, driver.printScreen());
			}
			// 房屋类型
			String type = driver.getText(Init.G_objMap
					.get("anjuke_wangluojingjiren_rent_detail_housetype"),
					"房屋类型");
			if (type.equals(rentInfo.getHouseType())) {
				Report.writeHTMLLog("房源详细页", "房屋类型：" + type, Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页",
						"房屋类型：" + type + "正确为：" + rentInfo.getHouseType(),
						Report.FAIL, driver.printScreen());
			}
			// 房源描述
			String description = driver.getText(Init.G_objMap
					.get("anjuke_wangluojingjiren_sale_detaile_desc"), "房源描述");
			if (description.equals(rentInfo.getHouseDescribe())) {
				Report.writeHTMLLog("房源详细页", "房源描述：" + description,
						Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "房源描述：" + description + "正确为："
						+ rentInfo.getHouseDescribe(), Report.FAIL,
						driver.printScreen());
			}
			driver.close();
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
