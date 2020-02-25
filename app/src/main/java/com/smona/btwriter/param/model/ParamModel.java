package com.smona.btwriter.param.model;

import com.smona.btwriter.common.DynamicBuilder;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.model.bean.ReqModel;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

public class ParamModel {
    public void requestNewParam(ReqModel reqModel, OnResultListener<BaseResponse<RespEmpty>> listener) {
        HttpCallbackProxy<BaseResponse<RespEmpty>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespEmpty>>(listener){};
        new DynamicBuilder<RespEmpty>(DynamicBuilder.REQUEST_POST, BusinessHttpService.MODEL_LIST).requestData(reqModel, callbackProxy);
    }
}
