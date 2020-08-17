package com.zhang.library.utils;

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
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    /** 一天的毫秒数 */
    private static final int MILLISECOND_PER_DAY = 24 * 60 * 60 * 1000;

    private static DateFormat mFormat = SimpleDateFormat.getInstance();

    /**
     * 计算时间差
     *
     * @param time 时间
     */
    public static String calculateTimeDifference(String time) {

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        StringBuilder buffer = new StringBuilder();
        try {
            Date topic_post_time = sdf1.parse(time);
            Date currenTime = new Date(System.currentTimeMillis());

            long diff = Math.abs(currenTime.getTime() - topic_post_time.getTime());
            long days = diff / (1000 * 60 * 60 * 24);
            diff -= days * (1000 * 60 * 60 * 24);
            long hours = diff / (1000 * 60 * 60);
            diff -= hours * (1000 * 60 * 60);
            long minutes = diff / (1000 * 60);
            diff -= minutes * (1000 * 60);
            //            long seconds = diff / 1000;
            if (days >= 7) {
                buffer.replace(0, buffer.length(), sdf2.format(topic_post_time));
            } else {
                if (days > 0) {
                    buffer.append(days).append("天前");
                } else {
                    if (hours > 0) {
                        buffer.append(hours).append("小时前");
                    } else {
                        if (minutes > 0) {
                            buffer.append(minutes).append("分前");
                        } else {
                            buffer.append("刚刚");
                        }
                    }
                }
            }
            return buffer.toString();
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

    public static String getTimes(long time) {
        return new SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.getDefault()).format(new Date(time));
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
        long between_days = (time2 - time1) / MILLISECOND_PER_DAY;

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
