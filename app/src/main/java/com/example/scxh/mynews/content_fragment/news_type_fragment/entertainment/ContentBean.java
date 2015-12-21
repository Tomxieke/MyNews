package com.example.scxh.mynews.content_fragment.news_type_fragment.entertainment;

import com.example.scxh.mynews.content_fragment.news_type_fragment.headlinenews.ThreeprictureUrlBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by scxh on 2015/12/11.
 */
public class ContentBean implements Serializable {
    private String digest;
    private String title;
    private String imgsrc;
    private String docid; //新闻内容
    private String source;  //新闻来源
    private String ptime;  //推送事件
    private String replyCount; //总共评论条数

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    private ArrayList<ThreeprictureUrlBean> imgextra;  //三张图片的特殊处理

    public ArrayList<ThreeprictureUrlBean> getImgextra() {
        return imgextra;
    }

    public void setImgextra(ArrayList<ThreeprictureUrlBean> imgextra) {
        this.imgextra = imgextra;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }


}
