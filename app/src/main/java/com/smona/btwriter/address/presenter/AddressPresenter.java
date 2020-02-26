package com.smona.btwriter.address.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.address.bean.AddressBean;
import com.smona.btwriter.address.model.AddressModel;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

public class AddressPresenter extends BasePresenter<AddressPresenter.IAddressView> {

    private AddressModel addressModel = new AddressModel();

    public void requestAddress(AddressBean addressBean) {
        addressModel.requestAddress(addressBean, new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> respEmptyBaseResponse) {
                if(mView != null) {
                    mView.onAddress();
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    mView.onError("requestAddress", stateCode, errorInfo);
                }
            }
        });
    }

    public interface IAddressView extends ICommonView {
        void onAddress();
    }
}
