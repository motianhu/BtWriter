package com.smona.btwriter.purchase.bean;

public class ReqGoodsBean {
    private int limit;
    private int currPage;
    private String name;
    private String sortField;
    private int isAsc;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

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
