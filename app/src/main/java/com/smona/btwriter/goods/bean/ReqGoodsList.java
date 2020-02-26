package com.smona.btwriter.goods.bean;

import com.smona.btwriter.common.http.bean.ReqPage;

public class ReqGoodsList extends ReqPage {
    private String name;
    private String sortField;
    private int isAsc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public int getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(int isAsc) {
        this.isAsc = isAsc;
    }
}
