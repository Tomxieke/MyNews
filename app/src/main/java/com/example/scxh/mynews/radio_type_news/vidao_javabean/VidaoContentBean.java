package com.example.scxh.mynews.radio_type_news.vidao_javabean;

/**
 * Created by xieke on 2015/12/16.
 */
public class VidaoContentBean {
    private String videosource;  //视频来源
    private String cover;   //视频图片
    private String title; //视频标题
    private String description; //视频简单描述
    private String mp4_url;  //视频播放地址
    private String sectiontitle; //精品视频来源；

    public String getSectiontitle() {
        return sectiontitle;
    }

    public void setSectiontitle(String sectiontitle) {
        this.sectiontitle = sectiontitle;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideosource() {
        return videosource;
    }

    public void setVideosource(String videosource) {
        this.videosource = videosource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMp4_url() {
        return mp4_url;
    }

    public void setMp4_url(String mp4_url) {
        this.mp4_url = mp4_url;
    }
}
