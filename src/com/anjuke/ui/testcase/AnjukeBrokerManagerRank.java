package com.anjuke.ui.testcase;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;
/**
 * 经纪人列表页最受欢迎经纪人排名情况
 * @Author chuzhaoqin
 * @time 2012-12-27
 */

public class AnjukeBrokerManagerRank {
	private Browser driver=null;
	private String  username;
	private String  password;
	private String  str1;
	private String  str2;
	
	
	@BeforeMethod
	public void setUp() throws Exception {
		driver = FactoryBrowser.factoryBrowser();
		
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
		driver = null;
//		Report.seleniumReport("shanghai.anjuke.com", "最受欢迎的经纪人");
	}
	    @Test
    public void DingJia(){
	    username="13370290211";//经纪人登录
		password="anjukeqa";
		PublicProcess.dologin(driver, username, password);	
			
			
	    driver.get("http://shanghai.anjuke.com/tycoon/");//进入经纪人列表		        		
		int[] i = new int[10];
		str1=driver.getText("//*[@id='content']/div[3]/div[1]/div/div[2]/dl/dd[1]", "第一名", 30);
	    String  p = str1.replaceAll("\\D+","");
	    i[0]=Integer.parseInt(p);
		System.out.println(i[0]);
			
		for(int j=1;j<10 ;j++){	
		str2=driver.getText("//*[@id='content']/div[3]/div[1]/div/ul/li["+ j +"]/span", "第"+j+"名",30);
		String  c = str2.replaceAll("\\D+","");
		i[j] = (int) Integer.parseInt(c);
		System.out.println(i[j]);	
		}
			
		for(int b=0 ; b<9 ;b++){
		if(i[b]>= i[b+1]){
		System.out.println(i[b] + ">=" + i[b+1]);
		Report.writeHTMLLog("排名正确","筛选排名分",Report.PASS,"");
					
		}else{
		Report.writeHTMLLog("排名不正确","",Report.FAIL,driver.printScreen());
		 }
	   }
   }						
}					
			
	   
