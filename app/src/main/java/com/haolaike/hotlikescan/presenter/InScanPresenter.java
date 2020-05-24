package com.haolaike.hotlikescan.presenter;

import android.text.TextUtils;

import com.haolaike.hotlikescan.base.BasePresenter;
import com.haolaike.hotlikescan.beans.InScanBean;
import com.haolaike.hotlikescan.beans.PartsBean;
import com.haolaike.hotlikescan.data.PartsData;
import com.haolaike.hotlikescan.model.InScanModel;
import com.haolaike.hotlikescan.utils.Constants;
import com.haolaike.hotlikescan.utils.SharedPreferencesUtils;
import com.haolaike.hotlikescan.view.InScanView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by holike on 2017/12/21.
 * 入库
 */

public class InScanPresenter extends BasePresenter<InScanView, InScanModel> {

    private String dateTime;

    public void setDateTime(String source) {
        dateTime = source;
//        dateTime = source.replaceAll("-", "");
    }

    @Override
    protected InScanModel getModel() {
        return new InScanModel();
    }

    /**
     * 分析二维码
     */
    public void getScanData(String code, List<PartsBean> list) {
        if (!TextUtils.isEmpty(code)) {
            if (hasCode(code, list)) {//列表中存在二维码
                if (getView() != null)
                    getView().setListData(code, list);
                if (isScanAll(list)) {
                    inWarehose(code, list);
                } else {
                    PartsData.getInstance().updateInScan(code);
                }
            } else {
                if (PartsData.getInstance().hasScanIn(code)) {//缓存中存在此二维码
                    list = PartsData.getInstance().getInListByCode(code);
                    hasCode(code, list);
                    if (getView() != null)
                        getView().setListData(code, list);
                    if (isScanAll(list)) {
                        inWarehose(code, list);
                    } else {
                        PartsData.getInstance().updateInScan(code);
                    }
                } else {
                    getNetScanData(code);
                }
            }
        }
    }

    /**
     * 从网络上获取二维码数据
     */
    private void getNetScanData(final String code) {
        if (getView() != null)
            getView().loading("正在获取数据...");
        model.getCodeData(code, new InScanModel.GetCodeDataListener() {
            @Override
            public void success(InScanBean result) {
                if (getView() != null)
                    getView().disLoading();
                List<PartsBean> list = result.getZtmmRkcxPdaExt();
                for (PartsBean bean : list) {
                    bean.setTargetBztm(result.getGvBztm());
                    bean.setType(PartsBean.TYPE_IN);
                    bean.setTime(System.currentTimeMillis());
                    if (TextUtils.equals(code, bean.getBztm())) {
                        bean.setIsScan(true);
                    }
                }
//                hasCode(code, list);
                PartsData.getInstance().addInList(list);
                if (getView() != null)
                    getView().setListData(code, list);
                if (isScanAll(list)) {
                    inWarehose(code, list);
                }
            }

            @Override
            public void failed(String failed) {
//                try {
//                    JSONObject jsonObject = new JSONObject(failed);
//                    failed = jsonObject.optString("reason");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                if (getView() != null) {
                    getView().disLoading();
                    getView().getCodeDataFailed(failed);
                }
            }
        });
    }


    /**
     * 判断列表是否存在条形码并且修改扫描状态
     */
    private boolean hasCode(String code, List<PartsBean> list) {
        if (list != null) {
            for (PartsBean bean : list) {
                if (TextUtils.equals(code, bean.getBztm())) {
                    bean.setIsScan(true);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否全部扫完
     */
    private boolean isScanAll(List<PartsBean> list) {
        if (list != null && list.size() > 0) {
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
     * 入库
     */
    public void inWarehose(String code, List<PartsBean> list) {
        if (getView() != null)
            getView().loading("正在入库...");
        JSONArray jsonArray = new JSONArray();
        for (PartsBean bean : list) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("bztm", bean.getBztm());
                jsonObject.put("zrkczr", SharedPreferencesUtils.getString(Constants.USER, ""));
                jsonObject.put("zpm", "");
                jsonObject.put("zgzrq", TextUtils.isEmpty(dateTime) ? "" : dateTime); //zgzrq
                jsonArray.put(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JSONObject json = new JSONObject();
        try {
            json.put("packagelist", jsonArray);
            json.put("token", SharedPreferencesUtils.getString(Constants.TOKEN, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.inWarehose(code, json.toString(), new InScanModel.InWarehoseListener() {
            @Override
            public void success() {
                if (getView() != null)
                    getView().disLoading();
                PartsData.getInstance().deleteIn(list);
                getView().inWareSuccess();
            }

            @Override
            public void failed(String failed) {
                if (getView() != null) {
                    getView().disLoading();
                    getView().inWareFailed(failed);
                }
//                PartsData.getInstance().deleteIn(list);
            }
        });
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

    /**
     * 根据code获取所在位置
     */
    public static int getPosition(String code, List<PartsBean> list) {
        int position = 0;
        if (list != null && list.size() > 0) {
            for (int i = 0, size = list.size(); i < size; i++) {
                PartsBean bean = list.get(i);
                if (TextUtils.equals(code, bean.getBztm())) {
                    position = i;
                    break;
                }
            }
        }
        return position;
    }
}

