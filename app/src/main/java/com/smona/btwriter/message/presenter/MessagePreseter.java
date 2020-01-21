package com.smona.btwriter.message.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.message.model.MessageModel;

public class MessagePreseter extends BasePresenter<MessagePreseter.IMessageView> {
    private MessageModel messageModel = new MessageModel();

    public interface IMessageView extends ICommonView {

    }
}
