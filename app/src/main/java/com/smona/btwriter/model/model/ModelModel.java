package com.smona.btwriter.model.model;

import com.smona.btwriter.common.DynamicBuilder;
import com.smona.btwriter.model.bean.ModelBean;
import com.smona.btwriter.model.bean.ReqModel;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

import java.util.List;

public class ModelModel {
    public void requestPhoneModelList(ReqModel reqModel, OnResultListener<BaseResponse<List<ModelBean>>> listener) {
        HttpCallbackProxy<BaseResponse<List<ModelBean>>> callbackProxy = new HttpCallbackProxy<BaseResponse<List<ModelBean>>>(listener){};
        new DynamicBuilder<List<ModelBean>>(DynamicBuilder.REQUEST_POST, BusinessHttpService.MODEL_LIST).requestData(reqModel, callbackProxy);
    }
}
