package com.smona.btwriter.param.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.param.model.ParamModel;

public class ParamPresenter extends BasePresenter<ParamPresenter.IParamView> {

    private ParamModel paramModel = new ParamModel();

    public void requestNewParam() {

    }

    public interface IParamView extends ICommonView {

    }
}
