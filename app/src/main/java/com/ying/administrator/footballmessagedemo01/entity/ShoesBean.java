package com.ying.administrator.footballmessagedemo01.entity;

import java.io.Serializable;

public class ShoesBean implements Serializable {
    private int id;
    private String username;
     private String shoes_id;

    private String add_time;
    private String collection_time;
    private String shoes_name;
    private String shoes_brand;
    private String shoes_children;
    private String shoes_texture;
    private String shoes_place;
    private String shoes_color;
    private String shoes_yieldly;
    private String shoes_level;
    private String shoes_type;
    private String shoes_weight;
    private String shoes_NO;
    private String shoes_price;
    private String shoes_cover_picture;
    private String shoes_content_picture_1;
    private String shoes_content_picture_2;
    private String shoes_content_picture_3;

    public String getShoes_id() {
        return shoes_id;
    }

    public void setShoes_id(String shoes_id) {
        this.shoes_id = shoes_id;
    }

    public String getCollection_time() {
        return collection_time;
    }

    public void setCollection_time(String collection_time) {
        this.collection_time = collection_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public ShoesBean() {
    }

    public ShoesBean(int id, String username, String add_time, String collection_time, String shoes_name, String shoes_brand, String shoes_children, String shoes_texture, String shoes_place, String shoes_color, String shoes_yieldly, String shoes_level, String shoes_type, String shoes_weight, String shoes_NO, String shoes_price, String shoes_cover_picture, String shoes_content_picture_1, String shoes_content_picture_2, String shoes_content_picture_3) {
        this.id = id;

        this.username = username;
        this.add_time = add_time;
        this.collection_time = collection_time;
        this.shoes_name = shoes_name;
        this.shoes_brand = shoes_brand;
        this.shoes_children = shoes_children;
        this.shoes_texture = shoes_texture;
        this.shoes_place = shoes_place;
        this.shoes_color = shoes_color;
        this.shoes_yieldly = shoes_yieldly;
        this.shoes_level = shoes_level;
        this.shoes_type = shoes_type;
        this.shoes_weight = shoes_weight;
        this.shoes_NO = shoes_NO;
        this.shoes_price = shoes_price;
        this.shoes_cover_picture = shoes_cover_picture;
        this.shoes_content_picture_1 = shoes_content_picture_1;
        this.shoes_content_picture_2 = shoes_content_picture_2;
        this.shoes_content_picture_3 = shoes_content_picture_3;
    }

    public String getShoes_name() {
        return shoes_name;
    }

    public void setShoes_name(String shoes_name) {
        this.shoes_name = shoes_name;
    }

    public String getShoes_brand() {
        return shoes_brand;
    }

    public void setShoes_brand(String shoes_brand) {
        this.shoes_brand = shoes_brand;
    }

    public String getShoes_children() {
        return shoes_children;
    }

    public void setShoes_children(String shoes_children) {
        this.shoes_children = shoes_children;
    }

    public String getShoes_texture() {
        return shoes_texture;
    }

    public void setShoes_texture(String shoes_texture) {
        this.shoes_texture = shoes_texture;
    }

    public String getShoes_place() {
        return shoes_place;
    }

    public void setShoes_place(String shoes_place) {
        this.shoes_place = shoes_place;
    }

    public String getShoes_color() {
        return shoes_color;
    }

    public void setShoes_color(String shoes_color) {
        this.shoes_color = shoes_color;
    }

    public String getShoes_yieldly() {
        return shoes_yieldly;
    }

    public void setShoes_yieldly(String shoes_yieldly) {
        this.shoes_yieldly = shoes_yieldly;
    }

    public String getShoes_level() {
        return shoes_level;
    }

    public void setShoes_level(String shoes_level) {
        this.shoes_level = shoes_level;
    }

    public String getShoes_type() {
        return shoes_type;
    }

    public void setShoes_type(String shoes_type) {
        this.shoes_type = shoes_type;
    }

    public String getShoes_weight() {
        return shoes_weight;
    }

    public void setShoes_weight(String shoes_weight) {
        this.shoes_weight = shoes_weight;
    }

    public String getShoes_NO() {
        return shoes_NO;
    }

    public void setShoes_NO(String shoes_NO) {
        this.shoes_NO = shoes_NO;
    }

    public String getShoes_price() {
        return shoes_price;
    }

    public void setShoes_price(String shoes_price) {
        this.shoes_price = shoes_price;
    }

    public String getShoes_cover_picture() {
        return shoes_cover_picture;
    }

    public void setShoes_cover_picture(String shoes_cover_picture) {
        this.shoes_cover_picture = shoes_cover_picture;
    }

    public String getShoes_content_picture_1() {
        return shoes_content_picture_1;
    }

    public void setShoes_content_picture_1(String shoes_content_picture_1) {
        this.shoes_content_picture_1 = shoes_content_picture_1;
    }

    public String getShoes_content_picture_2() {
        return shoes_content_picture_2;
    }

    public void setShoes_content_picture_2(String shoes_content_picture_2) {
        this.shoes_content_picture_2 = shoes_content_picture_2;
    }

    public String getShoes_content_picture_3() {
        return shoes_content_picture_3;
    }

    public void setShoes_content_picture_3(String shoes_content_picture_3) {
        this.shoes_content_picture_3 = shoes_content_picture_3;
    }

    @Override
    public String toString() {
        return "ShoesBean{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", shoes_id='" + shoes_id + '\'' +
                ", add_time='" + add_time + '\'' +
                ", collection_time='" + collection_time + '\'' +
                ", shoes_name='" + shoes_name + '\'' +
                ", shoes_brand='" + shoes_brand + '\'' +
                ", shoes_children='" + shoes_children + '\'' +
                ", shoes_texture='" + shoes_texture + '\'' +
                ", shoes_place='" + shoes_place + '\'' +
                ", shoes_color='" + shoes_color + '\'' +
                ", shoes_yieldly='" + shoes_yieldly + '\'' +
                ", shoes_level='" + shoes_level + '\'' +
                ", shoes_type='" + shoes_type + '\'' +
                ", shoes_weight='" + shoes_weight + '\'' +
                ", shoes_NO='" + shoes_NO + '\'' +
                ", shoes_price='" + shoes_price + '\'' +
                ", shoes_cover_picture='" + shoes_cover_picture + '\'' +
                ", shoes_content_picture_1='" + shoes_content_picture_1 + '\'' +
                ", shoes_content_picture_2='" + shoes_content_picture_2 + '\'' +
                ", shoes_content_picture_3='" + shoes_content_picture_3 + '\'' +
                '}';
    }
}
