package com.smona.btwriter.forget;

import com.smona.btwriter.R;
import com.smona.btwriter.forget.presenter.ForgetPresenter;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;

public class ForgetPwdActivity extends BaseLanguagePresenterActivity<ForgetPresenter, ForgetPresenter.IForgetPwdView> implements ForgetPresenter.IForgetPwdView {
    @Override
    protected ForgetPresenter initPresenter() {
        return new ForgetPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgetpwd;
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
