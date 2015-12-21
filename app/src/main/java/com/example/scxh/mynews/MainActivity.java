package com.example.scxh.mynews;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.scxh.mynews.content_fragment.NewsFragment;
import com.example.scxh.mynews.prcture_type_news.PricturFragment;
import com.example.scxh.mynews.radio_type_news.VidaoFragment;
import com.example.scxh.mynews.tab_fragment.TabFragment;
import com.example.scxh.mynews.uitl.StrDataInter;
import com.example.scxh.mynews.weather.WeatherFragment;

public class MainActivity extends FragmentActivity implements TabFragment.TabCallBack {
    private FragmentManager manager;
    private NewsFragment newsFragment ;
    private PricturFragment pricturFragment;
    private VidaoFragment vidaoFragment;
    private WeatherFragment weatherFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.main_tab_framelayout, TabFragment.newInstance()).commit();
    //    manager.beginTransaction().add(R.id.main_centent_framelayout, NewsFragment.newInstance()).commit();
       //上面这行代码是添加了两次
        newsFragment = NewsFragment.newInstance(); //这里要先实例化。
        manager.beginTransaction().add(R.id.main_centent_framelayout, newsFragment).commit();
    }


    @Override
    public void getMsg(String msg) {
        FragmentTransaction ft = manager.beginTransaction();
        hideAllFragment(ft);
        if (msg.equalsIgnoreCase(StrDataInter.TAB_NEWS)){
            if (newsFragment == null){
                newsFragment = NewsFragment.newInstance();
            }
            ft.show(newsFragment);
        }else if (msg.equalsIgnoreCase(StrDataInter.TAB_PRCTURE)){
            if (pricturFragment == null){
                pricturFragment = PricturFragment.newInstance();
                ft.add(R.id.main_centent_framelayout,pricturFragment);
            }else {
                ft.show(pricturFragment);
            }
        }else if (msg.equalsIgnoreCase(StrDataInter.TAB_REDIO)){
            if (vidaoFragment == null){
                vidaoFragment = VidaoFragment.newInstance();
                ft.add(R.id.main_centent_framelayout, vidaoFragment);
            }else {
                ft.show(vidaoFragment);
            }
        }else if (msg.equalsIgnoreCase(StrDataInter.TAB_WEATHER)){
            if (weatherFragment ==null){
                weatherFragment = WeatherFragment.newInstance();
                ft.add(R.id.main_centent_framelayout,weatherFragment);
            }else{
                ft.show(weatherFragment);
            }

        }
        ft.commit();
    }

   /* @Override
    public void getMsg(String msg) {
        FragmentTransaction ft = manager.beginTransaction();
        if(msg.equalsIgnoreCase(StrDataInter.TAB_NEWS)){
           if (newsFragment == null){
               newsFragment = NewsFragment.newInstance();
           }
            ft.replace(R.id.main_centent_framelayout,newsFragment);
            Toast.makeText(MainActivity.this,"新闻",Toast.LENGTH_SHORT).show();
        }else if(msg.equalsIgnoreCase(StrDataInter.TAB_PRCTURE)){
            if (pricturFragment == null){
                pricturFragment = PricturFragment.newInstance();
            }
            ft.replace(R.id.main_centent_framelayout,pricturFragment);
            Toast.makeText(MainActivity.this,"图片",Toast.LENGTH_SHORT).show();
        }
        ft.commit();
        Toast.makeText(MainActivity.this,"0000000000000000",Toast.LENGTH_SHORT).show();
    }*/

    /**
     * 隐藏所有Frament
     * @param transaction  FragmentTransaction实例
     */
    public void hideAllFragment(FragmentTransaction transaction){
        if (newsFragment != null){
            transaction.hide(newsFragment);
        }
        if (pricturFragment !=null){
            transaction.hide(pricturFragment);
        }
        if(vidaoFragment != null){
            transaction.hide(vidaoFragment);
        }
        if (weatherFragment != null){
            transaction.hide(weatherFragment);
        }
    }


}
