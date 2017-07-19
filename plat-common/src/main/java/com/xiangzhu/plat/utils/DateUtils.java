package com.xiangzhu.plat.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by liluoqi on 15/4/29.
 */
public class DateUtils {

    public static String formatDateToSecondsWithoutDecoration(Date date) {
        return date==null?EMPTY:new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }


    public static String formatDateToSeconds(Date date) {
        return date==null?EMPTY:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String formatDateToMilliSecondsWithoutDecoration(Date date) {
        return date==null?EMPTY:new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
    }

    public static String formatDateToString(Date date) {
        return date==null?EMPTY:new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String formatDateToMilliSeconds(Date date) {
        return date==null? EMPTY:new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(date);
    }

    public static Date formatUnixTimestampToDate(long unixTimeStamper) {
        return new Date(unixTimeStamper * 1000);
    }

    public static Date formatUnixTimestampToDate(String unixTimeStamper) {
        return new Date(Long.parseLong(unixTimeStamper) * 1000);
    }

    public static Date formatUnixTimestampToDate(int unixTimeStamper) {
        return new Date(((long) unixTimeStamper) * 1000);
    }

    public static Date convertToDateFromString(String dateString) throws ParseException {
        if (isBlank(dateString)) return null;
        return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
    }

    public static long intervalDays(Date before, Date after) {
        DateTime beforeTime = new DateTime(before);
        DateTime afterTime = new DateTime(after);
        long intervalMillis = 0;
        if (beforeTime.isBefore(afterTime)) {
            intervalMillis = afterTime.getMillis() - beforeTime.getMillis();
        } else {
            intervalMillis = beforeTime.getMillis() - afterTime.getMillis();
        }

        return intervalMillis / 86400000;
    }

    public static Date startOfDate(Date date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.parse(String.format("%s 00:00:00", formatDateToString(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Date endOfDate(Date date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.parse(String.format("%s 23:59:59", formatDateToString(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            System.out.println(formatUnixTimestampToDate(1473831607));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
