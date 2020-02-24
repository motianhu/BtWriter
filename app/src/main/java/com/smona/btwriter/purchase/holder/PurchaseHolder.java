package com.smona.btwriter.purchase.holder;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.common.XViewHolder;
import com.smona.btwriter.purchase.bean.GoodsBean;
import com.smona.btwriter.purchase.bean.TwoGoodsBean;
import com.smona.image.loader.ImageLoaderDelegate;

public class PurchaseHolder extends XViewHolder {

    private View leftItemView;
    private View rightItemView;

    public PurchaseHolder(View itemView) {
        super(itemView);
        leftItemView = itemView.findViewById(R.id.leftItem);
        rightItemView = itemView.findViewById(R.id.rightItem);
    }

    public void bindViews(TwoGoodsBean item) {
        showViews(leftItemView, item.getLeftBean());
        if(item.getRightBean() != null) {
            showViews(rightItemView, item.getRightBean());
        }
    }

    private void showViews(View rootView, GoodsBean goodsBean) {
        Context context = rootView.getContext();
        ImageView icon = rootView.findViewById(R.id.icon);
        ImageLoaderDelegate.getInstance().showTopCornerImage(goodsBean.getCoverImg(), icon, context.getResources().getDimensionPixelOffset(R.dimen.dimen_5dp), 0);
        TextView titleTv = rootView.findViewById(R.id.title);
        titleTv.setText(goodsBean.getName());
        TextView salesNumTv = rootView.findViewById(R.id.salesNum);
        salesNumTv.setText(context.getString(R.string.sales_num) + "  "  + goodsBean.getSaleAmount());

        TextView priceTv = rootView.findViewById(R.id.price);
        priceTv.setText(context.getString(R.string.rmb_sign) + ": " + goodsBean.getDiscountPrice()  + "");

        TextView realPriceTv = rootView.findViewById(R.id.realPrice);
        realPriceTv.setText(context.getString(R.string.rmb_sign) + ": " + goodsBean.getPrice()  + "");
        realPriceTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
    }
}
