package com.smona.btwriter.address.adapter;

import android.os.Bundle;

import com.smona.btwriter.address.bean.AddressBean;
import com.smona.btwriter.address.holder.AddressHolder;
import com.smona.btwriter.common.XBaseAdapter;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;

public class AddressAdapter extends XBaseAdapter<AddressBean, AddressHolder> {

    private int selectedId;

    public AddressAdapter(int resId) {
        super(resId);
    }

    public void seSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }

    @Override
    protected void convert(AddressHolder holder, AddressBean item, int pos) {
        if(selectedId != -1) {
            item.setSelected(selectedId == item.getId());
        }
        holder.bindView(item);
        holder.editView.setOnClickListener(v->clickEdit(item));
        holder.itemView.setOnClickListener(v->clickItem(item));
    }

    private void clickEdit(AddressBean addressBean) {
        Bundle bundle =new Bundle();
        bundle.putSerializable(AddressBean.class.getName(), addressBean);
        ARouterManager.getInstance().gotoActivityBundle(ARouterPath.PATH_TO_ADDRESS, bundle);
    }

    private void clickItem(AddressBean addressBean) {
        if(addressBean.getId() == selectedId) {
            return;
        }
        for(AddressBean item: mDataList) {
            if(item.getId() == addressBean.getId()) {
                item.setSelected(true);
                selectedId = item.getId();
            } else {
                item.setSelected(false);
            }
        }
        notifyDataSetChanged();
    }

    public AddressBean getSelectAddressBean() {
        for(AddressBean item: mDataList) {
            if(item.isSelected()) {
                return item;
            }
        }
        return null;
    }
}
