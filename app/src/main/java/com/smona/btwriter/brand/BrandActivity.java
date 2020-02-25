package com.smona.btwriter.brand;

import android.support.v7.widget.GridLayoutManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.btwriter.R;
import com.smona.btwriter.brand.adapter.BrandAdapter;
import com.smona.btwriter.brand.bean.BrandBean;
import com.smona.btwriter.brand.presenter.BrandPresenter;
import com.smona.btwriter.common.CommonItemDecoration;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;

import java.util.List;

@Route(path = ARouterPath.PATH_TO_BRAND)
public class BrandActivity extends BaseLoadingPresenterActivity<BrandPresenter, BrandPresenter.IBrandView> implements BrandPresenter.IBrandView {

    private int membraneType = 1;
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
        initSerialize();
        initHeader();
        initViews();
    }

    private void initSerialize() {
        membraneType = getIntent().getIntExtra(ARouterPath.PATH_TO_BRAND, -1);
        if(membraneType == -1) {
            finish();
        }
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(R.string.select_brand);
    }

    private void initViews() {
        xRecyclerView = findViewById(R.id.brandList);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        int margin = getResources().getDimensionPixelSize(R.dimen.dimen_6dp);
        CommonItemDecoration commonItemDecoration = new CommonItemDecoration(margin, margin);
        xRecyclerView.addItemDecoration(commonItemDecoration);
        adapter = new BrandAdapter(R.layout.adapter_item_brand);
        adapter.setMembraneType(membraneType);
        xRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.requestBrandList(membraneType);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        CommonUtil.showToastByFilter(errCode, errInfo);
    }

    @Override
    public void onBrandList(List<BrandBean> brandBeanList) {
        adapter.setNewData(brandBeanList);
    }
}
