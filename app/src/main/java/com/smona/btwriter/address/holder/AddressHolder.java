package com.smona.btwriter.address.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.address.bean.AddressBean;
import com.smona.btwriter.common.XViewHolder;

public class AddressHolder extends XViewHolder {
    private View setDefaultIv;
    private TextView nameTv;
    private TextView phoneTv;
    private TextView addressTv;

    public AddressHolder(View itemView) {
        super(itemView);
        setDefaultIv = itemView.findViewById(R.id.set_default);
        nameTv = itemView.findViewById(R.id.name);
        phoneTv = itemView.findViewById(R.id.phone);
        addressTv = itemView.findViewById(R.id.address);
    }

    public void bindView(AddressBean addressBean) {
        Context context = itemView.getContext();
        String name = context.getString(R.string.receiver_name) + "  " + addressBean.getUserName();
        nameTv.setText(name);
        String phone = context.getString(R.string.receiver_phone) + "  " + addressBean.getPhone();
        phoneTv.setText(phone);
        String address = context.getString(R.string.receiver_address) + "  " + addressBean.getAddress();
        addressTv.setText(address);
        setDefaultIv.setSelected(addressBean.isDefault());
    }
}
