package com.smona.btwriter;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.smona.base.http.HttpManager;
import com.smona.btwriter.common.exception.AppContext;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.http.wrapper.FilterChains;
import com.smona.logger.Logger;

import java.util.List;

public class BtWriterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (!isMainProcess()) {
            return;
        }

        AppContext.setAppContext(this);
        Logger.init(this);
        //初始化页面路由
        ARouterManager.init(this, true);
        //初始化网络库
        HttpManager.init(this);
        //过滤器
        FilterChains.getInstance().addAspectRouter("10001", () -> ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_LOGIN));
    }

    /**
     * 非主进程
     * @return
     */
    protected boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
