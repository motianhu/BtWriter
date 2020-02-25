package com.smona.btwriter.model.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.model.bean.ModelBean;
import com.smona.btwriter.model.bean.ReqModel;
import com.smona.btwriter.model.model.ModelModel;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

import java.util.List;

public class ModePresenter extends BasePresenter<ModePresenter.IModeView> {

    private ModelModel modelModel = new ModelModel();

    public void requestPhoneModelList(int brandId, int membraneType) {
        ReqModel reqModel = new ReqModel();
        reqModel.setId(brandId);
        reqModel.setType(membraneType);
        modelModel.requestPhoneModelList(reqModel, new OnResultListener<BaseResponse<List<ModelBean>>>() {
            @Override
            public void onSuccess(BaseResponse<List<ModelBean>> response) {
                if(mView != null) {
                    mView.onModelList(response.data);
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    mView.onError("requestPhoneModelList", stateCode, errorInfo);
                }
            }
        });
    }

    public interface IModeView extends ICommonView{
        void onModelList(List<ModelBean> modelBeanList);
    }
}
