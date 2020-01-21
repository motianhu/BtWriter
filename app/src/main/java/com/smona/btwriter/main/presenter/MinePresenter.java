package com.smona.btwriter.main.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.main.model.MineModel;

public class MinePresenter extends BasePresenter<MinePresenter.IMineView> {
    private MineModel mineModel = new MineModel();

    public interface IMineView extends ICommonView{

    }
}
