package com.smona.btwriter.main.model;

import com.smona.btwriter.common.DynamicBuilder;
import com.smona.btwriter.common.http.bean.ReqEmpty;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.main.bean.RespHomeBean;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

public class HomeModel {
    public void requestHome(OnResultListener<BaseResponse<RespHomeBean>> listener) {
        HttpCallbackProxy<BaseResponse<RespHomeBean>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespHomeBean>>(listener){};
        new DynamicBuilder<RespHomeBean>(DynamicBuilder.REQUEST_POST, BusinessHttpService.HOME_INFO).requestData(new ReqEmpty(), callbackProxy);
    }

    public void requestScan(String api, OnResultListener<BaseResponse<RespEmpty>> listener) {
        HttpCallbackProxy<BaseResponse<RespEmpty>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespEmpty>>(listener){};
        new DynamicBuilder<RespEmpty>(DynamicBuilder.REQUEST_POST, api).requestData(new ReqEmpty(), callbackProxy);
    }
}
