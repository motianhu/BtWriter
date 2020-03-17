package com.smona.btwriter.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.base.ui.activity.BaseUiActivity;
import com.smona.btwriter.R;
import com.smona.btwriter.bluetooth.adapter.BluetoothListAdapter;
import com.smona.btwriter.bluetooth.transport.ConnectService;
import com.smona.btwriter.bluetooth.transport.OnConnectListener;
import com.smona.btwriter.common.CommonItemDecoration;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;
import com.smona.logger.Logger;

import java.lang.reflect.Method;

@Route(path = ARouterPath.PATH_TO_BLUETOOTH_LIST)
public class BlueToothListActivity extends BaseUiActivity implements OnBluetoothListener {

    private XRecyclerView xRecyclerView;
    private BluetoothListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bluetooth_list;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initHeader();
        initViews();
        initPermissions();
        BluetoothDataCenter.getInstance().addBluetoothChangeListener(this);
        BluetoothDataCenter.getInstance().startSearch(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BluetoothDataCenter.getInstance().removeBluetoothChangeListener(this);
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> finish());
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(R.string.select_bluetooth);
        TextView rightTv = findViewById(R.id.rightTv);
        rightTv.setText(R.string.scan_blue);
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setOnClickListener(v -> clickRescan());
    }

    private void initViews(){
        xRecyclerView = findViewById(R.id.bluetoothList);
        CommonItemDecoration itemDecoration = new CommonItemDecoration(0, getResources().getDimensionPixelSize(R.dimen.dimen_3dp));
        xRecyclerView.addItemDecoration(itemDecoration);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BluetoothListAdapter(R.layout.adapter_bluetooth_item);
        adapter.setOnClickBluetoothListener(this::bondBT);
        xRecyclerView.setAdapter(adapter);
    }

    private void initPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1000);
    }

    private void clickRescan() {
        adapter.removeAllData();
        BluetoothDataCenter.getInstance().startSearch(this);
    }

    @Override
    public void onNewDevice(BluetoothDevice bluetoothDevice) {
        Logger.d("motianhu", "onNewDevice bluetoothDevice: " + bluetoothDevice);
        adapter.addNewDevice(bluetoothDevice);
    }

    @Override
    public void onPairDevice(BluetoothDevice bluetoothDevice) {
        Logger.d("motianhu", "onPairDevice bluetoothDevice: " + bluetoothDevice);
        adapter.addPairDevice(bluetoothDevice);
    }

    @Override
    public void onStatusChange(int status) {
        Logger.d("motianhu", "onStatusChange : " + status);
        adapter.notifyDataSetChanged();
    }

    private void bondBT(BluetoothDevice device) {
        // 搜索蓝牙设备的过程占用资源比较多，一旦找到需要连接的设备后需要及时关闭搜索
        // 获取蓝牙设备的连接状态
        int connectState = device.getBondState();
        switch (connectState) {
            // 未配对
            case BluetoothDevice.BOND_NONE:
                CommonUtil.showShort(this, "开始配对");
                try {
                    Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                    createBondMethod.invoke(device);
                } catch (Exception e) {
                    CommonUtil.showShort(this, "配对失败！");
                    hideLoadingDialog();
                    e.printStackTrace();
                }
                break;
            // 已配对
            case BluetoothDevice.BOND_BONDED:
                //进入连接界面
                connectBluetooth(device);
                break;
        }
    }

    private void connectBluetooth(BluetoothDevice device) {
        showLoadingDialog();
        ConnectService.getInstance().setOnServiceListener(new OnConnectListener() {
            @Override
            public void onConnect(boolean success) {
                hideLoadingDialog();
                if(success) {
                    BluetoothDataCenter.getInstance().setCurrentBluetoothDevice(device);
                    CommonUtil.showShort(BlueToothListActivity.this, "连接成功！");
                    finish();
                } else {
                    CommonUtil.showShort(BlueToothListActivity.this, "连接失败,请重新扫描设备再选择！");
                }
            }
        });
        ConnectService.getInstance().setBluetoothDevice(device);
    }
}
