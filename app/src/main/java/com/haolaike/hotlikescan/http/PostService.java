package com.haolaike.hotlikescan.http;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by wqj on 2017/11/24.
 * post请求
 */

public interface PostService {
    String CONNTENT_TYPE = "Content-Type: application/json";

    @POST
    @Headers(CONNTENT_TYPE)
    Observable<String> post(@Url String url);

    @POST
    @Headers(CONNTENT_TYPE)
    Observable<String> postHeader(@Url String url, @HeaderMap Map<String, String> header);

    @POST
    @Headers(CONNTENT_TYPE)
    Observable<String> postParams(@Url String url, @QueryMap Map<String, String> param);

    @POST
    @Headers(CONNTENT_TYPE)
    Observable<String> postHeaderParams(@Url String url, @HeaderMap Map<String, String> header, @QueryMap Map<String, String> params);
}
