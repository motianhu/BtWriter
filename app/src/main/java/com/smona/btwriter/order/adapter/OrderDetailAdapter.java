package com.smona.btwriter.order.adapter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.goods.bean.ShoppingCardBean;

public class OrderDetailAdapter extends XBaseAdapter<ShoppingCardBean, GoodsHolder> {

    public OrderDetailAdapter(int resId) {
        super(resId);
    }

    @Override
    protected void convert(GoodsHolder holder, ShoppingCardBean item, int pos) {
        holder.bindViews(item);
    }
}
