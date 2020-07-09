package com.smona.btwriter.goods.bean;

public class ShoppingCardBean {
    private int id;
    private String name;
    private double unitPrice;
    private double totalPrice;
    private int amount;
    private String coverImg;
    private int goodsType;
    private int goodsId;
    private int goodsKind;

    private int tmpCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    public int getGoodsKind() {
        return goodsKind;
    }

    public void setGoodsKind(int goodsKind) {
        this.goodsKind = goodsKind;
    }

    public int getTmpCount() {
        return tmpCount;
    }

    public void setTmpCount(int tmpCount) {
        this.tmpCount = tmpCount;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
}
