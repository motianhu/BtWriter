package com.smona.btwriter.register.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.register.model.RegisterModel;

public class RegisterPresetenr extends BasePresenter<RegisterPresetenr.IRegisterView> {
    private RegisterModel registerModel = new RegisterModel();
    public interface IRegisterView extends ICommonView {

    }
}
