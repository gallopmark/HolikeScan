package com.haolaike.hotlikescan.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by wqj on 2017/9/20.
 */

public abstract class BaseFragment<T extends BasePresenter, V extends BaseView> extends Fragment {
    protected T mPresenter;
    protected View mContentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenter();
        if (mPresenter != null && (this instanceof BaseView)) {
            mPresenter.attach((V) this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(setContentViewId(), container, false);
        init();
        return mContentView;
    }

    protected abstract int setContentViewId();

    protected abstract void init();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.deAttach();
        }
    }

    protected abstract T getPresenter();

    protected void startActivity(Class<?> c) {
        Intent intent = new Intent(getActivity(), c);
        getActivity().startActivity(intent);
    }

}
