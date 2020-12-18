package com.example.bidbadnew.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.repositories.CurrentProductsRepo;

import java.util.List;

public class HomeViewModel extends ViewModel {

    CurrentProductsRepo currentProductsRepo;

    private MutableLiveData<List<Current_Product>> current_productsmutable;

    public void init(int id) {
        if (current_productsmutable != null) {
            return;
        }
        currentProductsRepo = CurrentProductsRepo.getInstance(id);
        current_productsmutable = currentProductsRepo.getAppliancesCategory(id);
    }

    public MutableLiveData<List<Current_Product>> getCurrent_productsmutable() {
        return current_productsmutable;
    }
}