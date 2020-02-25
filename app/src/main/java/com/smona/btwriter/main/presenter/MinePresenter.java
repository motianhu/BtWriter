package com.smona.btwriter.main.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.main.model.MineModel;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

public class MinePresenter extends BasePresenter<MinePresenter.IMineView> {
    private MineModel mineModel = new MineModel();

    public void requestLogout() {
        mineModel.requestLogout(new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> respEmptyBaseResponse) {
                if(mView != null) {
                    mView.onLogout();
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    mView.onError("requestLogout", stateCode, errorInfo);
                }
            }
        });
    }

    public interface IMineView extends ICommonView{
        void onLogout();
    }
}
