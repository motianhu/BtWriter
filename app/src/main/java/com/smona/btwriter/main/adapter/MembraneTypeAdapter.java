package com.smona.btwriter.main.adapter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.main.bean.MembraneBean;
import com.smona.btwriter.main.holder.MembraneHolder;

public class MembraneTypeAdapter extends XBaseAdapter<MembraneBean, MembraneHolder> {

    public MembraneTypeAdapter(int resId) {
        super(resId);
    }

    @Override
    protected void convert(MembraneHolder holder, MembraneBean item, int pos) {
        holder.bindViews(item);
    }
}
