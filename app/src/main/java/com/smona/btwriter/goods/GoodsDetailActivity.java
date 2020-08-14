package com.smona.btwriter.goods;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.goods.bean.GoodsBean;
import com.smona.btwriter.goods.presenter.GoodsDetailPresenter;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;
import com.smona.image.loader.ImageLoaderDelegate;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

@Route(path = ARouterPath.PATH_TO_GOODSDETAIL)
public class GoodsDetailActivity extends BaseLoadingPresenterActivity<GoodsDetailPresenter, GoodsDetailPresenter.IGoodsDetailView> implements GoodsDetailPresenter.IGoodsDetailView {

    private int goodsId;
    private GoodsBean goodsBean;

    private Banner bannerView;
    private TextView titleTv;
    private TextView priceTv;
    private TextView realPriceTv;
    private TextView salesNumTv;

    private TextView commentTv;

    private SelectGoodsFragment selectGoodsFragment;

    @Override
    protected GoodsDetailPresenter initPresenter() {
        return new GoodsDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goodsdetail;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initSerialize();
        initHeader();
        initViews();
    }

    private void initSerialize() {
        goodsId = getIntent().getIntExtra(ARouterPath.PATH_TO_GOODSDETAIL, -1);
        if(goodsId == -1) {
            finish();
        }
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> finish());
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(R.string.goods_detail);
    }

    private void initViews() {
        bannerView = findViewById(R.id.banner);
        bannerView.setDelayTime(3000).isAutoPlay(true).setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        }).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                ImageLoaderDelegate.getInstance().showImage((String)path, imageView, 0);
            }
        }).setIndicatorGravity(BannerConfig.CENTER);

        titleTv = findViewById(R.id.name);
        priceTv = findViewById(R.id.price);
        realPriceTv = findViewById(R.id.realPrice);
        realPriceTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        salesNumTv = findViewById(R.id.salesNum);
        commentTv = findViewById(R.id.comment);
        findViewById(R.id.add).setOnClickListener(v->clickAdd());
        findViewById(R.id.btn_shoppingcard).setOnClickListener(v->clickShoppingCard());

        selectGoodsFragment = SelectGoodsFragment.buildInstance();
    }

    private void clickAdd() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.select_fragment, selectGoodsFragment);
        fragmentTransaction.commit();
        selectGoodsFragment.setParam(goodsBean);
        selectGoodsFragment.showFragment();
    }

    private void clickShoppingCard(){
        ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_SHOPPINGCARD);
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
    protected void initData() {
        super.initData();
        mPresenter.requestGoodsDetail(goodsId);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        CommonUtil.showToastByFilter(this, errCode, errInfo);
    }

    @Override
    public void onGoodsDetail(GoodsBean goodsBean) {
        this.goodsBean = goodsBean;
        refreshViews(goodsBean);
    }

    private void refreshViews(GoodsBean goodsBean) {
        bannerView.setImages(goodsBean.getImageList()).start();
        titleTv.setText(goodsBean.getName());
        salesNumTv.setText(getString(R.string.sales_num) + "  "  + goodsBean.getSaleAmount());
        priceTv.setText(getString(R.string.rmb_sign) + ": " + goodsBean.getRealPrice() );
        realPriceTv.setText(getString(R.string.rmb_sign) + ": " + goodsBean.getPrice());
        commentTv.setText(goodsBean.getComment());
    }
}
