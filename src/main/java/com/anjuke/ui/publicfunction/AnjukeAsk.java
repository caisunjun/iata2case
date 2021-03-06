package com.anjuke.ui.publicfunction;

import com.anjuke.ui.page.*;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.report.Report;

import java.sql.*;
import java.util.Random;

/**
 * 该类主要完成问答提问，回答，普通用户个人中，问答首页搜索等公共模块的功能及数据检测功能
 * 
 * @author agneszhang
 * @time 2012-09-19
 */

public class AnjukeAsk {
	/*
	 * public Browser driver; public AnjukeAsk(Browser driver){ this.driver =
	 * driver; }
	 */
	public String[] expertlist = { Ajk_AskQuestion.EXPERT_LOAN, Ajk_AskQuestion.EXPERT_LAW, Ajk_AskQuestion.EXPERT_ZX, Ajk_AskQuestion.EXPERT_FS, Ajk_AskQuestion.EXPERT_FC };

	// 提交问题
	/**
	 * 输入问题的标题和描述，并选择指定某类专家，提交问题.
	 * 
	 * @param driver
	 *            浏览器
	 * @param question_title
	 *            问题的标题内容
	 * @param question_description
	 *            问题的描述内容
	 * @param type
	 *            对应专家的类型（0-贷款专家，1-法律专家，2-装修专家，3-风水专家，4-房产专家）
	 */
	public String submitQuestion(Browser driver, String question_title, String question_description, int type) {
		String question_href = "";
		while (driver.check(Ajk_AskQuestion.REG)) {
			driver.refreshPage();
		}
		// String aa = Init.G_objMap.get("anjuke_qa_question_input_title_text");
		// System.out.println("aaaaaaaaaaaaaaaa======"+aa);
		driver.type(Ajk_AskQuestion.TITLE, question_title, "输入问题的标题");
		if (!driver.getAttribute(Ajk_AskQuestion.OPENMORE, "class").equals("forclose")) {
			driver.click(Ajk_AskQuestion.OPENMORE, "点击中间的展开条");
		}
		driver.type(Ajk_AskQuestion.DISCRIPTION, question_description, "输入问题的描述");

		// 5类专家类型定位器数组,并随机选择其中某类专家
		// type=0--贷款专家，type=1--法律专家，type=2--装修专家，type=3--风水专家，type=4--房产专家

		driver.click(expertlist[type], "随机选择其中某类专家");// type为0-4；表示专家类型的locator数组里的下标
		driver.click(Ajk_AskQuestion.SUBMIT, "点击提问按钮");
		// 进入提问成功页
		if (driver.check(Ajk_AskQuestionSuccess.VIEWBUTTON)) {
			question_href = driver.getAttribute(Ajk_AskQuestionSuccess.VIEWBUTTON, "href");
			driver.click(Ajk_AskQuestionSuccess.VIEWBUTTON, "点击查看按钮进入问答单页");
			Report.writeHTMLLog("张璐的测试报告之提问是否成功", "提问成功且问题URL是：" + question_href, Report.PASS, "");
		} else {
			String ps = driver.printScreen();
			Report.writeHTMLLog("张璐的测试报告之提问是否成功", "提问失败，没有进入提问成功页", Report.FAIL, ps);
		}
		return question_href;
	}

	// 返回专家类型的text文本内容
	/**
	 * 返回某类专家的类型名称.
	 * 
	 * @param type
	 *            对应专家的类型（0-贷款专家，1-法律专家，2-装修专家，3-风水专家，4-房产专家）
	 */
	public String getExpertText(Browser driver, int type) {
		String expertText = driver.getText(expertlist[type], "返回指定专家的类型名称");
		return expertText;
	}

	/**
	 * 问答单页回答问题
	 * 
	 * @param content为输入回答的内容
	 */
	public void submitAnswer(Browser driver, String content) {
		// 获取当前列表页所有回答总条数
		int m, n;
		m = 0;
		n = 0;
		boolean relate = true;
		if (driver.check(Ajk_AskView.RELATED) && driver.getText(Ajk_AskView.RELATED, "获取相关问题").equals("相关问题")) {
			m = driver.getElementCount(Ajk_AskView.ANSWERLISTS_Related);
			relate = true;
			System.out.println("回答前一共有" + m + "条回答内容" + relate);
		} else if (!driver.check(Ajk_AskView.RELATED)) {
			m = driver.getElementCount(Ajk_AskView.ANSWERLISTS_NoRelated);
			relate = false;
			System.out.println("回答前一共有" + m + "条回答内容" + relate);
		}

		if (driver.getAttribute(Ajk_AskView.IANSWER, "class").equals("answer-btn down")) {
			driver.click(Ajk_AskView.IANSWER, "点击我来帮你回答按钮");
		}
		driver.type(Ajk_AskView.INPUTANSWER, content, "输入回答内容");
		driver.click(Ajk_AskView.SUBMITANSWER, "点击提交回答的按钮");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (driver.check(Ajk_AskView.RELATED) && driver.getText(Ajk_AskView.RELATED, "获取相关问题").equals("相关问题")) {
			n = driver.getElementCount(Ajk_AskView.ANSWERLISTS_Related);
			System.out.println("回答后一共有" + n + "条回答内容");
		} else if (!driver.check(Ajk_AskView.RELATED)) {
			n = driver.getElementCount(Ajk_AskView.ANSWERLISTS_NoRelated);
			System.out.println("回答后一共有" + n + "条回答内容");
		}

		if (n > m) {
			for (int i = 1; i <= n;) {
				System.out.println("正在匹配第" + i + "条");
				String actual = driver.getText(Ajk_AskView.getAnswerElement(i, relate), "取第i条回复的数据");
				if (actual.equals(content)) {
					Report.writeHTMLLog("问答单页判断回答是否成功", "问答单页回答列表中有一条数据与实际值是匹配的", Report.PASS, "");
					System.out.println("找到成功匹配的在第" + i + "条");
					break;
				} else
					i++;
				if (i == n + 1) {
					Report.writeHTMLLog("问答单页判断回答是否成功", "回答失败：问答单页回答列表中没有一条数据与实际值是匹配的:" + "^预期值" + content + "^实际值" + actual, Report.FAIL, "");
					System.out.println("当前列表页没有找到成功匹配的，回答失败");
				}
			}
		} else {
			Report.writeHTMLLog("问答单页判断回答是否成功", "回答失败：问答列表没有更新", Report.FAIL, "");
			System.out.println("问答列表没有更新，回答失败");
		}

		// driver.assertEquals(content,
		// driver.getText(Ajk_AskView.getAnswerElement(m), "取第M条即最后一条回复的数据"),
		// "判断回答是否成功", "");
	}

	/**
	 * 问答单页--补充问题
	 * 
	 * @param addContent
	 *            :补充问题的内容
	 * 
	 */
	public void submitSupplement(Browser driver, String addContent) {
		driver.click(Ajk_AskView.AddLi, "点击补充问题TAB");
		driver.type(Ajk_AskView.Supplement, addContent, "输入补充问题内容");
		driver.click(Ajk_AskView.SupplementSubmit, "点击补充框下面的确认提交按钮");
		driver.assertEquals(addContent, getAskViewDescriptionAdd(driver), "检测补充问题是否成功", "检测补充问题内容是否与实际一致");
	}

	/**
	 * 个人中心提问列表的数据检测
	 * 
	 * @expertTitle 为用户提问成功后的问答标题
	 */
	public void checkNormalUserAskInfo(Browser driver, String expertTitle) {
		driver.click(Public_HeaderFooter.HEADER_UserName,"进入用户中心");
		driver.switchWindo(2);
		driver.click(Ajk_AskNormalUserInfo.MYASK, "点击左侧导航我的问答");

		driver.assertNonEquals(null, getNormalUserExperience(driver), "个人中心提问列表的数据检测", "检测经验值是否为空");
		driver.assertNonEquals(null, getNormalUserGold(driver), "个人中心提问列表的数据检测", "检测金币值是否为空");
		driver.assertNonEquals(null, getNormalUserAskNum(driver), "个人中心提问列表的数据检测", "检测提问数是否为空");
		driver.assertNonEquals(null, getNormalUserAnswerNum(driver), "个人中心提问列表的数据检测", "检测回答数是否为空");
		driver.assertNonEquals(null, getNormalUserAdoptionRate(driver), "个人中心提问列表的数据检测", "检测采纳率是否为空");
		driver.assertEquals(expertTitle, getNormalUserMyQuestionListFirst(driver), "个人中心提问列表的数据检测", "检测最新提问的问答标题是否正确--即最新的提问是否被记录");
		/*
		 * driver.assertTrue(driver.getText(Ajk_AskNormalUserInfo.EXPERIENCE,
		 * ""), sTCase, sDetails)
		 */

		/*
		 * if(!getNormalUserExperience(driver).equals("")){
		 * Report.writeHTMLLog("个人中心提问列表的数据检测", "普通用户经验值是否显示正常", Report.PASS,
		 * ""); } else{ Report.writeHTMLLog("个人中心提问列表的数据检测", "普通用户经验值是否显示正常",
		 * Report.FAIL, ""); }
		 */

	}

	/** 个人中心提问列表的数据检测 -- 返回当前用户显示的经验值数据 */
	public String getNormalUserExperience(Browser driver) {
		return driver.getText(Ajk_AskNormalUserInfo.EXPERIENCE, "获取当前用户显示的经验值数据");
	}

	/** 个人中心提问列表的数据检测 -- 返回当前用户显示的金币值数据 */
	public String getNormalUserGold(Browser driver) {
		return driver.getText(Ajk_AskNormalUserInfo.GOLD, "获取当前用户显示的金币值数据");
	}

	/** 个人中心提问列表的数据检测 -- 返回当前用户显示的提问数值数据 */
	public String getNormalUserAskNum(Browser driver) {
		return driver.getText(Ajk_AskNormalUserInfo.ASKNUM, "获取当前用户显示的提问数值数据");
	}

	/** 个人中心提问列表的数据检测 -- 返回当前用户显示的回答数值数据 */
	public String getNormalUserAnswerNum(Browser driver) {
		return driver.getText(Ajk_AskNormalUserInfo.ANSWERNUM, "获取当前用户显示的回答数值数据");
	}

	/** 个人中心提问列表的数据检测 -- 返回当前用户显示的采纳率数据 */
	public String getNormalUserAdoptionRate(Browser driver) {
		return driver.getText(Ajk_AskNormalUserInfo.ADOPTIONRATE, "获取当前用户显示的采纳率数据");
	}

	/** 个人中心提问列表的数据检测 -- 返回我提出的问题列表中第一条数据问答标题 */
	public String getNormalUserMyQuestionListFirst(Browser driver) {
		return driver.getText(Ajk_AskNormalUserInfo.MYQUESTIONSLISTFIRST, "获取我提出的问题列表中第一条数据问答标题");
	}

	/**
	 * 问答首页--搜索功能检测 1.处理异常搜索的情况 2.处理正常搜索无结果的情况
	 * 3.处理正常搜索有结果的情况：判断搜索结果的某个问答的数据中是否模糊匹配所输入的关键字
	 * 
	 * @param key为搜索框中输入的关键字
	 */
	public void checkAskSearch(Browser driver, String key) {
		driver.get("http://shanghai.anjuke.com/ask");
		if (!driver.check(Ajk_Ask.SEARCHBOX, 10)) {
			driver.refresh();
		} else {
			driver.type(Ajk_Ask.SEARCHBOX, key, "输入要搜索的关键字");
			driver.click(Ajk_Ask.SEARCHBTN, "点击搜索按钮");
		}
		int m = key.length();
		String[] keys = new String[m];
		String actualContent;
		if (key.trim().equals("") == true) {
			driver.assertEquals("所有问题", driver.getText(Ajk_Ask.CRUMBS_KEY, "获取面包屑上的关键字"), "检测异常搜索结果页", "检测异常搜索结果页面包屑上的关键字是否匹配");
		} else if (driver.check(Ajk_Ask.NOT_FOUND)) {
			driver.assertEquals(key, driver.getText(Ajk_Ask.CRUMBS_KEY, "获取面包屑上的关键字"), "检测搜索结果页", "面包屑上的关键字是否匹配");
			if (getNormalSearchNoResults(driver).contains(key)) {
				Report.writeHTMLLog("问答首页搜索关键字用例", "判断正常搜索无结果时是否正确显示友情提示语", Report.PASS, "");
			} else {
				Report.writeHTMLLog("问答首页搜索关键字用例", "判断正常搜索无结果时是否正确显示友情提示语", Report.FAIL, "");
			}
		}

		else {
			// driver.assertEquals(key, driver.getText(Ajk_Ask.CRUMBS_KEY,
			// "获取面包屑上的关键字"), "检测搜索结果页", "面包屑上的关键字是否匹配");
			actualContent = getListsAnyoneTitle(driver);
			for (int i = 0; i < m;) {
				keys[i] = key.substring(i, i + 1);
				if (actualContent.contains(keys[i])) {
					Report.writeHTMLLog("问答首页搜索关键字用例", "判断正常搜索结果是否模糊匹配搜索关键字", Report.PASS, "");
					break;
				} else
					i++;
				if (i == m) {
					Report.writeHTMLLog("问答首页搜索关键字用例", "判断正常搜索结果是否模糊匹配搜索关键字", Report.FAIL, "");
				}
			}
		}
	}

	/** 问答首页--搜索结果列表页，返回高亮的关键字 */
	public String getSearchHighlightKey(Browser driver) {
		int n = driver.getElementCount(Ajk_Ask.HIGHLIGHT_KEY);
		String actualKey = "";
		String[] partKey = null;
		int i;
		for (i = 1; i <= n; i++) {
			partKey[i - 1] = driver.getText(Ajk_Ask.HIGHLIGHT_KEY + "[" + i + "]", "获取高亮的所有关键字");
			actualKey = actualKey + partKey[i - 1];
		}
		return actualKey;
	}

	/** 问答单页--返回问答标题 */
	public String getAskViewTitle(Browser driver) {
		return driver.getText(Ajk_AskView.TITLE, "获取当前页任意一条数据的问答标题");
	}

	/** 问答单页--返回问答描述 */
	public String getAskViewDescription(Browser driver) {
		String description;
		if (driver.check(Ajk_AskView.DESCRIPTION)) {
			description = driver.getText(Ajk_AskView.DESCRIPTION, "获取当前页任意一条数据的问答描述");
		} else {
			description = "";
		}
		return description;
	}

	/** 问答单页--返回问答补充 */
	public String getAskViewDescriptionAdd(Browser driver) {
		String descriptionAdd;
		if (driver.check(Ajk_AskView.DESCRIPTIONADD)) {
			descriptionAdd = driver.getText(Ajk_AskView.DESCRIPTIONADD, "获取当前页任意一条数据的问答补充");
		} else {
			descriptionAdd = "";
		}
		return descriptionAdd;
	}

	/** 问答首页--搜索结果列表页，返回当前页任意一条数据的问答标题和描述及补充 */
	public String getListsAnyoneTitle(Browser driver) {
		String title, description, descriptionAdd, content;
		int m = driver.getElementCount(Ajk_Ask.NORMALSEARCHLIST);
		System.out.println("正常搜索列表页第一页共显示" + m + "条数据");
		// driver.assertIntEquals(12, m, "检测问答搜索列表页在有很多数据的情况下第一页最多展示条数是否为12条",
		// "检测问答搜索列表页第一次最多展示条数是否为12条");
		if (m == 12) {
			Report.writeHTMLLog("检测问答搜索列表页在有很多数据的情况下第一页最多展示条数", "检测问答搜索列表页在有很多数据的情况下第一页最多展示条数是否为12条", Report.PASS, "p1");
		} else {
			Report.writeHTMLLog("检测问答搜索列表页在有很多数据的情况下第一页最多展示条数", "检测问答搜索列表页在有很多数据的情况下第一页最多展示条数是否为12条", Report.FAIL, "p2");
		}

		Random rand = new Random();
		int t = rand.nextInt(m);
		driver.click(Ajk_Ask.getSearchListElement(t + 1), "点击进入当前页任意一条数据的问答单页");
		// driver.click(Ajk_Ask.FIRST_TITLE, "点击进入当前这个问答的问答单页");//这是第一条数据
		driver.switchWindo(2);
		title = driver.getText(Ajk_AskView.TITLE, "获取问答单页问答标题");
		if (driver.check(Ajk_AskView.DESCRIPTION)) {
			description = driver.getText(Ajk_AskView.DESCRIPTION, "获取当前页任意一条数据的问答描述");
		} else {
			description = "";
		}
		if (driver.check(Ajk_AskView.DESCRIPTIONADD)) {
			descriptionAdd = driver.getText(Ajk_AskView.DESCRIPTIONADD, "获取当前页任意一条数据的问答补充");
		} else {
			descriptionAdd = "";
		}
		content = title + description + descriptionAdd;
		driver.close();
		driver.switchWindo(1);
		return content;
	}

	/** 问答首页--搜索结果列表页，正常搜索无结果页时，返回友情提示语 */
	public String getNormalSearchNoResults(Browser driver) {
		return driver.getText(Ajk_Ask.NOT_FOUND, "正常搜索无结果页时，返回友情提示语");
	}

	/** 问答单页--采纳最佳答案 */
	public void AdoptBestAnswer(Browser driver) {
		driver.click(Ajk_AskView.AdoptBestAnswer, "点击第一条回答数据的”采纳最佳答案“按钮");
		driver.assertEquals("最佳答案", driver.getText(Ajk_AskView.BestAnswer, "最佳答案模块上的文案"), "检测采纳最佳答案是否成功", "检测采纳最佳答案是否成功");
	}

	/**
	 * 问答单页--编辑外部专家资料
	 * 
	 * @param trueName
	 *            :专家真实姓名
	 * @param mobile
	 *            ：专家手机号
	 * @param firmName
	 *            ：所在单位名称
	 * @param firmAdress
	 *            ：所在单位地址
	 * @param firmPhone
	 *            ：所在单位电话
	 * @param personalCV
	 *            ：个人简介
	 * @param imgPath
	 *            ：公司LOGO图片地址
	 */
	public void EditExternalExpertInfo(Browser driver, String trueName, String mobile, String firmName, String firmAdress, String firmPhone, String personalCV, String imgPath) {
		driver.click(Ajk_AskExpertView.Edit, "点击编辑资料按钮");
		driver.type(Ajk_AskExpertView.TrueName, trueName, "输入专家真实姓名");
		driver.type(Ajk_AskExpertView.Mobile, mobile, "输入专家手机号");
		driver.type(Ajk_AskExpertView.FirmName, firmName, "输入所在单位名称");
		driver.type(Ajk_AskExpertView.FirmAdress, firmAdress, "输入所在单位地址");
		driver.type(Ajk_AskExpertView.FirmPhone, firmPhone, "输入所在单位电话");
		driver.uploadFile(Ajk_AskExpertView.Upload, imgPath, "点击上传公司LOGO的浏览按钮");
		driver.type(Ajk_AskExpertView.Personalcv, personalCV, "输入个人简介");
		driver.click(Ajk_AskExpertView.Submitbutton, "点击确认提交按钮");
		String SubmitSuccessTitle = driver.getText(Ajk_AskExpertView.SubmitSuccess, "获取编辑资料成功后的提示语");
		System.out.println(SubmitSuccessTitle);
		driver.assertEquals("您的资料已更新成功，立刻去个人主页    去看", SubmitSuccessTitle, "判断外部专家的资料编辑是否修改成功", "");
	}

	/**
	 * 问答首页--右侧专家动态数据检测
	 * 
	 */

	public void checkExpertMovingData(Browser driver) {
		int n = driver.getElementCount(Ajk_Ask.ExpertMoving);
		System.out.println("专家动态模块一共展示" + n + "条数据");
		// driver.assertIntEquals(3, n, "检测问答右侧专家模块数据是否展示3条",
		// "检测问答右侧专家模块数据是否展示3条");
		if (n == 3) {
			Report.writeHTMLLog("检测问答右侧专家模块数据是否展示3条", "检测问答右侧专家模块数据是否展示3条", Report.PASS, "p3");
		} else {
			Report.writeHTMLLog("检测问答右侧专家模块数据是否展示3条", "检测问答右侧专家模块数据是否展示3条", Report.FAIL, "p4");
		}
		for (int i = 1; i <= n; i++) {
			String ExpertMovingTest = driver.getText(Ajk_Ask.ExpertMoving + "[" + i + "]", "获取每条数据的文本");
			driver.assertNonEquals(null, ExpertMovingTest, "检测专家动态模块的数据是否为空", "检测专家动态模块的数据是否为空");
		}
	}

	/**
	 * 访问指定城市问答首页
	 */
	public void getCityAskHome(Browser driver, String city) {
		driver.get("http://" + city + ".anjuke.com/ask");
	}

	/**
	 * 问答首页--未登录状态下6个TAB数据检测
	 * 
	 * @param tab
	 *            : 0表示常见问题TAB下列表， 1表示房产问题TAB下列表， 2表示法律问题TAB下列表， 3表示新房问题TAB下列表，
	 *            4表示贷款问题TAB下列表， 5表示装修风水TAB下列表，。
	 */
	public void checkFiveTabData(Browser driver, int tab) {
		String fiveTabElement[] = { Ajk_Ask.NomalQuestion, Ajk_Ask.EstateQuestion, Ajk_Ask.NewFangQuestion, Ajk_Ask.LawQuestion, Ajk_Ask.LoanQuestion, Ajk_Ask.FsQuestion

		};
		String fiveTabElementList[] = { Ajk_Ask.NomalQuestionList, Ajk_Ask.EstateQuestionList, Ajk_Ask.NewfangQuestionList, Ajk_Ask.LawQuestionList, Ajk_Ask.LoanQuestionList, Ajk_Ask.FsQuestionList

		};

		// 切换到指定TAB
		driver.moveToElement(fiveTabElement[tab]);
		String tabName = driver.getText(fiveTabElement[tab], "获取当前tab的名称");

		/*
		 * try { Thread.sleep(1000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		// 获取指定TAB下各数据
		int n1 = driver.getElementCount(fiveTabElementList[tab]);
		System.out.println("第" + (tab + 1) + "个TAB" + tabName + "列表一共展示" + n1 + "条数据");

		for (int i = 1; i <= n1; i++) {
			String QuestionData = driver.getText(fiveTabElementList[tab] + "[" + i + "]", "获取第" + (tab + 1) + "个TAB下每条数据的文本");
			String QuestionExpertName = driver.getText(fiveTabElementList[tab] + "[" + i + "]/div[1]/a", "获取第" + (tab + 1) + "个TAB下每条数据对应专家姓名的文本");
			System.out.println("每条数据的文本内容如下：" + QuestionData);
			System.out.println("每条数据下对应专家的姓名：" + QuestionExpertName + "\n");
			driver.assertNonEquals(null, QuestionData, "检测第" + (tab + 1) + "个TAB" + tabName + "下每条数据是否为空", "检测第" + (tab + 1) + "个TAB下每条数据是否为空");
			driver.assertNonEquals(null, QuestionExpertName, "检测第" + (tab + 1) + "个TAB" + tabName + "下专家姓名是否为空", "检测第" + (tab + 1) + "个TAB下专家姓名是否为空");
		}
	}

	/**
	 * 问答首页--未登录状态下等待解决问题列表数据检测
	 * 
	 */
	public void checkAskHomeToBeResolveQuestion(Browser driver) {
		int n = driver.getElementCount(Ajk_Ask.ToBeResolvedQuestionList);
		int ids[] = new int[n];
		for (int i = 1; i <= n; i++) {
			String QuestionTitle = driver.getText(Ajk_Ask.ToBeResolvedQuestionList + "[" + i + "]/div/a[2]", "获取每条问答的标题");
			String QuestionHref = driver.getAttribute(Ajk_Ask.ToBeResolvedQuestionList + "[" + i + "]/div/a[2]", "href");
			String[] HrefAndId = QuestionHref.split("\\D+"); // 按非数字分割链接字符串
			ids[(i - 1)] = Integer.valueOf(HrefAndId[1]); // 取分割后第二个元素即问答ID并转换为整型,并存放在数组里
			System.out.println("每条问答对应的ID号为：" + ids[(i - 1)]);
			String AnswerNum = driver.getText(Ajk_Ask.ToBeResolvedQuestionList + "[" + i + "]/span/a", "获取每条问答数据后面的回复数");
			driver.assertNonEquals(null, QuestionTitle, "检测待解决列表中每条问答标题是否为空", "检测待解决列表中每条问答标题是否为空");
			driver.assertNonEquals(null, AnswerNum, "检测待解决列表中每条问答数据的回答数是否为空", "检测待解决列表中每条问答数据的回答数是否为空");
		}

		// 连接数据库并检查问答对应的状态是否都为待解决
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException cnf_e) { // 如果找不到驱动类
			System.out.println("Driver Not Found: " + cnf_e);
		}

		String conURL = "jdbc:mysql://10.20.8.20:3306/user_db?useUnicode=true&characterEncoding=utf8"; // MySQL数据库Company的URL
		Connection con = null;
		Statement s = null;
		try {
			con = DriverManager.getConnection(conURL, "readonly", "aN2jU3ke"); // 连接数据库
			s = con.createStatement();
			for (int j = 0; j < n; j++) {
				ResultSet rs = s.executeQuery("SELECT questatus FROM qa_questions WHERE qid = " + ids[j] + ";"); // 提交查询问答ID，返回对应问答的状态表格保存在rs中
				while (rs.next()) {
					int questatus = rs.getInt("questatus");
					driver.assertIntEquals(1, questatus, "检测待解决列表的每条问答状态是否为1", "检测待解决列表的每条问答状态是否为1");
					if (questatus == 1) {
						System.out.println("这个问题是个待解决的问题状态questatus为" + questatus);
					} else {
						System.out.println("这个问题不是待解决状态且状态questatus为" + questatus);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (s != null)
					s.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}