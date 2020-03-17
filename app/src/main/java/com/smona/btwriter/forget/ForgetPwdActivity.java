package com.smona.btwriter.forget;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.common.CountTimer;
import com.smona.btwriter.forget.presenter.ForgetPresenter;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;

@Route(path = ARouterPath.PATH_TO_FORGETPWD)
public class ForgetPwdActivity extends BaseLanguagePresenterActivity<ForgetPresenter, ForgetPresenter.IForgetPwdView> implements ForgetPresenter.IForgetPwdView {

    private EditText emailEt;
    private EditText codeEt;
    private EditText resetPwdEt;
    private EditText resetCpwdEt;

    private TextView getCodeTv;
    private CountTimer countTimer;

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
        getCodeTv = findViewById(R.id.getCode);
        getCodeTv.setOnClickListener(view -> clickSendCode());
        findViewById(R.id.submitTv).setOnClickListener(view->clickSubmit());
        emailEt = findViewById(R.id.user_email);
        codeEt = findViewById(R.id.email_code);
        resetPwdEt = findViewById(R.id.user_password);
        resetCpwdEt = findViewById(R.id.confirm_password);

        countTimer = new CountTimer();
        countTimer.setParam(getCodeTv, () -> getCodeTv.setText(R.string.getvercode));
    }

    private void clickSubmit() {
        String email = emailEt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            CommonUtil.showShort(this, R.string.empty_email);
            return;
        }
        if (!CommonUtil.isEmail(email)) {
            CommonUtil.showShort(this, R.string.invalid_email);
            return;
        }
        String code = codeEt.getText().toString();
        if (TextUtils.isEmpty(code)) {
            CommonUtil.showShort(this, R.string.empty_code);
            return;
        }
        String pwd = resetPwdEt.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            CommonUtil.showShort(this, R.string.empty_pwd);
            return;
        }
        if (pwd.length() < 6) {
            CommonUtil.showShort(this, R.string.no_than_pwd);
            return;
        }
        String cpwd = resetCpwdEt.getText().toString();
        if (TextUtils.isEmpty(cpwd)) {
            CommonUtil.showShort(this, R.string.empty_cpwd);
            return;
        }
        if (cpwd.length() < 6) {
            CommonUtil.showShort(this, R.string.no_than_c_pwd);
            return;
        }
        if (!pwd.equals(cpwd)) {
            CommonUtil.showShort(this, R.string.not_pwd_common);
            return;
        }
        showLoadingDialog();
        mPresenter.requestResetPwd(email, code, pwd);
    }

    private void clickSendCode(){
        String email = emailEt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            CommonUtil.showShort(this, R.string.empty_email);
            return;
        }
        if (!CommonUtil.isEmail(email)) {
            CommonUtil.showShort(this, R.string.invalid_email);
            return;
        }
        showLoadingDialog();
        mPresenter.requestEmailCode(email);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        CommonUtil.showToastByFilter(this, errCode, errInfo);
    }

    @Override
    public void onEmailCode() {
        hideLoadingDialog();
        countTimer.start();
        CommonUtil.showShort(this, R.string.email_send_success);
    }

    @Override
    public void onResetPwd() {
        hideLoadingDialog();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countTimer.cancelTimer();
    }
}
