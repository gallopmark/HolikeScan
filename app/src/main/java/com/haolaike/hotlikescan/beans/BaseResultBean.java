package com.haolaike.hotlikescan.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pony on 2021/4/8.
 */
public class BaseResultBean {
    //    {"code":0,"reason":null,"data":{"eMessage":"更新成功","eSubrc":"S","tsj":null},"att":null}
    @SerializedName("eMessage")
    public String message;
    @SerializedName("eSubrc")
    String state;

    public boolean isStateSuccess() {
        return "S".equalsIgnoreCase(state);
    }
}
