package com.haolaike.hotlikescan.model;

import com.haolaike.hotlikescan.base.BaseModel;
import com.haolaike.hotlikescan.http.MyHttpClient;
import com.haolaike.hotlikescan.http.RequestCallBack;
import com.haolaike.hotlikescan.http.UrlPath;

import org.json.JSONObject;

/**
 * Created by wqj on 2018/1/1.
 * 出库
 */

public class OutModel implements BaseModel {

    /**
     * 出库
     */
    public void out(String code, String params, OutListener listener) {
        MyHttpClient.postBody(UrlPath.POST_RELEASE_QRCODES + code, params, new RequestCallBack<JSONObject>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(JSONObject result) {
                listener.success(result);
            }
        });
    }

    public interface OutListener {
        void success(JSONObject result);

        void failed(String result);
    }
}
