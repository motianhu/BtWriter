package com.smona.btwriter.goods.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.goods.bean.GoodsBean;
import com.smona.btwriter.goods.bean.ReqGoods;
import com.smona.btwriter.goods.model.GoodsModel;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

public class GoodsDetailPresenter extends BasePresenter<GoodsDetailPresenter.IGoodsDetailView> {

    private GoodsModel goodsModel = new GoodsModel();

    public void requestGoodsDetail(int id) {
        ReqGoods reqGoodsDetailBean = new ReqGoods();
        reqGoodsDetailBean.setId(id);
        goodsModel.requestGoodsDetail(reqGoodsDetailBean, new OnResultListener<BaseResponse<GoodsBean>>() {
            @Override
            public void onSuccess(BaseResponse<GoodsBean> response) {
                if (mView!= null){
                    mView.onGoodsDetail(response.data);
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if (mView!= null){
                    mView.onError("requestGoodsDetail", stateCode, errorInfo);
                }
            }
        });
    }

    public interface IGoodsDetailView extends ICommonView {
        void onGoodsDetail(GoodsBean goodsBean);
    }
}
