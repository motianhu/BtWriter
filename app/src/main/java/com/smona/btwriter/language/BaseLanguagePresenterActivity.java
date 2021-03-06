package com.smona.btwriter.language;

import android.content.res.Configuration;
import android.os.Bundle;

import com.smona.base.ui.activity.BasePresenterActivity;
import com.smona.base.ui.mvp.BasePresenter;
import com.smona.base.ui.mvp.IView;
import com.smona.btwriter.data.LanuageDataCenter;
import com.smona.btwriter.util.CommonUtil;

import java.util.Locale;

public abstract class BaseLanguagePresenterActivity<P extends BasePresenter<V>, V extends IView> extends BasePresenterActivity<P,V> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLanguage();
    }

    private void setLanguage() {
        String language = LanuageDataCenter.getInstance().getLanuage();
        Locale locale = Locale.ENGLISH;
        if(LanuageDataCenter.SERVER_ZH_CN.equals(language)) {
            locale = Locale.SIMPLIFIED_CHINESE;
        }
        setAppLanguage(locale);
    }
}
