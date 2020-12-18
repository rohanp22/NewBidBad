package com.example.bidbadnew.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bidbadnew.Model.Address;
import com.example.bidbadnew.Model.AddressModel;
import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.Model.AddressModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressRepo {

    public static AddressRepo addressRepo;

    public static AddressRepo getInstance(int id){
        if(addressRepo == null){
            addressRepo = new AddressRepo();
        }
        return addressRepo;
    }

    public MutableLiveData<Address> address = new MutableLiveData<>();

    public MutableLiveData<Address> getAddress(int id) {

        Call<AddressModel> call = RetrofitClient.getInstance().getMyApi().getMyAddresses(id);
        call.enqueue(new Callback<AddressModel>() {
            @Override
            public void onResponse(@NotNull Call<AddressModel> call, @NotNull Response<AddressModel> response) {
                address.postValue(response.body().getAddress().get(0));
                Log.d("Hi", "Hey");
            }

            @Override
            public void onFailure(@NotNull Call<AddressModel> call, @NotNull Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return address;
    }

}
