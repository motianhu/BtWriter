package com.smona.btwriter.goods.model;

import com.smona.btwriter.common.DynamicBuilder;
import com.smona.btwriter.common.http.bean.ReqEmpty;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.goods.bean.ReqAddGoodsBean;
import com.smona.btwriter.goods.bean.ReqGoods;
import com.smona.btwriter.goods.bean.ResShoppingCardList;
import com.smona.http.business.BaseResponse;
import com.smona.http.business.BusinessHttpService;
import com.smona.http.wrapper.HttpCallbackProxy;
import com.smona.http.wrapper.OnResultListener;

public class ShoppingCardModel {

    public void requestList(OnResultListener<BaseResponse<ResShoppingCardList>> listener) {
        HttpCallbackProxy<BaseResponse<ResShoppingCardList>> callbackProxy = new HttpCallbackProxy<BaseResponse<ResShoppingCardList>>(listener){};
        new DynamicBuilder<ResShoppingCardList>(DynamicBuilder.REQUEST_POST, BusinessHttpService.SHOPPINGCARD_LIST).requestData(new ReqEmpty(), callbackProxy);
    }

    public void addGoods(ReqAddGoodsBean addGoodsBean, OnResultListener<BaseResponse<RespEmpty>> listener) {
        HttpCallbackProxy<BaseResponse<RespEmpty>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespEmpty>>(listener){};
        new DynamicBuilder<RespEmpty>(DynamicBuilder.REQUEST_POST, BusinessHttpService.SHOPPINGCARD_ADD).requestData(addGoodsBean, callbackProxy);
    }

    public void deleteGoods(ReqGoods goodsBean, OnResultListener<BaseResponse<RespEmpty>> listener) {
        HttpCallbackProxy<BaseResponse<RespEmpty>> callbackProxy = new HttpCallbackProxy<BaseResponse<RespEmpty>>(listener){};
        new DynamicBuilder<RespEmpty>(DynamicBuilder.REQUEST_POST, BusinessHttpService.SHOPPINGCARD_DELETE).requestData(goodsBean, callbackProxy);
    }
}
