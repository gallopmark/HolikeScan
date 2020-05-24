package com.haolaike.hotlikescan.view;

import com.haolaike.hotlikescan.base.BaseView;
import com.haolaike.hotlikescan.beans.PartsBean;

import java.util.List;

/**
 * Created by holike on 2017/12/21.
 * 入库
 */

public interface InScanView extends BaseView {
    void setListData(String code, List<PartsBean> list);

    void getCodeDataFailed(String failed);

    void inWareSuccess();

    void inWareFailed(String failed);
//    public void inWarePartFailed(String failed);

    void loading(String text);

    void disLoading();
}
