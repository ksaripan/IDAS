package com.ic.kmitl.idas.datacontroller;

import android.app.Activity;

/**
 * Created by AbS01ute on 2/13/15 AD.
 */
public abstract class AbstractDataController {

    public abstract void sendData(byte[] data);
    public abstract void connect();
//    protected DataReceiver dataReceiver;
    private DataReceiver dataReceiver;
    private Activity activity;
    private boolean connected = false;

    protected AbstractDataController(Activity activity) {
        this.activity = activity;
    }

    public DataReceiver getDataReceiver() {
        return dataReceiver;
    }

    public void setDataReceiver(DataReceiver dataReceiver) {
        this.dataReceiver = dataReceiver;
    }

    public boolean hasDataReceiver(){
        return dataReceiver != null;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public boolean isConnected() {
        return connected;
    }

    protected void setConnected(boolean connected) {
        this.connected = connected;
    }
}
