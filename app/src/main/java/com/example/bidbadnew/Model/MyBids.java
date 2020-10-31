package com.example.bidbadnew.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyBids {

    @SerializedName("Bids")
    @Expose
    private List<MyBid> bids = null;

    public List<MyBid> getBids() {
        return bids;
    }

    public void setBids(List<MyBid> bids) {
        this.bids = bids;
    }

}