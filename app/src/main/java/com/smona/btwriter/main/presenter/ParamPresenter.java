package com.smona.btwriter.main.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.main.bean.ParamInfo;
import com.smona.btwriter.main.bean.ReqDelParam;
import com.smona.btwriter.main.model.ParamModel;
import com.smona.btwriter.util.CommonUtil;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

import java.util.List;

public class ParamPresenter extends BasePresenter<ParamPresenter.IParamView> {

    private ParamModel paramModel = new ParamModel();
    private int lastPage = CommonUtil.START_PAGE;

    public void requestParams() {
        paramModel.requestParamList(new OnResultListener<BaseResponse<List<ParamInfo>>>() {
            @Override
            public void onSuccess(BaseResponse<List<ParamInfo>> response) {
                if(mView != null) {
                    mView.onParams(response.data);
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

    public void requestDelParam(int id) {
        ReqDelParam delParam = new ReqDelParam();
        delParam.setId(id);
        paramModel.requestDelParam(delParam, new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> listBaseResponse) {
                if(mView != null) {
                    mView.onDelParam();
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    mView.onError("requestDelParam", stateCode, errorInfo);
                }
            }
        });
    }

    public interface  IParamView extends ICommonView {
        void onParams(List<ParamInfo> paramsList);
        void onDelParam();
    }
}
