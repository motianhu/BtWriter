package com.smona.btwriter.message.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.message.bean.MessageBean;
import com.smona.btwriter.message.bean.ReqMessageBean;
import com.smona.btwriter.message.model.MessageModel;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

public class MessagePresenter extends BasePresenter<MessagePresenter.IMessageView> {
    
    private MessageModel messageModel = new MessageModel();

    public void requestMessage(int id) {
        ReqMessageBean reqMessageBean = new ReqMessageBean();
        reqMessageBean.setId(id);
        messageModel.requestMessage(reqMessageBean, new OnResultListener<BaseResponse<MessageBean>>() {
            @Override
            public void onSuccess(BaseResponse<MessageBean> response) {
                if(mView != null) {
                    mView.onMessage(response.data);
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView != null) {
                    mView.onError("", stateCode, errorInfo);
                }
            }
        });
    }

    public interface IMessageView extends ICommonView {
        void onMessage(MessageBean messageBean);
    }
}
