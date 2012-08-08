package com.anjuke.ui.testcase;
import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Broker_info;
import com.anjuke.ui.page.Login_Anget;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;
import com.anjukeinc.iata.ui.report.Report;


/**
* @Todo 经纪人修改资料
* @author fjzhang
* @since 2012-8-7
* @file AnjukeBrokerdata.java
* @url http://my.anjuke.com/user/modify/brokerinfo
*
*/
public class AnjukeBrokerModifyInfo {
    Browser bs = null;
    String url = "http://my.anjuke.com/login/";
    String username = "ajk_sh";
    String passwd = "anjukeqa";

    @BeforeMethod
    public void setUp() {
        bs = FactoryBrowser.factoryBrowser();
    }
    @AfterMethod
    public void tearDown(){
        Report.seleniumReport("", "");
        bs.quit();
        bs = null;
    }
    public void Login(){ //经纪人登录
        bs.get(url);
        bs.type(Login_Anget.USERNAME, username, "输入用户账号");
        bs.type(Login_Anget.PASSWORD,passwd, "输入用户密码");
        bs.click(Login_Anget.BTN, "点击登录");
    }
    public int getrandom(){ //取得4以内的随机数
        Random a = new Random();
        int sel =0;
        while(true){
            sel = a.nextInt(4);
            if (sel != 0){
                break;
            }
        }
        return sel;
    }
    //@Test
    public void testBrokeremail(){
        /**
         * 修改经纪人邮件地址
         */
        Login();
        bs.click(Broker_info.updateinfo, "进入更新经纪人资料页面");
        bs.click(Broker_info.updateemail, "更新电子邮件");
        String oldemail = bs.getText(Broker_info.oldemail, "获得旧的邮件地址");
        String newemail = null;
        System.out.println(oldemail);
        if ( oldemail.equals("ajk_sh@anjuke.com")){
            newemail="ajk_sh@anjukeinc.com";
        }else{
            newemail="ajk_sh@anjuke.com";
        }
        System.out.println(newemail);
        bs.type(Broker_info.newemail, newemail, "输入新的邮件地址");
        bs.type(Broker_info.password, passwd, "输入登录密码");
        //bs.click(Broker_info.btn, "提交更改邮件");
        bs.findElement(Broker_info.btn, "", 60).click();
        String actualText = bs.getText(Broker_info.oktext, "获取更改邮件成功提示");
        System.out.println(actualText);
        bs.assertContains(actualText, "邮箱修改成功");
    }
    @Test
    public void testBrokerupdateinfo(){
        /**
         * 更新经纪人资料信息
         */
        Login();
        bs.get("http://my.anjuke.com/user/broker/brokerinfo");
        bs.click(Broker_info.UPDATEAREALINK, "进入修改经纪人资料页面");
        //更新区域
        bs.select(Broker_info.AREA, getrandom());
        //更新公司
        if (bs.findElement(Broker_info.INDIVIDUAL, "检查公司是否被选中", 60).isSelected()){
            bs.click(Broker_info.COMPANY, "选择公司", 60);
            bs.type(Broker_info.COMPANYTEXT, "测试公司-XFHONG", "修改公司名称");
        }else{
            bs.click(Broker_info.INDIVIDUAL, "更改独立经济人");
        }
        //更新版块
        bs.select(Broker_info.BLOCK, getrandom());
        bs.uploadFile(Broker_info.IMAGEUPLOAD, "d:\\320x240.jpg", "上传用户图片");
        bs.click(Broker_info.SUBMIT, "提交资料修改");
        String actualText = bs.getText(Broker_info.INFOOKTEXT, "获取修改资料提交成功文本");
        bs.assertContains(actualText, "您的资料修改已提交，工作人员会在1-2个工作日内为您审核。");
    }

}

