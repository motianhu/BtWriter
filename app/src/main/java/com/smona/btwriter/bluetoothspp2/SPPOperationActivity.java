package com.smona.btwriter.bluetoothspp2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.util.ARouterPath;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SPPOperationActivity extends BleBaseActivity implements CompoundButton.OnCheckedChangeListener {
    private TextView mTvReceiver, mTvTotal, mTvLog, mTvSendTotal;
    private EditText mEdtSend, mEdtTimer;
    private Switch mSwitchSendHex, mSwitchReceiverHex, mSwitchTimer;

    private BluetoothSocket BTSocket;
    private BluetoothDevice device;

    private long mTimer = 0;

    private int mTotalSendSize = 0;//发送字节统计
    private List<MsgBeen> msgBeens = new ArrayList<>();
    Boolean bConnect = false;
    private InputStream mInStream;
    private OutputStream mOutStream;
    private MsgAdapter mAdapter;
    private ListView mLvMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_operation);
        mTvReceiver = (TextView) findViewById(R.id.tv_receive);
        mTvTotal = (TextView) findViewById(R.id.tv_total);
        mTvLog = (TextView) findViewById(R.id.tv_log);
        mTvSendTotal = (TextView) findViewById(R.id.tv_send_total);
        mEdtSend = (EditText) findViewById(R.id.edt_send);
        mEdtTimer = (EditText) findViewById(R.id.edt_timer);
//        mTvReceiver.setMovementMethod(ScrollingMovementMethod.getInstance());
        mSwitchSendHex = (Switch) findViewById(R.id.switch_hex_send);
        mSwitchReceiverHex = (Switch) findViewById(R.id.switch_hex_receiver);
        mSwitchTimer = (Switch) findViewById(R.id.switch_timer);
        mLvMsg = (ListView) findViewById(R.id.lv_msg);
        mAdapter = new MsgAdapter(this);
        mAdapter.setData(msgBeens);
        mLvMsg.setAdapter(mAdapter);
        mSwitchReceiverHex.setOnCheckedChangeListener(this);
        mSwitchSendHex.setOnCheckedChangeListener(this);
        mSwitchTimer.setOnCheckedChangeListener(this);
        registerBTReceiver();
        device = getIntent().getParcelableExtra("device");
        if (device == null) {
            return;
        }
        TextView tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(null == device.getName() ? " " : device.getName());
        connectDevice();
    }


    /**
     * 注册广播
     */
    public void registerBTReceiver() {
        // 设置广播信息过滤
        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
//        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
//        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        // 注册广播接收器，接收并处理搜索结果
        registerReceiver(BTReceive, intentFilter);
    }

    private void connectDevice() {
        try {
            showProgressDialog("连接蓝牙...");
            clientThread clientConnectThread = new clientThread();
            clientConnectThread.start();
        } catch (Exception e) {
            showToast("连接异常，请退出本界面再次尝试！");
            showLog("连接异常");
            dismissProgressDialog();
            e.printStackTrace();
        }
    }

//
    /**
     * 广播接收者
     */
    private BroadcastReceiver BTReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //打印Action，调试使用
            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                // 获取蓝牙设备的连接状态
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDING://正在配对
//                        setProgressMsg("正在配对..");
//
////                        onRegisterBltReceiver.onBltIng(device);
                        break;
                    case BluetoothDevice.BOND_BONDED://配对结束
//                        setProgressMsg("配对完成,开始连接..");

                        break;
                    case BluetoothDevice.BOND_NONE://取消配对/未配对
//                        showToast("配对失败，请关闭页面后重新尝试");
//                        dismissProgressDialog();
                    default:
                        break;
                }

            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            }
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.switch_hex_send:

                break;
            case R.id.switch_hex_receiver:
                mAdapter.setHex(mSwitchReceiverHex.isChecked());
                setReceiverText();
                break;
            case R.id.switch_timer:
                if (compoundButton.isChecked()) {
                    if (TextUtils.isEmpty(mEdtTimer.getText().toString())) {
                        showToast("请设置时间");
                        compoundButton.setChecked(true);
                    } else {
                        mTimer = Long.valueOf(mEdtTimer.getText().toString());
                        mHandlerTimer.sendEmptyMessageDelayed(0, mTimer);
                    }
                } else {
                    mHandlerTimer.removeMessages(0);
                    mHandlerTimer.removeCallbacksAndMessages(null);
                }
                break;
        }
    }

    /**
     * 开启客户端
     */
    private class clientThread extends Thread {
        public void run() {
            try {
                //创建一个Socket连接：只需要服务器在注册时的UUID号
                BTSocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                //连接
                BTSocket.connect();
                setProgressMsg("连接成功");
                showLog("连接成功");
                bConnect = true;
                dismissProgressDialog();
                //启动接受数据
                readThread mreadThread = new readThread();
                mreadThread.start();
            } catch (IOException e) {
                showToast("连接异常，请退出本界面再次尝试！");
                showLog("连接异常");
                dismissProgressDialog();
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取数据
     */
    private class readThread extends Thread {
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
//            InputStream is = null;
            try {
                mInStream = BTSocket.getInputStream();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            while (bConnect) {
                try {
                    if ((bytes = mInStream.read(buffer)) > 0) {
                        byte[] nPacket = new byte[bytes];
                        System.arraycopy(buffer, 0, nPacket, 0, bytes);
                        //取得接收数据
                        MsgBeen msgBeen = new MsgBeen(nPacket, bytes);
                        msgBeens.add(msgBeen);
                        mTotalSize = mTotalSize + bytes;
                        setReceiverText();
                        Thread.sleep(100);
                    } else {
                        Thread.sleep(100);
                    }
                } catch (IOException e) {
                    showToast("连接异常，请退出本界面再次尝试！");
                    showLog("连接异常");
                    bConnect = false;
                    try {
                        if (mInStream != null)
                            mInStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                } catch (InterruptedException e) {
                    showToast("连接异常，请退出本界面再次尝试！");
                    showLog("连接异常");
                    e.printStackTrace();
                }
            }

        }
    }

    private int mTotalSize;

    //设置接收数据
    private void setReceiverText() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                mTvTotal.setText("接收字节统计：" + mTotalSize);
            }
        });
        if (true) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        int size = 0;
        if (mSwitchReceiverHex.isChecked()) {
            for (MsgBeen msg : msgBeens) {
                sb.append("---接收 ");
                sb.append(msg.getSize());
                sb.append(" 字节--\n");
                sb.append(msg.getHexMsg());
                sb.append("\n");
                size = size + msg.getSize();
            }

        } else {
            for (MsgBeen msg : msgBeens) {
                sb.append("---接收 ");
                sb.append(msg.getSize());
                sb.append(" 字节--\n");
                sb.append(msg.getStrMsg());
                sb.append("\n");
                size = size + msg.getSize();
            }
        }
        final int finalSize = size;
        final String str = sb.toString();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvReceiver.setText(str);
                mTvTotal.setText("接收字节统计：" + finalSize);
            }
        });

    }

    /**
     * 发送数据
     */
    public void sendMessage(View view) {
        sendMsg();
    }

    /**
     * 发送数据
     */
    private void sendMsg() {
        String msg = mEdtSend.getText().toString();
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (BTSocket == null) {
            showToast("设配未连接");
            showLog("设配未连接");
            return;
        }
        try {
            byte[] data = null;
            if (mSwitchSendHex.isChecked()) {
                if (checkHexStr(msg)) {
                    data = hexStringToBytes(msg);
                } else {
                    showToast("输入的Hex字符有误");
                    return;
                }
            } else {
                data = msg.getBytes();
            }
            mOutStream = BTSocket.getOutputStream();
            mOutStream.write(data);
            mOutStream.flush();
            mTotalSendSize = mTotalSendSize + data.length;
            mTvSendTotal.setText("发送字节统计：" + mTotalSendSize);
        } catch (IOException e) {
            showToast("发送失败！");
            e.printStackTrace();
        } catch (Exception e) {
            showToast("发送失败！");
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
                    sendMsg();
                    mHandlerTimer.sendEmptyMessageDelayed(0, mTimer);
                    break;
            }
        }
    };

    //清除接收数据
    public void clearReceiver(View view) {
        msgBeens.clear();
//        mTvReceiver.setText("");
        mTvTotal.setText("接收字节统计：0");
        mTvSendTotal.setText("发送字节统计：0");
        mTotalSendSize = 0;
        mTotalSize = 0;
        mAdapter.notifyDataSetChanged();
    }

    //清除发送数据
    public void clearSend(View view) {
        mEdtSend.setText("");
        mSwitchTimer.setChecked(false);
    }

    private void showLog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvLog.setText(msg);
            }
        });
    }










    public void back(View view) {
        finish();
    }


    @Override
    protected void onDestroy() {
        bConnect = false;
        try {
            if (mOutStream != null)
                mOutStream.close();
            if (mInStream != null)
                mInStream.close();
            if (BTSocket != null)
                BTSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mInStream = null;
            mOutStream = null;
            BTSocket = null;

        }
        mHandlerTimer.removeCallbacksAndMessages(null);
        unregisterReceiver(BTReceive);
        super.onDestroy();

    }

    /**
     //     * 绑定蓝牙
     //     */
//    private void bondBT() {
//        showProgressDialog("配对蓝牙开始");
//        // 搜索蓝牙设备的过程占用资源比较多，一旦找到需要连接的设备后需要及时关闭搜索
//        // 获取蓝牙设备的连接状态
//        int connectState = device.getBondState();
//        icon_switch (connectState) {
//            // 未配对
//            case BluetoothDevice.BOND_NONE:
//                setProgressMsg("开始配对");
//                try {
//                    Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
//                    createBondMethod.invoke(device);
//                } catch (Exception e) {
//                    showToast("连接异常，请退出本界面再次尝试！");
//                    dismissProgressDialog();
//                    e.printStackTrace();
//                }
//                break;
//            // 已配对
//            case BluetoothDevice.BOND_BONDED:
//                try {
//                    setProgressMsg("开始连接");
//                    bConnect = true;
//                    clientThread clientConnectThread = new clientThread();
//                    clientConnectThread.start();
//                } catch (Exception e) {
//                    showToast("连接异常，请退出本界面再次尝试！");
//                    dismissProgressDialog();
//                    e.printStackTrace();
//                }
//                break;
//        }
//
//    }

}
