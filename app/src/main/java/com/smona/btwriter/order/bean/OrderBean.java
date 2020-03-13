package com.smona.btwriter.order.bean;

public class OrderBean {
    private String name;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isOk() {
        return false;
    }

    public boolean isRefuse() {
        return false;
    }
}
