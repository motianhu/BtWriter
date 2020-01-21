package com.smona.btwriter.brand.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.brand.model.BrandModel;
import com.smona.btwriter.common.ICommonView;

public class BrandPresenter extends BasePresenter<BrandPresenter.IBrandView> {
    private BrandModel brandModel = new BrandModel();
    public interface IBrandView extends ICommonView{

    }
}
