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

    public static CurrentProductsRepo getInstance(int id){
        if(currentProductsRepo == null){
            currentProductsRepo = new CurrentProductsRepo();
        }
        return currentProductsRepo;
    }

    public MutableLiveData<List<Current_Product>> homeProductCategory = new MutableLiveData<>();
    public MutableLiveData<List<Current_Product>> electronicsProductCategory = new MutableLiveData<>();
    public MutableLiveData<List<Current_Product>> apparelProductCategory = new MutableLiveData<>();
    public MutableLiveData<List<Current_Product>> fitnessProductCategory = new MutableLiveData<>();
    public MutableLiveData<List<Current_Product>> personalcareProductCategory = new MutableLiveData<>();
    public MutableLiveData<List<Current_Product>> accessoriesCategory = new MutableLiveData<>();
    public MutableLiveData<List<Current_Product>> othersCategory = new MutableLiveData<>();
    public MutableLiveData<List<Current_Product>> appliancesCategory = new MutableLiveData<>();

    public MutableLiveData<List<Current_Product>> getAccessoriesCategory(int id) {
        Call<Current_Products> call = RetrofitClient.getInstance().getMyApi().getCurrentProducts(id, "Accessories");
        call.enqueue(new Callback<Current_Products>() {
            @Override
            public void onResponse(@NotNull Call<Current_Products> call, @NotNull Response<Current_Products> response) {
                accessoriesCategory.postValue(response.body().getCurrentProducts());
            }

            @Override
            public void onFailure(@NotNull Call<Current_Products> call, @NotNull Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return accessoriesCategory;
    }

    public MutableLiveData<List<Current_Product>> getApparelProductCategory(int id) {
        Call<Current_Products> call = RetrofitClient.getInstance().getMyApi().getCurrentProducts(id, "Apparel");
        call.enqueue(new Callback<Current_Products>() {
            @Override
            public void onResponse(@NotNull Call<Current_Products> call, @NotNull Response<Current_Products> response) {
                apparelProductCategory.postValue(response.body().getCurrentProducts());
            }

            @Override
            public void onFailure(@NotNull Call<Current_Products> call, @NotNull Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return apparelProductCategory;
    }

    public MutableLiveData<List<Current_Product>> getOthersCategory(int id) {
        Call<Current_Products> call = RetrofitClient.getInstance().getMyApi().getCurrentProducts(id, "Others");
        call.enqueue(new Callback<Current_Products>() {
            @Override
            public void onResponse(@NotNull Call<Current_Products> call, @NotNull Response<Current_Products> response) {
                othersCategory.postValue(response.body().getCurrentProducts());
            }

            @Override
            public void onFailure(@NotNull Call<Current_Products> call, @NotNull Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return othersCategory;
    }

    public MutableLiveData<List<Current_Product>> getAppliancesCategory(int id) {
        Call<Current_Products> call = RetrofitClient.getInstance().getMyApi().getCurrentProducts(id, "Appliances");
        call.enqueue(new Callback<Current_Products>() {
            @Override
            public void onResponse(@NotNull Call<Current_Products> call, @NotNull Response<Current_Products> response) {
                Log.d("Category loaded", "Appliances");
                appliancesCategory.postValue(response.body().getCurrentProducts());
                Log.d("Appliances", response.body().getCurrentProducts().size() + "");
            }

            @Override
            public void onFailure(@NotNull Call<Current_Products> call, @NotNull Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return appliancesCategory;
    }

    public MutableLiveData<List<Current_Product>> getPersonalcareProductCategory(int id) {
        Call<Current_Products> call = RetrofitClient.getInstance().getMyApi().getCurrentProducts(id, "Personalcare");
        call.enqueue(new Callback<Current_Products>() {
            @Override
            public void onResponse(@NotNull Call<Current_Products> call, @NotNull Response<Current_Products> response) {
                personalcareProductCategory.postValue(response.body().getCurrentProducts());
            }

            @Override
            public void onFailure(@NotNull Call<Current_Products> call, @NotNull Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return personalcareProductCategory;
    }

    public MutableLiveData<List<Current_Product>> getElectronicsProductCategory(int id) {
        Call<Current_Products> call = RetrofitClient.getInstance().getMyApi().getCurrentProducts(id, "Electronics");
        call.enqueue(new Callback<Current_Products>() {
            @Override
            public void onResponse(@NotNull Call<Current_Products> call, @NotNull Response<Current_Products> response) {
                Log.d("Category loaded", "Electronics");
                Log.d("Size", response.body().getCurrentProducts().size()+"");
                electronicsProductCategory.postValue(response.body().getCurrentProducts());
            }

            @Override
            public void onFailure(@NotNull Call<Current_Products> call, @NotNull Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return electronicsProductCategory;
    }

    public MutableLiveData<List<Current_Product>> getFitnessProductCategory(int id) {
        Call<Current_Products> call = RetrofitClient.getInstance().getMyApi().getCurrentProducts(id, "Fitness");
        call.enqueue(new Callback<Current_Products>() {
            @Override
            public void onResponse(@NotNull Call<Current_Products> call, @NotNull Response<Current_Products> response) {
                fitnessProductCategory.postValue(response.body().getCurrentProducts());
            }

            @Override
            public void onFailure(@NotNull Call<Current_Products> call, @NotNull Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return fitnessProductCategory;
    }

    public MutableLiveData<List<Current_Product>> getHomeProductCategory(int id) {
        Call<Current_Products> call = RetrofitClient.getInstance().getMyApi().getCurrentProducts(id, "Home");
        call.enqueue(new Callback<Current_Products>() {
            @Override
            public void onResponse(@NotNull Call<Current_Products> call, @NotNull Response<Current_Products> response) {
                homeProductCategory.postValue(response.body().getCurrentProducts());
            }

            @Override
            public void onFailure(@NotNull Call<Current_Products> call, @NotNull Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return homeProductCategory;
    }

}
