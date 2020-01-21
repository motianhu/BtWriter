package com.smona.btwriter.make.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.make.model.MakeModel;

public class MakePresenter extends BasePresenter<MakePresenter.IMakeView> {
    private MakeModel makeModel = new MakeModel();
    public interface IMakeView extends ICommonView{

    }
}
