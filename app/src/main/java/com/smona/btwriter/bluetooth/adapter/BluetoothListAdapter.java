package com.smona.btwriter.bluetooth.adapter;

import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;
import android.view.View;

import com.smona.btwriter.bluetooth.holder.BluetoothViewHolder;
import com.smona.btwriter.common.XBaseAdapter;

public class BluetoothListAdapter extends XBaseAdapter<BluetoothDevice, BluetoothViewHolder > {

    private OnClickBluetoothListener onClickBluetoothListener;

    public BluetoothListAdapter(int resId) {
        super(resId);
    }

    public void setOnClickBluetoothListener(OnClickBluetoothListener onClickBluetoothListener) {
        this.onClickBluetoothListener = onClickBluetoothListener;
    }

    @Override
    protected void convert(BluetoothViewHolder holder, BluetoothDevice item, int pos) {
        holder.bindViews(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBond(item);
            }
        });
    }

    private void clickBond(BluetoothDevice item) {
        onClickBluetoothListener.onClickDevice(item);
    }

    public void removeAllData() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    public void addNewDevice(BluetoothDevice bluetoothDevice) {
        for (BluetoothDevice d : mDataList) {
            if (TextUtils.isEmpty(d.getAddress()) || d.getAddress().equalsIgnoreCase(bluetoothDevice.getAddress())) {
                return;
            }
        }
        mDataList.add(bluetoothDevice);
        notifyDataSetChanged();
    }
    public void addPairDevice(BluetoothDevice bluetoothDevice) {
        for (BluetoothDevice d : mDataList) {
            if (TextUtils.isEmpty(d.getAddress()) || d.getAddress().equalsIgnoreCase(bluetoothDevice.getAddress())) {
                return;
            }
        }
        mDataList.add(bluetoothDevice);
        notifyDataSetChanged();
    }

    public interface OnClickBluetoothListener {
        void onClickDevice(BluetoothDevice device);
    }
}
