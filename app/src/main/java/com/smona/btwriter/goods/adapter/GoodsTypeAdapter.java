package com.smona.btwriter.goods.adapter;
import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.goods.bean.GoodsTypeBean;
import com.smona.btwriter.goods.holder.GoodsTypeHolder;

public class GoodsTypeAdapter extends XBaseAdapter<GoodsTypeBean, GoodsTypeHolder> {

    public GoodsTypeAdapter(int resId) {
        super(resId);
    }
    @Override
    protected void convert(GoodsTypeHolder holder, GoodsTypeBean item, int pos) {
        holder.bindViews(item);
        holder.itemView.setOnClickListener(v->clickType(item));
    }

    private void clickType(GoodsTypeBean selectedBean) {
        for(GoodsTypeBean typeBean: mDataList) {
            typeBean.setSelected(selectedBean.getId() == typeBean.getId());
        }
        notifyDataSetChanged();
    }

    public GoodsTypeBean getSelectedId() {
        for(GoodsTypeBean typeBean: mDataList) {
            if(typeBean.isSelected()) {
                return typeBean;
            }
        }
        return null;
    }
}