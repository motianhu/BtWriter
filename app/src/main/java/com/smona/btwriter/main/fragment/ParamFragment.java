package com.smona.btwriter.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.base.ui.fragment.BasePresenterFragment;
import com.smona.btwriter.R;
import com.smona.btwriter.common.CommonItemDecoration;
import com.smona.btwriter.main.adapter.ParamInfoAdapter;
import com.smona.btwriter.main.bean.ParamInfo;
import com.smona.btwriter.main.presenter.ParamPresenter;
import com.smona.btwriter.notify.NotifyCenter;
import com.smona.btwriter.notify.event.ParamChangeEvent;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class ParamFragment extends BasePresenterFragment<ParamPresenter, ParamPresenter.IParamView> implements ParamPresenter.IParamView {

    private SeekBar speedSeekBar;
    private TextView speedValueTv;

    private SeekBar pressSeekBar;
    private TextView pressValueTv;

    private XRecyclerView xRecyclerView;
    private ParamInfoAdapter adapter;

    @Override
    protected ParamPresenter initPresenter() {
        return new ParamPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_param;
    }

    @Override
    protected void initView(View content) {
        super.initView(content);
        speedSeekBar = content.findViewById(R.id.speedBar);
        speedValueTv = content.findViewById(R.id.speedValue);
        speedValueTv.setText("" + CommonUtil.SPEED_START);
        speedSeekBar.setMax(CommonUtil.SPEED_DIFF);
        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speedValueTv.setText((CommonUtil.SPEED_START + progress) + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pressSeekBar = content.findViewById(R.id.pressureBar);
        pressValueTv = content.findViewById(R.id.pressureValue);
        pressValueTv.setText("" + CommonUtil.PRESS_START);
        pressSeekBar.setMax(CommonUtil.PRESS_END);
        pressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pressValueTv.setText((CommonUtil.PRESS_START + progress) + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        xRecyclerView = content.findViewById(R.id.commonInfoList);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);

        int margin = getResources().getDimensionPixelSize(R.dimen.dimen_5dp);
        CommonItemDecoration ex = new CommonItemDecoration(0, margin);
        xRecyclerView.addItemDecoration(ex);

        adapter = new ParamInfoAdapter(R.layout.adapter_item_param);
        adapter.setOnParamListener(new ParamInfoAdapter.OnParamListener() {
            @Override
            public void onEdit(ParamInfo item) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ParamInfo.class.getName(), item);
                ARouterManager.getInstance().gotoActivityBundle(ARouterPath.PATH_TO_PARAM, bundle);
            }

            @Override
            public void onDelete(ParamInfo item) {
                showLoadingDialog();
                mPresenter.requestDelParam(item.getId());
            }

            @Override
            public void onUse(ParamInfo item) {
                clickUse(item);
            }
        });
        xRecyclerView.setAdapter(adapter);

        content.findViewById(R.id.resetValue).setOnClickListener(v -> clickResetValue());
        content.findViewById(R.id.add).setOnClickListener(v -> clickAdd());

        NotifyCenter.getInstance().registerListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        NotifyCenter.getInstance().unRegisterListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        requestParams();
    }

    private void requestParams(){
        mPresenter.requestParams();
    }

    private void clickResetValue() {

    }

    private void clickAdd() {
        ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_PARAM);
    }

    private void clickUse(ParamInfo item) {
        speedSeekBar.setProgress(item.getSpeed() - CommonUtil.SPEED_START);
        pressSeekBar.setProgress(item.getPressure() - CommonUtil.PRESS_START);
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        hideLoadingDialog();
        CommonUtil.showToastByFilter(errCode, errInfo);
    }

    @Override
    public void onParams(List<ParamInfo> paramsList) {
        adapter.setNewData(paramsList);
    }

    @Override
    public void onDelParam(int id) {
        hideLoadingDialog();
        adapter.delParam(id);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paramChange(ParamChangeEvent event) {
        requestParams();
    }
}
