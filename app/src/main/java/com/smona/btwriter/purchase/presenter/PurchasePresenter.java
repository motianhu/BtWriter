package com.smona.btwriter.purchase.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.common.ICommonView;
import com.smona.btwriter.purchase.model.PurchaseModel;

public class PurchasePresenter extends BasePresenter<PurchasePresenter.IPurchaseView> {
    private PurchaseModel purchaseModel = new PurchaseModel();

    public interface IPurchaseView extends ICommonView {

    }
}
