package com.smona.btwriter.make.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.base.ui.mvp.IBaseView;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.download.PltDownload;
import com.smona.btwriter.make.model.MakeModel;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

public class MakePresenter extends BasePresenter<MakePresenter.IMakeView> {

    private MakeModel makeModel = new MakeModel();

    public void requestMakeSuccess() {
        makeModel.requestMakeSuccess(new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> respEmptyBaseResponse) {
                if(mView != null) {
                    mView.onMakeSuccess();
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    mView.onError("requestMakeSuccess", stateCode, errorInfo);
                }
            }
        });
    }

    public void requestCheckMake() {
        makeModel.requestCheckMake(new OnResultListener<BaseResponse<Integer>>() {
            @Override
            public void onSuccess(BaseResponse<Integer> response) {
                if(mView != null) {
                    mView.onCheck(response.data != null && response.data == 1);
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    mView.onError("requestCheckMake", stateCode, errorInfo);
                }
            }
        });
    }

    public void downloadPlt(String fileName, String pltUrl, String md5) {
        PltDownload downloadPresenter = new PltDownload(new PltDownload.IDownloadView() {
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
                    mView.onDownload();
                }
            }

            @Override
            public void onFailure() {
                if(mView != null) {
                    mView.onError("downloadPlt", "", "");
                }
            }
        });
        downloadPresenter.downloadPlt(fileName, pltUrl, md5);
    }

    public interface IMakeView extends ICommonView {
        void onDownload();
        void onMakeSuccess();
        void onCheck(boolean avaliable);
    }
}
