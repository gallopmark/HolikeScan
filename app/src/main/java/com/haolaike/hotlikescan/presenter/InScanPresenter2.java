package com.haolaike.hotlikescan.presenter;

import com.haolaike.hotlikescan.base.BasePresenter;
import com.haolaike.hotlikescan.http.RequestCallBack;
import com.haolaike.hotlikescan.model.InScanModel2;
import com.haolaike.hotlikescan.view.InScanView2;

/**
 * Created by pony on 2020/5/24.
 * Copyright (c) 2020 holike. All rights reserved.
 */
public class InScanPresenter2 extends BasePresenter<InScanView2, InScanModel2> {
    @Override
    protected InScanModel2 getModel() {
        return new InScanModel2();
    }

    public void getCode(String code) {
        model.getCode(code, new RequestCallBack<String>() {
            @Override
            public void onFailed(String result) {
                if (getView() != null) {
                    getView().onScanFailure(result);
                }
            }

            @Override
            public void onSuccess(String result) {
                if (getView() != null) {
                    getView().onScanSuccess("扫描成功");
                }
            }
        });
    }
}
