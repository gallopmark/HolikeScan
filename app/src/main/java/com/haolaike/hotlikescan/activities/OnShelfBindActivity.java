package com.haolaike.hotlikescan.activities;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.base.BaseActivity;
import com.haolaike.hotlikescan.presenter.OnShelfBindPresenter;
import com.haolaike.hotlikescan.utils.Constants;
import com.haolaike.hotlikescan.utils.SharedPreferencesUtils;
import com.haolaike.hotlikescan.utils.ToastUtils;
import com.haolaike.hotlikescan.view.OnShelfBindView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by pony on 2021/4/7.
 *
 * @Description 绑定货架
 */
public final class OnShelfBindActivity extends BaseActivity<OnShelfBindPresenter, OnShelfBindView> implements View.OnClickListener, OnShelfBindView {

    private TextView mShelfPosView;
    private List<String> mCodeList;
    private String mShelfPosition; //货架位

    @Override
    protected OnShelfBindPresenter getPresenter() {
        return new OnShelfBindPresenter();
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_onshelf_bind;
    }

    @Override
    protected void init() {
        mShelfPosView = findViewById(R.id.tv_shelf_position);
        setShelfPosText("");
        mCodeList = new ArrayList<>();
        ArrayList<String> codeList = getIntent().getStringArrayListExtra("data");
        if (codeList != null && !codeList.isEmpty()) {
            mCodeList.addAll(codeList);
        }
        ((TextView) findViewById(R.id.tv_total)).setText(String.format(getString(R.string.tip_total_packages), mCodeList.size()));
        ((TextView) findViewById(R.id.tv_listed_by)).setText(String.format(getString(R.string.tip_listed_by), SharedPreferencesUtils.getString(Constants.USER)));
        findViewById(R.id.tv_rescan).setOnClickListener(this);
        findViewById(R.id.tv_binding).setOnClickListener(this);
    }

    private void setShelfPosText(String text) {
        mShelfPosition = text;
        mShelfPosView.setText(String.format(getString(R.string.tip_shelf_position), text));
    }

    @Override
    protected void scanData(String code) {
        if (!TextUtils.isEmpty(code)) {
            setShelfPosText(code);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_rescan) {
            setShelfPosText("");
        } else if (v.getId() == R.id.tv_binding) {
            boundShelf();
        }
    }

    private void boundShelf() {
        if (TextUtils.isEmpty(mShelfPosition)) {
            ToastUtils.warn(getString(R.string.text_submit_error));
        } else {
            showLoading();
            mPresenter.bindShelf(mCodeList, mShelfPosition);
        }
    }

    @Override
    public void onSuccess(String message) {
        dismissLoading();
        ToastUtils.success(message);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onFailure(String errorMessage) {
        dismissLoading();
        ToastUtils.failed(errorMessage);
    }
}
