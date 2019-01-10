package com.ying.administrator.footballmessagedemo01.entity;

public class HeadBean {
private String img;
private String href;
private String title;
public HeadBean() {
	
}
public HeadBean(String img, String href, String title) {
	super();
	this.img = img;
	this.href = href;
	this.title = title;
}
public String getImg() {
	return img;
}
public void setImg(String img) {
	this.img = img;
}
public String getHref() {
	return href;
}
public void setHref(String href) {
	this.href = href;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
@Override
public String toString() {
	return "HeadBean [img=" + img + ", href=" + href + ", title=" + title + "]";
}



}
