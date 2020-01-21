package com.smona.btwriter.brand;

import com.smona.btwriter.R;
import com.smona.btwriter.brand.presenter.BrandPresenter;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;

public class BrandActivity extends BaseLoadingPresenterActivity<BrandPresenter, BrandPresenter.IBrandView> implements BrandPresenter.IBrandView {
    @Override
    protected BrandPresenter initPresenter() {
        return new BrandPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_brand;
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
