package com.smona.btwriter.bluetoothspp2;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.smona.btwriter.R;
import com.smona.btwriter.ble.BleAdapter;
import com.smona.btwriter.ble.BleDetailActivity;
import com.smona.btwriter.ble.BleTool;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/***
 * 搜索蓝牙
 */
public class BleSppSearchActivity extends BleBaseActivity {

    private ListView mLvBle;
    private ImageView mImgSwitch;
    private TextView mTvTitle;
    private BluetoothAdapter BTAdapter;

    private List<BluetoothDevice> mDeviceList = new ArrayList<>();//2.0蓝牙列表
    private List<SearchResult> mBleList = new ArrayList<>();//蓝牙列表
    private SPPAdapter mAdapter;
    private BleAdapter mBleAdapter;
    private int mVersionState = 1;//当前版本，1：2.0蓝牙； 2：4.0蓝牙
    private boolean isRegister = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_spp_search);
        mLvBle = (ListView) findViewById(R.id.lv_ble);
        mAdapter = new SPPAdapter(this);
        mAdapter.setData(mDeviceList);
        mBleAdapter = new BleAdapter(this);
        mBleAdapter.setData(mBleList);
        mTvTitle = (TextView) findViewById(R.id.tv_title);

        mLvBle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mVersionState == 1) {
                    if (BTAdapter != null)
                        BTAdapter.cancelDiscovery();
                    if (mDeviceList.size() < i)
                        return;
//                showToast("点击了第 " + i + " 个");
                    BluetoothDevice device = mDeviceList.get(i);
                    bondBT(device);
                } else {
                    if (mBleList.size() < i)
                        return;
                    SearchResult device = mBleList.get(i);
                    BleDetailActivity.startActivity(BleSppSearchActivity.this, device.getAddress(), device.getName());
                }

            }
        });
        if (Build.VERSION.SDK_INT >= 6.0) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1000);
        }
        findViewById(R.id.img_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVersionState = mVersionState == 1 ? 2 : 1;
                if (mVersionState == 1) {
                    mTvTitle.setText("SPP设备列表");
                } else {
                    mTvTitle.setText("Ble设备列表");
                }
                search();
            }
        });
        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
        search();
    }

    private void search() {
        if (mVersionState == 1) {
            BleTool.getClient().stopSearch();
            mLvBle.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            initSPP();
            searchSPP();
        } else {
            releaseSPP();
            searchBle();
            mLvBle.setAdapter(mBleAdapter);
            mBleAdapter.notifyDataSetChanged();
        }
    }


    /***
     * 初始化SPP
     */
    private void initSPP() {
        if (!isRegister) {
            registerBTReceiver();
        }
        checkBle();
        isRegister = true;
    }

    /***
     * 释放SPP
     */
    private void releaseSPP() {
        if (BTAdapter != null) {
            BTAdapter.cancelDiscovery();
        }
        if (isRegister) {
            unregisterReceiver(BTReceive);
        }
        isRegister = false;
    }

    /**
     * 注册广播
     */
    public void registerBTReceiver() {
        // 设置广播信息过滤
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);   //发现蓝牙事件
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);//蓝牙扫描结束事件
//        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);//状态改变
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        // 注册广播接收器，接收并处理搜索结果
        registerReceiver(BTReceive, intentFilter);
    }

    /***
     * 检查蓝牙
     */
    public void checkBle() {
        BTAdapter = BluetoothAdapter.getDefaultAdapter();
        mDeviceList.clear();
        mAdapter.notifyDataSetChanged();
        if (BTAdapter != null) {
            if (!BTAdapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // 设置蓝牙可见性，最多300秒
                intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(intent);
            } else {
                addPairedDevice();
//                BTAdapter.startDiscovery();
            }
        } else {
            Toast.makeText(this, "本地设备驱动异常!", Toast.LENGTH_SHORT).show();
        }
    }


    /***
     * 搜索SPP设备
     */
    public void searchSPP() {
        if (BTAdapter == null) {
            return;
        }
        if (BTAdapter.isDiscovering()) {
            return;
        }
        BTAdapter.startDiscovery();
    }

    /**
     * 广播接收者
     */
    private BroadcastReceiver BTReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mVersionState == 2) {
                return;
            }
            String action = intent.getAction();
            //打印Action，调试使用

            Log.e("BroadcastReceiver", "BluetoothDevice.ACTION_FOUND");
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Log.e("BroadcastReceiver", "BluetoothDevice.ACTION_FOUND");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                for (BluetoothDevice d : mDeviceList) {
                    if (TextUtils.isEmpty(d.getAddress()) || d.getAddress().equalsIgnoreCase(device.getAddress())) {
                        return;
                    }
                }
                mDeviceList.add(device);
                mAdapter.notifyDataSetChanged();
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice blnDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                setProgressMsg("正在配对..");
                switch (blnDevice.getBondState()) {
                    case BluetoothDevice.BOND_BONDING://正在配对
                        setProgressMsg("正在配对..");
                        break;
                    case BluetoothDevice.BOND_BONDED://配对结束
                        mAdapter.notifyDataSetChanged();
                        setProgressMsg("配对完成,开始连接..");
                        startActivity(blnDevice);
                        break;
                    case BluetoothDevice.BOND_NONE://取消配对/未配对
                        showToast("配对失败!");
                        for (BluetoothDevice d : mDeviceList) {
                            if (TextUtils.isEmpty(d.getAddress()) && d.getAddress().equalsIgnoreCase(blnDevice.getAddress())) {
                                mAdapter.notifyDataSetChanged();
                                return;
                            }
                        }
                        dismissProgressDialog();
                    default:
                        break;
                }
            } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                Log.e("BroadcastReceiver", "BluetoothAdapter.ACTION_BOND_STATE_CHANGED");
            }
        }
    };

    /**
     * 绑定蓝牙
     */
    private void bondBT(BluetoothDevice device) {
        showProgressDialog("配对蓝牙开始");
        // 搜索蓝牙设备的过程占用资源比较多，一旦找到需要连接的设备后需要及时关闭搜索
        // 获取蓝牙设备的连接状态
        int connectState = device.getBondState();
        switch (connectState) {
            // 未配对
            case BluetoothDevice.BOND_NONE:
                setProgressMsg("开始配对");
                try {
                    Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                    createBondMethod.invoke(device);
                } catch (Exception e) {
                    showToast("配对失败！");
                    dismissProgressDialog();
                    e.printStackTrace();
                }
                break;
            // 已配对
            case BluetoothDevice.BOND_BONDED:
                //进入连接界面
                startActivity(device);
                break;
        }

    }

    private void startActivity(BluetoothDevice device) {
        dismissProgressDialog();
        Intent intent = new Intent(BleSppSearchActivity.this, SPPOperationActivity.class);
        intent.putExtra("device", device);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    /**
     * 增加配对设备
     */
    private void addPairedDevice() {
        Set<BluetoothDevice> pairedDevices = BTAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String str = device.getName() + "|" + device.getAddress();
                mDeviceList.add(device);
            }
        }
    }

    /***
     * 查找ble蓝牙
     */
    private void searchBle() {
        mBleList.clear();
        mBleAdapter.notifyDataSetChanged();
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(5000, 2)   // 先扫BLE设备3次，每次3s
                .build();
        BleTool.getClient().search(request, new SearchResponse() {
            private Map<String, String> existingMap = new HashMap<>();

            @Override
            public void onSearchStarted() {

            }

            @Override
            public void onDeviceFounded(SearchResult device) {
                if (existingMap.containsKey(device.getAddress())) {
                    return;
                }
                existingMap.put(device.getAddress(), device.getAddress());
                mBleList.add(device);
                mBleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSearchStopped() {

            }

            @Override
            public void onSearchCanceled() {

            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (BTAdapter != null)
            BTAdapter.cancelDiscovery();
        BleTool.getClient().stopSearch();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
        releaseSPP();
    }
}
