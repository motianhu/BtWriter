package com.inuker.bluetooth.library.connect.response;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Build;
import android.os.Handler;
import android.print.PageRange;
import android.util.Log;

import com.inuker.bluetooth.library.connect.listener.IBluetoothGattResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;

/**
 * Created by dingjikerbo on 2016/8/25.
 */
public class BluetoothGattResponse extends BluetoothGattCallback {

    private IBluetoothGattResponse response;
    private int status;
    private int newStatus;

    public BluetoothGattResponse(IBluetoothGattResponse response) {
        this.response = response;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        this.status = status;
        this.newStatus = newState;

        BluetoothLog.v("status=" + status + " newState=" + newState);
        if (status == 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            boolean ret = gatt.requestMtu(180);
            Log.e("BLE", "requestMTU " + 180 + " ret=" + ret + " " + status);
        } else {

            response.onConnectionStateChange(status, newState);
        }
    }

    @Override
    public void onServicesDiscovered(final BluetoothGatt gatt, final int status) {
        response.onServicesDiscovered(status);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                if (status == 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                    boolean ret = gatt.requestMtu(100);
////                    Log.e("BLE", "requestMTU " + 200 + " ret=" + ret + " " + status);
////                }
//            }
//        }, 1000);

    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        response.onCharacteristicRead(characteristic, status, characteristic.getValue());
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        response.onCharacteristicWrite(characteristic, status, characteristic.getValue());
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        response.onCharacteristicChanged(characteristic, characteristic.getValue());
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        response.onDescriptorWrite(descriptor, status);
    }

    @Override
    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        response.onDescriptorRead(descriptor, status, descriptor.getValue());
    }

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        response.onReadRemoteRssi(rssi, status);
    }

    @Override
    public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
        super.onMtuChanged(gatt, mtu, status);
        BluetoothLog.e("onMtuChanged " + mtu);
        response.onConnectionStateChange(status, newStatus);
    }
}
