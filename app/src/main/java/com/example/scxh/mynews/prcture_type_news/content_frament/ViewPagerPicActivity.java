package com.example.scxh.mynews.prcture_type_news.content_frament;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.scxh.mynews.R;
import com.example.scxh.mynews.prcture_type_news.pic_javabean.DetailsList;
import com.example.scxh.mynews.prcture_type_news.pic_javabean.DetailsPics;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

//还没用的Activity，想用ViewPager加Activity来实现浏览图片

public class ViewPagerPicActivity extends Activity {

    private String urlId ;
    private String headUrl = "http://api.sina.cn/sinago/article.json?postt=hdpic_hdpic_toutiao_4&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=12050_0001&imei=867064013906290&uid=802909da86d9f5fc&id=";
    private ViewPager mViewPager;
    private String title;
    private TestPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_pic);
        if(getIntent() != null) {
            urlId = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
        }
        mViewPager = (ViewPager) findViewById(R.id.test_viewPager);
        adapter = new TestPagerAdapter();
        mViewPager.setAdapter(adapter);
        getImgFromHttp();
    }



    public void getImgFromHttp(){
        String url = headUrl + urlId;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object  = new JSONObject(s);
                    JSONObject data = object.getJSONObject("data");
                    Gson gson = new Gson();
                    DetailsList detailsList = gson.fromJson(data.toString(),DetailsList.class);
                    ArrayList<DetailsPics> list = detailsList.getPics();
                //    adapter.setData(list);
                    ArrayList<View> views = new ArrayList<View>();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 适配器
     */
    public class TestPagerAdapter extends PagerAdapter{
        ArrayList<View> listData = new ArrayList<>();

        public void setListData(ArrayList<View> views){
            this.listData = views;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = listData.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = listData.get(position);
            container.removeView(view);
        }
    }
}
