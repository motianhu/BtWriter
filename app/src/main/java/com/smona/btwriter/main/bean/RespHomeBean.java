package com.smona.btwriter.main.bean;

import java.util.List;

public class RespHomeBean {
    private List<String> adList;
    private int accountType; //用户设备类型 1设备不限切割次数  2设备有限切割次数
    private int useAmount;
    private int unUseAmount;

    public List<String> getAdList() {
        return adList;
    }

    public void setAdList(List<String> adList) {
        this.adList = adList;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getUseAmount() {
        return useAmount;
    }

    public void setUseAmount(int useAmount) {
        this.useAmount = useAmount;
    }

    public int getUnUseAmount() {
        return unUseAmount;
    }

    public void setUnUseAmount(int unUseAmount) {
        this.unUseAmount = unUseAmount;
    }
}
