package com.smona.http.business;

public interface BusinessHttpService {
    String API_URL = "http://134.175.242.78:8080";

    //ACCOUNT
    String REGISTER = "/app/account/register";
    String EMAILCODE = "/app/account/getEmailCode";
    String LOGIN = "/app/account/login";
    String CHANGEPWD = "/api/account/modifyPassword";
    String RESETPWD = "/app/account/resetPassword";
    String LOGOUT = "/app/account/logout";

    //home
    String HOME_INFO = "/api/index/indexInfo";
    String PARAM_INFO = "/api/setting/list";
    String PARAM_DEL = "/api/setting/delete";
    String PARAM_SAVE = "/api/setting/save";

    //goods
    String GOODS_LIST = "/api/goods/queryGoodsByPage";
    String GOODSDETAIL = "/api/goods/queryGoodsDetail";
    String SHOPPINGCARD_LIST = "/api/goodsCart/viewGoodsCartDetail";
    String SHOPPINGCARD_ADD = "/api/goodsCart/addGoodsToCart";
    String SHOPPINGCARD_DELETE = "/api/goodsCart/removeGoodsFromCart";
    String SHOPPINGCARD_MODIFY = "/api/goodsCart/modifyGoodsCartAmount";
    String SHOPPINGCARD_SUBMIT = "/api/goodsCart/submitCart";
    String ORDER_LIST = "/api/purchaseOrder/queryOrderByPage";
    String ORDER_DETAIL = "/api/purchaseOrder/queryOrderDetail";


    //address
    String ADDRESS_LIST = "/api/address/queryPage";
    String ADDRESS_SAVE = "/api/address/saveAddress";
    String ADDRESS_SETDEFAULT = "/api/address/setDefaultAddress";
    String ADDRESS_DELETE = "/api/address/deleteAddress";

    //brand
    String BRAND_LIST = "/api/phoneBrand/queryBrandList";
    String MODEL_LIST = "/api/phoneBrand/queryPhoneListByBrand";

    //message
    String MESSAGE_DETAIL = "/api/message/viewMessageDetail";
    String MESSAGE_LIST = "/api/message/queryMsgList";
    String MESSAGE_DELETE = "/api/message/deleteMsg";

    //make
    String MAKE_SUCCESS = "/api/account/successNotify";
    String MAKE_CHECK = "/api/account/checkUserCutTimes";
}
