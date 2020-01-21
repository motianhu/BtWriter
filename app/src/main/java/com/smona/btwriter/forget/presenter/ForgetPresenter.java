package com.smona.btwriter.forget.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.forget.model.ForgetPwdModel;

public class ForgetPresenter extends BasePresenter<ForgetPresenter.IForgetPwdView> {
    private ForgetPwdModel forgetPwdModel = new ForgetPwdModel();

    public interface IForgetPwdView extends ICommonView{

    }
}
