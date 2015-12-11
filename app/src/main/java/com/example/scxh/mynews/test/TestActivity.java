package com.example.scxh.mynews.test;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scxh.mynews.R;
import com.lynnchurch.horizontalscrollmenu.BaseAdapter;
import com.lynnchurch.horizontalscrollmenu.HorizontalScrollMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestActivity extends ActionBarActivity {
    private HorizontalScrollMenu mHorizontalScrollMenu;  //横向滑动菜单
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mHorizontalScrollMenu = (HorizontalScrollMenu) findViewById(R.id.testHorizontalMenu);
        mHorizontalScrollMenu.setSwiped(false);
        mHorizontalScrollMenu.setAdapter(new MenuAdapter());

    }

    /*横向滑动菜单的适配器*/
    public class MenuAdapter extends BaseAdapter {
        String[] listTitle = {"头条","娱乐","热点","体育","财经","科技","图片","跟帖"};

        @Override
        public List<String> getMenuItems() {
            return Arrays.asList(listTitle);
        }

        @Override
        public List<View> getContentViews() {
            List<View> views = new ArrayList<View>();
            for(String str:listTitle){
                View v = LayoutInflater.from(TestActivity.this).inflate(R.layout.horizontal_menu_item_layout,null);
                TextView tv = (TextView) v.findViewById(R.id.menu_text);
                tv.setText(str);
            }
            return views;
        }

        @Override
        public void onPageChanged(int position, boolean visitStatus) {
            Toast.makeText(TestActivity.this,
                    "内容页：" + (position + 1) + " 访问状态：" + visitStatus,
                    Toast.LENGTH_SHORT).show();

        }
    }
}
