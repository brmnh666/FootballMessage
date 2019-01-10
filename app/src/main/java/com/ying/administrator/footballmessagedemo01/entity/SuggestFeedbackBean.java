package com.ying.administrator.footballmessagedemo01.entity;

import java.io.Serializable;

public class SuggestFeedbackBean implements Serializable {
    private String suggest_id;
    private String username;
    private String suggest;
    private String suggest_time;

    public String getSuggest_id() {
        return suggest_id;
    }

    public void setSuggest_id(String suggest_id) {
        this.suggest_id = suggest_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getSuggest_time() {
        return suggest_time;
    }

    public void setSuggest_time(String suggest_time) {
        this.suggest_time = suggest_time;
    }

    @Override
    public String toString() {
        return "SuggestFeedbackBean{" +
                "suggest_id='" + suggest_id + '\'' +
                ", username='" + username + '\'' +
                ", suggest='" + suggest + '\'' +
                ", suggest_time='" + suggest_time + '\'' +
                '}';
    }
}
