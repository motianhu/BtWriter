package com.smona.btwriter;

import android.app.Application;
import android.content.Context;

/**
 * Created by chen on 2018/5/31.
 */
public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
