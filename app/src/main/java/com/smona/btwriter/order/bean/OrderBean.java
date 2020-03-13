package com.smona.btwriter.order.bean;

public class OrderBean {
    private int id;
    private String orderNo;
    private int status; //1未确认，2确认，3拒绝
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isOk() {
        return getStatus() == 2;
    }

    public boolean isRefuse() {
        return getStatus() == 3;
    }
}
