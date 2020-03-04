package com.smona.btwriter.make;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.bluetooth.transport.BluetoothConnectService;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.make.presenter.MakePresenter;
import com.smona.btwriter.model.bean.ModelBean;
import com.smona.btwriter.util.ARouterPath;

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

    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
