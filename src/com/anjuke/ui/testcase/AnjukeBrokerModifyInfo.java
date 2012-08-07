package com.anjuke.ui.testcase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Ajk_BrokerIndex;
import com.anjuke.ui.page.Login_Anget;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;


/**
* @Todo 经纪人修改资料
* @author fjzhang
* @since 2012-8-7
* @file AnjukeBrokerdata.java
*
*/
public class AnjukeBrokerModifyInfo {
    Browser bs = null;
    String url = "http://my.anjuke.com/login/";
    String username = "ajk_sh";
    String password = "anjukeqa";

    @BeforeMethod
    public void setUp() {
        bs = FactoryBrowser.factoryBrowser();
    }
    @AfterMethod
    public void tearDown(){
        bs.quit();
        bs = null;
    }
    public void Login(){
        bs.get(url);
        bs.type(Login_Anget.USERNAME, username, "输入用户账号");
        bs.type(Login_Anget.PASSWORD,password, "输入用户密码");
        bs.click(Login_Anget.BTN, "点击登录");
    }
    @Test
    public void testBrokerregister(){
        Login();
        bs.click(Ajk_BrokerIndex.updateinfo, "进入更新经纪人资料页面");
        bs.click(Ajk_BrokerIndex.updateemail, "更新电子邮件");
        String oldemail = bs.getText(Ajk_BrokerIndex.oldemail, "获得旧的邮件地址");
        String newemail = null;
        if ( oldemail == "ajk_sh@anjuke.com"){
            newemail="ajk_sh@anjukeinc.com";
        }else{
            newemail="ajk_sh@anjuke.com";
        }
        bs.type(Ajk_BrokerIndex.newemail, newemail, "输入新的邮件地址");
        bs.type(Ajk_BrokerIndex.password, password, "输入登录密码");
        bs.click(Ajk_BrokerIndex.btn, "提交更改邮件");
        String actualText = bs.getText(Ajk_BrokerIndex.btn, "获取更改邮件成功提示");
        bs.assertContains(actualText, "邮件修改成功");
    }

}

