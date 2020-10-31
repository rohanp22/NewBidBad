package com.example.bidbadnew.Model;

public class HomeProductModel {

    private int image;
    private String imageurl;
    private String title;
    private String desc;

    public HomeProductModel(int image, String title, String desc, String imageurl) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.imageurl = imageurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}