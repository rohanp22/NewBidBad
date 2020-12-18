package com.example.bidbadnew.Fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bidbadnew.Model.Order;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.repositories.OrdersRepo;

import java.util.List;

public class OrdersViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<List<Order>> orders;
    int id;

    public void init(int id){
        if(orders != null){
            return;
        }
        this.id = id;
        OrdersRepo ordersRepo = OrdersRepo.getInstance();
        orders = ordersRepo.getMyOngoingBids(id);
    }

    public MutableLiveData<List<Order>> getOrder() {
        return orders;
    }
}