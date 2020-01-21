package com.smona.btwriter.brand;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.btwriter.R;
import com.smona.btwriter.brand.adapter.BrandAdapter;
import com.smona.btwriter.brand.presenter.BrandPresenter;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.util.ARouterPath;

@Route(path = ARouterPath.PATH_TO_BRAND)
public class BrandActivity extends BaseLoadingPresenterActivity<BrandPresenter, BrandPresenter.IBrandView> implements BrandPresenter.IBrandView {

    private XRecyclerView xRecyclerView;
    private BrandAdapter adapter;

    @Override
    protected BrandPresenter initPresenter() {
        return new BrandPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_brand;
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
        titleTv.setText(R.string.select_brand);
    }

    private void initViews() {
        xRecyclerView = findViewById(R.id.brandList);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BrandAdapter(R.layout.adapter_item_brand);
        xRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }

}
