package com.example.scxh.mynews.content_fragment.news_type_fragment.headlinenews;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scxh.mynews.R;
import com.example.scxh.mynews.xlistview.XListView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeadlineNewsFragment extends Fragment {
    private XListView mXlistView;
    private XlistviewAdapter adapter;


    public static HeadlineNewsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        HeadlineNewsFragment fragment = new HeadlineNewsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_headline_news, container, false);
        mXlistView = (XListView) view.findViewById(R.id.headlineNews_xlistView);
        adapter = new XlistviewAdapter(getActivity());
        mXlistView.setAdapter(adapter);
        getHttpData(inflater);
        return view;
    }


    ArrayList<HeadImgDataBean> headData;  //头部图片轮播数据

    /**
     * 联网获取xlistView Item的数据
     */
    public void getHttpData(final LayoutInflater inflater){
        String url = "http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Gson gson = new Gson();
                ListHeadlineBean lists = gson.fromJson(s, ListHeadlineBean.class);
                ArrayList<HeadlineNewsBean> newLists = lists.getT1348647909107();
                for (HeadlineNewsBean b : newLists) {
                    Log.e("test", b.getTitle());
                    adapter.refreshData(newLists);
                }


                try {
                    JSONObject object = new JSONObject(s);
                    JSONArray array = object.getJSONArray("T1348647909107");
                    JSONObject info = array.getJSONObject(0);
                    Log.d("test","org-json++++++  info:" +info);
                    JSONArray arrayInfo = info.getJSONArray("ads");
                    Log.d("test","ads---------" + arrayInfo.toString());

                    headData = new ArrayList<HeadImgDataBean>();
                    if (headData != null){
                        headData.clear();
                    }
                    for (int j = 0;j < arrayInfo.length();j++){
                        JSONObject o = arrayInfo.getJSONObject(j);
                        HeadImgDataBean bean = new HeadImgDataBean();
                        bean.setImgsrc(o.getString("imgsrc"));
                        bean.setTitle(o.getString("title"));
                        Log.d("test","ooooooooo" + bean.getTitle());
                        headData.add(bean);
                    }
                    mXlistView.addHeaderView(getXlistViewHead(inflater));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    /**
     * 返回xlistview 的头部。
     * @return
     */
    public View getXlistViewHead(LayoutInflater inflater){
        View  v = inflater.inflate(R.layout.slider_img_layout, null);
        SliderLayout mSliderLayout = (SliderLayout) v.findViewById(R.id.image_slider_layout);
        int length = headData.size();
        for(int i = 0; i < length; i++){
            TextSliderView sliderView = new TextSliderView(getActivity());   //向SliderLayout中添加控件
            sliderView.image(headData.get(i).getImgsrc());
            sliderView.description(headData.get(i).getTitle());
            sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Toast.makeText(getActivity(), "中国加剧芯片产业并购潮 砸钱模式难以理解", Toast.LENGTH_SHORT).show();
                }
            });

            mSliderLayout.addSlider(sliderView);
        }
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);  //将小圆点设置到右下方
        return v;
    }


    /*XlistView的适配器*/
    public class XlistviewAdapter extends BaseAdapter {
        ArrayList<HeadlineNewsBean> data = new ArrayList<HeadlineNewsBean>() ;
        private LayoutInflater inflater;
        private Context context;
        public XlistviewAdapter(Context context){
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        public void refreshData(ArrayList<HeadlineNewsBean> list){
            this.data = list;  //这里变成添加数据
            notifyDataSetChanged();
        }

        public void addData(ArrayList<HeadlineNewsBean> list){
            this.data.addAll(list);  //这里变成添加数据
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = null;
            Handler mHandler;
            if(convertView == null){
                mHandler = new Handler();
                v = inflater.inflate(R.layout.xlistview_item_layout, null);
                mHandler.iconImg = (ImageView) v.findViewById(R.id.xlistview_item_img);
                mHandler.titleTxt = (TextView) v.findViewById(R.id.xlistview_item_title_txt);
                mHandler.contentTxt = (TextView) v.findViewById(R.id.xlistview_content_text);
                v.setTag(mHandler);
            }else{
                v = convertView;
                mHandler = (Handler) v.getTag();
            }

            HeadlineNewsBean news = (HeadlineNewsBean) getItem(position);
            mHandler.titleTxt.setText(news.getTitle());
            mHandler.contentTxt.setText(news.getDigest());
            //mHandler.iconImg
            Picasso.with(context).load(news.getImgsrc()).into(mHandler.iconImg, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
            return v;
        }

        class Handler{
            ImageView iconImg;
            TextView titleTxt;
            TextView contentTxt;
        }

    }

}
