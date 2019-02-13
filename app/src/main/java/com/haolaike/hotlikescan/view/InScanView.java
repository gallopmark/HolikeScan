package com.haolaike.hotlikescan.view;

import com.haolaike.hotlikescan.base.BaseView;
import com.haolaike.hotlikescan.beans.PartsBean;

import java.util.List;

/**
 * Created by holike on 2017/12/21.
 * 入库
 */

public interface InScanView extends BaseView {
    public void setListData(String code, List<PartsBean> list);

    public void getCodeDataFailed(String failed);

    public void inWareSuccess();

    public void inWareFailed(String failed);
//    public void inWarePartFailed(String failed);

    public void loading(String text);

    public void disLoading();
}
