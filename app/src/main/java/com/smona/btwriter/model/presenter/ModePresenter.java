package com.smona.btwriter.model.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.model.model.ModelModel;

public class ModePresenter extends BasePresenter<ModePresenter.IModeView> {
    private ModelModel modelModel = new ModelModel();

    public interface IModeView extends ICommonView{

    }
}
