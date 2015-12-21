package com.example.scxh.mynews.radio_type_news.vidao_content;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scxh.mynews.R;
import com.example.scxh.mynews.radio_type_news.vidao_javabean.VidaoContentBean;
import com.example.scxh.mynews.uitl.HttpUtil;
import com.example.scxh.mynews.uitl.StrDataInter;
import com.example.scxh.mynews.xlistview.XListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class VidaoContentFragment extends Fragment implements XListView.IXListViewListener{
    private int pageNo = 0;
    private int pageSize = 10;
    private static final String TYPE_PARAM = "TYPE";
    private String vidaoType ;  //新闻类型
    private XListView mXlistView;
    private VidaoListViewAdapter adapter;



    public static VidaoContentFragment newInstance(String type) {
        
        Bundle args = new Bundle();
        args.putString(TYPE_PARAM,type);
        VidaoContentFragment fragment = new VidaoContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vidaoType = getArguments() != null ? getArguments().getString(TYPE_PARAM):"";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vidao_content, container, false);
        mXlistView = (XListView) view.findViewById(R.id.vidao_xlistview);
        adapter = new VidaoListViewAdapter(getActivity());
        mXlistView.setAdapter(adapter);
        mXlistView.setXListViewListener(this);
        getDataFromInternet(pageNo);
        return view;
    }


    /**
     * 连接网络获取数据
     */
    public void getDataFromInternet(int page){
        String url = "http://c.3g.163.com/nc/video/list/" +
                vidaoType + "/n/" +page*pageSize+ "-"  +pageSize+ ".html";
        Log.e("tag","url--------"+url+"----page--"+page);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    JSONObject object = new JSONObject(s);

                    JSONArray array = object.getJSONArray(vidaoType);
                    ArrayList<VidaoContentBean> list1 = new ArrayList<VidaoContentBean>();
                    for (int a = 0;a < array.length();a++){
                        JSONObject o = array.getJSONObject(a);

                        VidaoContentBean bean = new VidaoContentBean();
                        bean.setTitle(o.getString("title"));
                        bean.setCover(o.getString("cover"));
                        if (o.has("description")){
                            bean.setDescription(o.getString("description"));
                        }else {
                            bean.setDescription("没有描述");
                        }
                        bean.setMp4_url(o.getString("mp4_url"));
                        if(o.has("videosource")) {
                            bean.setVideosource(o.getString("videosource"));
                        }else {
                            bean.setVideosource("未知来源");  //没有这个字段就设置为空
                        }
                        if (o.has("sectiontitle")){
                            bean.setSectiontitle(o.getString("sectiontitle"));
                        }else{
                            bean.setSectiontitle("");
                        }

                        list1.add(bean);
                    }
                    if (pageNo == 0){
                        adapter.setListData(list1);
                    }else{
                        adapter.addListData(list1);
                    }

                    mXlistView.setPullLoadEnable(true);  //加载更多开启
                    mXlistView.stopRefresh();
                    mXlistView.stopLoadMore();

                    HttpUtil.setTime(mXlistView);  //设置更新时间

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }

    @Override
    public void onRefresh() {
        pageNo = 0;
        getDataFromInternet(pageNo);
    }

    @Override
    public void onLoadMore() {
        ++pageNo;
        getDataFromInternet(pageNo);

    }

    /**
     * XlistView的适配器
     */
    public class VidaoListViewAdapter extends BaseAdapter{
        ArrayList<VidaoContentBean> videos = new ArrayList<VidaoContentBean>();
        private LayoutInflater inflater;
        private Context context;

        public VidaoListViewAdapter(Context mcontent){
            this.context = mcontent;
            inflater  = LayoutInflater.from(mcontent);
        }

        public void setListData(ArrayList<VidaoContentBean> list){
            this.videos = list;
            this.notifyDataSetChanged();
        }

        public void addListData(ArrayList<VidaoContentBean> nList){
            this.videos.addAll(nList);
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return videos.size();
        }

        @Override
        public Object getItem(int position) {
            return videos.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Handler handler;
            if (null == convertView){
                handler = new Handler();
                convertView = inflater.inflate(R.layout.video_xlistview_item_layout,null);
                handler.img = (ImageView) convertView.findViewById(R.id.vd_xlistview_item_img);
                handler.playImg = (ImageView) convertView.findViewById(R.id.vd_play_img);
                handler.title = (TextView) convertView.findViewById(R.id.vd_titel_txt);
                convertView.setTag(handler);
            }

            handler = (Handler) convertView.getTag();
            final VidaoContentBean bean = (VidaoContentBean) getItem(position);
            Picasso.with(context).load(bean.getCover()).into(handler.img);
            handler.title.setText(bean.getTitle());
            handler.playImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),VidaoPlayActivity.class);
                    intent.putExtra(StrDataInter.VIDEO_URL,bean.getMp4_url());
                    intent.putExtra(StrDataInter.VIDEO_TITLE,bean.getTitle());
                    startActivity(intent);
                }
            });

            return convertView;
        }


        class Handler{
            ImageView img;  //默认覆盖在上面的
            ImageView playImg; //播放按钮图片
            TextView title;  //标题
        }
    }

}
