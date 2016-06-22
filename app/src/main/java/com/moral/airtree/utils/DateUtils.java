package com.moral.airtree.utils;

import android.net.ParseException;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bin.shen on 6/22/16.
 */
public class DateUtils {

//    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
//    public static final String FLOW_FORMAT = "yyyyMMddHHmmssSSS";
//    public static final String MIDDLE_FORMAT = "yyyy-MM-dd HH:mm:ss";
//    public static final long REFRESH_CACHE_TIME = 120000L;
//    public static final String SHORT_FORMAT = "yyyy-MM-dd";

    public static String date2Str(Date date, String format) {
        if(date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String timestamp2Str(Timestamp time) {
        Date date = null;
        if(time != null) {
            date = new Date(date.getTime());
        }
        return date2Str(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    public static Timestamp str2Timestamp(String str) {
        Date date = format(str, "yyyy-MM-dd HH:mm:ss.SSS");
        return new Timestamp(date, null);
    }

    public static String format(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(Date date, String pattern) {
        if(date == null) {
            return "null";
        }
        if((pattern == null) || (pattern.equals("")) || (pattern.equals("null"))) {
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date format(String date) {
        return format(date, null);
    }

    public static Date format(String date, String pattern) {
        if((pattern == null) || (pattern.equals("")) || (pattern.equals("null"))) {
        }
        if((date == null) || (date.equals("")) || (date.equals("null"))) {
            return new Date();
        }
        Date d = null;
        try {
            return d;
        } catch(ParseException pe) {
            pe.printStackTrace();
            return d;
        }
    }

    public static String getDefaultFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(new Date());
    }

    public static String getMiddleFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getShortFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
