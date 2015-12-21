package com.example.scxh.mynews.title_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.scxh.mynews.R;

/**
 * Created by xieke on 2015/12/17.
 */
public class NavigationView extends RelativeLayout implements View.OnClickListener{
    private int backRes,moreRes,bgColor;
    private String commentText;

    private OnClickListener onClickListener;
    public void registOnClickListener(OnClickListener o){  //注册监听
        Log.e("tag","+++++++o++++++"+o);
        this.onClickListener  = o;
    }

    public NavigationView(Context context) {
        super(context);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public NavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public void init(Context context, AttributeSet attrs){
        TypedArray array = null;
        try {
            array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NavigationView,0,0);
            backRes = array.getResourceId(R.styleable.NavigationView_nv_back_img,-1);
            moreRes = array.getResourceId(R.styleable.NavigationView_nv_more_img,-1);
            commentText = array.getString(R.styleable.NavigationView_nv_comment_text);
            bgColor = array.getColor(R.styleable.NavigationView_bgcolor,0);
            LayoutInflater inflater = LayoutInflater.from(context);
            initView(inflater);

        }finally {
            array.recycle();  //释放资源
        }
    }

    public void initView(LayoutInflater inflater){
        View view = inflater.inflate(R.layout.content_navigation_layout,this,true);
        view.setBackgroundColor(bgColor); // 背景颜色
        if (backRes != -1){
            ImageButton backBtn = (ImageButton) view.findViewById(R.id.navigation_back_imgbtn);
            backBtn.setImageResource(backRes);
            backBtn.setOnClickListener(this); //注册监听

        }
        if (moreRes != -1){
            ImageButton moreBtn = (ImageButton) view.findViewById(R.id.navigation_more_imgbtn);
            moreBtn.setImageResource(moreRes);
            moreBtn.setOnClickListener(this);
        }

            TextView commentTxt = (TextView) view.findViewById(R.id.navigation_comment_txt);
        if (commentText != null){
            commentTxt.setText(commentText);
        }
            commentTxt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.e("tag","---onClickListener-----"+onClickListener+"=====v===="+v);
        if(onClickListener != null){
            onClickListener.onClick(v);
        }
    }
}
