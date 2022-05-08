package com.da.controller;

import com.da.App;
import com.da.service.TemplateService;
import com.da.utils.AppUtil;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author Da
 * Description: <br/>
 * 三十年生死两茫茫，写程序，到天亮。
 * 千行代码，Bug何处藏。
 * 纵使上线又怎样，朝令改，夕断肠。
 * 领导每天新想法，天天改，日日忙。
 * 相顾无言，惟有泪千行。
 * 每晚灯火阑珊处，夜难寐，又加班。
 * Date: 2022-05-07
 * Time: 18:45
 * 自定义模板生成
 */
public class IndexController {

    //    选好模板后显示信息
    public Label message;
    //    文本域
    public TextArea text;
    //    模板生成的提示
    public Label resMessage;
    //    选择的模板文件
    private File template = null;

    public void initialize() {
        App.appStage.setTitle("自定义模板生成");
    }


    //    选择模板文件
    public void selectTemplate(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择你的模板文件");
//        只让选docx文件
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("模板文件", "*.docx"));
        Stage stage = new Stage();
//        关闭窗口会返回刚刚选择的模板文件
        template = fileChooser.showOpenDialog(stage);
        if (template != null) {
            message.setText("你选择的模板为 " + template.getAbsolutePath());
        }
    }

    //    填充模板
    public void create(ActionEvent actionEvent) {
//        处理文本域中的内容
        final String value = this.text.getText();
//        处理一点意外的情况
        if (this.template == null || "".equals(value)) {
            AppUtil.createDialog("没有找到模板文件或者文本域中没有数据,请检查");
            return;
        }
        final Map<String, Object> data = new HashMap<>();
//        文本域中有=号才开始分割
        if (value.contains("=")) {
            //        用回车分割每一行的数据
            String[] temp = value.split("\\n");
//        至少有一行
            if (temp.length > 0) {
                for (String s : temp) {
                    String[] d = s.split("=");
//                能用=分开的才添加到data中
                    if (d.length % 2 == 0) {
                        data.put(d[0], d[1]);
                    }
                }
            }
//        用新的线程来生成模板,然后更新UI
            resMessage.setText("正在生成模板");
            TemplateService templateService = new TemplateService(this.template, data, this.resMessage);
            templateService.start();
        } else {
            AppUtil.createDialog("文本域中数据格式有误,请检查输入");
        }
    }

    //    跳转立项书模板页面
    public void goOnePage(ActionEvent actionEvent) throws IOException {
        AppUtil.changeView("one");
    }

    //    跳转制度模板页面
    public void goTwoPage(ActionEvent actionEvent) throws IOException {
        AppUtil.changeView("two");
    }

    //    使用配置文件高级配置
    public void diyPage(ActionEvent actionEvent) throws IOException {
        AppUtil.changeView("diy");
    }
}
