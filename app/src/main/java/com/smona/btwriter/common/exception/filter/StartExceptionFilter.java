package com.smona.btwriter.common.exception.filter;

import com.smona.btwriter.common.exception.InitExceptionProcess;

/**
 * description:
 *
 * @author motianhu
 * @email motianhu@qq.com
 * created on: 7/24/19 9:52 AM
 */
public class StartExceptionFilter extends AbsExceptionFilter {

    @Override
    boolean isFilter(String api, String errCode, String errMsg) {
        return false;
    }

    @Override
    void exeFilter(String api, String errCode, String errMsg, InitExceptionProcess.OnReloadListener listener) {

    }

}
