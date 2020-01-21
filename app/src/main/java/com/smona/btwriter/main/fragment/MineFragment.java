package com.smona.btwriter.main.fragment;

import com.smona.base.ui.fragment.BasePresenterFragment;
import com.smona.btwriter.R;
import com.smona.btwriter.main.presenter.MinePresenter;

public class MineFragment extends BasePresenterFragment<MinePresenter, MinePresenter.IMineView> implements MinePresenter.IMineView {
    @Override
    protected MinePresenter initPresenter() {
        return new MinePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
