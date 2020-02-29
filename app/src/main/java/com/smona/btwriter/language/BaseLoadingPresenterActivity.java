package com.smona.btwriter.language;

import android.view.View;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.base.ui.mvp.IView;
import com.smona.btwriter.common.exception.InitExceptionProcess;
import com.smona.btwriter.widget.LoadingResultView;
import com.smona.http.wrapper.ErrorInfo;

public abstract class BaseLoadingPresenterActivity<P extends BasePresenter<V>, V extends IView> extends BaseLanguagePresenterActivity<P, V> {

    private InitExceptionProcess initExceptionProcess;

    @Override
    protected void initContentView() {
        super.initContentView();
        initExceptionProcess = new InitExceptionProcess();
    }

    public void initExceptionProcess(LoadingResultView loadingResultView, View... views) {
        initExceptionProcess.initViews(loadingResultView, views);
    }

    protected void doEmpty() {
        initExceptionProcess.doEmpty();
    }

    protected void doEmpty(String hint, int iconId) {
        initExceptionProcess.doEmpty(hint, iconId);
    }

    protected void doSuccess() {
        initExceptionProcess.doSuccess();
    }

    public void onError(String api, int errCode, ErrorInfo errorInfo, InitExceptionProcess.OnReloadListener listener) {
        initExceptionProcess.doError(api, errCode, errorInfo.getMessage(), listener);
    }

}
