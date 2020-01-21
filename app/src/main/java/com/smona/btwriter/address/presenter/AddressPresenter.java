package com.smona.btwriter.address.presenter;

import com.smona.base.ui.mvp.BasePresenter;
import com.smona.btwriter.address.model.AddressModel;
import com.smona.btwriter.common.ICommonView;

public class AddressPresenter extends BasePresenter<AddressPresenter.IAddressView> {
    private AddressModel addressModel = new AddressModel();

    public void addAddress() {

    }
    public interface IAddressView extends ICommonView{

    }
}
