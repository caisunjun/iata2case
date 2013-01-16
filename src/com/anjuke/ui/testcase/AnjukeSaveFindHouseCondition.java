package com.anjuke.ui.testcase;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Ajk_HomePage;
import com.anjuke.ui.page.Ajk_MemberManpropcond;
import com.anjuke.ui.page.Ajk_Sale;
import com.anjuke.ui.publicfunction.PublicProcess;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.init.Init;
import com.anjukeinc.iata.ui.report.Report;

/**
 * 该测试用例主要完成保存搜房条件操作，逻辑如下
 * 1、访问二手房列表页面
 * 2、选择找房条件
 * 3、点击订阅该找房条件
 * 4、判断该找房条件是否存在，如果存在则删除找房条件，并且在此订阅
 * 5、如果条件不存在，则保存成功后，进入条件列表页，验证保存是否成功
 * @author Gabrielgao
 * @time 2012-04-13 13:30
 * @updateAuthor ccyang
 * @last updatetime 2013-01-06 15:40
 */
public class AnjukeSaveFindHouseCondition{
	private Browser driver = null;
	private Map<String,String> condition = null;
	
	@BeforeMethod
	public void startUp(){
	driver = FactoryBrowser.factoryBrowser();
	condition = new HashMap<String,String>();
	}
	@AfterMethod
	public void tearDown(){
		driver.quit();
		driver=null;
	}
    @SuppressWarnings("deprecation")
	@Configuration(afterTestClass = true)
	public void doBeforeTests() {
		System.out.println("***AnjukeSaveFindHouseCondition is done***");
	}
	//(timeOut = 250000)
	@Test
	public void testSaveCon(){
		//Report.setTCNameLog("保存搜索条件-- AnjukeSaveFindHouseCondition --williamhu");
		//普通用户登录
//	    driver.deleteAllCookies();
		String loginName = PublicProcess.logIn(driver, "小瓶盖001", "6634472", false,0);
		//判断用户是否登录成功
		if(!(loginName==null||loginName.equals(""))){
			Report.writeHTMLLog("user login", "user login sucess,and name is: "+loginName, Report.DONE, "");
		}else{
			String ps = driver.printScreen();
			Report.writeHTMLLog("user login", "user login fail,and name is: "+loginName, Report.FAIL, ps);
		}
		//访问二手房列表
		driver.click(Ajk_HomePage.SALETAB, "访问二手房列表");
		//选择区域
		driver.check(Ajk_Sale.S_SELECT("浦东"),30);
		driver.click(Ajk_Sale.S_SELECT("浦东"), "选择区域");
		condition.put("range", "浦东");
		//等待加载完毕
		driver.check("//form[@id='propcondform']/div/span[2]");
		//选择售价
		driver.click(Ajk_Sale.S_SELECT("100-120万"), "选择价格");
		condition.put("price", "100-120万元");
		driver.check("//form[@id='propcondform']/div/span[3]");
		//选择面积
		driver.click(Ajk_Sale.S_SELECT("90-110平米"), "选择面积");
		condition.put("area", "90-110平米");
		driver.check("//form[@id='propcondform']/div/span[4]");
		//选择房型
		driver.click(Ajk_Sale.S_SELECT("三室"), "选择房型");
		condition.put("room", "三室");
		driver.check("//form[@id='propcondform']/div/span[5]");
		//选择房屋类型
		driver.moveToElement("//*[@id='condusetype_id']");
		driver.click("//*[@id='condusetype_id']/ul/li[3]/a", "选择类型");
//		driver.findElement("//a[contains(.,'老公房')]", "", 10).click();
//		System.out.println("老公房标题为：" + driver.getTitle());
//		driver.click(Init.G_objMap.get("anjuke_sale_condition_type"), "选择类型");
		condition.put("type", "公寓");
		driver.check("//form[@id='propcondform']/div/span[6]");
		//访问二手房列表
		driver.click(Ajk_HomePage.SALETAB, "访问二手房列表");
		driver.check(Ajk_Sale.S_SELECT("浦东"));
		//获取上次找房条件
		String searchCondition = driver.getText(Ajk_Sale.LastCond, "获取上次访问条件");
		//比较找房条件
		equalCondition(searchCondition,"[");
		//点击保存找房条件按钮
		driver.click(Ajk_Sale.SaveCond, "保存找房条件");
		//判断是否保存成功
		if(driver.check(Ajk_Sale.SaveCondPopUp)){
			//获取返回的提示信息
			String returnmess = getMessage();
			//找房条件未收藏过
			if(returnmess.equals("成功保存了找房条件！")){
				verifyCondition();
			}else if(returnmess.equals("您已经保存过这个条件啦！")){
				driver.click(Ajk_Sale.CheckSaveCond, "去订阅条件列表");
				driver.switchWindo(2);
				deleteAllCondition();
				driver.close();
				driver.switchWindo(1);
				driver.click(Ajk_Sale.CloseSaveCondPopUp, "继续找房");
				//再次保存找房条件
				driver.click(Ajk_Sale.SaveCond, "保存找房条件");
				
				
				if(driver.check(Ajk_Sale.SaveCondPopUp)){
					if(!getMessage().equals("成功保存了找房条件！")){
						for(int i = 0; i < 5; i ++){
							try {
								Report.writeHTMLLog("保存找房条件", "当前提示信息为显示成功，等待一秒", Report.DONE, "");
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if(getMessage().equals("成功保存了找房条件！"))
								break;
						}
					}
					if(getMessage().equals("成功保存了找房条件！")){
						verifyCondition();
					}else{
						String ps = driver.printScreen();
						Report.writeHTMLLog("订阅条件失败", "再次保存失败-提示已经存在", Report.FAIL, ps);
					}
					
					
					
				}else{
					String ps = driver.printScreen();
					Report.writeHTMLLog("订阅条件失败", "删除所有订阅条件，再次保存失败-没有弹出框", Report.FAIL, ps);	
				}
			}else{
				String ps = driver.printScreen();
				Report.writeHTMLLog("订阅条件失败", "订阅条件失败，提示信息为："+returnmess, Report.FAIL, ps);	
			}
		}else{
			String ps = driver.printScreen();
			Report.writeHTMLLog("订阅条件失败", "订阅条件失败，没有出现弹出框", Report.FAIL, ps);	
		}

	}
	//比较找房条件
	private boolean equalCondition(String searchCon,String split){
		//保存的找房条件
		String lastCon = null;
		//空格
		if(split.equals("[")){
			lastCon = "[ "+condition.get("range")+" "+condition.get("price")+" "+condition.get("area")+" "+condition.get("room")+" "+condition.get("type")+" ]";
		}
		//逗号
		if(split.equals("，")){
			lastCon = condition.get("range")+"，"+condition.get("price")+"，"+condition.get("area")+"，"+condition.get("room")+"，"+condition.get("type");
		}
		//null
		if(split.equals("top")){
			//lastCon = condition.get("range")+condition.get("price")+condition.get("area")+condition.get("room")+condition.get("type")+"二手房";
			lastCon = condition.get("range")+condition.get("price")+condition.get("area")+condition.get("room")+condition.get("type")+"二手房";
		}

		
		if(searchCon.equals(lastCon)){
			Report.writeHTMLLog("比较保存找房条件", "找房条件与保存一致", Report.PASS, "");
			return true;
		}else{
			String ps = driver.printScreen();
			Report.writeHTMLLog("比较保存找房条件", "找房条件与保存不一致<br>"+searchCon+"<br>"+lastCon, Report.FAIL, ps);
			return false;
		}		
	}
	//验证保存条件是否成功
	private void verifyCondition(){
		//用于判断是否存在相同的多条数据
		int equalCount=0;
		//订阅管理->找房条件
		driver.click(Ajk_Sale.CheckSaveCond, "订阅管理->找房条件");
		//跳转到新窗口
		driver.switchWindo(2);
		//判断订阅管理数据是否为空
		int count = (driver.getElementCount(Ajk_MemberManpropcond.ListCount)-1)/2;
		Report.writeHTMLLog("获取数据数量", "数据数量："+count, Report.DONE, "");
		if(count != 0){
			for(int i=1;i<=count;i++){
				String con_top = driver.getText("//form[@id='manacondform']/table/tbody/tr["+(i*2)+"]/td[@class='cond']/div/a", "获取上方的条件");
				if(equalCondition(con_top,"top")){
					Report.writeHTMLLog("订阅管理-找房条件", "保存找房条件成功", Report.PASS, "");
					equalCount=equalCount+1;
				}
			}
			if(equalCount>1){
				String ps = driver.printScreen();
				Report.writeHTMLLog("订阅管理-找房条件", "保存找房条件失败，存在相同的找房条件共:"+equalCount+"条", Report.FAIL, ps);
			}
			if(equalCount==0){
				String ps = driver.printScreen();
				Report.writeHTMLLog("订阅管理-找房条件", "保存找房条件失败，不存在保存的条件", Report.FAIL, ps);
			}
		}else{
			String ps = driver.printScreen();
			Report.writeHTMLLog("订阅管理-找房条件", "保存找房条件失败，列表没有数据", Report.FAIL, ps);
		}
		//删除数据
		deleteAllCondition();
	}
	//删除找房条件
	private void deleteAllCondition(){
		int count = (driver.getElementCount(Ajk_MemberManpropcond.ListCount)-1)/2;
		Report.writeHTMLLog("获取数据数量", "列表中数据数量："+count, Report.DONE, "");
		if(count != 0){
			for(int i=1;i<=count;i++){
				driver.click(Ajk_MemberManpropcond.DeleteFirstCond, "删除保存的条件");
				driver.doAlert("确定");
				//点完确定按钮后检查保存按钮是否可以被捕获到，如果否则表明当前还在alert窗口中，则继续等
				driver.check(Ajk_MemberManpropcond.DeleteFirstCond,10);
			}
		}else{
			String ps = driver.printScreen();
			Report.writeHTMLLog("订阅管理-找房条件", "找房条件列表为空", Report.FAIL, ps);
		}	
		if(driver.check(Ajk_MemberManpropcond.NoCond)){
			Report.writeHTMLLog("订阅管理-找房条件", "列表数据删除成功", Report.PASS, "");
		}
	}
	//获取提示信息
	private String getMessage(){
		String message = driver.getText(Ajk_Sale.SaveCondPopUpMessage, "获取提示信息");
		while(message.equals("Waiting ...")){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			message = driver.getText(Ajk_Sale.SaveCondPopUpMessage, "获取提示信息");
		}
		return message;
	}
}
