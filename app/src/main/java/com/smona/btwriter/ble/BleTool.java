package com.smona.btwriter.ble;

import com.inuker.bluetooth.library.BluetoothClient;
import com.smona.btwriter.common.exception.AppContext;

/**
 * Created by chen on 2018/6/13.
 * 4.0设备蓝牙
 */
public class BleTool {
    private static BluetoothClient mClient;

    public static BluetoothClient getClient() {
        if (mClient == null) {
            mClient = new BluetoothClient(AppContext.getAppContext());
        }
        return mClient;
    }

}
