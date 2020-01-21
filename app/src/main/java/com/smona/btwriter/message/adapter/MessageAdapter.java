package com.smona.btwriter.message.adapter;

import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.message.bean.MessageBean;
import com.smona.btwriter.message.holder.MessageHolder;

public class MessageAdapter extends XBaseAdapter<MessageBean, MessageHolder> {

    public MessageAdapter(int resId) {
        super(resId);
    }

    @Override
    protected void convert(MessageHolder holder, MessageBean item, int pos) {

    }
}
