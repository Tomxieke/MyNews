package com.example.scxh.mynews.prcture_type_news.content_frament;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scxh.mynews.R;
import com.example.scxh.mynews.prcture_type_news.pic_javabean.DetailsPics;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class PicDetailsFragment extends Fragment {
    private static final String PARAM = "param";  //对象参数
    private static final String CURRENT = "CURRENT"; //当前页
    private static final String TOTLE_COUNT ="totleCount"; //总共多上张
    private static final String TITLE = "title"; //标题
    private DetailsPics detailsPics;
    private int current;
    private int totlecount;
    private String title;
    private ImageView mImg;  //图片
    private TextView mTitleTxt,mCountTxt,mContent; //标题.图片总是，内容介绍


    public static PicDetailsFragment newInstance(DetailsPics d,int current,int totleCount,String title) {
        
        Bundle args = new Bundle();
        args.putSerializable(PARAM,d);
        args.putString(TITLE,title);
        args.putInt(CURRENT,current);
        args.putInt(TOTLE_COUNT,totleCount);
        PicDetailsFragment fragment = new PicDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = null;
        if (getArguments() != null){
            bundle = getArguments();
        }
        detailsPics = (DetailsPics) bundle.getSerializable(PARAM);
        current = bundle.getInt(CURRENT) + 1;
        totlecount = bundle.getInt(TOTLE_COUNT);
        title = bundle.getString(TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pic_details, container, false);
        mImg = (ImageView) view.findViewById(R.id.details_img);
        mTitleTxt = (TextView) view.findViewById(R.id.details_title_txt);
        mCountTxt = (TextView) view.findViewById(R.id.detailes_count_txt);
        mContent = (TextView) view.findViewById(R.id.details_content_txt);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Picasso.with(getActivity()).load(detailsPics.getPic()).into(mImg);
        mTitleTxt.setText(title);
        mCountTxt.setText(current+"/"+totlecount);
        mContent.setText(detailsPics.getAlt());

    }
}
