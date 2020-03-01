package com.smona.btwriter.bluetooth;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.base.ui.activity.BaseUiActivity;
import com.smona.btwriter.R;
import com.smona.btwriter.util.ARouterPath;

@Route(path = ARouterPath.PATH_TO_BLUETOOTH_LIST)
public class BlueToothListActivity extends BaseUiActivity {

    private XRecyclerView matchRecyclerView;
    private XRecyclerView notMatchRecycerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bluetooth_list;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initHeader();
        initViews();
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> finish());
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(R.string.select_bluetooth);
    }

    private void initViews(){
        matchRecyclerView = findViewById(R.id.matchBluetoothList);
        notMatchRecycerView = findViewById(R.id.findBluetoothList);
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
