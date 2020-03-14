package com.smona.btwriter.goods.bean;

import java.util.List;

public class ReqGoodsSubmit {
    private List<GoodsNum> goodsList;
     private int addressId;
     private String comment;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<GoodsNum> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsNum> goodsList) {
        this.goodsList = goodsList;
    }
}
