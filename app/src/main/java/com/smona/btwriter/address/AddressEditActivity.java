package com.smona.btwriter.address;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.address.bean.AddressBean;
import com.smona.btwriter.address.presenter.AddressPresenter;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;
import com.smona.btwriter.util.ToastUtil;

@Route(path = ARouterPath.PATH_TO_ADDRESS)
public class AddressEditActivity extends BaseLanguagePresenterActivity<AddressPresenter, AddressPresenter.IAddressView> implements AddressPresenter.IAddressView {

    private AddressBean addressBean;

    private EditText nameEt;
    private EditText phoneEt;
    private EditText addressEt;
    private View defaultView;

    @Override
    protected AddressPresenter initPresenter() {
        return new AddressPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initSerializable();
        initHeader();
        initViews();
    }

    private void initSerializable() {
        Bundle bundle = getIntent().getBundleExtra(ARouterPath.PATH_TO_ADDRESS);
        if(bundle == null) {
            addressBean = new AddressBean();
            return;
        }
        addressBean = (AddressBean) bundle.getSerializable(AddressBean.class.getName());
        if(addressBean == null) {
            addressBean = new AddressBean();
        }
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(R.string.address);
    }

    private void initViews() {
        nameEt = findViewById(R.id.name);
        phoneEt = findViewById(R.id.phone);
        addressEt = findViewById(R.id.address);
        defaultView = findViewById(R.id.default_iv);
        if(addressBean.getId() != 0) {
            nameEt.setText(addressBean.getUserName());
            phoneEt.setText(addressBean.getPhone());
            addressEt.setText(addressBean.getAddress());
            defaultView.setSelected(addressBean.isDefault());
        }
        findViewById(R.id.set_default).setOnClickListener(v-> clickSetDefault());
        findViewById(R.id.save).setOnClickListener(v-> clickSave());
    }

    private void clickSetDefault() {
        defaultView.setSelected(!defaultView.isSelected());
    }

    private void clickSave() {
        String userName = nameEt.getText().toString();
        if(TextUtils.isEmpty(userName)) {
            ToastUtil.showShort(R.string.user_name_hint);
            return;
        }
        String phone = phoneEt.getText().toString();
        if(TextUtils.isEmpty(userName)) {
            ToastUtil.showShort(R.string.user_phone_hint);
            return;
        }
        String address = addressEt.getText().toString();
        if(TextUtils.isEmpty(userName)) {
            ToastUtil.showShort(R.string.user_address_hint);
            return;
        }
        addressBean.setUserName(userName);
        addressBean.setPhone(phone);
        addressBean.setAddress(address);
        addressBean.setIsDefault(defaultView.isSelected()? 1:0);
        showLoadingDialog();
        mPresenter.requestAddress(addressBean);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        CommonUtil.showToastByFilter(errCode, errInfo);
    }

    @Override
    public void onAddress() {
        hideLoadingDialog();
        finish();
    }
}
