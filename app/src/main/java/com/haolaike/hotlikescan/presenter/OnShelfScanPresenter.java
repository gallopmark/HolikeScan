package com.haolaike.hotlikescan.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.haolaike.hotlikescan.R;
import com.haolaike.hotlikescan.adapter.SimpleAdapter;
import com.haolaike.hotlikescan.adapter.SimpleViewHolder;
import com.haolaike.hotlikescan.base.BasePresenter;
import com.haolaike.hotlikescan.beans.OnShelfDataBean;
import com.haolaike.hotlikescan.dialog.SimpleDialog;
import com.haolaike.hotlikescan.http.RequestCallBack;
import com.haolaike.hotlikescan.model.OnShelfScanModel;
import com.haolaike.hotlikescan.view.OnShelfScanView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pony on 2021/4/6.
 */
public final class OnShelfScanPresenter extends BasePresenter<OnShelfScanView, OnShelfScanModel> {

    private final List<OnShelfDataBean> mData = new ArrayList<>();
    private SubAdapter mAdapter;

    @Override
    protected OnShelfScanModel getModel() {
        return new OnShelfScanModel();
    }

    public void getOnShelfScanData(String code) {
        model.getCrmZmm(code, new RequestCallBack<OnShelfDataBean>() {
            @Override
            public void onFailed(String result) {
                if (getView() != null)
                    getView().onFailure(result);
            }

            @Override
            public void onSuccess(OnShelfDataBean result) {
                if (getView() != null)
                    getView().onSuccess(result);
            }
        });
    }

    public void setAdapter(RecyclerView rv, final TextView selectView) {
        final Context context = rv.getContext();
        mAdapter = new SubAdapter(context, mData, new SubAdapter.OnItemOperateCallback() {
            @Override
            public void onSelectChange() {
                setSelectDrawableState(selectView);
            }

            @Override
            public void onItemRemoved() {
                if (getView() != null)
                    getView().onItemRemoved();
            }
        });
        rv.setAdapter(mAdapter);
    }

    //判断扫描的包装条码是否已存在
    public boolean isContain(String code) {
        return mAdapter.isContain(code);
    }

    public void setData(OnShelfDataBean o) {
        if (!mData.contains(o)) {
            mData.add(o);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setCountText(TextView tv) {
        tv.setText(String.format(tv.getContext().getString(R.string.tip_total_packages), mAdapter.getTotalSize()));
    }

    public void setSelectState(TextView tv) {
        if (mAdapter.isSelectAll()) {
            mAdapter.clearSelect();
            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.layer_uncheck, 0, 0, 0);
        } else {
            mAdapter.selectAll();
            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.layer_checked, 0, 0, 0);
        }
    }

    public void setSelectDrawableState(TextView tv) {
        if (mAdapter.isSelectAll()) {
            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.layer_checked, 0, 0, 0);
        } else {
            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.layer_uncheck, 0, 0, 0);
        }
    }

    public ArrayList<String> getSelectData() {
        final ArrayList<String> codeList = new ArrayList<>();
        final List<OnShelfDataBean> data = mAdapter.getSelectData();
        for (OnShelfDataBean o : data) {
            for (OnShelfDataBean.InnerBean i : o.getDataList()) {
                codeList.add(i.bztm);
            }
        }
        return codeList;
    }

    public void clearData() {
        mAdapter.clear();
    }

    private static final class SubAdapter extends SimpleAdapter<OnShelfDataBean> {
        OnItemOperateCallback callback;
        static final int MAX_SHOW_SIZE = 50; //最大显示50条包装码

        SubAdapter(Context context, List<OnShelfDataBean> data, OnItemOperateCallback callback) {
            super(context, data);
            this.callback = callback;
        }

        @Override
        public int getItemViewType(int position) {
            return mData.get(position).getItemType();
        }

        boolean isContain(String code) {
            for (OnShelfDataBean o : mData) {
                if (TextUtils.equals(code, o.bztm)) {
                    return true;
                } else {
                    for (OnShelfDataBean.InnerBean i : o.getDataList()) {
                        if (TextUtils.equals(code, i.bztm)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        boolean isSelectAll() {
            if (mData.isEmpty()) {
                return false;
            }
            for (OnShelfDataBean o : mData) {
                if (!o.isSelect) {
                    return false;
                }
            }
            return true;
        }

        void selectAll() {
            for (OnShelfDataBean o : mData) {
                o.isSelect = true;
            }
            notifyDataSetChanged();
        }

        void clearSelect() {
            for (OnShelfDataBean o : mData) {
                o.isSelect = false;
            }
            notifyDataSetChanged();
        }

        void remove(int position) {
            mData.remove(position);
            notifyItemRemoved(position);
        }

        int getTotalSize() {
            int count = 0;
            for (OnShelfDataBean o : mData) {
                for (OnShelfDataBean.InnerBean i : o.getDataList()) {
                    count++;
                }
            }
            return count;
        }

        List<OnShelfDataBean> getSelectData() {
            final List<OnShelfDataBean> data = new ArrayList<>();
            for (OnShelfDataBean o : mData) {
                if (o.isSelect) {
                    data.add(o);
                }
            }
            return data;
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            if (viewType == 0) {
                return R.layout.item_onshelf_scan;
            }
            return R.layout.item_onshelf_scan_more;
        }

        @Override
        protected void convert(final SimpleViewHolder holder, final OnShelfDataBean o, int position) {
            int itemType = holder.getItemViewType();
            holder.setText(R.id.tv_logo, o.getLogo());
            if (itemType != 0) {
                holder.setText(R.id.tv_order_number, o.getCRMOrderNumber());
                holder.setText(R.id.tv_child_order_number, o.getChildOrderNumber());
                holder.setText(R.id.tv_sales_order_number, o.getSalesOrderNumber());
                RecyclerView rv = holder.itemView.findViewById(R.id.rv);
                rv.setNestedScrollingEnabled(false);
                String hiddenText = null;
                final List<OnShelfDataBean.InnerBean> subList = new ArrayList<>();
                final List<OnShelfDataBean.InnerBean> originList = o.getDataList();
                if (originList.size() > MAX_SHOW_SIZE) {
                    subList.addAll(originList.subList(0, MAX_SHOW_SIZE));
                    hiddenText = String.format(mContext.getString(R.string.tip_hide_data), (originList.size() - MAX_SHOW_SIZE));
                } else {
                    subList.addAll(originList);
                }
                rv.setAdapter(new PackageAdapter(mContext, subList, new PackageAdapter.OnItemClickCallback() {
                    @Override
                    public void onItemClick() {
                        toggle(holder, o);
                    }

                    @Override
                    public void onItemLongClick() {
                        showTipDialog(position);
                    }
                }));
                holder.setText(R.id.tv_hidden, hiddenText);
                holder.setVisibility(R.id.tv_hidden, TextUtils.isEmpty(hiddenText) ? View.GONE : View.VISIBLE);
            } else {
                holder.setText(R.id.tv_packing_barcode, o.bztm);
            }
            holder.setImageResource(R.id.iv_select, o.isSelect ? R.drawable.layer_checked : R.drawable.layer_uncheck);
            holder.itemView.setOnClickListener(view -> toggle(holder, o));
            holder.itemView.setOnLongClickListener(view -> {
                showTipDialog(position);
                return false;
            });
        }

        private void toggle(SimpleViewHolder holder, final OnShelfDataBean o) {
            o.isSelect = !o.isSelect;
            holder.setImageResource(R.id.iv_select, o.isSelect ? R.drawable.layer_checked : R.drawable.layer_uncheck);
            callback.onSelectChange();
        }

        private void showTipDialog(final int position) {
            new SimpleDialog(mContext).setDate(mContext.getString(R.string.text_tips),
                    mContext.getString(R.string.text_onshelf_scan_message), mContext.getString(R.string.dialog_cancel),
                    mContext.getString(R.string.dialog_sure)).setListener(new SimpleDialog.ClickListener() {
                @Override
                public void left() {

                }

                @Override
                public void right() {
                    remove(position);
                    callback.onItemRemoved();
                }
            }).show();
        }

        private static class PackageAdapter extends SimpleAdapter<OnShelfDataBean.InnerBean> {
            OnItemClickCallback callback;

            PackageAdapter(Context context, List<OnShelfDataBean.InnerBean> data, OnItemClickCallback callback) {
                super(context, data);
                this.callback = callback;
            }

            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.item_onshelf_scan_package;
            }

            @Override
            protected void convert(SimpleViewHolder holder, OnShelfDataBean.InnerBean o, int position) {
                holder.setText(R.id.tv_package_order, o.zbzsx);
                holder.setText(R.id.tv_package_barcode, o.bztm);
                holder.itemView.setOnClickListener(view -> callback.onItemClick());
                holder.itemView.setOnLongClickListener(view -> {
                    callback.onItemLongClick();
                    return false;
                });
            }

            interface OnItemClickCallback {
                void onItemClick();

                void onItemLongClick();
            }
        }

        interface OnItemOperateCallback {
            void onSelectChange();

            void onItemRemoved();
        }
    }
}
