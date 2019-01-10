package com.ying.administrator.footballmessagedemo01.entity;

import java.io.Serializable;

public class SkimNewsBean implements Serializable {
    private int id;
    private String username;
    private String title;
    private String thumb;
    private String web_url;
    private String display_time;
    private String skim_time;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDisplay_time() {
        return display_time;
    }

    public void setDisplay_time(String display_time) {
        this.display_time = display_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getSkim_time() {
        return skim_time;
    }

    public void setSkim_time(String skim_time) {
        this.skim_time = skim_time;
    }

    @Override
    public String toString() {
        return "SkimNewsBean{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", thumb='" + thumb + '\'' +
                ", web_url='" + web_url + '\'' +
                ", display_time='" + display_time + '\'' +
                ", skim_time='" + skim_time + '\'' +
                '}';
    }
}
