package com.example.scxh.mynews.title_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.scxh.mynews.R;

/**
 * 还没用上的自定义控件
 * Created by scxh on 2015/12/10.
 */
public class NewsTitleView extends RelativeLayout{
    private String mLeftTxt,mRightTxt,mCenterTxt;  //文本
    private int mLeftIconRes,mRightIconRes;  //图片资源id
    public NewsTitleView(Context context) {
        super(context);
    }

    public NewsTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public NewsTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    /**
     * 获取参数
     * @param context
     * @param attrs
     */
    public void init(Context context, AttributeSet attrs){
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NewsTitleView,0,0);
        try {

            mLeftTxt = array.getString(R.styleable.NewsTitleView_leftText);
            mLeftIconRes = array.getResourceId(R.styleable.NewsTitleView_leftIcon, -1);
            mCenterTxt = array.getString(R.styleable.NewsTitleView_centerContent);
            mRightTxt = array.getString(R.styleable.NewsTitleView_rightText);
            mRightIconRes = array.getResourceId(R.styleable.NewsTitleView_rightIcon, -1);
            LayoutInflater inflater = LayoutInflater.from(context);
            initView(inflater);

        }finally {
            array.recycle();
        }
    }

    /**
     *
     * @param inflater
     */
    public void initView(LayoutInflater inflater){

    }
}
