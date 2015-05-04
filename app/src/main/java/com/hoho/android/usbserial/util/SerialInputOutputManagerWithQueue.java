package com.hoho.android.usbserial.util;

import com.hoho.android.usbserial.driver.UsbSerialPort;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by AbS01ute on 4/1/15 AD.
 */
public class SerialInputOutputManagerWithQueue extends SerialInputOutputManager {


    private Queue<byte[]> queue = null;
    private OutBufferEmptyListener listener = new OutBufferEmptyListener() {
        @Override
        public void onOutBufferEmpty() {
            if (!queue.isEmpty()) {
                try {
                    Thread.sleep(36);
                    SerialInputOutputManagerWithQueue.super.writeAsync(queue.remove());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    };



    public SerialInputOutputManagerWithQueue(UsbSerialPort driver) {
        this(driver, null);
    }

    public SerialInputOutputManagerWithQueue(UsbSerialPort driver, Listener listener) {
        super(driver, listener);
        init();
    }

    private void init(){

//        queue = new LinkedList<byte[]>();
        queue = new LinkedList<>();
        setOutBufferEmptyListener(listener);
    }

    @Override
    public void writeAsync(byte[] data) {
        if (isOutBufferEmpty())
            super.writeAsync(data);
        else
            queue.add(data);
    }



}
