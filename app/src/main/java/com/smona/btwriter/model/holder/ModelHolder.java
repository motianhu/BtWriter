package com.smona.btwriter.model.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.common.XViewHolder;
import com.smona.btwriter.model.bean.ModelBean;
import com.smona.image.loader.ImageLoaderDelegate;

public class ModelHolder extends XViewHolder {

    private ImageView iconIv;
    private TextView titleTv;

    public ModelHolder(View itemView) {
        super(itemView);
        iconIv = itemView.findViewById(R.id.icon);
        titleTv = itemView.findViewById(R.id.title);
    }

    public void bindViews(ModelBean item) {
        ImageLoaderDelegate.getInstance().showImage(item.getPhoneImage(), iconIv, 0);
        titleTv.setText(item.getPhoneBrand() + "  " + item.getPhoneModel());
    }
}
