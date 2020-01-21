package com.smona.btwriter.purchase;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.btwriter.R;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.purchase.adapter.PurchaseAdapter;
import com.smona.btwriter.purchase.presenter.PurchasePresenter;
import com.smona.btwriter.util.ARouterPath;

@Route(path = ARouterPath.PATH_TO_PURCHASELIST)
public class PurchaseActivity extends BaseLoadingPresenterActivity<PurchasePresenter, PurchasePresenter.IPurchaseView> implements PurchasePresenter.IPurchaseView {

    private XRecyclerView xRecyclerView;
    private PurchaseAdapter adapter;

    @Override
    protected PurchasePresenter initPresenter() {
        return new PurchasePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_purchase;
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
        titleTv.setText(R.string.purchase);
    }

    private void initViews() {
        xRecyclerView = findViewById(R.id.purchaseList);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PurchaseAdapter(R.layout.adapter_item_message);
        xRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
