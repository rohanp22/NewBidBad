package com.example.bidbadnew.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bidbadnew.Model.MyBid;
import com.example.bidbadnew.Model.MyBids;
import com.example.bidbadnew.Model.OngoingItems;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OngoingBidsRepo {

    public static OngoingBidsRepo ongoingBidsRepo;

    public static OngoingBidsRepo getInstance(){
        if(ongoingBidsRepo == null){
            ongoingBidsRepo = new OngoingBidsRepo();
        }
        return ongoingBidsRepo;
    }

    public MutableLiveData<List<OngoingItems>> getMyOngoingBids(int id) {
        MutableLiveData<List<OngoingItems>> myOngoing = new MutableLiveData<>();
        Call<List<OngoingItems>> call = RetrofitClient.getInstance().getMyApi().getMyOngoingBids(id);
        call.enqueue(new Callback<List<OngoingItems>>() {
            @Override
            public void onResponse(Call<List<OngoingItems>> call, Response<List<OngoingItems>> response) {
                myOngoing.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<OngoingItems>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return myOngoing;
    }

}
