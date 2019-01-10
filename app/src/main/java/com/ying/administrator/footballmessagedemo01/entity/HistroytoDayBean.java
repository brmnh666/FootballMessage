package com.ying.administrator.footballmessagedemo01.entity;

public class HistroytoDayBean {

    private String date;
    private String year;
    private String details;
    private String position;
    private String img;
    private String title;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HistroytoDayBean(String date, String year, String details, String position, String img, String title) {
        this.date = date;
        this.year = year;
        this.details = details;
        this.position = position;
        this.img = img;
        this.title=title;
    }

    public HistroytoDayBean() {
    }

    @Override
    public String toString() {
        return "HistroytoDayBean{" +
                "date='" + date + '\'' +
                ", year='" + year + '\'' +
                ", details='" + details + '\'' +
                ", position='" + position + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
