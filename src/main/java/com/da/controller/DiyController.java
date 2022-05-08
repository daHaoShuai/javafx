package com.da.controller;

import com.da.service.ReadConfigTemplateService;
import com.da.utils.AppUtil;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
 * Time: 19:15
 * 使用自定义的配置文件来生成
 */
public class DiyController {

    //    模板文件
    public TextField templates;

    //    文本域的内容
    public TextArea content;


    //    生成模板
    public void createTemplate(ActionEvent actionEvent) throws IOException {
        if ("".equals(templates.getText()) || "".equals(content.getText())) {
            AppUtil.createDialog("请仔细检查输入是否完成");
            return;
        }
        String configFile = AppUtil.CONFIG_PATH + File.separator + "config" + File.separator + "da.txt";
        FileOutputStream os = new FileOutputStream(configFile);
        String writeData = "[template]=" + templates.getText() + "\n" + content.getText();
//        写入配置文件
        os.write(writeData.getBytes(StandardCharsets.UTF_8));
        os.close();
//        用新线程读取配置文件生成模板
        ReadConfigTemplateService service = new ReadConfigTemplateService();
        service.start();
    }

    //    回首页
    public void goHomePage(ActionEvent actionEvent) throws IOException {
        AppUtil.changeView("index");
    }
}
