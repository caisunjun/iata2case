package com.anjuke.ui.publicfunction;

import com.anjuke.ui.page.Ajk_PropRent;
import com.anjuke.ui.page.Ajk_Rental;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.report.Report;


public class AjkRentalSearchAction {
	
	//初始化自己
	private Browser driver = null;
	public AjkRentalSearchAction(Browser driver) {
		this.driver = driver;
	}
	
	/** 获得所有筛选项的通用方法  
	 * 传入的参数：鼠标移至筛选项上 浮动提示里的内容*/
	public String getLocater(String val) {
		return getPath(val.toString());
	}
	
	/** 筛选条件搜索 - 验证房源的区域板块 */
	public boolean verifyRegionBlock(String val) {
		return compareFilterRegion(val, "regionblock");
	}
	
	/** 筛选条件搜索 - 验证房源的区域 */
	public boolean verifyRegion(String val) {
		return compareFilterRegion(val, "region");
	}

	/** 筛选条件搜索 - 验证房源的板块 */
	public boolean verifyBlock(String val) {
		return compareFilterRegion(val, "block");
	}
	
	/** 筛选条件搜索 - 验证房源的总价 */
	public boolean verifyPrice(String val) {
		return compareFilterPrice(val);
	}
	
	/** 筛选条件搜索 - 验证房源的房型 例：1室、2室 */
	public boolean verifyRoom(String val) {
		return compareFilterRoom(val);
	}

	//获得租房列表页的locater
	private String getPath(String value) {
		String locater = value;
		locater = Ajk_Rental.S_SELECT(value);
		return locater;
	}
	
	// 获取列表页房源数量
	private int getListCount() {
		int listCount = 0;
		try {
			listCount = driver.getElementCount("//ol[@id='list_body']/li");
		} catch (NullPointerException e) {
			System.out.println("*******************这个筛选条件下没有房源");
		}
		return listCount;
	}
	
	/**
	 * @param val
	 *            验证筛选条件，可分3种："浦东"、"陆家嘴"、"浦东陆家嘴"
	 * @param category
	 *            对应val值，可分3种：region、block、regionblock
	 * @return
	 */
	private boolean compareFilterRegion(String val, String category) {
		boolean result = false;
		int listCount = getListCount();
		String value = val;
		String locater = null;
		String viewLocater = null;
		//遍历当前页面内所有筛选结果
		for (int j = 1; j <= listCount; j++) {
			driver.click(Ajk_Rental.getPic(j), "点击房源图片跳转到房源详情页");
			driver.switchWindo(2);
			//分三种情况来验证区域板块
			if (category.equals("region")) {
				locater = driver.getText(Ajk_PropRent.REGION,"获取房源详情页的面包屑中的" + category);
			}
			if (category.equals("block")) {
				locater = driver.getText(Ajk_PropRent.BLOCK, "获取房源详情页的面包屑中的"+ category);
			}
			if (category.equals("regionblock")) {
				viewLocater = driver.getText(Ajk_PropRent.REGION,"获取房源详情页的面包屑中的" + category)
						+ driver.getText(Ajk_PropRent.BLOCK, "获取房源详情页的面包屑中的"+ category);
			} else {
				viewLocater = driver.getText(locater, "获取房源详情页的面包屑中的"+ category);
			}

			if (viewLocater.equals(value)) {
				result = true;
				driver.close();
				driver.switchWindo(1);
			} else {
				result = false;
				break;
			}
		}
		return result;
	}

	private boolean compareFilterPrice(String val) {
		boolean result = false;
		int listCount = getListCount();
		//val为给出的租金范围
		String valtmp = val;
		String[] vals = null;
		//列表中的租金
		String[] listVals = new String[listCount];
		String locater = null;
		//以-为标记，拆分租金范围，如果不是范围呢？？？？？
		if (valtmp.indexOf("-") != -1) {
			vals = valtmp.split("-");
			//去掉“元”，如果有的话
			if (vals[1].indexOf("元") != -1) {
				vals[1] = vals[1].substring(0, vals[1].length() - 1);
			}
		}

		try {
			for (int i = 0, j = 1; j <= listCount; i++, j++) {
				locater = Ajk_Rental.getPrice(j);
				listVals[i] = driver.getText(locater, "获取第" + j + "条房源的租金");
				//传入的是租金范围
				if (vals.length == 2) {
					if (Double.parseDouble(listVals[i]) >= Double.parseDouble(vals[0])
							&& Double.parseDouble(listVals[i]) <= Double.parseDouble(vals[1])) {
						result = true;
					} else {
						result = false;
						break;
					}
				//传入的是租金具体值
				} else {
					if (Double.parseDouble(listVals[i]) == Double.parseDouble(vals[0])) {
						result = true;
					} else {
						result = false;
						break;
					}
				}

			}
		} catch (NullPointerException e) {
			String ps = driver.printScreen();
			Report.writeHTMLLog("parameter type fial ,not parse",
					"parseDouble value is null", Report.FAIL, ps);
			System.out.println("输入参数错误，不可转换类型");
		}
		return result;
	}
	
	private boolean compareFilterRoom(String val) {
		boolean result = false;
		String searchKeyword = val;
		String info = null;
		int listCount = getListCount();
		String[] listEm = new String[listCount];
		for (int i = 0, j = 1; j <= listCount; i++, j++) {
			info = driver.getText(Ajk_Rental.getKeyRoom(j), "获取第" + j + "条房源高亮");
			info = info.substring(0, info.indexOf("室")+1);
			listEm[i] = info;
			if (listEm[i].equals(searchKeyword)) {
				result = true;
			} else {
				result = false;
				break;
			}
		}
		return result;
	}
	
	
	
}
