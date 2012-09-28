package com.anjuke.ui.page;
/**
 * 安居客 - 估价信息页   http://shanghai.anjuke.com/gujia/
 * 
 * @author zhaoqinchu
 * */

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;

public class AjkGujia {
	private static String gujiaUrl = "http://shanghai.anjuke.com/gujia/";
	
	//定义等待时间
	public static void waitForPageToLoad(long longtime){
		try {
			Thread.sleep(longtime);
		} catch (InterruptedException exception) {
			exception.getStackTrace();
		}
	}
	
	public static final void GJDetail(Browser driver , AnjukeGujiaInformation info ){
		driver.get(gujiaUrl);
		gujia(driver, info, "Release");
}
	public static final void gujia(Browser driver, AnjukeGujiaInformation gujiaInfo, String type){

		//小区名
		if( type.equals("Release")){
//			driver.click(Init.G_objMap.get("anjuke_gujia_commname"), "选中小区名输入框");
			driver.type(Init.G_objMap.get("anjuke_gujia_commname"), gujiaInfo.getCommunityName(), "小区名称");
			driver.findElement(Init.G_objMap.get("anjuke_gujia_commname"), "", 30).sendKeys(" ");
			driver.click(Init.G_objMap.get("anjuke_gujia_commname_keywords"),"下拉框");
			driver.click("//p[@id='li_table_p0']","选择小区");
		}
		
		// 设置房屋类型
		driver.select(Init.G_objMap.get("anjuke_gujia_room"), gujiaInfo.getHouseType_S());
		driver.select(Init.G_objMap.get("anjuke_gujia_hall"), gujiaInfo.getHouseType_T());
		driver.select(Init.G_objMap.get("anjuke_gujia_toilet"), gujiaInfo.getHouseType_W());
        
		// 设置房屋面积
		driver.type(Init.G_objMap.get("anjuke_gujia_area"), gujiaInfo.getHouseArea(), "产证面积");
		
		// 设置楼层
		driver.type(Init.G_objMap.get("anjuke_gujia_input_pro_floor"), gujiaInfo.getFloorCur(), "当前第"+gujiaInfo.getFloorCur()+"层");
		driver.type(Init.G_objMap.get("anjuke_gujia_input_total_floor"), gujiaInfo.getFloorTotal(), "共"+gujiaInfo.getFloorCur()+"层");

		//房屋类型
		driver.select(Init.G_objMap.get("anjuke_gujia_house_type"), gujiaInfo.getHouseType());
		
		//装修情况
		driver.select(Init.G_objMap.get("anjuke_gujia_renovation"), gujiaInfo.getFitmentInfo());
		
		//朝向
		driver.select(Init.G_objMap.get("anjuke_gujia_orientation"), gujiaInfo.getOrientations());

		// 建筑年代
		driver.type(Init.G_objMap.get("anjuke_gujia_Time"), gujiaInfo.getBuildYear(), "建筑年代："+gujiaInfo.getBuildYear()+"年");
		
		//点击更多估价条件
		driver.click(Init.G_objMap.get("anjuke_gujia_moreCondition"), "更多估价条件", 30);
		
		//输入赠送花园的面积
		driver.type(Init.G_objMap.get("anjuke_gujia_giveGarden"),gujiaInfo.getGarden(),"花园："+gujiaInfo.getGarden()+"平米");
		
		//赠送地下室
		driver.type(Init.G_objMap.get("anjuke_gujia_giveBasement"), gujiaInfo.getBasement(), "地下室："+gujiaInfo.getBasement()+"平米");
		
		//景观状况
		driver.click(Init.G_objMap.get("anjuke_gujia_giveScenery"), "景观房");
		
		//离小区出口情况
		driver.click(Init.G_objMap.get("anjuke_gujia_giveDistrictExports"), "离出口情况");
		
		//小区配套
		driver.click(Init.G_objMap.get("anjuke_gujia_giveSupportingCell"), "小区配套");
		
		//立刻估价
		driver.click(Init.G_objMap.get("anjuke_gujia_submit"), "立刻估价");

	    //切换窗口
		driver.switchWindo(2);
	     
		//保存估价报告到个人用户中心
		driver.click(Init.G_objMap.get("anjuke_gujia_savaValue"), "保存估价报告");
		driver.click(Init.G_objMap.get("anjuke_gujia_see"), "查看");
		
		//检查房型
		String houseMode2 = driver.getText(Init.G_objMap.get("anjuke_gujia_chamber"),"获取房型");
		
		//检查楼层
		String houseBuild1=driver.getText(Init.G_objMap.get("anjuke_gujia_administrationLouCeng"), "楼层");
		
		//检查面积
		String houseArea1=driver.getText(Init.G_objMap.get("anjuke_gujia_administrationArea"), "房子面积");
		
		//检查装修情况
		String houseFixture1=driver.getText(Init.G_objMap.get("anjuke_gujia_administrationRenovation"), "装修情况");
		
		//检查朝向
		String houseDire1 = driver.getText(Init.G_objMap.get("anjuke_gujia_administrationOrientation"), "房子朝向");
		
		//离小区出口
		String attance = driver.getText(Init.G_objMap.get("anjuke_gujia_administrationcommunity"), "离小区出口");
		
		//自有车位
		String chewei1 = driver.getText(Init.G_objMap.get("anjuke_gujia_administrationParkingLot"), "自有车位");
		
		//景观房
		String jingguan = driver.getText(Init.G_objMap.get("anjuke_gujia_administrationLandscapeRoom"), "景观房");
		
		//赠送面积
		String mianji = driver.getText(Init.G_objMap.get("anjuke_gujia_giveArea"), "赠送面积");
		
		//小区配套
		String peitao = driver.getText(Init.G_objMap.get("anjuke_gujia_administrationSupportingCell"), "小区配套");
		
		//查看估价报告
		driver.click(Init.G_objMap.get("anjuke_gujia_chaKanGuide"), "查看估价报告");
		
		driver.switchWindo(3);
		
		//输出并打印评估总价
		String evaTotal=driver.getText(Init.G_objMap.get("anjuke_gujia_mainpanel"),"评估总价");
		if (evaTotal != "-"){
		//System.out.println("评估总价:"+evaTotal);
		Report.writeHTMLLog("估价成功页", "评估总价：" + evaTotal + "万",Report.PASS, "");
		}
		else {
			Report.writeHTMLLog("估价成功页", "评估总价错误：" + evaTotal+ "" + "", Report.FAIL,"");
		}
		//输出评估均价
        String avePrice=driver.getText(Init.G_objMap.get("anjuke_gujia_avePrice"),"评估总价");	
		if (avePrice != "-"){
		//System.out.println("评估总价:"+evaTotal);
		Report.writeHTMLLog("估价报告页", "评估总价：" + avePrice + "万",Report.PASS, "");
		}
		else {
			Report.writeHTMLLog("估价报告页", "评估总价错误：" + avePrice+ "" + "", Report.FAIL,"");
		}
		//检查房型
		String houseModel = driver.getText(Init.G_objMap.get("anjuke_gujia_houseModel"),"获取房型");
		String gujia_houseT = "房型：" + gujiaInfo.getHouseType_S()+"室"+gujiaInfo.getHouseType_T()+"厅";
		driver.assertContains(houseModel,gujia_houseT);
		driver.assertContains(houseMode2,gujia_houseT);
		
		//检查朝向
		String houseDire = driver.getText(Init.G_objMap.get("anjuke_gujia_houseDire"), "房子朝向");
		driver.assertContains(houseDire,gujiaInfo.getOrientations());
		driver.assertContains(houseDire1,gujiaInfo.getOrientations());
		driver.assertContains(houseDire,houseDire1);
		
		//检查面积
		String houseArea=driver.getText(Init.G_objMap.get("anjuke_gujia_houseArea1"), "房子面积");
		driver.assertContains(houseArea,gujiaInfo.getHouseArea());
		driver.assertContains(houseArea1,gujiaInfo.getHouseArea());
		driver.assertContains(houseArea,houseArea1);
		
		//检查装修情况
		String houseFixture=driver.getText(Init.G_objMap.get("anjuke_gujia_fixture"), "装修情况");
		driver.assertContains(houseFixture,gujiaInfo.getFitmentInfo());
		driver.assertContains(houseFixture1,gujiaInfo.getFitmentInfo());
		driver.assertContains(houseFixture,houseFixture1);
		
		//检查楼层
		String houseBuild=driver.getText(Init.G_objMap.get("anjuke_gujia_louceng"), "楼层");
		String houseInfo = "楼层：" + gujiaInfo.getFloorCur() + "/"+ gujiaInfo.getFloorTotal();
		driver.assertContains(houseBuild,houseInfo);
		driver.assertContains(houseBuild1,houseInfo);
		driver.assertContains(houseBuild,houseBuild1);
		
		//检查房屋类型
		String houseType=driver.getText(Init.G_objMap.get("anjuke_gujia_type"), "估价页房屋类型");
		//		String houseType1=driver.getText(Init.G_objMap.get("anjuke_gujia_administrationType"), "管理中心房屋类型");
		driver.assertContains(houseType,gujiaInfo.getHouseType());
		//		driver.assertContains(houseType1,gujiaInfo.getHouseType());
		//		driver.assertContains(houseType,houseType1);
		
		//检查年代
		String houseAge=driver.getText(Init.G_objMap.get("anjuke_gujia_houseAges"), "年代");
		driver.assertContains(houseAge,gujiaInfo.getBuildYear());
		
		//离小区出口距离
		String s=driver.getText(Init.G_objMap.get("anjuke_gujia_xiaoQuChuKou"), "距小区出口");
		//s = s.toString();
		//System.out.println(s);
		String s1[]=s.split("：");
		//System.out.println(s1[0]);
		//System.out.println(s1[1]);
		String chukou1=s1[1];
		String chukou2="离小区出口"+chukou1;
		driver.assertContains(attance,chukou2);
		
		//自有车位
		String chewei=driver.getText(Init.G_objMap.get("anjuke_gujia_cheWei"), "自有车位");
		driver.assertContains(chewei1,chewei);
		
		//景观房
		String jingguan1=driver.getText(Init.G_objMap.get("anjuke_gujia_jingGuanFang"), "景观房");
		String s2[]=jingguan1.split("：");
		String jg1=s2[1];
		String jg2="景观概况："+jg1;
		driver.assertContains(jingguan,jg2);
		
		//小区配套
		String peitao1=driver.getText(Init.G_objMap.get("anjuke_gujia_xiaoQuPeiTao"), "小区配套");
		driver.assertContains(peitao,peitao1);
		
		//面积
		String mianji1=driver.getText(Init.G_objMap.get("anjuke_gujia_mianJi"), "面积");
		driver.assertContains(mianji,mianji1);
		
		//检查月均价不为“—”
		String aveMonth=driver.getText(Init.G_objMap.get("anjuke_gujia_monAvePrice"),"月均价");
		if(aveMonth !="-");{
			Report.writeHTMLLog("估价报告页", "月均价：" + aveMonth + "元/平米",Report.PASS, "");
		}
	}
}





