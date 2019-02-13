package com.haolaike.hotlikescan.view;

import com.haolaike.hotlikescan.base.BaseView;

/**
 * Created by wqj on 2018/1/1.
 * 指定货架
 */

public interface BindShelfView extends BaseView {
    void loading(String text);

    void BindShelSuccess(String result);

    void BindShelFailed(String failed);

    void countdownFinish();

    void delectItem(int position);
}
