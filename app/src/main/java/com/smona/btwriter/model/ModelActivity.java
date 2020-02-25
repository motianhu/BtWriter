package com.smona.btwriter.model;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.btwriter.R;
import com.smona.btwriter.common.CommonItemDecoration;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.model.adapter.ModelAdapter;
import com.smona.btwriter.model.bean.ModelParam;
import com.smona.btwriter.model.bean.ModelBean;
import com.smona.btwriter.model.presenter.ModePresenter;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;

import java.util.List;

@Route(path = ARouterPath.PATH_TO_MODEL)
public class ModelActivity extends BaseLoadingPresenterActivity<ModePresenter,ModePresenter.IModeView> implements ModePresenter.IModeView {

    private ModelParam brandParam;

    private XRecyclerView xRecyclerView;
    private ModelAdapter adapter;

    @Override
    protected ModePresenter initPresenter() {
        return new ModePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_model;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initSerialize();
        initHeader();
        initViews();
    }

    private void initSerialize() {
        Bundle bundle = getIntent().getBundleExtra(ARouterPath.PATH_TO_MODEL);
        if(bundle == null) {
            finish();
            return;
        }
        brandParam = (ModelParam)bundle.getSerializable(ModelParam.class.getName());
        if(brandParam == null) {
            finish();
        }
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(R.string.select_model);
    }

    private void initViews() {
        xRecyclerView = findViewById(R.id.modelList);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        int margin = getResources().getDimensionPixelSize(R.dimen.dimen_7dp);
        CommonItemDecoration commonItemDecoration = new CommonItemDecoration(margin, margin);
        xRecyclerView.addItemDecoration(commonItemDecoration);
        adapter = new ModelAdapter(R.layout.adapter_item_model);
        xRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.requestPhoneModelList(brandParam.getBrandId(), brandParam.getMembraneType());
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        CommonUtil.showToastByFilter(errCode, errInfo);
    }

    @Override
    public void onModelList(List<ModelBean> modelBeanList) {
        adapter.setNewData(modelBeanList);
    }
}
