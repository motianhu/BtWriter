package com.smona.btwriter.goods.adapter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.goods.bean.GoodsNum;
import com.smona.btwriter.goods.bean.ShoppingCardBean;
import com.smona.btwriter.goods.holder.ShoppingCardListHolder;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCardListAdapter extends XBaseAdapter<ShoppingCardBean, ShoppingCardListHolder> {

    private OnShoppingCardListener listener;

    public ShoppingCardListAdapter(int resId) {
        super(resId);
    }

    public void setListener(OnShoppingCardListener listener) {
        this.listener = listener;
    }

    @Override
    protected void convert(ShoppingCardListHolder holder, ShoppingCardBean item, int pos) {
        holder.bindViews(item);
        holder.delView.setOnClickListener(v -> clickDel(item.getId()));
    }

    private void clickDel(int id) {
        listener.onDelelte(id);
    }

    public void notifiRefresh(int id, int count) {
        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getId() == id) {
                mDataList.get(i).setAmount(count);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public void notifiDelete(int id) {
        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getId() == id) {
                mDataList.remove(i);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public List<GoodsNum> getGoodsListNum() {
        List<GoodsNum> list = new ArrayList<>();
        GoodsNum goodsNum;
        for (int i = 0; i < mDataList.size(); i++) {
            goodsNum = new GoodsNum();
            goodsNum.setGoodsId(mDataList.get(i).getId());
            goodsNum.setAmount(mDataList.get(i).getTmpCount());
            list.add(goodsNum);
        }
        return list;
    }


    public interface OnShoppingCardListener {
        void onDelelte(int id);
    }
}
