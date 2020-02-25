package com.smona.btwriter.brand.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.brand.bean.BrandBean;
import com.smona.btwriter.common.XViewHolder;
import com.smona.image.loader.ImageLoaderDelegate;

public class BrandHolder extends XViewHolder {
    private ImageView iconIv;
    private TextView titleTv;

    public BrandHolder(View itemView) {
        super(itemView);
        iconIv = itemView.findViewById(R.id.icon);
        titleTv = itemView.findViewById(R.id.title);
    }

    public void bindViews(BrandBean brandBean) {
        ImageLoaderDelegate.getInstance().showImage(brandBean.getBrandImg(), iconIv, 0);
        titleTv.setText(brandBean.getBrandName());
    }
}
