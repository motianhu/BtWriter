package com.smona.btwriter.register.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.register.model.RegisterModel;

public class RegisterPresetenr extends BasePresenter<RegisterPresetenr.IRegisterView> {
    private RegisterModel registerModel = new RegisterModel();

    public void register( String userName, String email, String pwd, String cpwd) {
        registerModel.register();
    }

    public interface IRegisterView extends ICommonView {

    }
}
