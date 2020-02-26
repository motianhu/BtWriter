package com.smona.btwriter.goods;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.btwriter.R;
import com.smona.btwriter.goods.adapter.ShoppingCardListAdapter;
import com.smona.btwriter.goods.bean.ResShoppingCardList;
import com.smona.btwriter.goods.presenter.ShoppingCardPresenter;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;

@Route(path = ARouterPath.PATH_TO_SHOPPINGCARD)
public class ShoppingCardActivity extends BaseLoadingPresenterActivity<ShoppingCardPresenter, ShoppingCardPresenter.IShoppingCardView> implements ShoppingCardPresenter.IShoppingCardView {

    private XRecyclerView xRecyclerView;
    private ShoppingCardListAdapter adapter;

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
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        adapter = new ShoppingCardListAdapter(R.layout.adapter_item_shoppingcard);
        xRecyclerView.setAdapter(adapter);
        findViewById(R.id.address_rl).setOnClickListener(v -> clickAddress());
    }

    private void clickAddress() {
        ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_ADDRESSLIST);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        CommonUtil.showToastByFilter(errCode, errInfo);
    }

    @Override
    public void onList(ResShoppingCardList shoppingCardList) {
        if (CommonUtil.isEmptyList(shoppingCardList.getGoodsList())) {
            return;
        }
        adapter.setNewData(shoppingCardList.getGoodsList());
    }
}
