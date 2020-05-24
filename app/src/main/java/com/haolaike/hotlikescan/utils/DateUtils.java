package com.haolaike.hotlikescan.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by gallop on 2019/9/10.
 * Copyright holike possess 2019.
 */
public class DateUtils {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd";

    public static String formatDate(Date date) {
        return getDateFormat().format(date);
    }

    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(DEFAULT_PATTERN, Locale.getDefault());
    }

    public static Date parseDate(String source) {
        try {
            return getDateFormat().parse(source);
        } catch (Exception e) {
            return new Date();
        }
    }

    //判断两个Date是否是同一天
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            return isSameDay(cal1, cal2);
        }
        return false;
    }

    private static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
}
