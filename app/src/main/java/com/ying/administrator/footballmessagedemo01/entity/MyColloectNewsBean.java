package com.ying.administrator.footballmessagedemo01.entity;

import java.io.Serializable;

public class MyColloectNewsBean implements Serializable {

    private int id;  //新闻id
    private String web_url;  //新闻详情地址
    private String thumb;     //图片地址
    private String title; //新闻标题
    private String display_time; //新闻发布时间
    private String username; //该新闻的收藏者
    private String collection_time;//收藏时间



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getDisplay_time() {
        return display_time;
    }

    public void setDisplay_time(String display_time) {
        this.display_time = display_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCollection_time() {
        return collection_time;
    }

    public void setCollection_time(String collection_time) {
        this.collection_time = collection_time;
    }

    @Override
    public String toString() {
        return "MyColloectNewsBean{" +
                "id=" + id +
                ", web_url='" + web_url + '\'' +
                ", thumb='" + thumb + '\'' +
                ", title='" + title + '\'' +
                ", display_time='" + display_time + '\'' +
                ", username='" + username + '\'' +
                ", collection_time='" + collection_time + '\'' +
                '}';
    }
}
