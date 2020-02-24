package com.smona.btwriter.common.http.model;

import com.smona.btwriter.common.DynamicBuilder;
import com.smona.btwriter.common.http.bean.ReqEmailCode;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

public class EmailCodeModel {
    public void requestEmailCode(ReqEmailCode emailCode, OnResultListener<BaseResponse<RespEmpty>> listener) {
        HttpCallbackProxy<BaseResponse<RespEmpty>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespEmpty>>(listener){};
        new DynamicBuilder<RespEmpty>(DynamicBuilder.REQUEST_POST, BusinessHttpService.EMAILCODE).requestData(emailCode, callbackProxy);
    }
}
