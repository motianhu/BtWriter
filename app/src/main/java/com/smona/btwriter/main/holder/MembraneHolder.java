package com.smona.btwriter.main.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.common.XViewHolder;
import com.smona.btwriter.main.bean.MembraneBean;

public class MembraneHolder extends XViewHolder {

    private TextView titleTv;
    private ImageView iconIv;

    public MembraneHolder(View itemView) {
        super(itemView);
        titleTv = itemView.findViewById(R.id.title);
        iconIv = itemView.findViewById(R.id.icon);
    }

    public void bindViews(MembraneBean item) {
        titleTv.setText(item.getTitle());
        iconIv.setImageResource(item.getResId());
    }
}
