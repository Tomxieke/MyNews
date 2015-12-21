package com.example.scxh.mynews.prcture_type_news.content_frament;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scxh.mynews.R;
import com.example.scxh.mynews.prcture_type_news.pic_javabean.DetailsList;
import com.example.scxh.mynews.prcture_type_news.pic_javabean.DetailsPics;
import com.example.scxh.mynews.title_view.NavigationView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PicNewsDetailsActivity extends FragmentActivity {
    private String urlId ;
    private String headUrl = "http://api.sina.cn/sinago/article.json?postt=hdpic_hdpic_toutiao_4&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=12050_0001&imei=867064013906290&uid=802909da86d9f5fc&id=";
    private ViewPager mViewPager;
    private PicDetailsPagerAdapter adapter;
    private String title;
    private NavigationView ngView; //导航View
    private String totalComment;  //总评论数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_news_details);
        if(getIntent() != null) {
            urlId = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            totalComment = getIntent().getStringExtra("comment");
        }


        TextView comment = (TextView) findViewById(R.id.navigation_comment_txt);
        comment.setText(totalComment+"条评论");  //设置总评论数
        ngView = (NavigationView) findViewById(R.id.pc_navigationView);
        ngView.registOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.navigation_back_imgbtn:
                        finish();
                        break;
                    case R.id.navigation_comment_txt:
                        Toast.makeText(PicNewsDetailsActivity.this,"评论列表正在开发",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_more_imgbtn:
                        Toast.makeText(PicNewsDetailsActivity.this,"分享功能正在开发",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        mViewPager = (ViewPager) findViewById(R.id.details_viewpager);
        adapter = new PicDetailsPagerAdapter(getSupportFragmentManager());
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
                    JSONObject  object  = new JSONObject(s);
                    JSONObject data = object.getJSONObject("data");
                    Gson gson = new Gson();
                    DetailsList detailsList = gson.fromJson(data.toString(),DetailsList.class);
                    ArrayList<DetailsPics> list = detailsList.getPics();
                    adapter.setData(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public class PicDetailsPagerAdapter extends FragmentStatePagerAdapter{
        ArrayList<DetailsPics> list = new ArrayList<>();

        public void setData(ArrayList<DetailsPics> ml){
            this.list = ml;
            notifyDataSetChanged();
        }

        public PicDetailsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return PicDetailsFragment.newInstance(list.get(position),position,getCount(),title);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
