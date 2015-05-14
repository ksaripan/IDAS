package com.example.nepus.driverassistance;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SystemPagerFragment extends Fragment{
    public static final String KEY_PAGE = "key";
    private int pageNumber;
    private ViewGroup rootView;


    public SystemPagerFragment() {}

    public static SystemPagerFragment create(int pageNumber) {
        SystemPagerFragment fragment = new SystemPagerFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_PAGE, pageNumber);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(KEY_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        switch (pageNumber) {
            case 0:
                rootView = (ViewGroup) inflater.inflate(R.layout.fragment_information, container, false);
                return rootView;
            case 1:
                rootView = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);
                return rootView;
            case 2:
                rootView = (ViewGroup) inflater.inflate(R.layout.fragment_information, container, false);
                return rootView;
        }
        return null;
    }
}
