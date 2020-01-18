package com.smona.btwriter.ble;

import android.app.AlertDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattCharacter;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.model.BleGattService;
import com.smona.btwriter.R;
import com.smona.btwriter.bluetoothspp2.BleBaseActivity;
import com.smona.btwriter.bluetoothspp2.MsgAdapter;
import com.smona.btwriter.bluetoothspp2.MsgBeen;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BleDetailActivity extends BleBaseActivity implements CompoundButton.OnCheckedChangeListener {
    private TextView mTvReceiver, mTvTotal, mTvLog, mTvSendTotal;
    private EditText mEdtSend, mEdtTimer;
    private Switch mSwitchSendHex, mSwitchReceiverHex, mSwitchTimer;
    private long mTimer = 0;
    private int mTotalSize;
    private String mac;
    private String name;

    private List<MsgBeen> msgBeens = new ArrayList<>();
    Boolean bConnect = false;
    private MsgAdapter mAdapter;
    private ListView mLvMsg;
    private int mTotalSendSize = 0;//发送字节统计

    //    private UUID writeServiceUUID = null;//写服务通道
//    private UUID writeCharacterUUID = null;//写数据的特征值
    private CharacterBean mWriteCharacterBean;
    private UUID readServiceUUID = null;//接收服务通道
    private UUID readCharacterUUID = null;//接收数据的特征值
    private List<CharacterBean> mCharacterList = new ArrayList<>();//服务特征

    public static void startActivity(Context context, String mac, String name) {
        Intent intent = new Intent(context, BleDetailActivity.class);
        intent.putExtra("mac", mac);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_detail);
        mTvReceiver = (TextView) findViewById(R.id.tv_receive);
        mTvTotal = (TextView) findViewById(R.id.tv_total);
        mTvSendTotal = (TextView) findViewById(R.id.tv_send_total);
        mTvLog = (TextView) findViewById(R.id.tv_log);
        mEdtSend = (EditText) findViewById(R.id.edt_send);
        mEdtTimer = (EditText) findViewById(R.id.edt_timer);
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

        mac = getIntent().getStringExtra("mac");
        name = getIntent().getStringExtra("name");
        name = TextUtils.isEmpty(name) ? mac : name;
        TextView tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(name);
        connectDevice();
    }

    /**
     * 通道选择
     */
    public void switchService(View view) {
        int checkeIndex = 0;
        boolean isWriteExit = mWriteCharacterBean != null && mWriteCharacterBean.getCharacterUUID() != null;
        int index = 0;
        String items[] = new String[mCharacterList.size()];
        for (CharacterBean bean : mCharacterList) {
            items[index] = bean.getCharacterUUID().toString();
            if (isWriteExit && mWriteCharacterBean.getCharacterUUID() == bean.getCharacterUUID()) {
                checkeIndex = index;
            }
            index++;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 3);
        builder.setTitle("选择写入服务通道");
        builder.setIcon(R.mipmap.ic_launcher);
        // 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setSingleChoiceItems(items, checkeIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (mCharacterList == null || mCharacterList.size() < which)
                    return;
                mWriteCharacterBean = mCharacterList.get(which);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        });
        builder.create().show();
    }

    /***
     * 连接
     */
    private void connectDevice() {
        showProgressDialog("开始连接..");
        BleTool.getClient().connect(mac, new BleConnectResponse() {
            @Override
            public void onResponse(int code, BleGattProfile data) {
                dismissProgressDialog();
                if (code == 0) {
                    Log.i("onResponse", String.format("服务通道信息：profile:\n%s", data));
                    showLog("连接成功");
                    BleTool.getClient().registerConnectStatusListener(mac, mBleConnectStatusListener);
                    //得到读写通道
                    for (BleGattService service : data.getServices()) {
                        List<BleGattCharacter> characterList = service.getCharacters();
                        for (BleGattCharacter character : characterList) {
                            if (4 == character.getProperty()) {      //写
                                mWriteCharacterBean = new CharacterBean(service.getUUID(), character.getUuid());
                            } else if (BluetoothGattCharacteristic.PROPERTY_NOTIFY == character.getProperty()) {//读
                                readCharacterUUID = character.getUuid();
                                readServiceUUID = service.getUUID();
                                BleTool.getClient().notify(mac, readServiceUUID, readCharacterUUID, notifyResponse);
                            }
                            //保存所有写入通道
                            int charaProp = character.getProperty();
                            if ((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) {
                                mCharacterList.add(new CharacterBean(service.getUUID(), character.getUuid()));
                            }
                            if ((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) > 0) {
                                mCharacterList.add(new CharacterBean(service.getUUID(), character.getUuid()));
                            }
                        }

                    }
                } else {
                    showToast("连接失败");
                    showLog("连接失败！");
                }
            }
        });
    }


//    private final BluetoothBondListener mBluetoothBondListener = new BluetoothBondListener() {
//        @Override
//        public void onBondStateChanged(String mac, int bondState) {
//            // bondState = Constants.BOND_NONE, BOND_BONDING, BOND_BONDED
//
//        }
//    };


    private BleNotifyResponse notifyResponse = new BleNotifyResponse() {
        @Override
        public void onNotify(UUID service, UUID character, byte[] value) {
            //取得接收数据
            MsgBeen msgBeen = new MsgBeen(value, value.length);
            msgBeens.add(msgBeen);
            mTotalSize = mTotalSize + value.length;
            setReceiverText();
        }

        @Override
        public void onResponse(int code) {
            Log.e("  onResponse", " onResponse " + code);
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
        if (mWriteCharacterBean == null || mWriteCharacterBean.getServiceUUID() == null || mWriteCharacterBean.getCharacterUUID() == null) {
            showToast("服务或者特征不存在！");
            return;
        }
        mTotalSendSize = mTotalSendSize + data.length;
        BleTool.getClient().write(mac, mWriteCharacterBean.getServiceUUID(), mWriteCharacterBean.getCharacterUUID(), data, new BleWriteResponse() {
            @Override
            public void onResponse(int code) {
                mTvSendTotal.setText("发送字节统计：" + mTotalSendSize);
                Log.e(" onResponse ", " write onResponse " + code);
            }
        });
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

    private final BleConnectStatusListener mBleConnectStatusListener = new BleConnectStatusListener() {

        @Override
        public void onConnectStatusChanged(String mac, int status) {
            if (status == 2) {//连接成功

            } else {//
                showToast("设备断开");
                showLog("设备断开");
            }
        }
    };

    @Override
    protected void onDestroy() {
        BleTool.getClient().disconnect(mac);
        BleTool.getClient().unregisterConnectStatusListener(mac, mBleConnectStatusListener);
        if (readServiceUUID != null && readCharacterUUID != null)
            BleTool.getClient().unnotify(mac, readServiceUUID, readCharacterUUID, new BleUnnotifyResponse() {
                @Override
                public void onResponse(int code) {

                }
            });

        super.onDestroy();
    }

    public void back(View view) {
        finish();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_service://服务

                break;
        }
    }
}
