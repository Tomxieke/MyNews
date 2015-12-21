package com.example.scxh.mynews.content_fragment.news_type_fragment.headlinenews;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 新闻内容里面如果有图片时的处理
 * Created by xieke on 2015/12/18.
 */
public class ContentImgBean implements Parcelable {
    private String ref;
    private String alt;  //内容简介
    private String src;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ref);
        dest.writeString(alt);
        dest.writeString(src);
    }


    public static final Parcelable.Creator<ContentImgBean> CREATOR = new Parcelable.Creator<ContentImgBean>(){

        @Override
        public ContentImgBean createFromParcel(Parcel source) {

            String ref = source.readString();
            String alt = source.readString();
            String src = source.readString();
            ContentImgBean bean = new ContentImgBean();
            bean.setRef(ref);
            bean.setAlt(alt);
            bean.setSrc(src);
            return bean;
        }

        @Override
        public ContentImgBean[] newArray(int size) {
            return null;

        }
    };
}
