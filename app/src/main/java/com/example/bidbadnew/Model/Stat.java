package com.example.bidbadnew.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stat {

    @SerializedName("numberofbids")
    @Expose
    private String numberofbids;
    @SerializedName("wins")
    @Expose
    private String wins;

    public String getNumberofbids() {
        return numberofbids;
    }

    public void setNumberofbids(String numberofbids) {
        this.numberofbids = numberofbids;
    }

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

}