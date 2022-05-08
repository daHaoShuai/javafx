package com.da.service;

import com.da.utils.AppUtil;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import java.io.File;
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
 * Date: 2022-05-08
 * Time: 11:56
 */
public class TemplateService extends Service<String> {

    //    模板文件
    private final File template;
    //    填充的数据
    private final Map<String, Object> data;
    //    提示Label
    private final Label resMessage;

    public TemplateService(File template, Map<String, Object> data, Label resMessage) {
        this.template = template;
        this.data = data;
        this.resMessage = resMessage;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            //            更新UI线程的东西
            @Override
            protected void updateValue(String value) {
                if ("成功".equals(value)) {
                    resMessage.setText("模板生成" + value + ",生成的文件为 " + template.getParent() + File.separator + "生成的" + template.getName());
                } else {
                    resMessage.setText("模板生成" + value);
                }
            }

            @Override
            protected String call() throws Exception {
                boolean isOk = AppUtil.handleTemplate(template, data);
                return isOk ? "成功" : "失败";
            }
        };
    }
}
