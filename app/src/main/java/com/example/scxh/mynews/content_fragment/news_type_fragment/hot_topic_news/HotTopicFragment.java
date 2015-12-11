package com.example.scxh.mynews.content_fragment.news_type_fragment.hot_topic_news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scxh.mynews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotTopicFragment extends Fragment {


    public static HotTopicFragment newInstance() {
        
        Bundle args = new Bundle();
        
        HotTopicFragment fragment = new HotTopicFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hot_topic, container, false);
    }

}
