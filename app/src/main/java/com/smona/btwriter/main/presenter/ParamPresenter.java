package com.smona.btwriter.main.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.main.model.ParamModel;

public class ParamPresenter extends BasePresenter<ParamPresenter.IParamView> {
    private ParamModel paramModel = new ParamModel();
    public interface  IParamView extends ICommonView{

    }
}
