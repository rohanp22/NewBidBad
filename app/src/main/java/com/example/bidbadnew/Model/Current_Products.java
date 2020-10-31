package com.example.bidbadnew.Model;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Current_Products {

    @SerializedName("Current_Products")
    @Expose
    private List<Current_Product> currentProducts = null;

    public List<Current_Product> getCurrentProducts() {
        return currentProducts;
    }

    public void setCurrentProducts(List<Current_Product> currentProducts) {
        this.currentProducts = currentProducts;
    }

}