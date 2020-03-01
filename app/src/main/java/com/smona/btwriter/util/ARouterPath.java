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

    String PATH_TO_MESSAGELIST = "/app/activity/messagelist";
    String PATH_TO_MESSAGEDETAIL = "/app/activity/messagedetail";
    //商品列表和详情
    String PATH_TO_GOODSLIST = "/app/activity/goodsList";
    String PATH_TO_GOODSDETAIL = "/api/goods/goodsDetail";
    String PATH_TO_SHOPPINGCARD = "/app/activity/shoppingcard";

    String PATH_TO_BRAND = "/app/activity/brand";
    String PATH_TO_MODEL = "/app/activity/model";
    //address
    String PATH_TO_ADDRESSLIST = "/app/activity/addressList";
    String PATH_TO_ADDRESS = "/app/activity/address";
    String PATH_TO_MAKE = "/app/activity/make";
    String PATH_TO_PARAM = "/app/activity/param";

    //bluetooth
    String PATH_TO_BLUETOOTH_LIST = "/app/activity/bluetooth";

    //request code
    int REQUEST_DEVICE_DETAIL = 1;
    int REQUEST_DEVICE_DETAIL_MODIFY_PIC = 2;
}
