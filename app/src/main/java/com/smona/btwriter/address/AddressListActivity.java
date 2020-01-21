package com.smona.btwriter.address;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.btwriter.R;
import com.smona.btwriter.address.adapter.AddressAdapter;
import com.smona.btwriter.address.presenter.AddressPresenter;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.util.ARouterPath;

@Route(path = ARouterPath.PATH_TO_ADDRESS)
public class AddressListActivity extends BaseLoadingPresenterActivity<AddressPresenter, AddressPresenter.IAddressView> implements AddressPresenter.IAddressView {
    private XRecyclerView xRecyclerView;
    private AddressAdapter adapter;

    @Override
    protected AddressPresenter initPresenter() {
        return new AddressPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address;
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
        adapter = new AddressAdapter(R.layout.adapter_item_address);
        xRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
