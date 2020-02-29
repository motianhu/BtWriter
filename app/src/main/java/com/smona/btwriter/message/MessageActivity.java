package com.smona.btwriter.message;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.base.ui.activity.BaseUiActivity;
import com.smona.btwriter.R;
import com.smona.btwriter.message.bean.MessageBean;
import com.smona.btwriter.util.ARouterPath;

@Route(path = ARouterPath.PATH_TO_MESSAGEDETAIL)
public class MessageActivity extends BaseUiActivity {

    private MessageBean messageBean;

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
        messageBean = (MessageBean) getIntent().getSerializableExtra(ARouterPath.PATH_TO_MESSAGEDETAIL);
        if (messageBean == null) {
            finish();
        }
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(R.string.message_detail);
    }

    private void initViews() {
        TextView titleTv = findViewById(R.id.name);
        TextView timeTv = findViewById(R.id.date);
        TextView contentTv = findViewById(R.id.content);
        titleTv.setText(messageBean.getTitle());
        timeTv.setText(messageBean.getCreateTime());
        contentTv.setText(messageBean.getContent());
    }
}
