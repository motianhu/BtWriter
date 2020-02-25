package com.smona.btwriter.goods.adapter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.goods.bean.TwoGoodsBean;
import com.smona.btwriter.goods.holder.GoodsListHolder;

public class GoodsListAdapter extends XBaseAdapter<TwoGoodsBean, GoodsListHolder> {

    public GoodsListAdapter(int resId) {
        super(resId);
    }

    @Override
    protected void convert(GoodsListHolder holder, TwoGoodsBean item, int pos) {
        holder.bindViews(item);
    }
}
