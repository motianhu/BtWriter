package com.smona.btwriter.changepwd.model;

import com.smona.btwriter.changepwd.bean.ReqChangePwd;
import com.smona.btwriter.common.DynamicBuilder;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

public class ChangePwdModel {
    public void requestChangePwd(ReqChangePwd resetPwd, OnResultListener<BaseResponse<RespEmpty>> listener) {
        HttpCallbackProxy<BaseResponse<RespEmpty>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespEmpty>>(listener){};
        new DynamicBuilder<RespEmpty>(DynamicBuilder.REQUEST_POST, BusinessHttpService.CHANGEPWD).requestData(resetPwd, callbackProxy);
    }
}
