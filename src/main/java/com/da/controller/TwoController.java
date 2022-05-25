package com.da.controller;

import com.da.App;
import com.da.service.ReadConfigTemplateService;
import com.da.utils.AppUtil;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Author Da
 * Description: <br/>
 * 三十年生死两茫茫，写程序，到天亮。
 * 千行代码，Bug何处藏。
 * 纵使上线又怎样，朝令改，夕断肠。
 * 领导每天新想法，天天改，日日忙。
 * 相顾无言，惟有泪千行。
 * 每晚灯火阑珊处，夜难寐，又加班。
 * Date: 2022-05-08
 * Time: 17:00
 */
public class TwoController {

    //    公司名字
    public TextField name;
    //    时间
    public TextField time;


    public void initialize() {
        App.appStage.setTitle("制度模板生成");
        App.appStage.setResizable(false);
    }

    public void goHomePage() throws IOException {
        AppUtil.changeView("index");
    }

    //    生成模板
    public void createTemplate() throws IOException {
        if ("".equals(name.getText()) || "".equals(time.getText())) {
            AppUtil.createDialog("请仔细检查输入");
            return;
        }

        String[] keys = {"name", "time"};
        List<String> info = Arrays.asList(name.getText(), time.getText());
//        写入配置文件
        AppUtil.editConfigFile("two", keys, info);
//        新线程生成模板
        ReadConfigTemplateService service = new ReadConfigTemplateService();
        service.start();
    }
}
