package com.smona.btwriter.goods.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.goods.bean.GoodsBean;
import com.smona.btwriter.goods.bean.ReqAddGoodsBean;
import com.smona.btwriter.goods.bean.ReqGoods;
import com.smona.btwriter.goods.model.GoodsModel;
import com.smona.btwriter.goods.model.ShoppingCardModel;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

public class SelectGoodsPresenter extends BasePresenter<SelectGoodsPresenter.ISelectGoodsView> {
    private GoodsModel goodsModel = new GoodsModel();
    private ShoppingCardModel shoppingCardModel = new ShoppingCardModel();

    public void requestGoodsDetail(int id) {
        ReqGoods reqGoodsDetailBean = new ReqGoods();
        reqGoodsDetailBean.setId(id);
        goodsModel.requestGoodsDetail(reqGoodsDetailBean, new OnResultListener<BaseResponse<GoodsBean>>() {
            @Override
            public void onSuccess(BaseResponse<GoodsBean> response) {
                if (mView != null) {
                    mView.onGoodsDetail(response.data);
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if (mView != null) {
                    mView.onError("requestGoodsDetail", stateCode, errorInfo);
                }
            }
        });
    }

    public void requestAddGoods(int id, int typeId) {
        ReqAddGoodsBean reqGoodsDetailBean = new ReqAddGoodsBean();
        reqGoodsDetailBean.setId(id);
        reqGoodsDetailBean.setTypeId(typeId);
        shoppingCardModel.addGoods(reqGoodsDetailBean, new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> response) {
                if (mView != null) {
                    mView.onAddGoods();
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if (mView != null) {
                    mView.onError("requestAddGoods", stateCode, errorInfo);
                }
            }
        });
    }

    public interface ISelectGoodsView extends ICommonView {
        void onGoodsDetail(GoodsBean goodsBean);
        void onAddGoods();
    }
}
