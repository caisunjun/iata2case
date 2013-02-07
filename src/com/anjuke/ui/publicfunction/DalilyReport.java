package com.anjuke.ui.publicfunction;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Map;

import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.report.Report;

public class DalilyReport {
	private String Report_Html = "";
	private String today = PublicProcess.getNowDateTime("yyyy/MM/dd");
	private Map<String, String> caseName = AnjukeDb.getCaseName();
	private String PASS = "pass";
	private String FAIL = "fail";
	private String DONE = "done";
	private String INFO = "info";
	private String OTHER = "other";
	private String TOTAL = "total";
	private int i = 1;

	@Test
	public void sendReport() {
		writeReportLog();
        Report.sendReportEmail("脚本日执行情况一览-", today, Report_Html);
	}

	private void writeReportLog() {
		Report_Html = Report_Html + "<html><head><title>每日脚本执行报告</title></head><body>";
		Report_Html = Report_Html + "<table align='center'><tr align='center'><td  align='center'><p><FONT color= '#030303'>" + today + "日Selenium脚本执行结果报告</font></td></tr></table>";
		Report_Html = Report_Html + "<br><TABLE border='1' cellspacing='0' width='100%' style='border-collapse:collapse;' bordercolor='#CD6090' cellpadding='2'>";
		Report_Html = Report_Html + "<tr bgcolor= '#FFBBFF'>";
		Report_Html = Report_Html + "<TD width= '5%'  align='center' > <FONT   color= '#ffffff' size='2'> <B>";
		Report_Html = Report_Html + "序号" + "</B> </FONT> </TD>";
		Report_Html = Report_Html + "<TD width= '23%'  align='center' > <FONT   color= '#ffffff' size='2'> <B>";
		Report_Html = Report_Html + "Case名称" + "</B> </FONT> </TD>";
		Report_Html = Report_Html + "<TD  width= '8%'  align='center' > <FONT   color= '#ffffff' size='2'> <B>";
		Report_Html = Report_Html + "浏览器" + "</B> </FONT> </TD>";
		Report_Html = Report_Html + "<TD  width= '8%'  align='center' > <FONT   color= '#ffffff' size='2'> <B>";
		Report_Html = Report_Html + "执行次数" + "</B> </FONT> </TD>";
		Report_Html = Report_Html + "<TD width= '8%'  align='center' > <FONT   color= '#ffffff' size='2'> <B>";
		Report_Html = Report_Html + "DONE" + "</B> </FONT> </TD>";
		Report_Html = Report_Html + "<TD width= '8%'  align='center' > <FONT   color= '#ffffff' size='2'> <B>";
		Report_Html = Report_Html + "PASS" + "</B> </FONT> </TD>";
		Report_Html = Report_Html + "<TD width= '8%'  align='center' > <FONT   color= '#ffffff' size='2'> <B>";
		Report_Html = Report_Html + "FAIL" + "</B> </FONT> </TD>";
		Report_Html = Report_Html + "<TD width= '8%'  align='center' > <FONT   color= '#ffffff' size='2'> <B>";
		Report_Html = Report_Html + "INFO" + "</B> </FONT> </TD>";
		Report_Html = Report_Html + "<TD width= '8%'  align='center' > <FONT   color= '#ffffff' size='2'> <B>";
		Report_Html = Report_Html + "OTHER" + "</B> </FONT> </TD>";
		Report_Html = Report_Html + "<TD width= '8%'  align='center' > <FONT   color= '#ffffff' size='2'> <B>";
		Report_Html = Report_Html + "TOTAL" + "</B> </FONT> </TD>";
		Report_Html = Report_Html + "<TD width= '8%'  align='center' > <FONT   color= '#ffffff' size='2'> <B>";
		Report_Html = Report_Html + "成功率" + "</B> </FONT> </TD>";
		Report_Html = Report_Html + "</tr>";
		Iterator<Map.Entry<String, String>> it = caseName.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> result = (Map.Entry<String, String>) it.next();
			String caseName = result.getKey().substring(0, result.getKey().indexOf("^"));
			String browserName = result.getKey().substring(result.getKey().indexOf("^") + 1, result.getKey().length());
			String caseExecuteCount = result.getValue();
			String done = getStateCount(caseName, browserName, DONE);
			String pass = getStateCount(caseName, browserName, PASS);
			String fail = getStateCount(caseName, browserName, FAIL);
			String info = getStateCount(caseName, browserName, INFO);
			String other = getStateCount(caseName, browserName, OTHER);
			String total = getStateCount(caseName, browserName, TOTAL);
			Double sucess = 00.0;
			if (!(pass.equals(fail) && pass.equals("0"))) {
				sucess = Double.parseDouble(pass) / (Double.parseDouble(pass) + Double.parseDouble(fail));
			}
			DecimalFormat myformat = (DecimalFormat) NumberFormat.getPercentInstance();
			// 设置百分率的输出形式，形如00.*,根据需要设定。
			myformat.applyPattern("00.0%");
			if (i % 2 == 0) {
				Report_Html = Report_Html + "<tr bgcolor= '#FFF8DC'>";
			} else {
				Report_Html = Report_Html + "<tr bgcolor= '#FFFAFA'>";
			}
			Report_Html = Report_Html + "<td align='center'>" + i + "</td>";
			Report_Html = Report_Html + "<td align='left'>" + caseName + "</td>";
			Report_Html = Report_Html + "<td align='left'>" + browserName + "</td>";
			Report_Html = Report_Html + "<td align='center'>" + caseExecuteCount + "</td>";
			Report_Html = Report_Html + "<td align='center'>" + done + "</td>";
			Report_Html = Report_Html + "<td align='center'>" + pass + "</td>";
			if (fail.equals("0")) {
				Report_Html = Report_Html + "<td align='center'>" + fail + "</td>";
			} else {
				Report_Html = Report_Html + "<td align='center'><FONT color= '#CD2626'>" + fail + "</font></td>";
			}
			Report_Html = Report_Html + "<td align='center'>" + info + "</td>";
			Report_Html = Report_Html + "<td align='center'>" + other + "</td>";
			Report_Html = Report_Html + "<td align='center'>" + total + "</td>";
			if (!myformat.format(sucess).equals("100.0%")) {
				Report_Html = Report_Html + "<td align='center' ><FONT color= '#CD2626'>" + myformat.format(sucess) + "</font></td>";
			} else {
				Report_Html = Report_Html + "<td align='center'>" + myformat.format(sucess) + "</td>";
			}
			Report_Html = Report_Html + "</tr>";
			i++;
		}
		Report_Html = Report_Html + "</table>";
		Report_Html = Report_Html + "<table><tr><td><a href='http://hibug.qa.anjuke.com/selenium/caseLog.php'>查看报告点击这里</a></font></td></tr></table>";
	}

	private String getStateCount(String caseName, String browsername, String state) {
		String stateCount = AnjukeDb.getCaseExecuteResult(caseName, browsername, state);
		return stateCount;
	}
}
