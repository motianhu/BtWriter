package com.smona.btwriter.bluetooth.transport;

import android.content.Context;

import com.smona.btwriter.R;
import com.smona.btwriter.bluetoothspp2.MsgBeen;
import com.smona.btwriter.common.exception.AppContext;
import com.smona.btwriter.util.CommonUtil;
import com.smona.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public class BluetoothConnectService {

    private OnReadListener onReadListener;

    private InputStream mInStream;
    private OutputStream mOutStream;

    private final Object mLock = new Object();
    private final AtomicBoolean pauseFlag = new AtomicBoolean(false);

    public static BluetoothConnectService buildService(OnReadListener onReadListener) {
        return new BluetoothConnectService(onReadListener);
    }

    private BluetoothConnectService(OnReadListener onReadListener) {
        this.onReadListener = onReadListener;
    }

    public void connectBluetooth(Context context) {
        if(ConnectService.getInstance().isConnecting()) {
            startRead();
        } else {
            onReadListener.onCreateChannel(false);
            CommonUtil.showShort(context, R.string.blue_not_connection);
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
            int bytes = -1;
            try {
                mInStream = ConnectService.getInstance().getBluetoothSocket().getInputStream();
                onReadListener.onCreateChannel(true);
            } catch (IOException e1) {
                e1.printStackTrace();
                onReadListener.onCreateChannel(false);
            }
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
                    CommonUtil.showShort(AppContext.getAppContext(),R.string.thread_exception);
                    e.printStackTrace();
                }
            }
        }
    }

    private void processReceiveMsg(MsgBeen msgBeen) {
        int zhiling = msgBeen.getLastByte();
        Logger.d("motianhu", "processReceiveMsg " + msgBeen.getHexMsg() + ", getLastByte: " + zhiling);
        if(BluetoothConnectService.INSTRUCTIONS_CONTINUE == zhiling) {
            synchronized (mLock) {
                pauseFlag.set(false);
                mLock.notify();
            }
        } else if(BluetoothConnectService.INSTRUCTIONS_PAUSE == zhiling) {
            pauseFlag.set(true);
        }
    }

    /**
     * 发送数据
     */
    public void sendFile(String filePath) {
        File file = new File(filePath);
        if(!(file.isFile() && file.exists())){
            return;
        }
        if (!ConnectService.getInstance().isConnecting()) {
            CommonUtil.showShort(AppContext.getAppContext(),R.string.device_not_connect);
            return;
        }
        WriteThread writeThread = new WriteThread(filePath);
        writeThread.start();
    }

    /**
     * 读取数据
     */
    private class WriteThread extends Thread {

        private String filePath;

        private WriteThread(String filePath) {
            this.filePath = filePath;
        }

        public void run() {
            File file = new File(filePath);
            byte[] buffer = new byte[200];
            FileInputStream fis = null;
            try {
                int index = -1;
                fis = new FileInputStream(file);
                mOutStream = ConnectService.getInstance().getBluetoothSocket().getOutputStream();
                while ((index = fis.read(buffer)) != -1) {
                    mOutStream.write(buffer);
                    mOutStream.flush();
                    if(pauseFlag.get()) {
                        Logger.d("motianhu", "write  wait========================== ");
                        synchronized (mLock) {
                            mLock.wait();
                        }
                    }
                    Logger.d("motianhu", "write index= " + index);
                }
                onReadListener.executeFinish(true);
            } catch (Exception e) {
                CommonUtil.showShort(AppContext.getAppContext(),"发送失败！");
                e.printStackTrace();
                onReadListener.executeFinish(false);
                Logger.d("motianhu", "wrate Exception  " + e);
            } finally {

                Logger.d("motianhu", "wrate finally  ");
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    static final byte INSTRUCTIONS_PAUSE = 0x13;//暂停发送
    static final byte INSTRUCTIONS_CONTINUE = 0x11;//继续发送。终止靠自己计算累计发送字节总数。
}
