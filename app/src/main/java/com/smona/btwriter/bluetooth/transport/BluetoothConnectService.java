package com.smona.btwriter.bluetooth.transport;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.smona.btwriter.bluetoothspp2.MsgBeen;
import com.smona.btwriter.util.HexBytesUtil;
import com.smona.btwriter.util.ToastUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothConnectService {

    private OnServiceListener onServiceListener;

    private BluetoothSocket bluetoothSocket;
    private BluetoothDevice bluetoothDevice;
    private InputStream mInStream;
    private OutputStream mOutStream;

    private int mTotalSize;
    private int mTotalSendSize = 0;//发送字节统计

    public void connectBluetooth(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
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
                onServiceListener.onConnect(true);
                startRead();
            } catch (IOException e) {
                ToastUtil.showShort("连接异常: " + bluetoothDevice.getName() + "(" + bluetoothDevice.getAddress() + ")");
                e.printStackTrace();
                onServiceListener.onConnect(false);
            }
        }
    }

    private void startRead() {
        ReadThread readThread = new ReadThread();
        readThread.start();
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
                onServiceListener.onReadListener(false);
            }
            while (bluetoothSocket.isConnected()) {
                try {
                    if ((bytes = mInStream.read(buffer)) > 0) {
                        byte[] nPacket = new byte[bytes];
                        System.arraycopy(buffer, 0, nPacket, 0, bytes);
                        //取得接收数据
                        MsgBeen msgBeen = new MsgBeen(nPacket, bytes);
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


    private void processReceiveMsg(MsgBeen msgBeen) {

    }

    /**
     * 发送数据
     */
    public void sendParam(String param) {
        if (TextUtils.isEmpty(param)) {
            return;
        }
        if (bluetoothSocket == null) {
            ToastUtil.showShort("设配未连接");
            return;
        }
        try {
            byte[] data = null;
            if (HexBytesUtil.checkHexStr(param)) {
                data = HexBytesUtil.hexStringToBytes(param);
            } else {
                ToastUtil.showShort("输入的Hex字符有误");
                return;
            }
            mOutStream = bluetoothSocket.getOutputStream();
            mOutStream.write(data);
            mOutStream.flush();
            mTotalSendSize = mTotalSendSize + data.length;
        } catch (Exception e) {
            ToastUtil.showShort("发送失败！");
            e.printStackTrace();
        }
    }

    /**
     * 发送数据
     */
    public void sendMessage(boolean signal, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (bluetoothSocket == null) {
            ToastUtil.showShort("设配未连接");
            return;
        }
        try {
            byte[] data = null;
            if(signal){
                if (HexBytesUtil.checkHexStr(msg)) {
                    data = HexBytesUtil.hexStringToBytes(msg);
                } else {
                    ToastUtil.showShort("输入的Hex字符有误");
                    return;
                }
            } else {
                data = msg.getBytes();
            }
            mOutStream = bluetoothSocket.getOutputStream();
            mOutStream.write(data);
            mOutStream.flush();
            mTotalSendSize = mTotalSendSize + data.length;
        } catch (Exception e) {
            ToastUtil.showShort("发送失败！");
            e.printStackTrace();
        }
    }

    /***
     * 定时发送
     */
    private Handler mHandlerTimer = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mHandlerTimer.sendEmptyMessageDelayed(0, 100);
                    break;
            }
        }
    };

    public interface OnServiceListener {
        void onConnect(boolean success);
        void onReadListener(boolean success);
        void onReadStatus(boolean success);
    }

    public static final byte INSTRUCTIONS_PAUSE = 0x13;//暂停发送
    public static final byte INSTRUCTIONS_CONTINUE = 0x11;//继续发送。终止靠自己计算累计发送字节总数。
    public static String INSTRUCTIONS_PARAM = "#F%s;#S%s;";//同时发送压力和速度值
}
