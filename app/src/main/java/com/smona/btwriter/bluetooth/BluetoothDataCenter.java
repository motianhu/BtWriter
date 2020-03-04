package com.smona.btwriter.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.smona.btwriter.R;
import com.smona.btwriter.ble.BleTool;
import com.smona.btwriter.common.exception.AppContext;
import com.smona.btwriter.notify.NotifyCenter;
import com.smona.btwriter.notify.event.BluetoothChangeEvent;
import com.smona.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothDataCenter {
    //蓝牙适配器
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice currentBluetoothDevice;

    private BluetoothDataCenter() {
    }

    private static class ParamHolder {
        private static BluetoothDataCenter paramCenter = new BluetoothDataCenter();
    }

    public static BluetoothDataCenter getInstance() {
        return BluetoothDataCenter.ParamHolder.paramCenter;
    }

    /**
     * 初始化蓝牙中心
     * @param context
     */
    public void initBlueDataCenter(Context context) {
        registerBluetoothReceiver(context);
    }

    /**
     * 注册监听蓝牙变化
     */
    private List<OnBluetoothListener> listenerList = new ArrayList<>();

    public void addBluetoothChangeListener(OnBluetoothListener listener) {
        listenerList.add(listener);
    }

    public void removeBluetoothChangeListener(OnBluetoothListener listener) {
        listenerList.remove(listener);
    }

    public void setCurrentBluetoothDevice(BluetoothDevice currentBluetoothDevice) {
        this.currentBluetoothDevice = currentBluetoothDevice;
        NotifyCenter.getInstance().postEvent(new BluetoothChangeEvent());
    }

    public String getCurrentDeviceName() {
        if(currentBluetoothDevice == null) {
            return AppContext.getAppContext().getResources().getString(R.string.bluetooth_not_match);
        } else {
            return TextUtils.isEmpty(currentBluetoothDevice.getName()) ? currentBluetoothDevice.getAddress():currentBluetoothDevice.getName();
        }
    }

    public String getCurrentDeviceAddress() {
        if(currentBluetoothDevice == null) {
            return "";
        } else {
            return currentBluetoothDevice.getAddress();
        }
    }

    public BluetoothDevice getCurrentBluetoothDevice() {
        return currentBluetoothDevice;
    }

    /**
     * 注册广播
     */
    private void registerBluetoothReceiver(Context context) {
        // 设置广播信息过滤
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);   //发现蓝牙事件
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);//蓝牙扫描结束事件
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);//状态改变
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        // 注册广播接收器，接收并处理搜索结果
        context.registerReceiver(bthReceiver, intentFilter);
    }

    private BroadcastReceiver bthReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //打印Action，调试使用
            Logger.d("motianhu", "action: " + action);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                for(OnBluetoothListener listener: listenerList) {
                    listener.onNewDevice(device);
                }
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice blnDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                for(OnBluetoothListener listener: listenerList) {
                    listener.onStatusChange(OnBluetoothListener.STATUS_MATCHING);
                }
                switch (blnDevice.getBondState()) {
                    case BluetoothDevice.BOND_BONDING://正在配对
                        for(OnBluetoothListener listener: listenerList) {
                            listener.onStatusChange(OnBluetoothListener.STATUS_MATCHING);
                        }
                        break;
                    case BluetoothDevice.BOND_BONDED://配对结束
                        for(OnBluetoothListener listener: listenerList) {
                            listener.onStatusChange(OnBluetoothListener.STATUS_CONNECTING);
                        }
                        break;
                    case BluetoothDevice.BOND_NONE://取消配对/未配对
                        for(OnBluetoothListener listener: listenerList) {
                            listener.onStatusChange(OnBluetoothListener.STATUS_FAILED);
                        }
                    default:
                        break;
                }
            } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                Log.e("motianhu", "BluetoothAdapter.ACTION_BOND_STATE_CHANGED");
            }
        }
    };

    public void startSearch(Context context) {
        //停止之前的搜索
        BleTool.getClient().stopSearch();
        //检查已配对的
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBluetoothEnv(context);
        //启动新的搜索
        searchSPP();
    }

    /***
     * 搜索SPP设备
     */
    private void searchSPP() {
        if (bluetoothAdapter == null) {
            return;
        }
        if (bluetoothAdapter.isDiscovering()) {
            return;
        }
        bluetoothAdapter.startDiscovery();
    }

    /***
     * 检查蓝牙
     */
    private void checkBluetoothEnv(Context context) {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled()) {
                Intent intent = new Intent(android.bluetooth.BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // 设置蓝牙可见性，最多300秒
                intent.putExtra(android.bluetooth.BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                context.startActivity(intent);
            } else {
                addPairedDevice();
            }
        } else {
            Toast.makeText(context, "本地设备驱动异常!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 增加配对设备
     */
    private void addPairedDevice() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                for(OnBluetoothListener listener: listenerList) {
                    listener.onPairDevice(device);
                }
            }
        }
    }
}
