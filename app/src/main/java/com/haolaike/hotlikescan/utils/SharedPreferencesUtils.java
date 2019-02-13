package com.haolaike.hotlikescan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.haolaike.hotlikescan.MyApplication;

public class SharedPreferencesUtils {
    public static final String SP_NAME = "holikePreference";

    public static void saveInt(String key, int value) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        if (sp != null) {
            Editor editor = sp.edit();
            editor.putInt(key, value);
            editor.commit();
        }
    }

    public static void saveString(String key, String value) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        if (sp != null) {
            Editor editor = sp.edit();
            editor.putString(key, value);
            boolean success = editor.commit();
        }
    }

    public static void saveLong(String key, Long value) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        if (sp != null) {
            Editor editor = sp.edit();
            editor.putLong(key, value);
            editor.commit();
        }
    }

    public static void saveBoolean(String key, Boolean value) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        if (sp != null) {
            Editor editor = sp.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }
    }

    public static Boolean getBoolean(String key, Boolean defaultValue) {
        Boolean result = defaultValue;
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        if (sp != null) {
            result = sp.getBoolean(key, defaultValue);
        }
        return result;
    }

    public static String getString(String key, String defaultValue) {
        String result = defaultValue;
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        if (sp != null) {
            result = sp.getString(key, defaultValue);
        }
        return result;
    }

    public static int getInt(String key, int defaultValue) {
        int result = defaultValue;
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        if (sp != null) {
            result = sp.getInt(key, defaultValue);
        }
        return result;
    }

    public static long getLong(String key, long defaultValue) {
        long result = defaultValue;
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        if (sp != null) {
            result = sp.getLong(key, defaultValue);
        }
        return result;
    }

    public static long getLong(String key) {
        return getLong(key, 0);
    }

    public static String getString(String key) {
        return getString(key, null);
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }
}
