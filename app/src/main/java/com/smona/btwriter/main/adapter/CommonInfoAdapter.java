package com.smona.btwriter.main.adapter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.main.bean.ParamInfo;
import com.smona.btwriter.main.holder.ParamInfoHolder;

public class CommonInfoAdapter extends XBaseAdapter<ParamInfo, ParamInfoHolder> {

    public CommonInfoAdapter(int resId) {
        super(resId);
    }

    @Override
    protected void convert(ParamInfoHolder holder, ParamInfo item, int pos) {
        holder.bindViews(item);
    }
}