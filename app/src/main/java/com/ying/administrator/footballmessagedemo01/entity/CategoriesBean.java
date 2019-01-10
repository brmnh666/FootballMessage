package com.ying.administrator.footballmessagedemo01.entity;

import java.io.Serializable;

public class CategoriesBean implements Serializable {
 
	private String title; //分类tab名称
	private String href; //分类点击的地址
	private String rel;  // 判断分类的标签
	public CategoriesBean() {
		super();
	}

	public CategoriesBean(String title, String href, String rel) {
		this.title = title;
		this.href = href;
		this.rel = rel;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}


	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	@Override
	public String toString() {
		return "CategoriesBean{" +
				"title='" + title + '\'' +
				", href='" + href + '\'' +
				", rel='" + rel + '\'' +
				'}';
	}
}
