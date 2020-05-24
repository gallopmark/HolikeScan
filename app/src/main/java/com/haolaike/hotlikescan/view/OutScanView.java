package com.haolaike.hotlikescan.view;

import com.haolaike.hotlikescan.base.BaseView;
import com.haolaike.hotlikescan.beans.PartsBean;

import java.util.List;

/**
 * Created by holike on 2017/12/22.
 * 出库
 */

public interface OutScanView extends BaseView {
    void setListData(String code, List<PartsBean> list);

    void getCodeDataFailed(String failed);

    void loading(String text);

    void disLoading();

    void scaned();

    void difference();

    void scanFinish();
}
