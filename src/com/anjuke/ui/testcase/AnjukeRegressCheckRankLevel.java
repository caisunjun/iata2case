package com.anjuke.ui.testcase;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.LogFile;
import com.anjukeinc.iata.ui.report.Report;
import com.anjukeinc.iata.ui.report.solrReport;
import com.anjuke.ui.page.*;

/**检查solr的rank_level、rank_sub_level
 * 基于bug 15683
 * @author ccyang
 * @last createtime 2013-4-10上午10:02:55
 */
public class AnjukeRegressCheckRankLevel {

	String shanghaiSolr = "http://10.10.6.51:8983/ajk-sale11/";
	String beijingSolr = "http://10.10.6.51:8983/ajk-sale14/";
	String other1Solr = "http://10.10.6.51:8983/ajk-sale00/";
	String other2Solr = "http://10.10.6.51:8983/ajk-sale04/";
	String customQuery = "";
			
    @BeforeMethod
    public void setUp() {
    }
    
    @AfterMethod
    public void tearDown(){
    }
    
    @Test(groups = {"unstable"})
    public void testCheckRankLevel(){
    	//rank_sub_level的值只在[1,3]范围内
    	customQuery = "rank_level:0";
		try {
			solrmain(shanghaiSolr,customQuery);
			solrmain(beijingSolr,customQuery);
			solrmain(other1Solr,customQuery);
			solrmain(other2Solr,customQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		customQuery = "rank_level:[4 TO 100]";
		try {
			solrmain(shanghaiSolr,customQuery);
			solrmain(beijingSolr,customQuery);
			solrmain(other1Solr,customQuery);
			solrmain(other2Solr,customQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test(groups = {"unstable"})
    public void testCheckRankSubLevel(){
    	//rank_sub_level只需验证查0的房源数
    	customQuery = "rank_sub_level:0";
		try {
			solrmain(shanghaiSolr,customQuery);
			solrmain(beijingSolr,customQuery);
			solrmain(other1Solr,customQuery);
			solrmain(other2Solr,customQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	/**
	 * @param args
	 * @throws SolrServerException
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	private void solrmain(String solrServer,String customQuery)
			throws SolrServerException, NumberFormatException, IOException {

		SolrServer solr = new CommonsHttpSolrServer(solrServer);
		QueryResponse response = null;
		SolrQuery query = new SolrQuery();
		
		query.set("q", customQuery);
		response = solr.query(query);
		
		if (0 == response.getResults().getNumFound()) {
			Report.writeHTMLLog("solr查询检查", solrServer+"里查不到"+customQuery+"的房源", Report.PASS, "");
		} else {
			SolrDocumentList docs = response.getResults();
			//for each SolrDocument in docs,copy its value to doc,then...
			for (SolrDocument doc : docs) {
				int id = (Integer) doc.getFieldValue("id");
				Report.writeHTMLLog(solrServer+"select?q="+customQuery+"找到了房源", "房源id为："+String.valueOf(id), Report.FAIL, "");
			}
		}
	}
}
