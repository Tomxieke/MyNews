package com.example.scxh.mynews.prcture_type_news.pic_javabean;

/**
 * Created by xieke on 2015/12/15.
 */
public class PicContentBean {
    private String id; //访问详情id
    private String title;  //短题目
    private String long_title;  //长题目
    private String pic;  //图片来源
    private CommentBean comment_count_info;   //获取评论数据

    public CommentBean getComment_count_info() {
        return comment_count_info;
    }

    public void setComment_count_info(CommentBean comment_count_info) {
        this.comment_count_info = comment_count_info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLong_title() {
        return long_title;
    }

    public void setLong_title(String long_title) {
        this.long_title = long_title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
