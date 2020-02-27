package com.smona.btwriter.main.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.main.bean.RespHomeBean;
import com.smona.btwriter.main.model.HomeModel;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

public class HomePresenter extends BasePresenter<HomePresenter.IHomeView> {
    private HomeModel homeModel = new HomeModel();

    public void requestHome(){
        homeModel.requestHome(new OnResultListener<BaseResponse<RespHomeBean>>() {
            @Override
            public void onSuccess(BaseResponse<RespHomeBean> response) {
                if(mView != null) {
                    mView.onHome(response.data);
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    mView.onError("requestHome", stateCode, errorInfo);
                }
            }
        });
    }

    public void requestScan(String api){
        homeModel.requestScan(api, new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> response) {
                if(mView != null) {
                    mView.onScan();
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    mView.onError("requestScan", stateCode, errorInfo);
                }
            }
        });
    }

    public interface IHomeView extends ICommonView{
        void onHome(RespHomeBean homeBean);
        void onScan();
    }
}
