package com.smona.btwriter.main.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.base.ui.fragment.BasePresenterFragment;
import com.smona.btwriter.MainActivity;
import com.smona.btwriter.R;
import com.smona.btwriter.bluetooth.BluetoothDataCenter;
import com.smona.btwriter.common.CommonItemDecoration;
import com.smona.btwriter.main.adapter.MembraneTypeAdapter;
import com.smona.btwriter.main.bean.AdBean;
import com.smona.btwriter.main.bean.MembraneBean;
import com.smona.btwriter.main.bean.MembraneType;
import com.smona.btwriter.main.bean.RespHomeBean;
import com.smona.btwriter.main.presenter.HomePresenter;
import com.smona.btwriter.notify.NotifyCenter;
import com.smona.btwriter.notify.event.BluetoothChangeEvent;
import com.smona.btwriter.notify.event.CountChangeEvent;
import com.smona.btwriter.scan.ScanActivity;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;
import com.smona.image.loader.ImageLoaderDelegate;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BasePresenterFragment<HomePresenter, HomePresenter.IHomeView> implements HomePresenter.IHomeView {

    private TextView matchBluetoothStatusTv;
    private Banner bannerView;
    private TextView totalCutTimesTv;
    private TextView remainCutTimesTv;

    private XRecyclerView xRecyclerView;
    private MembraneTypeAdapter adapter;

    private List<AdBean> bannerImageList = new ArrayList<>();

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        View header = View.inflate(getContext(), R.layout.home_header, null);

        xRecyclerView = root.findViewById(R.id.membraneType);
        xRecyclerView.addHeaderView(header);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        int margin = getResources().getDimensionPixelSize(R.dimen.dimen_5dp);
        CommonItemDecoration ex = new CommonItemDecoration(0, margin);
        xRecyclerView.addItemDecoration(ex);
        adapter = new MembraneTypeAdapter(R.layout.adapter_item_membranetype);
        adapter.setNewData(buildMembraneList());
        xRecyclerView.setAdapter(adapter);

        matchBluetoothStatusTv = header.findViewById(R.id.bluetoothStatus);
        matchBluetoothStatusTv.setOnClickListener(v-> clickMatch());
        refreshBluetoothStatus();

        bannerView = header.findViewById(R.id.banner);
        bannerView.setDelayTime(3000).isAutoPlay(true).setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ARouterManager.getInstance().gotoActivityWithString(ARouterPath.PATH_TO_WEBVIEW, ARouterPath.PATH_TO_WEBVIEW, bannerImageList.get(position).getAdUrl());
            }
        }).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                ImageLoaderDelegate.getInstance().showCornerImage(((AdBean)path).getAdImageUrl(), imageView, getResources().getDimensionPixelOffset(R.dimen.dimen_10dp), 0);
            }
        }).setIndicatorGravity(BannerConfig.CENTER);

        totalCutTimesTv = header.findViewById(R.id.total_times);
        remainCutTimesTv = header.findViewById(R.id.remind_times);

        header.findViewById(R.id.scanView).setOnClickListener(v -> clickScan());

        NotifyCenter.getInstance().registerListener(this);
    }

    private void clickMatch() {
        //ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_BLUETOOTH_LIST);
        //ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_SPP);
        ((MainActivity)mActivity).switchSettingFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        NotifyCenter.getInstance().unRegisterListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        requestHome();
    }

    private void requestHome() {
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
        Intent intent = new Intent();
        intent.setClass(mActivity, ScanActivity.class);
        startActivityForResult(intent, 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == Activity.RESULT_OK) {
           String api = data.getStringExtra(ARouterPath.PATH_TO_SCAN);
           if(TextUtils.isEmpty(api)) {
               return;
           }
           showLoadingDialog();
           mPresenter.requestScan(api);
        }
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        if("requestScan".equalsIgnoreCase(api)) {
            CommonUtil.showLongToastByFilter(getContext(), errCode, errInfo);
        } else {
            CommonUtil.showToastByFilter(getContext(), errCode, errInfo);
        }
    }

    @Override
    public void onHome(RespHomeBean homeBean) {
        hideLoadingDialog();
        refreshViews(homeBean);
    }

    @Override
    public void onScan() {
        requestHome();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void countChange(CountChangeEvent event) {
        requestHome();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void blueConnectChange(BluetoothChangeEvent event) {
        refreshBluetoothStatus();
    }

    private void refreshBluetoothStatus() {
        matchBluetoothStatusTv.setText(BluetoothDataCenter.getInstance().getCurrentDeviceName(getContext()));
    }
}
