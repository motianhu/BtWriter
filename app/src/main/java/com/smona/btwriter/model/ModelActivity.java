package com.smona.btwriter.model;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.btwriter.R;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.model.adapter.ModelAdapter;
import com.smona.btwriter.model.presenter.ModePresenter;
import com.smona.btwriter.util.ARouterPath;

@Route(path = ARouterPath.PATH_TO_MODEL)
public class ModelActivity extends BaseLoadingPresenterActivity<ModePresenter,ModePresenter.IModeView> implements ModePresenter.IModeView {

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
        initHeader();
        initViews();
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(R.string.select_model);
    }

    private void initViews() {
        xRecyclerView = findViewById(R.id.modelList);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ModelAdapter(R.layout.adapter_item_model);
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
