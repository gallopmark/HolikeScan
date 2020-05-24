package com.haolaike.hotlikescan.presenter;


import com.haolaike.hotlikescan.base.BasePresenter;
import com.haolaike.hotlikescan.beans.OutScanBean;
import com.haolaike.hotlikescan.beans.PartsBean;
import com.haolaike.hotlikescan.data.PartsData;
import com.haolaike.hotlikescan.model.OutScanModel;
import com.haolaike.hotlikescan.view.OutScanView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by holike on 2017/12/22.
 * 出库
 */

public class OutScanPresenter extends BasePresenter<OutScanView, OutScanModel> {
    @Override
    protected OutScanModel getModel() {
        return new OutScanModel();
    }

    /**
     * 分析二维码
     */
    public void getScanData(String code, List<PartsBean> list) {
        if (list == null) {
            List<PartsBean> dataList = new ArrayList<>(PartsData.getInstance().getOutListByCode(code));
            if (!dataList.isEmpty()) {
                hasCode(code, dataList);
                if (isScanAll(dataList)) {
                    if (getView() != null) getView().scaned();
                } else {
                    if (getView() != null) getView().setListData(code, dataList);
                    PartsData.getInstance().updateOutScan(code);
                }
            } else {
                getNetScanData(code);
            }
        } else {
            if (hasCode(code, list)) {//单号列表中存在此包装码
                if (getView() != null) getView().setListData(code, list);
                if (PartsData.getInstance().hasScanOut(code)) {//出库列表中已经存在
                    PartsData.getInstance().updateOutScan(code);
                    if (isScanAll(list)) {
                        if (getView() != null) getView().scaned();
//                        if (getView() != null) getView().scanFinish();
                    }
                } else {
                    if (isScanAll(list)) {
                        if (getView() != null) getView().scaned();
//                        PartsData.getInstance().addOutList(list);
//                        if (getView() != null) getView().scanFinish();
                    }
                }
            } else {//不在列表中，不同部件
                getNetScanData(code);
//                    if (getView() != null) getView().difference();
            }
        }
    }

    /**
     * 从网络上获取二维码数据
     */
    private void getNetScanData(final String code) {
        if (getView() != null) getView().loading("正在获取数据...");
        model.getCodeData(code, new OutScanModel.GetCodeDataListener() {
            @Override
            public void success(OutScanBean result) {
                if (getView() != null) getView().disLoading();
                List<PartsBean> list = result.getGtReturnExt();
                for (PartsBean bean : list) {
                    bean.setType(PartsBean.TYPE_OUT);
                    bean.setTargetBztm(result.getGvZbztm());
                    bean.setTime(System.currentTimeMillis());
                }
                hasCode(code, list);
                if (getView() != null) getView().setListData(code, list);
                PartsData.getInstance().addOutList(list);
                if (isScanAll(list)) {
//                    PartsData.getInstance().addOutList(list);
                    if (getView() != null) getView().scaned();
//                    if (getView() != null) getView().scanFinish();
                }
            }

            @Override
            public void failed(String failed) {
//                try {
//                    JSONObject jsonObject = new JSONObject(failed);
//                    JSONObject dataObject = jsonObject.getJSONObject("data");
//                    failed = dataObject.optString("emessage");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                if (getView() != null) getView().disLoading();
                if (getView() != null) getView().getCodeDataFailed(failed);
            }
        });
    }


    /**
     * 判断列表是否存在条形码并且修改扫描状态
     */
    private boolean hasCode(String code, List<PartsBean> list) {
        if (list == null || list.isEmpty()) return false;
        for (PartsBean bean : list) {
            if (code.equals(bean.getBztm())) {
                bean.setIsScan(true);
                return true;
            }
        }
        return false;
    }


    /**
     * 是否全部扫完
     */
    private boolean isScanAll(List<PartsBean> list) {
        if (list == null || list.isEmpty()) return false;
        for (PartsBean bean : list) {
            if (!bean.getIsScan()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取已经扫描包数
     */
    public int getScanedNum(List<PartsBean> list) {
        int num = 0;
        for (PartsBean bean : list) {
            if (bean.getIsScan()) {
                num++;
            }
        }
        return num;
    }
}
