package com.smona.btwriter.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.smona.btwriter.bluetoothspp2.MsgBeen;
import com.smona.btwriter.bluetoothspp2.SPPOperationActivity;
import com.smona.btwriter.util.ToastUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BluetoothConnectService {

    private OnServiceListener onServiceListener;

    private BluetoothSocket bluetoothSocket;
    private BluetoothDevice bluetoothDevice;
    private InputStream mInStream;
    private OutputStream mOutStream;

    private int mTotalSize;
    private int mTotalSendSize = 0;//发送字节统计
    private List<MsgBeen> msgBeens = new ArrayList<>();

    public void connectBluetooth(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
        ConnectionThread connectionThread = new ConnectionThread();
        connectionThread.start();
    }

    private void startRead() {
        ReadThread readThread = new ReadThread();
        readThread.start();
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
                onServiceListener.onConnectListener(true);
                startRead();
            } catch (IOException e) {
                ToastUtil.showShort("连接异常: " + bluetoothDevice.getName() + "(" + bluetoothDevice.getAddress() + ")");
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取数据
     */
    private class ReadThread extends Thread {
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            try {
                mInStream = bluetoothSocket.getInputStream();
                onServiceListener.onReadListener(true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            while (bluetoothSocket.isConnected()) {
                try {
                    if ((bytes = mInStream.read(buffer)) > 0) {
                        byte[] nPacket = new byte[bytes];
                        System.arraycopy(buffer, 0, nPacket, 0, bytes);
                        //取得接收数据
                        MsgBeen msgBeen = new MsgBeen(nPacket, bytes);
                        msgBeens.add(msgBeen);
                        mTotalSize = mTotalSize + bytes;
                        Thread.sleep(100);
                    } else {
                        Thread.sleep(100);
                    }
                } catch (IOException e) {
                    ToastUtil.showShort("读取异常: " + bluetoothDevice.getName() + "(" + bluetoothDevice.getAddress() + ")");
                    try {
                        if (mInStream != null)
                            mInStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                } catch (InterruptedException e) {
                    ToastUtil.showShort("线程异常: " + bluetoothDevice.getName() + "(" + bluetoothDevice.getAddress() + ")");
                    e.printStackTrace();
                }
            }
        }
    }

    public interface OnServiceListener {
        void onConnectListener(boolean success);
        void onReadListener(boolean success);
    }
}
