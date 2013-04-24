package com.anjuke.ui.page;

/**
 * 安居客 - 问答频道首页 http://shanghai.anjuke.com/ask
 * 
 * @author grayhu
 * @updater agneszhang
 * */

public class Ajk_Ask {
	
	//问答
	/** 问答 - 搜索列表页 - 输入框 */
	public static final String S_BOX = "//input[@id='formTxt']";
	/** 问答 - 搜索 - 搜索回答按钮 */
	public static final String S_BTN = "//form[@id='frmAsk']/input[1]";
	
	
	//问答搜索结果页
	/** 问答搜索结果页 - 列表 - 第一页问答内容 */
	public static final String LIST_COUNT = "//ul[@class='q_list']/li";

	/** 问答搜索结果页 - 列表 - 翻页按钮（上一页） */
	public static final String FANYE = "//span[@class='nolink prexpage']";
	/** 问答搜索结果页 - 列表 - 高亮关键字 */
	public static final String HIGHLIGHT_KEY = "//ul[@class='q_list']/li[1]/div/a/span";
	/** 问答搜索结果页 - 列表 - 异常搜索无结果，显示全部问答列表页 */
	public static final String NO_FOUND = "//div[@class='SearchIndepNoResult']";

	
	//updater  agneszhang
	/** 问答搜索结果页 - 列表 - 面包屑上的关键字 */
	public static final String CRUMBS_KEY = "//*[@id='bread_crumb']/span";
	/** 问答搜索结果页 - 列表 - 列表页第一条数据的问答标题 */
	public static final String FIRST_TITLE = "//*[@id='content']/div[2]/div[1]/div/ul/li[1]/div[1]/a";
	/** 问答搜索结果页 - 列表 - 正常搜索结果列表页各数据集 */
	public static final String NORMALSEARCHLIST = "//*[@id='content']/div[2]/div[1]/div/ul/li";
	/** 问答搜索结果页 - 列表 - 正常搜索无结果 */
	public static final String NOT_FOUND = "//*[@id='frmAsk']/div/div[1]";
	/** 问答 - 问答首页- 输入框 */
	public static final String SEARCHBOX = "//*[@id='formTxt']";
	/** 问答 - 问答首页 - 搜索回答按钮 */
	public static final String SEARCHBTN = "//*[@id='frmAsk']/input[1]";
	/**问答搜索结果页--列表各数据集--返回其中某条数据
	 * @param n为要取的第n条数据
	 * */
	public static String getSearchListElement(int n){
		String element = "//*[@id='content']/div[2]/div[1]/div/ul/li["+n+"]/div[1]/a";
		return element;
	}
	/** 问答 - 问答首页 - 外部专家登录右侧，登录模块中的“个人中心”按钮 */
	public static final String ExpertPersonal = "//*[@id='content']/div[3]/div[1]/div[1]/div[2]/div/a[2]";
	/** 问答 - 问答首页 - 右侧专家动态模块列表*/
	public static final String ExpertMoving = "//*[@id='content']/div[3]/div[2]/ul/li";

	
	/**问答 - 问答首页 - 未登录状态下常见问题TAB*/
	public static final String NomalQuestion = "//*[@id='nomaltab0']";
	/**问答 - 问答首页 - 未登录状态下常见问题列表*/
	public static final String NomalQuestionList = "//*[@id='nomalUl0']/li";
	/**问答 - 问答首页 - 未登录状态下常见问题列表解答专家姓名*/
	public static final String NomalQuestionExpertName = "//*[@id='nomalUl0']/li[1]/div[1]/a";
	/**问答 - 问答首页 - 未登录状态下房产问题TAB*/
	public static final String EstateQuestion = "//*[@id='nomaltab1']";
	/**问答 - 问答首页 - 未登录状态下房产问题列表*/
	public static final String EstateQuestionList = "//*[@id='nomalUl1']/li";
	/**问答 - 问答首页 - 未登录状态下法律问题TAB*/
	public static final String LawQuestion = "//*[@id='nomaltab2']";
	/**问答 - 问答首页 - 未登录状态下法律问题列表*/
	public static final String LawQuestionList = "//*[@id='nomalUl2']/li";
	/**问答 - 问答首页 - 未登录状态下贷款问题TAB*/
	public static final String LoanQuestion = "//*[@id='nomaltab3']";
	/**问答 - 问答首页 - 未登录状态下贷款问题列表*/
	public static final String LoanQuestionList = "//*[@id='nomalUl3']/li";
	/**问答 - 问答首页 - 未登录状态下装修风水TAB*/
	public static final String	FsQuestion = "//*[@id='nomaltab4']";
	/**问答 - 问答首页 - 未登录状态下装修风水列表*/
	public static final String	FsQuestionList = "//*[@id='nomalUl4']/li";
	/**问答 - 问答首页 - 未登录状态下新房问题TAB*/
	public static final String	NewFangQuestion = "//*[@id='nomaltab5']";
	/**问答 - 问答首页 - 未登录状态下新房问题列表*/
	public static final String	NewfangQuestionList = "//*[@id='nomalUl5']/li";
	
	
	/**问答 - 问答首页 - 待解决问题列表*/
	public static final String	ToBeResolvedQuestionList = "//*[@id='content']/div[2]/div[4]/ul/li";
	/**问答 - 问答首页 - 待解决问题列表第一条数据*/
	public static final String	ToBeResolvedQuestionFirstTitle = "//*[@id='content']/div[2]/div[4]/ul/li[1]/div/a[2]";
	/**问答 - 问答首页 - 待解决问题列表第一条数据的回复数*/
	public static final String	ToBeResolvedQuestionFirstAnswerNum = "//*[@id='content']/div[2]/div[4]/ul/li[1]/span/a";
}
