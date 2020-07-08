package com.smona.btwriter.bluetooth.transport;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.smona.btwriter.common.exception.AppContext;
import com.smona.btwriter.util.CommonUtil;

import java.io.IOException;
import java.util.UUID;

public class ConnectService {

    private OnConnectListener onServiceListener;

    private BluetoothSocket bluetoothSocket;
    private BluetoothDevice bluetoothDevice;

    private ConnectService() {
    }

    private static class ParamHolder {
        private static ConnectService paramCenter = new ConnectService();
    }

    public static ConnectService getInstance() {
        return ConnectService.ParamHolder.paramCenter;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
        connectBluetooth();
    }

    public OnConnectListener getOnServiceListener() {
        return onServiceListener;
    }

    public void setOnServiceListener(OnConnectListener onServiceListener) {
        this.onServiceListener = onServiceListener;
    }

    public void connectBluetooth() {
        if(bluetoothDevice == null) {
            return;
        }
        closeSocket();
        ConnectionThread connectionThread = new ConnectionThread();
        connectionThread.start();
    }

    /**
     * 开启客户端
     */
    private class ConnectionThread extends Thread {
        public void run() {
            try {
                //创建一个Socket连接：只需要服务器在注册时的UUID号
                bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                //连接
                bluetoothSocket.connect();
                if(onServiceListener != null) {
                    onServiceListener.onConnect(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
                if(onServiceListener != null) {
                    onServiceListener.onConnect(false);
                }
            }
        }
    }

    public boolean isConnecting() {
        return bluetoothSocket != null && bluetoothSocket.isConnected();
    }

    public BluetoothSocket getBluetoothSocket() {
        return bluetoothSocket;
    }

    private void closeSocket() {
        if(bluetoothSocket == null) {
            return;
        }
        try {
            bluetoothSocket.close();
            bluetoothSocket = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bluetoothSocket = null;
        }
    }
}
