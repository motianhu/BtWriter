package com.smona.btwriter.register;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.register.presenter.RegisterPresetenr;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;
import com.smona.btwriter.util.ToastUtil;

@Route(path = ARouterPath.PATH_TO_REGISTER)
public class RegisterActivity extends BaseLanguagePresenterActivity<RegisterPresetenr, RegisterPresetenr.IRegisterView> implements RegisterPresetenr.IRegisterView {

    private EditText serialNumEt;
    private EditText userEmailEt;
    private EditText codeEt;
    private EditText userPwdEt;
    private EditText userConfirmPwdEt;

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

        findViewById(R.id.getCode).setOnClickListener(v -> clickGetCode());
        findViewById(R.id.btn_register).setOnClickListener(view -> clickRegister());
    }

    private void clickGetCode() {
        String email = userEmailEt.getText().toString();
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

    private void clickRegister() {
        String userName = serialNumEt.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.showShort(R.string.empty_serialnum);
            return;
        }
        String email = userEmailEt.getText().toString();
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
        String pwd = userPwdEt.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showShort(R.string.empty_pwd);
            return;
        }
        if (pwd.length() < 6) {
            ToastUtil.showShort(R.string.no_than_pwd);
            return;
        }
        String cpwd = userConfirmPwdEt.getText().toString();
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
        mPresenter.requestRegister(userName, email, code, pwd);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        CommonUtil.showToastByFilter(errCode, errInfo);
    }

    @Override
    public void onEmailCode() {
        hideLoadingDialog();
    }

    @Override
    public void onRegister() {
        hideLoadingDialog();
        ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_MAIN);
    }
}
