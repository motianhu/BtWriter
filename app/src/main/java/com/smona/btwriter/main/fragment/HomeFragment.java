package com.smona.btwriter.main.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.base.ui.fragment.BasePresenterFragment;
import com.smona.btwriter.R;
import com.smona.btwriter.common.CommonItemDecoration;
import com.smona.btwriter.main.adapter.MembraneTypeAdapter;
import com.smona.btwriter.main.bean.BannerBean;
import com.smona.btwriter.main.bean.MembraneBean;
import com.smona.btwriter.main.bean.MembraneType;
import com.smona.btwriter.main.bean.RespHomeBean;
import com.smona.btwriter.main.presenter.HomePresenter;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;
import com.smona.image.loader.ImageLoaderDelegate;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BasePresenterFragment<HomePresenter, HomePresenter.IHomeView> implements HomePresenter.IHomeView {

    private TextView matchBluetoothStatusTv;
    private Banner bannerView;
    private TextView totalCutTimesTv;
    private TextView remainCutTimesTv;

    private XRecyclerView xRecyclerView;
    private MembraneTypeAdapter adapter;

    private List<String> bannerImageList = new ArrayList<>();

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
        bannerView.setDelayTime(3000).isAutoPlay(true).setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        }).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                ImageLoaderDelegate.getInstance().showCornerImage((String)path, imageView, getResources().getDimensionPixelOffset(R.dimen.dimen_10dp), 0);
            }
        }).setIndicatorGravity(BannerConfig.CENTER);

        totalCutTimesTv = content.findViewById(R.id.total_times);
        remainCutTimesTv = content.findViewById(R.id.remind_times);

        xRecyclerView = content.findViewById(R.id.membraneType);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);

        int margin = getResources().getDimensionPixelSize(R.dimen.dimen_7dp);
        CommonItemDecoration ex = new CommonItemDecoration(0, margin);
        xRecyclerView.addItemDecoration(ex);

        adapter = new MembraneTypeAdapter(R.layout.adapter_item_membranetype);
        adapter.setNewData(buildMembraneList());
        xRecyclerView.setAdapter(adapter);

        content.findViewById(R.id.scanView).setOnClickListener(v -> clickScan());
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.requestHome();
    }

    private List<MembraneBean> buildMembraneList() {
        List<MembraneBean> data = new ArrayList<>();
        MembraneBean membraneBean = new MembraneBean();
        membraneBean.setResId(R.drawable.phone_preview);
        membraneBean.setType(MembraneType.FULL_TYPE);
        membraneBean.setTitle(getString(R.string.home_phone_full));
        data.add(membraneBean);

        membraneBean = new MembraneBean();
        membraneBean.setResId(R.drawable.phone_shell);
        membraneBean.setType(MembraneType.SHELL_TYPE);
        membraneBean.setTitle(getString(R.string.home_phone_shell));
        data.add(membraneBean);

        membraneBean = new MembraneBean();
        membraneBean.setResId(R.drawable.phone_behind);
        membraneBean.setType(MembraneType.BEHIND_TYPE);
        membraneBean.setTitle(getString(R.string.home_phone_behind));
        data.add(membraneBean);

        return data;
    }

    private void refreshViews(RespHomeBean homeBean) {
        String numUnit = getString(R.string.num_unit);
        totalCutTimesTv.setText(homeBean.getUseAmount() + numUnit);
        remainCutTimesTv.setText(homeBean.getUnUseAmount() + numUnit);

        bannerImageList.clear();
        bannerImageList.addAll(homeBean.getAdList());
        bannerView.setImages(homeBean.getAdList()).start();
    }

    private void clickScan() {
        ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_SCAN);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        CommonUtil.showToastByFilter(errCode, errInfo);
    }

    @Override
    public void onHome(RespHomeBean homeBean) {
        refreshViews(homeBean);
    }
}
