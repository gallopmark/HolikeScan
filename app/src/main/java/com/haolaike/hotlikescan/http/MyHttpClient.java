package com.haolaike.hotlikescan.http;


import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2016/12/8 0008.
 * 请求框架，请求头带版本(version)默认参数
 */

public class MyHttpClient {

    /**
     * 只带url的get请求
     *
     * @param url
     * @param callBack
     */
    public static void get(String url, RequestCallBack callBack) {
        get(url, null, null, callBack);
    }

    /**
     * 带参数的get请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    public static void get(String url, Map<String, String> params, RequestCallBack callBack) {
        get(url, null, params, callBack);
    }

    /**
     * 带请求头和参数的get请求
     *
     * @param url
     * @param header
     * @param params
     * @param callBack
     */
    public static void get(String url, Map<String, String> header, Map<String, String> params, RequestCallBack callBack) {
        if (header == null) {
            header = new HashMap<>();
        }
        HttpClient.getInstance().get(url, header, params, callBack);
    }

    /**
     * 只带url的post请求
     *
     * @param url
     * @param callBack
     */
    public static void post(String url, RequestCallBack callBack) {
        post(url, null, null, callBack);
    }

    /**
     * 带参数的post请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    public static void post(String url, Map<String, String> params, RequestCallBack callBack) {
        post(url, null, params, callBack);
    }

    /**
     * 带cliid请求头和参数的post请求
     *
     * @param url
     * @param header
     * @param params
     * @param callBack
     */
    public static void postByCliId(String url, Map<String, String> header, Map<String, String> params, RequestCallBack callBack) {
        if (header == null) {
            header = new HashMap<>();
        }
        post(url, header, params, callBack);
    }

    /**
     * 带请求头和参数的post请求
     *
     * @param url
     * @param header
     * @param params
     * @param callBack
     */
    public static void post(String url, Map<String, String> header, Map<String, String> params, RequestCallBack callBack) {
        if (header == null) {
            header = new HashMap<>();
        }
        HttpClient.getInstance().post(url, header, params, callBack);
    }

    /**
     * 带参数的body post请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    public static void postBody(String url, String params, RequestCallBack callBack) {
        postBody(url, null, params, callBack);
    }

    /**
     * 带请求头和参数的body post请求
     *
     * @param url
     * @param header
     * @param params
     * @param callBack
     */
    public static void postBody(String url, Map<String, String> header, String params, RequestCallBack callBack) {
        if (header == null) {
            header = new HashMap<>();
        }
        HttpClient.getInstance().post(url, header, params, callBack);
    }

    /**
     * 带cliid请求头和参数的body post请求
     *
     * @param url
     * @param header
     * @param params
     * @param callBack
     */
    public static void postBodyById(String url, Map<String, String> header, String params, RequestCallBack callBack) {
        if (header == null) {
            header = new HashMap<>();
        }
        postBody(url, header, params, callBack);
    }


    /**
     * 可设置超时时间请求
     */
    public static void postBodyByTimeout(String url, Map<String, String> header, String params, int timeout, RequestCallBack<?> callBack) {
        HttpClient.getInstance().postBodyByTimeout(url, header, params, timeout, callBack);
    }
}
