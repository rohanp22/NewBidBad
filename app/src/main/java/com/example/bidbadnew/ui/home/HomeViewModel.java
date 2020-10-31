package com.example.bidbadnew.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.repositories.CurrentProductsRepo;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<Current_Product>> current_productsmutable;
    Application application;

    public void init(){
        if(current_productsmutable != null){
            return;
        }
        CurrentProductsRepo currentProductsRepo = CurrentProductsRepo.getInstance();
        current_productsmutable = currentProductsRepo.getCurrentProducts();
    }

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public MutableLiveData<List<Current_Product>> getCurrent_productsmutable() {
        return current_productsmutable;
    }
}