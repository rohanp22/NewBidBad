package com.example.bidbadnew.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bidbadnew.Api.Api;
import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.Model.Current_Products;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
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

    // CurrentProduct item for free bid
    public MutableLiveData<Current_Product> currentProduct = new MutableLiveData<>();

    public MutableLiveData<Current_Product> getCurrentProduct() {
        return currentProduct;
    }

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

    public void getProduct(int productId) {
        Call<Current_Products> call = retrofitClient.getProductInfo(productId);
        Log.d("URRRRRL", call.request().url().url().toString());
        call.enqueue(new Callback<Current_Products>() {
            @Override
            public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                if (response.code() == 200 && response.body() != null)
                    currentProduct.setValue(response.body().getCurrentProducts().get(0));
            }

            @Override
            public void onFailure(Call<Current_Products> call, Throwable t) {

            }
        });

    }

    public void getProducts(int position, int userid) {
        Log.d("position", position + "");
        Call<Current_Products> call = null;
        switch (position) {
            case 0: {
                call = retrofitClient.getCurrentProducts(userid, "Electronics");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if (response.body() != null)
                            electronicsProductCategory.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
            }
            case 1:
                call = retrofitClient.getCurrentProducts(userid, "Appliances");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if (response.body() != null)
                            appliancesCategory.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
            case 2:
                call = retrofitClient.getCurrentProducts(userid, "Accessories");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if (response.body() != null)
                            accessoriesCategory.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
            case 3:
                call = retrofitClient.getCurrentProducts(userid, "Personal Care");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if (response.body() != null)
                            personalcareProductCategory.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
            case 4:
                call = retrofitClient.getCurrentProducts(userid, "Home and Furniture");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if (response.body() != null)
                            homeProductCategory.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
            case 5:
                call = retrofitClient.getCurrentProducts(userid, "Fitness");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if (response.body() != null)
                            fitnessProductCategory.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
            case 6:
                call = retrofitClient.getCurrentProducts(userid, "Other");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if (response.body() != null)
                            othersCategory.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
            case 7:
                call = retrofitClient.getCurrentProducts(userid, "Apparel");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if (response.body() != null)
                            apparelProductCategory.setValue(response.body().getCurrentProducts());
                    }

                    @Override
                    public void onFailure(Call<Current_Products> call, Throwable t) {

                    }
                });
                break;
            case 8:
                call = retrofitClient.getCurrentProducts(userid, "Free Bid");
                call.enqueue(new Callback<Current_Products>() {
                    @Override
                    public void onResponse(Call<Current_Products> call, Response<Current_Products> response) {
                        if (response.body() != null)
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
