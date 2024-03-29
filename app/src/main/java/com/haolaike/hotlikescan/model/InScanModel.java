package com.haolaike.hotlikescan.model;


import android.text.TextUtils;

import com.haolaike.hotlikescan.base.BaseModel;
import com.haolaike.hotlikescan.beans.InScanBean;
import com.haolaike.hotlikescan.beans.SubmitResultBean;
import com.haolaike.hotlikescan.http.MyHttpClient;
import com.haolaike.hotlikescan.http.RequestCallBack;
import com.haolaike.hotlikescan.http.UrlPath;
import com.haolaike.hotlikescan.utils.Constants;
import com.haolaike.hotlikescan.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by holike on 2017/12/21.
 * 入库
 */

public class InScanModel implements BaseModel {

    /**
     * 获取二维码信息
     */
    public void getCodeData(String code, GetCodeDataListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("token", SharedPreferencesUtils.getString(Constants.TOKEN, ""));
        MyHttpClient.get(UrlPath.GET_STORAGE_QRCODES + code, params, new RequestCallBack<InScanBean>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(InScanBean result) {
                listener.success(result);
            }
        });
    }

    public interface GetCodeDataListener {
        void success(InScanBean result);

        void failed(String failed);
    }

    /**
     * 入库
     */
    public void inWarehose(String code, String params, InWarehoseListener listener) {
        MyHttpClient.postBodyByTimeout(UrlPath.POST_STORAGE_QRCODES + code, null, params, 5 * 60, new RequestCallBack<SubmitResultBean>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(SubmitResultBean result) {
                StringBuilder builder = new StringBuilder();
                int errorCount = 0;
                if (result.getGtMessageZj() == null) {
                    listener.success();
                    return;
                }
                for (int i = 0; i < result.getGtMessageZj().size(); i++) {
                    SubmitResultBean.GtMessageZjBean bean = result.getGtMessageZj().get(i);
                    if (!TextUtils.equals(bean.getSubrc(), "S")) {
                        builder.append(bean.getMessage()).append(i == result.getGtMessageZj().size() ? "" : "\n");
                        errorCount++;
                    }
                }
                try {
                    if (errorCount == 0) {
                        listener.success();
                    } else {
                        listener.failed(builder.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface InWarehoseListener {
        void success();

        void failed(String failed);

    }
}
