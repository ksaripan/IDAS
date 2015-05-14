package com.ic.kmitl.idas.datacontroller;

import com.hoho.android.usbserial.driver.UsbSerialPort;

/**
 * Created by AbS01ute on 3/2/15 AD.
 */
public class UsbSerialPortParameters {
    private int baudRate;
    private int dataBits;
    private int stopBits;
    private int parity;

    public final int DEFAULT_BAUD_RATE = 115200;
    public final int DEFAULT_DATA_BITS = 8;
    public final int DEFAULT_STOP_BITS = UsbSerialPort.STOPBITS_1;
    public final int DEFAULT_PARITY = UsbSerialPort.PARITY_NONE;

    public UsbSerialPortParameters() {
        this.baudRate = DEFAULT_BAUD_RATE;
        this.dataBits = DEFAULT_DATA_BITS;
        this.stopBits = DEFAULT_STOP_BITS;
        this.parity = DEFAULT_PARITY;
    }

    public UsbSerialPortParameters(int baudRate, int dataBits, int stopBits, int parity) {
        this.baudRate = baudRate;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.parity = parity;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public int getDataBits() {
        return dataBits;
    }

    public void setDataBits(int dataBits) {
        this.dataBits = dataBits;
    }

    public int getStopBits() {
        return stopBits;
    }

    public void setStopBits(int stopBits) {
        this.stopBits = stopBits;
    }

    public int getParity() {
        return parity;
    }

    public void setParity(int parity) {
        this.parity = parity;
    }
}
