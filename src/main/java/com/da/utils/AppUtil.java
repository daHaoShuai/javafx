package com.da.utils;

import com.da.App;
import com.deepoove.poi.XWPFTemplate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
 * Time: 10:07
 */
public class AppUtil {

    //    配置文件所在的目录
    public static final String CONFIG_PATH = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()
            + File.separator + "daTemplate";

    //    切换页面
    public static void changeView(String view) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL url = loader.getClassLoader().getResource("fxml/" + view + ".fxml");
        loader.setLocation(url);
        Object load = loader.load();
        App.scene.setRoot((Parent) load);
    }

    //    编辑配置文件
    public static void editConfigFile(String templateName, String[] keys, List<String> data) throws IOException {
//            获取配置文件
        String configFile = CONFIG_PATH + File.separator + "config" + File.separator + "da.txt";
//        要写入配置文件的内容
        String configContent = "[template]=" + templateName + "\n";
        for (int i = 0; i < data.size(); i++) {
//            修改配置文件
            configContent += keys[i] + "=" + data.get(i) + "\n";
        }
        FileOutputStream os = new FileOutputStream(configFile);
        os.write(configContent.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    //    处理模板文件,返回成功或者失败
    public static boolean handleTemplate(File template, Map<String, Object> data) {
        boolean isOk = false;
        FileInputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(template);
//            先在模板目录下创建result目录
            Path resultPath = Paths.get(template.getParent() + File.separator + "result");
            if (!Files.exists(resultPath)) {
                Files.createDirectory(resultPath);
            }
//            生成替换好的模板文件到result目录下
            os = new FileOutputStream(resultPath + File.separator + template.getName());
            XWPFTemplate render = XWPFTemplate.compile(is).render(data);
            render.writeAndClose(os);
            isOk = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isOk;
    }

    //    创建弹出提示页面
    public static void createDialog(String message) {
        DialogPane pane = new DialogPane();
        pane.setHeaderText("提示信息");
        pane.setContentText(message);
//        添加确定按钮
        pane.getButtonTypes().add(ButtonType.OK);
//        获取确定按钮
        Button button = (Button) pane.lookupButton(ButtonType.OK);

        Stage stage = new Stage();
        stage.setScene(new Scene(pane));
//            设置窗口为模态窗口
        stage.initOwner(App.appStage);
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);//禁止窗口拉伸
        stage.show();
        button.setOnAction(e -> stage.close());
    }

    //    分配时间
    public static List<String> getSegmentTime(String startTime, String endTime, int count) throws ParseException {
//        格式化时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
//        获取开始和结束的时间
        Date startData = simpleDateFormat.parse(startTime);
        Date endData = simpleDateFormat.parse(endTime);
//        算出2个日期间的天数
        long day = 24 * 60 * 60 * 1000;
        long interval = (endData.getTime() - startData.getTime()) / day;
//        均分间隔天数
        long section = interval / count;
//        保存生成好的所有时间
        List<String> datas = new ArrayList<>();
//        记录变化的时间节点
        Date temp = startData;
        for (int i = 1; i < count * 2 - 1; i++) {
            String data = "";
            if (i % 2 == 0) {
                temp = new Date(temp.getTime() + day);
            } else {
                temp = new Date(temp.getTime() + section * day);
            }
            datas.add(simpleDateFormat.format(temp));
        }
        return datas;
    }
}
