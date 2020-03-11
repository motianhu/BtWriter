package com.smona.btwriter.make;

import android.os.Environment;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.bluetooth.BluetoothDataCenter;
import com.smona.btwriter.bluetooth.transport.BluetoothConnectService;
import com.smona.btwriter.bluetooth.transport.OnReadListener;
import com.smona.btwriter.common.exception.AppContext;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.make.presenter.MakePresenter;
import com.smona.btwriter.model.bean.ModelBean;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;
import com.smona.btwriter.util.ToastUtil;

import java.io.File;

@Route(path = ARouterPath.PATH_TO_MAKE)
public class MakeActivity extends BaseLanguagePresenterActivity<MakePresenter, MakePresenter.IMakeView> implements MakePresenter.IMakeView {

    private ModelBean modelBean;
    private BluetoothConnectService bluetoothConnectService;

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
        if(modelBean == null) {
            finish();
        }
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> finish());
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(R.string.make);
    }

    private void initViews() {
        findViewById(R.id.make).setOnClickListener(v->clickMake());
        bluetoothConnectService = BluetoothConnectService.buildService(new OnReadListener() {
            @Override
            public void onCreateChannel(boolean success) {

            }

            @Override
            public void executeFinish(boolean success) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String msg = "传输失败,请重新传输！";
                        if(success) {
                            msg = "传输成功";
                        }
                        ToastUtil.showShort(msg);
                    }
                });
            }
        });
        bluetoothConnectService.connectBluetooth();
    }

    private void clickMake() {
        if(modelBean == null) {
            return;
        }
        if(TextUtils.isEmpty(modelBean.getMd5())) {
            return;
        }
        if(TextUtils.isEmpty(modelBean.getPltUrl())) {
            return;
        }
        if(TextUtils.isEmpty(modelBean.getOriginName())) {
            return;
        }
        showLoadingDialog();
        String filePath = AppContext.getAppContext().getExternalCacheDir() + File.separator+ CommonUtil.CURRENT_USE_PLT;
        String fileMd5 = CommonUtil.getFileMD5(new File(filePath));
        if(modelBean.getMd5().equalsIgnoreCase(fileMd5)) {
            transportToBluetooth();
            return;
        }
        mPresenter.downloadPlt(modelBean.getOriginName(), modelBean.getPltUrl(), modelBean.getMd5());
    }

    @Override
    public void onSuccess() {
        ToastUtil.showShort("onSuccess");
        transportToBluetooth();
    }

    @Override
    public void onFailed() {
        hideLoadingDialog();
        ToastUtil.showShort("onFailed");
    }

    private void transportToBluetooth() {
        hideLoadingDialog();
        String filePath = this.getExternalCacheDir() + File.separator + CommonUtil.CURRENT_USE_PLT;
        bluetoothConnectService.sendFile(filePath);
    }
}
