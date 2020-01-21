package com.smona.btwriter.message;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.btwriter.R;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.message.adapter.MessageAdapter;
import com.smona.btwriter.message.presenter.MessagePreseter;
import com.smona.btwriter.util.ARouterPath;

@Route(path = ARouterPath.PATH_TO_CHANGEPWD)
public class MessageActivity extends BaseLoadingPresenterActivity<MessagePreseter, MessagePreseter.IMessageView> implements MessagePreseter.IMessageView {

    private XRecyclerView xRecyclerView;
    private MessageAdapter adapter;

    @Override
    protected MessagePreseter initPresenter() {
        return new MessagePreseter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initHeader();
        initViews();
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(R.string.message_list);
    }

    private void initViews() {
        xRecyclerView = findViewById(R.id.messageList);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter(R.layout.adapter_item_message);
        xRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
