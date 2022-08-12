package com.haolaike.hotlikescan.view;

import com.haolaike.hotlikescan.base.BaseView;

/**
 * Created by pony on 2021/4/7.
 */
public interface OnShelfBindView extends BaseView {

    void onFailure(String errorMessage);
    void onSuccess(String message);
}
