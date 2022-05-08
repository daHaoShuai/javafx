package com.da.service;

import com.da.utils.AppUtil;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * Time: 15:30
 */
//读取桌面上的配置文件来生成对应的模板文件
public class ReadConfigTemplateService extends Service<String> {

    //    配置文件所在的目录
    private final String configPath = AppUtil.CONFIG_PATH;

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected void updateValue(String value) {
                AppUtil.createDialog(value);
            }

            @Override
            protected String call() throws Exception {
//                获取配置文件
                Path configFile = Paths.get(configPath + File.separator + "config" + File.separator + "da.txt");
                List<String> lines = Files.lines(configFile).collect(Collectors.toList());
//                获取要填充数据的模板们,配置文件的第一行
                String[] templates = lines.get(0).split("=")[1].split(",");
//                保存所有要生成的模板文件
                List<File> allFile = new ArrayList<>();
                for (String template : templates) {
                    Path tempPath = Paths.get(configPath + File.separator + template);
//                    获取当前目录下的所有要填充的文件
                    List<File> files = Files.list(tempPath).map(Path::toFile)
                            .filter(i -> !i.isDirectory() && i.getName().endsWith(".docx"))
                            .collect(Collectors.toList());
                    allFile.addAll(files);
                }
//                填充的数据
                Map<String, Object> data = new HashMap<>();
//                从第二行开始才是要填充的数据
                for (int i = 1; i < lines.size(); i++) {
                    String value = lines.get(i);
//                    如果这一行有=的时侯才继续操作
                    if (value.contains("=")) {
                        String[] mp = value.split("=");
                        data.put(mp[0], mp[1]);
                    }
                }
//                遍历所有模板文件替换数据
                allFile.forEach(file -> {
                    boolean isOk = AppUtil.handleTemplate(file, data);
                    if (isOk) {
                        System.out.println("生成模板" + file.getParent() + File.separator + "result" + File.separator + file.getName() + "成功");
                    }
                });
                return "模板生成完成了";
            }
        };
    }
}
