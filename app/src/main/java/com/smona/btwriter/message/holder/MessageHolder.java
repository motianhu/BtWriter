package com.smona.btwriter.message.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.common.XViewHolder;
import com.smona.btwriter.message.bean.MessageBean;

public class MessageHolder extends XViewHolder {

    private TextView titleTv;
    private TextView contentTv;
    private TextView timeTv;
    public ImageView selectedIv;

    public MessageHolder(View itemView) {
        super(itemView);
        titleTv = itemView.findViewById(R.id.title);
        contentTv = itemView.findViewById(R.id.content);
        timeTv = itemView.findViewById(R.id.time);
        selectedIv = itemView.findViewById(R.id.selected);
    }

    public void bindView(MessageBean messageBean) {
        titleTv.setText(messageBean.getTitle());
        contentTv.setText(messageBean.getContent());
        timeTv.setText(messageBean.getCreateTime());
        selectedIv.setSelected(messageBean.isSelected());
        selectedIv.setOnClickListener(v->clickSelect(messageBean));
    }

    private void clickSelect(MessageBean item) {
        item.setSelected(!item.isSelected());
        selectedIv.setSelected(item.isSelected());
    }
}
