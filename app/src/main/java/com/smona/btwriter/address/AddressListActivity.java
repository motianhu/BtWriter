package com.smona.btwriter.address;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.btwriter.R;
import com.smona.btwriter.address.adapter.AddressAdapter;
import com.smona.btwriter.address.bean.AddressBean;
import com.smona.btwriter.address.presenter.AddressListPresenter;
import com.smona.btwriter.common.CommonItemDecoration;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.notify.NotifyCenter;
import com.smona.btwriter.notify.event.AddressEvent;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

@Route(path = ARouterPath.PATH_TO_ADDRESSLIST)
public class AddressListActivity extends BaseLoadingPresenterActivity<AddressListPresenter, AddressListPresenter.IAddressListView> implements AddressListPresenter.IAddressListView {

    private int selectedId = -1;
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
        initSerializable();
        initHeader();
        initViews();
        NotifyCenter.getInstance().registerListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotifyCenter.getInstance().unRegisterListener(this);
    }

    private void initSerializable() {
        selectedId = getIntent().getIntExtra(ARouterPath.PATH_TO_ADDRESSLIST, -1);
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(R.string.addressList);
        TextView newTv = findViewById(R.id.rightTv);
        newTv.setText(R.string.address_new);
        newTv.setVisibility(View.VISIBLE);
        newTv.setOnClickListener(v->clickNewAddress());
    }

    private void initViews() {
        xRecyclerView = findViewById(R.id.addressList);
        CommonItemDecoration commonItemDecoration = new CommonItemDecoration(0, getResources().getDimensionPixelSize(R.dimen.dimen_5dp));
        xRecyclerView.addItemDecoration(commonItemDecoration);
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
        adapter.seSelectedId(selectedId);
        xRecyclerView.setAdapter(adapter);

        findViewById(R.id.submit).setOnClickListener(v -> clickOk());
    }

    private void clickNewAddress() {
        ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_ADDRESS);
    }

    private void clickOk() {
        Intent intent = new Intent();
        AddressBean addressBean = adapter.getSelectAddressBean();
        if(addressBean != null) {
            intent.putExtra(AddressBean.class.getName(), addressBean);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void initData() {
        super.initData();
        requestAddressList();
    }

    private void requestAddressList() {
        mPresenter.requestAddressList();
    }

    private void refreshAddressList() {
        mPresenter.refreshAddressList();
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
    public void onEmpty() {
        xRecyclerView.loadMoreComplete();
        xRecyclerView.refreshComplete();
    }

    @Override
    public void onComplete() {
        xRecyclerView.setNoMore(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshAddress(AddressEvent event) {
        refreshAddressList();
    }
}
