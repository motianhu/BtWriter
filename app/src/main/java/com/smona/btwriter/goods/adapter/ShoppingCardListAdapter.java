package com.smona.btwriter.goods.adapter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.goods.bean.ShoppingCardBean;
import com.smona.btwriter.goods.holder.ShoppingCardListHolder;

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
        holder.minusView.setOnClickListener(v -> clickModify(item.getId(), item.getAmount() - 1));
        holder.addView.setOnClickListener(v -> clickModify(item.getId(), item.getAmount() + 1));
        holder.delView.setOnClickListener(v -> clickDel(item.getId()));
    }

    private void clickModify(int id, int count) {
        listener.onModify(id, count);
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

    public interface OnShoppingCardListener {
        void onModify(int id, int count);

        void onDelelte(int id);
    }
}
