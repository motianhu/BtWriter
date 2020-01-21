package com.smona.btwriter.util;

import java.util.Locale;

public class CommonUtil {
    /**
     * 获取启动时的系统语言，并转换为可支持的语言。
     * @return
     */
    public static String getSysLanuage(){
        String curSysLa = Locale.getDefault().toString();
        return curSysLa;
    }
}
