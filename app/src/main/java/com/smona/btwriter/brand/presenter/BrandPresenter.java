package com.smona.btwriter.brand.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.brand.bean.BrandBean;
import com.smona.btwriter.brand.bean.ReqBrand;
import com.smona.btwriter.brand.model.BrandModel;
import com.smona.btwriter.common.ICommonView;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

import java.util.List;

public class BrandPresenter extends BasePresenter<BrandPresenter.IBrandView> {
    private BrandModel brandModel = new BrandModel();

    public void requestBrandList(int type) {
        ReqBrand reqBrand = new ReqBrand();
        reqBrand.setType(type);
        brandModel.requestBrandList(reqBrand, new OnResultListener<BaseResponse<List<BrandBean>>>() {
            @Override
            public void onSuccess(BaseResponse<List<BrandBean>> response) {
                if(mView != null) {
                    mView.onBrandList(response.data);
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    mView.onError("requestBrandList", stateCode, errorInfo);
                }
            }
        });
    }

    public interface IBrandView extends ICommonView{
        void onBrandList(List<BrandBean> brandBeanList);
    }
}
