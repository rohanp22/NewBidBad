package com.example.bidbadnew.Fragments;

import androidx.lifecycle.ViewModel;

import com.example.bidbadnew.Model.WonItem;

public class PlaceOrderViewModel extends ViewModel {

    WonItem wonItem;

    public WonItem getWonItem() {
        if(wonItem == null){
            return null;
        }
        return wonItem;
    }

    public void setWonItem(WonItem wonItem) {
        this.wonItem = wonItem;
    }
}
