package com.smona.btwriter.model.adapter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.model.bean.ModelBean;
import com.smona.btwriter.model.holder.ModelHolder;

public class ModelAdapter extends XBaseAdapter<ModelBean, ModelHolder> {

    public ModelAdapter(int resId) {
        super(resId);
    }

    @Override
    protected void convert(ModelHolder holder, ModelBean item, int pos) {
        holder.bindViews(item);
    }
}
