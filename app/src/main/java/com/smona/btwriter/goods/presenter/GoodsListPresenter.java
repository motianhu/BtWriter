package com.smona.btwriter.goods.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.IPageView;
import com.smona.btwriter.goods.bean.GoodsBean;
import com.smona.btwriter.goods.bean.ReqGoodsList;
import com.smona.btwriter.goods.bean.RespGoodsList;
import com.smona.btwriter.goods.model.GoodsModel;
import com.smona.btwriter.util.CommonUtil;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

import java.util.List;

public class GoodsListPresenter extends BasePresenter<GoodsListPresenter.IPurchaseView> {

    private GoodsModel purchaseModel = new GoodsModel();
    private int lastPage = CommonUtil.START_PAGE;

    public void requestGoodsList() {
        ReqGoodsList reqGoodsBean = new ReqGoodsList();
        reqGoodsBean.setCurrPage(lastPage);
        reqGoodsBean.setLimit(CommonUtil.SIZE);
        purchaseModel.requestGoodsList(reqGoodsBean, new OnResultListener<BaseResponse<RespGoodsList>>() {
            @Override
            public void onSuccess(BaseResponse<RespGoodsList> response) {
                if(mView != null) {
                    if (lastPage == CommonUtil.START_PAGE && CommonUtil.isEmptyList(response.data.getList())) {
                        mView.onEmpty();
                        return;
                    }
                    mView.onGoodsList(lastPage == CommonUtil.START_PAGE, response.data.getList());
                    lastPage++;
                    boolean noMore = lastPage > response.data.getTotalPage();
                    if (noMore) {
                        mView.onComplete();
                    }
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    if (lastPage == CommonUtil.START_PAGE) {
                        mView.onError("requestGoodsList", stateCode, errorInfo);
                    } else {
                        mView.onError("requestGoodsList", stateCode, errorInfo);
                    }
                }
            }
        });
    }

    public void refreshGoodList() {
        requestGoodsList();;
    }

    public interface IPurchaseView extends IPageView {
        void onGoodsList(boolean isFirst, List<GoodsBean> goodsBeanList);
    }
}
