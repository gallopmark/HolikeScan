package com.haolaike.hotlikescan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.haolaike.hotlikescan.R;


/**
 * Created by wqj on 2017/11/2.
 * 通用dialog
 */

public abstract class BaseDialog extends Dialog {
    protected Context mContext;
    protected View mContentView;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        mContext = context;
        mContentView = LayoutInflater.from(context).inflate(setContentView(), null, false);
        setContentView(mContentView);
    }

    abstract int setContentView();

    protected boolean isFullScreen() {
        return false;
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        if (isFullScreen()) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            getWindow().getDecorView().setPadding(0, 0, 0, 0);
            getWindow().setAttributes(layoutParams);
        }
    }
}
