package com.smona.http.business;

public interface BusinessHttpService {
    String API_URL = "http://134.175.242.78:8080";

    //ACCOUNT
    String REGISTER = "/app/account/register";
    String EMAILCODE = "/app/account/getEmailCode";
    String LOGIN = "/app/account/login";
    String RESETPWD = "/app/account/resetPassword";
    String LOGOUT = "/app/account/logout";
}
