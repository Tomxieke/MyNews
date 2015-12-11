package com.example.scxh.mynews.content_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scxh.mynews.R;
import com.example.scxh.mynews.content_fragment.news_type_fragment.entertainment.PlayFragment;
import com.example.scxh.mynews.content_fragment.news_type_fragment.headlinenews.HeadlineNewsFragment;
import com.example.scxh.mynews.content_fragment.news_type_fragment.hot_topic_news.HotTopicFragment;
import com.lynnchurch.horizontalscrollmenu.BaseAdapter;
import com.lynnchurch.horizontalscrollmenu.HorizontalScrollMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements ViewPager.OnPageChangeListener{

    private HorizontalScrollMenu mHorizontalScrollMenu;  //横向滑动菜单
    private ViewPager mViewPager;

    public static NewsFragment newInstance() {

        Bundle args = new Bundle();

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.news_viewpager);
        mViewPager.setAdapter(new ViewPagerFragmentAdapter(getFragmentManager(),getViewPagerData()));
        mViewPager.setOnPageChangeListener(this);
        mHorizontalScrollMenu = (HorizontalScrollMenu) view.findViewById(R.id.HorizontalScrollMenu);
        mHorizontalScrollMenu.setSwiped(false);
        mHorizontalScrollMenu.setAdapter(new MenuAdapter());

        return view;
    }




    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mHorizontalScrollMenu.steChecked(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * 返回viewPager的数据源
     * @return
     */
    public ArrayList<Fragment> getViewPagerData() {
        ArrayList<Fragment> listFragment = new ArrayList<Fragment>();
        listFragment.add(HeadlineNewsFragment.newInstance());
        listFragment.add(PlayFragment.newInstance());
        listFragment.add(HotTopicFragment.newInstance());
        return listFragment;
    }


    /*ViewPager的适配器*/
    public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> listFragment = new ArrayList<Fragment>();

        public ViewPagerFragmentAdapter(FragmentManager fm, ArrayList<Fragment> data) {
            super(fm);
            this.listFragment = data;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int i) {
            return listFragment.get(i);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }
    }


    /*横向滑动菜单的适配器*/
    public class MenuAdapter extends BaseAdapter{
        String[] listTitle = {"头条","娱乐","热点","体育","财经","科技","图片","跟帖"};

        @Override
        public List<String> getMenuItems() {
            return Arrays.asList(listTitle);
        }

        @Override
        public List<View> getContentViews() {
            List<View> views = new ArrayList<View>();
            for(String str:listTitle){
                View v = LayoutInflater.from(getActivity()).inflate(R.layout.horizontal_menu_item_layout,null);
                TextView tv = (TextView) v.findViewById(R.id.menu_text);
                tv.setText(str);
            }
             return views;
        }

        @Override
        public void onPageChanged(int position, boolean visitStatus) {
            Toast.makeText(getActivity(),
                    "内容页：" + (position + 1) + " 访问状态：" + visitStatus,
                    Toast.LENGTH_SHORT).show();
            mViewPager.setCurrentItem(position);
        }
    }

}
