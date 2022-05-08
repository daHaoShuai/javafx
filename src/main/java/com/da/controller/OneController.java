package com.da.controller;

import com.da.App;
import com.da.service.ReadConfigTemplateService;
import com.da.utils.AppUtil;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
 * Time: 10:14
 */
public class OneController {
    //    公司名字
    public TextField name;
    //    开始时间
    public TextField time;
    //    结束时间
    public TextField endtime;
    //    项目名字
    public TextField project;
    //    项目评审组长
    public TextField p1;
    //    项目负责人
    public TextField p2;

    public void initialize() {
        App.appStage.setTitle("立项书模板生成");
    }

    //    创建立项书模板
    public void createTemplate(ActionEvent actionEvent) {
//        获取输入框中所有输入的内容
        List<String> inpText = Arrays.asList(name.getText(), time.getText(), endtime.getText(), project.getText(), p1.getText(), p2.getText());
//        获取不为空的列表
        List<String> infoText = inpText.stream().filter(i -> !"".equals(i)).collect(Collectors.toList());
//        如果有没有填的内容就提示
        if (inpText.size() > infoText.size()) {
            AppUtil.createDialog("还有没填的数据,请仔细填写完整");
            return;
        }

        try {
            //        模板中要填充的数据的键名
            String[] keys = {"name", "time", "endtime", "project", "p1", "p2"};
//            获取配置文件
            String configFile = AppUtil.CONFIG_PATH + File.separator + "config" + File.separator + "da.txt";
//        要写入配置文件的内容
            String configContent = "[template]=one\n";
            for (int i = 0; i < infoText.size(); i++) {
//            修改配置文件
                configContent += keys[i] + "=" + infoText.get(i) + "\n";
            }
            FileOutputStream os = new FileOutputStream(configFile);
            os.write(configContent.getBytes(StandardCharsets.UTF_8));
//        使用新线程 读取配置文件并且创建模板
            ReadConfigTemplateService service = new ReadConfigTemplateService();
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    回首页
    public void goHome(ActionEvent actionEvent) throws IOException {
        AppUtil.changeView("index");
    }
}
