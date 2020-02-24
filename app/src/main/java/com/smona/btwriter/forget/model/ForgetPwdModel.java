package com.smona.btwriter.forget.model;

import com.smona.btwriter.common.DynamicBuilder;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.forget.bean.ReqResetPwd;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

public class ForgetPwdModel {
    public void requestResetPwd(ReqResetPwd resetPwd, OnResultListener<BaseResponse<RespEmpty>> listener) {
        HttpCallbackProxy<BaseResponse<RespEmpty>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespEmpty>>(listener){};
        new DynamicBuilder<RespEmpty>(DynamicBuilder.REQUEST_POST, BusinessHttpService.RESETPWD).requestData(resetPwd, callbackProxy);
    }
}
