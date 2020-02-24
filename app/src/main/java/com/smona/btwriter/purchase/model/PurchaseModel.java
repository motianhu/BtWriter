package com.smona.btwriter.purchase.model;

import com.smona.btwriter.common.DynamicBuilder;
import com.smona.btwriter.purchase.bean.ReqGoodsBean;
import com.smona.btwriter.purchase.bean.RespGoodsList;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

public class PurchaseModel {
    public void requestGoodsList(ReqGoodsBean reqGoodsBean, OnResultListener<BaseResponse<RespGoodsList>> listener) {
        HttpCallbackProxy<BaseResponse<RespGoodsList>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespGoodsList>>(listener){};
        new DynamicBuilder<RespGoodsList>(DynamicBuilder.REQUEST_POST, BusinessHttpService.GOODS_LIST).requestData(reqGoodsBean, callbackProxy);
    }
}
