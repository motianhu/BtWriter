package com.smona.btwriter.forget;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.forget.presenter.ForgetPresenter;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;
import com.smona.btwriter.util.ToastUtil;

@Route(path = ARouterPath.PATH_TO_FORGETPWD)
public class ForgetPwdActivity extends BaseLanguagePresenterActivity<ForgetPresenter, ForgetPresenter.IForgetPwdView> implements ForgetPresenter.IForgetPwdView {

    private EditText emailEt;
    private EditText codeEt;
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
        findViewById(R.id.getCode).setOnClickListener(view -> clickSendCode());
        findViewById(R.id.submitTv).setOnClickListener(view->clickSubmit());
        emailEt = findViewById(R.id.user_email);
        codeEt = findViewById(R.id.email_code);
        resetPwdEt = findViewById(R.id.user_password);
        resetCpwdEt = findViewById(R.id.confirm_password);
    }

    private void clickSubmit() {
        String email = emailEt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            ToastUtil.showShort(R.string.empty_email);
            return;
        }
        if (!CommonUtil.isEmail(email)) {
            ToastUtil.showShort(R.string.invalid_email);
            return;
        }
        String code = codeEt.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showShort(R.string.empty_code);
            return;
        }
        String pwd = resetPwdEt.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showShort(R.string.empty_pwd);
            return;
        }
        if (pwd.length() < 6) {
            ToastUtil.showShort(R.string.no_than_pwd);
            return;
        }
        String cpwd = resetCpwdEt.getText().toString();
        if (TextUtils.isEmpty(cpwd)) {
            ToastUtil.showShort(R.string.empty_cpwd);
            return;
        }
        if (cpwd.length() < 6) {
            ToastUtil.showShort(R.string.no_than_c_pwd);
            return;
        }
        if (!pwd.equals(cpwd)) {
            ToastUtil.showShort(R.string.not_pwd_common);
            return;
        }
        showLoadingDialog();
        mPresenter.requestResetPwd(email, code, pwd);
    }

    private void clickSendCode(){
        String email = emailEt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            ToastUtil.showShort(R.string.empty_email);
            return;
        }
        if (!CommonUtil.isEmail(email)) {
            ToastUtil.showShort(R.string.invalid_email);
            return;
        }
        showLoadingDialog();
        mPresenter.requestEmailCode(email);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        CommonUtil.showToastByFilter(errCode, errInfo);
    }

    @Override
    public void onEmailCode() {
        hideLoadingDialog();
        ToastUtil.showShort(R.string.email_send_success);
    }

    @Override
    public void onResetPwd() {
        hideLoadingDialog();
        finish();
    }
}
