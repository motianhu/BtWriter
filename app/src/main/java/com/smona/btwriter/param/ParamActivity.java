package com.smona.btwriter.param;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.param.presenter.ParamPresenter;
import com.smona.btwriter.util.ARouterPath;

@Route(path = ARouterPath.PATH_TO_PARAM)
public class ParamActivity extends BaseLanguagePresenterActivity<ParamPresenter, ParamPresenter.IParamView> implements ParamPresenter.IParamView {
    @Override
    protected ParamPresenter initPresenter() {
        return new ParamPresenter();
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
