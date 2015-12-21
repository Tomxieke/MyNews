package com.example.scxh.mynews.prcture_type_news.content_frament;


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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.scxh.mynews.R;
import com.example.scxh.mynews.prcture_type_news.pic_javabean.CommentBean;
import com.example.scxh.mynews.prcture_type_news.pic_javabean.ListContentBean;
import com.example.scxh.mynews.prcture_type_news.pic_javabean.PicContentBean;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFun extends Fragment implements AdapterView.OnItemClickListener{

    private static final String PARAM1 = "param1";
    private String mUrl;
    private ListView mListView;
    private ListViewAdapter adapter;
    private ProgressBar p;


    public static FragmentFun newInstance(String url) {
        
        Bundle args = new Bundle();
        args.putString(PARAM1,url);
        FragmentFun fragment = new FragmentFun();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mUrl = getArguments().getString(PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_one, container, false);
        p  = (ProgressBar) view.findViewById(R.id.picture_progressbar);
        mListView = (ListView) view.findViewById(R.id.picture_content_listview);
        adapter = new ListViewAdapter(inflater,getActivity());
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        getDataFromHhttp();
        return view;
    }


    public void getDataFromHhttp(){
        Log.e("tag","array-----getDataFromHhttp");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(mUrl, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject O1 = new JSONObject(s);
                    JSONObject data = O1.getJSONObject("data");
                    Log.e("tag","array-----"+data.toString());
                    Gson gson = new Gson();
                    ListContentBean listContentBean = gson.fromJson(data.toString(),ListContentBean.class);
                    ArrayList<PicContentBean> listPic = listContentBean.getList();
                    adapter.setData(listPic);
                    p.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PicContentBean picContentBean = (PicContentBean) parent.getAdapter().getItem(position);
        CommentBean comments = picContentBean.getComment_count_info();
        String totleComments = comments.getTotal(); //得到总评论数
        Intent intent = new Intent(getActivity(),PicNewsDetailsActivity.class);
        intent.putExtra("id",picContentBean.getId()); //把id传过去
        intent.putExtra("title",picContentBean.getTitle()); //把标题传过去
        intent.putExtra("comment",totleComments);  //把总评论数传过去
        startActivity(intent);
    }


    /**
     * listView的适配器
     */
    public class ListViewAdapter extends BaseAdapter{
        ArrayList<PicContentBean> list = new ArrayList<>();
        private LayoutInflater inflater;
        private Context context;
        public ListViewAdapter(LayoutInflater mInflater, Context mcontext){
            inflater = mInflater;
            context = mcontext;
        }

        public void setData(ArrayList<PicContentBean> mlist){
            list = mlist;
            notifyDataSetChanged();
        }

        public void addData(ArrayList<PicContentBean> mlist){
            list.addAll(mlist);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TagHandle handle = null;
            if (convertView == null){
                convertView = inflater.inflate(R.layout.picture_xlistview_item_layout, null);
                handle = new TagHandle();
                handle.img = (ImageView) convertView.findViewById(R.id.picture_listItem_img);
                handle.txt = (TextView) convertView.findViewById(R.id.picture_listItem_titleTxt);
                convertView.setTag(handle);
            }

            handle = (TagHandle) convertView.getTag();
            PicContentBean picContentBean = (PicContentBean) getItem(position);
            handle.txt.setText(picContentBean.getTitle());
            Picasso.with(context).load(picContentBean.getPic()).into(handle.img);
            return convertView;
        }


        class TagHandle{
            ImageView img;
            TextView txt;
        }
    }

}
