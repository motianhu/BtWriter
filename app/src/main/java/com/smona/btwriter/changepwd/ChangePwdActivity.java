package com.smona.btwriter.changepwd;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.changepwd.presenter.ChangePwdPresenter;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;

@Route(path = ARouterPath.PATH_TO_CHANGEPWD)
public class ChangePwdActivity extends BaseLanguagePresenterActivity<ChangePwdPresenter,ChangePwdPresenter.IChangePwdView> implements ChangePwdPresenter.IChangePwdView {

    private EditText resetPwdEt;
    private EditText resetCpwdEt;

    @Override
    protected ChangePwdPresenter initPresenter() {
        return new ChangePwdPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_changepwd;
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
        titleTv.setText(R.string.changepwd);
    }

    private void initViews() {
        findViewById(R.id.submitTv).setOnClickListener(view->clickSubmit());
        resetPwdEt = findViewById(R.id.user_password);
        resetCpwdEt = findViewById(R.id.confirm_password);
    }

    private void clickSubmit() {
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
        mPresenter.requestChangePwd(pwd);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        CommonUtil.showToastByFilter(this, errCode, errInfo);
    }

    @Override
    public void onChangePwd() {
        hideLoadingDialog();
        CommonUtil.showShort(this, R.string.change_pwd_success);
        finish();
    }
}
