package com.example.scxh.mynews.weather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scxh.mynews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {


    public static WeatherFragment newInstance() {
        
        Bundle args = new Bundle();
        
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

}
