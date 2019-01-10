package com.ying.administrator.footballmessagedemo01.entity;

import java.io.Serializable;

public class TacitcsBean implements Serializable {
    private int id;
    private String tactics_name;
    private String tactics_img;
    private String tactics_content;
    private String tactics_url;
    private String min_tactics;
    private String min_readtime;
    private String tactics;
    private String worth;
    private String add_time;



    public TacitcsBean() {
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTactics_name() {
        return tactics_name;
    }

    public void setTactics_name(String tactics_name) {
        this.tactics_name = tactics_name;
    }

    public String getTactics_img() {
        return tactics_img;
    }

    public void setTactics_img(String tactics_img) {
        this.tactics_img = tactics_img;
    }

    public String getTactics_content() {
        return tactics_content;
    }

    public void setTactics_content(String tactics_content) {
        this.tactics_content = tactics_content;
    }

    public String getTactics_url() {
        return tactics_url;
    }

    public void setTactics_url(String tactics_url) {
        this.tactics_url = tactics_url;
    }

    public String getMin_tactics() {
        return min_tactics;
    }

    public void setMin_tactics(String min_tactics) {
        this.min_tactics = min_tactics;
    }

    public String getMin_readtime() {
        return min_readtime;
    }

    public void setMin_readtime(String min_readtime) {
        this.min_readtime = min_readtime;
    }

    public String getTactics() {
        return tactics;
    }

    public void setTactics(String tactics) {
        this.tactics = tactics;
    }

    public String getWorth() {
        return worth;
    }

    public void setWorth(String worth) {
        this.worth = worth;
    }

    @Override
    public String toString() {
        return "TacitcsBean{" +
                "id=" + id +
                ", tactics_name='" + tactics_name + '\'' +
                ", tactics_img='" + tactics_img + '\'' +
                ", tactics_content='" + tactics_content + '\'' +
                ", tactics_url='" + tactics_url + '\'' +
                ", min_tactics='" + min_tactics + '\'' +
                ", min_readtime='" + min_readtime + '\'' +
                ", tactics='" + tactics + '\'' +
                ", worth='" + worth + '\'' +
                ", add_time='" + add_time + '\'' +
                '}';
    }
}
