package com.example.nepus.driverassistance;

import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.ic.kmitl.idas.datacontroller.AbstractDataController;
import com.ic.kmitl.idas.datacontroller.DataControllerFactory;
import com.ic.kmitl.idas.datacontroller.DataReceiver;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {


    private static final String TAG = MainActivity.class.getSimpleName();
    private NavigationDrawerFragment navDrawerFrag;
    private CharSequence title;
//    private static PlaceholderFragment placeholderFragment = null;


    private static final int MESSAGE_REFRESH = 101;
    private static final long REFRESH_TIMEOUT_MILLIS = 5000;

//    use mHandler to refresh device list periodically
//    mHandler will be called from onResume and continue calling it self periodically
    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_REFRESH:
                    refreshDeviceList();
                    mHandler.sendEmptyMessageDelayed(MESSAGE_REFRESH, REFRESH_TIMEOUT_MILLIS);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }

    };
//    call abstractDataController.sendData(data) to send data
//    use below code to convert data from string to byte[]
//    byte[] data = stringData.getBytes(Charset.forName("UTF-8"));
//    call abstractDataController.isConnected() to check if the connection is established successfully
    private AbstractDataController abstractDataController = null;

//    DataReceiver of AbstractDataController
    private DataReceiver dataReceiverListener = new DataReceiver() {
        @Override
        public void onDataReceive(byte[] data) {

            String stringData = null;
            try {
                stringData = new String(data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Log.i(TAG, stringData);

        }
    };


    private void refreshDeviceList() {
        new AsyncTask<Void, Void, List<UsbSerialPort>>() {
            @Override
            protected List<UsbSerialPort> doInBackground(Void... params) {
                Log.d(TAG, "Refreshing device list ...");
                SystemClock.sleep(1000);


                UsbManager mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
                final List<UsbSerialDriver> drivers =
                        UsbSerialProber.getDefaultProber().findAllDrivers(mUsbManager);

                final List<UsbSerialPort> result = new ArrayList<UsbSerialPort>();
                for (final UsbSerialDriver driver : drivers) {
                    final List<UsbSerialPort> ports = driver.getPorts();
                    Log.d(TAG, String.format("+ %s: %s port%s",
                            driver, Integer.valueOf(ports.size()), ports.size() == 1 ? "" : "s"));
                    result.addAll(ports);
                }

                return result;
            }

            @Override
            protected void onPostExecute(List<UsbSerialPort> result) {
                if (result.isEmpty())
                    return;


                Log.i(TAG, result.toString());
                if (result.size() == 1){

                    abstractDataController = DataControllerFactory.createUsbDataController(MainActivity.this, result.get(0));
                    abstractDataController.setDataReceiver(dataReceiverListener);
                    abstractDataController.connect();

                    mHandler.removeMessages(MESSAGE_REFRESH);
                }

            }

        }.execute((Void) null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navDrawerFrag = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        //Navigation drawer
        navDrawerFrag.setUp(R.id.navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessage(MESSAGE_REFRESH);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeMessages(MESSAGE_REFRESH);
    }

    private Fragment [] fragments;

    public MainActivity() {
        fragments = new Fragment[5];
        fragments[0] = new SystemPagerFragment();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (position > 1)
            position = position % 2;

        // crate fragment to place page
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragments[position]).commit();

//        if (placeholderFragment == null)
//            placeholderFragment = new PlaceholderFragment();
////        fragmentManager.beginTransaction()
////                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
////                .commit();
//        fragmentManager.beginTransaction().replace(R.id.container, placeholderFragment.newInstance(position + 1)).commit();

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                title = getString(R.string.title_dashboard);
                break;
            case 2:
                title = getString(R.string.title_vehicle);
                break;
            case 3:
                title = getString(R.string.title_history);
                break;
            case 4:
                title = getString(R.string.title_setting);
                break;
            case 5:
                title = getString(R.string.title_notification);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!navDrawerFrag.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

//    class PlaceholderFragment extends Fragment {
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        public PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            int page = getArguments().getInt(ARG_SECTION_NUMBER);
//            if(page == 1) {
//                ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_viewpage, container, false);
//                return rootView;
//            }
//            if(page == 2) {
//                View rootView = inflater.inflate(R.layout.fragment_information, container, false);
//                return rootView;
//            }
//            else if(page == 4){
//                View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
//                return rootView;
//            }
//            return null;
//        }
//
//        @Override
//        public void onAttach(Activity activity) {
//            super.onAttach(activity);
//            ((MainActivity) activity).onSectionAttached(
//                    getArguments().getInt(ARG_SECTION_NUMBER));
//        }
//
//    }

}
