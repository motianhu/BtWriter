package com.smona.btwriter.download.model;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DownloadModel {
    public void get(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder.build();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
