package com.haolaike.hotlikescan.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.beans.PartsBean;
import com.haolaike.hotlikescan.data.PartsData;
import com.haolaike.hotlikescan.presenter.InScanPresenter;
import com.haolaike.hotlikescan.utils.SoundPoolUtils;
import com.haolaike.hotlikescan.utils.TipDialogUtil;
import com.haolaike.hotlikescan.utils.ToastUtils;
import com.haolaike.hotlikescan.view.InScanView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 入库扫描页
 */
public class InScanActivity extends com.haolaike.hotlikescan.base.BaseActivity<InScanPresenter, InScanView> implements InScanView {

    @BindView(R.id.tv_inwarehose_sacnCode)
    EditText etSacnCode;
    @BindView(R.id.tv_inwarehose_scanNum)
    TextView tvScanNum;
    @BindView(R.id.rv_inwarehose)
    RecyclerView rv;
    private String code;
    private List<PartsBean> list;
    private boolean isLoading;

    @Override
    protected void init() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        rv.setLayoutManager(new LinearLayoutManager(this));
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
        if (!isLoading) {
            etSacnCode.setText(code);
            mPresenter.getScanData(code, list);
        }
    }

    @OnClick({R.id.btn_inwarehose_disInwarehose, R.id.btn_inwarehose_input})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_inwarehose_disInwarehose:
                list = null;//先清空列表，防止进入未入库列表中删除该列表
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

    /**
     * 显示扫描列表信息
     *
     * @param code
     * @param list
     */
    @Override
    public void setListData(String code, List<PartsBean> list) {
        if (!TextUtils.isEmpty(this.code) && code.equals(this.code)) {//重复扫描提示
            SoundPoolUtils.getInstance().play(2);
        }
        this.list = list;
        this.code = code;
        tvScanNum.setText(mPresenter.getScanedNum(list) + "/" + list.size());
        setList(this, rv, list);
    }

    @Override
    public void getCodeDataFailed(String failed) {
        ToastUtils.failed(failed);
    }

    @Override
    public void inWareSuccess() {
        ToastUtils.success(getString(R.string.inscan_in_success));
        list.clear();
        rv.getAdapter().notifyDataSetChanged();
        list = null;
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
     *
     * @param text
     */
    @Override
    public void loading(String text) {
        isLoading = true;
        showLoading(text);
    }

    /**
     * 加载完成/加载失败，未加载状态
     */
    @Override
    public void disLoading() {
        isLoading = false;
        dismissLoading();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == DisInActivity.RESULTCODE_DISIN) {//在未入库列表进来
            String key = data.getStringExtra("key");
            list = PartsData.getInstance().getInListByKey(key);
            etSacnCode.setText("");
            setListData("", list);
        }
    }

    /**
     * 显示扫描列表
     *
     * @param context
     * @param recyclerView
     * @param list
     */

    public void setList(final Context context, RecyclerView recyclerView, List<PartsBean> list) {
        recyclerView.setAdapter(new CommonAdapter<PartsBean>(context, R.layout.item_rv_inwarehose, list) {
            @Override
            protected void convert(ViewHolder holder, PartsBean bean, int position) {
                TextView tvBztm = (TextView) holder.getView(R.id.tv_item_inwarehouse_bztm);
                TextView tvOrderNo = (TextView) holder.getView(R.id.tv_item_inwarehouse_orderNo);
                TextView tvPackageNo = (TextView) holder.getView(R.id.tv_item_inwarehouse_packageNo);
                TextView tvSoOrderNo = (TextView) holder.getView(R.id.tv_item_inwarehouse_soOrderNo);
                TextView tvChlidOrderNo = (TextView) holder.getView(R.id.tv_item_inwarehouse_chlidOrderNo);
                tvBztm.setText(bean.getBztm());
                tvOrderNo.setText(bean.getOrderNo());
                tvPackageNo.setText(bean.getPackageNo() + "/" + list.size());
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
        recyclerView.scrollToPosition(mPresenter.getPosition(code, list));
    }
}
