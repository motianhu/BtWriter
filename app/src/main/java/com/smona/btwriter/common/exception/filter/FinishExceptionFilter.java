package com.smona.btwriter.common.exception.filter;

import android.text.TextUtils;
import android.view.View;

import com.smona.btwriter.R;
import com.smona.btwriter.common.exception.AppContext;
import com.smona.btwriter.common.exception.InitExceptionProcess;
import com.smona.btwriter.util.CommonUtil;

/**
 * description:
 *
 * @author motianhu
 * @email motianhu@qq.com
 * created on: 7/24/19 9:48 AM
 */
public class FinishExceptionFilter extends AbsExceptionFilter {
    @Override
    boolean isFilter(String api, String errCode, String errMsg) {
        return true;
    }

    @Override
    void exeFilter(String api, String errCode, String errMsg, InitExceptionProcess.OnReloadListener listener) {
        if (TextUtils.isEmpty(api) || api.endsWith("_first")) {
            mProcess.getErrorView().setNoContent(mProcess.getErrorView().getContext().getString(R.string.no_content), R.drawable.nodata);
            for (View view : mProcess.getContentViews()) {
                view.setVisibility(View.GONE);
            }
        }
        if (TextUtils.isEmpty(errMsg)) {
            CommonUtil.showShort(AppContext.getAppContext(), R.string.network_error);
        } else {
            CommonUtil.showShort(AppContext.getAppContext(), errMsg);
        }
    }
}
