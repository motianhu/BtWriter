package com.smona.btwriter.bluetooth.transport;

public abstract class AbsTransportThread extends Thread {

    public AbsTransportThread(AbsTransportThread nextTransportThread) {
        this.nextTransportThread = nextTransportThread;
    }

    private AbsTransportThread nextTransportThread;

    @Override
    public void run() {

    }



}
