package com.smona.btwriter.message;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.base.ui.activity.BasePresenterActivity;
import com.smona.base.ui.activity.BaseUiActivity;
import com.smona.btwriter.R;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.message.bean.MessageBean;
import com.smona.btwriter.message.presenter.MessagePresenter;
import com.smona.btwriter.util.ARouterPath;

@Route(path = ARouterPath.PATH_TO_MESSAGEDETAIL)
public class MessageActivity extends BaseLoadingPresenterActivity<MessagePresenter, MessagePresenter.IMessageView> implements MessagePresenter.IMessageView {
    private int id;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initSerializable();
        initHeader();
        initViews();
    }

    private void initSerializable() {
        id = getIntent().getIntExtra(ARouterPath.PATH_TO_MESSAGEDETAIL, -1);
        if (id == -1) {
            finish();
        }
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(R.string.message_detail);
    }

    private void initViews() {
        initExceptionProcess(findViewById(R.id.loadingresult), findViewById(R.id.contentll));
    }

    private void refreshView(MessageBean messageBean) {
        TextView titleTv = findViewById(R.id.name);
        TextView timeTv = findViewById(R.id.date);
        TextView contentTv = findViewById(R.id.content);
        titleTv.setText(messageBean.getTitle());
        timeTv.setText(messageBean.getCreateTime());
        contentTv.setText(messageBean.getContent());
    }

    @Override
    protected void initData() {
        super.initData();
        requestMessage();
    }

    protected void requestMessage() {
        mPresenter.requestMessage(id);
    }

    @Override
    protected MessagePresenter initPresenter() {
        return new MessagePresenter();
    }

    @Override
    public void onMessage(MessageBean messageBean) {
        doSuccess();
        refreshView(messageBean);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        onError(api, errCode, errInfo, this::requestMessage);
    }
}
