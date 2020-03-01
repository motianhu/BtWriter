package com.smona.btwriter.message.adapter;

import android.view.View;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.message.bean.MessageBean;
import com.smona.btwriter.message.holder.MessageHolder;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends XBaseAdapter<MessageBean, MessageHolder> {

    private boolean isEditStatus = false;

    public MessageAdapter(int resId) {
        super(resId);
    }

    public boolean isEditStatus() {
        return isEditStatus;
    }

    @Override
    protected void convert(MessageHolder holder, MessageBean item, int pos) {
        holder.bindView(item);
        holder.itemView.setOnClickListener(v -> ARouterManager.getInstance().sgotoActivitySble(ARouterPath.PATH_TO_MESSAGEDETAIL, item));
        holder.selectedIv.setVisibility(isEditStatus ? View.VISIBLE : View.GONE);
    }

    public void deleteMessage() {
        for (int i = mDataList.size() - 1; i >= 0; i--) {
            if (mDataList.get(i).isSelected()) {
                mDataList.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    public void editMessage() {
        isEditStatus = !isEditStatus;
        for (MessageBean messageBean : mDataList) {
            if (!isEditStatus) {
                messageBean.setSelected(false);
            }
        }
        notifyDataSetChanged();
    }

    public List<Integer> getSelectedMessage() {
        List<Integer> ids = new ArrayList<>();
        for (MessageBean messageBean : mDataList) {
            if (messageBean.isSelected()) {
                ids.add(messageBean.getId());
            }
        }
        return ids;
    }
}
