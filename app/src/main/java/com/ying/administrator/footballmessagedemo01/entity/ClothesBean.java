package com.ying.administrator.footballmessagedemo01.entity;

import java.io.Serializable;

public class ClothesBean implements Serializable {
    private int id;
    private String clothes_id;
    private String username;
    private String add_time;
    private String collection_time;
    private String clothes_name;
    private String clothes_brand;
    private String clothes_yieldly;
    private String clothes_price;
    private String clothes_cover_picture;
    private String clothes_detail_picture1;
    private String clothes_detail_picture2;
    private String clothes_detail_picture3;

    public ClothesBean(String clothes_name, String clothes_brand, String clothes_yieldly, String clothes_price, String clothes_cover_picture, String clothes_detail_picture1, String clothes_detail_picture2, String clothes_detail_picture3) {
        this.clothes_name = clothes_name;
        this.clothes_brand = clothes_brand;
        this.clothes_yieldly = clothes_yieldly;
        this.clothes_price = clothes_price;
        this.clothes_cover_picture = clothes_cover_picture;
        this.clothes_detail_picture1 = clothes_detail_picture1;
        this.clothes_detail_picture2 = clothes_detail_picture2;
        this.clothes_detail_picture3 = clothes_detail_picture3;
    }

    public ClothesBean() {
    }

    @Override
    public String toString() {
        return "ClothesBean{" +
                "clothes_name='" + clothes_name + '\'' +
                ", clothes_brand='" + clothes_brand + '\'' +
                ", clothes_yieldly='" + clothes_yieldly + '\'' +
                ", clothes_price='" + clothes_price + '\'' +
                ", clothes_cover_picture='" + clothes_cover_picture + '\'' +
                ", clothes_detail_picture1='" + clothes_detail_picture1 + '\'' +
                ", clothes_detail_picture2='" + clothes_detail_picture2 + '\'' +
                ", clothes_detail_picture3='" + clothes_detail_picture3 + '\'' +
                '}';
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getCollection_time() {
        return collection_time;
    }

    public void setCollection_time(String collection_time) {
        this.collection_time = collection_time;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClothes_id() {
        return clothes_id;
    }

    public void setClothes_id(String clothes_id) {
        this.clothes_id = clothes_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClothes_name() {
        return clothes_name;
    }

    public void setClothes_name(String clothes_name) {
        this.clothes_name = clothes_name;
    }

    public String getClothes_brand() {
        return clothes_brand;
    }

    public void setClothes_brand(String clothes_brand) {
        this.clothes_brand = clothes_brand;
    }

    public String getClothes_yieldly() {
        return clothes_yieldly;
    }

    public void setClothes_yieldly(String clothes_yieldly) {
        this.clothes_yieldly = clothes_yieldly;
    }

    public String getClothes_price() {
        return clothes_price;
    }

    public void setClothes_price(String clothes_price) {
        this.clothes_price = clothes_price;
    }

    public String getClothes_cover_picture() {
        return clothes_cover_picture;
    }

    public void setClothes_cover_picture(String clothes_cover_picture) {
        this.clothes_cover_picture = clothes_cover_picture;
    }

    public String getClothes_detail_picture1() {
        return clothes_detail_picture1;
    }

    public void setClothes_detail_picture1(String clothes_detail_picture1) {
        this.clothes_detail_picture1 = clothes_detail_picture1;
    }

    public String getClothes_detail_picture2() {
        return clothes_detail_picture2;
    }

    public void setClothes_detail_picture2(String clothes_detail_picture2) {
        this.clothes_detail_picture2 = clothes_detail_picture2;
    }

    public String getClothes_detail_picture3() {
        return clothes_detail_picture3;
    }

    public void setClothes_detail_picture3(String clothes_detail_picture3) {
        this.clothes_detail_picture3 = clothes_detail_picture3;
    }
}
