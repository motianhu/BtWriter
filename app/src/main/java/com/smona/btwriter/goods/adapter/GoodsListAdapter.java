package com.smona.btwriter.goods.adapter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.goods.bean.GoodsBean;
import com.smona.btwriter.goods.bean.TwoGoodsBean;
import com.smona.btwriter.goods.holder.GoodsListHolder;

public class GoodsListAdapter extends XBaseAdapter<TwoGoodsBean, GoodsListHolder> {

    private OnClickGoodListListerner onClickGoodListListerner;

    public GoodsListAdapter(int resId) {
        super(resId);
    }

    public void setOnClickGoodListListerner(OnClickGoodListListerner onClickGoodListListerner) {
        this.onClickGoodListListerner = onClickGoodListListerner;
    }

    @Override
    protected void convert(GoodsListHolder holder, TwoGoodsBean item, int pos) {
        holder.bindViews(item, onClickGoodListListerner);
    }

    public interface OnClickGoodListListerner {
        void onClickAdd(GoodsBean goodsBean);
    }
}
