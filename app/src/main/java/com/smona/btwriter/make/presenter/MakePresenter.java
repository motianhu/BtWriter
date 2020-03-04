package com.smona.btwriter.make.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.base.ui.mvp.IBaseView;
import com.smona.btwriter.download.PltDownload;

public class MakePresenter extends BasePresenter<MakePresenter.IMakeView> {

    private PltDownload downloadPresenter;

    public void downloadPlt(String fileName, String pltUrl, String md5) {
        downloadPresenter = new PltDownload(new PltDownload.IDownloadView() {
            @Override
            public void onStart() {
                if(mView != null) {

                }
            }

            @Override
            public void onProcess(int process) {
                if(mView != null) {

                }
            }

            @Override
            public void onComplete() {
                if(mView != null) {
                    mView.onSuccess();
                }
            }

            @Override
            public void onFailure() {
                if(mView != null) {
                    mView.onFailed();
                }
            }
        });
        downloadPresenter.downloadPlt(fileName, pltUrl, md5);
    }

    public interface IMakeView extends IBaseView {
        void onSuccess();
        void onFailed();
    }
}
