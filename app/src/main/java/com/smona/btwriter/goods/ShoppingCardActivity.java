package com.smona.btwriter.goods;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.goods.presenter.ShoppingCardPresenter;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.util.ARouterPath;

@Route(path = ARouterPath.PATH_TO_SHOPPINGCARD)
public class ShoppingCardActivity extends BaseLoadingPresenterActivity<ShoppingCardPresenter, ShoppingCardPresenter.IShoppingCardView> implements ShoppingCardPresenter.IShoppingCardView {
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

    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
