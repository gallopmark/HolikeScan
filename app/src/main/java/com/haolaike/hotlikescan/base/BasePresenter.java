package com.haolaike.hotlikescan.base;

/**
 * Created by wqj on 2017/10/17.
 * 所有presenter的抽象类
 */

public abstract class BasePresenter<V extends BaseView, M extends BaseModel> {
    public final static String DEATTACH_EXCEPTION = "view not attached";
    private V view;
    protected M model;

    public void attach(V t) {
        view = t;
        model = getModel();
    }

    public void deAttach() {
        view = null;
        onDestroy();
    }

    protected void onDestroy() {
//        HttpClient.getInstance().cancelAll();
    }

    public boolean isViewAttached() {
        return view != null;
    }

    public V getView() {
        if (view == null) {
            throw new RuntimeException("view not attached");
        } else {
            return view;
        }
    }

    protected abstract M getModel();
}
