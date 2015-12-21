package com.example.scxh.mynews.content_fragment.news_type_fragment.entertainment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scxh.mynews.R;
import com.example.scxh.mynews.content_fragment.news_type_fragment.headlinenews.HeadImgDataBean;
import com.example.scxh.mynews.content_fragment.news_type_fragment.headlinenews.ThreeprictureUrlBean;
import com.example.scxh.mynews.xlistview.XListView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayFragment extends Fragment implements AdapterView.OnItemClickListener,XListView.IXListViewListener{
    private int pageNo = 0;
    private int pageSize = 20;
    private String news_type_id = "T1348648517839";
    private LayoutInflater mInflater;
    private View mHeadView;
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
        mInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        mXlistView = (XListView) view.findViewById(R.id.play_xlistView);
        adapter = new XlistviewPlayAdapter(getActivity());
        mXlistView.setAdapter(adapter);
        getHttpData(mInflater);

        mHeadView  = inflater.inflate(R.layout.slider_img_layout, null); //这里先实例化头部View
        mXlistView.addHeaderView(mHeadView);  //并将其添加到ListView的头部。之后只需要向头里面放控件即可
        mXlistView.setXListViewListener(this);
        mXlistView.setOnItemClickListener(this);
        return view;
    }


    public void getHttpData(final LayoutInflater inflater){
        String url ="http://c.m.163.com/nc/article/list/"+news_type_id
                +"/"+pageNo*pageSize+ "-" +pageSize+ ".html";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Gson gson = new Gson();
                ListPlayBean lists = gson.fromJson(s, ListPlayBean.class);
                ArrayList<ContentBean> newLists = lists.getT1348648517839();
                if (pageNo == 0){
                    adapter.refreshData(newLists);
                }else {
                    adapter.addData(newLists);
                }

                mXlistView.setPullLoadEnable(true);  //加载更多开启
                mXlistView.stopRefresh();
                mXlistView.stopLoadMore();

                try {
                    JSONObject object = new JSONObject(s);
                    JSONArray array = object.getJSONArray(news_type_id);
                    JSONObject info = array.getJSONObject(0);
                    Log.d("test","org-json++++++  info:" +info);
                    JSONArray arrayInfo = info.getJSONArray("ads");
                    Log.d("test","ads---------" + arrayInfo.toString());

                    ArrayList<HeadImgDataBean> headData = new ArrayList<HeadImgDataBean>();
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

                    setXlistViewHead(headData);  //设置头部数据

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    /**
     * 设置xlistview 的头部。
     *
     */
    public void setXlistViewHead(ArrayList<HeadImgDataBean> headList){
        SliderLayout mSliderLayout = (SliderLayout) mHeadView.findViewById(R.id.image_slider_layout);
        mSliderLayout.removeAllSliders();  //加载前让他先移除所有内容，便于第二次加载刷新数据
        int length = headList.size();
        for(int i = 0; i < length; i++){
            TextSliderView sliderView = new TextSliderView(getActivity());   //向SliderLayout中添加控件
            sliderView.image(headList.get(i).getImgsrc());
            sliderView.description(headList.get(i).getTitle());
            sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Toast.makeText(getActivity(), "中国加剧芯片产业并购潮 砸钱模式难以理解", Toast.LENGTH_SHORT).show();
                }
            });

            mSliderLayout.addSlider(sliderView);
        }
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);  //将小圆点设置到右下方
    }

    @Override
    public void onRefresh() {
        pageNo = 0;
        getHttpData(mInflater);
    }

    @Override
    public void onLoadMore() {
        ++pageNo;
        getHttpData(mInflater);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(),"oooooooo",Toast.LENGTH_SHORT).show();
        ContentBean contentBean = (ContentBean)(parent.getAdapter().getItem(position)); //将JAVBean参数全部传过去。
        Intent intent = new Intent(getActivity(),PlayItemActivity.class);
        intent.putExtra("prarm",contentBean);
        startActivity(intent);
    }


    /*XlistView的适配器*/
    public class XlistviewPlayAdapter extends BaseAdapter {
        ArrayList<ContentBean> data = new ArrayList<ContentBean>() ;
        private LayoutInflater inflater;
        private Context context;
        private final int VIEW_TYPE_COUNT = 2;  //子布局个数
        private final int TYPE_1 = 0;   //子布局 1
        private final int TYPE_2 = 1;   //
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
        public int getViewTypeCount() {
            return VIEW_TYPE_COUNT;   //子布局个数
        }

        @Override
        public int getItemViewType(int position) {
            if (data.get(position).getImgextra() == null){   //以数据源中的某个标签来判断加载那个子布局样式
                return TYPE_1;
            }else{
                return TYPE_2;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = null;
            Handler mHandler = null;
            Type2_Handler type2_handler = null;
            int view_type = getItemViewType(position); //得到对应位置子布局类型

            if(convertView == null){   //逻辑判断
                switch (view_type){
                    case TYPE_1:
                        mHandler = new Handler();
                        v = inflater.inflate(R.layout.xlistview_item_layout, null);
                        mHandler.iconImg = (ImageView) v.findViewById(R.id.xlistview_item_img);
                        mHandler.titleTxt = (TextView) v.findViewById(R.id.xlistview_item_title_txt);
                        mHandler.contentTxt = (TextView) v.findViewById(R.id.xlistview_content_text);
                        v.setTag(mHandler);
                        break;
                    case TYPE_2:
                        type2_handler = new Type2_Handler();
                        v = inflater.inflate(R.layout.xlistview_item_typetwo_layout,null);
                        type2_handler.title = (TextView) v.findViewById(R.id.xlistview2_item_title_txt);
                        type2_handler.oneImg = (ImageView) v.findViewById(R.id.img_one);
                        type2_handler.twoImg = (ImageView) v.findViewById(R.id.img_two);
                        type2_handler.threeImg = (ImageView) v.findViewById(R.id.img_three);
                        v.setTag(type2_handler);
                        break;
                }
            }else{
                switch (view_type){
                    case TYPE_1:
                        v = convertView;
                        mHandler = (Handler) v.getTag();
                        break;
                    case TYPE_2:
                        v = convertView;
                        type2_handler = (Type2_Handler) v.getTag();
                        break;
                }
            }

            ContentBean news = (ContentBean) getItem(position); //JAVABean对象
            //设置数据
            switch (view_type){
                case TYPE_1:
                    mHandler.titleTxt.setText(news.getTitle());
                    mHandler.contentTxt.setText(news.getDigest());
                    //mHandler.iconImg
                    Picasso.with(context).load(news.getImgsrc()).into(mHandler.iconImg);
                    break;
                case TYPE_2:
                    type2_handler.title.setText(news.getTitle());
                    ArrayList<ThreeprictureUrlBean> list = news.getImgextra();
                    Picasso.with(context).load(((ThreeprictureUrlBean)list.get(0)).getImgsrc())
                            .into(type2_handler.oneImg);
                    Picasso.with(context).load(((ThreeprictureUrlBean)list.get(1)).getImgsrc())
                            .into(type2_handler.twoImg);
                    Picasso.with(context).load(news.getImgsrc()).into(type2_handler.threeImg);
                    break;
            }
            return v;
        }

        //保存控件，有几个子布局就有多少个这样的类
        class Handler{
            ImageView iconImg;
            TextView titleTxt;
            TextView contentTxt;
        }

        class Type2_Handler{
            TextView title;
            ImageView oneImg,twoImg,threeImg;
        }

    }

}
