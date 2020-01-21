package com.smona.btwriter.main.fragment;

import com.smona.base.ui.fragment.BasePresenterFragment;
import com.smona.btwriter.R;
import com.smona.btwriter.main.presenter.ParamPresenter;

public class ParamFragment extends BasePresenterFragment<ParamPresenter, ParamPresenter.IParamView> implements ParamPresenter.IParamView {
    @Override
    protected ParamPresenter initPresenter() {
        return new ParamPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_param;
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
