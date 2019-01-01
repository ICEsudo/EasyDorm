package com.easydorm.easydorm.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    private static SimpleDateFormat getSimpleDateFormat(String str) {
        return new SimpleDateFormat(str);
    }

    private static SimpleDateFormat getOutputFormat() {
        String str = "";
        str += "yyyy年 ";
        str += "MM月dd日 ";
        str += "HH:mm";
        return getSimpleDateFormat(str);
    }

    private static SimpleDateFormat getInputFormat() {
        String str =  "yyyy-MM-dd HH:mm:ss";
        return getSimpleDateFormat(str);
    }


    public static String dateToString(Date date) {
        return getOutputFormat().format(date);
    }

    public static Date stringToDate(String str) {
        str = str.substring(0,10) + " " + str.substring(11,19);
        Date date = null;
        try {
            date = getInputFormat().parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String stringToString(String str) {
        return dateToString(stringToDate(str));
    }

}
