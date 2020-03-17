package com.smona.btwriter.data;

import android.text.TextUtils;

import com.smona.btwriter.util.SPUtils;

import java.util.Locale;

public class LanuageDataCenter {

    public final static String SERVER_ZH_CN = "zh_CN";
    public final static String SERVER_EN_US = "en_US";

    public static final String LOCALE_ZH_CN = "zh_CN";
    private static final String LOCALE_ZH_HK = "zh_HK";
    private static final String LOCALE_ZH_TW = "zh_TW";
    public static final String LOCALE_EN = "en";


    private String lanuage;

    private LanuageDataCenter() {
    }

    private static class ParamHolder {
        private static LanuageDataCenter paramCenter = new LanuageDataCenter();
    }

    public static LanuageDataCenter getInstance() {
        return ParamHolder.paramCenter;
    }

    private void setLanuage(String lanuage) {
        this.lanuage = lanuage;
    }

    public String getLanuage() {
        return lanuage;
    }

    public void loadLanuage() {
        String temp = (String)SPUtils.get(SPUtils.CONFIG_INFO, "");
        if(TextUtils.isEmpty(temp)) {
            setLanuage(getSysLanuage());
        } else {
            setLanuage(temp);
        }
    }

    public void saveLanuage(String lanuage) {
        //刷新内存
        setLanuage(lanuage);
        //持久化数据
        SPUtils.put(SPUtils.CONFIG_INFO, lanuage);
    }

    private static String getSysLanuage(){
        String curSysLa = Locale.getDefault().toString();
        if (LOCALE_ZH_CN.equalsIgnoreCase(curSysLa) || LOCALE_ZH_HK.equalsIgnoreCase(curSysLa) || LOCALE_ZH_TW.equalsIgnoreCase(curSysLa)) {
            return SERVER_ZH_CN;
        } else {
            return SERVER_EN_US;
        }
    }
}
