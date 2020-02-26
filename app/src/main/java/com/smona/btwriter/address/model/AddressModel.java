package com.smona.btwriter.address.model;

import com.smona.btwriter.address.bean.AddressBean;
import com.smona.btwriter.address.bean.RespAddressList;
import com.smona.btwriter.common.DynamicBuilder;
import com.smona.btwriter.common.http.bean.ReqId;
import com.smona.btwriter.common.http.bean.ReqPage;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

public class AddressModel {

    public void requestAddressList(ReqPage reqPage, OnResultListener<BaseResponse<RespAddressList>> listener) {
        HttpCallbackProxy<BaseResponse<RespAddressList>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespAddressList>>(listener){};
        new DynamicBuilder<RespAddressList>(DynamicBuilder.REQUEST_POST, BusinessHttpService.ADDRESS_LIST).requestData(reqPage, callbackProxy);
    }

    public void requestAddress(AddressBean addressBean, OnResultListener<BaseResponse<RespEmpty>> listener) {
        HttpCallbackProxy<BaseResponse<RespEmpty>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespEmpty>>(listener){};
        new DynamicBuilder<RespEmpty>(DynamicBuilder.REQUEST_POST, BusinessHttpService.ADDRESS_SAVE).requestData(addressBean, callbackProxy);
    }

    public void requestSetDefault(ReqId reqId, OnResultListener<BaseResponse<RespEmpty>> listener) {
        HttpCallbackProxy<BaseResponse<RespEmpty>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespEmpty>>(listener) {
        };
        new DynamicBuilder<RespEmpty>(DynamicBuilder.REQUEST_POST, BusinessHttpService.ADDRESS_SETDEFAULT).requestData(reqId, callbackProxy);
    }

    public void requestAddressDelete(ReqId reqId, OnResultListener<BaseResponse<RespEmpty>> listener) {
        HttpCallbackProxy<BaseResponse<RespEmpty>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespEmpty>>(listener){};
        new DynamicBuilder<RespEmpty>(DynamicBuilder.REQUEST_POST, BusinessHttpService.ADDRESS_DELETE).requestData(reqId, callbackProxy);
    }
}
