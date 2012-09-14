package com.anjuke.ui.testcase;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

//import com.anjuke.ui.bean.AnjukeSaleInfo;
import com.anjuke.ui.publicfunction.PublicProcess;


public class sellhouse {
	private String  baseUrl;
	private String  str;
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();
	
	@BeforeMethod
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = ("http://shanghai.anjuke.com/");
		
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}


	@AfterMethod
	public void tearDown() throws Exception {
		driver.quit();
		

		}

/*Test
	public void sale() throws Exception{
	for (int i=0; i<=2;i++);
	    test1();
	
	}

	*/
		@Test
		public void test1() throws Exception{
		driver.get (baseUrl);
		driver.get("http://agent.anjuke.com/login/");
		driver.findElement(By.id("loginName")).clear();
		driver.findElement(By.id("loginName")).sendKeys("ajk_sh");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("anjukeqa");
		driver.findElement(By.cssSelector("input.btn")).click();
		driver.findElement(By.id("my_app_sale")).click();
		driver.findElement(By.id("txtCommunity")).clear();
	    driver.findElement(By.id("txtCommunity")).sendKeys("申良花园");
		waitForPageToLoad(1000);
		driver.findElement(By.cssSelector("span.code")).click();
		driver.findElement(By.id("txtProPrice")).clear();
		driver.findElement(By.id("txtProPrice")).sendKeys("115");
		driver.findElement(By.id("txtAreaNum")).clear();
		driver.findElement(By.id("txtAreaNum")).sendKeys("112");
		driver.findElement(By.id("txtRoomNum")).clear();
		driver.findElement(By.id("txtRoomNum")).sendKeys("2");
		driver.findElement(By.id("txtHallNum")).clear();
		driver.findElement(By.id("txtHallNum")).sendKeys("2");
		driver.findElement(By.id("txtToiletNum")).clear();
		driver.findElement(By.id("txtToiletNum")).sendKeys("2");
		driver.findElement(By.id("txtProFloor")).clear();
		driver.findElement(By.id("txtProFloor")).sendKeys("2");
		driver.findElement(By.id("txtFloorNum")).clear();
		driver.findElement(By.id("txtFloorNum")).sendKeys("3");
		new Select(driver.findElement(By.id("radUseType"))).selectByVisibleText("老公房");
		new Select(driver.findElement(By.id("radFitment"))).selectByVisibleText("普通装修");
		new Select(driver.findElement(By.id("radHouseOri"))).selectByVisibleText("东");
		driver.findElement(By.id("txtHouseAge")).clear();
		driver.findElement(By.id("txtHouseAge")).sendKeys("2005");
		driver.findElement(By.id("txtProName")).clear();
		driver.findElement(By.id("txtProName")).sendKeys("测试房源 请勿联系");
		driver.findElement(By.id("btnSubmit")).click();
		WebElement description = driver.findElement(By.xpath("//td[@id='cke_contents_txtExplain']/iframe"));
		driver.switchTo().frame(description);
		driver.switchTo().activeElement().sendKeys("测试 测试 测试");
		driver.switchTo().defaultContent();
		driver.findElement(By.id("btnSubmit")).click();
		driver.findElement(By.linkText("管理出售")).click();
		str = driver.findElement(By.linkText("管理出售")).getText();
		
		{Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(str);
		if(m.find()){
			System.out.println(m);}}
		//driver.findElement(By.xpath(".//*[@id='my_bpm_on_sale']")).click();
		
		//driver.findElement(By.id("radSaleType_2")).click();
		//driver.findElement(By.id("chkUploadPic")).click();
		//driver.findElement(By.id("btnNext")).click();
		
		

	}
	@Test
	public void waitForPageToLoad(long longtime) {
		try {
			Thread.sleep(longtime);
		} catch (InterruptedException exception) {
			exception.getStackTrace();
		}
	}

}


