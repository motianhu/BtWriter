package com.smona.btwriter.order.adapter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.order.bean.OrderBean;
import com.smona.btwriter.order.holder.OrderHolder;

public class OrderListAdapter extends XBaseAdapter<OrderBean, OrderHolder> {

    public OrderListAdapter(int resId) {
        super(resId);
    }

    @Override
    protected void convert(OrderHolder holder, OrderBean item, int pos) {
        holder.bindViews(item);
    }
}
