package com.haolaike.hotlikescan.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZaakMan on 2017/8/9.
 * 普通工具类
 */

public class CommentUtil {
    public static String TransErrorToCNString(String errorStr) {
        Integer i = null;
        try {
            i = Integer.valueOf(errorStr);
        }catch (Exception e){

        }
        if (i != null && i.intValue() != 0){
            return TransErrorCodeToCNString(i.intValue());
        }
        if (errorStr.contains("NoConnectionError")) {
            return "未能访问服务器";
        }
        if (errorStr.equals("RESOURCE_NOT_FOUND")) {
            return "二维码不存在";
        }
        if (errorStr.contains("TimeoutError")) {
            return "访问超时";
        }
        if (errorStr.contains("UNKOWN_ACCOUNT")) {
            return "账号或密码错误";
        }
        if (errorStr.contains("ServerError")) {
            return "服务器错误";
        }
        if (errorStr.contains("LOSS_REQUIRED_PARAM")){
            return "丢失参数";
        }
        if (errorStr.contains("UNAUTHENTICATED")){
            return "用户验证失败，请再次登录";
        }
        return "未知错误";
    }

    public static String TransErrorCodeToCNString(int errorCode) {
        switch (errorCode){
            case 1: return "服务器内部错误";
            case 2: return "二维码不存在";
            case 3: return "丢失参数";
            case 4: return "无效值";
            case 5: return "不允许该操作";
            case 6: return "二维码已经扫描过了!请不要重复扫描";
            case 7: return "错误凭据";
            case 8: return "账号或密码错误";
            case 9:
            case 10: return "用户验证失败，请再次登录";
            case 11: return "格式错误";
        }
        return "未知错误";
    }



    public static boolean MatchString(String patternStr , String content){
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

}
