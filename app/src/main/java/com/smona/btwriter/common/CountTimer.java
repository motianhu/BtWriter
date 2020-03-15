package com.smona.btwriter.common;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.smona.btwriter.R;
import com.smona.btwriter.common.exception.AppContext;

/**
 * description:
 *
 * @author motianhu
 * @email motianhu@qq.com
 * created on: 7/9/19 11:32 AM
 */
public class CountTimer {
    private static final long DURATION = 60 * 1000;
    private TextView mShowTv;
    private CountDownTimer mCountDownTimer;
    private ITimerListener mListener;

    public void setParam(TextView showTv, ITimerListener listener) {
        mShowTv = showTv;
        mListener = listener;
    }

    public void start() {
        refreshUI(DURATION);
        mCountDownTimer  = new CountDownTimer(DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                refreshUI(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                if(mListener != null) {
                    mListener.onFinish();
                }
            }
        };
        mCountDownTimer.start();
    }

    public void cancelTimer() {
        if(mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    private void refreshUI(long millisUntilFinished) {
        String mmss= millisUntilFinished / 1000 + AppContext.getAppContext().getResources().getString(R.string.get_code_retry);
        if(mShowTv != null) {
            mShowTv.setText(mmss);
        }
    }

    public interface ITimerListener {
        void onFinish();
    }
}
