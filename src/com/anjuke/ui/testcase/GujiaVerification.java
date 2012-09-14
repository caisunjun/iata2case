package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.AjkGujia;
import com.anjuke.ui.page.AnjukeGujiaInformation;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;

public class GujiaVerification {
		private Browser driver = null;
		private AnjukeGujiaInformation gj = new AnjukeGujiaInformation();
		//private AnjukeGujiaInformation GjInfosuccess = new AnjukeGujiaInformation();

		@BeforeMethod
		public void startUp() {
			driver = FactoryBrowser.factoryBrowser();
			driver.deleteAllCookies();
			gj= gjInfo_init();
			
		}

		@AfterMethod
		public void tearDown() {
			//driver.quit();
			driver = null;
		}		
			
	private AnjukeGujiaInformation gjInfo_init(){
      if (!driver.check(Init.G_objMap.get("anjuke_gujia_mainpanel"))) {
	      String price_T = driver.getText("anjuke_gujia_mainpanel", "评估总价");
	      System.out.println(price_T);
	
	//判断评估区是否存在数据
	if (!driver.check(Init.G_objMap.get("anjuke_gujia_mainpanel"))) {
	// 获取评估总价
	   price_T = driver.getText(Init.G_objMap.get("anjuke_gujia_total"), "评估总价");
	   System.out.println(price_T);
	 
	}
      }
	return gj;
	}
	
	// 获取评估均价
	//price_A = driver.getText(Init.G_objMap.get("anjuke_gujia_avePrice"), "评估总价");
	//System.out.println(price_A); 
	   
	   @Test(timeOut = 2000000000)
		public void gujia() {
			driver.deleteAllCookies();
			GujiaDetail.gj(driver, gj);
}
	}