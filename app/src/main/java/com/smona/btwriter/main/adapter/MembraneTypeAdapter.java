package com.smona.btwriter.main.adapter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.main.bean.MembraneBean;
import com.smona.btwriter.main.holder.MembraneHolder;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;

public class MembraneTypeAdapter extends XBaseAdapter<MembraneBean, MembraneHolder> {

    public MembraneTypeAdapter(int resId) {
        super(resId);
    }

    @Override
    protected void convert(MembraneHolder holder, MembraneBean item, int pos) {
        holder.bindViews(item);
        holder.itemView.setOnClickListener(v -> ARouterManager.getInstance().gotoActivityWithInt(ARouterPath.PATH_TO_BRAND, ARouterPath.PATH_TO_BRAND, item.getType()));
    }
}
