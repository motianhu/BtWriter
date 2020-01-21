package com.smona.btwriter.util;

import android.text.InputFilter;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.smona.btwriter.common.ActionModeCallbackInterceptor;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    /**
     * 获取启动时的系统语言，并转换为可支持的语言。
     * @return
     */
    public static String getSysLanuage(){
        String curSysLa = Locale.getDefault().toString();
        return curSysLa;
    }

    public static void setMaxLenght(EditText editText, int length) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    /**
     * 检验是否是邮箱
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static final int MAX_NAME_LENGHT = 100;
    public static final int MAX_PWD_LENGHT = 60;
    public static final int MAX_PHONE_LENGHT = 30;

    /**
     * 禁用密码框的复制粘贴
     * @param editText
     */
    public static void disableEditTextCopy(EditText editText) {
        editText.setLongClickable(false);
        editText.setTextIsSelectable(false);
        editText.setCustomInsertionActionModeCallback(new ActionModeCallbackInterceptor());
        editText.setCustomSelectionActionModeCallback(new ActionModeCallbackInterceptor());
        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // setInsertionDisabled when user touches the view
                setInsertionDisabled(editText);
            }
            return false;
        });
    }

    private static void setInsertionDisabled(EditText editText) {
        try {
            Field editorField = TextView.class.getDeclaredField("mEditor");
            editorField.setAccessible(true);
            Object editorObject = editorField.get(editText);

            // if this view supports insertion handles
            Class editorClass = Class.forName("android.widget.Editor");
            Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
            mInsertionControllerEnabledField.setAccessible(true);
            mInsertionControllerEnabledField.set(editorObject, false);

            // if this view supports selection handles
            Field mSelectionControllerEnabledField = editorClass.getDeclaredField("mSelectionControllerEnabled");
            mSelectionControllerEnabledField.setAccessible(true);
            mSelectionControllerEnabledField.set(editorObject, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
