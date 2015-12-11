package com.example.scxh.mynews;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.scxh.mynews.content_fragment.NewsFragment;
import com.example.scxh.mynews.tab_fragment.TabFragment;

public class MainActivity extends FragmentActivity {
    FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.main_tab_framelayout, TabFragment.newInstance()).commit();
        manager.beginTransaction().add(R.id.main_centent_framelayout, NewsFragment.newInstance()).commit();

    }
}
