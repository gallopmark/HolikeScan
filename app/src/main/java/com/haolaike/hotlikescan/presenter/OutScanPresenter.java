package com.haolaike.hotlikescan.presenter;

import android.text.TextUtils;

import com.haolaike.hotlikescan.base.BasePresenter;
import com.haolaike.hotlikescan.beans.OutScanBean;
import com.haolaike.hotlikescan.beans.PartsBean;
import com.haolaike.hotlikescan.data.PartsData;
import com.haolaike.hotlikescan.model.OutScanModel;
import com.haolaike.hotlikescan.view.OutScanView;

import org.json.JSONObject;

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
     *
     * @param code
     * @param list
     */
    public void getScanData(String code, List<PartsBean> list) {
        if (!TextUtils.isEmpty(code)) {
            if (list == null) {
                if (PartsData.getInstance().hasScanOut(code)) {//出库列表中已经存在
                    getView().scaned();
                } else {
                    getNetScanData(code);
                }
            } else {
                if (hasCode(code, list)) {//列表中存在
                    getView().setListData(code, list);
                    if (PartsData.getInstance().hasScanOut(code)) {//出库列表中已经存在
                        PartsData.getInstance().updateOutScan(code);
                        if (isScanAll(list)) {
                            getView().scanFinish();
                        }
                    } else {
                        if (isScanAll(list)) {
                            PartsData.getInstance().addOutList(list);
                            getView().scanFinish();
                        }
                    }
                } else {//不在列表中，不同部件
                    getView().difference();
                }
            }
        }
    }

    /**
     * 从网络上获取二维码数据
     *
     * @param code
     */
    private void getNetScanData(final String code) {
        getView().loading("正在获取数据...");
        model.getCodeData(code, new OutScanModel.GetCodeDataListener() {
            @Override
            public void success(OutScanBean result) {
                getView().disLoading();
                List<PartsBean> list = result.getGtReturnExt();
                for (PartsBean bean : list) {
                    bean.setType(PartsBean.TYPE_OUT);
                    bean.setTime(System.currentTimeMillis());
                }
                hasCode(code, list);
                getView().setListData(code, list);
                if (isScanAll(list)) {
                    PartsData.getInstance().addOutList(list);
                    getView().scanFinish();
                }
            }

            @Override
            public void failed(String failed) {
                try {
                    JSONObject jsonObject = new JSONObject(failed);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    failed = dataObject.optString("emessage");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getView().disLoading();
                getView().getCodeDataFailed(failed);
            }
        });
    }


    /**
     * 判断列表是否存在条形码并且修改扫描状态
     *
     * @param code
     * @param list
     * @return
     */
    public boolean hasCode(String code, List<PartsBean> list) {
        if (list != null) {
            for (PartsBean bean : list) {
                if (code.equals(bean.getBztm())) {
                    bean.setIsScan(true);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否全部扫完
     *
     * @param list
     * @return
     */
    public boolean isScanAll(List<PartsBean> list) {
        if (list != null) {
            for (PartsBean bean : list) {
                if (!bean.getIsScan()) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取已经扫描包数
     *
     * @param list
     * @return
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
