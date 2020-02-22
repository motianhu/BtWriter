package com.smona.btwriter.forget;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.forget.presenter.ForgetPresenter;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;

@Route(path = ARouterPath.PATH_TO_FORGETPWD)
public class ForgetPwdActivity extends BaseLanguagePresenterActivity<ForgetPresenter, ForgetPresenter.IForgetPwdView> implements ForgetPresenter.IForgetPwdView {

    private View codeView;
    private EditText emailEt;
    private EditText codeEt;

    private View resetView;
    private EditText resetEmailEt;
    private EditText resetPwdEt;
    private EditText resetCpwdEt;

    @Override
    protected ForgetPresenter initPresenter() {
        return new ForgetPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgetpwd;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initHeader();
        initViews();
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(R.string.forget_pwd);
    }

    private void initViews() {
        findViewById(R.id.next_step).setOnClickListener(view -> clickSendCode());
    }

    private void clickSendCode(){

    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
