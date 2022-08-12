package com.haolaike.hotlikescan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by pony on 2020/12/23.
 * Copyright (c) 2020 holike. All rights reserved.
 */
abstract class BaseAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    LayoutInflater mLayoutInflater;
    protected final Context mContext;
    protected final List<T> mData;

    public BaseAdapter(Context context, List<T> data) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mData = data;
    }

    public void set(int position, T t) {
        if (mData != null) {
            mData.set(position, t);
            notifyDataSetChanged();
        }
    }

    public void add(T t) {
        if (mData != null) {
            mData.add(t);
            notifyDataSetChanged();
        }
    }

    public void addAll(List<T> data) {
        addAll(data, true);
    }

    public void addAll(List<T> data, boolean removeAll) {
        if (mData != null) {
            if (removeAll) {
                this.mData.clear();
            }
            this.mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (mData != null) {
            mData.clear();
            notifyDataSetChanged();
        }
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(parent, viewType);
    }

    abstract VH getViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        innerBindViewHolder(holder, position);
    }

    abstract void innerBindViewHolder(@NonNull VH holder, int position);
}
