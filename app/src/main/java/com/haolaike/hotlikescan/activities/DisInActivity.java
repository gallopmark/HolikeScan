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
 * 未入库列表
 */
public class DisInActivity extends BaseActivity {
    public final static int RESULTCODE_DISIN = 10001;

    @BindView(R.id.rv_disin)
    SwipeMenuRecyclerView rv;

    private List<LocalItem> list;

    @Override
    protected void init() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setSwipeMenuCreator(mSwipeMenuCreator);
        rv.setSwipeMenuItemClickListener(mMenuItemClickListener);
        Map<String, List<PartsBean>> mBeanMap = PartsData.getInstance().getInMap();
        Set<String> keys = mBeanMap.keySet();
        if (keys.size() > 0) {
            list = new ArrayList<>();
            for (String key : keys) {
                List<PartsBean> list = mBeanMap.get(key);
                if (list != null && !list.isEmpty()) {
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
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_dis_in;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    /**
     * 显示未入库列表
     */
    public void setList(final Context context, RecyclerView recyclerView, List<LocalItem> list) {
        recyclerView.setAdapter(new CommonAdapter<LocalItem>(context, R.layout.item_rv_disin, list) {
            @Override
            protected void convert(ViewHolder holder, final LocalItem item, int position) {//key=bean.getBjbz() + "+" + bean.getSoOrderNo() + "+" + bean.getChlidOrderNo();
                TextView tvSoOrderNo = holder.getView(R.id.tv_item_disin_soOrderNo);
                TextView tvBjbz = holder.getView(R.id.tv_item_disin_bjbz);
                TextView tvChildOrderNo = holder.getView(R.id.tv_item_chlidOrderNo);
                tvBjbz.setText(item.bjbz);
                tvSoOrderNo.setText(item.soOrderNo);
                tvChildOrderNo.setText(item.childOrderNo);
                holder.getConvertView().setOnClickListener(v -> {
                    Intent intent = new Intent();
                    intent.putExtra("key", item.targetBztm);
                    setResult(RESULTCODE_DISIN, intent);
                    finish();
                });
            }
        });
    }

    /**
     * 侧滑删除菜单
     */
    SwipeMenuCreator mSwipeMenuCreator = (leftMenu, rightMenu, viewType) -> {
        SwipeMenuItem deleteItem = new SwipeMenuItem(DisInActivity.this)
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
            PartsData.getInstance().deleteIn(PartsData.getInstance().getInListByKey(list.get(position).targetBztm));
            list.remove(position);
            rv.getAdapter().notifyItemRemoved(position);
            rv.getAdapter().notifyItemRangeChanged(position, list.size() - position);
        }
    };
}
