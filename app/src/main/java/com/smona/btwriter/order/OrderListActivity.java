package com.smona.btwriter.order;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.btwriter.R;
import com.smona.btwriter.common.CommonItemDecoration;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.order.adapter.OrderListAdapter;
import com.smona.btwriter.order.bean.OrderBean;
import com.smona.btwriter.order.presenter.OrderListPresenter;
import com.smona.btwriter.util.ARouterPath;

import java.util.List;

@Route(path = ARouterPath.PATH_TO_ORDERLIST)
public class OrderListActivity extends BaseLoadingPresenterActivity<OrderListPresenter, OrderListPresenter.IOrderListView> implements OrderListPresenter.IOrderListView {

    private XRecyclerView xRecyclerView;
    private OrderListAdapter adapter;

    @Override
    protected OrderListPresenter initPresenter() {
        return new OrderListPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_orderlist;
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
        titleTv.setText(R.string.order_list);
    }

    private void initViews() {
        xRecyclerView = findViewById(R.id.order_list);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonItemDecoration spacesItemDecoration = new CommonItemDecoration(0, this.getResources().getDimensionPixelSize(R.dimen.dimen_6dp));
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.addItemDecoration(spacesItemDecoration);
        adapter = new OrderListAdapter(R.layout.adapter_item_order);
        xRecyclerView.setAdapter(adapter);

        initExceptionProcess(findViewById(R.id.loadingresult), xRecyclerView);
    }

    @Override
    protected void initData() {
        super.initData();
        requestOrderList();
    }

    private void requestOrderList() {
        mPresenter.requestOrderList();
    }

    @Override
    public void onEmpty() {
        hideLoadingDialog();
        xRecyclerView.loadMoreComplete();
        xRecyclerView.refreshComplete();
        doEmpty();
    }

    @Override
    public void onOrderList(boolean isFirstPage, List<OrderBean> list) {
        hideLoadingDialog();
        doSuccess();
        xRecyclerView.loadMoreComplete();
        xRecyclerView.refreshComplete();
        if(isFirstPage) {
            adapter.setNewData(list);
        } else {
            adapter.addData(list);
        }
    }

    @Override
    public void onComplete() {
        hideLoadingDialog();
        doSuccess();
        xRecyclerView.setNoMore(true);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        onError(api, errCode, errInfo, this::requestOrderList);
    }
}
