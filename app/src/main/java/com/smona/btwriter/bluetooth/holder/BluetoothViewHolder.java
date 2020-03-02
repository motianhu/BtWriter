package com.smona.btwriter.bluetooth.holder;

import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.bluetooth.BluetoothDataCenter;
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
        statusTv.setSelected(false);
        addressTv.setText(bluetoothDevice.getAddress());
        switch (bluetoothDevice.getBondState()) {
            case BluetoothDevice.BOND_BONDED:
                statusTv.setText(R.string.bluetooth_bonded);
                setCurrUseDevice(bluetoothDevice);
                break;
            case BluetoothDevice.BOND_BONDING:
                statusTv.setText(R.string.bluetooth_bonding);
                break;
            case BluetoothDevice.BOND_NONE:
                statusTv.setText(R.string.bluetooth_not_bond);
                break;
        }
    }

    private void setCurrUseDevice(BluetoothDevice bluetoothDevice) {
        if(BluetoothDataCenter.getInstance().getCurrentDeviceAddress().equalsIgnoreCase(bluetoothDevice.getAddress())) {
            statusTv.setText(R.string.bluetooth_useing);
            statusTv.setSelected(true);
        }
    }
}
