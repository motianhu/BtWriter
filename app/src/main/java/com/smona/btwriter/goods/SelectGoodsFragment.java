package com.smona.btwriter.goods;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.base.ui.fragment.BasePresenterFragment;
import com.smona.btwriter.R;
import com.smona.btwriter.goods.bean.CategoryContract;
import com.smona.btwriter.goods.bean.GoodsBean;
import com.smona.btwriter.goods.presenter.SelectGoodsPresenter;
import com.smona.btwriter.util.CommonUtil;
import com.smona.btwriter.util.PopupAnim;
import com.smona.image.loader.ImageLoaderDelegate;

import java.util.HashMap;
import java.util.Map;

public class SelectGoodsFragment extends BasePresenterFragment<SelectGoodsPresenter, SelectGoodsPresenter.ISelectGoodsView> implements SelectGoodsPresenter.ISelectGoodsView {

    private GoodsBean goodsBean;

    private PopupAnim popupAnim = new PopupAnim();
    private View contentView;
    private View maskView;

    private ImageView iconIv;
    private TextView nameTv;
    private TextView salesNumTv;
    private TextView priceTv;
    private TextView realPriceTv;
    private View goodTypeView;
    private TextView goodTypeTv;

    private CategoryContract categoryContract;

    public static SelectGoodsFragment buildInstance() {
        return new SelectGoodsFragment();
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
        categoryContract = new CategoryContract(getContext());

        contentView = content.findViewById(R.id.contentView);
        maskView = content.findViewById(R.id.maskView);
        maskView.setOnClickListener(v -> closeFragment());

        iconIv = content.findViewById(R.id.icon);
        nameTv = content.findViewById(R.id.name);
        salesNumTv = content.findViewById(R.id.sales_num);
        priceTv = content.findViewById(R.id.price);
        realPriceTv = content.findViewById(R.id.real_price);
        goodTypeView = content.findViewById(R.id.goods_type);
        goodTypeTv = content.findViewById(R.id.category);
        content.findViewById(R.id.btn_ok).setOnClickListener(v -> clickOk());
        refreshViews();
    }

    private void clickOk() {
        showLoadingDialog();
        mPresenter.requestAddGoods(goodsBean.getId());
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
        CommonUtil.showToastByFilter(getContext(), errCode, errInfo);
    }

    private void refreshViews() {
        if (goodsBean == null) {
            return;
        }
        ImageLoaderDelegate.getInstance().showCornerImage(goodsBean.getCoverImg(), iconIv, mActivity.getResources().getDimensionPixelSize(R.dimen.dimen_5dp), 0);
        nameTv.setText(goodsBean.getName());
        if (goodsBean.getKind() == 1) {
            goodTypeView.setVisibility(View.VISIBLE);
            goodTypeTv.setText(categoryContract.getCategoryName(goodsBean.getType()));
            goodTypeTv.setSelected(true);
        } else {
            goodTypeView.setVisibility(View.GONE);
        }
        salesNumTv.setText(mActivity.getString(R.string.sales_num) + "  " + goodsBean.getSaleAmount());
        priceTv.setText(mActivity.getString(R.string.rmb_sign) + ": " + goodsBean.getRealPrice());
        realPriceTv.setText(mActivity.getString(R.string.rmb_sign) + ": " + goodsBean.getPrice());
        realPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
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
        refreshViews();
        popupAnim.ejectView(true, mActivity, rootView, maskView, contentView);
    }

    public void closeFragment() {
        popupAnim.retract(true, mActivity, getView(), maskView, contentView, null);
    }
}
