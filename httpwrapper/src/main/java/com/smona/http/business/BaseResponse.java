package com.smona.http.business;

/**
 * description:
 *
 * @author motianhu
 * @email motianhu@qq.com
 * created on: 3/25/19 1:59 PM
 */
public class BaseResponse<R> {
    public String code;
    public String message;
    public R data;
}
