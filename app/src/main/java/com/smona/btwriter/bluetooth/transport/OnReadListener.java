package com.smona.btwriter.bluetooth.transport;

public interface OnReadListener {
    void onCreateChannel(boolean success);
    void executeFinish(boolean success);
}
