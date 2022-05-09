package com.da.controller;

import com.da.App;
import com.da.service.ReadConfigTemplateService;
import com.da.utils.AppUtil;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
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
//            获取分割的时间段
            List<String> data = AppUtil.getSegmentTime(infoText.get(1), infoText.get(2), 4);
//            向原来的数组中加入时间
            for (int i = 0; i < data.size(); i++) {
                infoText.add(2 + i, data.get(i));
            }
//        模板中要填充的数据的键名
            String[] keys = {"name", "time", "time1", "time2", "time3", "time4", "time5", "time6", "endtime", "project", "p1", "p2"};
//            写入配置文件
            AppUtil.editConfigFile("one", keys, infoText);
//        使用新线程 读取配置文件并且创建模板
            ReadConfigTemplateService service = new ReadConfigTemplateService();
            service.start();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    //    回首页
    public void goHome(ActionEvent actionEvent) throws IOException {
        AppUtil.changeView("index");
    }
}
