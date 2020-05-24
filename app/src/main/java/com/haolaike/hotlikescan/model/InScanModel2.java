package com.haolaike.hotlikescan.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.haolaike.hotlikescan.base.BaseModel;
import com.haolaike.hotlikescan.http.MyHttpClient;
import com.haolaike.hotlikescan.http.RequestCallBack;
import com.haolaike.hotlikescan.http.UrlPath;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by pony on 2020/5/24.
 * Copyright (c) 2020 holike. All rights reserved.
 */
public class InScanModel2 implements BaseModel {

    public void getCode(String code, RequestCallBack<String> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        MyHttpClient.get(UrlPath.URL_TEMP, params, new RequestCallBack<String>() {
            @Override
            public void onFailed(String result) {
                callBack.onFailed(getMessage(result));
            }

            @Override
            public void onSuccess(String result) {
                callBack.onSuccess(result);
            }

        });
    }

    private String getMessage(String json) {
        try {
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
            if (obj.has("msg")) {
                JsonElement element = obj.get("msg");
                if (element == null || element.isJsonNull()) return "";
                return element.getAsString();
            }
        } catch (Exception ignored) {
        }
        return json;
    }
}
