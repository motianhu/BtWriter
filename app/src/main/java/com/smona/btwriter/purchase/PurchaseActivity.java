package com.smona.btwriter.purchase;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.btwriter.R;
import com.smona.btwriter.common.CommonItemDecoration;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.purchase.adapter.PurchaseAdapter;
import com.smona.btwriter.purchase.bean.GoodsBean;
import com.smona.btwriter.purchase.bean.TwoGoodsBean;
import com.smona.btwriter.purchase.presenter.PurchasePresenter;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 采购
 */
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
        int margin = getResources().getDimensionPixelSize(R.dimen.dimen_14dp);
        CommonItemDecoration ex = new CommonItemDecoration(0, margin, 0);
        xRecyclerView.addItemDecoration(ex);

        adapter = new PurchaseAdapter(R.layout.adapter_item_purchase);
        xRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        requestGoodsList();
    }

    private void requestGoodsList() {
        mPresenter.requestGoodsList();
    }

    private void refreshGoodList() {
        mPresenter.refreshGoodList();
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        CommonUtil.showToastByFilter(errCode, errInfo);
    }

    @Override
    public void onEmpty() {

    }

    @Override
    public void onGoodsList(boolean hasMore, List<GoodsBean> goodsBeanList) {
        List<TwoGoodsBean> twoGoodsBeanList = new ArrayList<>();
        TwoGoodsBean twoGoodsBean = null;
        for (int i = 0; i < goodsBeanList.size(); i++) {
            if (i % 2 == 0) {
                twoGoodsBean = new TwoGoodsBean();
                twoGoodsBeanList.add(twoGoodsBean);
                twoGoodsBean.setLeftBean(goodsBeanList.get(i));
            } else {
                twoGoodsBean.setRightBean(goodsBeanList.get(i));
            }
        }
        adapter.addData(twoGoodsBeanList);
    }
}
