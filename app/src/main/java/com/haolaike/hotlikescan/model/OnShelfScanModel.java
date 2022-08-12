package com.haolaike.hotlikescan.model;

import com.haolaike.hotlikescan.base.BaseModel;
import com.haolaike.hotlikescan.beans.OnShelfDataBean;
import com.haolaike.hotlikescan.http.MyHttpClient;
import com.haolaike.hotlikescan.http.RequestCallBack;
import com.haolaike.hotlikescan.http.UrlPath;
import com.haolaike.hotlikescan.utils.Constants;
import com.haolaike.hotlikescan.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pony on 2021/4/6.
 */
public final class OnShelfScanModel implements BaseModel {

    public void getCrmZmm(String code, RequestCallBack<OnShelfDataBean> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("token", SharedPreferencesUtils.getString(Constants.TOKEN, ""));
        MyHttpClient.get(UrlPath.URL_ONSHELF_SCAN + "?BZTM=" + code, params, callBack);
    }
}
