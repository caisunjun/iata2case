package com.anjuke.ui.publicfunction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.anjuke.ui.page.Ajk_PropView;
import com.anjuke.ui.page.Ajk_Sale;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.report.Report;

/**
 * 该脚本主要完成: 1、区域板块、面积、售价、房型的筛选条件结果验证 2、区域、板块、户型的keyword搜索结果验证
 * 3、获取列表页筛选条件的locater
 * 
 * @param val
 *            筛选条件和页面显示的一致，如50平米以下、200-250万、浦东、北新泾
 * 
 * @author Grayhu
 * @time 2012-09-04 17:30 keyword搜索验证：长宁北新泾2室，拆分为：长宁、北新泾、2室后分别调用不同的verify
 **/

public class AjkSaleSearchAction {
	private Browser driver = null;

	public AjkSaleSearchAction(Browser driver) {
		this.driver = driver;
	}

	/**
	 * 点击筛选条件搜索 - 获得所有筛选条件locater
	 * 
	 * @param val
	 *            筛选条件在页面显示的值，其中售价需由“50-80万”改为：“50-80万元”；
	 * */
	public String getLocater(String val) {
		return getPath(val.toString());
	}

	/** 筛选条件搜索 - 验证房源的总价 */
	public boolean verifyPrice(String val) {
		return compareFilterResult(val, "price");
	}

	/** 筛选条件搜索 - 验证房源的户型室 */
	public boolean verifyRoom(String val) {
		return compareFilterResult(val, "room");
	}

	/** 筛选条件搜索 - 验证房源的面积 */
	public boolean verifyArea(String val) {
		return compareFilterResult(val, "area");
	}

	/** 筛选条件搜索 - 验证房源的板块 */
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

	/** 关键字搜索 - 验证房源的高亮区域 */
	public boolean verifyKeyRegion(String val) {
		return compareKeywordResult(val, "region");
	}

	/** 关键字搜索 - 验证房源的高亮板块 */
	public boolean verifyKeyBlock(String val) {
		return compareKeywordResult(val, "block");
	}

	/** 关键字搜索 -验证房源的高亮户型室 */
	public boolean verifyKeyRoom(String val) {
		return compareKeywordResult(val, "room");
	}

	/** 关键字搜索 -验证房源的高亮小区名 */
	public boolean verifyDistrict(String val) {
		return compareKeywordResult(val, "district");
	}

	private String getPath(String value) {
		String locater = value;
		locater = Ajk_Sale.S_SELECT(value);
		return locater;
	}

	// 获取列表页房源数量
	private int getListCount() {
		int listCount = 0;
		try {
			listCount = driver.getElementCount("//ol[@id='list_body']/li");
		} catch (NullPointerException e) {
			System.out.println("*******************没有找到记录");
		}
		return listCount;
	}

	// 正则提取只包含数字的字符串
	public static String getStrings(String str) {
		StringBuilder sb = new StringBuilder();
		if (str == null)
			return "";
		if (str.equals(""))
			return sb.toString();
		Pattern p = Pattern.compile("[0-9]*.?[0-9]+");
		Matcher m = p.matcher(str);
		if (m.find()) {
			String group = m.group();
			System.out.println(group);
			sb.append(group);
			String subStr = str.substring(group.length());
			Pattern pattern = Pattern.compile("\\d+.*");
			if (pattern.matcher(subStr).matches()) {
				getStrings(str.substring(group.length()));
			}
		}
		return sb.toString();
	}

	private double getDoubleVal(String val) {
		return Double.parseDouble(val);
	}

	/**
	 * @param val
	 *            筛选条件，可分3种："浦东"、"陆家嘴"、"浦东陆家嘴"
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
		for (int i = 0, j = 1; j <= listCount; i++, j++) {
			driver.click(Ajk_Sale.getTitle(j), "点击房源图片跳转到房源详情页");
			driver.switchWindo(2);
			if (category.equals("region")) {
				locater = Ajk_PropView.REGION;
			}
			if (category.equals("block")) {
				locater = Ajk_PropView.BLOCK;
			}
			if (category.equals("regionblock")) {
				viewLocater = driver.getText(Ajk_PropView.REGION,
						"获取房源详情页的面包屑中的" + category)
						+ driver.getText(Ajk_PropView.BLOCK, "获取房源详情页的面包屑中的"
								+ category);
			} else {
				viewLocater = driver.getText(locater, "获取房源详情页的面包屑中的"
						+ category);
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

	/**
	 * @param val
	 *            搜索词，需拆分；上海康城2室：上海康城、2室，分别调用。
	 * @param category
	 *            分为：region、block、room、district
	 * @return
	 */
	private boolean compareKeywordResult(String val, String category) {
		boolean result = false;
		String searchKeyword = val;
		String locater = category;
		int listCount = getListCount();
		String[] listEm = new String[listCount];

		for (int i = 0, j = 1; j <= listCount; i++, j++) {
			if (locater.equals("region")) {
				locater = Ajk_Sale.getKeyRegion(j);
			}
			if (locater.equals("block")) {
				locater = Ajk_Sale.getKeyBlock(j);
			}
			if (locater.equals("room")) {
				locater = Ajk_Sale.getKeyRoom(j);
			}
			if (locater.equals("district")) {
				locater = Ajk_Sale.getKeyDistrict(j);
			}
			listEm[i] = driver.getText(locater, "获取第" + j + "条房源高亮");
			if (listEm[i].trim().equals(searchKeyword.trim())) {
				result = true;
			} else {
				result = false;
				break;
			}
		}
		return result;
	}

	// 拆分搜索条件
	private String[] getValueSplit(String splitValue) {
		String value = splitValue;
		String[] val = null;// new String[2];
		if (value.indexOf("-") != -1) {
			val = value.split("-");
			if (val[1].indexOf("万") != -1) {
				val[1] = val[1].substring(0, val[1].length() - 1);
			} else {
				val[1] = val[1].substring(0, val[1].length() - 2);
			}

		} else if (value.indexOf("万") != -1) {
			val = value.split("万");
			if (val[1].indexOf("以下") != -1) {
				val[1] = val[0];
				val[0] = "0";
			} else {
				val[1] = "999999";
			}
		} else if (value.indexOf("平米") != -1) {
			val = value.split("平米");
			if (val[1].indexOf("以下") != -1) {
				val[1] = val[0];
				val[0] = "0";
			} else {
				val[1] = "9999";
			}
		}

		if (value.indexOf("室") != -1) {
			val = value.split("室");
			if (val.length > 1 && val[1].equals("以上")) {
				val[0] = getDoubleVal(val[0]) + 1 + "";
				val[1] = "9";
			}
		}

		if (value.indexOf("房") != -1) {
			val = value.split("房");
		}

		if (value.indexOf("房") != -1 && value.indexOf("大") != -1) {
			val[0] = value.substring(1, value.length() - 1);
		}

		return val;
	}

	private boolean compareFilterResult(String val, String category) {
		boolean result = false;
		int listCount = getListCount();
		String[] vals = getValueSplit(val.trim());
		String[] listVals = new String[listCount];
		String locater = null;

		try {
			for (int i = 0, j = 1; j <= listCount; i++, j++) {
				if (category.equals("price")) {
					locater = Ajk_Sale.getPrice(j);
				}
				if (category.equals("area")) {
					locater = Ajk_Sale.getArea(j);
				}
				if (category.equals("room")) {
					locater = Ajk_Sale.getRoom(j);
				}

				listVals[i] = driver.getText(locater, "获取第" + j + "条房源"
						+ category);

				if (category.equals("area")) {
					String[] r = listVals[i].split("，");
					listVals[i] = r[1].substring(0, r[1].length() - 2);
				}
				if (category.equals("room")) {
					String[] r = listVals[i].split("，");
					listVals[i] = r[0].substring(0, 1);
				}

				if (vals.length == 2) {
					if (getDoubleVal(listVals[i]) >= getDoubleVal(vals[0])
							&& getDoubleVal(listVals[i]) <= getDoubleVal(vals[1])) {
						result = true;
					} else {
						result = false;
						break;
					}
				} else {
					if (getDoubleVal(listVals[i]) == getDoubleVal(vals[0])) {
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

}
