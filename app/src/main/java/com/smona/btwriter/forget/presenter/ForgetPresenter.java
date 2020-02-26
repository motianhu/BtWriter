package com.smona.btwriter.forget.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.common.http.model.EmailCodeModel;
import com.smona.btwriter.forget.bean.ReqResetPwd;
import com.smona.btwriter.forget.model.ForgetPwdModel;
import com.smona.btwriter.common.http.bean.ReqEmailCode;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

public class ForgetPresenter extends BasePresenter<ForgetPresenter.IForgetPwdView> {

    private EmailCodeModel emailCodeModel = new EmailCodeModel();
    private ForgetPwdModel forgetPwdModel = new ForgetPwdModel();

    public void requestEmailCode(String email) {
        ReqEmailCode emailCode = new ReqEmailCode();
        emailCode.setEmail(email);
        emailCodeModel.requestEmailCode(emailCode, new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> respEmptyBaseResponse) {
                if(mView != null) {
                    mView.onEmailCode();
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    mView.onError("requestEmailCode", stateCode, errorInfo);
                }
            }
        });
    }


    public void requestResetPwd(String email, String code, String pwd) {
        ReqResetPwd resetPwd = new ReqResetPwd();
        resetPwd.setEmail(email);
        resetPwd.setCode(code);
        resetPwd.setPassword(pwd);
        forgetPwdModel.requestResetPwd(resetPwd, new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> respEmptyBaseResponse) {
                if(mView != null) {
                    mView.onResetPwd();
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    mView.onError("requestEmailCode", stateCode, errorInfo);
                }
            }
        });
    }

    public interface IForgetPwdView extends ICommonView{
        void onEmailCode();
        void onResetPwd();
    }
}
