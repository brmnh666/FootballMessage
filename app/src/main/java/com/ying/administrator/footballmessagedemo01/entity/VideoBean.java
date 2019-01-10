package com.ying.administrator.footballmessagedemo01.entity;

/**
 * Created by wuxiaoqi on 2017/9/27.
 */

public class VideoBean {
     private String title;

    private String video_url;

    public VideoBean() {
    }

    public VideoBean(String title, String video_url) {
        this.title = title;
        this.video_url = video_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "title='" + title + '\'' +
                ", video_url='" + video_url + '\'' +
                '}';
    }
}
