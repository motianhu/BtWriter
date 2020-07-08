package com.smona.btwriter.main.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.common.exception.AppContext;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.data.AccountDataCenter;
import com.smona.btwriter.main.bean.CompanyBean;
import com.smona.btwriter.main.bean.ReqModifyPhone;
import com.smona.btwriter.main.model.MineModel;
import com.smona.btwriter.push.PushApiManager;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

public class MinePresenter extends BasePresenter<MinePresenter.IMineView> {
    private MineModel mineModel = new MineModel();

    public void requestLogout() {
        mineModel.requestLogout(new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> respEmptyBaseResponse) {
                if (mView != null) {
                    PushApiManager.getInstance().removeTag(AppContext.getAppContext(), AccountDataCenter.getInstance().getAccountInfo().getEmail());
                    mView.onLogout();
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if (mView != null) {
                    mView.onError("requestLogout", stateCode, errorInfo);
                }
            }
        });
    }

    public void requestModifyPhone(String phone) {
        ReqModifyPhone modifyPhone = new ReqModifyPhone();
        modifyPhone.setPhone(phone);
        mineModel.requestModifyPhone(modifyPhone, new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> respEmptyBaseResponse) {
                if (mView != null) {
                    mView.onModify();
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if (mView != null) {
                    mView.onError("requestModifyPhone", stateCode, errorInfo);
                }
            }
        });
    }

    public void requestCompany() {
        mineModel.requestCompanyInfo(new OnResultListener<BaseResponse<CompanyBean>>() {
            @Override
            public void onSuccess(BaseResponse<CompanyBean> respEmptyBaseResponse) {
                if (mView != null) {
                    mView.onCompany(respEmptyBaseResponse.data);
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if (mView != null) {
                    mView.onError("requestModifyPhone", stateCode, errorInfo);
                }
            }
        });
    }

    public interface IMineView extends ICommonView {
        void onLogout();

        void onModify();

        void onCompany(CompanyBean companyBean);
    }
}
