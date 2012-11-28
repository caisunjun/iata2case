package com.anjuke.ui.testcase;
import java.util.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.anjuke.ui.page.Broker_info;
import com.anjuke.ui.page.Login_Anget;
import com.anjukeinc.iata.ui.browser.Browser;
import com.anjukeinc.iata.ui.browser.FactoryBrowser;


/**
* @Todo 个人设置-资料修改（邮箱、密码、个人资料）
* 个人资料修改：
* 更改工作区域（随机）
* 更改所属公司门店（独立经济人 - 测试公司-XFHONG）
* 传图片
* @author fjzhang
* @since 2012-8-7
* @file AnjukeBrokerdata.java
* @url http://my.anjuke.com/user/modify/brokerinfo
*
*/
public class AnjukeBrokerModifyInfo {
    Browser bs = null;
    String url = "http://agent.anjuke.com/login/";
    String username = "ajk_sh";
    String passwd = "anjukeqa";

    @BeforeMethod
    public void setUp() {
        bs = FactoryBrowser.factoryBrowser();
    }
    @AfterMethod
    public void tearDown(){
        bs.quit();
        bs = null;
    }
    public void Login(String username,String passwd){ //经纪人登录
        bs.get(url);
        bs.type(Login_Anget.USERNAME, username, "输入用户账号");
        bs.type(Login_Anget.PASSWORD,passwd, "输入用户密码");
        bs.click(Login_Anget.BTN, "点击登录");
        if(!bs.check("class^logo"))
        {bs.refresh();}
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
    @Test
    public void testBrokeremail(){
        /**
         * 修改经纪人邮件地址
         */
        Login(username,passwd);
        bs.click(Broker_info.brokerInfoTab, "进入更新经纪人资料页面");
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
        Login(username,passwd);
        bs.get("http://my.anjuke.com/user/broker/brokerinfo");
        bs.click(Broker_info.UPDATEAREALINK, "进入修改经纪人资料页面");
        //更新区域
        bs.select(Broker_info.AREA, getrandom());
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //更新版块
        bs.select(Broker_info.BLOCK, getrandom());
        //更新公司
        if (bs.findElement(Broker_info.INDIVIDUAL, "检查公司是否被选中", 60).isSelected()){
            bs.click(Broker_info.COMPANY, "选择公司", 60);
            bs.type(Broker_info.COMPANYTEXT, "测试公司-XFHONG", "修改公司名称");
            bs.click("//div[@id='company_auto_complete']/ul[1]", "点公司");
            bs.click(Broker_info.STORETEXT, "选择门店", 60);
            bs.type(Broker_info.STORETEXT, "测试", "修改门店名称");
            bs.click("//div[@id='store_auto_complete']/ul/li[2]", "点门店");
        }else{
            bs.click(Broker_info.INDIVIDUAL, "更改独立经济人");
        }
        //获得项目\tools里图片的绝对路径
        String imgPath = System.getProperty("user.dir") + "\\tools\\320x240.jpg";
        bs.uploadFile(Broker_info.IMAGEUPLOAD, imgPath, "上传用户图片");
        bs.click(Broker_info.SUBMIT, "提交资料修改");

        String actualText = null;
        actualText = bs.getText(Broker_info.INFOOKTEXT, "获取修改资料提交成功文本");
        bs.assertContains(actualText, "您的资料修改已提交，工作人员会在1-2个工作日内为您审核。");
    }
    
    @Test
    public void testBrokerUpdatePasswd(){
        String username="13000170010";
        String passwd = "123123";
        String url = "http://my.anjuke.com/user/modify/password";
        Login(username,passwd);
        bs.get(url);
        bs.type(Broker_info.OLDPASS, "123123", "输入旧密码");
        bs.type(Broker_info.NEWPASS, "123123", "输入新密码");
        bs.type(Broker_info.NEWPASS1, "123123", "确认密码");
        bs.click(Broker_info.SUBMITPASS, "提交修改密码");
        String actualText = bs.getText(Broker_info.PASSOK, "获取修改密码成功文本");
        bs.assertContains(actualText, "密码修改成功");
    }

}

