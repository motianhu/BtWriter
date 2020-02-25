package com.smona.btwriter.brand.adapter;

import android.os.Bundle;

import com.smona.btwriter.brand.bean.BrandBean;
import com.smona.btwriter.brand.holder.BrandHolder;
import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.model.bean.BrandParam;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;

public class BrandAdapter extends XBaseAdapter<BrandBean, BrandHolder> {

    private int membraneType;

    public BrandAdapter(int resId) {
        super(resId);
    }

    public void setMembraneType(int membraneType) {
        this.membraneType = membraneType;
    }

    @Override
    protected void convert(BrandHolder holder, BrandBean item, int pos) {
        holder.bindViews(item);
        Bundle bundle = new Bundle();
        BrandParam param = new BrandParam();
        param.setBrandId(item.getId());
        param.setMembraneType(membraneType);
        bundle.putSerializable(BrandParam.class.getName(), param);
        holder.itemView.setOnClickListener(v -> ARouterManager.getInstance().gotoActivityBundle(ARouterPath.PATH_TO_MODEL, bundle));
    }
}
