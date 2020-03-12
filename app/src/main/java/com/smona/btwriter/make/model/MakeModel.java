package com.smona.btwriter.make.model;

import com.smona.btwriter.common.DynamicBuilder;
import com.smona.btwriter.common.http.bean.ReqEmpty;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

public class MakeModel {
    public void requestMakeSuccess(OnResultListener<BaseResponse<RespEmpty>> listener) {
        HttpCallbackProxy<BaseResponse<RespEmpty>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespEmpty>>(listener){};
        new DynamicBuilder<RespEmpty>(DynamicBuilder.REQUEST_POST, BusinessHttpService.MAKE_SUCCESS).requestData(new ReqEmpty(), callbackProxy);
    }

    public void requestCheckMake(OnResultListener<BaseResponse<Integer>> listener) {
        HttpCallbackProxy<BaseResponse<Integer>> callbackProxy = new HttpCallbackProxy<BaseResponse<Integer>>(listener){};
        new DynamicBuilder<Integer>(DynamicBuilder.REQUEST_POST, BusinessHttpService.MAKE_CHECK).requestData(new ReqEmpty(), callbackProxy);
    }
}
