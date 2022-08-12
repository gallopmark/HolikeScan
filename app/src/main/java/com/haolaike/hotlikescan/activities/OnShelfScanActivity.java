package com.haolaike.hotlikescan.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.base.BaseActivity;
import com.haolaike.hotlikescan.beans.OnShelfDataBean;
import com.haolaike.hotlikescan.presenter.OnShelfScanPresenter;
import com.haolaike.hotlikescan.utils.ToastUtils;
import com.haolaike.hotlikescan.view.OnShelfScanView;

import java.util.ArrayList;


/**
 * Created by pony on 2021/4/6.
 * 上架扫描
 */
public class OnShelfScanActivity extends BaseActivity<OnShelfScanPresenter, OnShelfScanView> implements View.OnClickListener, OnShelfScanView {
    private EditText mCodeWidget;
    private TextView mCountView;
    private TextView mSelectView;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_onshelf_scan;
    }

    @Override
    protected OnShelfScanPresenter getPresenter() {
        return new OnShelfScanPresenter();
    }

    @Override
    protected void init() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mCodeWidget = findViewById(R.id.et_code);
        mCountView = findViewById(R.id.tv_total);
        RecyclerView recyclerView = findViewById(R.id.rv);
        mSelectView = findViewById(R.id.tv_select);
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        findViewById(R.id.tv_select).setOnClickListener(this);
        findViewById(R.id.tv_binding).setOnClickListener(this);
        mPresenter.setAdapter(recyclerView, mSelectView);
        setInitialState();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_confirm) {
            final String code = mCodeWidget.getText().toString();
            if (!TextUtils.isEmpty(code)) {
                hideSoftInput(mCodeWidget);
                scanData(code);
            }
        } else if (v.getId() == R.id.tv_select) {
            mPresenter.setSelectState(mSelectView);
        } else if (v.getId() == R.id.tv_binding) {
            final ArrayList<String> codeList = mPresenter.getSelectData();
            if (codeList.isEmpty()) {
                ToastUtils.showToast(getString(R.string.text_submit_error));
            } else {
                Intent intent = new Intent(this, OnShelfBindActivity.class);
                intent.putStringArrayListExtra("data", codeList);
                startActivityForResult(intent, 996);
            }
        }
    }

    @Override
    protected void scanData(String code) {
        if (!TextUtils.isEmpty(code)) {
            mCodeWidget.setText(code);
            mCodeWidget.setSelection(mCodeWidget.getText().length());
            if (mPresenter.isContain(code)) {
                ToastUtils.warn(getString(R.string.text_barcode_exists));
            } else {
                getCrmBmm(code);
            }
        }
    }

    private void getCrmBmm(String code) {
        showLoading();
        mPresenter.getOnShelfScanData(code);
    }

    @Override
    public void onSuccess(OnShelfDataBean bean) {
        dismissLoading();
        if (bean.isStateSuccess()) {
            mPresenter.setData(bean);
            setInitialState();
        } else {
            ToastUtils.warn(bean.message);
        }
    }

    private void setInitialState(){
        mPresenter.setCountText(mCountView);
        mPresenter.setSelectDrawableState(mSelectView);
    }

    @Override
    public void onFailure(String failReason) {
        dismissLoading();
        ToastUtils.failed(failReason);
    }

    @Override
    public void onItemRemoved() {
        mPresenter.setSelectDrawableState(mSelectView);
        mPresenter.setCountText(mCountView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 996 && resultCode == RESULT_OK) {  //上级页面绑定成功返回，清空数据
            mPresenter.clearData();
            setInitialState();
            mCodeWidget.setText("");
        }
    }
}
