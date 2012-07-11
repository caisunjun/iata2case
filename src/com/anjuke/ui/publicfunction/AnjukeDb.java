package com.anjuke.ui.publicfunction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.anjuke.ui.bean.AnjukePriceMonitorBean;
import com.anjukeinc.iata.db.DbFactory;

public class AnjukeDb {
	/**
	 * 获取小区昨日均价和趋势
	 * 
	 * @author Gabrielgao
	 * @time 2012-05-04 17:55
	 */
	public static HashMap<String, String> getCommunity(String commid) {
		HashMap<String, String> commMap = new HashMap<String, String>();
		Connection con = DbFactory.getConnection();
		Statement s = null;
		try {
			s = con.createStatement();
			ResultSet rs = s.executeQuery("select Price,Trend from auto_CommunityPrice where CommunityID=" + commid + " order by date desc LIMIT 1;"); // 提交查询，返回的表格保存在rs中
			while (rs.next()) { // ResultSet指针指向下一个“行”
				commMap.put((rs.getString("Price")), rs.getString("Trend"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				s.close();
				con.close(); // 关闭到MySQL服务器的连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 释放Statement对象
		}
		return commMap;
	}

	/**
	 * 获取小区最近5天均价和趋势
	 * 
	 * @author Gabrielgao
	 * @time 2012-05-04 17:55
	 */
	public static Map<String, AnjukePriceMonitorBean> getCommunityPrice(String commid) {
		Map<String, AnjukePriceMonitorBean> commMap = new LinkedHashMap<String, AnjukePriceMonitorBean>();
		Connection con = DbFactory.getConnection();
		Statement s = null;
		try {
			s = con.createStatement();
			ResultSet rs = s.executeQuery("select Date,City,Community,Price,Trend,CommunityID from auto_CommunityPrice where CommunityID=" + commid + " order by date desc LIMIT 5;"); // 提交查询，返回的表格保存在rs中
			while (rs.next()) { // ResultSet指针指向下一个“行”
				AnjukePriceMonitorBean bean = new AnjukePriceMonitorBean();
				bean.setDATE(rs.getString("Date"));
				bean.setCity(rs.getString("City"));
				bean.setCommunity(rs.getString("Community"));
				bean.setPrice(rs.getString("Price"));
				bean.setThrend(rs.getString("trend"));
				bean.setCommunityID(rs.getString("CommunityID"));
				commMap.put((rs.getString("Date")), bean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				s.close();
				con.close(); // 关闭到MySQL服务器的连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 释放Statement对象
		}
		return commMap;
	}

	/**
	 * 插入今天小区均价和趋势
	 * 
	 * @author Gabrielgao
	 * @time 2012-05-04 17:55
	 */
	public static void insertCommunity(String city, String community, String commid, String commPrice, String commChg) {
		String date = getNowDateTime("yyyy-MM-dd ");
		String executeDate = getNowDateTime("yyyy-MM-dd HH:mm:ss");
		Connection con = DbFactory.getConnection();
		Statement s = null;
		try {
			s = con.createStatement();
			s.executeUpdate("insert into auto_CommunityPrice values('" + date + "','" + city + "','" + community + "','" + commPrice + "','" + commChg + "','" + commid + "','" + executeDate + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				s.close();
				con.close(); // 关闭到MySQL服务器的连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 释放Statement对象
		}
	}



	/**
	 * 返回每日执行的用例名称
	 * 
	 * @author Gabrielgao
	 * @time 2012-05-16 17:55
	 */
	public static Map<String, String> getCaseName() {
		Map<String, String> commMap = new LinkedHashMap<String, String>();
		Connection con = DbFactory.getConnection();
		String date = getNowDateTime("yyyy-MM-dd");
		Statement s = null;
		try {
			s = con.createStatement();
			ResultSet rs = s.executeQuery("select case_name,browser_name,count(case_name) from sel_report_caseinfo where start_time like '" + date + "%' group by case_name,browser_name;"); // 提交查询，返回的表格保存在rs中
			while (rs.next()) { // ResultSet指针指向下一个“行”
				commMap.put(rs.getString("case_name") + "^" + rs.getString("browser_name"), rs.getString("count(case_name)"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				s.close();
				con.close(); // 关闭到MySQL服务器的连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 释放Statement对象
		}
		return commMap;
	}

	/**
	 * 返回每日用例执行结果
	 * 
	 * @author Gabrielgao
	 * @time 2012-05-16 17:55
	 */
	public static String getCaseExecuteResult(String caseName, String browsername, String status) {
		String executeResult = null;
		Connection con = DbFactory.getConnection();
		String date = getNowDateTime("yyyy-MM-dd");
		Statement s = null;
		try {
			s = con.createStatement();
			ResultSet rs = null;
			if (status.equals("total")) {
				rs = s.executeQuery("select count(b.state) from sel_report_caseinfo as a,sel_report_stepinfo as b where a.id= b.case_id and a.start_time like'" + date + "%' and a.case_name ='"
						+ caseName + "' and b.browser_name='" + browsername + "';"); // 提交查询，返回的表格保存在rs中
			} else {
				rs = s.executeQuery("select count(b.state) from sel_report_caseinfo as a,sel_report_stepinfo as b where a.id= b.case_id and a.start_time like'" + date + "%' and b.state='" + status
						+ "' and a.case_name ='" + caseName + "' and b.browser_name='" + browsername + "';"); // 提交查询，返回的表格保存在rs中
			}

			while (rs.next()) { // ResultSet指针指向下一个“行”
				executeResult = (rs.getString("count(b.state)"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				s.close();
				con.close(); // 关闭到MySQL服务器的连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 释放Statement对象
		}
		return executeResult;
	}

	// 返回时间戳
	final private static String getNowDateTime(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}
}
