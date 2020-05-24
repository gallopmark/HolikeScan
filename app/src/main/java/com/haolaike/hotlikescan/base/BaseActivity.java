package com.haolaike.hotlikescan.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.haolaike.hotlikescan.BuildConfig;
import com.haolaike.hotlikescan.dialog.LoadingDialog;
import com.haolaike.hotlikescan.http.LogCat;
import com.haolaike.hotlikescan.utils.Constants;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Created by wqj on 2017/9/19.
 */

public abstract class BaseActivity<T extends BasePresenter, V extends BaseView> extends AppCompatActivity {
    protected CountDownTimer timer;
    protected T mPresenter;
    protected LoadingDialog loadingDialog;
    protected MyHandler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentViewId());
        ButterKnife.bind(this);
        mPresenter = getPresenter();
        handler = new MyHandler(this);
        setOrientation();
        if (mPresenter != null && (this instanceof BaseView)) {
            mPresenter.attach((V) this);
        }
        init();
    }

    protected abstract void init();

    protected abstract int setContentViewId();

    protected abstract T getPresenter();

    @Override
    protected void onResume() {
        super.onResume();
        register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegister();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (mPresenter != null) {
            mPresenter.deAttach();
            mPresenter = null;
        }
        handler.removeCallbacksAndMessages(null);
    }

    protected void startActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivityForResult(intent, 0);
    }

    /**
     * 设置屏幕方向
     */
    protected void setOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    protected void scanData(String code) {
        if (!TextUtils.isEmpty(code) && BuildConfig.DEBUG) {
            LogCat.e("scanData", code);
        }
    }

    /**
     * 注册扫描监听
     */
    protected void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.SCANACTION);
        registerReceiver(scanReceiver, intentFilter);
    }

    /**
     * 注销扫描监听
     */
    protected void unRegister() {
        unregisterReceiver(scanReceiver);
    }

    private BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), Constants.SCANACTION)) {
                String mCode = intent.getStringExtra(Constants.SCANNERDATA);
                handler.postDelayed(() -> scanData(mCode.trim()), 1);
            }
        }
    };

    static class MyHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public MyHandler(Activity activity) {
            mWeakReference = new WeakReference<>(activity);
        }
    }

    /**
     * 显示键盘
     *
     * @param view
     */
    protected void showSoftInput(View view) {
        if (view instanceof EditText) {
            ((EditText) view).setSelection(((EditText) view).getText().toString().length());
        }
        view.requestFocus();
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏键盘
     *
     * @param view
     */
    protected void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected void showLoading(String text) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show(text);
    }

    protected void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
