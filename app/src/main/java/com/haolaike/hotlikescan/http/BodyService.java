package com.haolaike.hotlikescan.http;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by wqj on 2017/11/24.
 * body请求
 */

public interface BodyService {
    String CONNTENT_TYPE = "Content-Type: application/json";

    @POST
    @Headers(CONNTENT_TYPE)
    Observable<String> post(@Url String url, @Body String body);

    @POST
    @Headers(CONNTENT_TYPE)
    Observable<String> post(@Url String url, @HeaderMap Map<String, String> header, @Body String body);
}
