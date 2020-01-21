package com.smona.btwriter.login;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.login.presenter.LoginPresenter;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;
import com.smona.btwriter.util.ToastUtil;

@Route(path = ARouterPath.PATH_TO_LOGIN)
public class LoginActivity extends BaseLanguagePresenterActivity<LoginPresenter, LoginPresenter.ILoginView> implements LoginPresenter.ILoginView {

    //popwindow
    private PopupWindow popupWindow;

    private EditText emailEt;
    private EditText emailPwd;
    private TextView languageTv;

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initViews();
    }

    private void initViews() {
        findViewById(R.id.forget_pwd).setOnClickListener(view -> ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_FORGETPWD));
        findViewById(R.id.goRegister).setOnClickListener(view -> ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_REGISTER));
        findViewById(R.id.btn_login).setOnClickListener(view -> clickLogin(emailEt.getText().toString(), emailPwd.getText().toString()));
        languageTv = findViewById(R.id.switchLanguage);
        languageTv.setOnClickListener(view -> clickSwitchLanguage());

        emailEt = findViewById(R.id.et_input_email);
        CommonUtil.setMaxLenght(emailEt, CommonUtil.MAX_NAME_LENGHT);
        emailPwd = findViewById(R.id.et_input_password);
        CommonUtil.disableEditTextCopy(emailPwd);
        CommonUtil.setMaxLenght(emailPwd, CommonUtil.MAX_PWD_LENGHT);
    }

    private void clickSwitchLanguage() {
        if (popupWindow == null) {
            initPopwindow();
        }
        popupWindow.showAsDropDown(languageTv, 0, 0, Gravity.START | Gravity.BOTTOM);
    }

    private void initPopwindow() {
        popupWindow = new PopupWindow();
        View popContentView = View.inflate(this, R.layout.layout_language_list, null);
        popupWindow.setContentView(popContentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        popContentView.findViewById(R.id.chinese).setOnClickListener(v -> {
            popupWindow.dismiss();
        });
        popContentView.findViewById(R.id.english).setOnClickListener(v -> {
            popupWindow.dismiss();
        });
    }

    private void clickLogin(String email, String pwd) {
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
        showLoadingDialog();
        mPresenter.login(emailEt.getText().toString(), emailPwd.getText().toString());
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }

    @Override
    public void onLoginSuccess() {
        ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_MAIN);
    }
}
