package com.smona.btwriter.data;

public class AccountDataCenter {
    private AccountInfo accountInfo = new AccountInfo();

    private AccountDataCenter() {
    }

    private static class ParamHolder {
        private static AccountDataCenter paramCenter = new AccountDataCenter();
    }

    public static AccountDataCenter getInstance() {
        return ParamHolder.paramCenter;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(String email, String token) {
        this.accountInfo.setToken(token);
        this.accountInfo.setEmail(email);
    }
}
