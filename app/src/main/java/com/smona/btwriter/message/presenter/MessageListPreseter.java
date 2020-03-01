package com.smona.btwriter.message.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.common.http.bean.RespEmpty;
import com.smona.btwriter.message.bean.MessageBean;
import com.smona.btwriter.message.bean.ReqMessageDel;
import com.smona.btwriter.message.model.MessageModel;
import com.smona.http.business.BaseResponse;
import com.smona.http.wrapper.OnResultListener;

import java.util.List;

public class MessageListPreseter extends BasePresenter<MessageListPreseter.IMessageView> {
    private MessageModel messageModel = new MessageModel();

    public void requestMessageList() {
        messageModel.requestMessageList(new OnResultListener<BaseResponse<List<MessageBean>>>() {
            @Override
            public void onSuccess(BaseResponse<List<MessageBean>> listBaseResponse) {
                if(mView!=null) {
                    mView.onMessageList(listBaseResponse.data);
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView!=null) {
                    mView.onError("requestMessageList", stateCode, errorInfo);
                }
            }
        });
    }

    public void requestDelMessage(List<Integer> ids) {
        ReqMessageDel messageDel = new ReqMessageDel();
        messageDel.setIdList(ids);
        messageModel.requestDelMessage(messageDel, new OnResultListener<BaseResponse<RespEmpty>>() {
            @Override
            public void onSuccess(BaseResponse<RespEmpty> response) {
                if(mView!=null) {
                    mView.onMessageDelete();
                }
            }

            @Override
            public void onError(String stateCode, String errorInfo) {
                if(mView!=null) {
                    mView.onError("requestDelMessage", stateCode, errorInfo);
                }
            }
        });
    }

    public interface IMessageView extends ICommonView {
        void onMessageList(List<MessageBean> list);
        void onMessageDelete();
    }
}
