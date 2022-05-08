package com.da.controller;

import com.da.App;
import com.da.utils.AppUtil;
import javafx.event.ActionEvent;

import java.io.IOException;

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

    public void initialize() {
        App.appStage.setTitle("制度模板生成");
    }

    public void goHomePage(ActionEvent actionEvent) throws IOException {
        AppUtil.changeView("index");
    }
}
