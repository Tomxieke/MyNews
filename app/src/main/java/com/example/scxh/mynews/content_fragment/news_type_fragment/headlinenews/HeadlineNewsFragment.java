package com.example.scxh.mynews.content_fragment.news_type_fragment.headlinenews;

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
import com.example.scxh.mynews.uitl.HttpUtil;
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
public class HeadlineNewsFragment extends Fragment implements AdapterView.OnItemClickListener,XListView.IXListViewListener{
    private XListView mXlistView;
    private XlistviewAdapter adapter;
    private int pageNo = 0;
    private int pageSize = 20;
    private String news_type_id = "T1348647909107/";
    private View mHeadView;


    public static HeadlineNewsFragment newInstance() {
        
        Bundle args = new Bundle();
        Log.d("test","HeadlineNewsFragment newInstance()");
        HeadlineNewsFragment fragment = new HeadlineNewsFragment();
        fragment.setArguments(args);
        Log.d("test","HeadlineNewsFragment newInstance()");
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("test","onCreateView+++++++++++++++++++++++++=");
        View view = inflater.inflate(R.layout.fragment_headline_news, container, false);
        mXlistView = (XListView) view.findViewById(R.id.headlineNews_xlistView);
        adapter = new XlistviewAdapter(getActivity());
        mXlistView.setAdapter(adapter);
        mHeadView = inflater.inflate(R.layout.slider_img_layout, null);
        mXlistView.addHeaderView(mHeadView);
        getHttpData();
        mXlistView.setOnItemClickListener(this);
        mXlistView.setXListViewListener(this);
        return view;
    }


    /**
     * 联网获取xlistView Item的数据
     */
    public void getHttpData(){
        String url = "http://c.m.163.com/nc/article/headline/" + news_type_id +pageNo*pageSize+ "-"  +pageSize+ ".html";
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
                if (pageNo == 0) {
                    adapter.refreshData(newLists);
                } else {
                    adapter.addData(newLists);  //当不为第一页时添加
                }
                mXlistView.setPullLoadEnable(true);  //加载更多开启
                mXlistView.stopRefresh();
                mXlistView.stopLoadMore();

                HttpUtil.setTime(mXlistView);  //设置更新时间

                    try {
                        JSONObject object = new JSONObject(s);
                        JSONArray array = object.getJSONArray("T1348647909107");
                        JSONObject info = array.getJSONObject(0);
                        Log.d("test", "org-json++++++  info:" + info);
                        JSONArray arrayInfo = info.getJSONArray("ads");
                        Log.d("test", "ads---------" + arrayInfo.toString());

                        ArrayList<HeadImgDataBean> headData = new ArrayList<HeadImgDataBean>();
                        if (headData != null) {
                            headData.clear();
                        }
                        for (int j = 0; j < arrayInfo.length(); j++) {
                            JSONObject o = arrayInfo.getJSONObject(j);
                            HeadImgDataBean bean = new HeadImgDataBean();
                            bean.setImgsrc(o.getString("imgsrc"));
                            bean.setTitle(o.getString("title"));
                            Log.d("test", "ooooooooo" + bean.getTitle());
                            headData.add(bean);
                        }

                        getXlistViewHead(headData);


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
    public void getXlistViewHead(ArrayList<HeadImgDataBean> list){
        SliderLayout mSliderLayout = (SliderLayout) mHeadView.findViewById(R.id.image_slider_layout);
        int length = list.size();
        mSliderLayout.removeAllSliders();  //移除
        for(int i = 0; i < length; i++){
            TextSliderView sliderView = new TextSliderView(getActivity());   //向SliderLayout中添加控件
            sliderView.image(list.get(i).getImgsrc());
            sliderView.description(list.get(i).getTitle());
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
    /*listView每一项的点击事件*/
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(),"oooooooo",Toast.LENGTH_SHORT).show();
        HeadlineNewsBean headlineNewsBean = (HeadlineNewsBean)(parent.getAdapter().getItem(position)); //将JAVBean参数全部传过去。
        Intent intent = new Intent(getActivity(),ListViewItemActivity.class);
        intent.putExtra("prarm",headlineNewsBean);
        startActivity(intent);
    }




    /*XlistView的适配器*/
    public class XlistviewAdapter extends BaseAdapter {
        ArrayList<HeadlineNewsBean> data = new ArrayList<HeadlineNewsBean>() ;
        private LayoutInflater inflater;
        private Context context;
        private final int VIEW_TYPE_COUNT = 2;
        private final int TYPE_1 = 0;
        private final int TYPE_2 = 1;
        public XlistviewAdapter(Context context){
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        public void refreshData(ArrayList<HeadlineNewsBean> list){
            this.data = list;
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
        public int getViewTypeCount() {
            return VIEW_TYPE_COUNT;
        }

        @Override
        public int getItemViewType(int position) {
            if (data.get(position).getImgextra() == null){  //多张图片的Imgextra是不为空的。
                return TYPE_1;
            }else{
                return TYPE_2;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.e("test","onCreateView++++++++++++++++getView+++++++++=" +position);
            View v = null;
            Type1_Handler mHandler = null;
            Type2_Handler type2_handler = null;
            int view_type = getItemViewType(position);
            if(convertView == null){
                switch (view_type){
                    case TYPE_1:
                        mHandler = new Type1_Handler();
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
                        mHandler = (Type1_Handler) v.getTag();
                        break;
                    case TYPE_2:
                        v = convertView;
                        type2_handler = (Type2_Handler) v.getTag();
                        break;
                }
            }


            HeadlineNewsBean news = (HeadlineNewsBean) getItem(position);
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

        //保存数据资源
        class Type1_Handler {
            ImageView iconImg;
            TextView titleTxt;
            TextView contentTxt;
        }

        class Type2_Handler{
            TextView title;
            ImageView oneImg,twoImg,threeImg;
        }



    }


    @Override
    //上拉刷新
    public void onRefresh() {

        pageNo = 0;
        getHttpData();

    }

    @Override
    //下拉加载更多
    public void onLoadMore() {
        ++pageNo;
        Log.d("test","----------"+pageNo);
        getHttpData();
    }

}
