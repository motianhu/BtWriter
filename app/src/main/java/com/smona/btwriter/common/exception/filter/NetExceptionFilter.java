package com.smona.btwriter.common.exception.filter;

import android.text.TextUtils;
import android.view.View;

import com.smona.btwriter.R;
import com.smona.btwriter.common.exception.InitExceptionProcess;
import com.smona.btwriter.util.ToastUtil;

/**
 * description:
 *
 * @author motianhu
 * @email motianhu@qq.com
 * created on: 7/24/19 9:16 AM
 */
public class NetExceptionFilter extends AbsExceptionFilter {

    @Override
    boolean isFilter(String api, int errCode, String errMsg) {
        return errCode >= 1000 && errCode <= 1006;
    }

    @Override
    void exeFilter(String api, int errCode, String errMsg, InitExceptionProcess.OnReloadListener listener) {
        ToastUtil.showShort(R.string.network_error);
        if (TextUtils.isEmpty(api) || api.endsWith("_first")) { //控制显示的请求;非控制类接口不处理(可能有问题)
            mProcess.getErrorView().setNoNetwork(listener);
            for (View view : mProcess.getContentViews()) {
                view.setVisibility(View.GONE);
            }
        }
    }
}
