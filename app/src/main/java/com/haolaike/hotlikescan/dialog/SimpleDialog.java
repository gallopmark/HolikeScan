package com.haolaike.hotlikescan.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.haolaike.hotlikescan.R;


/**
 * Created by wqj on 2017/12/6.
 * 简单的两个按钮dialog
 */

public class SimpleDialog extends BaseDialog {
    private TextView tvTitle;
    private TextView tvContent;
    private TextView btnLeft, btnRight;
    private ClickListener listener;

    public SimpleDialog(@NonNull Context context) {
        super(context);
        init();
    }

    @Override
    int setContentView() {
        return R.layout.dialog_simple;
    }

    private void init() {
        tvTitle = (TextView) mContentView.findViewById(R.id.tv_dialog_simple_title);
        tvContent = (TextView) mContentView.findViewById(R.id.tv_dialog_simple_content);
        btnLeft = (TextView) mContentView.findViewById(R.id.btn_dialog_simple_left);
        btnRight = (TextView) mContentView.findViewById(R.id.btn_dialog_simple_right);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.left();
                }
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.right();
                }
            }
        });
    }

    public SimpleDialog setDate(String title, String content, String left, String right) {
        tvTitle.setText(title);
        tvContent.setText(content);
        btnLeft.setText(left);
        btnRight.setText(right);
        return this;
    }

    public SimpleDialog setDate(int titleId, int contentId, int leftId, int rightId) {
        tvTitle.setText(mContext.getString(titleId));
        tvContent.setText(mContext.getString(contentId));
        btnLeft.setText(mContext.getString(leftId));
        btnRight.setText(mContext.getString(rightId));
        return this;
    }

    public SimpleDialog setContentGravity(int gravity) {
        tvContent.setGravity(gravity);
        return this;
    }

    public SimpleDialog setListener(ClickListener listener) {
        this.listener = listener;
        return this;
    }

    public interface ClickListener {
        void left();

        void right();
    }

}
