package com.smona.btwriter.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smona.base.ui.fragment.BasePresenterFragment;
import com.smona.btwriter.R;
import com.smona.btwriter.common.CommonItemDecoration;
import com.smona.btwriter.main.adapter.CommonInfoAdapter;
import com.smona.btwriter.main.bean.ParamInfo;
import com.smona.btwriter.main.presenter.ParamPresenter;
import com.smona.btwriter.util.CommonUtil;

import java.util.List;

public class ParamFragment extends BasePresenterFragment<ParamPresenter, ParamPresenter.IParamView> implements ParamPresenter.IParamView {

    private SeekBar speedSeekBar;
    private TextView speedValueTv;

    private SeekBar pressSeekBar;
    private TextView pressValueTv;

    private XRecyclerView xRecyclerView;
    private CommonInfoAdapter adapter;

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
        pressSeekBar = content.findViewById(R.id.pressureBar);
        pressValueTv = content.findViewById(R.id.pressureValue);

        xRecyclerView = content.findViewById(R.id.commonInfoList);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);

        int margin = getResources().getDimensionPixelSize(R.dimen.dimen_5dp);
        CommonItemDecoration ex = new CommonItemDecoration(0, margin, 0);
        xRecyclerView.addItemDecoration(ex);

        adapter = new CommonInfoAdapter(R.layout.adapter_item_commoninfo);
        xRecyclerView.setAdapter(adapter);

        content.findViewById(R.id.resetValue).setOnClickListener(v -> clickResetValue());
        content.findViewById(R.id.add).setOnClickListener(v -> clickAdd());
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.requestParams();
    }

    private void clickResetValue() {

    }

    private void clickAdd() {

    }

    @Override
    public void onError(String api, String errCode, String errInfo) {
        CommonUtil.showToastByFilter(errCode, errInfo);
    }

    @Override
    public void onParams(List<ParamInfo> paramsList) {
        adapter.setNewData(paramsList);
    }
}
