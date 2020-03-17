package com.smona.btwriter.register;

import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.common.CountTimer;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.register.presenter.RegisterPresetenr;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;

@Route(path = ARouterPath.PATH_TO_REGISTER)
public class RegisterActivity extends BaseLanguagePresenterActivity<RegisterPresetenr, RegisterPresetenr.IRegisterView> implements RegisterPresetenr.IRegisterView {

    private EditText serialNumEt;
    private EditText userEmailEt;
    private EditText codeEt;
    private EditText userPwdEt;
    private EditText userConfirmPwdEt;

    private CheckBox userAgreeCb;

    private TextView getCodeTv;
    private CountTimer countTimer;

    @Override
    protected RegisterPresetenr initPresenter() {
        return new RegisterPresetenr();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
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
        titleTv.setText(R.string.register_account);
    }

    private void initViews() {
        serialNumEt = findViewById(R.id.serial_number);
        CommonUtil.setMaxLenght(serialNumEt, CommonUtil.MAX_NAME_LENGHT);
        userEmailEt = findViewById(R.id.user_email);
        CommonUtil.setMaxLenght(userEmailEt, CommonUtil.MAX_NAME_LENGHT);
        codeEt = findViewById(R.id.email_code);
        userPwdEt = findViewById(R.id.user_password);
        CommonUtil.disableEditTextCopy(userPwdEt);
        CommonUtil.setMaxLenght(userPwdEt, CommonUtil.MAX_PWD_LENGHT);
        userConfirmPwdEt = findViewById(R.id.confirm_password);
        CommonUtil.disableEditTextCopy(userConfirmPwdEt);
        CommonUtil.setMaxLenght(userConfirmPwdEt, CommonUtil.MAX_PWD_LENGHT);

        getCodeTv = findViewById(R.id.getCode);
        getCodeTv.setOnClickListener(v -> clickGetCode());
        findViewById(R.id.btn_register).setOnClickListener(view -> clickRegister());
        findViewById(R.id.user_protocol).setOnClickListener(view -> clickUserAgree());

        userAgreeCb = findViewById(R.id.agree_protocol);

        countTimer = new CountTimer();
        countTimer.setParam(getCodeTv, () -> getCodeTv.setText(R.string.getvercode));
    }

    private void clickGetCode() {
        String email = userEmailEt.getText().toString();
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

    private void clickRegister() {
        String userName = serialNumEt.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            CommonUtil.showShort(this, R.string.empty_serialnum);
            return;
        }
        String email = userEmailEt.getText().toString();
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
        String pwd = userPwdEt.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            CommonUtil.showShort(this, R.string.empty_pwd);
            return;
        }
        if (pwd.length() < 6) {
            CommonUtil.showShort(this, R.string.no_than_pwd);
            return;
        }
        String cpwd = userConfirmPwdEt.getText().toString();
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

        if(!userAgreeCb.isChecked()) {
            CommonUtil.showShort(this, R.string.not_select_agree_protocol);
            return;
        }
        showLoadingDialog();
        mPresenter.requestRegister(userName, email, code, pwd);
    }

    private void clickUserAgree() {
        ARouterManager.getInstance().gotoActivityWithString(ARouterPath.PATH_TO_WEBVIEW, ARouterPath.PATH_TO_WEBVIEW, "http://www.baidu.com");
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
    public void onRegister() {
        hideLoadingDialog();
        ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_LOGIN);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countTimer.cancelTimer();
    }
}
