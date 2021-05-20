package com.example.bidbadnew.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class MyBid implements Serializable {

    @SerializedName("bidid")
    @Expose
    private String bidid;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("productid")
    @Expose
    private String productid;
    @SerializedName("bidamount")
    @Expose
    private String bidamount;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("bonuspoints")
    @Expose
    private String bonuspoints;
    @SerializedName("freebid")
    @Expose
    private String freebid;
    @SerializedName("past_id")
    @Expose
    private String pastId;
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
    @SerializedName("bonus")
    @Expose
    private String bonus;
    @SerializedName("bidlimit")
    @Expose
    private String bidlimit;
    @SerializedName("userlimit")
    @Expose
    private String userlimit;
    @SerializedName("orderplaced")
    @Expose
    private Object orderplaced;
    @SerializedName("bid_price")
    @Expose
    private String bidPrice;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("firstname")
    @Expose
    private String firstname;

    public String getBidid() {
        return bidid;
    }

    public void setBidid(String bidid) {
        this.bidid = bidid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getBidamount() {
        return bidamount;
    }

    public void setBidamount(String bidamount) {
        this.bidamount = bidamount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBonuspoints() {
        return bonuspoints;
    }

    public void setBonuspoints(String bonuspoints) {
        this.bonuspoints = bonuspoints;
    }

    public String getFreebid() {
        return freebid;
    }

    public void setFreebid(String freebid) {
        this.freebid = freebid;
    }

    public String getPastId() {
        return pastId;
    }

    public void setPastId(String pastId) {
        this.pastId = pastId;
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

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getBidlimit() {
        return bidlimit;
    }

    public void setBidlimit(String bidlimit) {
        this.bidlimit = bidlimit;
    }

    public String getUserlimit() {
        return userlimit;
    }

    public void setUserlimit(String userlimit) {
        this.userlimit = userlimit;
    }

    public Object getOrderplaced() {
        return orderplaced;
    }

    public void setOrderplaced(Object orderplaced) {
        this.orderplaced = orderplaced;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

}