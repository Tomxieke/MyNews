package com.example.scxh.mynews.content_fragment.news_type_fragment.entertainment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scxh.mynews.R;
import com.example.scxh.mynews.xlistview.XListView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayFragment extends Fragment {


    private XListView mXlistView;
    private XlistviewPlayAdapter adapter;


    public static PlayFragment newInstance() {

        Bundle args = new Bundle();

        PlayFragment fragment = new PlayFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        mXlistView = (XListView) view.findViewById(R.id.play_xlistView);
        adapter = new XlistviewPlayAdapter(getActivity());
        mXlistView.setAdapter(adapter);
        getHttpData();
        return view;
    }


    public void getHttpData(){
        String url = "http://c.m.163.com/nc/article/list/T1348648517839/0-20.html";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
             //   Log.d("test", "onSuccess: " + s);
                Gson gson = new Gson();
                ListPlayBean lists = gson.fromJson(s, ListPlayBean.class);
            //    Log.d("test", "onSuccess: " + lists);
                ArrayList<ContentBean> newLists = lists.getT1348648517839();
             //   Log.d("test", "onSuccess: " + newLists);
                for (ContentBean b : newLists) {
             //       Log.e("test", b.getTitle());
                    adapter.refreshData(newLists);
                }
            }
        });
    }


    /*XlistView的适配器*/
    public class XlistviewPlayAdapter extends BaseAdapter {
        ArrayList<ContentBean> data = new ArrayList<ContentBean>() ;
        private LayoutInflater inflater;
        private Context context;
        public XlistviewPlayAdapter(Context context){
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        public void refreshData(ArrayList<ContentBean> list){
            this.data = list;  //这里变成添加数据
            notifyDataSetChanged();
        }

        public void addData(ArrayList<ContentBean> list){
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

            ContentBean news = (ContentBean) getItem(position);
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
