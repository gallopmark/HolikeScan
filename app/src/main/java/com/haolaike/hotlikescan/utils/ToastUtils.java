package com.haolaike.hotlikescan.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.haolaike.hotlikescan.MyApplication;
import com.haolaike.hotlikescan.R;

/**
 * Created by wqj on 2017/12/25.
 */

public class ToastUtils {
    /**
     * 之前显示的内容
     */
    private static String oldMsg;
    /**
     * Toast对象
     */
    private static Toast toast = null;
    /**
     * 第一次时间
     */
    private static long oneTime = 0;
    /**
     * 第二次时间
     */
    private static long twoTime = 0;

    /**
     * 显示Toast
     */
    public static void showToast(String message) {
        if (TextUtils.isEmpty(message)) return;
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getInstance(), message, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (TextUtils.equals(message, oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = message;
                toast.setText(message);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    public static void warn(String text) {
        SoundPoolUtils.getInstance().play(2);
        showToast(text);
    }

    public static void success(String text) {
        SoundPoolUtils.getInstance().play(1);
        showToast(text);
    }

    public static void failed(String text) {
        SoundPoolUtils.getInstance().play(2);
        showToast(text);
    }
}
