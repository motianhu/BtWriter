package com.smona.btwriter.goods.holder;

import android.view.View;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.common.XViewHolder;
import com.smona.btwriter.goods.bean.GoodsTypeBean;

public class GoodsTypeHolder extends XViewHolder {

    private TextView typeNameTv;

    public GoodsTypeHolder(View itemView) {
        super(itemView);
        typeNameTv = itemView.findViewById(R.id.tv_goods_type);
    }

    public void bindViews(GoodsTypeBean item) {
        typeNameTv.setText(item.getTypeName());
        typeNameTv.setSelected(item.isSelected());
    }
}
