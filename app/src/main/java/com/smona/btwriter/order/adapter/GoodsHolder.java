package com.smona.btwriter.order.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.common.XViewHolder;
import com.smona.btwriter.goods.bean.CategoryContract;
import com.smona.btwriter.goods.bean.ShoppingCardBean;
import com.smona.image.loader.ImageLoaderDelegate;

import java.util.HashMap;
import java.util.Map;

public class GoodsHolder extends XViewHolder {

    private ImageView iconIv;
    private TextView nameTv;
    private TextView categoryTv;
    private TextView priceTv;
    private TextView amountTv;

    private CategoryContract categoryContract;

    public GoodsHolder(View itemView) {
        super(itemView);
        categoryContract = new CategoryContract(itemView.getContext());
        iconIv = itemView.findViewById(R.id.icon);
        nameTv = itemView.findViewById(R.id.name);
        categoryTv = itemView.findViewById(R.id.category);
        priceTv = itemView.findViewById(R.id.price);
        amountTv = itemView.findViewById(R.id.amount);
    }

    public void bindViews(ShoppingCardBean goodsBean) {
        Context context = itemView.getContext();
        ImageLoaderDelegate.getInstance().showCornerImage(goodsBean.getCoverImg(), iconIv, context.getResources().getDimensionPixelSize(R.dimen.dimen_10dp), 0);
        nameTv.setText(goodsBean.getName());
        if (goodsBean.getGoodsKind() == 1) {
            categoryTv.setVisibility(View.VISIBLE);
            categoryTv.setText(context.getResources().getString(R.string.category) + "  " + categoryContract.getCategoryName(goodsBean.getGoodsType() + ""));
        } else {
            categoryTv.setVisibility(View.GONE);
        }
        priceTv.setText(context.getString(R.string.rmb_sign) + ": " + goodsBean.getTotalPrice());
        amountTv.setText(context.getString(R.string.number) + goodsBean.getAmount());
    }
}