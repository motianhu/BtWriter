package com.smona.btwriter.order;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.btwriter.R;
import com.smona.btwriter.common.CommonItemDecoration;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.order.adapter.OrderDetailAdapter;
import com.smona.btwriter.order.bean.OrderDetailBean;
import com.smona.btwriter.order.presenter.OrderPresenter;
import com.smona.btwriter.util.ARouterPath;

import java.util.ArrayList;

@Route(path = ARouterPath.PATH_TO_ORDERDETAIL)
public class OrderDetailActivity extends BaseLoadingPresenterActivity<OrderPresenter, OrderPresenter.IOrderView> implements OrderPresenter.IOrderView {

    private String mOrderNo;

    private TextView orderNoTv;
    private TextView timeTv;
    private TextView priceTv;
    private TextView statusTv;
    private TextView remarkTv;
    private TextView contactTv;
    private TextView phoneTv;
    private TextView distributorTv;
    private TextView addressTv;

    private XRecyclerView xRecyclerView;
    private OrderDetailAdapter adapter;

    @Override
    protected OrderPresenter initPresenter() {
        return new OrderPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_orderdetail;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initSerilized();
        initHeader();
        initViews();
    }

    private void initSerilized() {
        mOrderNo = getIntent().getStringExtra(ARouterPath.PATH_TO_ORDERDETAIL);
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(R.string.order_detail);
    }

    private void initViews() {
        orderNoTv = findViewById(R.id.order_no);
        timeTv = findViewById(R.id.order_date);
        priceTv = findViewById(R.id.order_price);
        statusTv = findViewById(R.id.order_status);
        remarkTv = findViewById(R.id.remark);
        contactTv = findViewById(R.id.contact);
        phoneTv = findViewById(R.id.phone);
        distributorTv = findViewById(R.id.distributor);
        addressTv = findViewById(R.id.address);

        xRecyclerView = findViewById(R.id.goods_list);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonItemDecoration spacesItemDecoration = new CommonItemDecoration(0, this.getResources().getDimensionPixelSize(R.dimen.dimen_6dp));
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.addItemDecoration(spacesItemDecoration);
        adapter = new OrderDetailAdapter(R.layout.adapter_item_goods);
        xRecyclerView.setAdapter(adapter);

        initExceptionProcess(findViewById(R.id.loadingresult), xRecyclerView, orderNoTv, timeTv, priceTv, statusTv, remarkTv, contactTv, phoneTv, addressTv, findViewById(R.id.remark_hint));
    }

    private void refreshView(OrderDetailBean item) {
        String orderNo = getString(R.string.order_no) + item.getOrderNo();
        orderNoTv.setText(orderNo);
        String time = getString(R.string.order_time) + item.getCreateTime();
        timeTv.setText(time);
        String status = getString(R.string.order_status_wait);
        int resId = R.drawable.bg_corner_ffcd63_10;
        if (item.isOk()) {
            resId = R.drawable.bg_corner_1fdc37_10;
            status = getString(R.string.order_status_ok);
        } else if (item.isRefuse()) {
            resId = R.drawable.bg_corner_f65566_10;
            status = getString(R.string.order_status_refuse);
        }
        statusTv.setText(status);
        statusTv.setBackgroundResource(resId);

        String price = getString(R.string.order_price) + getString(R.string.rmb_sign) + item.getTotalPrice();
        priceTv.setText(price);

        remarkTv.setText(item.getRemark());

        String receiver_name = getString(R.string.receiver_name) + item.getUserName();
        contactTv.setText(receiver_name);

        String receiver_phone = getString(R.string.receiver_phone) + item.getPhone();
        phoneTv.setText(receiver_phone);

        String distributor = getString(R.string.account) + ":  "+ item.getPayAccount();
        distributorTv.setText(distributor);

        String receiver_address = getString(R.string.receiver_address) + item.getAddress();
        addressTv.setText(receiver_address);

        adapter.setNewData(item.getGoodsList());
    }

    @Override
    protected void initData() {
        super.initData();
        requestOrderDetail();
    }

    private void requestOrderDetail() {
        mPresenter.requestOrderDetail(mOrderNo);
    }

    @Override
    public void onOrder(OrderDetailBean orderDetailBean) {
        if (orderDetailBean == null) {
            doEmpty();
            return;
        }
        doSuccess();
        refreshView(orderDetailBean);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        onError(api, errCode, errInfo, this::requestOrderDetail);
    }
}
