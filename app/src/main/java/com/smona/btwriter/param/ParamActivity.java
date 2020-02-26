package com.smona.btwriter.param;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.main.bean.ParamInfo;
import com.smona.btwriter.param.presenter.ParamPresenter;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;
import com.smona.btwriter.util.ToastUtil;

@Route(path = ARouterPath.PATH_TO_PARAM)
public class ParamActivity extends BaseLanguagePresenterActivity<ParamPresenter, ParamPresenter.IParamView> implements ParamPresenter.IParamView {

    private ParamInfo paramInfo;
    private EditText nameEt;

    private SeekBar speedBar;
    private TextView speedValueTv;

    private SeekBar pressBar;
    private TextView pressValueTv;

    @Override
    protected ParamPresenter initPresenter() {
        return new ParamPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_param;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initSerilize();
        initHeader();
        initViews();
    }

    private void initSerilize() {
        Bundle bundle = getIntent().getBundleExtra(ARouterPath.PATH_TO_PARAM);
        if(bundle == null) {
            paramInfo = new ParamInfo();
            return;
        }
        paramInfo = (ParamInfo)bundle.getSerializable(ParamInfo.class.getName());
        if(paramInfo == null) {
            paramInfo = new ParamInfo();
        }
    }

    private void initHeader() {
        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());
        TextView titleTv = findViewById(R.id.title);
        int resId = R.string.new_param;
        if(paramInfo.getId() != 0) {
            resId = R.string.edit_param;
        }
        titleTv.setText(resId);
    }

    private void initViews() {
        findViewById(R.id.save).setOnClickListener(view->clickSave());
        nameEt = findViewById(R.id.param_edit);
        speedBar = findViewById(R.id.speedBar);
        speedValueTv = findViewById(R.id.speedValue);
        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speedValueTv.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        pressBar = findViewById(R.id.pressureBar);
        pressValueTv = findViewById(R.id.pressureValue);
        pressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pressValueTv.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //编辑
        if(paramInfo.getId() != 0) {
            nameEt.setText(paramInfo.getName());
            speedBar.setProgress(paramInfo.getSpeed());
            pressBar.setProgress(paramInfo.getPressure());
        }
    }

    private void clickSave() {
        String name = nameEt.getText().toString();
        if(TextUtils.isEmpty(name)) {
            ToastUtil.showShort(R.string.add_name_hint);
            return;
        }

        paramInfo.setName(nameEt.getText().toString());
        paramInfo.setSpeed(speedBar.getProgress());
        paramInfo.setPressure(pressBar.getProgress());

        showLoadingDialog();
        mPresenter.requestSaveParam(paramInfo);
    }


    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        CommonUtil.showToastByFilter(errCode, errInfo);
    }

    @Override
    public void onParamSave() {
        hideLoadingDialog();
        finish();
    }
}
