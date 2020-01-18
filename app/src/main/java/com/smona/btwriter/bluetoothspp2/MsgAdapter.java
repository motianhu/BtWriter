package com.smona.btwriter.bluetoothspp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smona.btwriter.R;

import java.util.List;

/**
 * Created by QunChen on 2018/5/7.
 * 列表
 */
public class MsgAdapter extends BaseAdapter {
    private List<MsgBeen> msgBeens;
    private LayoutInflater mInflater;
    private boolean isHex = false;

    public MsgAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<MsgBeen> data) {
        msgBeens = data;
    }

    public void setHex(boolean isHex) {
        this.isHex = isHex;
    }

    @Override
    public int getCount() {
        return msgBeens == null ? 0 : msgBeens.size();
    }

    @Override
    public Object getItem(int i) {
        return msgBeens == null ? null : msgBeens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return msgBeens == null ? 0 : i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.ble_item_msg, null);
            holder.title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.msg = (TextView) convertView.findViewById(R.id.tv_msg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MsgBeen been = msgBeens.get(i);
        holder.title.setText("接收 " + been.getSize() + " 字节");
        if (isHex) {
            holder.msg.setText(been.getHexMsg());
        } else {
            holder.msg.setText(been.getStrMsg());
        }
        return convertView;
    }

    public final class ViewHolder {
        public TextView title;
        public TextView msg;
    }
}
