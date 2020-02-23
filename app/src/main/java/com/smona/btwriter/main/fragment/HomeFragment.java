package com.smona.btwriter.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.base.ui.fragment.BasePresenterFragment;
import com.smona.btwriter.R;
import com.smona.btwriter.common.CommonItemDecoration;
import com.smona.btwriter.main.adapter.MembraneTypeAdapter;
import com.smona.btwriter.main.bean.MembraneBean;
import com.smona.btwriter.main.presenter.HomePresenter;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

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
        xRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);

        int margin = getResources().getDimensionPixelSize(R.dimen.dimen_14dp);
        CommonItemDecoration ex = new CommonItemDecoration(0, margin, 0);
        xRecyclerView.addItemDecoration(ex);

        adapter = new MembraneTypeAdapter(R.layout.adapter_item_membranetype);
        adapter.setNewData(buildMembraneList());
        xRecyclerView.setAdapter(adapter);

        content.findViewById(R.id.scanView).setOnClickListener(v -> clickScan());
    }

    @Override
    protected void initData() {
        super.initData();
    }

    private List<MembraneBean> buildMembraneList() {
        List<MembraneBean> data = new ArrayList<>();
        MembraneBean membraneBean = new MembraneBean();
        membraneBean.setResId(R.drawable.add_icon);
        membraneBean.setTitle(getString(R.string.home_phone_full));
        data.add(membraneBean);

        membraneBean = new MembraneBean();
        membraneBean.setResId(R.drawable.add_icon);
        membraneBean.setTitle(getString(R.string.home_phone_shell));
        data.add(membraneBean);

        membraneBean = new MembraneBean();
        membraneBean.setResId(R.drawable.add_icon);
        membraneBean.setTitle(getString(R.string.home_phone_behind));
        data.add(membraneBean);

        return data;
    }

    private void clickScan() {
        ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_SCAN);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
