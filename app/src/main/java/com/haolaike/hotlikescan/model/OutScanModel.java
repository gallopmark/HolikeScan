package com.haolaike.hotlikescan.model;

import com.haolaike.hotlikescan.base.BaseModel;
import com.haolaike.hotlikescan.beans.OutScanBean;
import com.haolaike.hotlikescan.http.MyHttpClient;
import com.haolaike.hotlikescan.http.RequestCallBack;
import com.haolaike.hotlikescan.http.UrlPath;
import com.haolaike.hotlikescan.utils.Constants;
import com.haolaike.hotlikescan.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by holike on 2017/12/22.
 * 出库扫描页
 */

public class OutScanModel implements BaseModel {

    /**
     * 获取二维码信息
     *
     * @param code
     * @param listener
     */
    public void getCodeData(String code, GetCodeDataListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("token", SharedPreferencesUtils.getString(Constants.TOKEN, ""));
        MyHttpClient.get(UrlPath.GET_RELEASE_QRCODES + code, params, new RequestCallBack<OutScanBean>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(OutScanBean result) {
                listener.success(result);
            }
        });
    }

    public interface GetCodeDataListener {
        void success(OutScanBean result);

        void failed(String failed);
    }

}
