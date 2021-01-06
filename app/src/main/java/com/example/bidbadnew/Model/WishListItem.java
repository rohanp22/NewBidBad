package com.example.bidbadnew.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishListItem {

    @SerializedName("currentid")
    @Expose
    private String currentid;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("endtime")
    @Expose
    private String endtime;
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("sp")
    @Expose
    private String sp;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image_url2")
    @Expose
    private String imageUrl2;
    @SerializedName("image_url3")
    @Expose
    private String imageUrl3;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("subtitle")
    @Expose
    private String subtitle;
    @SerializedName("category")
    @Expose
    private String category;

    public String getCurrentid() {
        return currentid;
    }

    public void setCurrentid(String currentid) {
        this.currentid = currentid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getImageUrl3() {
        return imageUrl3;
    }

    public void setImageUrl3(String imageUrl3) {
        this.imageUrl3 = imageUrl3;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
