package com.zhang.library.utils;

import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具
 */
public class TimeUtils {

    private static final String TAG = TimeUtils.class.getSimpleName();

    public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_DEFAULT_LONG = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YYYY_MM_DD_SEPARATOR = "yyyy/MM/dd";
    public static final String DATE_FORMAT_YYYY_MM_DD_CN = "yyyy年MM月dd日";
    public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";
    public static final String DATE_FORMAT_YYYY_MM_SEPARATOR = "yyyy/MM";
    public static final String DATE_FORMAT_YYYY_MM_CN = "yyyy年MM月";
    public static final String DATE_FORMAT_MM_DD = "MM-dd";
    public static final String DATE_FORMAT_MM_DD_SEPARATOR = "MM/dd";
    public static final String DATE_FORMAT_MM_DD_CN = "MM月dd日";
    public static final String DATE_FORMAT_HH_MM_SS = "HH:mm:ss";

    /** 秒（以毫秒为单位） */
    public static final long SECOND_IN_MILLIS = DateUtils.SECOND_IN_MILLIS;
    /** 分钟（以毫秒为单位） */
    public static final long MINUTE_IN_MILLIS = SECOND_IN_MILLIS * 60;
    /** 小时（毫秒） */
    public static final long HOUR_IN_MILLIS = MINUTE_IN_MILLIS * 60;
    /** 天（以毫秒为单位） */
    public static final long DAY_IN_MILLIS = HOUR_IN_MILLIS * 24;
    /** 周（以毫秒为单位） */
    public static final long WEEK_IN_MILLIS = DAY_IN_MILLIS * 7;
    /** 月份（单位：毫） 此处的月用30天暂代 */
    public static final long MONTH_IN_MILLIS = DAY_IN_MILLIS * 30;
    /** 365天的长度 */
    public static final long YEAR_IN_MILLIS = DAY_IN_MILLIS * 365;

    private static final DateFormat mFormat;

    static {
        mFormat = SimpleDateFormat.getInstance();
    }

    /**
     * 计算时间差
     *
     * @param targetTime 时间
     */
    public static String calculateTimeDifference(String targetTime) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            Date targetDate = null;
            try {
                targetDate = sdf1.parse(targetTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (targetDate == null)
                targetDate = new Date();

            Date currentDate = new Date(System.currentTimeMillis());

            long diff = Math.abs(currentDate.getTime() - targetDate.getTime()); //时间差，单位：毫秒
            long days = diff / (1000 * 60 * 60 * 24);   //计算时间差的天数
            diff -= days * (1000 * 60 * 60 * 24);       //扣除天数后剩余的时间
            long hours = diff / (1000 * 60 * 60);       //计算时间差的小时数
            diff -= hours * (1000 * 60 * 60);           //扣除小时数后的时间
            long minutes = diff / (1000 * 60);          //计算时间差的分钟数
            diff -= minutes * (1000 * 60);              //扣除分钟数后的时间
            //            long seconds = diff / 1000;
            if (days >= 7) {
                builder.replace(0, builder.length(), sdf2.format(targetDate));
            } else {
                if (days > 0) {
                    builder.append(days).append("天前");
                } else {
                    if (hours > 0) {
                        builder.append(hours).append("小时前");
                    } else {
                        if (minutes > 0) {
                            builder.append(minutes).append("分前");
                        } else {
                            builder.append("刚刚");
                        }
                    }
                }
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得年月日格式的时间
     *
     * @param time 时间
     *
     * @return 例：2016-10-01
     */
    public static String getTime(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD, Locale.getDefault());
            Date date = format.parse(time);
            if (date == null) {
                throw new NullPointerException("Parse time failed! Please check the time format!");
            }
            return format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得年月日时分秒格式的时间
     *
     * @param time 例：2016-10-01 10:00:00
     */
    public static String getTimes(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.getDefault());
            Date date = format.parse(time);
            if (date == null) {
                throw new NullPointerException("Parse time failed! Please check the time format!");
            }
            return format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取时间格式，时间格式为{@link #DATE_FORMAT_DEFAULT}
     *
     * @param timestamp 时间戳，毫秒为单位
     */
    public static String getTimes(long timestamp) {
        return getTimes(DATE_FORMAT_DEFAULT, timestamp);
    }

    /**
     * 获取时间格式
     *
     * @param format    时间格式
     * @param timestamp 时间戳，毫秒为单位
     */
    public static String getTimes(String format, long timestamp) {
        return getTimes(format, Locale.getDefault(), timestamp);
    }

    /**
     * 获取时间格式
     *
     * @param format    时间格式
     * @param locale    地区
     * @param timestamp 时间戳，毫秒为单位
     */
    public static String getTimes(String format, Locale locale, long timestamp) {
        return new SimpleDateFormat(format, locale).format(new Date(timestamp));
    }

    public static int getDaysBetween(String smdate, String bdate) throws ParseException {
        return getDaysBetween(new Date(Long.parseLong(smdate)), new Date(Long.parseLong(bdate)));
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smDate  较小的时间
     * @param bigDate 较大的时间
     *
     * @return 相差天数
     */
    public static int getDaysBetween(Date smDate, Date bigDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD, Locale.getDefault());

        smDate = format.parse(format.format(smDate));
        if (smDate == null) {
            throw new NullPointerException("Parse smDate failed! Please check the time format!");
        }

        bigDate = format.parse(format.format(bigDate));
        if (bigDate == null) {
            throw new NullPointerException("Parse bigDate failed! Please check the time format!");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(smDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bigDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / DAY_IN_MILLIS;

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获得时间指定天数前后的时间
     *
     * @param originalTime 原始时间
     * @param intervalDay  间隔天数
     */
    public static String getDays(long originalTime, int intervalDay) {
        return getDays(new Date(originalTime), intervalDay);
    }

    /**
     * 获得时间指定天数前后的时间
     *
     * @param originalTime 原始时间
     * @param intervalDay  间隔天数
     */
    public static String getDays(String originalTime, int intervalDay) {
        return getDays(new Date(Long.parseLong(originalTime)), intervalDay);
    }

    /**
     * 获得时间指定天数前后的时间
     *
     * @param originalDate 原始时间
     * @param intervalDay  间隔天数
     */
    public static String getDays(Date originalDate, int intervalDay) {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD, Locale.getDefault());
            Date date = format.parse(format.format(originalDate));
            if (date == null) {
                throw new NullPointerException("Parse orginalDate time failed! Please check the time format!");
            }
            calendar.setTime(date);
            LogUtils.info(TAG, "originalDate:%d-%d-%d %d:%d:%d", (calendar.getTime().getYear() + 1900), (calendar.getTime().getMonth() + 1), calendar.getTime().getDate(), calendar.getTime().getHours(),
                    calendar.getTime().getMinutes(), calendar.getTime().getSeconds());
            calendar.add(Calendar.DATE, intervalDay);
            LogUtils.info(TAG, "originalDate:%d-%d-%d %d:%d:%d", (calendar.getTime().getYear() + 1900), (calendar.getTime().getMonth() + 1), calendar.getTime().getDate(), calendar.getTime().getHours(),
                    calendar.getTime().getMinutes(), calendar.getTime().getSeconds());
            return String.valueOf(calendar.getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
