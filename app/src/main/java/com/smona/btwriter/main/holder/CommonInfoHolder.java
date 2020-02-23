package com.smona.btwriter.main.holder;

import android.view.View;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.common.XViewHolder;
import com.smona.btwriter.main.bean.CommonInfo;

public class CommonInfoHolder extends XViewHolder {

    private TextView titleTv;
    private TextView speedTv;
    private TextView pressTv;
    private View editView;
    private View deleteView;
    private View useView;

    public CommonInfoHolder(View itemView) {
        super(itemView);
        titleTv = itemView.findViewById(R.id.title);
        speedTv = itemView.findViewById(R.id.speed);
        pressTv = itemView.findViewById(R.id.press);
        editView = itemView.findViewById(R.id.edit);
        deleteView = itemView.findViewById(R.id.delete);
        useView = itemView.findViewById(R.id.use);
    }

    public void bindViews(CommonInfo item) {
        titleTv.setText(item.getTitle());
        speedTv.setText(item.getSpeed() + "");
        pressTv.setText(item.getPress() + "");
        editView.setOnClickListener(v-> clickEdit());
        deleteView.setOnClickListener(v-> clickDelete());
        useView.setOnClickListener(v-> clickUse());
    }

    private void clickEdit() {

    }

    private void clickDelete() {

    }

    private void clickUse() {

    }
}
