package com.smona.btwriter.purchase;

import com.smona.btwriter.R;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.purchase.presenter.PurchasePresenter;

public class PurchaseActivity extends BaseLoadingPresenterActivity<PurchasePresenter, PurchasePresenter.IPurchaseView> implements PurchasePresenter.IPurchaseView {
    @Override
    protected PurchasePresenter initPresenter() {
        return new PurchasePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_purchase;
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
