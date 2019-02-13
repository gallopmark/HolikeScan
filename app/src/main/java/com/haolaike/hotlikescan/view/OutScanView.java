package com.haolaike.hotlikescan.view;

import com.haolaike.hotlikescan.base.BaseView;
import com.haolaike.hotlikescan.beans.PartsBean;

import java.util.List;

/**
 * Created by holike on 2017/12/22.
 * 出库
 */

public interface OutScanView extends BaseView {
    public void setListData(String code, List<PartsBean> list);

    public void getCodeDataFailed(String failed);

    public void loading(String text);

    public void disLoading();

    public void scaned();

    public void difference();

    public void scanFinish();
}
