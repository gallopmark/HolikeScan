package com.haolaike.hotlikescan.view;

import com.haolaike.hotlikescan.base.BaseView;
import com.haolaike.hotlikescan.beans.OnShelfDataBean;

/**
 * Created by pony on 2021/4/6.
 */
public interface OnShelfScanView extends BaseView {

    void onSuccess(OnShelfDataBean bean);

    void onFailure(String failReason);

    void onItemRemoved();
}
