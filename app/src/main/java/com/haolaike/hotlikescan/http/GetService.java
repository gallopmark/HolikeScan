package com.haolaike.hotlikescan.http;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by wqj on 2017/11/24.
 * get请求
 */

public interface GetService {
    String CONNTENT_TYPE = "Content-Type: application/x-www-form-urlencoded";

    @GET
    @Headers(CONNTENT_TYPE)
    Observable<String> get(@Url String url);

    @GET
    @Headers(CONNTENT_TYPE)
    Observable<String> getHeader(@Url String url, @HeaderMap Map<String, String> header);

    @GET
    @Headers(CONNTENT_TYPE)
    Observable<String> getParams(@Url String url, @QueryMap Map<String, String> params);

    @GET
    @Headers(CONNTENT_TYPE)
    Observable<String> getHeaderParams(@Url String url, @HeaderMap Map<String, String> header, @QueryMap Map<String, String> params);
}
