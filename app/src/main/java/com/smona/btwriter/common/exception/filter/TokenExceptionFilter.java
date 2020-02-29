package com.smona.btwriter.common.exception.filter;

import android.text.TextUtils;
import android.view.View;

import com.smona.btwriter.common.exception.InitExceptionProcess;

/**
 * description:
 *
 * @author motianhu
 * @email motianhu@qq.com
 * created on: 7/24/19 9:48 AM
 */
public class TokenExceptionFilter extends AbsExceptionFilter {
    @Override
    boolean isFilter(String api, String errCode, String errMsg) {
        return "10001".equals(errCode);
    }

    @Override
    void exeFilter(String api, String errCode, String errMsg, InitExceptionProcess.OnReloadListener listener) {
        if (TextUtils.isEmpty(api) || api.endsWith("_first")) { //控制显示的请求;非控制类接口不处理(可能有问题)
            mProcess.getErrorView().setNoNetwork(listener);
            for (View view : mProcess.getContentViews()) {
                view.setVisibility(View.GONE);
            }
        }
    }
}
