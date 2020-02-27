package com.smona.btwriter.address.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.address.bean.AddressBean;
import com.smona.btwriter.common.XViewHolder;

public class AddressHolder extends XViewHolder {
    private View selectView;
    private TextView nameTv;
    private TextView phoneTv;
    private TextView addressTv;
    private View defaultTv;
    public View editView;

    public AddressHolder(View itemView) {
        super(itemView);
        selectView = itemView.findViewById(R.id.selectedView);
        nameTv = itemView.findViewById(R.id.name);
        phoneTv = itemView.findViewById(R.id.phone);
        addressTv = itemView.findViewById(R.id.address);
        defaultTv = itemView.findViewById(R.id.set_default);
        editView = itemView.findViewById(R.id.edit);
    }

    public void bindView(AddressBean addressBean) {
        Context context = itemView.getContext();
        String name = addressBean.getUserName();
        nameTv.setText(name);
        String phone = context.getString(R.string.receiver_phone) + "  " + addressBean.getPhone();
        phoneTv.setText(phone);
        String address = context.getString(R.string.receiver_address) + "  " + addressBean.getAddress();
        addressTv.setText(address);
        selectView.setSelected(addressBean.isSelected());
        defaultTv.setVisibility(addressBean.isDefault() ? View.VISIBLE:View.GONE);
    }
}
