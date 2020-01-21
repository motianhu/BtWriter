package com.smona.btwriter.changepwd;

import com.smona.btwriter.R;
import com.smona.btwriter.changepwd.presenter.ChangePwdPresenter;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;

public class ChangePwdActivity extends BaseLanguagePresenterActivity<ChangePwdPresenter,ChangePwdPresenter.IChangePwdView> implements ChangePwdPresenter.IChangePwdView {
    @Override
    protected ChangePwdPresenter initPresenter() {
        return new ChangePwdPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_changepwd;
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
