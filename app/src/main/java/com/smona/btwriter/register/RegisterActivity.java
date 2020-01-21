package com.smona.btwriter.register;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.register.presenter.RegisterPresetenr;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;
import com.smona.btwriter.util.ToastUtil;

@Route(path = ARouterPath.PATH_TO_REGISTER)
public class RegisterActivity extends BaseLanguagePresenterActivity<RegisterPresetenr, RegisterPresetenr.IRegisterView> implements RegisterPresetenr.IRegisterView {

    private EditText serialNumEt;
    private EditText userEmailEt;
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
        titleTv.setText(R.string.register);
    }

    private void initViews() {
        serialNumEt = findViewById(R.id.serial_number);
        CommonUtil.setMaxLenght(serialNumEt, CommonUtil.MAX_NAME_LENGHT);
        userEmailEt = findViewById(R.id.user_email);
        CommonUtil.setMaxLenght(userEmailEt, CommonUtil.MAX_NAME_LENGHT);
        userPwdEt = findViewById(R.id.user_password);
        CommonUtil.disableEditTextCopy(userPwdEt);
        CommonUtil.setMaxLenght(userPwdEt, CommonUtil.MAX_PWD_LENGHT);
        userConfirmPwdEt = findViewById(R.id.confirm_password);
        CommonUtil.disableEditTextCopy(userConfirmPwdEt);
        CommonUtil.setMaxLenght(userConfirmPwdEt, CommonUtil.MAX_PWD_LENGHT);

        findViewById(R.id.btn_register).setOnClickListener(view -> clickRegister(serialNumEt.getText().toString(), userEmailEt.getText().toString(), userPwdEt.getText().toString(), userConfirmPwdEt.getText().toString()));
    }

    private void clickRegister(String userName, String email, String pwd, String cpwd) {
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.showShort(R.string.empty_serialnum);
            return;
        }
        if (TextUtils.isEmpty(email)) {
            ToastUtil.showShort(R.string.empty_email);
            return;
        }
        if (!CommonUtil.isEmail(email)) {
            ToastUtil.showShort(R.string.invalid_email);
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showShort(R.string.empty_pwd);
            return;
        }
        if (pwd.length() < 8) {
            ToastUtil.showShort(R.string.no_than_pwd);
            return;
        }
        if (TextUtils.isEmpty(cpwd)) {
            ToastUtil.showShort(R.string.empty_cpwd);
            return;
        }
        if (cpwd.length() < 8) {
            ToastUtil.showShort(R.string.no_than_c_pwd);
            return;
        }
        if (!pwd.equals(cpwd)) {
            ToastUtil.showShort(R.string.not_pwd_common);
            return;
        }
        showLoadingDialog();
        mPresenter.register(userName, email, pwd, cpwd);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
