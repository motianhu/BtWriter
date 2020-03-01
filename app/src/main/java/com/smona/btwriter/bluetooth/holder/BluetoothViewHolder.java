package com.smona.btwriter.bluetooth.holder;

import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.common.XViewHolder;

public class BluetoothViewHolder extends XViewHolder {
    private TextView nameTv;
    private TextView statusTv;
    private TextView addressTv;
    public BluetoothViewHolder(View itemView) {
        super(itemView);
        nameTv = itemView.findViewById(R.id.tv_name);
        statusTv = itemView.findViewById(R.id.tv_status);
        addressTv = itemView.findViewById(R.id.tv_address);
    }

    public void bindViews(BluetoothDevice bluetoothDevice) {
        nameTv.setText(bluetoothDevice.getName());
        addressTv.setText(bluetoothDevice.getAddress());
        switch (bluetoothDevice.getBondState()) {
            case BluetoothDevice.BOND_BONDED:
                statusTv.setText("已绑定");
                break;
            case BluetoothDevice.BOND_BONDING:
                statusTv.setText("绑定中");
                break;
            case BluetoothDevice.BOND_NONE:
                statusTv.setText("未绑定");
                break;
        }
    }
}
