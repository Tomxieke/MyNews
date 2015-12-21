package com.example.scxh.mynews.content_fragment.news_type_fragment.headlinenews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scxh.mynews.R;
import com.example.scxh.mynews.title_view.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListViewItemActivity extends Activity {
    private TextView mTitleTxt,mFromTxt,mContentTxt,mImgConuntTxt;
    private ImageView  mImg;
    private NavigationView ngView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_item);
        Intent intent = getIntent();
        HeadlineNewsBean headlineNewsBean = null;   //新闻相关内容
        if (intent != null){
            headlineNewsBean = (HeadlineNewsBean) intent.getSerializableExtra("prarm");
        }

        initView(headlineNewsBean);

    }


    public void initView( HeadlineNewsBean headlineNewsBean){
        mImgConuntTxt = (TextView) findViewById(R.id.imgcount_text);
        TextView comment = (TextView) findViewById(R.id.navigation_comment_txt);
        comment.setText(headlineNewsBean.getReplyCount()+"条评论");
        ngView = (NavigationView) findViewById(R.id.navigation_view);
        ngView.registOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.navigation_back_imgbtn:
                        finish();
                        break;
                    case R.id.navigation_comment_txt:
                        Toast.makeText(ListViewItemActivity.this,"评论列表正在开发",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_more_imgbtn:
                        Toast.makeText(ListViewItemActivity.this,"分享功能正在开发",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        mTitleTxt = (TextView) findViewById(R.id.title_txt);
        mTitleTxt.setText(headlineNewsBean.getTitle());

        mFromTxt = (TextView) findViewById(R.id.from_text);
        mFromTxt.setText(headlineNewsBean.getSource() +"    "+headlineNewsBean.getPtime());

        mImg = (ImageView) findViewById(R.id.news_img);
        Picasso.with(this).load(headlineNewsBean.getImgsrc()).into(mImg, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Toast.makeText(ListViewItemActivity.this,"网络不给力哟",Toast.LENGTH_SHORT).show();
            }
        });

        mContentTxt = (TextView) findViewById(R.id.content_txt);
       getContent(headlineNewsBean.getDocid() ,mContentTxt);

    }


    /**
     *
     * @param urlStr  接口参数
     * @param txt  显示内容的TextView
     */
    public void getContent(final String urlStr, final TextView txt) {
        String url = "http://c.m.163.com/nc/article/" + urlStr + "/full.html";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
              //  Log.e("test", s);

                try {
                    JSONObject object = new JSONObject(s);
                    JSONObject object2 = object.getJSONObject(urlStr);
                    String content = object2.getString("body");
                    txt.setText(Html.fromHtml(content)); //去掉HTML标签
                    JSONArray imgSrcs = object2.getJSONArray("img");
                    Log.d("tag", "onSuccess: ======="+imgSrcs.toString());
                    if(! imgSrcs.toString().equals("[]")) { //有图片数据的时候才启动浏览
                        ArrayList<ContentImgBean> imgs = new ArrayList<ContentImgBean>();
                        for (int a = 0; a < imgSrcs.length(); a++) {
                            JSONObject o = imgSrcs.getJSONObject(a);
                            ContentImgBean bean = new ContentImgBean();
                            bean.setSrc(o.getString("src"));
                            bean.setAlt(o.getString("alt"));
                            bean.setRef(o.getString("ref"));
                            imgs.add(bean);
                        }

                        mImgConuntTxt.setText("共" + imgs.size() + "张");
                        scanImgDetail(mImg, imgs);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    //有多张图片介绍时。点击图片启动浏览图片的Activity
    public void scanImgDetail(ImageView imgview, final ArrayList<ContentImgBean> listSrc){
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListViewItemActivity.this,ImgDetailActivity.class);
                intent.putParcelableArrayListExtra("listBean", listSrc);
                Bundle b = new Bundle();

                startActivity(intent);
            }
        });
    }

}
