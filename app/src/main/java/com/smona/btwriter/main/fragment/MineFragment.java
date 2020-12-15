package com.smona.btwriter.main.fragment;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.base.ui.fragment.BasePresenterFragment;
import com.smona.btwriter.R;
import com.smona.btwriter.bluetooth.BluetoothDataCenter;
import com.smona.btwriter.data.AccountDataCenter;
import com.smona.btwriter.main.bean.CompanyBean;
import com.smona.btwriter.main.presenter.MinePresenter;
import com.smona.btwriter.notify.NotifyCenter;
import com.smona.btwriter.notify.event.BluetoothChangeEvent;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;
import com.smona.btwriter.widget.EditCommonDialog;
import com.smona.btwriter.widget.ShowCompanyDialog;
import com.smona.http.business.BusinessHttpService;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MineFragment extends BasePresenterFragment<MinePresenter, MinePresenter.IMineView> implements MinePresenter.IMineView {

    private TextView userNameTv;
    private TextView matchStatusTv;

    @Override
    protected MinePresenter initPresenter() {
        return new MinePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View content) {
        super.initView(content);
        userNameTv = content.findViewById(R.id.email);

        matchStatusTv = content.findViewById(R.id.bluetoothStatus);
        refreshBluetoothStatus();
        content.findViewById(R.id.bind_bluetooth).setOnClickListener(v->clickConnect());

        content.findViewById(R.id.contact_mch).setOnClickListener(v -> clickContactMch());
        content.findViewById(R.id.messages).setOnClickListener(v -> ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_MESSAGELIST));
        content.findViewById(R.id.orderlist).setOnClickListener(v -> ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_ORDERLIST));
        content.findViewById(R.id.purchase).setOnClickListener(v -> ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_GOODSLIST));
        content.findViewById(R.id.changepwd).setOnClickListener(v -> ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_CHANGEPWD));
        content.findViewById(R.id.modify_info).setOnClickListener(v -> clickModifyInfo());
        content.findViewById(R.id.user_protocol).setOnClickListener(v -> startH5(BusinessHttpService.USER_PROTOCOL));
        content.findViewById(R.id.privacy_protocol).setOnClickListener(v -> startH5(BusinessHttpService.PRIVACY_PROTOCOL));
        content.findViewById(R.id.logout).setOnClickListener(v -> clickLogout());

        NotifyCenter.getInstance().registerListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        NotifyCenter.getInstance().unRegisterListener(this);
    }

    private void clickContactMch() {
        showLoadingDialog();
        mPresenter.requestCompany();
    }

    private void clickModifyInfo() {
        EditCommonDialog editCommonDialog = new EditCommonDialog(getContext()).setOnCommitListener((dialog, content) -> {
            if(TextUtils.isEmpty(content)) {
                CommonUtil.showShort(getContext(), R.string.empty_phone);
            } else {
                dialog.dismiss();
                showLoadingDialog();
                mPresenter.requestModifyPhone(content);
            }
        });
        editCommonDialog.show();
    }

    private void clickConnect() {
        ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_BLUETOOTH_LIST);
    }

    private void clickLogout() {
        showLoadingDialog();
        mPresenter.requestLogout();
    }

    @Override
    protected void initData() {
        super.initData();
        userNameTv.setText(AccountDataCenter.getInstance().getAccountInfo().getEmail());
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        CommonUtil.showToastByFilter(getContext(), errCode, errInfo);
    }

    @Override
    public void onLogout() {
        hideLoadingDialog();
        ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_LOGIN);
        mActivity.finish();
    }

    @Override
    public void onModify() {
        hideLoadingDialog();
        CommonUtil.showCustomToast(getString(R.string.modify_phone_success));
    }

    private void startH5(String url) {
        ARouterManager.getInstance().gotoActivityWithString(ARouterPath.PATH_TO_WEBVIEW, ARouterPath.PATH_TO_WEBVIEW, url);
    }

    @Override
    public void onCompany(CompanyBean companyBean) {
        hideLoadingDialog();
        new ShowCompanyDialog(getContext()).setCompanyBean(companyBean).setOnCommitListener(Dialog::dismiss).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void blueConnectChange(BluetoothChangeEvent event) {
        refreshBluetoothStatus();
    }

    private void refreshBluetoothStatus() {
        matchStatusTv.setText(BluetoothDataCenter.getInstance().getCurrentDeviceName(getContext()));
    }
}
