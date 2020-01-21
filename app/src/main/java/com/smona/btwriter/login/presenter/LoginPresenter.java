package com.smona.btwriter.login.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.login.model.LoginModel;

public class LoginPresenter extends BasePresenter<LoginPresenter.ILoginView> {

    private LoginModel loginModel = new LoginModel();

    public interface ILoginView extends ICommonView {

    }

}
