package com.haolaike.hotlikescan.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.base.BaseActivity;
import com.haolaike.hotlikescan.base.BasePresenter;
import com.haolaike.hotlikescan.beans.LocalItem;
import com.haolaike.hotlikescan.beans.PartsBean;
import com.haolaike.hotlikescan.data.PartsData;
import com.haolaike.hotlikescan.utils.DensityUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;

/**
 * Created by gallop on 2019/10/9.
 * Copyright holike possess 2019.
 * 未出库列表
 */
public class DisOutActivity extends BaseActivity {
    @BindView(R.id.rv_disout)
    SwipeMenuRecyclerView rv;

    private List<LocalItem> list;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void init() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setSwipeMenuCreator(mSwipeMenuCreator);
        rv.setSwipeMenuItemClickListener(mMenuItemClickListener);
        Map<String, List<PartsBean>> beanMap = PartsData.getInstance().getOutMap();
        Set<String> keys = beanMap.keySet();
        if (keys.size() > 0) {
            list = new ArrayList<>();
            for (String key : keys) {
                List<PartsBean> list = beanMap.get(key);
                if (list != null && !list.isEmpty() && !isScanAll(list)) {
                    PartsBean bean = list.get(0);
                    LocalItem item = new LocalItem();
                    item.bjbz = bean.getBjbz();
                    item.soOrderNo = bean.getSoOrderNo();
                    item.childOrderNo = bean.getChlidOrderNo();
                    item.targetBztm = bean.getTargetBztm();
                    if (!this.list.contains(item)) {
                        this.list.add(item);
                    }
                }
            }
            setList(this, rv, list);
        }
    }

    private boolean isScanAll(List<PartsBean> list) {
        for (PartsBean bean : list) {
            if (!bean.getIsScan()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示未入库列表
     */
    public void setList(final Context context, RecyclerView recyclerView, List<LocalItem> list) {
        recyclerView.setAdapter(new CommonAdapter<LocalItem>(context, R.layout.item_rv_disout, list) {
            @Override
            protected void convert(ViewHolder holder, final LocalItem item, int position) {//key=bean.getBjbz() + "+" + bean.getSoOrderNo() + "+" + bean.getChlidOrderNo();
                TextView tvSoOrderNo = holder.getView(R.id.tv_item_disout_soOrderNo);
                TextView tvBjbz = holder.getView(R.id.tv_item_disout_bjbz);
                TextView tvChildOrderNo = holder.getView(R.id.tv_item_chlidOrderNo);
                tvBjbz.setText(item.bjbz);
                tvSoOrderNo.setText(item.soOrderNo);
                tvChildOrderNo.setText(item.childOrderNo);
                holder.getConvertView().setOnClickListener(v -> {
                    Intent intent = new Intent();
                    intent.putExtra("key", item.targetBztm);
                    setResult(OutActivity.RESULTCODE_OUT, intent);
                    finish();
                });
            }
        });
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_dis_out;
    }

    /**
     * 侧滑删除菜单
     */
    final SwipeMenuCreator mSwipeMenuCreator = (leftMenu, rightMenu, viewType) -> {
        SwipeMenuItem deleteItem = new SwipeMenuItem(this)
                .setHeight(DensityUtil.dp2px(50))
                .setWidth(DensityUtil.dp2px(50))
                .setBackgroundColorResource(R.color.textColor5)
                .setTextColorResource(R.color.white)
                .setText("删除");
        rightMenu.addMenuItem(deleteItem);
    };

    /**
     * 侧滑删除
     */
    SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int position = menuBridge.getAdapterPosition();
            PartsData.getInstance().deleteOut(PartsData.getInstance().getOutListByKey(list.get(position).targetBztm));
            list.remove(position);
            rv.getAdapter().notifyItemRemoved(position);
            rv.getAdapter().notifyItemRangeChanged(position, list.size() - position);
        }
    };
}
