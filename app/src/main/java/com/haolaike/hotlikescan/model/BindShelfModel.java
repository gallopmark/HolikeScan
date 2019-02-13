package com.haolaike.hotlikescan.model;

import com.haolaike.hotlikescan.base.BaseModel;
import com.haolaike.hotlikescan.http.MyHttpClient;
import com.haolaike.hotlikescan.http.RequestCallBack;
import com.haolaike.hotlikescan.http.UrlPath;
import com.haolaike.hotlikescan.utils.Constants;
import com.haolaike.hotlikescan.utils.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by wqj on 2018/1/1.
 * 指定货架
 */

public class BindShelfModel implements BaseModel {

    /**
     * 指定货架
     *
     * @param code
     * @param shelfCodes
     * @param listener
     */
    public void bindShelf(String code, List<String> shelfCodes, BindShelfListener listener) {
        JSONArray jsonArray = new JSONArray();
        for (String shelfCode : shelfCodes) {
            jsonArray.put(shelfCode);
        }
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("shelfCode", jsonArray);
            jsonParams.put("token", SharedPreferencesUtils.getString(Constants.TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyHttpClient.postBody(UrlPath.POST_BINDING_QRCODE + code, jsonParams.toString(), new RequestCallBack<String>() {
            @Override
            public void onFailed(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String reason = jsonObject.getString("reason");
                    listener.failed(reason);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String result) {
                listener.success(result);
            }

        });
    }

    public interface BindShelfListener {
        void success(String result);

        void failed(String result);
    }
}
