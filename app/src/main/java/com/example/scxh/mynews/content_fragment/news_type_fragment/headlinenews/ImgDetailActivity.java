package com.example.scxh.mynews.content_fragment.news_type_fragment.headlinenews;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scxh.mynews.R;
import com.example.scxh.mynews.title_view.NavigationView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImgDetailActivity extends Activity {
    private ViewPager mViewPager;
    private NavigationView ngView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_detail);
        ArrayList<ContentImgBean> lists = new ArrayList<>();
        if (getIntent() != null){
            lists = getIntent().getParcelableArrayListExtra("listBean");
        }

        for (ContentImgBean c:lists){
            Log.e("tag","----------"+c.getSrc());
            Log.e("tag","----------"+c.getAlt());
        }

        ngView = (NavigationView) findViewById(R.id.news_content_navigationView);
        ngView.registOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.navigation_back_imgbtn:
                        finish();
                        break;
                    case R.id.navigation_comment_txt:
                        Toast.makeText(ImgDetailActivity.this,"评论列表正在开发",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_more_imgbtn:
                        Toast.makeText(ImgDetailActivity.this,"分享功能正在开发",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });



        mViewPager = (ViewPager) findViewById(R.id.dateil_img_viewPager);
        ImgViewPagerAdapter adapter = new ImgViewPagerAdapter();
        mViewPager.setAdapter(adapter);
        LayoutInflater inflater = LayoutInflater.from(this);
        adapter.setViews(getPagerViews(inflater,lists));


    }


    /**
     * 返回View
     * @param inflater
     * @param lists  数据源
     * @return
     */
    public ArrayList<View> getPagerViews(LayoutInflater inflater,ArrayList<ContentImgBean> lists){
        ArrayList<View> views = new ArrayList<>();
        ContentImgBean bean;
        int count = lists.size();
        for (int a = 0;a<count;a++){
            bean = lists.get(a);
            View view = inflater.inflate(R.layout.news_content_img_layout,null);
            final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.news_content_progressbar);
            ImageView srcImg = (ImageView) view.findViewById(R.id.news_content_imgsrc_img);
            Picasso.with(this).load(bean.getSrc()).into(srcImg, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {

                }
            });
            TextView altText = (TextView) view.findViewById(R.id.news_content_alt_text);
            altText.setText(bean.getAlt());
            TextView countText = (TextView) view.findViewById(R.id.news_content_count_text);
            countText.setText((a+1) + "/" + count);

            views.add(view);
            bean = null;
        }

        return views;
    }

    /**
     * viewpager的适配器
     */
    public class ImgViewPagerAdapter extends PagerAdapter{
        ArrayList<View> views = new ArrayList<>();
        public void setViews(ArrayList<View> listViews){
            this.views = listViews;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = views.get(position);
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = views.get(position);
            container.removeView(v);
        }
    }
}
