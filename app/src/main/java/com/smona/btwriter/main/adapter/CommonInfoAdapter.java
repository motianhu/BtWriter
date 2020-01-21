package com.smona.btwriter.main.adapter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.main.bean.CommonInfo;
import com.smona.btwriter.main.holder.CommonInfoHolder;

public class CommonInfoAdapter extends XBaseAdapter<CommonInfo, CommonInfoHolder> {

    public CommonInfoAdapter(int resId) {
        super(resId);
    }

    @Override
    protected void convert(CommonInfoHolder holder, CommonInfo item, int pos) {

    }
}