package com.haolaike.hotlikescan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.haolaike.hotlikescan.R;

/**
 * Created by wqj on 2018/6/22.
 */

public class LoadingDialog extends Dialog {
    private View contnetView;
    private TextView tv;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        init(context);
    }

    private void init(Context context) {
        contnetView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        setContentView(contnetView);
        tv = (TextView) contnetView.findViewById(R.id.tv_dialog_loading);
        setCanceledOnTouchOutside(false);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void show(String text) {
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
        if (!isShowing()) {
            show();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        tv.setVisibility(View.GONE);
    }
}
