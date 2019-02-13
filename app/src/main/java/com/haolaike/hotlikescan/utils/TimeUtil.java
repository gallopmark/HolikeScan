package com.haolaike.hotlikescan.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wqj on 2017/10/9.
 */

public class TimeUtil {
    /**
     * timestamp转string
     *
     * @param s
     * @param type"yyyy-MM-dd"
     * @return
     */
    public static String stampToStirng(String s, String type) {
        long lt = new Long(s);
        return stampToString(lt, type);
    }

    public static String stampToString(long s, String type) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }


    /**
     * string转timeStamp
     *
     * @param s
     * @param type "yyyy-MM-dd"
     * @return
     */
    public static long stringToStamp(String s, String type) {
        Date date = stringToDate(s, type);
        return date.getTime();
    }

    /**
     * string转date
     *
     * @param str
     * @param type
     * @return
     */
    public static Date stringToDate(String str, String type) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * date转string
     *
     * @param date
     * @param type
     * @return
     */
    public static String dateTostring(Date date, String type) {
        SimpleDateFormat format = new SimpleDateFormat(type);
        return format.format(date);
    }

    /**
     * String 转化Calendar
     *
     * @param str
     * @param pattern "yyyy-MM-dd"
     * @return
     */
    public static Calendar stringToCalendar(String str, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 时间戳转换成与现在相隔多久
     *
     * @param stamp 时间戳
     * @return
     */
    public static String stampToDays(String stamp) {
        if (TextUtils.isEmpty(stamp)) {
            return "";
        }
        String result = stampToStirng(stamp, "yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(result);
            Date now = new Date(System.currentTimeMillis());
            long nowTime = now.getTime();
            long lastTime = date.getTime();
            int daysOfTwo = daysOfTwo(date, now);
            nowTime = nowTime - lastTime;
            nowTime /= 1000 * 60;
            Calendar calNow = Calendar.getInstance();
            calNow.setTime(now);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            if (nowTime <= 1) {
                result = "刚刚";
            } else if (nowTime < 60 && nowTime > 1) {
                result = nowTime + "分钟前";
            } else if (nowTime >= 60 && nowTime < 60 * 24) {
                result = (nowTime / 60) + "小时前";
            } else if (daysOfTwo <= 30) {
                result = daysOfTwo + "天前";
            } else if (calNow.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
                result = (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
            } else {
                result = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);

            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 两个时间之间相隔的天数
     *
     * @param smdate
     * @param bdate
     * @return
     */
    public static int daysOfTwo(Date smdate, Date bdate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    //由出生日期获得年龄
    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }
}
