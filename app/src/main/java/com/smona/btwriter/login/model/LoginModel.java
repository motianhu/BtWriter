package com.smona.btwriter.login.model;

import com.smona.btwriter.common.DynamicBuilder;
import com.smona.btwriter.login.bean.ReqLogin;
import com.smona.btwriter.login.bean.RespLogin;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BtBuilder;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

public class LoginModel {

    public void login(ReqLogin reqLogin, OnResultListener<BaseResponse<RespLogin>> listener) {
        HttpCallbackProxy<BaseResponse<RespLogin>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespLogin>>(listener){};
        new BtBuilder<RespLogin>(DynamicBuilder.REQUEST_POST, BusinessHttpService.LOGIN).requestData(reqLogin, callbackProxy);
    }

}
