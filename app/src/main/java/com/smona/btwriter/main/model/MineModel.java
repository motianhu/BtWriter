package com.smona.btwriter.main.model;

import com.smona.btwriter.common.DynamicBuilder;
import com.smona.btwriter.common.http.bean.ReqEmpty;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.main.bean.CompanyBean;
import com.smona.btwriter.main.bean.ReqModifyPhone;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

public class MineModel {
    public void requestLogout(OnResultListener<BaseResponse<RespEmpty>> listener) {
        HttpCallbackProxy<BaseResponse<RespEmpty>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespEmpty>>(listener){};
        new DynamicBuilder<RespEmpty>(DynamicBuilder.REQUEST_POST, BusinessHttpService.LOGOUT).requestData(new ReqEmpty(), callbackProxy);
    }

    public void requestModifyPhone(ReqModifyPhone phone, OnResultListener<BaseResponse<RespEmpty>> listener) {
        HttpCallbackProxy<BaseResponse<RespEmpty>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespEmpty>>(listener){};
        new DynamicBuilder<RespEmpty>(DynamicBuilder.REQUEST_POST, BusinessHttpService.MODIFY_ACCOUNT).requestData(phone, callbackProxy);
    }

    public void requestCompanyInfo(OnResultListener<BaseResponse<CompanyBean>> listener) {
        HttpCallbackProxy<BaseResponse<CompanyBean>> callbackProxy = new HttpCallbackProxy<BaseResponse<CompanyBean>>(listener){};
        new DynamicBuilder<CompanyBean>(DynamicBuilder.REQUEST_POST, BusinessHttpService.CONTACT).requestData(new ReqEmpty(), callbackProxy);
    }
}
