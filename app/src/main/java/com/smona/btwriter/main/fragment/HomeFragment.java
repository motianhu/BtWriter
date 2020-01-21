package com.smona.btwriter.main.fragment;

import com.smona.base.ui.fragment.BasePresenterFragment;
import com.smona.btwriter.R;
import com.smona.btwriter.main.presenter.HomePresenter;

public class HomeFragment extends BasePresenterFragment<HomePresenter, HomePresenter.IHomeView> implements HomePresenter.IHomeView {
    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
