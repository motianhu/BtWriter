package com.smona.btwriter.goods.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.common.XViewHolder;
import com.smona.btwriter.goods.bean.ShoppingCardBean;
import com.smona.image.loader.ImageLoaderDelegate;

public class ShoppingCardListHolder extends XViewHolder {

    private ImageView iconIv;
    private TextView nameTv;
    private TextView categoryTv;
    private TextView numTv;
    private TextView priceTv;
    private View minusView;
    private View addView;
    private View delView;

    public ShoppingCardListHolder(View itemView) {
        super(itemView);
        iconIv = itemView.findViewById(R.id.icon);
        nameTv = itemView.findViewById(R.id.name);
        categoryTv = itemView.findViewById(R.id.category);
        priceTv = itemView.findViewById(R.id.price);
        minusView = itemView.findViewById(R.id.minus);
        numTv = itemView.findViewById(R.id.num);;
        addView = itemView.findViewById(R.id.plus);
        delView = itemView.findViewById(R.id.delete);
    }

    public void bindViews(ShoppingCardBean goodsBean) {
        Context context = itemView.getContext();
        ImageLoaderDelegate.getInstance().showImage(goodsBean.getCoverImg(), iconIv, 0);
        nameTv.setText(goodsBean.getName());
        categoryTv.setText(goodsBean.getGoodsTypeName());
        priceTv.setText(context.getString(R.string.rmb_sign) + ": " + goodsBean.getTotalPrice());
        numTv.setText(goodsBean.getAmount() + "");
    }
}
