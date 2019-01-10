package com.ying.administrator.footballmessagedemo01.entity;

import java.io.Serializable;

public class HomeNewsBean implements Serializable{
private String web_url;  //新闻详情地址
private String thumb;     //图片地址
private String title; //新闻标题
private String description; //新闻详细细节
private String display_time; //新闻发布时间
public HomeNewsBean() {
	// TODO Auto-generated constructor stub
}

	public HomeNewsBean(String web_url, String thumb, String title, String description, String display_time) {
		this.web_url = web_url;
		this.thumb = thumb;
		this.title = title;
		this.description = description;
		this.display_time = display_time;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplay_time() {
		return display_time;
	}

	public void setDisplay_time(String display_time) {
		this.display_time = display_time;
	}

	@Override
	public String toString() {
		return "HomeNewsBean{" +
				"web_url='" + web_url + '\'' +
				", thumb='" + thumb + '\'' +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", display_time='" + display_time + '\'' +
				'}';
	}
}
