package com.example.bidbadnew.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bidbadnew.Model.WishListItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishListRepo {

    public static WishListRepo wishListRepo;

    public static WishListRepo getInstance(){
        if(wishListRepo == null){
            wishListRepo = new WishListRepo();
        }
        return wishListRepo;
    }

    public MutableLiveData<List<WishListItem>> getWishList(int id) {
        MutableLiveData<List<WishListItem>> myOngoing = new MutableLiveData<>();
        Call<List<WishListItem>> call = RetrofitClient.getInstance().getMyApi().getWishlist(id);
        call.enqueue(new Callback<List<WishListItem>>() {
            @Override
            public void onResponse(Call<List<WishListItem>> call, Response<List<WishListItem>> response) {
                myOngoing.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<WishListItem>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return myOngoing;
    }
}
