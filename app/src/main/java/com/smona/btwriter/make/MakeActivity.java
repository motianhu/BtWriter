package com.smona.btwriter.make;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.bluetooth.transport.BluetoothConnectService;
import com.smona.btwriter.bluetooth.transport.OnReadListener;
import com.smona.btwriter.common.exception.AppContext;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.make.presenter.MakePresenter;
import com.smona.btwriter.model.bean.ModelBean;
import com.smona.btwriter.notify.NotifyCenter;
import com.smona.btwriter.notify.event.CountChangeEvent;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;

import java.io.File;

@Route(path = ARouterPath.PATH_TO_MAKE)
public class MakeActivity extends BaseLanguagePresenterActivity<MakePresenter, MakePresenter.IMakeView> implements MakePresenter.IMakeView {

    private ModelBean modelBean;
    private BluetoothConnectService bluetoothConnectService;
    private View makeView;

    @Override
    protected MakePresenter initPresenter() {
        return new MakePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_make;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initSerialized();
        initHeader();
        initViews();
    }

    private void initSerialized() {
        modelBean = (ModelBean) getIntent().getSerializableExtra(ARouterPath.PATH_TO_MAKE);
        if (modelBean == null) {
            finish();
        }
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> finish());
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(R.string.make);
    }

    private void initViews() {
        makeView = findViewById(R.id.make);
        makeView.setEnabled(false);
        makeView.setOnClickListener(v -> clickMake());
        bluetoothConnectService = BluetoothConnectService.buildService(new OnReadListener() {
            @Override
            public void onCreateChannel(boolean success) {
                makeView.setEnabled(success && makeView.isEnabled());
            }

            @Override
            public void executeFinish(boolean success) {
                runOnUiThread(() -> deviceMake(success));
            }
        });
        bluetoothConnectService.connectBluetooth(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.requestCheckMake();
    }

    private void clickMake() {
        if (modelBean == null) {
            return;
        }
        if (TextUtils.isEmpty(modelBean.getMd5())) {
            return;
        }
        if (TextUtils.isEmpty(modelBean.getPltUrl())) {
            return;
        }
        if (TextUtils.isEmpty(modelBean.getOriginName())) {
            return;
        }

        showLoadingDialog();
        String filePath = AppContext.getAppContext().getExternalCacheDir() + File.separator + CommonUtil.CURRENT_USE_PLT;
        String fileMd5 = CommonUtil.getFileMD5(new File(filePath));
        if (modelBean.getMd5().equalsIgnoreCase(fileMd5)) {
            transportToBluetooth();
            return;
        }
        mPresenter.downloadPlt(modelBean.getOriginName(), modelBean.getPltUrl(), modelBean.getMd5());
    }

    private void deviceMake(boolean success) {
        int msgResId = R.string.plt_transport_failed;
        if (success) {
            msgResId = R.string.plt_transport_success;
            if (mPresenter != null) {
                mPresenter.requestMakeSuccess();
            }
        }
        CommonUtil.showShort(this, msgResId);
    }

    @Override
    public void onDownload() {
        transportToBluetooth();
    }

    @Override
    public void onMakeSuccess() {
        NotifyCenter.getInstance().postEvent(new CountChangeEvent());
        mPresenter.requestCheckMake();
    }

    @Override
    public void onCheck(boolean avaliable) {
        makeView.setEnabled(avaliable);
    }

    private void transportToBluetooth() {
        hideLoadingDialog();
        String filePath = this.getExternalCacheDir() + File.separator + CommonUtil.CURRENT_USE_PLT;
        bluetoothConnectService.sendFile(filePath);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        onError(api);
    }

    private void onError(String api) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideLoadingDialog();
                if ("downloadPlt".equals(api)) {
                    CommonUtil.showShort(MakeActivity.this, R.string.plt_download_failed);
                }
            }
        });
    }
}
