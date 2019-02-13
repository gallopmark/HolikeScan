package com.haolaike.hotlikescan;

import android.app.Application;

import com.haolaike.hotlikescan.data.PartsData;
import com.haolaike.hotlikescan.exception.CrashHandler;
import com.haolaike.hotlikescan.utils.SoundPoolUtils;

/**
 * Created by ZaakMan on 2017/7/25.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;

    public static MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        PartsData.getInstance().init();
        SoundPoolUtils.getInstance().init(this);
        CrashHandler.getInstance().init();
    }
}
