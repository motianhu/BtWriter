package com.smona.btwriter.message;

import com.smona.btwriter.R;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.message.presenter.MessagePreseter;

public class MessageActivity extends BaseLoadingPresenterActivity<MessagePreseter, MessagePreseter.IMessageView> implements MessagePreseter.IMessageView {
    @Override
    protected MessagePreseter initPresenter() {
        return new MessagePreseter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
