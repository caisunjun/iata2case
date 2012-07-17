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
 * 该用例完成安居客出售发布操作，逻辑如下
 * 1、填写出售信息，添加图片附件
 * 2、发布成功后，验证出售基本信息
 * 3、在出售详细页，验证房屋出售详细信息
 * @Author gabrielgao
 * @time 2012-04-11 17:00
 * @UpdateAuthor ccyang
 * @last updatetime 2012-07-11 10:00
 */
public class AnjukeBrokerSaleRelease {
	private Browser driver = null;
	private AnjukeSaleInfo saleInfo = new AnjukeSaleInfo();
	private ArrayList<String> houseListNumber = null;

	@BeforeMethod
	public void startUp() {
		driver = FactoryBrowser.factoryBrowser();
		driver.deleteAllCookies();
		saleInfo = saleInfo_init();
	}

	@AfterMethod
	public void tearDown() {
		Report.seleniumReport("", "");
		driver.quit();
		driver = null;
	}
	
	private AnjukeSaleInfo saleInfo_init() {
		saleInfo.setCommunityName("潍坊八村");
		saleInfo.setPriceTaxe("200");
		saleInfo.setHouseArea("120.00平米");	
		saleInfo.setHouseType_S("3室");
		saleInfo.setHouseType_T("2厅");
		saleInfo.setHouseType_W("1卫");
		saleInfo.setFloorCur("3");
		saleInfo.setFloorTotal("6");
		saleInfo.setHouseType("公寓");
		saleInfo.setFitmentInfo("装修：精装修");
		saleInfo.setOrientations("朝向：南");
		saleInfo.setBuildYear("建造年代：2009");
		String time = PublicProcess.getNowDateTime("HH:mm:ss");
		saleInfo.setHouseTitle("发布出售请勿联系谢谢" + time);
		saleInfo.setHouseDescribe("发布出售，此房源为测试房源，房源信息没有经过验证，请勿联系，感谢您对的支持。谢谢！");
		return saleInfo;
	}

	//(timeOut = 200000)
	@Test(groups = {"unstable"})
	public void releaseSale() {
		driver.deleteAllCookies();
		//Report.setTCNameLog("发布出售-- AnjukeReleaseSale --Hendry_huang");
		saleInfo.setUserName(PublicProcess.logIn(driver, "ajk_sh", "anjukeqa",false, 1));
		houseListNumber = houseNumber();
		driver.get("http://my.anjuke.com/user/broker/property/sale/step1");
		if (driver.check(Init.G_objMap.get("ajk_releaseSale_div_full"))) {
			String ps = driver.printScreen();
			houseListNumber = houseNumber();
			Report.writeHTMLLog("发布出售", "抱歉，您本期发布的新房源已达上限，无法再发布了。"+ "本期已发布的房源:"+houseListNumber.get(0)+"剩余发布房源数:"+houseListNumber.get(1),
					Report.FAIL, ps);
		} else {
			// 设置小区名称
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_xiaoqu_yinyu"), "潍坊八村","小区名称");
//			driver.switchToIframe(Init.G_objMap.get("anjuke_wangluojingjiren_salse_ifrCommunityList"),"选择小区");
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_xiaoqu_nameClick"),"选择小区");
//			driver.exitFrame();

			// 税费自理价
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_proPrice"),	"200", "税费自理价：200万");

			// 设置房屋面积
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chanzhengArea"), "120","产证面积");
			
			// 设置房型
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_room"), "3","3房");
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_hall"), "2","2厅");
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangxing_toilet"), "1","1卫");

			// 设置楼层
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_louceng"),"3", "当前第三层");
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_loucengCnt"), "6", "共6层");

			// 设置房屋类型  --新版发房公寓是下拉里的第3个
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_gongyu_click"), "选择公寓");

			// 装修情况
			// XPATH 适配IE6不行 使用id定位IE6   --新版发房ie6还没改 --ie6 must die
			// driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fitMent2_ie6"),"装修情况：精装修");
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fitMent4"),"装修情况：精装修");

			// 朝向
			// XPATH 适配IE6不行 使用id定位IE6   --新版发房ie6还没改 --ie6 must die
			// driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chaoxiang_south_ie6"),"方向：朝南");
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_chaoxiang_south"),"方向：朝南");

			// 建筑年代
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_houseAge"),	"2009", "建筑年代：2009年");

			// 房源标题
			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangyuanbiaoti_name"),saleInfo.getHouseTitle(), "房源标题");

			// 房源描述
			driver.switchToIframe(Init.G_objMap.get("anjuke_wangluojingjiren_salse_ifrExplain"),"获取房源描述frame");
			driver.activeElementSendkeys("发布出售，此房源为测试房源，房源信息没有经过验证，请勿联系，感谢您对的支持。谢谢！");
			driver.exitFrame();
			
//			// 计算单价
//			double danjia = (double) 2000000.00 / (double) 120.00;
//			/*String price = new BigDecimal(danjia).setScale(0,
//					BigDecimal.ROUND_HALF_DOWN).toString();*/
//			String price = Double.toString(Math.floor(danjia)); 
//			price.indexOf(".");
//			String price1 = price.substring(0,price.indexOf("."));
//			saleInfo.setPrice("单价：" + price1 + "元");
//			// 房东到手价
//			/*driver.type(Init.G_objMap
//					.get("anjuke_wangluojingjiren_sale_landladyPrice"), "180",
//					"房东到手价：180万");
//			saleInfo.setPriceGet("房东到手价：180万");*/
			

			// 设置租约
			// XPATH 适配IE6不行 使用id定位IE6
			// driver.click("id^radCurrentState_0", "租约：是");
//			driver.click(Init.G_objMap
//					.get("anjuke_wangluojingjiren_sale_zuyue_true_ie6"), "租约：是");
			// driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_zuyue_true"),
			// "租约：是");
			
			// 备注信息           --新版发房no备注...了
//			driver.type(Init.G_objMap.get("anjuke_wangluojingjiren_sale_beizhu_info"),
//					"测试房源，请勿联系，谢谢", "房源备注");
	
			// 发布类型		
			// XPATH 适配IE6不行 使用id定位IE6
			// driver.click("id^radSaleType_2", "发布类型：只在我的店面显示");
			// driver.click(Init.G_objMap.get("anjuke_sale_fangyuanleixing_type2_ie6"),"发布类型：只在我的店面显示");
			// driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_fangyuanleixing_type2"),"发布类型：只在我的店面显示");
			
			//--新版发房用单选区分推荐与否   有这句就是非推荐房源
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_tuijiangaifangyuan"),"推荐该房源，在二手房列表页显示");
			
			// 点去上传照片  进入发房第二步
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_nextBtn"),"去上传照片");
			// 上传房型图
			PublicProcess.uploadPic(driver, "sale");
			// 点击发布房源
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_step2_submitup"),"发房第二步-发布房源");
			// 判断是否发布成功
			String releaseMess = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSuccMess"),"获取发布后提示信息");
			
			//houseListNumber.get(1) 拿到的是剩余发布房源数：“xx 套”
			String houseList = houseListNumber.get(1);
			houseList=houseList.replaceAll(" 套", "");
			
			if (releaseMess.equals("发布成功！") && Integer.parseInt(houseList)>= 10) {
				Report.writeHTMLLog("发布成功", "出售房源发布成功"+"本期已发布的房源:"+houseListNumber.get(0)+"剩余发布房源数:"+houseListNumber.get(1), Report.PASS, "");
			} else if(releaseMess.equals("发布成功！") && Integer.parseInt(houseList) < 10){
				Report.writeHTMLLog("发布成功", "出售房源发布成功"+"本期已发布的房源:"+houseListNumber.get(0)+"剩余发布房源数:"+houseListNumber.get(1), Report.WARNING, "");
			}else{
				String ps = driver.printScreen();
				Report.writeHTMLLog("发布失败", "出售房源发布失败" + "本期已发布的房源:"+houseListNumber.get(0)+"剩余发布房源数:"+houseListNumber.get(1), Report.FAIL, ps);
			}
			// 判断预览房源标题------------------------------------新版发房取消了预览
//			String houseTitle = driver.getText(Init.G_objMap
//					.get("anjuke_wangluojingjiren_sale_newSucc_pageTitle"),
//					"获取房源标题");
//			if (houseTitle.equals(saleInfo.getHouseTitle())) {
//				Report.writeHTMLLog("房源标题", "发布后房源标题和输入一致", Report.PASS, "");
//			} else {
//				Report.writeHTMLLog("房源标题", "发布后房源标题和输入不一致", Report.FAIL,
//						driver.printScreen());
//			}
			// 判断发布房源的经纪人姓名------------------------------------新版发房取消了预览
//			String tycoonName = driver.getText(Init.G_objMap
//					.get("anjuke_wangluojingjiren_sale_newSucc_userName"),
//					"获取经纪人姓名");
//			if (tycoonName.equals(saleInfo.getUserName())) {
//				Report.writeHTMLLog("经纪人姓名", "登录经纪人姓名和发布房源经纪人姓名一致",
//						Report.PASS, "");
//			} else {
//				Report.writeHTMLLog("经纪人姓名", "登录经纪人姓名和发布房源经纪人姓名不一致",
//						Report.FAIL, driver.printScreen());
//			}
			// 房型检查------------------------------------新版发房取消了预览
//			String infoAppend = saleInfo.getHouseType_S()
//					+ saleInfo.getHouseType_T() + "， "
//					+ saleInfo.getHouseArea() + "，" + saleInfo.getPrice()
//					+ "， 楼层： " + saleInfo.getFloorCur() + "/"
//					+ saleInfo.getFloorTotal() + " ，" + saleInfo.getBuildYear();
//			String fangyuandesc = driver
//					.getText(
//							Init.G_objMap
//									.get("anjuke_wangluojingjiren_sale_sucess_desc"),
//							"获取房源信息").trim();
//			String fangyuan = PublicProcess.splitString(fangyuandesc, "\n");
//			if (infoAppend.equals(fangyuan)) {
//				Report.writeHTMLLog("房型", "房型：" + fangyuan, Report.PASS, "");
//
//			} else {
//				Report.writeHTMLLog("房型", "房型错误：" + fangyuan + "**正确的应该为："
//						+ infoAppend, Report.FAIL, driver.printScreen());
//			}
			// 跳转到房源单页
			driver.click(Init.G_objMap.get("anjuke_wangluojingjiren_sale_newSucc_pageLink"),"点击房源链接");
			/*System.out.println(driver.getWindowHandles());
			try {
				Thread.currentThread().sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
//			------------------------------------新版发房在当前页面打开房源单页
//			driver.switchWindo(2);
			driver.check("className^propInfoTitle");
			// 房源详细页，房源标题检查
			String propInfoTitle = driver.getText("className^propInfoTitle", "获取房源标题");
			/*String propInfoTitle = driver
					.getText(Init.G_objMap
							.get("anjuke_wangluojingjiren_sale_detail_title"),
							"获取房源标题");*/
			if (propInfoTitle.equals(saleInfo.getHouseTitle())) {
				Report.writeHTMLLog("房源详细页", "房源详细页标题：" + propInfoTitle,Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "房源详细页标题错误：" + propInfoTitle+ "**正确的应该为：" + saleInfo.getHouseTitle(), Report.FAIL,driver.printScreen());
			}
			// 税费自理价格检查
			String taxPrice = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_taxPrice"),"获取税费自理价");
			if (taxPrice.equals(saleInfo.getPriceTaxe())) {
				Report.writeHTMLLog("房源详细页", "税费自理价：" + taxPrice + "万",Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "税费自理价错误：" + taxPrice + "万"+ "**应该为：" + saleInfo.getPriceTaxe() + "万",Report.FAIL, driver.printScreen());
			}
			// 房屋单价检查
			String unitPrice = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_rent_detail_housetype"),"获取房屋单价");
			if (unitPrice.equals(saleInfo.getPrice() + "/平米")) {
				Report.writeHTMLLog("房源详细页", "验证房屋单价-->" + unitPrice,Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "验证房屋单价错误-->" + unitPrice+ "**应该为：" + saleInfo.getPrice() + "/平米", Report.FAIL,driver.printScreen());
			}
			// 房东到手价检查  页面无房东到手价
			/*String priceGet = driver
					.getText(
							Init.G_objMap
									.get("anjuke_wangluojingjiren_sale_detail_handPrice"),
							"房东到手价").trim();
			if (priceGet.equals(saleInfo.getPriceGet())) {
				Report.writeHTMLLog("房源详细页", "验证房东到手价-->" + priceGet,
						Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "验证房东到手价错误-->" + priceGet
						+ "**正确为：" + saleInfo.getPriceGet(), Report.FAIL,
						driver.printScreen());
			}*/
			// 检查房型
			String houseType = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_housetype"),"获取房型");
			String saleInfo_houseT = "房型：" + saleInfo.getHouseType_S()+ saleInfo.getHouseType_T() + saleInfo.getHouseType_W();
			if (houseType.equals(saleInfo_houseT)) {
				Report.writeHTMLLog("房源详细页", "房型详细信息：" + houseType,Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "房型详细信息错误：" + houseType + "正确为："+ saleInfo_houseT, Report.FAIL, driver.printScreen());
			}
			// 产证面积
			String houseArea = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_area"), "产证面积");
			if (houseArea.equals("面积：" + saleInfo.getHouseArea())) {
				Report.writeHTMLLog("房源详细页", "产证面积：" + houseArea, Report.PASS,"");
			} else {
				Report.writeHTMLLog("房源详细页", "产证面积错误：" + houseArea + "正确为："+ "产证面积：" + saleInfo.getHouseArea(), Report.FAIL,driver.printScreen());
			}
			// 朝向
			String houseDire = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_dire"), "房子朝向");
			if (houseDire.equals(saleInfo.getOrientations())) {
				Report.writeHTMLLog("房源详细页", "朝向：" + houseDire, Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "朝向错误：" + houseDire + "正确为："+ saleInfo.getOrientations(), Report.FAIL,driver.printScreen());
			}
			// 建筑年代
			String buildYear = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_year"), "建筑年代");
			if (buildYear.equals(saleInfo.getBuildYear() + "年")) {
				Report.writeHTMLLog("房源详细页", "建筑年代：" + buildYear, Report.PASS,"");
			} else {
				Report.writeHTMLLog("房源详细页", "建筑年代错误：" + buildYear + "正确为："+ saleInfo.getBuildYear() + "年", Report.FAIL,driver.printScreen());
			}
			// 楼层
			String floor = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_floor"), "楼层");
			String floorInfo = "楼层：" + saleInfo.getFloorCur() + "/"+ saleInfo.getFloorTotal();
			if (floor.equals(floorInfo)) {
				Report.writeHTMLLog("房源详细页", "楼层情况：" + floor, Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "楼层情况：" + floor + "正确为："+ floorInfo, Report.FAIL, driver.printScreen());
			}
			// 装修
			String fitment = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detail_fitment"), "装修");
			if (fitment.equals(saleInfo.getFitmentInfo())) {
				Report.writeHTMLLog("房源详细页", "装修：" + fitment, Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "装修：" + fitment + "正确为："+ saleInfo.getFitmentInfo(), Report.FAIL,driver.printScreen());
			}
			// 房源描述
			String description = driver.getText(Init.G_objMap.get("anjuke_wangluojingjiren_sale_detaile_desc"), "房源描述");
			if (description.equals(saleInfo.getHouseDescribe())) {
				Report.writeHTMLLog("房源详细页", "房源描述：" + description,Report.PASS, "");
			} else {
				Report.writeHTMLLog("房源详细页", "房源描述：" + description + "正确为："+ saleInfo.getHouseDescribe(), Report.FAIL,driver.printScreen());
			}
			//driver.switchWindo(1);
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
		//如果拿不到经纪人首页那个发布房源数的模块，则说明这个经纪人不是付费的经纪人
		int isPayed = driver.getElementCount("//dl[5]/dd/a");
		if (isPayed !=0 )
		{
			houseln.add(driver.getText("//dl[5]/dd/a", "获取已发布房源数"));
			houseln.add(driver.getText("//dl[6]/dd/a", "获取剩余发布房源数"));
		}
		else
		{
			houseln.add(driver.getText("1", "非付费经纪人·已发布数·假的"));
			houseln.add(driver.getText("1", "非付费经纪人·剩余发布数·假的"));
		}
		return houseln;
	}

}
