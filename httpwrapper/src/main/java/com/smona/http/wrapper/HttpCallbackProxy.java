package com.smona.http.wrapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smona.base.http.HttpCallBack;
import com.smona.http.business.BaseResponse;

public class HttpCallbackProxy<K> extends HttpCallBack<K> {
    private static final Gson sGson = new GsonBuilder().disableHtmlEscaping().create();

    private OnResultListener<K> realListener;

    public HttpCallbackProxy(OnResultListener<K> real) {
        this.realListener = real;
    }

    @Override
    public void onSuccess(K data) {
        BaseResponse<?> response;
        if (data instanceof BaseResponse<?>) {
            response = (BaseResponse<?>) data;
            if ("00000".equals(response.code)) {
                if (realListener != null) {
                    realListener.onSuccess(data);
                }
            } else {
                FilterChains.getInstance().exeFilter(response.code);
                if (realListener != null) {
                    realListener.onError(response.code, response.message);
                }
            }
        } else {
            if (realListener != null) {
                realListener.onSuccess(data);
            }
        }
    }

    @Override
    public void onError(String stateCode, String errorInfo) {
        if (realListener != null) {
            realListener.onError(stateCode, errorInfo);
        }
    }
}
