package com.smona.btwriter.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.inuker.bluetooth.library.search.SearchResult;
import com.smona.btwriter.R;

import java.util.List;

/**
 * Created by QunChen on 2018/5/1.
 * BLe
 */
public class BleAdapter extends BaseAdapter {
    private List<SearchResult> mList;
    private LayoutInflater mInflater;

    public BleAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<SearchResult> data) {
        mList = data;
    }

    @Override
    public int getCount() {
        return mList == null || mList.size() < 1 ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null || mList.size() < 1 ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mList == null || mList.size() < 1 ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.ble_item_view, null);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.state = (TextView) convertView.findViewById(R.id.tv_state);
            holder.address = (TextView) convertView.findViewById(R.id.tv_address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SearchResult bluetoothDevice = mList.get(position);
        holder.name.setText(bluetoothDevice.getName());
        holder.address.setText(bluetoothDevice.getAddress());
        holder.state.setVisibility(View.GONE);
        return convertView;
    }

    public final class ViewHolder {
        public TextView name;
        public TextView state;
        public TextView address;
    }

}
