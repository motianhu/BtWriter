package com.smona.btwriter.order.model;

import com.smona.btwriter.common.DynamicBuilder;
import com.smona.btwriter.common.http.bean.ReqPage;
import com.smona.btwriter.order.bean.OrderDetailBean;
import com.smona.btwriter.order.bean.ReqOrderDetail;
import com.smona.btwriter.order.bean.RespOrderList;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

public class OrderModel {
    public void requestOrderList(ReqPage page, OnResultListener<BaseResponse<RespOrderList>> listener) {
        HttpCallbackProxy<BaseResponse<RespOrderList>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespOrderList>>(listener){};
        new DynamicBuilder<RespOrderList>(DynamicBuilder.REQUEST_POST, BusinessHttpService.ORDER_LIST).requestData(page, callbackProxy);
    }

    public void requestOrderDetail(ReqOrderDetail reqGoodsBean, OnResultListener<BaseResponse<OrderDetailBean>> listener) {
        HttpCallbackProxy<BaseResponse<OrderDetailBean>> callbackProxy = new HttpCallbackProxy<BaseResponse<OrderDetailBean>>(listener){};
        new DynamicBuilder<OrderDetailBean>(DynamicBuilder.REQUEST_POST, BusinessHttpService.ORDER_DETAIL).requestData(reqGoodsBean, callbackProxy);
    }
}
