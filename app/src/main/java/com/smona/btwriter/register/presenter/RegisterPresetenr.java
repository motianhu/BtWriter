package com.smona.btwriter.register.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.common.http.bean.ReqEmailCode;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.common.http.model.EmailCodeModel;
import com.smona.btwriter.register.bean.ReqRegister;
import com.smona.btwriter.register.model.RegisterModel;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

public class RegisterPresetenr extends BasePresenter<RegisterPresetenr.IRegisterView> {

    private EmailCodeModel emailCodeModel = new EmailCodeModel();
    private RegisterModel registerModel = new RegisterModel();

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

    public void requestRegister( String userName, String email, String code, String pwd) {
        ReqRegister reqRegister = new ReqRegister();
        reqRegister.setSerialNo(userName);
        reqRegister.setEmail(email);
        reqRegister.setCode(code);
        reqRegister.setPassword(pwd);
        registerModel.requestRegister(reqRegister, new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> respEmptyBaseResponse) {
                if(mView != null) {
                    mView.onRegister();
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    mView.onError("requestRegister", stateCode, errorInfo);
                }
            }
        });
    }

    public interface IRegisterView extends ICommonView {
        void onEmailCode();
        void onRegister();
    }
}
