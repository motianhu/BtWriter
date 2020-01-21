package com.smona.btwriter.model;


import com.smona.btwriter.R;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.model.presenter.ModePresenter;

public class ModelActivity extends BaseLoadingPresenterActivity<ModePresenter,ModePresenter.IModeView> implements ModePresenter.IModeView {
    @Override
    protected ModePresenter initPresenter() {
        return new ModePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_model;
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
