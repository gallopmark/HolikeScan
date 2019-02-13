package com.haolaike.hotlikescan.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.beans.OutInfoBean;
import com.haolaike.hotlikescan.data.PartsData;
import com.haolaike.hotlikescan.presenter.OutPresenter;
import com.haolaike.hotlikescan.utils.Constants;
import com.haolaike.hotlikescan.utils.SharedPreferencesUtils;
import com.haolaike.hotlikescan.utils.TipDialogUtil;
import com.haolaike.hotlikescan.utils.ToastUtils;
import com.haolaike.hotlikescan.view.OutView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 出库
 */
public class OutActivity extends com.haolaike.hotlikescan.base.BaseActivity<OutPresenter, OutView> implements OutView {
    public final static int RESULTCODE_OUT = 10002;

    @BindView(R.id.rv_out)
    SwipeMenuRecyclerView rv;

    private List<String> list;
    private OutInfoBean outInfoBean;
    private boolean isLoading;

    @Override
    protected void init() {
        outInfoBean = new Gson().fromJson(SharedPreferencesUtils.getString(Constants.OUTINFOBEAN), OutInfoBean.class);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setSwipeMenuCreator(mPresenter.getSwipeMenuCreator(this));
        rv.setSwipeMenuItemClickListener(mPresenter.getSwipeMenuItemClickListener());
        Set<String> keys = PartsData.getInstance().getOutMap().keySet();
        if (keys.size() > 0) {
            list = new ArrayList<>();
            for (String key : keys) {
                list.add(key);
            }
            setList(this, rv, list);
        }
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
            mPresenter.out(PartsData.getInstance().getAllOutList(), outInfoBean);
        }
    }

    public void setList(final Context context, RecyclerView recyclerView, List<String> list) {
        recyclerView.setAdapter(new CommonAdapter<String>(context, R.layout.item_rv_out, list) {
            @Override
            protected void convert(ViewHolder holder, String key, int position) {//key=bean.getBjbz() + "+" + bean.getSoOrderNo() + "+" + bean.getChlidOrderNo();
                String[] keys = key.split("\\+");
                TextView tvSoOrderNo = holder.getView(R.id.tv_item_out_soOrderNo);
                TextView tvBjbz = holder.getView(R.id.tv_item_out_bjbz);
                TextView tvChlidOrderNo = holder.getView(R.id.tv_item_out_chlidOrderNo);
                tvBjbz.setText(keys.length > 0 ? keys[0] : "");
                tvSoOrderNo.setText(keys.length > 1 ? keys[1] : "");
                tvChlidOrderNo.setText(keys.length > 2 ? (keys[2].equals("null") ? "" : keys[2]) : "");
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
        PartsData.getInstance().deleteOut(PartsData.getInstance().getAllOutList());
        finish();
    }

    /**
     * 出库失败
     *
     * @param result
     */
    @Override
    public void outFailed(String result) {
        dismissLoading();
        isLoading = false;
        TipDialogUtil.showConfirm(this, getString(R.string.out_out_failed) + "：" + result, "知道了");
    }

    /**
     * 删除出库列表子项
     *
     * @param position
     */
    @Override
    public void delectItem(int position) {
        PartsData.getInstance().deleteOut(PartsData.getInstance().getOutListByKey(list.get(position)));
        list.remove(position);
        rv.getAdapter().notifyItemRemoved(position);
        rv.getAdapter().notifyItemRangeChanged(position, list.size() - position);
    }
}
