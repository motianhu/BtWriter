package com.smona.btwriter.order.bean;

import com.smona.btwriter.goods.bean.ShoppingCardBean;

import java.util.List;

public class OrderDetailBean extends OrderBean {
    private String remark;
    private String phone;
    private String address;
    private String userName;
    private double totalPrice;
    private List<ShoppingCardBean> goodsList;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ShoppingCardBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<ShoppingCardBean> goodsList) {
        this.goodsList = goodsList;
    }
}
