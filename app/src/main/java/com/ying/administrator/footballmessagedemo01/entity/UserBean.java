package com.ying.administrator.footballmessagedemo01.entity;

import java.io.Serializable;

public class UserBean implements Serializable {
    private int id;
    private String username;
    private String password;
    private String name; //app里的账号名
    private String phone;   //电话号
    private String adress;
    private String sex;
    private String register_time;
    private String head_img;
    private String skill;
    private String worth;
    private String tactics;
    private String rank;
    private String birthday;


    private String is_new_user;//判断是否是新用户 1是新用户    -1不是新用户



    public String getIs_new_user() {
        return is_new_user;
    }

    public void setIs_new_user(String is_new_user) {
        this.is_new_user = is_new_user;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
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

    public String getTactics() {
        return tactics;
    }

    public void setTactics(String tactics) {
        this.tactics = tactics;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", adress='" + adress + '\'' +
                ", sex='" + sex + '\'' +
                ", register_time='" + register_time + '\'' +
                ", head_img='" + head_img + '\'' +
                ", skill='" + skill + '\'' +
                ", worth='" + worth + '\'' +
                ", tactics='" + tactics + '\'' +
                ", rank='" + rank + '\'' +
                ", birthday='" + birthday + '\'' +
                ", is_new_user='" + is_new_user + '\'' +
                '}';
    }
}
