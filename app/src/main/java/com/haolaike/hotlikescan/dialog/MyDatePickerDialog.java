package com.haolaike.hotlikescan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.utils.DensityUtil;

import java.util.Calendar;
import java.util.Date;


public class MyDatePickerDialog extends Dialog {
    private OnDatePickerListener onDatePickerListener;
    private boolean mInitDate;
    private int year, month = -1, dayOfMonth;
    private Date minDate, maxDate;

    public MyDatePickerDialog(Context context) {
        super(context, R.style.Dialog);
    }

    public MyDatePickerDialog init(Date date) {
        if (date == null) {
            mInitDate = false;
            return this;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mInitDate = true;
        return this;
    }

    public MyDatePickerDialog minDate(Date minDate) {
        this.minDate = minDate;
        return this;
    }

    public MyDatePickerDialog maxDate(Date maxDate) {
        this.maxDate = maxDate;
        return this;
    }


    public MyDatePickerDialog setOnDatePickerListener(OnDatePickerListener onDatePickerListener) {
        this.onDatePickerListener = onDatePickerListener;
        return this;
    }

    private long getTimeInMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    @Override
    public void show() {
        if (getWindow() != null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_datepicker, new LinearLayout(getContext()), false);
            TextView mCancelTv = view.findViewById(R.id.mCancelTv);
            TextView mConfirmTv = view.findViewById(R.id.mConfirmTv);
            final DatePicker datePicker = view.findViewById(R.id.datePicker);
            if (minDate != null) {
                datePicker.setMinDate(getTimeInMillis(minDate));
            }
            if (maxDate != null) {
                datePicker.setMaxDate(getTimeInMillis(maxDate));
            }
            if (mInitDate) {
                datePicker.updateDate(year, month, dayOfMonth);
            }
            mCancelTv.setOnClickListener(view1 -> dismiss());
            mConfirmTv.setOnClickListener(view12 -> {
                if (onDatePickerListener != null) {
                    onDatePickerListener.onPicker(MyDatePickerDialog.this, datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                }
            });
            getWindow().setContentView(view);
            int width = (DensityUtil.getScreenWidth(getContext()) - DensityUtil.dp2px(getContext().getResources().getDimension(R.dimen.dp_30)));
            getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
        }
        super.show();
    }

    public interface OnDatePickerListener {
        void onPicker(MyDatePickerDialog dialog, int year, int month, int day);
    }
}
