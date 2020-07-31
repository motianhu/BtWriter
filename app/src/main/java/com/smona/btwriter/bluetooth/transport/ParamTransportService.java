package com.smona.btwriter.bluetooth.transport;

import android.content.Context;
import android.support.annotation.RequiresPermission;

import com.smona.btwriter.R;
import com.smona.btwriter.bluetoothspp2.MsgBeen;
import com.smona.btwriter.common.exception.AppContext;
import com.smona.btwriter.util.CommonUtil;
import com.smona.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ParamTransportService {

    private OnReadListener onReadListener;

    private InputStream mInStream;
    private OutputStream mOutStream;

    public static ParamTransportService buildService(OnReadListener onReadListener) {
        return new ParamTransportService(onReadListener);
    }

    private ParamTransportService(OnReadListener onReadListener) {
        this.onReadListener = onReadListener;
    }

    public void connectBluetooth(Context context) {
        if(ConnectService.getInstance().isConnecting()) {
            startWork(context);
        } else {
            onReadListener.onCreateChannel(false);
            CommonUtil.showShort(context, R.string.blue_not_connection);
        }
    }

    private void startWork(Context context) {
        try {
            mInStream = ConnectService.getInstance().getBluetoothSocket().getInputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
            onReadListener.onCreateChannel(false);
            CommonUtil.showShort(context, R.string.blue_not_connection);
            return;
        }
        startRead();
        startWrite();
    }

    private void startRead() {
        ReadThread readThread = new ReadThread();
        readThread.start();
    }

    private void startWrite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                onReadListener.onCreateChannel(true);
            }
        }).start();
    }

    /**
     * 读取数据.参数设置需要先监听再设置，这个设置是实时反馈的。
     */
    private class ReadThread extends Thread {

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes = -1;
            while (ConnectService.getInstance().getBluetoothSocket().isConnected()) {
                try {
                    if ((bytes = mInStream.read(buffer)) > 0) {
                        byte[] nPacket = new byte[bytes];
                        System.arraycopy(buffer, 0, nPacket, 0, bytes);
                        //取得接收数据
                        MsgBeen msgBeen = new MsgBeen(nPacket, bytes);
                        processReceiveMsg(msgBeen);
                        Thread.sleep(100);
                    } else {
                        Thread.sleep(100);
                    }
                } catch (IOException e) {
                    CommonUtil.showShort(AppContext.getAppContext(), R.string.read_exception);
                    try {
                        if (mInStream != null) {
                            mInStream.close();
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                } catch (InterruptedException e) {
                    CommonUtil.showShort(AppContext.getAppContext(), R.string.thread_exception);
                    e.printStackTrace();
                }
            }
        }
    }


    private void processReceiveMsg(MsgBeen msgBeen) {
        Logger.d("motianhu", "processReceiveMsg: " + msgBeen.getStrMsg() + "," + msgBeen.getHexMsg() + ", getLastByte: " + msgBeen.getLastByte());
        if(BluetoothConnectService.INSTRUCTIONS_CONTINUE == msgBeen.getLastByte()) {
            CommonUtil.showShort(AppContext.getAppContext(), R.string.send_finish);
        }
    }

    /**
     * 发送数据
     */
    public void sendParam(int speed, int press) {
        if(speed < CommonUtil.SPEED_START || speed > CommonUtil.SPEED_END) {
            return;
        }
        if(press < CommonUtil.PRESS_START || press > CommonUtil.PRESS_END) {
            return;
        }
        String param = String.format(INSTRUCTIONS_PARAM, press, speed);
        //String param = "#G;########";
        //String param = "#F200;#####";
        //String param = "#S400;#####";
        Logger.d("motianhu", "sendParam: " + param);
        if (!ConnectService.getInstance().isConnecting()) {
            CommonUtil.showShort(AppContext.getAppContext(), R.string.device_not_connect);
            return;
        }
        try {
            byte[] data = param.getBytes();
            mOutStream = ConnectService.getInstance().getBluetoothSocket().getOutputStream();
            mOutStream.write(data);
            mOutStream.flush();
        } catch (Exception e) {
            CommonUtil.showShort(AppContext.getAppContext(), R.string.send_failed);
            e.printStackTrace();
        }
    }
    private static final String INSTRUCTIONS_PARAM = "#F%s;S%s;#";//同时发送速度和压力值
}
