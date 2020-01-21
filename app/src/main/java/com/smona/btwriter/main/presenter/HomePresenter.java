package com.smona.btwriter.main.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.main.model.HomeModel;

public class HomePresenter extends BasePresenter<HomePresenter.IHomeView> {
    private HomeModel homeModel = new HomeModel();

    public interface IHomeView extends ICommonView{

    }
}
