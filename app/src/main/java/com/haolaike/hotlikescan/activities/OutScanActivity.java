package com.haolaike.hotlikescan.activities;

import android.content.Context;
import android.content.Intent;
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
import com.haolaike.hotlikescan.dialog.SimpleDialog;
import com.haolaike.hotlikescan.presenter.InScanPresenter;
import com.haolaike.hotlikescan.presenter.OutScanPresenter;
import com.haolaike.hotlikescan.utils.SoundPoolUtils;
import com.haolaike.hotlikescan.utils.ToastUtils;
import com.haolaike.hotlikescan.view.OutScanView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 出库扫描
 */
public class OutScanActivity extends BaseActivity<OutScanPresenter, OutScanView> implements OutScanView {
    @BindView(R.id.tv_outwarehose_sacnCode)
    EditText etSacnCode;
    @BindView(R.id.rv_outwarehose)
    RecyclerView rv;
    @BindView(R.id.tv_inwarehose_scanNum)
    TextView tvScanNum;
    private String code;
    private List<PartsBean> mDataList;
    private boolean isFirstLoadCompleted;

    @Override
    protected void init() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        rv.setLayoutManager(new LinearLayoutManager(this));
//        code = getIntent().getStringExtra("code");
//        if (!TextUtils.isEmpty(code)) {
//            mBeans = PartsData.getInstance().getOutListByCode(code);
//            setListData("", mBeans);
//        }
        String key = getIntent().getStringExtra("key");
        if (!TextUtils.isEmpty(key)) {
            List<PartsBean> localBeans = PartsData.getInstance().getOutListByKey(key);
            if (localBeans != null && !localBeans.isEmpty()) {
                List<PartsBean> beans = new ArrayList<>(localBeans);
                etSacnCode.setText("");
                setListData("", beans);
            }
        }
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_out_scan;
    }

    @Override
    protected OutScanPresenter getPresenter() {
        return new OutScanPresenter();
    }

    @Override
    protected void scanData(String code) {
        if (!TextUtils.isEmpty(code)) {
            if (isFirstLoadCompleted && !isContainsCode(code) && !isScanAll()) {
                if (LocalSource.isHangup(this)) {     //打开了挂起提示
                    SoundPoolUtils.getInstance().play(2);
                    new SimpleDialog(this).setDate(getString(R.string.outscan_title),
                            getString(R.string.dialog_order_noscan_finish), getString(R.string.dialog_button_refuse),
                            getString(R.string.dialog_button_hangup)).setListener(new SimpleDialog.ClickListener() {
                        @Override
                        public void left() {

                        }

                        @Override
                        public void right() {
                            clear();
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

    private void getData(String code) {
        etSacnCode.setText(code);
        etSacnCode.setSelection(etSacnCode.getText().length());
        mPresenter.getScanData(code, mDataList);
    }

    private boolean isScanAll() {
        if (mDataList == null) return true;
        for (PartsBean bean : mDataList) {
            if (!bean.getIsScan()) {
                return false;
            }
        }
        return true;
    }

    /*列表中是否包含此code 匹配bztm字段*/
    private boolean isContainsCode(String code) {
        if (mDataList == null || mDataList.isEmpty()) return true;
        for (PartsBean bean : mDataList) {
            if (TextUtils.equals(bean.getBztm(), code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 显示出库扫描列表
     */
    @Override
    public void setListData(String code, List<PartsBean> list) {
        if (!TextUtils.isEmpty(this.code) && code.equals(this.code)) {//重复扫描提示
            SoundPoolUtils.getInstance().play(2);
        }
        this.mDataList = new ArrayList<>(list);
        this.code = code;
        String scanNum = mPresenter.getScanedNum(list) + "/" + list.size();
        tvScanNum.setText(scanNum);
        setList(this, rv, this.mDataList);
        if (!isFirstLoadCompleted) {
            isFirstLoadCompleted = true;
        }
    }

    /**
     * 获取二维码信息失败
     */
    @Override
    public void getCodeDataFailed(String failed) {
        ToastUtils.failed(failed);
    }

    /**
     * 加载中状态
     */
    @Override
    public void loading(String text) {
        showLoading(text);
    }

    /**
     * 未加载状态
     */
    @Override
    public void disLoading() {
        dismissLoading();
    }

    /**
     * 已经扫描过
     */
    @Override
    public void scaned() {
        clear();
        ToastUtils.warn(getString(R.string.outscan_in_list));
    }

    /**
     * 扫描不同部件
     */
    @Override
    public void difference() {
        ToastUtils.warn(getString(R.string.outscan_different));
    }

    /**
     * 扫描完成
     */
    @Override
    public void scanFinish() {
        clear();
//        this.mDataList = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == OutActivity.RESULTCODE_OUT && data != null) {//从出库页返回
            String key = data.getStringExtra("key");
            if (!TextUtils.isEmpty(key)) {
                List<PartsBean> localBeans = PartsData.getInstance().getOutListByKey(key);
                if (localBeans != null && !localBeans.isEmpty()) {
                    List<PartsBean> beans = new ArrayList<>(localBeans);
                    etSacnCode.setText("");
                    setListData("", beans);
                }
            }
        }
    }

    @OnClick({R.id.btn_outwarehose, R.id.btn_unwarehose, R.id.btn_skip, R.id.btn_outwarehose_input})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_outwarehose_input:
                if (etSacnCode.getText().toString().length() > 0) {
                    hideSoftInput(etSacnCode);
                    scanData(etSacnCode.getText().toString());
                }
                break;
            case R.id.btn_outwarehose:  //出库按钮
//                if (mBeans != null && !isScanAll()) {//列表不为空，并且扫描列表还没有，则插入
//                    ToastUtils.showToast(getString(R.string.outscan_unfinish_scan));
//                } else {
//                    startActivity(OutActivity.class);
//                }
                startActivity(OutActivity.class);
                break;
            case R.id.btn_unwarehose: //未出库列表
                startActivity(DisOutActivity.class);
                break;
            case R.id.btn_skip://清空扫描列表，设置列表为null
                etSacnCode.setText("");
                clear();
                break;
        }
    }

    private void clear(){
        if (mDataList != null) {
            mDataList.clear();
            if (rv.getAdapter() != null)
                rv.getAdapter().notifyDataSetChanged();
            mDataList = null;
            tvScanNum.setText("0/0");
        }
    }

    @Override
    public void onBackPressed() {
        new SimpleDialog(this).setDate(getString(R.string.outscan_dialog_exit), getString(R.string.outscan_dialog_exit_content),
                getString(R.string.dialog_cancel), getString(R.string.dialog_sure))
                .setListener(new SimpleDialog.ClickListener() {
                    @Override
                    public void left() {
                    }

                    @Override
                    public void right() {
                        finish();
                    }
                }).show();
    }

    /**
     * 显示出库扫描列表
     */
    public void setList(final Context context, RecyclerView recyclerView, List<PartsBean> list) {
        if (list == null || list.isEmpty()) return;
        final int mSize = list.size();
        recyclerView.setAdapter(new CommonAdapter<PartsBean>(context, R.layout.item_rv_outwarehose, list) {
            @Override
            protected void convert(ViewHolder holder, PartsBean bean, int position) {
                TextView tvBztm = holder.getView(R.id.tv_item_outwarehouse_bztm);
                TextView tvOrderNo = holder.getView(R.id.tv_item_outwarehouse_orderNo);
                TextView tvPackageNo = holder.getView(R.id.tv_item_outwarehouse_packageNo);
                TextView tvSoOrderNo = holder.getView(R.id.tv_item_outwarehouse_soOrderNo);
                TextView tvChlidOrderNo = holder.getView(R.id.tv_item_outwarehouse_chlidOrderNo);
                tvBztm.setText(bean.getBztm());
                tvOrderNo.setText(bean.getOrderNo());
                String packageNo = bean.getPackageNo() + "/" + mSize;
                tvPackageNo.setText(packageNo);
                tvSoOrderNo.setText(bean.getSoOrderNo());
                tvChlidOrderNo.setText(bean.getChlidOrderNo());
                if (bean.getIsScan()) {
                    tvBztm.setTextColor(ContextCompat.getColor(context, R.color.bg_tv_overpass_part4));
                    tvOrderNo.setTextColor(ContextCompat.getColor(context, R.color.bg_tv_overpass_part4));
                    tvPackageNo.setTextColor(ContextCompat.getColor(context, R.color.bg_tv_overpass_part4));
                    tvSoOrderNo.setTextColor(ContextCompat.getColor(context, R.color.bg_tv_overpass_part4));
                    tvChlidOrderNo.setTextColor(ContextCompat.getColor(context, R.color.bg_tv_overpass_part4));
                } else {
                    tvBztm.setTextColor(ContextCompat.getColor(context, R.color.textColor2));
                    tvOrderNo.setTextColor(ContextCompat.getColor(context, R.color.textColor2));
                    tvPackageNo.setTextColor(ContextCompat.getColor(context, R.color.textColor2));
                    tvSoOrderNo.setTextColor(ContextCompat.getColor(context, R.color.textColor2));
                    tvChlidOrderNo.setTextColor(ContextCompat.getColor(context, R.color.textColor2));
                }
            }
        });
        recyclerView.scrollToPosition(InScanPresenter.getPosition(code, list));
    }
}
