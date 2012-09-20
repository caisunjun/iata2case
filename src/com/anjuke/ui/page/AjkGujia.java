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
	
	
	
	public static final void GJDetail(Browser driver, AnjukeGujiaInformation info ){
		driver.get(gujiaUrl);
		gujia(driver, info, "Release");
}
	
	public static final void gujia(Browser driver, AnjukeGujiaInformation gujiaInfo, String type){

		//小区名
		if( type.equals("Release")){
			driver.type(Init.G_objMap.get("anjuke_gujia_commname"), gujiaInfo.getCommunityName(), "小区名称");
			driver.findElement(Init.G_objMap.get("anjuke_gujia_commname"), "", 3).sendKeys(" ");
			driver.click(Init.G_objMap.get("anjuke_gujia_commname_keywords"),"下拉框");
			driver.click("//*[@id='li_table_p0']","选择小区");
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
		
		//立刻估价
		driver.click(Init.G_objMap.get("anjuke_gujia_submit"), "立刻估价");
		
	    //切换窗口
		driver.switchWindo(2);
		
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
		
		//检查朝向
		String houseDire = driver.getText(Init.G_objMap.get("anjuke_gujia_houseDire"), "房子朝向");
		driver.assertContains(houseDire,gujiaInfo.getOrientations());
		
		//检查面积
		String houseArea=driver.getText(Init.G_objMap.get("anjuke_gujia_houseArea1"), "房子面积");
		driver.assertContains(houseArea,gujiaInfo.getHouseArea());
		
		//检查装修情况
		String houseFixture=driver.getText(Init.G_objMap.get("anjuke_gujia_fixture"), "装修情况");
		driver.assertContains(houseFixture,gujiaInfo.getFitmentInfo());

		//检查楼层
		String houseBuild=driver.getText(Init.G_objMap.get("anjuke_gujia_louceng"), "楼层");
		String houseInfo = "楼层：" + gujiaInfo.getFloorCur() + "/"+ gujiaInfo.getFloorTotal();
		driver.assertContains(houseBuild,houseInfo);
		
		//检查年代
		String houseAge=driver.getText(Init.G_objMap.get("anjuke_gujia_houseAges"), "年代");
		driver.assertContains(houseAge,gujiaInfo.getBuildYear());
	
		//检查月均价不为“—”
		String aveMonth=driver.getText(Init.G_objMap.get("anjuke_gujia_monAvePrice"),"月均价");
		if(aveMonth !="-");{
			Report.writeHTMLLog("估价报告页", "月均价：" + aveMonth + "元/平米",Report.PASS, "");
		}
		//与上月比较其涨幅
		String zhangfu=driver.getText(Init.G_objMap.get("anjuke_gujia_monRange"),"涨幅");
		if(zhangfu !="-");{
			Report.writeHTMLLog("估价报告页", "涨幅："+zhangfu + "",Report.PASS, "");
		}
		//小区今日房价
		String todayHousePrice=driver.getText(Init.G_objMap.get("anjuke_gujia_todayHousePrice"),"今日小区房价");
		if(todayHousePrice !="-");{
			Report.writeHTMLLog("估价报告页", "小区今日房价："+todayHousePrice + "元/平米",Report.PASS, "");
		}
		//对比本月均价涨幅
		String aveZhangfu=driver.getText(Init.G_objMap.get("anjuke_gujia_conRange"),"今日小区房价");
		if(aveZhangfu !="-");{
			Report.writeHTMLLog("估价报告页", "小区今日房价："+aveZhangfu + "",Report.PASS, "");
		}
	}
}


