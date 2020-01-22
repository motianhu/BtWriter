package com.smona.http.business;

public interface BusinessHttpService {
    String API_URL = "http://134.175.242.78:8080";

    //ACCOUNT
    String REGISTER = "/account/register";
    String EMAILCODE = "/account/getEmailCode";
    String LOGIN = "/account/login";
    String LOGOUT = "/account/logout";
    String CHANGEPWD = "/account/resetPassword";
}
