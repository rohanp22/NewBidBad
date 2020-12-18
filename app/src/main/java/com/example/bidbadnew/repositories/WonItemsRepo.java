package com.example.bidbadnew.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bidbadnew.Model.OngoingItems;
import com.example.bidbadnew.Model.WonItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WonItemsRepo {

    public static WonItemsRepo wonItemsRepo;

    public static WonItemsRepo getInstance(){
        if(wonItemsRepo == null){
            wonItemsRepo = new WonItemsRepo();
        }
        return wonItemsRepo;
    }

    public MutableLiveData<List<WonItem>> getMyWonItems(int id) {
        MutableLiveData<List<WonItem>> myOngoing = new MutableLiveData<>();
        Call<List<WonItem>> call = RetrofitClient.getInstance().getMyApi().getMyWins(id);
        call.enqueue(new Callback<List<WonItem>>() {
            @Override
            public void onResponse(Call<List<WonItem>> call, Response<List<WonItem>> response) {
                System.out.println(response.body());
                myOngoing.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<WonItem>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return myOngoing;
    }
    
}
