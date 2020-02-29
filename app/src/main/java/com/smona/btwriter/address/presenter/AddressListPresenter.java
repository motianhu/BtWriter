package com.smona.btwriter.address.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.address.bean.AddressBean;
import com.smona.btwriter.address.bean.RespAddressList;
import com.smona.btwriter.address.model.AddressModel;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.common.IPageView;
import com.smona.btwriter.common.http.bean.ReqId;
import com.smona.btwriter.common.http.bean.ReqPage;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.util.CommonUtil;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

import java.util.List;

public class AddressListPresenter extends BasePresenter<AddressListPresenter.IAddressListView> {

    private AddressModel addressModel = new AddressModel();
    private int lastPage = CommonUtil.START_PAGE;

    public void requestAddressList() {
        ReqPage reqPage = new ReqPage();
        reqPage.setCurrPage(lastPage);
        reqPage.setLimit(CommonUtil.SIZE);
        addressModel.requestAddressList(reqPage, new OnResultListener<BaseResponse<RespAddressList>>() {
            @Override
            public void onSuccess(BaseResponse<RespAddressList> response) {
                if (mView != null) {
                    if (lastPage == CommonUtil.START_PAGE && CommonUtil.isEmptyList(response.data.getList())) {
                        mView.onEmpty();
                        return;
                    }
                    mView.onAddressList(lastPage == CommonUtil.START_PAGE, response.data.getList());
                    lastPage++;
                    boolean noMore = lastPage > response.data.getTotalPage();
                    if (noMore) {
                        mView.onComplete();
                    }
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if (mView != null) {
                    if (lastPage == CommonUtil.START_PAGE) {
                        mView.onError("", stateCode, errorInfo);
                    } else {
                        mView.onError("requestAddressList", stateCode, errorInfo);
                    }
                }
            }
        });
    }

    public void refreshAddressList() {
        lastPage = CommonUtil.START_PAGE;
        requestAddressList();
    }

    public interface IAddressListView extends IPageView {
        void onAddressList(boolean isFirstPage, List<AddressBean> list);
        void onSetDefault();
    }
}
