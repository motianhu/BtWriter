package com.smona.btwriter.register.model;

import com.smona.btwriter.common.DynamicBuilder;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.register.bean.ReqRegister;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

public class RegisterModel {
    public void requestRegister(ReqRegister reqRegister, OnResultListener<BaseResponse<RespEmpty>> listener) {
        HttpCallbackProxy<BaseResponse<RespEmpty>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespEmpty>>(listener){};
        new DynamicBuilder<RespEmpty>(DynamicBuilder.REQUEST_POST, BusinessHttpService.REGISTER).requestData(reqRegister, callbackProxy);
    }
}
