package com.example.bidbadnew.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bidbadnew.Model.MyBid;
import com.example.bidbadnew.Model.MyBids;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBidsRepo {

    public static MyBidsRepo myBidsRepo;

    public static MyBidsRepo getInstance(){
        if(myBidsRepo == null){
            myBidsRepo = new MyBidsRepo();
        }
        return myBidsRepo;
    }

    public static List<MyBid> myBidsList;

    public MutableLiveData<List<MyBid>> getMyBids(int id) {
        MutableLiveData<List<MyBid>> myBids = new MutableLiveData<>();
        Call<MyBids> call = RetrofitClient.getInstance().getMyApi().getMyBids(id);
        call.enqueue(new Callback<MyBids>() {
            @Override
            public void onResponse(Call<MyBids> call, Response<MyBids> response) {
                myBidsList = response.body().getBids();
                myBids.postValue(myBidsList);
            }

            @Override
            public void onFailure(Call<MyBids> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return myBids;
    }
    
}
