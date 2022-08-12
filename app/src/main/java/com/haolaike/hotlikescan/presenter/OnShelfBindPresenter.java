package com.haolaike.hotlikescan.presenter;

import com.haolaike.hotlikescan.base.BasePresenter;
import com.haolaike.hotlikescan.beans.BaseResultBean;
import com.haolaike.hotlikescan.http.RequestCallBack;
import com.haolaike.hotlikescan.model.OnShelfBindModel;
import com.haolaike.hotlikescan.view.OnShelfBindView;

import java.util.List;

/**
 * Created by pony on 2021/4/7.
 */
public final class OnShelfBindPresenter extends BasePresenter<OnShelfBindView, OnShelfBindModel> {
    @Override
    protected OnShelfBindModel getModel() {
        return new OnShelfBindModel();
    }

    public void bindShelf(List<String> codeList, String shelfPosition) {
        model.bindShelf(codeList, shelfPosition, new RequestCallBack<BaseResultBean>() {
            @Override
            public void onFailed(String result) {
                if (getView() != null) {
                    getView().onFailure(result);
                }
            }

            @Override
            public void onSuccess(BaseResultBean result) {
                if (getView() != null) {
                    getView().onSuccess(result.message);
                }
            }
        });
    }
}
