package com.da;

import com.da.utils.AppUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
 * Date: 2022-05-09
 * Time: 9:50
 */
public class Test {
    public static void main(String[] args) throws ParseException {
        String startTime = "2020年5月8日";
        String endTime = "2020年9月8日";
        List<String> time = AppUtil.getSegmentTime(startTime, endTime, 4);
        System.out.println(time);
    }
}
