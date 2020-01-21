package com.smona.btwriter.util;

/**
 * description:
 * 页面路由表
 * @author motianhu
 * @email motianhu@qq.com
 * created on: 8/30/19 11:43 AM
 */
public interface ARouterPath {
    String PATH_TO_SPLASH = "/app/activity/splash";
    String PATH_TO_LOGIN = "/app/activity/login";
    String PATH_TO_REGISTER = "/app/activity/register";
    String PATH_TO_FORGETPWD = "/app/activity/forgetPwd";
    String PATH_TO_CHANGEPWD = "/app/activity/changePwd";

    String PATH_TO_MAIN = "/app/activity/main";
    String PATH_TO_SCAN = "/app/activity/scan";

    String PATH_TO_MESSAGELIST = "/app/activity/message";
    String PATH_TO_PURCHASELIST = "/app/activity/purchase";
    String PATH_TO_BRAND = "/app/activity/brand";
    String PATH_TO_MODEL = "/app/activity/model";
    String PATH_TO_ADDRESS = "/app/activity/address";

    //request code
    int REQUEST_DEVICE_DETAIL = 1;
    int REQUEST_DEVICE_DETAIL_MODIFY_PIC = 2;
}
