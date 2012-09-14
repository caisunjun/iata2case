package com.anjuke.ui.publicfunction;

import com.anjuke.ui.bean.AnjukeSaleInfo;
import com.anjuke.ui.bean.Evaluate;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.init.Init;

public class BrokerRentOperating {
	private static String releaseUrl = "http://my.anjuke.com/user/broker/property/rent/step1/";
	private static String editUrl = "http://my.anjuke.com/user/brokerpropmanage/W0QQactZrent#proptop";
	
	/**
	 * 发布租房，访问发布房源页面，输入rentinfo提交发布，到房源详情页验证数据。
	 * @param driver browserDriver
	 * @param info  AnjukeSaleInfo
	 */
	public static final void releaseRent(Browser driver, AnjukeSaleInfo info ){
		driver.get(releaseUrl);
		rent(driver, info, "Release");
	}
	
	/**
	 * 编辑租房，点击管理房源列表的第num条房源编辑，0为第1条，编辑成功后到房源详情页验证数据。
	 * @param driver browserDriver
	 * @param eva	 AnjukeSaleInfo
	 * @param Num    管理房源，编辑第几条房源
	 */
	public static final void editRent(Browser driver, Evaluate eva , int num){
		driver.get(editUrl);
		driver.click("id^edit_"+num, "编辑第"+num+"套房源");
		rent(driver, eva, "Edit");
	}
	
	public static final void rent(Browser driver, AnjukeSaleInfo info, String type){
		driver.assertFalse(driver.check(Init.G_objMap.get("ajk_releaseSale_div_full")), "本月发布房源是否到上限", "本期发布房源未到上限！", "抱歉，您本期发布的新房源已达上限，无法再发布了。");

		//小区名
		if( type.equals("Release")){
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_xiaoqu_yinyu"), info.getCommunityName(), "小区名称");
			driver.click(".//*[@id='targetid']/li[2]", "选择小区", 60); 
		}
		//房屋类型
		if(info.getRentType().equals("合租")){
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_radlsDolmus0"), "出租方式：合租");
		}else{
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_radlsDolmus1"), "出租方式：整租");
		}
		// 租金
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_proPrice"), info.getRental(), "租金："+info.getRental() +"元/月");
		
		// 付款方式
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_rent_payNum"), info.getPayType_pay(), "付"+info.getPayType_pay());
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_rent_depositNum"),info.getPayType_case(), "押"+info.getPayType_case());

		// 设置房屋面积
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chanzhengArea"), info.getHouseArea(), "产证面积");
		
		// 设置房屋类型
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_room"), info.getHouseType_S(), info.getHouseType_S()+"室");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_hall"), info.getHouseType_T(), info.getHouseType_T()+"厅");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_toilet"), info.getHouseType_W(), info.getHouseType_W()+"卫");

		// 设置楼层
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_louceng"), info.getFloorCur(), "当前第"+info.getFloorCur()+"层");
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_loucengCnt"), info.getFloorTotal(), "共"+info.getFloorCur()+"层");

		//房屋类型
		driver.select(Init.G_objMap.get("anjuke_wangluojingjiren_sale_leixing_house_ie6"), info.getHouseType());
		
		//装修情况
		driver.select(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fitMent2_ie6"), info.getFitmentInfo());
		
		//朝向
		driver.select(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chaoxiang_south_ie6"), info.getOrientations());

		// 建筑年代
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_houseAge"), info.getBuildYear(), "建筑年代："+info.getBuildYear()+"年");

		// 配置情况
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_tv"), "配置：电视");

		// 房源标题
		driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangyuanbiaoti_name"), info.getHouseTitle(), "出租房源标题");

		 //房源描述
		driver.switchToIframe(Init.G_objMap.get("anjuke_wangluojingjiren_salse_ifrExplain"), "获取房源描述frame");
		driver.activeElementSendkeys(info.getHouseDescribe());
		driver.exitFrame();
		
		//推荐房源
		if(type.equals("Release") && info.getRecommendation().equals("不推荐")){
			driver.click("id^checkbox-r", "发布类型：不推荐该房源");
		}
			
		//图片情况
		if(info.getHouseImage().equals("无图片")){
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_lijifabu_SaveEdit"), "租房立即发布");
		}else{
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_UploadImg_To2"), "去上传照片");
			PublicProcess.uploadPic(driver, "rent");
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_step2_submitup"), "发布出租房源");
		}
		verifyRent(driver, info, type);
}

	public static final void verifyRent(Browser driver,AnjukeSaleInfo info, String type) {
		// 判断是否发布成功
		String releaseMess = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSuccMess"), "获取发布后提示信息");
		driver.assertEquals("发布成功！", releaseMess, "发布成功", "判断房源发布是否成功");

		// 跳转到房源详细页  
		driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_rent_setp3_danye"), "点击房源单页");
		
		//小区名检查
		if(type.equals("Edit")){
			String communityName = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_detail_communityname"), "获取房源小区名");
			driver.assertEquals(info.getCommunityName(), communityName, "房源详细页", "房源小区名是否正确");
		}
		
		// 房源详细页，房源标题检查
		driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_title"));
		String propInfoTitle = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_title"), "获取房源标题");
		driver.assertEquals(info.getHouseTitle(), propInfoTitle, "房源详细页", "房源详细页标题是否正确");

		// 租金检查
		String rentMessage = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_taxPrice"), "租金")
				+ driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_detail_rentMon"), "租金单位") + driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_datail_renttype"), "押金方式");
		String rentInput = info.getRental()+"元/月" + "(付" +info.getPayType_pay()+"押"+ info.getPayType_case()+")";
		driver.assertEquals(rentInput, rentMessage, "房源详细页", "租金检查是否正确");

		// 检查房型
		String roomType = driver.getText(Init.G_objMap.get("anjuke_wangluojjr_sale_detail_hossort_ie8"), "获取房型");
		String info_houseT = "房型："+ info.getHouseType_S()+ "室"+ info.getHouseType_T()+ "厅"+ info.getHouseType_W()+ "卫"+ "("+ info.getRentType()+ ")";
		driver.assertEquals(info_houseT, roomType, "房源详细页", "房型详细信息是否正确");

		// 朝向
		String houseDire = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_dire"), "房子朝向");
		driver.assertEquals("朝向："+info.getOrientations(), houseDire, "房源详细页", "朝向 ");

		// 建筑年代
		String buildYear = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_year"), "建筑年代");

		driver.assertEquals("建造年代："+info.getBuildYear()+"年", buildYear, "房源详细页", "建筑年代");

		// 楼层
		String floor = driver.getText(Init.G_objMap.get("anjuke_wangluojjr_sale_detail_floor"), "楼层");
		String floorInfo = "楼层：" + info.getFloorCur() + "/" + info.getFloorTotal();
		driver.assertEquals(floorInfo, floor, "房源详细页", "楼层情况 ");

		// 装修
		String fitment = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_fitment"), "装修");
		driver.assertEquals("装修："+info.getFitmentInfo(), fitment, "房源详细页", "装修");

		// 面积
		String area = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_detail_area"), "面积");
		driver.assertEquals("面积：" + info.getHouseArea() + "平米", area, "房源详细页", "装修");

		// 房屋类型
		String houseType = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_detail_housetype"), "房屋类型");
		driver.assertEquals("类型："+info.getHouseType(), houseType, "房源详细页", "房屋类型");
		// 房源描述
		String description = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detaile_desc"), "房源描述");
		driver.assertEquals(info.getHouseDescribe(), description, "房源详细页", "房源描述");
	}
	

}
