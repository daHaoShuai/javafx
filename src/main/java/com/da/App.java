package com.da;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


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
 * Time: 17:28
 */
public class App extends Application {
    //    打开弹窗时,用于控制最底层的界面
    public static Stage appStage;
    //    用于切换页面
    public static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        appStage = stage;
//        加载index.fxml文件
        FXMLLoader initLoader = new FXMLLoader();
        initLoader.setLocation(initLoader.getClassLoader().getResource("fxml/index.fxml"));
        AnchorPane an = initLoader.load();
//        显示fxml的场景
        scene = new Scene(an);
        stage.setScene(scene);
        stage.getIcons().add(new Image("bg.png"));
        //禁止窗口拉伸
        stage.setResizable(false);
        stage.show();
    }
}


