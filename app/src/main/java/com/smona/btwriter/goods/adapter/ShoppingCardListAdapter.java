package com.smona.btwriter.goods.adapter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.goods.bean.ShoppingCardBean;
import com.smona.btwriter.goods.holder.ShoppingCardListHolder;

public class ShoppingCardListAdapter extends XBaseAdapter<ShoppingCardBean, ShoppingCardListHolder> {

    public ShoppingCardListAdapter(int resId) {
        super(resId);
    }

    @Override
    protected void convert(ShoppingCardListHolder holder, ShoppingCardBean item, int pos) {
        holder.bindViews(item);
    }
}
