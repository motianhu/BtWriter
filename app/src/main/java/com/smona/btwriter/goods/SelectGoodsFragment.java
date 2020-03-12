package com.smona.btwriter.goods;

import android.graphics.Paint;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.base.ui.fragment.BasePresenterFragment;
import com.smona.btwriter.R;
import com.smona.btwriter.common.CommonItemDecoration;
import com.smona.btwriter.goods.adapter.GoodsTypeAdapter;
import com.smona.btwriter.goods.bean.GoodsBean;
import com.smona.btwriter.goods.bean.GoodsTypeBean;
import com.smona.btwriter.goods.presenter.SelectGoodsPresenter;
import com.smona.btwriter.util.CommonUtil;
import com.smona.btwriter.util.PopupAnim;
import com.smona.btwriter.util.ToastUtil;
import com.smona.image.loader.ImageLoaderDelegate;

public class SelectGoodsFragment extends BasePresenterFragment<SelectGoodsPresenter, SelectGoodsPresenter.ISelectGoodsView> implements SelectGoodsPresenter.ISelectGoodsView {

    private GoodsBean goodsBean;

    private PopupAnim popupAnim = new PopupAnim();
    private View contentView;
    private View maskView;

    private XRecyclerView recyclerView;
    private GoodsTypeAdapter goodsTypeAdapter;

    private ImageView iconIv;
    private TextView nameTv;
    private TextView salesNumTv;
    private TextView priceTv;
    private TextView realPriceTv;

    public static SelectGoodsFragment buildInstance() {
        SelectGoodsFragment selectGoodsFragment = new SelectGoodsFragment();
        return selectGoodsFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_goods;
    }

    @Override
    protected SelectGoodsPresenter initPresenter() {
        return new SelectGoodsPresenter();
    }

    @Override
    protected void initView(View content) {
        super.initView(content);

        contentView = content.findViewById(R.id.contentView);
        maskView = content.findViewById(R.id.maskView);
        maskView.setOnClickListener(v->closeFragment());

        iconIv = content.findViewById(R.id.icon);
        nameTv = content.findViewById(R.id.name);
        recyclerView = content.findViewById(R.id.goods_type);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 4));
        CommonItemDecoration commonItemDecoration = new CommonItemDecoration(0, mActivity.getResources().getDimensionPixelSize(R.dimen.dimen_4dp));
        recyclerView.addItemDecoration(commonItemDecoration);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(false);
        goodsTypeAdapter = new GoodsTypeAdapter(R.layout.item_goods_type);
        recyclerView.setAdapter(goodsTypeAdapter);
        salesNumTv = content.findViewById(R.id.sales_num);
        priceTv = content.findViewById(R.id.price);
        realPriceTv = content.findViewById(R.id.real_price);
        content.findViewById(R.id.btn_ok).setOnClickListener(v-> clickOk());
        requestData();
    }

    private void clickOk() {
        GoodsTypeBean goodsTypeBean = goodsTypeAdapter.getSelectedId();
        if(goodsTypeBean == null) {
            ToastUtil.showShort(R.string.empty_goods_type);
            return;
        }
        showLoadingDialog();
        mPresenter.requestAddGoods(goodsBean.getId(), goodsTypeBean.getId());
    }

    @Override
    public void onGoodsDetail(GoodsBean goodsBean) {
        this.goodsBean = goodsBean;
        if (contentView == null) {
            return;
        }

        View rootView = getView();
        if (rootView == null) {
            // Fragment View 还没创建
            return;
        }
        refreshViews();
    }

    @Override
    public void onAddGoods() {
        hideLoadingDialog();
        closeFragment();
        CommonUtil.showCustomToast(getString(R.string.add_shoppingcard_success));
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        CommonUtil.showToastByFilter(errCode, errInfo);
    }

    private void requestData() {
        if(CommonUtil.isEmptyList(goodsBean.getTypeList())) {
            if(mPresenter != null) {
                mPresenter.requestGoodsDetail(goodsBean.getId());
            }
        }  else {
            refreshViews();
        }
    }

    private void refreshViews() {
        if(goodsBean == null) {
            return;
        }
        ImageLoaderDelegate.getInstance().showCornerImage(goodsBean.getCoverImg(), iconIv, mActivity.getResources().getDimensionPixelSize(R.dimen.dimen_5dp), 0);
        nameTv.setText(goodsBean.getName());
        if(!CommonUtil.isEmptyList(goodsBean.getTypeList()) && goodsBean.getTypeList().size() == 1) {
            goodsBean.getTypeList().get(0).setSelected(true);
        }
        goodsTypeAdapter.setNewData(goodsBean.getTypeList());
        salesNumTv.setText(mActivity.getString(R.string.sales_num) + "  "  + goodsBean.getSaleAmount());
        priceTv.setText(mActivity.getString(R.string.rmb_sign) + ": " + goodsBean.getDiscountPrice());
        realPriceTv.setText(mActivity.getString(R.string.rmb_sign) + ": " + goodsBean.getPrice());
        realPriceTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
    }

    //外部设置
    public void setParam(GoodsBean goodsBean) {
        this.goodsBean = goodsBean;
    }

    public void showFragment() {
        if (contentView == null) {
            return;
        }
        View rootView = getView();
        if (rootView == null) {
            // Fragment View 还没创建
            return;
        }
        requestData();
        popupAnim.ejectView(true, mActivity, rootView, maskView, contentView);
    }

    public void closeFragment() {
        popupAnim.retract(true, mActivity, getView(), maskView, contentView, null);
    }
}
