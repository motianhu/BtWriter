package com.smona.btwriter.main.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.base.ui.fragment.BasePresenterFragment;
import com.smona.btwriter.R;
import com.smona.btwriter.bluetooth.BluetoothDataCenter;
import com.smona.btwriter.data.AccountDataCenter;
import com.smona.btwriter.main.presenter.MinePresenter;
import com.smona.btwriter.notify.NotifyCenter;
import com.smona.btwriter.notify.event.BluetoothChangeEvent;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MineFragment extends BasePresenterFragment<MinePresenter, MinePresenter.IMineView> implements MinePresenter.IMineView {

    private ImageView userIconIv;
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
        userIconIv = content.findViewById(R.id.icon);
        userNameTv = content.findViewById(R.id.email);

        matchStatusTv = content.findViewById(R.id.bluetoothStatus);
        refreshBluetoothStatus();
        content.findViewById(R.id.bind_bluetooth).setOnClickListener(v->clickConnect());


        content.findViewById(R.id.messages).setOnClickListener(v -> ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_MESSAGELIST));
        content.findViewById(R.id.orderlist).setOnClickListener(v -> ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_ORDERLIST));
        content.findViewById(R.id.purchase).setOnClickListener(v -> ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_GOODSLIST));
        content.findViewById(R.id.changepwd).setOnClickListener(v -> ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_CHANGEPWD));
        content.findViewById(R.id.logout).setOnClickListener(v -> clickLogout());

        NotifyCenter.getInstance().registerListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        NotifyCenter.getInstance().unRegisterListener(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void blueConnectChange(BluetoothChangeEvent event) {
        refreshBluetoothStatus();
    }

    private void refreshBluetoothStatus() {
        matchStatusTv.setText(BluetoothDataCenter.getInstance().getCurrentDeviceName(getContext()));
    }
}
