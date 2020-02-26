package com.smona.btwriter.goods.bean;

import com.smona.btwriter.address.bean.AddressBean;

import java.util.List;

public class ResShoppingCardList {
    private List<ShoppingCardBean> goodsList;
    private AddressBean address;

    public List<ShoppingCardBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<ShoppingCardBean> goodsList) {
        this.goodsList = goodsList;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }
}
