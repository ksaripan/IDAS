package com.ic.kmitl.idas.datacontroller;

import android.app.Activity;

import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

/**
 * Created by AbS01ute on 5/2/15 AD.
 */
public class DataControllerFactory {

    public static AbstractDataController createDataController(Activity activity){
        UsbSerialPortParameters usbParam = new UsbSerialPortParameters();
        return new ArduinoDataController(activity, usbParam);
    }

    public static ArduinoDataController createUsbDataController(Activity activity, UsbSerialPort usbSerialPort){
        UsbSerialPortParameters usbParam = new UsbSerialPortParameters();
        ArduinoDataController arduinoDataController = new ArduinoDataController(activity, usbParam);
        arduinoDataController.setUsbSerialPort(usbSerialPort);
        return arduinoDataController;
    }

}
