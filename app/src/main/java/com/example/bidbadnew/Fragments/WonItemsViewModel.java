package com.example.bidbadnew.Fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bidbadnew.Model.OngoingItems;
import com.example.bidbadnew.Model.WonItem;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.repositories.OngoingBidsRepo;
import com.example.bidbadnew.repositories.WonItemsRepo;

import java.util.List;

public class WonItemsViewModel extends ViewModel {

    private MutableLiveData<List<WonItem>> wonItems;
    int id;

    public void init(int id){
        this.id = id;
        if(wonItems != null){
            return;
        }
        WonItemsRepo wonItemsRepo = WonItemsRepo.getInstance();
        wonItems = wonItemsRepo.getMyWonItems(id);
    }

    public MutableLiveData<List<WonItem>> getMyWonItems() {
        return wonItems;
    }

}
