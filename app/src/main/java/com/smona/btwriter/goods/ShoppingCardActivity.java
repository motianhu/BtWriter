package com.smona.btwriter.goods;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.btwriter.R;
import com.smona.btwriter.address.AddressListActivity;
import com.smona.btwriter.address.bean.AddressBean;
import com.smona.btwriter.common.CommonItemDecoration;
import com.smona.btwriter.goods.adapter.ShoppingCardListAdapter;
import com.smona.btwriter.goods.bean.GoodsNum;
import com.smona.btwriter.goods.bean.ResShoppingCardList;
import com.smona.btwriter.goods.presenter.ShoppingCardPresenter;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;
import com.smona.btwriter.util.ToastUtil;
import com.smona.btwriter.widget.CommonOkDialog;

import java.util.List;

@Route(path = ARouterPath.PATH_TO_SHOPPINGCARD)
public class ShoppingCardActivity extends BaseLoadingPresenterActivity<ShoppingCardPresenter, ShoppingCardPresenter.IShoppingCardView> implements ShoppingCardPresenter.IShoppingCardView {

    private AddressBean addressBean;

    private XRecyclerView xRecyclerView;
    private ShoppingCardListAdapter adapter;

    private View setDefaultIv;
    private TextView nameTv;
    private TextView phoneTv;
    private TextView addressTv;

    private EditText remarkTv;

    @Override
    protected ShoppingCardPresenter initPresenter() {
        return new ShoppingCardPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shoppingcardlist;
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
        titleTv.setText(R.string.shoppingcard_list);
    }

    private void initViews() {
        xRecyclerView = findViewById(R.id.shoppingcardlist);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonItemDecoration spacesItemDecoration = new CommonItemDecoration(0, this.getResources().getDimensionPixelSize(R.dimen.dimen_6dp));
        xRecyclerView.addItemDecoration(spacesItemDecoration);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        adapter = new ShoppingCardListAdapter(R.layout.adapter_item_shoppingcard);
        adapter.setListener(this::clickDelete);
        xRecyclerView.setAdapter(adapter);
        findViewById(R.id.address_rl).setOnClickListener(v -> clickAddress());

        setDefaultIv = findViewById(R.id.set_default);
        nameTv = findViewById(R.id.name);
        phoneTv = findViewById(R.id.phone);
        addressTv = findViewById(R.id.address);
        remarkTv = findViewById(R.id.remark);

        findViewById(R.id.submit).setOnClickListener(v-> clickSubmit());
    }

    private void clickDelete(int id) {
        CommonOkDialog commonOkDialog = new CommonOkDialog(this);
        commonOkDialog.setTitle(getString(R.string.action_hint));
        commonOkDialog.setContent(getString(R.string.action_delete_content));
        commonOkDialog.setPositiveButton(getString(R.string.action_ok));
        commonOkDialog.setCancel(getString(R.string.action_cancel));
        commonOkDialog.setCommitListener((dialog, confirm) -> {
            dialog.dismiss();
            if(confirm) {
                showLoadingDialog();
                mPresenter.requestDelete(id);
            }
        });
        commonOkDialog.show();
    }

    private void clickSubmit() {
        if(addressBean == null) {
            ToastUtil.showShort(R.string.empty_address);
            return;
        }
        if(adapter.getItemCount() ==0) {
            ToastUtil.showShort(R.string.empty_shoppingcard);
            return;
        }
        List<GoodsNum> list = adapter.getGoodsListNum();
        showLoadingDialog();
        mPresenter.requestSubmit(addressBean.getId(), remarkTv.getText().toString(), list);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.requestShoppingList();
    }

    private void clickAddress() {
        Intent intent = new Intent();
        if (addressBean != null) {
            intent.putExtra(ARouterPath.PATH_TO_ADDRESSLIST, addressBean.getId());
        }
        intent.setClass(this, AddressListActivity.class);
        startActivityForResult(intent, 10001);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        CommonUtil.showToastByFilter(this, errCode, errInfo);
    }

    @Override
    public void onList(ResShoppingCardList shoppingCardList) {
        adapter.setNewData(shoppingCardList.getGoodsList());
        refreshAddress(shoppingCardList.getAddress());
    }

    @Override
    public void onModify(int id, int count) {
        hideLoadingDialog();
        adapter.notifiRefresh(id, count);
    }

    @Override
    public void onDelete(int id) {
        hideLoadingDialog();
        adapter.notifiDelete(id);
    }

    @Override
    public void onSubmit() {
        hideLoadingDialog();
        CommonUtil.showCustomToast(getString(R.string.shopping_submit_success));
        finish();
    }

    private void refreshAddress(AddressBean addressBean) {
        String name = getString(R.string.receiver_name) + "  " ;
        String phone = getString(R.string.receiver_phone) + "  ";
        String address = getString(R.string.receiver_address) + "  ";
        if (addressBean != null) {
            this.addressBean = addressBean;
            name += addressBean.getUserName();
            phone += addressBean.getPhone();
            address += addressBean.getAddress();
            setDefaultIv.setVisibility(addressBean.isDefault() ? View.VISIBLE : View.GONE);
        }
        nameTv.setText(name);
        phoneTv.setText(phone);
        addressTv.setText(address);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001 && resultCode == RESULT_OK) {
            AddressBean addressBean = (AddressBean) data.getSerializableExtra(AddressBean.class.getName());
            refreshAddress(addressBean);
        }
    }
}
