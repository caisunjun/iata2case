package com.anjuke.ui.publicfunction;

import com.anjuke.ui.bean.AnjukeSaleInfo;
import com.anjuke.ui.page.Ajk_PropRent;
import com.anjuke.ui.page.Broker_PropertynewRentStep;
import com.anjukeinc.iata.ui.browser.Browser;

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
	 * @param info	 AnjukeSaleInfo
	 * @param Num    管理房源，编辑第几条房源
	 */
	public static final void editRent(Browser driver, AnjukeSaleInfo info , int num){
		driver.get(editUrl);
		driver.click("id^edit_"+num, "编辑第"+num+"套房源");
		rent(driver, info, "Edit");
	}
	
	public static final void rent(Browser driver, AnjukeSaleInfo info, String type){
		driver.assertFalse(driver.check(Broker_PropertynewRentStep.SHANGXIAN), "本月发布房源是否到上限", "本期发布房源未到上限！", "抱歉，您本期发布的新房源已达上限，无法再发布了。");

		//小区名
		if( type.equals("Release")){
			driver.type(Broker_PropertynewRentStep.XIAOQUMING, info.getCommunityName(), "小区名称");
			driver.click(Broker_PropertynewRentStep.XUANXIAOQU, "选择小区", 60); 
		}
		//房屋类型
		if(info.getRentType().equals("合租")){
			driver.click(Broker_PropertynewRentStep.HEZU, "出租方式：合租");
		}else{
			driver.click(Broker_PropertynewRentStep.ZHENGZU, "出租方式：整租");
		}
		// 租金
		driver.type(Broker_PropertynewRentStep.ZUJIN, info.getRental(), "租金："+info.getRental() +"元/月");
		
		// 付款方式
		driver.type(Broker_PropertynewRentStep.FUKUAN, info.getPayType_pay(), "付"+info.getPayType_pay());
		driver.type(Broker_PropertynewRentStep.YAJIN,info.getPayType_case(), "押"+info.getPayType_case());

		// 设置房屋面积
		driver.type(Broker_PropertynewRentStep.AREA, info.getHouseArea(), "产证面积");
		
		// 设置房屋类型
		driver.type(Broker_PropertynewRentStep.ROOM, info.getHouseType_S(), info.getHouseType_S()+"室");
		driver.type(Broker_PropertynewRentStep.HALL, info.getHouseType_T(), info.getHouseType_T()+"厅");
		driver.type(Broker_PropertynewRentStep.TOILE, info.getHouseType_W(), info.getHouseType_W()+"卫");

		// 设置楼层
		driver.type(Broker_PropertynewRentStep.FLOOR, info.getFloorCur(), "当前第"+info.getFloorCur()+"层");
		driver.type(Broker_PropertynewRentStep.FLOOR_T, info.getFloorTotal(), "共"+info.getFloorCur()+"层");

		//房屋类型
		driver.select(Broker_PropertynewRentStep.LEIXING, info.getHouseType());
		
		//装修情况
		driver.select(Broker_PropertynewRentStep.ZHUANGXIU, info.getFitmentInfo());
		
		//朝向
		driver.select(Broker_PropertynewRentStep.CHAOXIANG, info.getOrientations());

		// 建筑年代
		driver.type(Broker_PropertynewRentStep.HOUSE_AGE, info.getBuildYear(), "建筑年代："+info.getBuildYear()+"年");

		// 配置情况
		driver.click(Broker_PropertynewRentStep.CONFIG_TV, "配置：电视");

		// 房源标题
		driver.type(Broker_PropertynewRentStep.TITLE, info.getHouseTitle(), "出租房源标题");

		 //房源描述
		driver.switchToIframe(Broker_PropertynewRentStep.MIAOSHU, "获取房源描述frame");
		driver.activeElementSendkeys(info.getHouseDescribe());
		driver.exitFrame();
		
		//推荐房源
		if(type.equals("Release") && info.getRecommendation().equals("不推荐")){
			driver.click(Broker_PropertynewRentStep.TUIJIAN, "发布类型：不推荐该房源");
		}
			
		//图片情况
		if(info.getHouseImage().equals("无图片")){
			driver.click(Broker_PropertynewRentStep.PUBLISH, "租房立即发布");
		}else{
			driver.click(Broker_PropertynewRentStep.UPLOAD_PHOTO, "去上传照片");
			PublicProcess.uploadPic(driver, "rent");
			driver.click(Broker_PropertynewRentStep.PUBLISH2, "发布出租房源");
		}
		verifyRent(driver, info, type);
}

	public static final void verifyRent(Browser driver,AnjukeSaleInfo info, String type) {
		// 判断是否发布成功
		String releaseMess = driver.getText(Broker_PropertynewRentStep.SUCCEED, "获取发布后提示信息");
		driver.assertEquals("发布成功！", releaseMess, "发布成功", "判断房源发布是否成功");

		// 跳转到房源详细页  
		driver.click(Broker_PropertynewRentStep.PROPRENT, "点击房源单页");
		
		//小区名检查
		if(type.equals("Edit")){
			String communityName = driver.getText(Ajk_PropRent.XIAOQUMING, "获取房源小区名");
			driver.assertEquals(info.getCommunityName(), communityName, "房源详细页", "房源小区名是否正确");
		}
		
		// 房源详细页，房源标题检查
		driver.check(Ajk_PropRent.TITLE);
		String propInfoTitle = driver.getText(Ajk_PropRent.TITLE, "获取房源标题");
		driver.assertEquals(info.getHouseTitle(), propInfoTitle, "房源详细页", "房源详细页标题是否正确");

		// 租金检查
		String rentMessage = driver.getText(Ajk_PropRent.ZUJIN, "租金")
				+ driver.getText(Ajk_PropRent.ZUJIN_DW, "租金单位") + driver.getText(Ajk_PropRent.YAJIN, "押金方式");
		String rentInput = info.getRental()+"元/月" + "(付" +info.getPayType_pay()+"押"+ info.getPayType_case()+")";
		driver.assertEquals(rentInput, rentMessage, "房源详细页", "租金检查是否正确");

		// 检查房型
		String roomType = driver.getText(Ajk_PropRent.FANGXING, "获取房型");
		String info_houseT = "房型："+ info.getHouseType_S()+ "室"+ info.getHouseType_T()+ "厅"+ info.getHouseType_W()+ "卫"+ "("+ info.getRentType()+ ")";
		driver.assertEquals(info_houseT, roomType, "房源详细页", "房型详细信息是否正确");

		// 朝向
		String houseDire = driver.getText(Ajk_PropRent.CHAOXIANG, "房子朝向");
		driver.assertEquals("朝向："+info.getOrientations(), houseDire, "房源详细页", "朝向 ");

		// 建筑年代
		String buildYear = driver.getText(Ajk_PropRent.HOUSE_AGE, "建筑年代");

		driver.assertEquals("建造年代："+info.getBuildYear()+"年", buildYear, "房源详细页", "建筑年代");

		// 楼层
		String floor = driver.getText(Ajk_PropRent.FLOOR, "楼层");
		String floorInfo = "楼层：" + info.getFloorCur() + "/" + info.getFloorTotal();
		driver.assertEquals(floorInfo, floor, "房源详细页", "楼层情况 ");

		// 装修
		String fitment = driver.getText(Ajk_PropRent.ZHUANGXIU, "装修");
		driver.assertEquals("装修："+info.getFitmentInfo(), fitment, "房源详细页", "装修");

		// 面积
		String area = driver.getText(Ajk_PropRent.AREA, "面积");
		driver.assertEquals("面积：" + info.getHouseArea() + "平米", area, "房源详细页", "装修");

		// 房屋类型
		String houseType = driver.getText(Ajk_PropRent.LEIXING, "房屋类型");
		driver.assertEquals("类型："+info.getHouseType(), houseType, "房源详细页", "房屋类型");
		// 房源描述
		String description = driver.getText(Ajk_PropRent.MIAOSHU, "房源描述");
		driver.assertEquals(info.getHouseDescribe(), description, "房源详细页", "房源描述");
	}
	

}
