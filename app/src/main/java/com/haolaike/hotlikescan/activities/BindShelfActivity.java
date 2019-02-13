package com.haolaike.hotlikescan.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.dialog.SimpleDialog;
import com.haolaike.hotlikescan.presenter.BindShelfPresenter;
import com.haolaike.hotlikescan.utils.ToastUtils;
import com.haolaike.hotlikescan.view.BindShelfView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 指定货架
 */
public class BindShelfActivity extends com.haolaike.hotlikescan.base.BaseActivity<BindShelfPresenter, BindShelfView> implements BindShelfView {


    @BindView(R.id.tv_bind_shelf_component)
    TextView tvComponent;
    @BindView(R.id.rv_bind_shelf)
    SwipeMenuRecyclerView rv;
    @BindView(R.id.cb_bind_shelf_auto)
    CheckBox cbAuto;

    private List<String> list = new ArrayList<>();

    @Override
    protected void init() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setSwipeMenuCreator(mPresenter.getSwipeMenuCreator(this));
        rv.setSwipeMenuItemClickListener(mPresenter.getSwipeMenuItemClickListener());
        list.add("");
        setList(this, rv, list);
    }

    @Override
    protected void scanData(String code) {
        if (TextUtils.isEmpty(tvComponent.getText().toString())) {//未扫描部件码
            tvComponent.setText(code);
        } else {//已描部件码
            if (list.size() > 0) {

                for (int i = 0, size = list.size(); i < size; i++) {
                    String text = list.get(i);
                    if (TextUtils.isEmpty(text)) {
                        list.set(i, code);
                        rv.getAdapter().notifyDataSetChanged();
                        break;
                    }
                }
                if (mPresenter.isScanAll(list)) {//是否全部扫描
                    if (cbAuto.isChecked()) {//自动提交
                        mPresenter.countdown(4);
                    } else {
                        new SimpleDialog(this).setDate(getString(R.string.bind_shelf_title), getString(R.string.bind_shelf_dialog_contnet),
                                getString(R.string.dialog_cancel), getString(R.string.dialog_sure))
                                .setListener(new SimpleDialog.ClickListener() {
                                    @Override
                                    public void left() {
                                    }

                                    @Override
                                    public void right() {
                                        mPresenter.bindShelf(tvComponent.getText().toString(), list);
                                    }
                                }).show();
                    }
                }
            } else {
                ToastUtils.showToast(getString(R.string.bind_shelf_toast_add_shelf));
            }
        }
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_bind_shelf;
    }

    @Override
    protected BindShelfPresenter getPresenter() {
        return new BindShelfPresenter();
    }

    @Override
    public void loading(String text) {
        showLoading(text);
    }

    @Override
    public void BindShelSuccess(String result) {
        dismissLoading();
        ToastUtils.success(getString(R.string.bind_shelf_bind_success));
    }

    @Override
    public void BindShelFailed(String failed) {
        dismissLoading();
        ToastUtils.failed(getString(R.string.bind_shelf_bind_failed) +"："+ failed);
    }

    /**
     * 倒计时完成
     */
    @Override
    public void countdownFinish() {
        if (cbAuto.isChecked()) {
            mPresenter.bindShelf(tvComponent.getText().toString(), list);
        }
    }

    @Override
    public void delectItem(int position) {
        list.remove(position);
        rv.getAdapter().notifyItemRemoved(position);
        rv.getAdapter().notifyItemRangeChanged(position, list.size() - position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_bind_shelf_add, R.id.btn_bind_shelf_rescan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bind_shelf_add:
                list.add("");
                rv.getAdapter().notifyDataSetChanged();
                break;
            case R.id.btn_bind_shelf_rescan:
                tvComponent.setText("");
                list.clear();
                list.add("");
                rv.getAdapter().notifyDataSetChanged();
                break;
        }
    }

    /**
     * 显示货架列表
     *
     * @param context
     * @param recyclerView
     * @param list
     */
    public void setList(final Context context, RecyclerView recyclerView, List<String> list) {
        recyclerView.setAdapter(new CommonAdapter<String>(context, R.layout.item_rv_bind_shelf, list) {
            @Override
            protected void convert(ViewHolder holder, String text, int position) {
                TextView textView = (TextView) holder.getView(R.id.tv_item_rv_bind_shelf);
                textView.setText(text);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.cancelCountdown();
        }
    }
}
