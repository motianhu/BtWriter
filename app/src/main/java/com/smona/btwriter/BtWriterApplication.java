package com.smona.btwriter;

import android.app.Application;

import com.smona.btwriter.common.exception.AppContext;

public class BtWriterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.setAppContext(this);
    }
}
