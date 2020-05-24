package com.haolaike.hotlikescan.data;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalSource {

    private static final String LOCAL_FILE = "localSource";
    private static final String KEY_TIME = "time";
    private static final String KEY_HANGUP = "hangup";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(LOCAL_FILE, Context.MODE_PRIVATE);
    }

    public static long getLastTime(Context context) {
        return getSharedPreferences(context).getLong(KEY_TIME, 0);
    }

    public static void saveTime(Context context) {
        getSharedPreferences(context).edit().putLong(KEY_TIME, System.currentTimeMillis()).apply();
    }

    public static void saveHangup(Context context, boolean isHangup) {
        getSharedPreferences(context).edit().putBoolean(KEY_HANGUP, isHangup).apply();
    }

    public static boolean isHangup(Context context) {
        return getSharedPreferences(context).getBoolean(KEY_HANGUP, true);
    }
}
