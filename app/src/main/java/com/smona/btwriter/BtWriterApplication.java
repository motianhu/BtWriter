package com.smona.btwriter;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.smona.base.http.HttpManager;
import com.smona.btwriter.bluetooth.BluetoothDataCenter;
import com.smona.btwriter.common.exception.AppContext;
import com.smona.btwriter.data.AccountDataCenter;
import com.smona.btwriter.data.AccountInfo;
import com.smona.btwriter.data.LanuageDataCenter;
import com.smona.btwriter.push.PushApiManager;
import com.smona.btwriter.util.ARouterManager;
import com.smona.btwriter.util.ARouterPath;
import com.smona.btwriter.util.CommonUtil;
import com.smona.btwriter.util.SPUtils;
import com.smona.http.config.GsonUtil;
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
        FilterChains.getInstance().addAspectRouter("10001", this::processLogout);
        //注册监听蓝牙数据变化
        BluetoothDataCenter.getInstance().initBlueDataCenter(this);
        //初始化Push
        PushApiManager.getInstance().init(this);
        //初始化语言
        LanuageDataCenter.getInstance().loadLanuage();
        //load user info
        loadLoginInfo();
    }

    private void processLogout() {
        SPUtils.put(SPUtils.LOGIN_INFO, "");
        CommonUtil.sendCloseAllActivity(this);
        ARouterManager.getInstance().gotoActivity(ARouterPath.PATH_TO_LOGIN);
    }

    private void loadLoginInfo() {
        String loginInfo = (String) SPUtils.get(SPUtils.LOGIN_INFO, "");
        if (TextUtils.isEmpty(loginInfo)) {
            return;
        }
        AccountInfo accountInfo = GsonUtil.jsonToObj(loginInfo, AccountInfo.class);
        if (accountInfo != null && !TextUtils.isEmpty(accountInfo.getToken())) {
            AccountDataCenter.getInstance().setAccountInfo(accountInfo.getEmail(), accountInfo.getToken());
        }
    }

    /**
     * 非主进程
     *
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
