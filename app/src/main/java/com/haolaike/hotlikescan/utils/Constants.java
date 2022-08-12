package com.haolaike.hotlikescan.utils;


/**
 * Created by wqj on 2017/11/23.
 * 常量类
 */

public class Constants {
    public final static int CLEAR_TIME = 1000 * 60 * 60 * 24 * 7;//多久清除缓存数据(七天)

    /*sharepreference*/
    public final static String USER = "user";
    public final static String TOKEN = "token";
    public final static String EXPIRATIONTIME = "expirationTime";
    public final static String OUTINFOBEAN = "outinfoBean";


    /*广播行为*/
    public static final String SCANACTION = "com.android.server.scannerservice.broadcast";
    public static final String SCANNERDATA = "scannerdata";

    public static final String PDA_ACTION = "android.intent.ACTION_DECODE_DATA";
    public static final String PDA_DATA = "barcode_string";
}
