package com.haolaike.hotlikescan.http;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by wqj on 2017/9/28.
 */

public abstract class RequestCallBack<T> implements Observer<String> {
    private Disposable disposable;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        cancel();
        disposable = d;
    }

    public void cancel() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onComplete() {
        HttpClient.getInstance().remove(this);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFailed(ApiException.handleException(e).getMessage());
    }

    @Override
    public void onNext(@NonNull String s) {
        LogCat.i_response(s);
        try {
            JSONObject json = new JSONObject(s);
            switch (json.getInt("code")) {
                case 0:
                    if (json.has("data")) {
                        String result = json.optString("data");
                        Type genType = getClass().getGenericSuperclass();
                        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                        T t = new Gson().fromJson(result, params[0]);
                        onSuccess(t);
                    } else {
                        onSuccess((T) s);
                    }
                    break;
                default:
                    String reason = null;
                    try {
                        if (json.has("data")) {
                            JSONObject dataObject = json.getJSONObject("data");
                            if (dataObject.has("emessage")) {
                                reason = dataObject.optString("emessage");
                            }
                        }
                    } catch (Exception ignored) {
                    }
                    if (TextUtils.isEmpty(reason)) {
                        try {
                            reason = json.optString("reason");
                        } catch (Exception ignored) {
                        }
                    }
                    if (!TextUtils.isEmpty(reason)) {
                        onFailed(reason);
                    } else
                        onFailed(s);
                    break;
            }
        } catch (Exception e) {
            onFailed(ApiException.handleException(e).getMessage());
        }
    }


    public abstract void onFailed(String result);

    public abstract void onSuccess(T result);
}
