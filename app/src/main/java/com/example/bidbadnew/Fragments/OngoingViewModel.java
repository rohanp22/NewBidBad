package com.example.bidbadnew.Fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.bidbadnew.Model.OngoingItems;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.repositories.OngoingBidsRepo;

import java.util.List;

public class OngoingViewModel extends AndroidViewModel {

    private MutableLiveData<List<OngoingItems>> onGoingItems;
    Application application;

    public void init(){
        if(onGoingItems != null){
            return;
        }
        OngoingBidsRepo ongoingBidsRepo = OngoingBidsRepo.getInstance();
        onGoingItems = ongoingBidsRepo.getMyOngoingBids(SharedPrefManager.getInstance(application.getApplicationContext()).getUser().getId());
    }

    public OngoingViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public MutableLiveData<List<OngoingItems>> getCurrent_productsmutable() {
        return onGoingItems;
    }
}
