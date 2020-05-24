package com.haolaike.hotlikescan.activities;

import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.EditText;

import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.base.BaseActivity;
import com.haolaike.hotlikescan.presenter.InScanPresenter2;
import com.haolaike.hotlikescan.utils.ToastUtils;
import com.haolaike.hotlikescan.view.InScanView2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 入库扫描页
 */
public class InScan2Activity extends BaseActivity<InScanPresenter2, InScanView2> implements InScanView2 {

    @BindView(R.id.tv_inwarehose_sacnCode)
    EditText etSacnCode;

    @Override
    protected void init() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_in_scan2;
    }

    @Override
    protected InScanPresenter2 getPresenter() {
        return new InScanPresenter2();
    }

    @OnClick(R.id.btn_inwarehose_input)
    public void onViewClicked() {
        if (etSacnCode.getText().toString().length() > 0) {
            hideSoftInput(etSacnCode);
            scanData(etSacnCode.getText().toString());
        }
    }

    @Override
    protected void scanData(String code) {
        if (!TextUtils.isEmpty(code)) {
            getCode(code);
        }
    }

    private void getCode(String code) {
        showLoading("");
        mPresenter.getCode(code);
    }

    @Override
    public void onScanSuccess(String message) {
        dismissLoading();
        ToastUtils.success(message);
    }

    @Override
    public void onScanFailure(String failReason) {
        dismissLoading();
        ToastUtils.success(failReason);
    }
}
