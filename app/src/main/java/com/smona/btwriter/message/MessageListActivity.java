package com.smona.btwriter.message;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.btwriter.R;
import com.smona.btwriter.common.CommonItemDecoration;
import com.smona.btwriter.language.BaseLoadingPresenterActivity;
import com.smona.btwriter.message.adapter.MessageAdapter;
import com.smona.btwriter.message.bean.MessageBean;
import com.smona.btwriter.message.presenter.MessageListPreseter;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;

import java.util.List;

@Route(path = ARouterPath.PATH_TO_MESSAGELIST)
public class MessageListActivity extends BaseLoadingPresenterActivity<MessageListPreseter, MessageListPreseter.IMessageView> implements MessageListPreseter.IMessageView {

    private TextView rightTv;

    private XRecyclerView xRecyclerView;
    private MessageAdapter adapter;

    @Override
    protected MessageListPreseter initPresenter() {
        return new MessageListPreseter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_list;
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
        rightTv = findViewById(R.id.rightTv);
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText(R.string.select);
    }

    private void initViews() {
        xRecyclerView = findViewById(R.id.messageList);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonItemDecoration spacesItemDecoration = new CommonItemDecoration(0, this.getResources().getDimensionPixelSize(R.dimen.dimen_6dp));
        xRecyclerView.addItemDecoration(spacesItemDecoration);
        adapter = new MessageAdapter(R.layout.adapter_item_message);
        xRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.requestMessageList();
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        CommonUtil.showToastByFilter(errCode, errInfo);
    }

    @Override
    public void onMessageList(List<MessageBean> list) {
        adapter.setNewData(list);
    }

    @Override
    public void onMessageDelete(List<String> list) {
        hideLoadingDialog();
        adapter.deleteMessage(list);
    }
}
