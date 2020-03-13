package com.smona.btwriter.order.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.order.bean.OrderDetailBean;
import com.smona.btwriter.order.bean.ReqOrderDetail;
import com.smona.btwriter.order.model.OrderModel;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

public class OrderPresenter extends BasePresenter<OrderPresenter.IOrderView> {

    private OrderModel orderModel = new OrderModel();

    public void requestOrderDetail(String orderNo) {
        ReqOrderDetail reqOrderDetail = new ReqOrderDetail();
        reqOrderDetail.setOrderNo(orderNo);
        orderModel.requestOrderDetail(reqOrderDetail, new OnResultListener<BaseResponse<OrderDetailBean>>() {
            @Override
            public void onSuccess(BaseResponse<OrderDetailBean> response) {
                if(mView!= null) {
                    mView.onOrder(response.data);
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView!= null) {
                    mView.onError("", stateCode, errorInfo);
                }
            }
        });
    }

    public interface IOrderView extends ICommonView {
        void onOrder(OrderDetailBean orderDetailBean);
    }
}
