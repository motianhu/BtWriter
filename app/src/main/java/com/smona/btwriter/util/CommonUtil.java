package com.smona.btwriter.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputFilter;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.smona.base.ui.activity.BaseActivity;
import com.smona.btwriter.common.ActionModeCallbackInterceptor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    public static final int START_PAGE = 1;
    public static final int SIZE = 10;


    public static final int SPEED_START = 100;
    public static final int SPEED_END = 800;
    public static final int SPEED_DIFF = SPEED_END - SPEED_START;

    public static final int PRESS_START = 300;
    public static final int PRESS_END = 1000;
    public static final int PRESS_DIFF = PRESS_END - PRESS_START;


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

    public static void showToastByFilter(String stateCode, String msg) {
        ToastUtil.showShort(msg);
    }

    public static boolean isEmptyList(List list) {
        return list == null || list.isEmpty();
    }

    /**
     * 关掉所有页面
     * @param context
     */
    public static void sendCloseAllActivity(Context context) {
        Intent closeAllIntent = new Intent(BaseActivity.ACTION_BASE_ACTIVITY);
        closeAllIntent.putExtra(BaseActivity.ACTION_BASE_ACTIVITY_EXIT_KEY, BaseActivity.ACTION_BASE_ACTIVITY_EXIT_VALUE);
        LocalBroadcastManager.getInstance(context).sendBroadcast(closeAllIntent);
    }
}
