package com.smona.btwriter.goods;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.btwriter.R;
import com.smona.btwriter.common.CommonItemDecoration;
import com.smona.btwriter.common.exception.InitExceptionProcess;
import com.smona.btwriter.goods.bean.ReqGoodsList;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.goods.adapter.GoodsListAdapter;
import com.smona.btwriter.goods.bean.GoodsBean;
import com.smona.btwriter.goods.bean.TwoGoodsBean;
import com.smona.btwriter.goods.presenter.GoodsListPresenter;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 采购
 */
@Route(path = ARouterPath.PATH_TO_GOODSLIST)
public class GoodsListActivity extends BaseLoadingPresenterActivity<GoodsListPresenter, GoodsListPresenter.IPurchaseView> implements GoodsListPresenter.IPurchaseView {

    private XRecyclerView xRecyclerView;
    private GoodsListAdapter adapter;
    private SelectGoodsFragment selectGoodsFragment;

    private EditText searchTv;
    private TextView sort_sales;
    private TextView sort_price;

    private ReqGoodsList reqGoodsBean = new ReqGoodsList();

    @Override
    protected GoodsListPresenter initPresenter() {
        return new GoodsListPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goodslist;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initHeader();
        initViews();
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> finish());
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(R.string.goods_list);
    }

    private void initViews() {
        xRecyclerView = findViewById(R.id.goodsList);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int margin = getResources().getDimensionPixelSize(R.dimen.dimen_5dp);
        CommonItemDecoration ex = new CommonItemDecoration(0, margin);
        xRecyclerView.addItemDecoration(ex);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshGoodList();
            }

            @Override
            public void onLoadMore() {
                requestGoodsList();
            }
        });

        adapter = new GoodsListAdapter(R.layout.adapter_item_goodslist);
        adapter.setOnClickGoodListListerner(this::showSelectFragment);
        xRecyclerView.setAdapter(adapter);
        findViewById(R.id.btn_shoppingcard).setOnClickListener(v->clickShoppingCard());

        selectGoodsFragment = SelectGoodsFragment.buildInstance();

        TextView zonghe = findViewById(R.id.sort_all);
        zonghe.setOnClickListener(v->clickSortAll());
        sort_sales  = findViewById(R.id.sort_sales);
        sort_sales.setOnClickListener(v->clickSales());
        sort_price = findViewById(R.id.sort_price);
        sort_price.setOnClickListener(v->clickPrice());
        searchTv = findViewById(R.id.searchBar);
        findViewById(R.id.search).setOnClickListener(v->clickSearch());

        initExceptionProcess(findViewById(R.id.loadingresult), xRecyclerView, findViewById(R.id.btn_shoppingcard));
    }

    private void clickSortAll() {
        reqGoodsBean.setSortField("sortAll");
        showLoadingDialog();
        refreshGoodList();
    }

    private void clickSales() {
        reqGoodsBean.setSortField("sales");
        reqGoodsBean.setIsAsc(sort_sales.isSelected() ? 0:1);
        sort_sales.setSelected(!sort_sales.isSelected());
        showLoadingDialog();
        refreshGoodList();
    }

    private void clickPrice() {
        reqGoodsBean.setSortField("price");
        reqGoodsBean.setIsAsc(sort_price.isSelected() ? 0:1);
        sort_price.setSelected(!sort_price.isSelected());
        showLoadingDialog();
        refreshGoodList();
    }

    private void clickSearch() {
        reqGoodsBean.setName(searchTv.getText().toString());
        showLoadingDialog();
        refreshGoodList();
    }

    private void clickShoppingCard(){
        ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_SHOPPINGCARD);
    }

    @Override
    protected void initData() {
        super.initData();
        requestGoodsList();
    }

    private void requestGoodsList() {
        mPresenter.requestGoodsList(reqGoodsBean);
    }

    private void refreshGoodList() {
        mPresenter.refreshGoodList(reqGoodsBean);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        onError(api, errCode, errInfo, this::requestGoodsList);
    }

    @Override
    public void onEmpty() {
        hideLoadingDialog();
        xRecyclerView.loadMoreComplete();
        xRecyclerView.refreshComplete();
        doEmpty();
    }

    @Override
    public void onGoodsList(boolean isFirstPage, List<GoodsBean> goodsBeanList) {
        hideLoadingDialog();
        doSuccess();
        xRecyclerView.loadMoreComplete();
        xRecyclerView.refreshComplete();
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
        if(isFirstPage) {
            adapter.setNewData(twoGoodsBeanList);
        } else {
            adapter.addData(twoGoodsBeanList);
        }
    }

    @Override
    public void onBackPressed() {
        if(selectGoodsFragment != null && selectGoodsFragment.isVisible()) {
            selectGoodsFragment.closeFragment();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onComplete() {
        hideLoadingDialog();
        doSuccess();
        xRecyclerView.setNoMore(true);
    }

    public void showSelectFragment(GoodsBean goodsBean) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.select_fragment, selectGoodsFragment);
        fragmentTransaction.commit();
        selectGoodsFragment.setParam(goodsBean);
        selectGoodsFragment.showFragment();
    }
}
