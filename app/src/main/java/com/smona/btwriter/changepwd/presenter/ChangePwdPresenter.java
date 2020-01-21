package com.smona.btwriter.changepwd.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.changepwd.model.ChangePwdModel;
import com.smona.btwriter.common.ICommonView;

public class ChangePwdPresenter extends BasePresenter<ChangePwdPresenter.IChangePwdView> {
    private ChangePwdModel changePwdModel = new ChangePwdModel();

    public interface IChangePwdView extends ICommonView{

    }
}
