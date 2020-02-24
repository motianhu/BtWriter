package com.smona.btwriter.purchase.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.purchase.bean.GoodsBean;
import com.smona.btwriter.purchase.bean.ReqGoodsBean;
import com.smona.btwriter.purchase.bean.RespGoodsList;
import com.smona.btwriter.purchase.model.PurchaseModel;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

import java.util.List;

public class PurchasePresenter extends BasePresenter<PurchasePresenter.IPurchaseView> {
    private PurchaseModel purchaseModel = new PurchaseModel();

    public void requestGoodsList() {
        ReqGoodsBean reqGoodsBean = new ReqGoodsBean();
        reqGoodsBean.setCurrPage(1);
        reqGoodsBean.setLimit(10);
        purchaseModel.requestGoodsList(reqGoodsBean, new OnResultListener<BaseResponse<RespGoodsList>>() {
            @Override
            public void onSuccess(BaseResponse<RespGoodsList> response) {
                if(mView != null) {
                    mView.onGoodsList(false, response.data.getList());
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    mView.onError("requestGoodsList", stateCode, errorInfo);
                }
            }
        });
    }

    public void refreshGoodList() {
        requestGoodsList();;
    }

    public interface IPurchaseView extends ICommonView {
        void onEmpty();
        void onGoodsList(boolean hasMore, List<GoodsBean> goodsBeanList);
    }
}
