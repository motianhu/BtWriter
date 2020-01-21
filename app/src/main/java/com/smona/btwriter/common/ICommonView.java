package com.smona.btwriter.common;

import com.smona.base.ui.mvp.IBaseView;

public interface ICommonView extends IBaseView {
    void onError(String api, String errCode, String errInfo);
}
