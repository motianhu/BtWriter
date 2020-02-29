package com.smona.btwriter.login.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.data.AccountDataCenter;
import com.smona.btwriter.login.bean.ReqLogin;
import com.smona.btwriter.login.bean.RespLogin;
import com.smona.btwriter.login.model.LoginModel;
import com.smona.btwriter.util.SPUtils;
import com.smona.http.business.BaseResponse;
import com.smona.http.config.GsonUtil;
import com.smona.http.wrapper.OnResultListener;

public class LoginPresenter extends BasePresenter<LoginPresenter.ILoginView> {

    private LoginModel loginModel = new LoginModel();

    public void login(String email, String pwd) {
        ReqLogin reqLogin = new ReqLogin();
        reqLogin.setEmail(email);
        reqLogin.setPassword(pwd);
        loginModel.login(reqLogin, new OnResultListener<BaseResponse<RespLogin>>() {
            @Override
            public void onSuccess(BaseResponse<RespLogin> response) {
                if(mView!= null) {
                    AccountDataCenter.getInstance().setAccountInfo(email, response.data.getToken());
                    SPUtils.put(SPUtils.LOGIN_INFO, GsonUtil.objToJson(AccountDataCenter.getInstance().getAccountInfo()));
                    mView.onLoginSuccess();
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView!= null) {
                    mView.onError("login", stateCode, errorInfo);
                }
            }
        });
    }

    public interface ILoginView extends ICommonView {
        void onLoginSuccess();
    }

}
