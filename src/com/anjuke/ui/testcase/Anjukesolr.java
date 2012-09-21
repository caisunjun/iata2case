package com.anjuke.ui.testcase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjukeinc.iata.ui.report.LogFile;
import com.anjukeinc.iata.ui.report.Report;

/**
 * @Todo : TODO
 * @author : fjzhang
 * @filename : Anjukesolr.java
 * @date : 2012-9-21
 */

public class Anjukesolr {
	String[] server = { "ajk-prop11/", "ajk-prop14/", "ajk-prop00/",
			"ajk-prop04/" };

	@BeforeMethod
	public void setUp() {
		// bs = FactoryBrowser.factoryBrowser();
		// SolrServer solr = null;
	}

	@AfterMethod
	public void tearDown() {
		 Report.seleniumReport("", "");
		// bs.quit();
		// bs = null;
	}

	@Test (groups = {"unstable"})
	public void testKeyword() throws NumberFormatException, IOException {
		/**
		 * 好租关键字检查
		 */
		for (int i = 0; i < server.length; i++) {
			String solrServer = "http://10.10.6.51:8983/" + server[i];
			Report.writeHTMLLog("新开solr", server[i], Report.DONE, "");
			try {
				solrmain(solrServer);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConnectException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * @param args
	 * @throws SolrServerException
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	private void solrmain(String solrServer) throws SolrServerException,
			NumberFormatException, IOException {

		Map<String, String> keywordsMap = LogFile.getConfigInfo("keywords");
		Iterator<Entry<String, String>> keywordsIter = keywordsMap.entrySet()
				.iterator();

		Map<String, String> map = new HashMap<String, String>();
		map = readcityinfo();
		
		Collection<String> c = map.keySet();
		Iterator<String> city = c.iterator();
		for (; city.hasNext();) {
			
			String citypinyin = readfile(Integer.parseInt((String) city.next()));
			System.out.println(city.next().toString() + ":" + citypinyin);
			while (keywordsIter.hasNext()) {
				Map.Entry<String, String> keywordsEntry = keywordsIter.next();
				String keywordsVal = keywordsEntry.getValue();
				System.out.println("------------" + keywordsVal);
				SolrServer solr = new CommonsHttpSolrServer(solrServer);

				// CommonsHttpSolrServer("http://localhost:8983/solr/");
				// http://localhost:8983/solr/spellCheckCompRH?q=epod&spellcheck=on&spellcheck.build=true
				ModifiableSolrParams params = new ModifiableSolrParams();
				params.set("q", keywordsVal);
				params.set("spellcheck", "on");
				params.set("spellcheck.build", "true");
				params.set("fq", "islist:1");
				params.set("city_id", city.next().toString());
				QueryResponse response = solr.query(params);

				//System.out.println(response.getResults());

				if (0 == response.getResults().getNumFound()) {
					Report.writeHTMLLog("关键字检查", keywordsVal, Report.PASS, "");
				} else {
					// System.out.println("reponse = : " +
					// response.getResults());
					SolrDocumentList docs = response.getResults();
					for (SolrDocument doc : docs) {
						// String title = (String) doc.getFieldValue("name");
						int id = (Integer) doc.getFieldValue("id");
						// int cityid = (Integer) doc.getFieldValue("city_id");
						int cityid = Integer.parseInt(city.next().toString());
						// int from = (Integer) doc.getFieldValue("from");
						String url = haozuhouseurl(id, cityid,citypinyin);
						// String url =
						// "http://shanghai.anjuke.com/prop/view/131950393";
						Report.writeHTMLLogKeyword("关键字检查", keywordsVal,
								Report.FAIL, String.valueOf(id), url);
						// System.out.println(id);
						// System.out.println(cityid);
					}
				}
			}
		}
	}

	private String haozuhouseurl(int id, int cityid,String citypinyin)
			throws NumberFormatException, IOException {
		String url = "";
		url = "http://" + citypinyin + ".anjuke.com/prop/view/" + id;
		return url;
	}

	public static Map<String, String> readcityinfo() throws IOException {
		FileInputStream file = new FileInputStream("cityinfo.ini");
		@SuppressWarnings("resource")
		BufferedReader input = new BufferedReader(new InputStreamReader(file,
				"UTF-8"));
		String text = null;
		Map<String, String> map = new HashMap<String, String>();

		while ((text = input.readLine()) != null) {
			if (text.length() >= 1 && text.substring(0, 1).equals("#")) {
				continue;
			}
			int number = text.indexOf("=");
			if (number != -1) {
				map.put(text.substring(0, number),
						text.substring(number + 1, text.length()));
				/*
				 * if (Integer.parseInt(text.substring(0, number)) == cityid) {
				 * text = text.substring(number + 1, text.length()); break; }
				 */
			}
		}
		return map;
	}

	@SuppressWarnings("resource")
	public static String readfile(int cityid) throws NumberFormatException,
			IOException {
		/**
		 * 读取文件
		 */
		BufferedReader input = null;// 读文件用
		FileInputStream file = null;
		String text = null;
		file = new FileInputStream("cityinfo.ini");
		input = new BufferedReader(new InputStreamReader(file, "UTF-8"));
		while ((text = input.readLine()) != null) {
			if (text.length() >= 1 && text.substring(0, 1).equals("#")) {
				continue;
			}
			int number = text.indexOf("=");
			if (number != -1) {
				if (Integer.parseInt(text.substring(0, number)) == cityid) {
					text = text.substring(number + 1, text.length());
					break;
				}
			}
		}
		text = text.trim();
		return text;
	}
}
