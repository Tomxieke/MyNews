package com.example.scxh.mynews.tab_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scxh.mynews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {


    public static TabFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab, container, false);

        return view;
    }

}
