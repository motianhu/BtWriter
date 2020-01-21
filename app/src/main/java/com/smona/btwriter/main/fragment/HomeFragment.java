package com.smona.btwriter.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.base.ui.fragment.BasePresenterFragment;
import com.smona.btwriter.R;
import com.smona.btwriter.main.adapter.MembraneTypeAdapter;
import com.smona.btwriter.main.presenter.HomePresenter;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.youth.banner.Banner;

public class HomeFragment extends BasePresenterFragment<HomePresenter, HomePresenter.IHomeView> implements HomePresenter.IHomeView {

    private TextView matchBluetoothStatusTv;
    private Banner bannerView;
    private TextView totalCutTimesTv;
    private TextView remainCutTimesTv;

    private XRecyclerView xRecyclerView;
    private MembraneTypeAdapter adapter;

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View content) {
        super.initView(content);
        matchBluetoothStatusTv = content.findViewById(R.id.bluetoothStatus);
        bannerView = content.findViewById(R.id.banner);
        totalCutTimesTv = content.findViewById(R.id.total_times);
        remainCutTimesTv = content.findViewById(R.id.remind_times);

        xRecyclerView = content.findViewById(R.id.membraneType);
        adapter = new MembraneTypeAdapter(R.layout.adapter_item_membranetype);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        xRecyclerView.setAdapter(adapter);

        content.findViewById(R.id.scanView).setOnClickListener(v -> clickScan());

    }

    @Override
    protected void initData() {
        super.initData();
    }

    private void clickScan() {
        ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_SCAN);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
