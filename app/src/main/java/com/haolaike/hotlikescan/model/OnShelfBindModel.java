package com.haolaike.hotlikescan.model;

import com.google.gson.Gson;
import com.haolaike.hotlikescan.base.BaseModel;
import com.haolaike.hotlikescan.beans.BaseResultBean;
import com.haolaike.hotlikescan.http.LogCat;
import com.haolaike.hotlikescan.http.MyHttpClient;
import com.haolaike.hotlikescan.http.RequestCallBack;
import com.haolaike.hotlikescan.http.UrlPath;
import com.haolaike.hotlikescan.utils.Constants;
import com.haolaike.hotlikescan.utils.SharedPreferencesUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by pony on 2021/4/7.
 */
public final class OnShelfBindModel implements BaseModel {

    public void bindShelf(List<String> codeList, String shelfPosition, final RequestCallBack<BaseResultBean> callBack) {
        List<Map<String, String>> tempList = new ArrayList<>();
        for (String code : codeList) {
            final Map<String, String> tempMap = new HashMap<>();
            tempMap.put("bztm", code);
            tempMap.put("zsjkw", shelfPosition);
            tempMap.put("zsjczr", SharedPreferencesUtils.getString(Constants.USER));
            tempList.add(tempMap);
        }
        final Map<String, Object> params = new HashMap<>();
        params.put("tsj", tempList);
        final String body = new Gson().toJson(params);
        LogCat.e("body", body);
        MyHttpClient.postBodyByTimeout(UrlPath.URL_ONSHELF_BIND, null, body, 3 * 60, new RequestCallBack<BaseResultBean>() {
            @Override
            public void onFailed(String result) {
                String errorMsg;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    errorMsg = dataObject.optString("emessage");
                } catch (Exception e) {
                    errorMsg = result;
                }
                callBack.onFailed(errorMsg);
            }

            @Override
            public void onSuccess(BaseResultBean result) {
                callBack.onSuccess(result);
            }
        });
    }
}
