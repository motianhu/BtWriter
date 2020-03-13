package com.smona.btwriter.order.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.common.XViewHolder;
import com.smona.btwriter.order.bean.OrderBean;

public class OrderHolder extends XViewHolder {

    private TextView orderNoTv;
    private TextView timeTv;
    private TextView statusTv;

    public OrderHolder(View itemView) {
        super(itemView);
        orderNoTv = itemView.findViewById(R.id.orderNo);
        timeTv = itemView.findViewById(R.id.time);
        statusTv = itemView.findViewById(R.id.status);
    }

    public void bindViews(OrderBean item) {
        Context context = itemView.getContext();
        String orderNo = context.getString(R.string.order_no) + item.getOrderNo();
        orderNoTv.setText(orderNo);
        String time = context.getString(R.string.order_time) + item.getCreateTime();
        timeTv.setText(time);
        String status = context.getString(R.string.order_status_wait);
        int resId = R.drawable.bg_corner_ffcd63_10;
        if (item.isOk()) {
            resId = R.drawable.bg_corner_1fdc37_10;
            status = context.getString(R.string.order_status_ok);
        } else if (item.isRefuse()) {
            resId = R.drawable.bg_corner_f65566_10;
            status = context.getString(R.string.order_status_refuse);
        }
        statusTv.setText(status);
        statusTv.setBackgroundResource(resId);
    }
}
