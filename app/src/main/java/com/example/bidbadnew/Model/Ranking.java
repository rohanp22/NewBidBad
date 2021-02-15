package com.example.bidbadnew.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ranking {

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
    @SerializedName("firstname")
    @Expose
    private String firstname;

    public String getFirstname() {
        return firstname;
    }

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

}