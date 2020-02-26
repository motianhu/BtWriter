package com.smona.btwriter.param.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.main.bean.ParamInfo;
import com.smona.btwriter.main.model.ParamModel;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

public class ParamPresenter extends BasePresenter<ParamPresenter.IParamView> {

    private ParamModel paramModel = new ParamModel();

    public void requestSaveParam(ParamInfo paramInfo) {
        paramModel.requestSaveParam(paramInfo, new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> respEmptyBaseResponse) {
                if(mView!=null) {
                    mView.onParamSave();
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView!=null) {
                    mView.onError("requestSaveParam", stateCode, errorInfo);
                }
            }
        });
    }

    public interface IParamView extends ICommonView {
        void onParamSave();
    }
}
