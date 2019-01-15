package com.easydorm.easydorm.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static String stringToEasyString(String str) {
        return dateToEasyString(stringToDate(str));
    }


    public static String dateToEasyString(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Calendar now = Calendar.getInstance();
        long nowLong = now.getTimeInMillis()/60000;
        long dateLong = cal.getTimeInMillis()/60000;
        long diff = nowLong - dateLong;
        if(diff <= 1) {
            return "刚刚";
        } else if(diff <= 10) {
            return String.valueOf(diff)+"分钟前";
        }
        String str = "";
        if(cal.get(Calendar.YEAR) != now.get(Calendar.YEAR)) {
            str = "yyyy年 MM月dd日 ";
        } else {
            if(cal.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
                str = "今天 ";
            }
            cal.add(Calendar.DATE, 1);
            if(cal.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
                str = "昨天 ";
            }
            cal.add(Calendar.DATE, 1);
            if(cal.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
                str = "前天 ";
            } else if(str.equals("")) {
                str = "MM月dd日 ";
            }
            cal.add(Calendar.DATE, -2);
        }
        str += "HH:mm";
        return getSimpleDateFormat(str).format(date);
    }


}
