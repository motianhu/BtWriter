package com.smona.btwriter.changepwd.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.changepwd.bean.ReqChangePwd;
import com.smona.btwriter.changepwd.model.ChangePwdModel;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

public class ChangePwdPresenter extends BasePresenter<ChangePwdPresenter.IChangePwdView> {
    private ChangePwdModel changePwdModel = new ChangePwdModel();

    public void requestChangePwd(String pwd) {
        ReqChangePwd changePwd = new ReqChangePwd();
        changePwd.setPassword(pwd);
        changePwdModel.requestChangePwd(changePwd, new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> respEmptyBaseResponse) {
                if(mView!= null) {
                    mView.onChangePwd();
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView!= null) {
                    mView.onError("requestChangePwd", stateCode, errorInfo);
                }
            }
        });
    }

    public interface IChangePwdView extends ICommonView{
        void onChangePwd();
    }
}
