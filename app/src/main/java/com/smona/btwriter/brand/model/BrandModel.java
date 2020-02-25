package com.smona.btwriter.brand.model;

import com.smona.btwriter.brand.bean.BrandBean;
import com.smona.btwriter.brand.bean.ReqBrand;
import com.smona.btwriter.common.DynamicBuilder;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

import java.util.List;

public class BrandModel {
    public void requestBrandList(ReqBrand reqBrand, OnResultListener<BaseResponse<List<BrandBean>>> listener) {
        HttpCallbackProxy<BaseResponse<List<BrandBean>>> callbackProxy = new HttpCallbackProxy<BaseResponse<List<BrandBean>>>(listener){};
        new DynamicBuilder<List<BrandBean>>(DynamicBuilder.REQUEST_POST, BusinessHttpService.BRAND_LIST).requestData(reqBrand, callbackProxy);
    }
}
