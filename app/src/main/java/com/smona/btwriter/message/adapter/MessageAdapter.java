package com.smona.btwriter.message.adapter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.message.bean.MessageBean;
import com.smona.btwriter.message.holder.MessageHolder;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;

import java.util.List;

public class MessageAdapter extends XBaseAdapter<MessageBean, MessageHolder> {

    public MessageAdapter(int resId) {
        super(resId);
    }

    @Override
    protected void convert(MessageHolder holder, MessageBean item, int pos) {
        holder.bindView(item);
        holder.itemView.setOnClickListener(v-> ARouterManager.getInstance().sgotoActivitySble(ARouterPath.PATH_TO_MESSAGEDETAIL, item));
    }

    public void deleteMessage(List<String> ids) {
        notifyDataSetChanged();
    }

}
