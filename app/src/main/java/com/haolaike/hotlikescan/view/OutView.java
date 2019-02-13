package com.haolaike.hotlikescan.view;

import com.haolaike.hotlikescan.base.BaseView;

/**
 * Created by wqj on 2018/1/1.
 * 出库
 */

public interface OutView extends BaseView {
    void outSuccess();

    void outFailed(String result);

    void delectItem(int position);
}
