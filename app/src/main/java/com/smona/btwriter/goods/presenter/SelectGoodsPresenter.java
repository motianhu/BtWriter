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
    private ShoppingCardModel shoppingCardModel = new ShoppingCardModel();

    public void requestAddGoods(int id) {
        ReqAddGoodsBean reqGoodsDetailBean = new ReqAddGoodsBean();
        reqGoodsDetailBean.setId(id);
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
        void onAddGoods();
    }
}
