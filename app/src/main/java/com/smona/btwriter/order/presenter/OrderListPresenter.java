package com.smona.btwriter.order.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.common.http.bean.ReqPage;
import com.smona.btwriter.order.bean.OrderBean;
import com.smona.btwriter.order.bean.RespOrderList;
import com.smona.btwriter.order.model.OrderModel;
import com.smona.btwriter.util.CommonUtil;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

import java.util.List;

public class OrderListPresenter extends BasePresenter<OrderListPresenter.IOrderListView> {

    private OrderModel orderModel = new OrderModel();
    private int lastPage = CommonUtil.START_PAGE;

    public void requestOrderList() {
        ReqPage reqPage = new ReqPage();
        reqPage.setCurrPage(lastPage);
        reqPage.setLimit(CommonUtil.SIZE);
        orderModel.requestOrderList(null, new OnResultListener<BaseResponse<RespOrderList>>() {
            @Override
            public void onSuccess(BaseResponse<RespOrderList> response) {
                if(mView!= null) {
                    if (lastPage == CommonUtil.START_PAGE && CommonUtil.isEmptyList(response.data.getList())) {
                        mView.onEmpty();
                        return;
                    }
                    mView.onOrderList(lastPage == CommonUtil.START_PAGE, response.data.getList());
                    lastPage++;
                    boolean noMore = lastPage > response.data.getTotalPage();
                    if (noMore) {
                        mView.onComplete();
                    }
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView!= null) {
                    if (lastPage == CommonUtil.START_PAGE) {
                        mView.onError("", stateCode, errorInfo);
                    } else {
                        mView.onError("requestGoodsList", stateCode, errorInfo);
                    }
                }
            }
        });
    }

    public interface IOrderListView extends ICommonView {
        void onEmpty();
        void onOrderList(boolean isFirtPage, List<OrderBean> list);
        void onComplete();
    }
}
