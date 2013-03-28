package com.anjuke.ui.testcase;

import java.util.Set;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.*;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;

/**脚本检查点如下：
 * 1、安居客首页-服务之星-服务等级icon
 * 2、经纪人列表页-服务等级icon
 * @author jessili
 */
public class AnjukeTycoonUfsIcon { 
	Set<String> list = null;
	public Browser bs = null;
	private String baseUrl;
	
	@BeforeMethod
	public void setUp() throws Exception {
		bs = FactoryBrowser.factoryBrowser();
		baseUrl = "http://shanghai.anjuke.com";	
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		bs.quit();
		bs = null;
	}
	
	@Test(groups = { "unstable" })
	public void main() throws Exception {			
		
		//检查安居客首页-服务之星-服务等级
		ServiceGradeIconOfServiceStar();
		
		bs.click(Ajk_HomePage.SearchExcellentBroker, "点击：寻找优秀经纪人");
		
		//切换窗口
		bs.switchWindo(2);
		
		//检查经纪人列表-服务等级
		ServiceGradeIconOfTycoon();		
	}
	
	public void ServiceGradeIconOfServiceStar(){
		//打开安居客首页
		bs.get(baseUrl);
		//检查安居客首页-服务之星-服务等级
		for(int i = 1;i<=2;i++){
			if(bs.check(Ajk_HomePage.SERVICEGRADE(i))){
				Report.writeHTMLLog("安居客首页-服务之星-服务等级icon检查", "第"+i+"个经纪人的服务等级icon正常展示", Report.PASS, "");
			}else{
				Report.writeHTMLLog("安居客首页-服务之星-服务等级icon检查", "第"+i+"个经纪人的服务等级icon未展示", Report.FAIL, "");
			}		
		}		
	}
	
	//检查经纪人列表-服务等级
	public void ServiceGradeIconOfTycoon(){
		int m = bs.getElementCount(Ajk_Tycoon.ServiceGradeIcon);
		int n = bs.getElementCount(Ajk_Tycoon.NoServiceGradeIcon);
		if(m+n == 10){
			Report.writeHTMLLog("经纪人列表-服务等级icon检查", "服务等级正常展示", Report.PASS, "");
		}else{
			Report.writeHTMLLog("经纪人列表-服务等级icon检查", "服务等级展示异常", Report.FAIL, "");
		}		
	}
}
