package com.haolaike.hotlikescan.model;

import com.haolaike.hotlikescan.base.BaseModel;
import com.haolaike.hotlikescan.beans.UserBean;
import com.haolaike.hotlikescan.http.MyHttpClient;
import com.haolaike.hotlikescan.http.RequestCallBack;
import com.haolaike.hotlikescan.http.UrlPath;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by holike on 2017/12/15.
 * 登录model
 */

public class LoginModel implements BaseModel {

    /**
     * 登录
     *
     */
    public void login(String name, String password, RequestCallBack<UserBean> callBack) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("password", password);
        MyHttpClient.post(UrlPath.URL_LOGIN, params, callBack);
    }
}
