package com.example.bidbadnew.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bidbadnew.Api.Api;
import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.Model.Current_Products;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentProductsRepo {

    public static CurrentProductsRepo currentProductsRepo;

    public static CurrentProductsRepo getInstance() {
        if (currentProductsRepo == null) {
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
    public MutableLiveData<List<Current_Product>> freeBids = new MutableLiveData<>();

    public MutableLiveData<List<Current_Product>> getFreeBids() {
        return freeBids;
    }

    public MutableLiveData<List<Current_Product>> getElectronicsProductCategory() {
        return electronicsProductCategory;
    }

    public MutableLiveData<List<Current_Product>> getApparelProductCategory() {
        return apparelProductCategory;
    }

    public MutableLiveData<List<Current_Product>> getAppliancesCategory() {
        return appliancesCategory;
    }

    public MutableLiveData<List<Current_Product>> getOthersCategory() {
        return othersCategory;
    }

    public MutableLiveData<List<Current_Product>> getFitnessProductCategory() {
        return fitnessProductCategory;
    }

    public MutableLiveData<List<Current_Product>> getAccessoriesCategory() {
        return accessoriesCategory;
    }

    public MutableLiveData<List<Current_Product>> getHomeProductCategory() {
        return homeProductCategory;
    }

    public MutableLiveData<List<Current_Product>> getPersonalcareProductCategory() {
        return personalcareProductCategory;
    }

    Api retrofitClient = RetrofitClient.getInstance().getMyApi();

    public void getProducts(int position, int userid) {
        Log.d("position", position + "");
        Call<Current_Products> call = null;
        switch (position) {
            case 1: {
                call = retrofitClient.getCurrentProducts(userid, "Electronics");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if(response.body() != null)
                        electronicsProductCategory.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
            }
            case 2:
                call = retrofitClient.getCurrentProducts(userid, "Appliances");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if(response.body() != null)
                            appliancesCategory.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
            case 3:
                call = retrofitClient.getCurrentProducts(userid, "Accessories");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if(response.body() != null)
                            accessoriesCategory.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
            case 4:
                call = retrofitClient.getCurrentProducts(userid, "Personalcare");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if(response.body() != null)
                            personalcareProductCategory.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
            case 5:
                call = retrofitClient.getCurrentProducts(userid, "Home");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if(response.body() != null)
                            homeProductCategory.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
            case 6:
                call = retrofitClient.getCurrentProducts(userid, "Fitness");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if(response.body() != null)
                            fitnessProductCategory.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
            case 7:
                call = retrofitClient.getCurrentProducts(userid, "Others");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if(response.body() != null)
                            othersCategory.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
            case 8:
                call = retrofitClient.getCurrentProducts(userid, "Apparel");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if(response.body() != null)
                            apparelProductCategory.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
            case 0:
                call = retrofitClient.getCurrentProducts(userid, "Freebid");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if(response.body() != null)
                            freeBids.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
        }
    }
}
