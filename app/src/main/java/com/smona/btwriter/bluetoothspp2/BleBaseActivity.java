package com.smona.btwriter.bluetoothspp2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.smona.base.ui.activity.BaseActivity;

import java.util.Locale;

/**
 * Created by QunChen on 2018/5/5.
 */
public class BleBaseActivity extends BaseActivity {
    protected ProgressDialog mProgressDlg;
    protected Context mContent;

    protected final static String mHexStr = "0123456789ABCDEF";//检查16进制字符串是否有效
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = this;
    }

    protected void showProgressDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDlg == null) {
                    mProgressDlg = ProgressDialog.show(BleBaseActivity.this, null, msg, true);
                    mProgressDlg.setCancelable(true);
                }
            }
        });

    }

    protected void dismissProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDlg != null) {
                    mProgressDlg.dismiss();
                    mProgressDlg = null;
                }
            }
        });

    }

    protected void setProgressMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDlg != null && mProgressDlg.isShowing()) {
                    mProgressDlg.setMessage(msg);
                }
            }
        });

    }

    public void showToast(final String msg) {
        if (this.isFinishing()) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContent, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean checkHexStr(String sHex) {
        String sTmp = sHex.toString().trim().replace(" ", "").toUpperCase(Locale.US);
        int iLen = sTmp.length();

        if (iLen > 1 && iLen % 2 == 0) {
            for (int i = 0; i < iLen; i++)
                if (!mHexStr.contains(sTmp.substring(i, i + 1)))
                    return false;
            return true;
        } else
            return false;
    }
    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) mHexStr.indexOf(c);
    }
}
