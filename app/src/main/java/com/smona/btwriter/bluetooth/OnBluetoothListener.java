package com.smona.btwriter.bluetooth;

import android.bluetooth.BluetoothDevice;

public interface OnBluetoothListener {
    int STATUS_MATCHING = 1;
    int STATUS_CONNECTING = 2;
    int STATUS_FAILED = 3;
    void onNewDevice(BluetoothDevice bluetoothDevice);
    void onPairDevice(BluetoothDevice bluetoothDevice);
    void onStatusChange(int status); //1--正在配对  2--配对完成，正在连接。3--配对失败
}
