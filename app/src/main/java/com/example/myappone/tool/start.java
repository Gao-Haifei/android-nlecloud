package com.example.myappone.tool;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class start {
    public static String TOKEN ;  //登录成功的token
    public static String ApiTag ;  //传感器的标识
    public static String DeviceID ;  //设备id
    public static String ProjectId;  //项目id
    public final static String URL = "http://api.nlecloud.com/";

    public static String Device_zx; //设备是否在线


    public static String get_time(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String get_project_time(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        //先取得今天的日历日时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        //转换得到今天的日期
        String today = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, +1);
        return sdf.format(calendar.getTime());
    }
}
