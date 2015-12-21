package com.example.scxh.mynews.prcture_type_news.pic_javabean;

import java.io.Serializable;

/**
 * Created by xieke on 2015/12/15.
 */
public class DetailsPics implements Serializable {
    private String pic;
    private String alt;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
