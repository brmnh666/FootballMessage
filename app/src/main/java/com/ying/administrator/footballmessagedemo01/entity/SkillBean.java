package com.ying.administrator.footballmessagedemo01.entity;

import java.io.Serializable;

public class SkillBean implements Serializable {
    private int id;
    private String skill_name;
    private String skill_img;
    private String skill_video_url;
    private String skill_content_url;
    private String min_skill;
    private String min_readtime;
    private String skill;
    private String worth;
    private String add_time;

    public SkillBean(int id, String skill_name, String skill_img, String skill_video_url, String skill_content_url, String min_skill, String min_readtime, String skill, String worth) {
        this.id = id;
        this.skill_name = skill_name;
        this.skill_img = skill_img;
        this.skill_video_url = skill_video_url;
        this.skill_content_url = skill_content_url;
        this.min_skill = min_skill;
        this.min_readtime = min_readtime;
        this.skill = skill;
        this.worth = worth;
    }

    public SkillBean() {
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

    public String getSkill_name() {
        return skill_name;
    }

    public void setSkill_name(String skill_name) {
        this.skill_name = skill_name;
    }

    public String getSkill_img() {
        return skill_img;
    }

    public void setSkill_img(String skill_img) {
        this.skill_img = skill_img;
    }

    public String getSkill_video_url() {
        return skill_video_url;
    }

    public void setSkill_video_url(String skill_video_url) {
        this.skill_video_url = skill_video_url;
    }

    public String getSkill_content_url() {
        return skill_content_url;
    }

    public void setSkill_content_url(String skill_content_url) {
        this.skill_content_url = skill_content_url;
    }

    public String getMin_skill() {
        return min_skill;
    }

    public void setMin_skill(String min_skill) {
        this.min_skill = min_skill;
    }

    public String getMin_readtime() {
        return min_readtime;
    }

    public void setMin_readtime(String min_readtime) {
        this.min_readtime = min_readtime;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getWorth() {
        return worth;
    }

    public void setWorth(String worth) {
        this.worth = worth;
    }

    @Override
    public String toString() {
        return "SkillBean{" +
                "id=" + id +
                ", skill_name='" + skill_name + '\'' +
                ", skill_img='" + skill_img + '\'' +
                ", skill_video_url='" + skill_video_url + '\'' +
                ", skill_content_url='" + skill_content_url + '\'' +
                ", min_skill='" + min_skill + '\'' +
                ", min_readtime='" + min_readtime + '\'' +
                ", skill='" + skill + '\'' +
                ", worth='" + worth + '\'' +
                '}';
    }
}
