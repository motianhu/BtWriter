package com.smona.btwriter.register;

import com.smona.btwriter.R;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.register.presenter.RegisterPresetenr;

public class RegisterActivity extends BaseLanguagePresenterActivity<RegisterPresetenr, RegisterPresetenr.IRegisterView> implements RegisterPresetenr.IRegisterView {

    @Override
    protected RegisterPresetenr initPresenter() {
        return new RegisterPresetenr();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
