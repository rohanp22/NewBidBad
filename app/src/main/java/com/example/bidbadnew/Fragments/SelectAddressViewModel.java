package com.example.bidbadnew.Fragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bidbadnew.Model.Address;
import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.repositories.AddressRepo;
import com.example.bidbadnew.repositories.CurrentProductsRepo;

import java.util.List;

public class SelectAddressViewModel extends ViewModel {

    AddressRepo addressRepo;

    private MutableLiveData<Address> addressMutableLiveData;

    public void init(int id){
        if(addressMutableLiveData != null){
            return;
        }
        addressRepo = AddressRepo.getInstance(id);
        addressMutableLiveData = addressRepo.getAddress(id);
    }

    public MutableLiveData<Address> getAddressMutableLiveData() {
        return addressMutableLiveData;
    }

}
