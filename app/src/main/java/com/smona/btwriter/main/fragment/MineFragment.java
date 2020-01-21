package com.smona.btwriter.main.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.base.ui.fragment.BasePresenterFragment;
import com.smona.btwriter.R;
import com.smona.btwriter.main.presenter.MinePresenter;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;

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

        content.findViewById(R.id.messageList).setOnClickListener(v -> ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_MESSAGELIST));
        content.findViewById(R.id.purchase).setOnClickListener(v -> ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_PURCHASELIST));
        content.findViewById(R.id.changepwd).setOnClickListener(v -> ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_CHANGEPWD));
        content.findViewById(R.id.logout).setOnClickListener(v -> clickLogout());
    }

    private void clickLogout() {

    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
