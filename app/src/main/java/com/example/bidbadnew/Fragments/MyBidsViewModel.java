package com.example.bidbadnew.Fragments;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bidbadnew.Model.MyBid;
import com.example.bidbadnew.repositories.MyBidsRepo;

import java.util.List;

public class MyBidsViewModel extends ViewModel {

    private MutableLiveData<List<MyBid>> myBids;

    public void init(int id){
        MyBidsRepo myBidsRepo = MyBidsRepo.getInstance();
        myBids = myBidsRepo.getMyBids(id);
    }

    public MutableLiveData<List<MyBid>> getMyBids() {
        return myBids;
    }

}
