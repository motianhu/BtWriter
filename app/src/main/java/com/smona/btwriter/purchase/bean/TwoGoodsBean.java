package com.smona.btwriter.purchase.bean;

public class TwoGoodsBean {
    private GoodsBean leftBean;
    private GoodsBean rightBean;

    public GoodsBean getLeftBean() {
        return leftBean;
    }

    public void setLeftBean(GoodsBean leftBean) {
        this.leftBean = leftBean;
    }

    public GoodsBean getRightBean() {
        return rightBean;
    }

    public void setRightBean(GoodsBean rightBean) {
        this.rightBean = rightBean;
    }
}
