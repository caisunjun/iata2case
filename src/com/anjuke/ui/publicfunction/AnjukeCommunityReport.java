package com.anjuke.ui.publicfunction;

import java.util.Iterator;
import java.util.Map;

import com.anjuke.ui.bean.AnjukePriceMonitorBean;
/**
 * 该类主要完成小区均价监控最近五天数据的报表输出
 * @author Gabrielgao
 * @time 2012-05-04 17:55
 */
public class AnjukeCommunityReport {
	private static String ReportTable = ""; // HTML Log
	private static String tmpRed = "#ff0000";
	private static String tmpBlue = "#0000ff";
	private static boolean headStatus = false;	
	
	//写脚本报告的页头
	final private static boolean writeHTMLHead(Map<String,AnjukePriceMonitorBean> resultData){
		int j = 0;
		String[] dateList = new String[5];
		Iterator<Map.Entry<String, AnjukePriceMonitorBean>> it = resultData.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, AnjukePriceMonitorBean> entry = (Map.Entry<String, AnjukePriceMonitorBean>) it.next();
			AnjukePriceMonitorBean bean = (AnjukePriceMonitorBean)entry.getValue();
			//获取最近五天日期
			dateList[j]=bean.getDATE();
			j++;
		}
		ReportTable = ReportTable + "</table>";
		ReportTable = ReportTable + "<br><br>";
		ReportTable = ReportTable + "<TABLE border='1' cellspacing='0' width='100%' style='border-collapse:collapse;' bordercolor='#aaaaaa' cellpadding='2'>";
		ReportTable = ReportTable + "<tr>";
		ReportTable = ReportTable + "<TD width= '5%' bgcolor= '#003366' align='center' rowspan='2'> <FONT   color= '#ffffff' size='2'> <B>";
		ReportTable = ReportTable + "序号"+"</B> </FONT> </TD>";
		ReportTable = ReportTable + "<TD width= '10%' bgcolor= '#003366' align='center' rowspan='2'> <FONT   color= '#ffffff' size='2'> <B>";
		ReportTable = ReportTable + "城市"+"</B> </FONT> </TD>";
		ReportTable = ReportTable + "<TD  bgcolor= '#003366' align='left' rowspan='2'> <FONT   color= '#ffffff' size='2'> <B>";
		ReportTable = ReportTable + "小区"+"</B> </FONT> </TD>";
		ReportTable = ReportTable + "<TD width= '30%' bgcolor= '#003366' align='center' colspan='5'> <FONT   color= '#ffffff' size='2'> <B>";
		ReportTable = ReportTable + "均价"+"</B> </FONT> </TD>";
		ReportTable = ReportTable + "<TD width= '30%' bgcolor= '#003366' align='center' colspan='5'> <FONT   color= '#ffffff' size='2'> <B>";
		ReportTable = ReportTable + "趋势"+"</B> </FONT> </TD>";
		ReportTable = ReportTable + "</tr>";
		ReportTable = ReportTable + "<tr>";
		for(int b=0;b<2;b++){
			for(int i=0;i<5;i++){
				ReportTable = ReportTable + "<TD width= '6%' bgcolor= '#003366' align='center'> <FONT   color= '#ffffff' size='2'> <B>";
				ReportTable = ReportTable + dateList[i]+"</B> </FONT> </TD>";
			}		
		}
		ReportTable = ReportTable + "</tr>";
		headStatus = true;
		return headStatus;
	}
	/**
	 * 记录脚本运行时的HTML格式log
	 */
	final public static void writeDataLog(int num,Map<String,AnjukePriceMonitorBean> resultData) {		
		if(!headStatus){
			writeHTMLHead(resultData);
		}
		int i = 0;
		int n = 0;
		String[] dataResult = new String[10];
		String[] dataC = new String[2];
		Iterator<Map.Entry<String, AnjukePriceMonitorBean>> it = resultData.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, AnjukePriceMonitorBean> entry = (Map.Entry<String, AnjukePriceMonitorBean>) it.next();
			AnjukePriceMonitorBean bean = (AnjukePriceMonitorBean)entry.getValue();
			//获取城市、小区
			dataC[i]="["+bean.getCommunityID()+"]-"+bean.getCommunity();
			dataC[i+1]= bean.getCity();
			//获取五天数据
			dataResult[n] = bean.getPrice();
			dataResult[n+5] = bean.getThrend();
			n++;
		}
		ReportTable = ReportTable + "<tr align='center'>";
		ReportTable = ReportTable + "<td>" + num+ "</td>";
		ReportTable = ReportTable + "<td>" + dataC[i+1] + "</td>";
		ReportTable = ReportTable + "<td align='left'>" + dataC[0] + "</td>";
		for(int m=0;m<10;m++){
			//当日小区均价
			if(m==0){
				ReportTable = ReportTable + "<td><font color='"+tmpRed+"'>"+dataResult[m]+"</td>";
			}
			//当日小区均价趋势
			if(m==5){
				ReportTable = ReportTable + "<td><font color='"+tmpBlue+"'>"+dataResult[m]+"</td>";
			}
			//如果连续几天的数据一致，则报错
			if(m>0&&m<5){
				int equalsCount = 0;
				for(int a =0;a<m;a++){
					if(dataResult[a]==null||dataResult[a+1]==null){
						continue;
					}else if(dataResult[a].equals(dataResult[a+1])){
						equalsCount=equalsCount+1;
				    }
				}
				if(equalsCount==m){
					ReportTable = ReportTable + "<td><font color='"+tmpRed+"'>"+dataResult[m]+"</td>";
				}else{
					ReportTable = ReportTable + "<td>"+dataResult[m]+"</td>";
				}
			}
			//如果连续几天的数据一致，则报错
			if(m>5&&m<10){
				int equalsCount = 0;
				for(int a =5;a<m;a++){
					if(dataResult[a]==null||dataResult[a+1]==null){
						continue;
					}else if(dataResult[a].equals(dataResult[a+1])){
						equalsCount=equalsCount+1;
				    }
				}
				if(equalsCount==m-5){
					ReportTable = ReportTable + "<td><font color='"+tmpBlue+"'>"+dataResult[m]+"</td>";
				}else{
					ReportTable = ReportTable + "<td>"+dataResult[m]+"</td>";
			}			
			}
		}
	}

	final public static String getHtml(){
		ReportTable = ReportTable + "</tr>";
		ReportTable = ReportTable + "</table>";
		ReportTable = ReportTable + "<br>";
		ReportTable = ReportTable + "<table border='1' width='100%' style='border-collapse:collapse;' bordercolor='#dddddd'>";
		ReportTable = ReportTable + "<tr>";
		ReportTable = ReportTable + "<td bgcolor='#003366' width='15%'><font color='#FFFFFF'><b>发生时间</b></font></td>";
		ReportTable = ReportTable + "<td bgcolor='#003366' width='20%'><font color='#FFFFFF'><b>测试业务</b></font></td>";
		ReportTable = ReportTable + "<td bgcolor='#003366' width='10%'><font color='#FFFFFF'><b>结果</b></font></td>";
		ReportTable = ReportTable + "<td bgcolor='#003366' width='40%'><font color='#FFFFFF'><b>注释</b></font></td>";
		ReportTable = ReportTable + "<td bgcolor='#003366' width='15%'><font color='#FFFFFF'><b>截图</b></font></td>";
		ReportTable = ReportTable + "</tr>";
		return ReportTable;
	}
	
}
