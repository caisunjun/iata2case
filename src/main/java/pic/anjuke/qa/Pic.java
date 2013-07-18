package pic.anjuke.qa;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;

/**
 * Ticket #11816 成都——金铺——发布删除写字楼房源 功能：新建写字楼房源 验证写字楼发布及删除是否正常
 * 
 * @author mingyongwang
 */
public class Pic {
	private Browser bs = null;

	@BeforeClass
	public void setUp() {

		bs = FactoryBrowser.factoryBrowser();

	}

	@AfterClass
	public void tearDown() {
		bs.quit();
		bs = null;
	}

	@Test
	public void shanghai() throws InterruptedException {
		String shanghai="上海";
		String sh="http://shanghai";
		String shprimary=sh+".pmt15111.anjuke.test/?from=navigation";
		
		
		// 首页  
		bs.get(shprimary);
		bs.assertContains(bs.getText("//*[@id='switch_apf_id_6']/span[1]","got info of netpage"), shanghai);
		System.out.print("first page  test successfully");
		
		// 二手房房源
		String shhouse=sh+".pmt15111.anjuke.test/sale/p";
		String shhouseurl=null;
		for (int i=1;i<6;i++){
			shhouseurl=shhouse+i+"/";
			System.out.print(shhouseurl);
			bs.get(shhouseurl);
			bs.assertContains(bs.getText("//*[@id='switch_apf_id_6']/span[1]", "shanghai city"), shanghai);
			
		}
		
		
		// 多图
		String multpic=sh+".anjuke.com/sale/hq1-p";
		for (int i=1;i<6;i++){
			String multpicurl=multpic+i+"/";
			bs.get(multpicurl);
			bs.assertContains(bs.getText("//*[@id='switch_apf_id_7']/span[1]", "shanghai city"), shanghai);
		}
		

		// 个人
		String privatepic=sh+".anjuke.com/sale/p";
		for (int i=1;i<6;i++){
			String privatepicurl=privatepic+i+"-tg9/";
			bs.get(privatepicurl);
			bs.assertContains(bs.getText("//*[@id='switch_apf_id_7']/span[1]", "shanghai city"), shanghai);
		}
		
		

		// 二手房小区
		
		String resident=null;
		for (int i=1;i<6;i++){
			resident=sh+".pmt15111.anjuke.test/community/W0QQfromZnavigationQQpZ"+i;
			bs.get(resident);
			bs.assertContains(bs.getText("//*[@id='switch_apf_id_6']/span[1]", "shanghai city"), shanghai);			
		}
				
		
		// 租房房源
		String sh2="http://sh";
		String renthouse=null;
		
		for (int i=1;i<6;i++){
			renthouse=sh2+".pmt15111.zu.anjuke.test/fangyuan/p"+i;
			bs.get(renthouse);
			bs.assertContains(bs.getText("//*[@id='city_selector']/a", "shanghai city"), shanghai);
			
		}

		

		// 整租
		String renthole=null;
		for (int i=1;i<6;i++){
			renthole=sh2+".pmt15111.zu.anjuke.test/fangyuan/p"+i+"-x1/";
			bs.get(renthole);
			bs.assertContains(bs.getText("//*[@id='city_selector']/a", "shanghai city"), shanghai);
			
		}
		
		

		// 合租
		String rentcollect=null;
		for (int i=1;i<6;i++){
			rentcollect=sh2+".pmt15111.zu.anjuke.test/fangyuan/p"+i+"-x2/";
			bs.get(rentcollect);
			bs.assertContains(bs.getText("//*[@id='city_selector']/a", "shanghai city"), shanghai);
			
		}
		

		// 小区
		String xiaoqu=null;
		for (int i=1;i<6;i++){
			xiaoqu=sh2+".pmt15111.zu.anjuke.test/xiaoqu/p"+i+"/";
			bs.get(xiaoqu);
			bs.assertContains(bs.getText("//*[@id='city_selector']/a", "shanghai city"), shanghai);
			
		}
		
		
		// 写字楼

		// 出租
		String chuzu=null;
		for (int i=1;i<6;i++){
			chuzu=sh2+".pmt15111.xzl.anjuke.test/zu/p"+i+"/";
			bs.get(chuzu);
			bs.assertContains(bs.getText("//*[@id='switch']", "shanghai city"), shanghai);
			
		}
		
		

		// 出售
		String shou=null;
		for (int i=1;i<6;i++){
			shou=sh2+".pmt15111.xzl.anjuke.test/shou/p"+i+"/";
			bs.get(shou);
			bs.assertContains(bs.getText("//*[@id='switch']", "shanghai city"), shanghai);
			
		}
		

		// 楼盘
		String loupan=null;
		for (int i=1;i<6;i++){
			loupan=sh2+".pmt15111.xzl.anjuke.test/loupan/p"+i+"/";
			bs.get(loupan);
			bs.assertContains(bs.getText("//*[@id='switch']", "shanghai city"), shanghai);
			
		}
		
		
		// 商铺

		// 出售		
		String spshou=null;
		for (int i=1;i<6;i++){
			spshou=sh2+".pmt15111.sp.anjuke.test/shou/p"+i+"/";
			bs.get(spshou);
			bs.assertContains(bs.getText("//*[@id='switch']", "shanghai city"), shanghai);
			
		}

		

		// 出租	
		String spzu=null;
		for (int i=1;i<6;i++){
			spzu=sh2+".pmt15111.sp.anjuke.test/zu/p"+i+"/";
			bs.get(spzu);
			bs.assertContains(bs.getText("//*[@id='switch']", "shanghai city"), shanghai);		
		}



		// 物业
		String spwuye=null;
		for (int i=1;i<6;i++){
			spwuye=sh2+".pmt15111.sp.anjuke.test/wuye/p"+i+"/";
			bs.get(spwuye);
			bs.assertContains(bs.getText("//*[@id='switch']", "shanghai city"), shanghai);	
		}
	}
	
	/*
		
		@Test
		public void secondHouseDB() throws InterruptedException {

	}
		@Test
		public void jinpuDB() throws InterruptedException {

	}
	
	*/
	
	
	
}
