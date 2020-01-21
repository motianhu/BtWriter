package com.smona.btwriter.purchase.adapter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.purchase.bean.PurchaseBean;
import com.smona.btwriter.purchase.holder.PurchaseHolder;

public class PurchaseAdapter extends XBaseAdapter<PurchaseBean, PurchaseHolder> {

    public PurchaseAdapter(int resId) {
        super(resId);
    }

    @Override
    protected void convert(PurchaseHolder holder, PurchaseBean item, int pos) {

    }
}
