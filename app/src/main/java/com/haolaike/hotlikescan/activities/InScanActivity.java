package com.haolaike.hotlikescan.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.base.BaseActivity;
import com.haolaike.hotlikescan.beans.PartsBean;
import com.haolaike.hotlikescan.data.LocalSource;
import com.haolaike.hotlikescan.data.PartsData;
import com.haolaike.hotlikescan.dialog.MyDatePickerDialog;
import com.haolaike.hotlikescan.dialog.SimpleDialog;
import com.haolaike.hotlikescan.http.LogCat;
import com.haolaike.hotlikescan.presenter.InScanPresenter;
import com.haolaike.hotlikescan.utils.DateUtils;
import com.haolaike.hotlikescan.utils.SoundPoolUtils;
import com.haolaike.hotlikescan.utils.TipDialogUtil;
import com.haolaike.hotlikescan.utils.ToastUtils;
import com.haolaike.hotlikescan.view.InScanView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 入库扫描页
 */
public class InScanActivity extends BaseActivity<InScanPresenter, InScanView> implements InScanView {

    @BindView(R.id.mDateTextView)
    TextView mDateTextView;
    @BindView(R.id.tv_inwarehose_sacnCode)
    EditText etSacnCode;
    @BindView(R.id.tv_inwarehose_scanNum)
    TextView tvScanNum;
    @BindView(R.id.rv_inwarehose)
    RecyclerView rv;
    private String code;
    private List<PartsBean> list;
    private MyListAdapter adapter;
    private boolean isFirstLoadCompleted;

    private Date mCurrDate;  //进入页面时默认日期-当前系统日期
    private Date mSelectDate; //当前选择的日期
    private DatetimeChangeReceiver mReceiver;

    /*监听时间变化*/
    private class DatetimeChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), Intent.ACTION_TIME_TICK)) { //每一分钟更新时间
                LogCat.e("tick", "时间发生了变化");
                //如果日期不是当天日期，及过了当天的日期
                Date today = new Date();
                if (!DateUtils.isSameDay(mCurrDate, today) && today.after(mCurrDate)) {
                    setDateTime(new Date());
                }
            }
        }
    }

    private SimpleDateFormat getFormat() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    private String formatDate(Date date) {
        return getFormat().format(date);
    }

    @Override
    protected void init() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mCurrDate = new Date();
        setDateTime(mCurrDate);
        rv.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new MyListAdapter(this, R.layout.item_rv_inwarehose, list);
        rv.setAdapter(adapter);
        mReceiver = new DatetimeChangeReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);  //每一分钟更新时间
        registerReceiver(mReceiver, filter);
    }

    private void setDateTime(Date date) {
        mSelectDate = date;
        mDateTextView.setText(formatDate(mSelectDate));
        mPresenter.setDateTime(mDateTextView.getText().toString());
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_in_scan;
    }

    @Override
    protected InScanPresenter getPresenter() {
        return new InScanPresenter();
    }

    @Override
    protected void scanData(String code) {
        if (!TextUtils.isEmpty(code)) {
            if (isFirstLoadCompleted && !isContainsCode(code) && !isScanAll()) {
                if (LocalSource.isHangup(this)) {     //打开了挂起提示
                    SoundPoolUtils.getInstance().play(2);
                    new SimpleDialog(this).setDate(getString(R.string.inscan_title),
                            getString(R.string.dialog_order_noscan_finish), getString(R.string.dialog_button_refuse),
                            getString(R.string.dialog_button_hangup)).setListener(new SimpleDialog.ClickListener() {
                        @Override
                        public void left() {

                        }

                        @Override
                        public void right() {
                            getData(code);
                        }
                    }).show();
                } else {
                    getData(code);
                }
            } else {
                getData(code);
            }
        }
    }

    /*是否全部扫完*/
    private boolean isScanAll() {
        for (PartsBean bean : list) {
            if (!bean.getIsScan()) {
                return false;
            }
        }
        return true;
    }

    /*列表中是否包含此code 匹配bztm字段*/
    private boolean isContainsCode(String code) {
        if (list.isEmpty()) return true;
        for (PartsBean bean : list) {
            if (TextUtils.equals(bean.getBztm(), code)) {
                return true;
            }
        }
        return false;
    }

    private void getData(String code) {
        etSacnCode.setText(code);
        etSacnCode.setSelection(etSacnCode.getText().length());
        list.clear();
        adapter.notifyDataSetChanged();
        mPresenter.getScanData(code, list);
    }

    @OnClick({R.id.mDateTextView, R.id.btn_inwarehose_disInwarehose, R.id.btn_inwarehose_input})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mDateTextView:
                showDatePickerDialog();
                break;
            case R.id.btn_inwarehose_disInwarehose:
//                list.clear();//先清空列表，防止进入未入库列表中删除该列表
                startActivity(DisInActivity.class);
                break;
            case R.id.btn_inwarehose_input:
                if (etSacnCode.getText().toString().length() > 0) {
                    hideSoftInput(etSacnCode);
                    scanData(etSacnCode.getText().toString());
                }
                break;
        }
    }

    private void showDatePickerDialog() {
        new MyDatePickerDialog(this)
                .init(mSelectDate)
                .maxDate(new Date())
                .setOnDatePickerListener((dialog, year, month, day) -> {
                    dialog.dismiss();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day);
                    Date date = calendar.getTime();
                    setDateTime(date);
                }).show();
    }

    /**
     * 显示扫描列表信息
     */
    @Override
    public void setListData(String code, List<PartsBean> list) {
        if (!TextUtils.isEmpty(this.code) && code.equals(this.code)) {//重复扫描提示
            SoundPoolUtils.getInstance().play(2);
        }
        this.code = code;
        String scanNum = mPresenter.getScanedNum(list) + "/" + list.size();
        tvScanNum.setText(scanNum);
        this.list.clear();
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
        rv.scrollToPosition(getPosition(code, list));
//        setList(this, rv, list);
        if (!isFirstLoadCompleted) {
            isFirstLoadCompleted = true;
        }
    }

    private int getPosition(String code, List<PartsBean> list) {
        int position = 0;
        if (list != null && list.size() > 0) {
            for (int i = 0, size = list.size(); i < size; i++) {
                PartsBean bean = list.get(i);
                if (code.equals(bean.getBztm())) {
                    position = i;
                    break;
                }
            }
        }
        return position;
    }

    @Override
    public void getCodeDataFailed(String failed) {
        ToastUtils.failed(failed);
    }

    @Override
    public void inWareSuccess() {
        ToastUtils.success(getString(R.string.inscan_in_success));
        list.clear();
        adapter.notifyDataSetChanged();
        etSacnCode.setText("");
        tvScanNum.setText("");
    }

    @Override
    public void inWareFailed(String failed) {
        String faildMessage = getString(R.string.inscan_in_failed) + "\n" + failed;
        TipDialogUtil.showConfirm(this, faildMessage, "知道了");
    }


    /**
     * 加载中
     */
    @Override
    public void loading(String text) {
        showLoading(text);
    }

    /**
     * 加载完成/加载失败，未加载状态
     */
    @Override
    public void disLoading() {
        dismissLoading();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == DisInActivity.RESULTCODE_DISIN && data != null) {//在未入库列表进来
            String key = data.getStringExtra("key");
            List<PartsBean> list = PartsData.getInstance().getInListByKey(key);
            etSacnCode.setText("");
            if (list != null && !list.isEmpty())
                setListData("", list);
        }
    }

    private class MyListAdapter extends CommonAdapter<PartsBean> {

        MyListAdapter(Context context, int layoutId, List<PartsBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, PartsBean bean, int position) {
            TextView tvBztm = holder.getView(R.id.tv_item_inwarehouse_bztm);
            TextView tvOrderNo = holder.getView(R.id.tv_item_inwarehouse_orderNo);
            TextView tvPackageNo = holder.getView(R.id.tv_item_inwarehouse_packageNo);
            TextView tvSoOrderNo = holder.getView(R.id.tv_item_inwarehouse_soOrderNo);
            TextView tvChlidOrderNo = holder.getView(R.id.tv_item_inwarehouse_chlidOrderNo);
            tvBztm.setText(bean.getBztm());
            tvOrderNo.setText(bean.getOrderNo());
            String packageNo = bean.getPackageNo() + "/" + list.size();
            tvPackageNo.setText(packageNo);
            tvSoOrderNo.setText(bean.getSoOrderNo());
            tvChlidOrderNo.setText(bean.getChlidOrderNo());
            if (bean.getIsScan()) {
                tvBztm.setTextColor(ContextCompat.getColor(mContext, R.color.bg_tv_overpass_part4));
                tvOrderNo.setTextColor(ContextCompat.getColor(mContext, R.color.bg_tv_overpass_part4));
                tvPackageNo.setTextColor(ContextCompat.getColor(mContext, R.color.bg_tv_overpass_part4));
                tvSoOrderNo.setTextColor(ContextCompat.getColor(mContext, R.color.bg_tv_overpass_part4));
                tvChlidOrderNo.setTextColor(ContextCompat.getColor(mContext, R.color.bg_tv_overpass_part4));
            } else {
                tvBztm.setTextColor(ContextCompat.getColor(mContext, R.color.textColor2));
                tvOrderNo.setTextColor(ContextCompat.getColor(mContext, R.color.textColor2));
                tvPackageNo.setTextColor(ContextCompat.getColor(mContext, R.color.textColor2));
                tvSoOrderNo.setTextColor(ContextCompat.getColor(mContext, R.color.textColor2));
                tvChlidOrderNo.setTextColor(ContextCompat.getColor(mContext, R.color.textColor2));
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
        super.onDestroy();
    }
}
