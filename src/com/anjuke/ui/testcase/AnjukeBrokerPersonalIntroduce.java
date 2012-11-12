package com.anjuke.ui.testcase;

//import java.util.HashSet;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import com.anjuke.ui.page.Ajk_ShopView;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
//import com.anjukeinc.iata.ui.report.Report;

/**
 * 店铺管理--个人介绍,主要验证这些功能是否可以点击
 * @Author chuzhaoqin
 */

public class AnjukeBrokerPersonalIntroduce {
	 Browser bs = null;
	 String username="ajk_sh";
	 String password="ajk_sh";
	 private boolean isLogin = false;
	 String  baseUrl;
	 
	 public void waitForPageToLoad(long longtime) {
			try {
				Thread.sleep(longtime);
			} catch (InterruptedException exception) {
				exception.getStackTrace();
			}
	 }


	 
	 @BeforeMethod
	    public void setUp() {
		    baseUrl = "http://my.anjuke.com/user/broker/profile";
		    bs = FactoryBrowser.factoryBrowser();
	       
	    }
	  @AfterMethod
	     public void tearDown(){
		  bs.quit();
		  bs = null;
		  

		  
}
	  @Test
	  private void dianpuInfo(){
		    username = "shtest";
			password = "anjukeqa";
			PublicProcess.dologin(bs, username, password);
			isLogin = true;
			bs.get(baseUrl);
			
			
			
			//出生地    筛选条件 
			waitForPageToLoad(500);
	        bs.select(Ajk_ShopView.PROAndCity, "上海市");
	        bs.select(Ajk_ShopView.Region, "浦东新区");
	        bs.click(Ajk_ShopView.Region1, "公开");
	        //年龄不公开
	        bs.click(Ajk_ShopView.Age2, "不公开");
	        //生肖
	        bs.select(Ajk_ShopView.shenXiao, "羊");
	        bs.click(Ajk_ShopView.shenXiao2,"不公开");
	        //星座
	        bs.select(Ajk_ShopView.xingzuo, "射手座");
	        bs.click(Ajk_ShopView.xingzuo1, "公开");
	        
	        //生成书籍list
	        waitForPageToLoad(500);
		   	int n;
	        for(int i=0;i<5;i++){
	        	Random r = new Random();
		 		n= r.nextInt(11)+1; 
	        	bs.click("//*[@id='hotTag_book']/tbody/tr[2]/td/a["+n+"]", "点击书籍");
	       int m;
	       for (int a=0;a<5;a++);
	            Random s = new Random();
	            m= s.nextInt(13)+1;
	        	bs.click("//*[@id='hotTag_food']/tbody/tr[2]/td/a["+m+"]", "选择美食");
	       int l;
	  	   for (int b=0;b<5;b++);
	  	        Random q = new Random();
	  	        l= q.nextInt(14)+1;
	  	        bs.click("//*[@id='hotTag_acti']/tbody/tr[2]/td/a["+l+"]", "选择娱乐活动");  
	  	        //bs.click(Ajk_ShopView.submit, "点击确认保存");
	        }
	  }       	        	    	
}