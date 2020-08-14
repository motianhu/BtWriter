package com.smona.btwriter.goods.holder;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.common.XViewHolder;
import com.smona.btwriter.goods.adapter.GoodsListAdapter;
import com.smona.btwriter.goods.bean.GoodsBean;
import com.smona.btwriter.goods.bean.TwoGoodsBean;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.image.loader.ImageLoaderDelegate;

public class GoodsListHolder extends XViewHolder {

    private View leftItemView;
    private View rightItemView;

    public GoodsListHolder(View itemView) {
        super(itemView);
        leftItemView = itemView.findViewById(R.id.leftItem);
        rightItemView = itemView.findViewById(R.id.rightItem);
    }

    public void bindViews(TwoGoodsBean item, GoodsListAdapter.OnClickGoodListListerner onClickGoodListListerner) {
        showViews(leftItemView, item.getLeftBean(), onClickGoodListListerner);
        if(item.getRightBean() != null) {
            showViews(rightItemView, item.getRightBean(), onClickGoodListListerner);
        }
    }

    private void showViews(View rootView, GoodsBean goodsBean, GoodsListAdapter.OnClickGoodListListerner onClickGoodListListerner) {
        Context context = rootView.getContext();
        ImageView icon = rootView.findViewById(R.id.icon);
        ImageLoaderDelegate.getInstance().showTopCornerImage(goodsBean.getCoverImg(), icon, context.getResources().getDimensionPixelOffset(R.dimen.dimen_5dp), 0);
        TextView titleTv = rootView.findViewById(R.id.title);
        titleTv.setText(goodsBean.getName());
        TextView salesNumTv = rootView.findViewById(R.id.salesNum);
        salesNumTv.setText(context.getString(R.string.sales_num) + "  "  + goodsBean.getSaleAmount());

        TextView priceTv = rootView.findViewById(R.id.price);
        priceTv.setText(context.getString(R.string.rmb_sign) + ": " + goodsBean.getRealPrice());

        TextView realPriceTv = rootView.findViewById(R.id.realPrice);
        realPriceTv.setText(context.getString(R.string.rmb_sign) + ": " + goodsBean.getPrice());
        realPriceTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );

        rootView.setOnClickListener(v->clickViewDetail(goodsBean.getId()));
        rootView.findViewById(R.id.shoppingcard).setOnClickListener(v-> onClickGoodListListerner.onClickAdd(goodsBean));
    }

    private void clickViewDetail(int id) {
        ARouterManager.getInstance().gotoActivityWithInt(ARouterPath.PATH_TO_GOODSDETAIL, ARouterPath.PATH_TO_GOODSDETAIL, id);
    }
}
