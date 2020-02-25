package com.smona.btwriter.main.model;

import com.smona.btwriter.common.DynamicBuilder;
import com.smona.btwriter.common.http.bean.ReqEmpty;
import com.smona.btwriter.main.bean.ParamInfo;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

import java.util.List;

public class ParamModel {
    public void requestParamList(OnResultListener<BaseResponse<List<ParamInfo>>> listener) {
        HttpCallbackProxy<BaseResponse<List<ParamInfo>>> callbackProxy = new HttpCallbackProxy<BaseResponse<List<ParamInfo>>>(listener){};
        new DynamicBuilder<List<ParamInfo>>(DynamicBuilder.REQUEST_POST, BusinessHttpService.PARAM_INFO).requestData(new ReqEmpty(), callbackProxy);
    }
}
