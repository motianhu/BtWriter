package com.smona.btwriter.make;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smona.btwriter.R;
import com.smona.btwriter.language.BaseLanguagePresenterActivity;
import com.smona.btwriter.make.presenter.MakePresenter;
import com.smona.btwriter.util.ARouterPath;

@Route(path = ARouterPath.PATH_TO_MAKE)
public class MakeActivity extends BaseLanguagePresenterActivity<MakePresenter, MakePresenter.IMakeView> implements MakePresenter.IMakeView {
    @Override
    protected MakePresenter initPresenter() {
        return new MakePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_make;
    }

    @Override
    public void onError(String api, String errCode, String errInfo) {

    }
}
