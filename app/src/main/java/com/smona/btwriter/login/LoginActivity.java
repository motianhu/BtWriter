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
import com.smona.btwriter.data.LanuageDataCenter;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.login.presenter.LoginPresenter;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;
import com.smona.btwriter.util.SPUtils;

import java.util.Locale;

@Route(path = ARouterPath.PATH_TO_LOGIN)
public class LoginActivity extends BaseLanguagePresenterActivity<LoginPresenter, LoginPresenter.ILoginView> implements LoginPresenter.ILoginView {

    //popwindow
    private PopupWindow popupWindow;

    private TextView welcomeTv;
    private TextView languageTv;

    private EditText emailEt;
    private EditText emailPwd;

    private TextView forgetTv;
    private TextView loginTv;

    private TextView guideRegisterTv;
    private TextView goRegisterTv;

    private TextView chinaTv;
    private TextView englishTv;

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
        welcomeTv = findViewById(R.id.welcome);
        forgetTv = findViewById(R.id.forget_pwd);
        forgetTv.setOnClickListener(view -> ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_FORGETPWD));
        findViewById(R.id.goRegister).setOnClickListener(view -> ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_REGISTER));
        loginTv = findViewById(R.id.btn_login);
        loginTv.setOnClickListener(view -> clickLogin(emailEt.getText().toString(), emailPwd.getText().toString()));
        languageTv = findViewById(R.id.switchLanguage);
        languageTv.setOnClickListener(view -> clickSwitchLanguage());
        guideRegisterTv = findViewById(R.id.guide_register);
        goRegisterTv = findViewById(R.id.goRegister);

        emailEt = findViewById(R.id.et_input_email);
        CommonUtil.setMaxLenght(emailEt, CommonUtil.MAX_NAME_LENGHT);
        String email = (String)SPUtils.get(SPUtils.LAST_ACCOUNT, "");
        if(!TextUtils.isEmpty(email)) {
            emailEt.setText(email);
        }
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

        chinaTv = popContentView.findViewById(R.id.chinese);
        chinaTv.setOnClickListener(v -> {
            popupWindow.dismiss();
            LanuageDataCenter.getInstance().saveLanuage(LanuageDataCenter.SERVER_ZH_CN);
            applyLanguage(LanuageDataCenter.LOCALE_ZH_CN);
        });
        englishTv = popContentView.findViewById(R.id.english);
        englishTv.setOnClickListener(v -> {
            popupWindow.dismiss();
            LanuageDataCenter.getInstance().saveLanuage(LanuageDataCenter.SERVER_EN_US);
            applyLanguage(LanuageDataCenter.LOCALE_EN);
        });
    }

    private void clickLogin(String email, String pwd) {
        if (TextUtils.isEmpty(email)) {
            CommonUtil.showShort(this, R.string.empty_email);
            return;
        }
        if (!CommonUtil.isEmail(email)) {
            CommonUtil.showShort(this, R.string.invalid_email);
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            CommonUtil.showShort(this, R.string.empty_pwd);
            return;
        }
        if (pwd.length() < 6) {
            CommonUtil.showShort(this, R.string.no_than_pwd);
            return;
        }
        showLoadingDialog();
        mPresenter.login(emailEt.getText().toString(), emailPwd.getText().toString());
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        CommonUtil.showToastByFilter(this, errCode, errInfo);
    }

    @Override
    public void onLoginSuccess() {
        hideLoadingDialog();
        ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_MAIN);
        finish();
    }

    private void applyLanguage(String locale) {
        if (LanuageDataCenter.LOCALE_ZH_CN.equals(locale)) {
            setAppLanguage(Locale.SIMPLIFIED_CHINESE);
        } else {
            setAppLanguage(Locale.ENGLISH);
        }
        refreshViews();
    }

    private void refreshViews() {
        welcomeTv.setText(R.string.welcome);
        languageTv.setText(R.string.switch_language);
        emailEt.setHint(R.string.email);
        emailPwd.setHint(R.string.password);


        forgetTv.setText(R.string.guide_forgetpwd);
        loginTv.setText(R.string.login);

        guideRegisterTv.setText(R.string.guide_register);
        goRegisterTv.setText(R.string.guide_register1);

        chinaTv.setText(R.string.chinese);
        englishTv.setText(R.string.english);
    }

}
