package com.haolaike.hotlikescan.http;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ApiException extends Exception {

    private final int code;
    private String message;

    private ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.message = throwable.getMessage();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ApiException(httpException, httpException.code());
            try {
                if (httpException.response() != null) {
                    ResponseBody errorBody = httpException.response().errorBody();
                    if (errorBody != null) ex.message = errorBody.string();
                    else ex.message = "请求失败，请稍后再试！";
                } else {
                    ex.message = "请求失败，请稍后再试！";
                }
            } catch (Exception e1) {
                ex.message = "请求失败，请稍后再试！";
            }
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.message = "网络连接超时，请检查您的网络状态，稍后重试！";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.message = "网络连接异常，请检查您的网络状态，稍后重试！";
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.message = "网络连接超时，请检查您的网络状态，稍后重试！";
            return ex;
        } else if (e instanceof UnknownHostException) {
            ex = new ApiException(e, ERROR.TIMEOUT_ERROR);
            ex.message = "网络连接异常，请检查您的网络状态，稍后重试！";
            return ex;
        } else if (e instanceof NullPointerException) {
            ex = new ApiException(e, ERROR.NULL_POINTER_EXCEPTION);
            ex.message = "空指针异常";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ApiException(e, ERROR.SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else if (e instanceof ClassCastException) {
            ex = new ApiException(e, ERROR.CAST_ERROR);
            ex.message = "类型转换错误";
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof JsonSerializer
                || e instanceof NotSerializableException
                || e instanceof ParseException) {
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.message = "数据解析错误";
            return ex;
        } else if (e instanceof IllegalStateException) {
            ex = new ApiException(e, ERROR.ILLEGAL_STATE_ERROR);
            ex.message = "非法数据异常";
            return ex;
        } else {
            ex = new ApiException(e, ERROR.UNKNOWN);
            ex.message = "请求失败，请稍后再试！";
            return ex;
        }
    }

    /**
     * 约定异常
     */
    public interface ERROR {
        /**
         * 未知错误
         */
        int UNKNOWN = 1000;
        /**
         * 连接超时
         */
        int TIMEOUT_ERROR = 1001;
        /**
         * 空指针错误
         */
        int NULL_POINTER_EXCEPTION = 1002;

        /**
         * 证书出错
         */
        int SSL_ERROR = 1003;

        /**
         * 类转换错误
         */
        int CAST_ERROR = 1004;

        /**
         * 解析错误
         */
        int PARSE_ERROR = 1005;

        /**
         * 非法数据异常
         */
        int ILLEGAL_STATE_ERROR = 1006;
    }
}
