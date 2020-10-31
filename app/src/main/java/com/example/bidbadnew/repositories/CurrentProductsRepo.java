package com.example.bidbadnew.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.Model.Current_Products;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentProductsRepo {

    public static CurrentProductsRepo currentProductsRepo;

    public static CurrentProductsRepo getInstance(){
        if(currentProductsRepo == null){
            currentProductsRepo = new CurrentProductsRepo();
        }
        return currentProductsRepo;
    }

    public static List<Current_Product> currentProductsList;

    public MutableLiveData<List<Current_Product>> getCurrentProducts() {
        MutableLiveData<List<Current_Product>> currentProducts = new MutableLiveData<>();
        Call<Current_Products> call = RetrofitClient.getInstance().getMyApi().getCurrentProducts();
        call.enqueue(new Callback<Current_Products>() {
            @Override
            public void onResponse(@NotNull Call<Current_Products> call, @NotNull Response<Current_Products> response) {
                currentProductsList = response.body().getCurrentProducts();
                Log.d("Hi", "Hey");
                currentProducts.postValue(currentProductsList);
            }

            @Override
            public void onFailure(@NotNull Call<Current_Products> call, @NotNull Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return currentProducts;
    }

}
