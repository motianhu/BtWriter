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
import com.smona.btwriter.util.ToastUtil;

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
        ToastUtil.showShort(R.string.change_pwd_success);
        finish();
    }
}
