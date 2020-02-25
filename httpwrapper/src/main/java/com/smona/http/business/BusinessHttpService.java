package com.smona.http.business;

public interface BusinessHttpService {
    String API_URL = "http://134.175.242.78:8080";

    //ACCOUNT
    String REGISTER = "/app/account/register";
    String EMAILCODE = "/app/account/getEmailCode";
    String LOGIN = "/app/account/login";
    String RESETPWD = "/app/account/resetPassword";
    String LOGOUT = "/app/account/logout";

    //home
    String HOME_INFO = "/api/index/indexInfo";
    String PARAM_INFO = "/api/setting/list";

    //goods
    String GOODS_LIST = "/api/goods/queryGoodsByPage";

    //brand
    String BRAND_LIST = "/api/phoneBrand/queryBrandList";
    String MODEL_LIST = "/api/phoneBrand/queryPhoneListByBrand";
}
