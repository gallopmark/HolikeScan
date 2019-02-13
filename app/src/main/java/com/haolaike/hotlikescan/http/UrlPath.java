package com.haolaike.hotlikescan.http;

import com.haolaike.hotlikescan.BuildConfig;

/**
 * Created by wqj on 2017/9/28.
 */

public class UrlPath {
//        private static String HOST = "http://120.79.76.243";
//    private static String HOST = "http://172.16.111.48:8081";
//    private static String HOST = "http://c.holike.com";
    private static String HOST = BuildConfig.API_HOST;

    //登录接口
    public static final String URL_LOGIN = HOST + "/warehouse/api/appuser";

    //获取入库二维码
    public static final String GET_STORAGE_QRCODES = HOST + "/warehouse/api/package/";

    //扫描完入库
    public static final String POST_STORAGE_QRCODES = HOST + "/warehouse/api/package/";

    //获取出库二维码
    public static final String GET_RELEASE_QRCODES = HOST + "/warehouse/api/release/";

    //出库
    public static final String POST_RELEASE_QRCODES = HOST + "/warehouse/api/release/";

    //绑定部件码跟货架码
    public static final String POST_BINDING_QRCODE = HOST + "/warehouse/api/onshelves/";

    //检查App版本号
    public static final String URL_CHECK_VERSION = HOST + "/warehouse/api/appversion";
}
