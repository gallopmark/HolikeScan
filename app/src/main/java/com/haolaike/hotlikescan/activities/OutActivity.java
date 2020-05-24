package com.haolaike.hotlikescan.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.base.BaseActivity;
import com.haolaike.hotlikescan.beans.LocalItem;
import com.haolaike.hotlikescan.beans.OutInfoBean;
import com.haolaike.hotlikescan.beans.PartsBean;
import com.haolaike.hotlikescan.data.PartsData;
import com.haolaike.hotlikescan.dialog.MyDatePickerDialog;
import com.haolaike.hotlikescan.http.LogCat;
import com.haolaike.hotlikescan.presenter.OutPresenter;
import com.haolaike.hotlikescan.utils.Constants;
import com.haolaike.hotlikescan.utils.DateUtils;
import com.haolaike.hotlikescan.utils.SharedPreferencesUtils;
import com.haolaike.hotlikescan.utils.TipDialogUtil;
import com.haolaike.hotlikescan.utils.ToastUtils;
import com.haolaike.hotlikescan.view.OutView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 出库
 */
public class OutActivity extends BaseActivity<OutPresenter, OutView> implements OutView {
    public final static int RESULTCODE_OUT = 10002;

    @BindView(R.id.tv_select_date)
    TextView mDateTextView;
    @BindView(R.id.rv_out)
    SwipeMenuRecyclerView rv;

    private List<LocalItem> list;
    private OutInfoBean outInfoBean;
    private boolean isLoading;

    private Date mCurrDate;  //进入页面时默认日期-当前系统日期
    private Date mSelectDate; //当前选择日期
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

    @Override
    protected void init() {
        mCurrDate = new Date();
        setDateTime(mCurrDate);
        outInfoBean = new Gson().fromJson(SharedPreferencesUtils.getString(Constants.OUTINFOBEAN), OutInfoBean.class);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setSwipeMenuCreator(mPresenter.getSwipeMenuCreator(this));
        rv.setSwipeMenuItemClickListener(mPresenter.getSwipeMenuItemClickListener());
        Map<String, List<PartsBean>> beanMap = PartsData.getInstance().getOutMap();
        Set<String> keys = beanMap.keySet();
        if (keys.size() > 0) {
            list = new ArrayList<>();
            for (String key : keys) {
                List<PartsBean> list = beanMap.get(key);
                if (list != null && !list.isEmpty() && isScanAll(list)) {
                    PartsBean bean = list.get(0);
                    LocalItem item = new LocalItem();
                    item.bjbz = bean.getBjbz();
                    item.soOrderNo = bean.getSoOrderNo();
                    item.childOrderNo = bean.getChlidOrderNo();
                    item.targetBztm = bean.getTargetBztm();
                    this.list.add(item);
                }
            }
            setList(this, rv, list);
        }
        mDateTextView.setOnClickListener(view -> onSelectDate());
        mReceiver = new DatetimeChangeReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(mReceiver, filter);
    }

    private boolean isScanAll(List<PartsBean> list) {
        for (PartsBean bean : list) {
            if (!bean.getIsScan()) {
                return false;
            }
        }
        return true;
    }

    private void setDateTime(Date date) {
        mSelectDate = date;
        mDateTextView.setText(DateUtils.formatDate(mSelectDate));
        mPresenter.setDateTime(mDateTextView.getText().toString());
    }

    /**
     * 默认是当天日期
     * 选择的话，不能大于当前月份，可以小于当前月份-1
     * 就是今天9月10，可以选择9月份任何一天，也可以选择8月的任何一天，但是不能选择7月/10月
     */
    private void onSelectDate() {
        new MyDatePickerDialog(this)
                .init(mSelectDate)
                .minDate(getMinDate())
                .maxDate(getMaxDate())
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

    private Date getMinDate() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        Calendar cMin = Calendar.getInstance();
        cMin.set(Calendar.YEAR, year);
        cMin.set(Calendar.MONTH, month - 1);
        cMin.set(Calendar.DAY_OF_MONTH, 1);
        return cMin.getTime();
    }

    private Date getMaxDate() {
        Calendar cMax = Calendar.getInstance();
        cMax.set(Calendar.DAY_OF_MONTH, getCurrentMonthLastDay());
        return cMax.getTime();
    }

    private int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_out;
    }

    @Override
    protected OutPresenter getPresenter() {
        return new OutPresenter();
    }


    @OnClick(R.id.btn_out_all)
    public void onViewClicked() {
        if (!isLoading) {
            showLoading("正在出库...");
            isLoading = true;
            mPresenter.out(PartsData.getInstance().getAllScanOutList(), outInfoBean);
        }
    }

    public void setList(final Context context, RecyclerView recyclerView, List<LocalItem> list) {
        recyclerView.setAdapter(new CommonAdapter<LocalItem>(context, R.layout.item_rv_out, list) {
            @Override
            protected void convert(ViewHolder holder, LocalItem item, int position) {//key=bean.getBjbz() + "+" + bean.getSoOrderNo() + "+" + bean.getChlidOrderNo();
                TextView tvSoOrderNo = holder.getView(R.id.tv_item_out_soOrderNo);
                TextView tvBjbz = holder.getView(R.id.tv_item_out_bjbz);
                TextView tvChlidOrderNo = holder.getView(R.id.tv_item_out_chlidOrderNo);
                tvBjbz.setText(item.bjbz);
                tvSoOrderNo.setText(item.soOrderNo);
                tvChlidOrderNo.setText(item.targetBztm);
                holder.getConvertView().setOnClickListener(view -> {
                    Intent intent = new Intent();
                    intent.putExtra("key", item.targetBztm);
                    setResult(RESULTCODE_OUT, intent);
                    finish();
                });
            }
        });
    }

    /**
     * 出库成功
     */
    @Override
    public void outSuccess() {
        dismissLoading();
        isLoading = false;
        ToastUtils.success(getString(R.string.out_out_success));
        PartsData.getInstance().deleteOut(PartsData.getInstance().getAllScanOutList());
        finish();
    }

    /**
     * 出库失败
     */
    @Override
    public void outFailed(String result) {
        dismissLoading();
        isLoading = false;
        TipDialogUtil.showConfirm(this, getString(R.string.out_out_failed) + "：" + result, "知道了");
    }

    /**
     * 删除出库列表子项
     */
    @Override
    public void delectItem(int position) {
        PartsData.getInstance().deleteOut(PartsData.getInstance().getOutListByKey(list.get(position).targetBztm));
        list.remove(position);
        rv.getAdapter().notifyItemRemoved(position);
        rv.getAdapter().notifyItemRangeChanged(position, list.size() - position);
    }

    @Override
    protected void onDestroy() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
        super.onDestroy();
    }
}
