package com.smona.btwriter.address.bean;

import java.io.Serializable;

public class AddressBean implements Serializable {

    private int id;
    private String userName;
    private String phone;
    private String address;
    private int isDefault; //1是 0否

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public boolean isDefault() {
        return isDefault == 1;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }
}
