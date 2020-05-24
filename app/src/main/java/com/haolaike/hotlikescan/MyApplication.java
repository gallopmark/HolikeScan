package com.haolaike.hotlikescan;

import android.os.Build;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.haolaike.hotlikescan.data.LocalSource;
import com.haolaike.hotlikescan.data.PartsData;
import com.haolaike.hotlikescan.db.LogDaoUtils;
import com.haolaike.hotlikescan.exception.CrashHandler;
import com.haolaike.hotlikescan.utils.Constants;
import com.haolaike.hotlikescan.utils.SoundPoolUtils;

/**
 * Created by ZaakMan on 2017/7/25.
 */

public class MyApplication extends MultiDexApplication {
    private static MyApplication myApplication;

    public static MyApplication getInstance() {
        return myApplication;
    }

    static {
        /*
         * 兼容5.0以下系统
         */
        /*获取当前系统的android版本号*/
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //适配android5.0以下
            /*解决低版本手机vectorDrawable不支持儿闪退问题*/
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        PartsData.getInstance().init();
        initLog();
        SoundPoolUtils.getInstance().init(this);
        CrashHandler.getInstance().init();
    }

    private void initLog() {
        long old = LocalSource.getLastTime(this);
        if (old == 0) {
            LocalSource.saveTime(this);
        } else {
            long now = System.currentTimeMillis();
            if ((now - old) > Constants.CLEAR_TIME) {      //超过7天清除记录
                new Thread(() -> new LogDaoUtils().clear()).start();
                LocalSource.saveTime(this);       //记录上次清除的时间
            }
        }
    }
}
