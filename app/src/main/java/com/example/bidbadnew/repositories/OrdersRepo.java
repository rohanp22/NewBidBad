package com.example.bidbadnew.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bidbadnew.Model.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersRepo {

    public static OrdersRepo ordersRepo;

    public static OrdersRepo getInstance(){
        if(ordersRepo == null){
            ordersRepo = new OrdersRepo();
        }
        return ordersRepo;
    }

    public MutableLiveData<List<Order>> getMyOngoingBids(int id) {
        MutableLiveData<List<Order>> myOngoing = new MutableLiveData<>();
        Call<List<Order>> call = RetrofitClient.getInstance().getMyApi().getMyOrders(id);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                myOngoing.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return myOngoing;
    }
    
}
