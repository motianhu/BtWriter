package com.smona.btwriter.message.model;

import com.smona.btwriter.common.DynamicBuilder;
import com.smona.btwriter.common.http.bean.ReqEmpty;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.message.bean.MessageBean;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

import java.util.List;

public class MessageModel {
    public void requestMessageList(OnResultListener<BaseResponse<List<MessageBean>>> listener) {
        HttpCallbackProxy<BaseResponse<List<MessageBean>>> callbackProxy = new HttpCallbackProxy<BaseResponse<List<MessageBean>>>(listener){};
        new DynamicBuilder<List<MessageBean>>(DynamicBuilder.REQUEST_POST, BusinessHttpService.MESSAGE_LIST).requestData(new ReqEmpty(), callbackProxy);
    }

    public void requestDelMessage(List<String> ids, OnResultListener<BaseResponse<RespEmpty>> listener) {
        HttpCallbackProxy<BaseResponse<RespEmpty>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespEmpty>>(listener){};
        new DynamicBuilder<RespEmpty>(DynamicBuilder.REQUEST_POST, BusinessHttpService.MESSAGE_DELETE).requestData(ids, callbackProxy);
    }
}
