package com.example.bidbadnew.Fragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.repositories.CurrentProductsRepo;

import java.util.List;

public class ProductsViewModel extends ViewModel {

    CurrentProductsRepo currentProductsRepo = CurrentProductsRepo.getInstance();
    public MutableLiveData<List<Current_Product>> homeProductCategory = currentProductsRepo.getHomeProductCategory();
    public MutableLiveData<List<Current_Product>> electronicsProductCategory = currentProductsRepo.getElectronicsProductCategory();
    public MutableLiveData<List<Current_Product>> apparelProductCategory = currentProductsRepo.getApparelProductCategory();
    public MutableLiveData<List<Current_Product>> fitnessProductCategory = currentProductsRepo.getFitnessProductCategory();
    public MutableLiveData<List<Current_Product>> personalcareProductCategory = currentProductsRepo.getPersonalcareProductCategory();
    public MutableLiveData<List<Current_Product>> accessoriesCategory = currentProductsRepo.getAccessoriesCategory();
    public MutableLiveData<List<Current_Product>> othersCategory = currentProductsRepo.getOthersCategory();
    public MutableLiveData<List<Current_Product>> appliancesCategory = currentProductsRepo.getAppliancesCategory();
    public MutableLiveData<List<Current_Product>> freeBidsCategory = currentProductsRepo.getFreeBids();

    public void refreshProduct(int position, int userid){
        currentProductsRepo.getProducts(position, userid);
    }

}
