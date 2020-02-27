package com.smona.btwriter.goods.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.goods.bean.ReqAddGoodsBean;
import com.smona.btwriter.goods.bean.ReqGoods;
import com.smona.btwriter.goods.bean.ReqGoodsCount;
import com.smona.btwriter.goods.bean.ReqGoodsSubmit;
import com.smona.btwriter.goods.bean.ResShoppingCardList;
import com.smona.btwriter.goods.bean.RespGoodsList;
import com.smona.btwriter.goods.model.ShoppingCardModel;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

public class ShoppingCardPresenter extends BasePresenter<ShoppingCardPresenter.IShoppingCardView> {

    private ShoppingCardModel shoppingCardModel = new ShoppingCardModel();

    public void requestShoppingList() {
        shoppingCardModel.requestList(new OnResultListener<BaseResponse<ResShoppingCardList>>() {
            @Override
            public void onSuccess(BaseResponse<ResShoppingCardList> response) {
                if(mView != null) {
                    mView.onList(response.data);
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    mView.onError("", stateCode, errorInfo);
                }
            }
        });
    }

    public void requestModify(int id, int count) {
        ReqGoodsCount goodsCount = new ReqGoodsCount();
        goodsCount.setId(id);
        goodsCount.setAmount(count);
        shoppingCardModel.modifyGoods(goodsCount, new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> response) {
                if (mView != null) {
                    mView.onModify(id, count);
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if (mView != null) {
                    mView.onError("requestModify", stateCode, errorInfo);
                }
            }
        });
    }

    public void requestDelete(int id) {
        ReqGoods goods = new ReqGoods();
        goods.setId(id);
        shoppingCardModel.deleteGoods(goods, new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> response) {
                if (mView != null) {
                    mView.onDelete(id);
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if (mView != null) {
                    mView.onError("requestDelete", stateCode, errorInfo);
                }
            }
        });
    }

    public void requestSubmit(int addressId, String remark) {
        ReqGoodsSubmit goods = new ReqGoodsSubmit();
        goods.setAddressId(addressId);
        goods.setComment(remark);
        shoppingCardModel.requestSubmit(goods, new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> response) {
                if (mView != null) {
                    mView.onSubmit();
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if (mView != null) {
                    mView.onError("requestDelete", stateCode, errorInfo);
                }
            }
        });
    }

    public interface IShoppingCardView extends ICommonView {
        void onList(ResShoppingCardList shoppingCardList);
        void onModify(int id, int count);
        void onDelete(int id);
        void onSubmit();
    }

}
