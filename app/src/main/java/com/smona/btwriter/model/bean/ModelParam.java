package com.smona.btwriter.model.bean;

import java.io.Serializable;

public class ModelParam implements Serializable {
    private int brandId;
    private int membraneType;

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getMembraneType() {
        return membraneType;
    }

    public void setMembraneType(int membraneType) {
        this.membraneType = membraneType;
    }
}
