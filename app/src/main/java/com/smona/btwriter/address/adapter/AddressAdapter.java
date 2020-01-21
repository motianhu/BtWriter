package com.smona.btwriter.address.adapter;

import com.smona.btwriter.address.bean.AddressBean;
import com.smona.btwriter.address.holder.AddressHolder;
import com.smona.btwriter.common.XBaseAdapter;

public class AddressAdapter extends XBaseAdapter<AddressBean, AddressHolder> {

    public AddressAdapter(int resId) {
        super(resId);
    }

    @Override
    protected void convert(AddressHolder holder, AddressBean item, int pos) {
        holder.bindView(item);
    }
}
