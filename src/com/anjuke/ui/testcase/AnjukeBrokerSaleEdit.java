package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.bean.AnjukeSaleInfo;
import com.anjuke.ui.publicfunction.BrokerSaleOperating;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
/**
 * 该用例完成安居客出售更新操作，逻辑如下
 * 1、编辑出售信息，添加图片附件
 * 2、更新成功后，验证出售基本信息
 * 3、在出售详细页，验证房屋出售详细信息
 * 
 * @Author gabrielgao
 * @time 2012-04-11 17:00
 * @UpdateAuthor ccyang
 * @last updatetime 2012-08-16 16:00
 */
public class AnjukeBrokerSaleEdit {
	private Browser driver = null;
	private AnjukeSaleInfo saleInfo = new AnjukeSaleInfo();
	private AnjukeSaleInfo updateInfo = new AnjukeSaleInfo();
	private boolean needPic = false;
	
	@BeforeMethod
	public void startUp(){
	driver = FactoryBrowser.factoryBrowser();
	saleInfo = saleInfo_init();
	updateInfo = updateInfo_init();
	}
	
	@AfterMethod
	public void tearDown(){
		driver.quit();
		driver=null;		
	}
	//发布时的房源信息，如果账号里没房源需要发布的话
	private AnjukeSaleInfo saleInfo_init() {
		saleInfo.setCommunityName("潍坊八村");//小区
		saleInfo.setPriceTaxe("200");//售价
		saleInfo.setHouseArea("120.00");//面积	
		saleInfo.setHouseType_S("3");//室
		saleInfo.setHouseType_T("2");//厅
		saleInfo.setHouseType_W("1");//卫
		saleInfo.setFloorCur("3");//第几层
		saleInfo.setFloorTotal("6");//共几层
		saleInfo.setHouseType("公寓");//房屋类型
		saleInfo.setFitmentInfo("精装修");//装修类型
		saleInfo.setOrientations("东西");//朝向
		saleInfo.setBuildYear("2009");//建造年代
		String time = PublicProcess.getNowDateTime("HH:mm:ss");
		saleInfo.setHouseTitle("发布出售请勿联系谢谢" + time);
		saleInfo.setHouseDescribe("发布出售，此房源为测试房源，房源信息没有经过验证，请勿联系，感谢您对的支持。谢谢！");
		return saleInfo;
	}
	//编辑时的房源信息
	private AnjukeSaleInfo updateInfo_init() {
		//updateInfo.setCommunityName("潍坊八村");//小区
		updateInfo.setPriceTaxe("123");//售价
		updateInfo.setHouseArea("99.00");//面积	
		updateInfo.setHouseType_S("1");//室
		updateInfo.setHouseType_T("1");//厅
		updateInfo.setHouseType_W("1");//卫
		updateInfo.setFloorCur("1");//第几层
		updateInfo.setFloorTotal("12");//共几层
		updateInfo.setHouseType("公寓");//房屋类型
		updateInfo.setFitmentInfo("普通装修");//装修类型
		updateInfo.setOrientations("南北");//朝向
		updateInfo.setBuildYear("1999");//建造年代
		String time = PublicProcess.getNowDateTime("HH:mm:ss");
		updateInfo.setHouseTitle("发布出售编辑请勿联系" + time);
		updateInfo.setHouseDescribe("发布出售编辑，此房源为测试房源，房源信息没有经过验证，请勿联系，感谢您的支持。谢谢！目前为编辑状态");
		return updateInfo;
	}
	
	//(timeOut = 200000)
	@Test (groups = {"unstable"})
	public void editSale() throws InterruptedException{
//		driver.deleteAllCookies();
		//Report.setTCNameLog("管理出售编辑-- AnjukeUpdateReleaseSale --Hendry_huang");
		String casestatus = "";
		String testing = "testing";
		casestatus = Init.G_config.get("casestatus");

		if(testing.equals(casestatus))
		{
			saleInfo.setUserName(PublicProcess.logIn(driver, "test1", "123456",false, 1));
		}
		else
		{
			saleInfo.setUserName(PublicProcess.logIn(driver, "ajk_sh", "anjukeqa",false, 1));
		}
		
		driver.get("http://my.anjuke.com/user/brokerpropmanage/W0QQactZsale#proptop");
		//driver.check(Init.G_objMap.get("anjuke_wangluojingjiren_sale_update_firstEdit"));
		
		if(driver.check("id^edit_0")){
			BrokerSaleOperating.editSale(driver, updateInfo, needPic);
		}else{
			BrokerSaleOperating.releaseSale(driver, saleInfo,needPic);
		}
	}

}
