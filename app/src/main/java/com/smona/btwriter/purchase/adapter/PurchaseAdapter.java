package com.smona.btwriter.purchase.adapter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.purchase.bean.TwoGoodsBean;
import com.smona.btwriter.purchase.holder.PurchaseHolder;

public class PurchaseAdapter extends XBaseAdapter<TwoGoodsBean, PurchaseHolder> {

    public PurchaseAdapter(int resId) {
        super(resId);
    }

    @Override
    protected void convert(PurchaseHolder holder, TwoGoodsBean item, int pos) {
        holder.bindViews(item);
    }
}
