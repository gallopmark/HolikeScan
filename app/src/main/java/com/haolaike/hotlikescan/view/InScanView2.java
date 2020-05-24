package com.haolaike.hotlikescan.view;

import com.haolaike.hotlikescan.base.BaseView;
import com.haolaike.hotlikescan.beans.PartsBean;

import java.util.List;

/**
 * Created by holike on 2017/12/21.
 * 入库
 */

public interface InScanView2 extends BaseView {
    void onScanSuccess(String message);
    void onScanFailure(String failReason);
}
