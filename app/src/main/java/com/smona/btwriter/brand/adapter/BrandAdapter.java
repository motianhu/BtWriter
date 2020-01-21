package com.smona.btwriter.brand.adapter;

import com.smona.btwriter.brand.bean.BrandBean;
import com.smona.btwriter.brand.holder.BrandHolder;
import com.smona.btwriter.common.XBaseAdapter;

public class BrandAdapter extends XBaseAdapter<BrandBean, BrandHolder> {

    public BrandAdapter(int resId) {
        super(resId);
    }

    @Override
    protected void convert(BrandHolder holder, BrandBean item, int pos) {
        holder.bindViews(item);
    }
}
