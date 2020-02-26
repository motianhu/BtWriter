package com.smona.btwriter.main.holder;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.common.XViewHolder;
import com.smona.btwriter.main.bean.ParamInfo;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;

public class ParamInfoHolder extends XViewHolder {

    private TextView titleTv;
    private TextView speedTv;
    private TextView pressTv;
    public View editView;
    public View deleteView;
    public View useView;

    public ParamInfoHolder(View itemView) {
        super(itemView);
        titleTv = itemView.findViewById(R.id.title);
        speedTv = itemView.findViewById(R.id.speed);
        pressTv = itemView.findViewById(R.id.press);
        editView = itemView.findViewById(R.id.edit);
        deleteView = itemView.findViewById(R.id.delete);
        useView = itemView.findViewById(R.id.use);
    }

    public void bindViews(ParamInfo item) {
        titleTv.setText(item.getName());
        speedTv.setText(item.getSpeed() + "");
        pressTv.setText(item.getPressure() + "");
    }
}
