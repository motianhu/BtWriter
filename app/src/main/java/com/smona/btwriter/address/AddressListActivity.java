package com.smona.btwriter.address;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.btwriter.R;
import com.smona.btwriter.address.adapter.AddressAdapter;
import com.smona.btwriter.address.bean.AddressBean;
import com.smona.btwriter.address.presenter.AddressListPresenter;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;

import java.util.List;

@Route(path = ARouterPath.PATH_TO_ADDRESSLIST)
public class AddressListActivity extends BaseLoadingPresenterActivity<AddressListPresenter, AddressListPresenter.IAddressListView> implements AddressListPresenter.IAddressListView {

    private XRecyclerView xRecyclerView;
    private AddressAdapter adapter;

    @Override
    protected AddressListPresenter initPresenter() {
        return new AddressListPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addresslist;
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
        titleTv.setText(R.string.addressList);
    }

    private void initViews() {
        xRecyclerView = findViewById(R.id.addressList);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                requestAddressList();
            }
        });
        adapter = new AddressAdapter(R.layout.adapter_item_address);
        xRecyclerView.setAdapter(adapter);

        findViewById(R.id.submit).setOnClickListener(v -> clickNewAddress());
    }

    private void clickNewAddress() {
        ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_ADDRESS);
    }

    @Override
    protected void initData() {
        super.initData();
        requestAddressList();
    }

    private void requestAddressList() {
        mPresenter.requestAddressList();
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        CommonUtil.showToastByFilter(errCode, errInfo);
    }

    @Override
    public void onAddressList(boolean isFirstPage, List<AddressBean> list) {
        xRecyclerView.loadMoreComplete();
        xRecyclerView.refreshComplete();
        if(isFirstPage) {
            adapter.setNewData(list);
        } else {
            adapter.addData(list);
        }
    }

    @Override
    public void onSetDefault() {
        hideLoadingDialog();
    }

    @Override
    public void onDelete() {
        hideLoadingDialog();
    }

    @Override
    public void onEmpty() {
        xRecyclerView.loadMoreComplete();
        xRecyclerView.refreshComplete();
    }

    @Override
    public void onComplete() {
        xRecyclerView.setNoMore(true);
    }
}
